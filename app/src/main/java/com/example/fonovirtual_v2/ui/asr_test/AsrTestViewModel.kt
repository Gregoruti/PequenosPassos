package com.example.fonovirtual_v2.ui.asr_test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fonovirtual_v2.speech.AsrInitializationStatus
import com.example.fonovirtual_v2.speech.AsrRecognitionStatus
import com.example.fonovirtual_v2.speech.VoskAsrModule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine // Import combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de Teste de ASR (`AsrTestScreen`).
 *
 * Responsável por gerenciar o estado da UI e a lógica de interação com o [VoskAsrModule].
 * Expõe o status da inicialização do ASR, o status do reconhecimento, os resultados parciais,
 * os resultados finais e quaisquer mensagens de erro.
 *
 * **Nota sobre Permissões:** Este ViewModel assume que a permissão `RECORD_AUDIO`
 * é tratada e concedida pela UI (Activity/Screen) antes de chamar [startRecognition].
 *
 * @param application A instância da aplicação, necessária para o contexto do [VoskAsrModule].
 * @since 1.0.1
 * @updated 1.0.2 - Integração com VoskAsrModule, exposição de StateFlows e funções de controle.
 * @updated 1.0.3 - Corrigido erro de tipagem no combine de errorMessage utilizando kotlinx.coroutines.flow.combine.
 * @validationStatus Em Teste
 */
class AsrTestViewModel(application: Application) : AndroidViewModel(application) {

    private val voskAsrModule = VoskAsrModule(application.applicationContext)

    // Status da Inicialização do Modelo ASR
    val initializationStatus: StateFlow<AsrInitializationStatus> = voskAsrModule.initializationStatus
        .stateIn(viewModelScope, SharingStarted.Lazily, AsrInitializationStatus.Idle)

    // Status do Reconhecimento de Fala
    val recognitionStatus: StateFlow<AsrRecognitionStatus> = voskAsrModule.recognitionStatus
        .stateIn(viewModelScope, SharingStarted.Lazily, AsrRecognitionStatus.Idle)

    // Último resultado parcial reconhecido
    val lastPartialResult: StateFlow<String> = voskAsrModule.lastPartialResult
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    // Último resultado final reconhecido
    val finalResult: StateFlow<String> = voskAsrModule.finalResult
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    // Mensagem de Erro (combinada de erros de inicialização e reconhecimento)
    val errorMessage: StateFlow<String?> = combine(
        voskAsrModule.initializationStatus.map { status ->
            if (status is AsrInitializationStatus.Error) status.message else null
        },
        voskAsrModule.recognitionStatus.map { status ->
            if (status is AsrRecognitionStatus.Error) status.message else null
        }
    ) { initError: String?, recError: String? ->
        initError ?: recError
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = (voskAsrModule.initializationStatus.value as? AsrInitializationStatus.Error)?.message
            ?: (voskAsrModule.recognitionStatus.value as? AsrRecognitionStatus.Error)?.message
    )


    init {
        // O VoskAsrModule já inicia o carregamento do modelo em seu próprio init.
        // Podemos observar o initializationStatus para reagir a sucesso/falha se necessário.
        viewModelScope.launch {
            initializationStatus.collect { status ->
                // Log ou tratar status de inicialização se necessário aqui
            }
        }
    }

    /**
     * Inicia o processo de reconhecimento de fala.
     *
     * Delega a chamada para [VoskAsrModule.startListening].
     * A UI deve garantir que a permissão RECORD_AUDIO foi concedida.
     */
    fun startRecognition() {
        if (initializationStatus.value is AsrInitializationStatus.Initialized) {
            voskAsrModule.startListening()
        } else {
            // Tratar caso o modelo não esteja pronto (ex: exibir mensagem de erro específica)
            // O errorMessage StateFlow já deve refletir o erro de inicialização.
        }
    }

    /**
     * Para o processo de reconhecimento de fala.
     *
     * Delega a chamada para [VoskAsrModule.stopListening].
     */
    fun stopRecognition() {
        voskAsrModule.stopListening()
    }

    /**
     * Chamado quando o ViewModel está prestes a ser destruído.
     * Libera os recursos do [VoskAsrModule].
     */
    override fun onCleared() {
        super.onCleared()
        voskAsrModule.release()
    }
}
// A função de extensão combineWith foi removida pois estamos usando kotlinx.coroutines.flow.combine diretamente.