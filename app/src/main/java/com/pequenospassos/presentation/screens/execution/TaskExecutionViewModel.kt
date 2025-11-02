package com.pequenospassos.presentation.screens.execution

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.repository.TaskRepository
import com.pequenospassos.domain.repository.AppSettingsRepository
import com.pequenospassos.domain.usecase.GetTaskByIdUseCase
import com.pequenospassos.domain.usecase.GetStepsByTaskUseCase
import com.pequenospassos.domain.usecase.GetChildProfileUseCase
import com.pequenospassos.presentation.utils.TtsManager
import com.pequenospassos.presentation.utils.AsrManager
import com.pequenospassos.presentation.utils.VoiceCommandParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para TaskExecutionScreen.
 *
 * Gerencia:
 * - Carregamento da tarefa e steps
 * - Timer countdown com pause/resume
 * - Navegação entre steps
 * - Conclusão da tarefa
 * - MVP-09: Marcação de tarefa completada no dia
 *
 * @since MVP-07 (17/10/2025)
 * @updated MVP-09 (24/10/2025) - Controle diário de tarefas
 */
@HiltViewModel
class TaskExecutionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getStepsByTaskUseCase: GetStepsByTaskUseCase,
    private val getChildProfileUseCase: GetChildProfileUseCase,
    private val taskRepository: TaskRepository,
    private val ttsManager: TtsManager,
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskExecutionState())
    val state: StateFlow<TaskExecutionState> = _state.asStateFlow()

    private var timerJob: Job? = null
    private var currentTask: Task? = null
    private var steps: List<Step> = emptyList()
    
    // MVP-14 Fase 5: ASR e Voice Command
    private lateinit var asrManager: AsrManager
    private val voiceCommandParser = VoiceCommandParser()
    
    // MVP-14 Fase 5: Estado de escuta de voz
    private val _isListeningVoice = MutableStateFlow(false)
    val isListeningVoice: StateFlow<Boolean> = _isListeningVoice.asStateFlow()
    
    // MVP-14 Fase 5: Mensagem de erro de reconhecimento de voz
    private val _voiceRecognitionError = MutableStateFlow<String?>(null)
    val voiceRecognitionError: StateFlow<String?> = _voiceRecognitionError.asStateFlow()

    // MVP-14 Fase 6: Permissão de microfone
    private val _hasMicrophonePermission = MutableStateFlow(false)
    val hasMicrophonePermission: StateFlow<Boolean> = _hasMicrophonePermission.asStateFlow()

    // MVP-14 Fase 5: Observa configuração de "Perguntar se deseja mais tempo"
    private var askExtraTimeAtStep: Boolean = true

    // MVP-14 Fase 5: Observa configuração de "Resposta em Áudio"
    private var enableVoiceResponse: Boolean = false

    init {
        // MVP-14 Fase 5: Observar configurações
        viewModelScope.launch {
            appSettingsRepository.getSettings().collect { settings ->
                askExtraTimeAtStep = settings?.askExtraTimeAtStep ?: true
            }
        }
        viewModelScope.launch {
            appSettingsRepository.getEnableVoiceResponse().collect { enabled ->
                enableVoiceResponse = enabled
            }
        }
    }

    /**
     * Lista de mensagens alternativas para quando o tempo se esgota.
     * Mensagens são sutis e encorajadoras, sem pressionar a criança.
     */
    private fun getRandomTimeUpMessage(childName: String): String {
        val messages = listOf(
            "$childName, podemos ir para o próximo passo?",
            "$childName, vamos continuar a atividade?",
            "$childName, que tal seguirmos em frente?",
            "$childName, já podemos avançar?",
            "$childName, está pronto para o próximo passo?",
            "$childName, vamos para a próxima parte?",
            "$childName, deseja continuar?"
        )
        return messages.random()
    }

    /**
     * Carrega a tarefa e seus steps.
     */
    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)
                
                // MVP-14 Fase 5: Inicializar ASR na primeira carga
                if (!::asrManager.isInitialized) {
                    asrManager = AsrManager(context)
                    asrManager.initialize(
                        onSuccess = {
                            println("[TaskExecutionVM] AsrManager inicializado com sucesso")
                        },
                        onError = { error ->
                            println("[TaskExecutionVM] Erro ao inicializar ASR: $error")
                        }
                    )
                }

                // Coletar o perfil da criança
                val childProfile = getChildProfileUseCase().firstOrNull()
                val childName = childProfile?.name ?: "Amiguinho"

                // Coletar task do Flow
                val task = getTaskByIdUseCase(taskId).firstOrNull()
                if (task == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "Tarefa não encontrada"
                    )
                    return@launch
                }

                currentTask = task

                // Coletar steps do Flow
                steps = getStepsByTaskUseCase(taskId).firstOrNull()?.sortedBy { it.order } ?: emptyList()

                // Debug: verificar dados dos steps carregados
                steps.forEachIndexed { index, step ->
                    println("TaskExecutionVM: Step ${index + 1} - title: '${step.title}', imageUrl: '${step.imageUrl}', duration: ${step.durationSeconds}s")
                }

                if (steps.isEmpty()) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "Esta tarefa não possui passos"
                    )
                    return@launch
                }

                // Iniciar no primeiro step
                _state.value = _state.value.copy(
                    isLoading = false,
                    taskTitle = task.title,
                    taskStars = task.stars,
                    currentStepIndex = 0,
                    totalSteps = steps.size,
                    currentStep = steps[0],
                    remainingSeconds = steps[0].durationSeconds,
                    isPaused = false,
                    childName = childName
                )

                // Ler título da tarefa
                ttsManager.speak(task.title)

                // Aguardar um pouco e ler o primeiro passo
                delay(2000) // Esperar 2 segundos após falar o título
                val firstStepText = "${childName}, ${steps[0].title}"
                ttsManager.speakQueued(firstStepText)

                // Iniciar timer
                startTimer()

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Erro ao carregar tarefa: ${e.message}"
                )
            }
        }
    }

    /**
     * Inicia o countdown do timer.
     */
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_state.value.remainingSeconds > 0 && !_state.value.isPaused) {
                delay(1000) // 1 segundo
                if (!_state.value.isPaused) {
                    _state.value = _state.value.copy(
                        remainingSeconds = _state.value.remainingSeconds - 1
                    )
                }
            }
            // Timer chegou a zero
            if (_state.value.remainingSeconds == 0) {
                // MVP-14 Fase 5: Consultar configuração askExtraTimeAtStep
                val askExtra = askExtraTimeAtStep

                if (askExtra) {
                    val message = getRandomTimeUpMessage(_state.value.childName)
                    _state.value = _state.value.copy(
                        showTimeUpDialog = true,
                        timeUpMessage = message
                    )

                    // MVP-14 Fase 5: Falar mensagem e depois iniciar ASR
                    if (enableVoiceResponse) {
                        // Usar callback para iniciar ASR APÓS TTS terminar
                        ttsManager.speakWithCallback(message) {
                            println("[TaskExecutionVM] TTS terminou, iniciando ASR...")
                            startVoiceListening()
                        }
                    } else {
                        // Sem ASR, apenas falar
                        ttsManager.speak(message)
                    }
                } else {
                    // Avançar automaticamente para o próximo passo
                    nextStep()
                }
            }
        }
    }

    /**
     * Pausa ou retoma o timer.
     */
    fun togglePause() {
        val newPausedState = !_state.value.isPaused
        _state.value = _state.value.copy(isPaused = newPausedState)

        if (!newPausedState) {
            // Retomou, reiniciar timer
            startTimer()
        } else {
            // Pausou, cancelar timer
            timerJob?.cancel()
        }
    }

    /**
     * Avança para o próximo step ou conclui a tarefa.
     */
    fun nextStep() {
        timerJob?.cancel()

        val nextIndex = _state.value.currentStepIndex + 1

        if (nextIndex >= steps.size) {
            // Última step - concluir tarefa
            completeTask()
        } else {
            // Próximo step
            val nextStep = steps[nextIndex]
            _state.value = _state.value.copy(
                currentStepIndex = nextIndex,
                currentStep = nextStep,
                remainingSeconds = nextStep.durationSeconds,
                isPaused = false,
                showTimeUpDialog = false
            )

            // Ler o próximo passo com o nome da criança
            val stepText = "${_state.value.childName}, ${nextStep.title}"
            ttsManager.speak(stepText)

            startTimer()
        }
    }

    /**
     * Adiciona tempo extra ao step atual.
     */
    fun addExtraTime(seconds: Int) {
        _state.value = _state.value.copy(
            remainingSeconds = _state.value.remainingSeconds + seconds,
            showTimeUpDialog = false
        )
        startTimer()
    }

    /**
     * Fecha o dialog de tempo esgotado.
     */
    fun dismissTimeUpDialog() {
        _state.value = _state.value.copy(showTimeUpDialog = false)
    }

    /**
     * Marca a tarefa como concluída.
     * MVP-09: Agora persiste a conclusão no banco de dados.
     */
    private fun completeTask() {
        viewModelScope.launch {
            try {
                val task = currentTask ?: return@launch
                // Por enquanto, usar childId hardcoded (será do perfil selecionado no futuro)
                val childId = 1L

                // MVP-09: Marcar tarefa como completada no banco
                val result = taskRepository.markTaskAsCompleted(
                    taskId = task.id.toString(),
                    childId = childId,
                    starsEarned = task.stars
                )

                if (result.isSuccess) {
                    println("TaskExecutionVM: Tarefa ${task.id} marcada como completada! ${task.stars} estrelas ganhas.")
                } else {
                    println("TaskExecutionVM: Erro ao marcar tarefa como completada: ${result.exceptionOrNull()?.message}")
                }

                // Marca como completa no estado (navega para tela de conclusão)
                _state.value = _state.value.copy(isCompleted = true)

            } catch (e: Exception) {
                println("TaskExecutionVM: Exceção ao marcar tarefa como completada: ${e.message}")
                // Mesmo com erro, marca como completa para não travar a navegação
                _state.value = _state.value.copy(isCompleted = true)
            }
        }
    }

    // ========================================
    // MVP-14 FASE 5: RECONHECIMENTO DE VOZ
    // ========================================

    /**
     * Atualiza status de permissão de microfone.
     * MVP-14 Fase 6.
     */
    fun updateMicrophonePermission(granted: Boolean) {
        _hasMicrophonePermission.value = granted
        println("[TaskExecutionVM] Permissão de microfone: $granted")
    }

    /**
     * Inicia reconhecimento de voz para capturar comando.
     * MVP-14 Fase 5.
     */
    private fun startVoiceListening() {
        // MVP-14 Fase 6: Verificar permissão de microfone
        if (!_hasMicrophonePermission.value) {
            println("[TaskExecutionVM] ❌ Permissão de microfone negada")
            _voiceRecognitionError.value = "Permissão de microfone necessária"
            return
        }

        _isListeningVoice.value = true
        _voiceRecognitionError.value = null

        println("[TaskExecutionVM] 🎤 Iniciando reconhecimento de voz (30 segundos)")

        asrManager.startListeningWithTimeout(
            timeoutSeconds = 30, // MVP-14: Aumentado de 3 para 30 segundos
            listener = object : AsrManager.VoiceRecognitionListener {
                override fun onResult(text: String) {
                    println("[TaskExecutionVM] ✅ Texto reconhecido: '$text'")
                    processVoiceCommand(text)
                }

                override fun onPartialResult(text: String) {
                    println("[TaskExecutionVM] 📝 Parcial: '$text'")
                    // Opcional: Atualizar UI com texto parcial
                }

                override fun onError(error: String) {
                    println("[TaskExecutionVM] ❌ Erro ASR: $error")
                    _voiceRecognitionError.value = "Erro no reconhecimento: $error"
                    _isListeningVoice.value = false
                    // Pop-up continua aberto, botões manuais disponíveis
                }

                override fun onTimeout() {
                    println("[TaskExecutionVM] ⏱️ Timeout - sem fala detectada")
                    _isListeningVoice.value = false
                    // Pop-up continua aberto, botões manuais disponíveis
                }
            }
        )
    }

    /**
     * Processa comando de voz reconhecido.
     * MVP-14 Fase 5.
     */
    private fun processVoiceCommand(recognizedText: String) {
        val result = voiceCommandParser.parse(recognizedText)

        _isListeningVoice.value = false

        when (result) {
            VoiceCommandParser.CommandResult.POSITIVE -> {
                println("[TaskExecutionVM] ✅ Comando POSITIVO - Avançar")
                // Fecha pop-up e avança para próximo step
                _state.value = _state.value.copy(showTimeUpDialog = false)
                nextStep()
            }
            VoiceCommandParser.CommandResult.NEGATIVE -> {
                println("[TaskExecutionVM] ⏱️ Comando NEGATIVO - Mais 30 segundos")
                // Fecha pop-up e adiciona tempo
                _state.value = _state.value.copy(showTimeUpDialog = false)
                addExtraTime(30)
            }
            VoiceCommandParser.CommandResult.UNKNOWN -> {
                println("[TaskExecutionVM] ❓ Comando DESCONHECIDO: '$recognizedText'")
                _voiceRecognitionError.value = "Não entendi '$recognizedText'. Use os botões abaixo."
                // Pop-up continua aberto, botões manuais disponíveis
            }
        }
    }

    /**
     * Chamado quando usuário clica em botão manual.
     * Cancela escuta de voz se estiver ativa.
     * MVP-14 Fase 5.
     */
    fun onManualButtonClick(action: String) {
        // Se está escutando, para o ASR
        if (_isListeningVoice.value) {
            println("[TaskExecutionVM] 🛑 Usuário clicou em botão - cancelando ASR")
            asrManager.stopListening()
            _isListeningVoice.value = false
        }

        // Executa ação
        when (action) {
            "NEXT" -> {
                _state.value = _state.value.copy(showTimeUpDialog = false)
                nextStep()
            }
            "MORE_TIME" -> {
                _state.value = _state.value.copy(showTimeUpDialog = false)
                addExtraTime(30)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        ttsManager.stop() // Para qualquer fala em andamento
        // MVP-14 Fase 5: Liberar recursos do ASR
        asrManager.release()
    }
}

/**
 * Estado da tela de execução.
 */
data class TaskExecutionState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val taskTitle: String = "",
    val taskStars: Int = 5,
    val currentStepIndex: Int = 0,
    val totalSteps: Int = 0,
    val currentStep: Step? = null,
    val remainingSeconds: Int = 0,
    val isPaused: Boolean = false,
    val showTimeUpDialog: Boolean = false,
    val isCompleted: Boolean = false,
    val childName: String = "",
    val timeUpMessage: String = ""
)
