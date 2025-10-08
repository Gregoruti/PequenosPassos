package com.example.fonovirtual_v2.ui.tts_test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fonovirtual_v2.speech.TextToSpeechModule
import com.example.fonovirtual_v2.speech.TtsInitializationStatus
import com.example.fonovirtual_v2.speech.TtsSpeakingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de teste de Text-to-Speech (TTS - [TtsTestScreen]).
 *
 * Este ViewModel gerencia a lógica de interação com o [TextToSpeechModule],
 * incluindo a inicialização, o envio de texto para ser falado, e o monitoramento
 * dos status de inicialização e de fala do TTS. Ele também expõe o estado
 * relevante para a UI e lida com o ciclo de vida do módulo TTS.
 *
 * @param application O contexto da aplicação, necessário para o [TextToSpeechModule].
 * @property ttsModule Instância do [TextToSpeechModule] para interagir com o motor TTS.
 * @constructor Cria uma instância de TtsTestViewModel.
 * @since 0.4.0
 * @validationStatus Validado (Funcionalidade de gerenciamento de estado e interação com TextToSpeechModule operacionais em DD/MM/AAAA - v0.4.0)
 */
class TtsTestViewModel(application: Application) : AndroidViewModel(application) {

    val ttsModule: TextToSpeechModule = TextToSpeechModule(application.applicationContext)

    /**
     * Fluxo de estado que representa o texto atualmente inserido pelo usuário na UI.
     */
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    /**
     * Estado da interface do usuário para a tela de teste TTS.
     * Combina os status de inicialização e de fala do TTS, bem como o texto de entrada atual.
     * Agora inclui `inputText` como uma fonte direta para que o `uiState` reaja às mudanças de texto.
     * Utiliza [stateIn] para converter o Flow em um StateFlow que pode ser coletado pela UI.
     */
    val uiState: StateFlow<TtsTestUiState> = combine(
        ttsModule.initializationStatus,
        ttsModule.speakingStatus,
        inputText // inputText agora é uma fonte direta do combine
    ) { initStatus, speakStatus, currentText ->
        TtsTestUiState(initStatus, speakStatus, currentText)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = TtsTestUiState(TtsInitializationStatus.Idle, TtsSpeakingStatus.Idle, "")
    )

    /**
     * Atualiza o texto de entrada com base na interação do usuário.
     *
     * @param newInput O novo texto inserido pelo usuário.
     */
    fun onInputTextChanged(newInput: String) {
        _inputText.value = newInput
    }

    /**
     * Solicita ao [TextToSpeechModule] para falar o texto atualmente armazenado em [_inputText].
     * A lógica de verificar se o TTS está pronto ou se o texto é válido está no [TextToSpeechModule].
     */
    fun speakText() {
        // Acessa o valor mais recente de _inputText, que é a fonte da verdade para o texto a ser falado.
        val textToSpeak = _inputText.value
        if (textToSpeak.isNotBlank()) {
            viewModelScope.launch {
                ttsModule.speak(textToSpeak)
            }
        }
    }

    /**
     * Chamado quando o ViewModel está prestes a ser destruído.
     * Garante que o [TextToSpeechModule] seja desligado para liberar recursos.
     */
    override fun onCleared() {
        super.onCleared()
        ttsModule.shutdown()
    }
}

/**
 * Representa o estado da UI para a tela de teste TTS ([TtsTestScreen]).
 *
 * @property initializationStatus O estado atual da inicialização do motor TTS.
 * @property speakingStatus O estado atual da fala do motor TTS (ocioso, falando, erro).
 * @property currentInputText O texto atualmente digitado pelo usuário no campo de entrada.
 */
data class TtsTestUiState(
    val initializationStatus: TtsInitializationStatus = TtsInitializationStatus.Idle,
    val speakingStatus: TtsSpeakingStatus = TtsSpeakingStatus.Idle,
    val currentInputText: String = ""
)
