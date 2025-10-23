package com.pequenospassos.presentation.screens.execution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.usecase.GetTaskByIdUseCase
import com.pequenospassos.domain.usecase.GetStepsByTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
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
 *
 * @since MVP-07 (17/10/2025)
 */
@HiltViewModel
class TaskExecutionViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getStepsByTaskUseCase: GetStepsByTaskUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskExecutionState())
    val state: StateFlow<TaskExecutionState> = _state.asStateFlow()

    private var timerJob: Job? = null
    private var currentTask: Task? = null
    private var steps: List<Step> = emptyList()

    /**
     * Carrega a tarefa e seus steps.
     */
    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)

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
                    isPaused = false
                )

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
                _state.value = _state.value.copy(showTimeUpDialog = true)
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
     */
    private fun completeTask() {
        // Por enquanto apenas marca como completa no estado
        // No futuro, podemos adicionar persistência da conclusão
        _state.value = _state.value.copy(isCompleted = true)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
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
    val isCompleted: Boolean = false
)
