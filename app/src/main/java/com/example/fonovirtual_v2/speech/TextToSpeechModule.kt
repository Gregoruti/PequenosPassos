package com.example.fonovirtual_v2.speech

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.pequenospassos.R
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Módulo de Síntese de Fala (Text-to-Speech - TTS).
 *
 * Responsável por inicializar o motor TTS do Android, configurar o idioma para Português do Brasil,
 * e fornecer funcionalidades para converter texto em fala. Este módulo também gerencia o estado
 * de inicialização e a disponibilidade do idioma, além de prover feedback sobre o processo de fala.
 *
 * @property context Contexto da aplicação, necessário para inicializar o motor TTS.
 * @constructor Cria uma instância de TextToSpeechModule.
 * @since 0.4.0
 * @validationStatus Validado (Funcionalidade de inicialização, configuração de idioma, síntese de voz e gerenciamento de status operacionais em DD/MM/AAAA - v0.4.0)
 */
class TextToSpeechModule(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val localePtBR = Locale("pt", "BR")

    private val _initializationStatus = MutableStateFlow<TtsInitializationStatus>(TtsInitializationStatus.Idle)
    val initializationStatus: StateFlow<TtsInitializationStatus> = _initializationStatus.asStateFlow()

    private val _speakingStatus = MutableStateFlow<TtsSpeakingStatus>(TtsSpeakingStatus.Idle)
    val speakingStatus: StateFlow<TtsSpeakingStatus> = _speakingStatus.asStateFlow()

    companion object {
        private const val TAG = "TextToSpeechModule"
        private const val UTTERANCE_ID_DEFAULT = "fonovirtual_tts_utterance"
    }

    init {
        Log.d(TAG, "Iniciando a inicialização do TextToSpeech.")
        _initializationStatus.value = TtsInitializationStatus.Initializing
        try {
            tts = TextToSpeech(context, this)
        } catch (e: Exception) {
            Log.e(TAG, "Exceção durante a criação da instância de TextToSpeech.", e)
            _initializationStatus.value = TtsInitializationStatus.Error(context.getString(R.string.tts_initialization_error, e.message))
        }
    }

    /**
     * Callback invocado quando a inicialização do motor TTS é concluída.
     *
     * @param status Status da inicialização. Pode ser [TextToSpeech.SUCCESS] ou [TextToSpeech.ERROR].
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            Log.i(TAG, "Motor TTS inicializado com sucesso.")
            val result = tts?.setLanguage(localePtBR)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w(TAG, "Idioma Português (Brasil) não suportado ou dados ausentes.")
                _initializationStatus.value = TtsInitializationStatus.Error(context.getString(R.string.tts_speak_error_language_unavailable))
            } else {
                Log.i(TAG, "Idioma Português (Brasil) configurado com sucesso para TTS.")
                _initializationStatus.value = TtsInitializationStatus.Initialized
                setupProgressListener()
            }
        } else {
            Log.e(TAG, "Falha na inicialização do motor TTS. Status: $status")
            _initializationStatus.value = TtsInitializationStatus.Error(context.getString(R.string.tts_initialization_error, "Status code: $status"))
        }
    }

    /**
     * Configura um [UtteranceProgressListener] para monitorar o progresso da fala.
     * Este listener atualiza o [_speakingStatus] para refletir se o TTS está falando ou ocioso.
     */
    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.d(TAG, "TTS iniciou a fala para utteranceId: $utteranceId")
                _speakingStatus.value = TtsSpeakingStatus.Speaking
            }

            override fun onDone(utteranceId: String?) {
                Log.d(TAG, "TTS concluiu a fala para utteranceId: $utteranceId")
                _speakingStatus.value = TtsSpeakingStatus.Idle
            }

            @Deprecated("Este método foi depreciado.", ReplaceWith("onError(utteranceId, errorCode)"))
            override fun onError(utteranceId: String?) {
                Log.e(TAG, "Erro no TTS (depreciado) para utteranceId: $utteranceId")
                _speakingStatus.value = TtsSpeakingStatus.Error("Erro desconhecido durante a fala (depreciado)")
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                Log.e(TAG, "Erro no TTS para utteranceId: $utteranceId, código: $errorCode")
                _speakingStatus.value = TtsSpeakingStatus.Error("Erro durante a fala, código: $errorCode")
            }
        })
    }


    /**
     * Converte o texto fornecido em fala.
     *
     * @param text O texto a ser convertido em fala.
     *             Se o texto estiver vazio ou em branco, a operação não será realizada.
     * @param utteranceId Um identificador único para esta requisição de fala. Padrão: [UTTERANCE_ID_DEFAULT].
     * @return Boolean Retorna `true` se a requisição de fala foi enfileirada com sucesso, `false` caso contrário
     *                 (ex: TTS não inicializado, idioma não disponível, ou texto inválido).
     */
    fun speak(text: String, utteranceId: String = UTTERANCE_ID_DEFAULT): Boolean {
        if (_initializationStatus.value !is TtsInitializationStatus.Initialized) {
            Log.w(TAG, "Tentativa de falar, mas o TTS não está inicializado.")
            // Poderia também mudar o _speakingStatus para um erro específico aqui se desejado.
            return false
        }

        if (text.isBlank()) {
            Log.w(TAG, "Tentativa de falar com texto vazio ou em branco.")
            return false
        }

        // Verifica o status do speakingStatus para evitar múltiplas falas simultâneas se necessário, 
        // ou enfileirar. O TTS do Android já enfileira por padrão.
        // if (_speakingStatus.value is TtsSpeakingStatus.Speaking) {
        //     Log.w(TAG, "TTS já está falando. Nova requisição ignorada ou enfileirada.")
        //     // return false; // ou lógica de enfileiramento
        // }

        Log.i(TAG, "Solicitando TTS para falar o texto: '$text' com utteranceId: $utteranceId")
        val params = Bundle()
        // params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId) // Já é passado no método speak

        val result = tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
        return if (result == TextToSpeech.SUCCESS) {
            Log.d(TAG, "Texto enviado para a fila do TTS com sucesso.")
            // O status de fala será atualizado pelo UtteranceProgressListener
            true
        } else {
            Log.e(TAG, "Falha ao enviar texto para a fila do TTS. Código de erro: $result")
            _speakingStatus.value = TtsSpeakingStatus.Error("Falha ao iniciar a fala.")
            false
        }
    }

    /**
     * Interrompe a fala atual, se houver alguma.
     */
    fun stop() {
        if (_speakingStatus.value is TtsSpeakingStatus.Speaking) {
            Log.i(TAG, "Interrompendo a fala do TTS.")
            tts?.stop()
            _speakingStatus.value = TtsSpeakingStatus.Idle // Define como idle imediatamente após o stop
        }
    }

    /**
     * Libera os recursos utilizados pelo motor TTS.
     * Este método deve ser chamado quando o módulo TTS não for mais necessário
     * (ex: no `onCleared()` de um ViewModel ou `onDestroy()` de uma Activity/Fragment)
     * para evitar vazamentos de memória.
     */
    fun shutdown() {
        Log.i(TAG, "Desligando o motor TTS.")
        _initializationStatus.value = TtsInitializationStatus.Idle // Reseta o status
        _speakingStatus.value = TtsSpeakingStatus.Idle
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}

/**
 * Representa os possíveis estados de inicialização do [TextToSpeechModule].
 */
sealed class TtsInitializationStatus {
    /** Estado inicial ou após o shutdown. */
    object Idle : TtsInitializationStatus()
    /** O motor TTS está em processo de inicialização. */
    object Initializing : TtsInitializationStatus()
    /** O motor TTS foi inicializado com sucesso e está pronto para uso. */
    object Initialized : TtsInitializationStatus()
    /** Ocorreu um erro durante a inicialização do motor TTS. */
    data class Error(val message: String) : TtsInitializationStatus()
}

/**
 * Representa os possíveis estados de fala do [TextToSpeechModule].
 */
sealed class TtsSpeakingStatus {
    /** O motor TTS não está falando. */
    object Idle : TtsSpeakingStatus()
    /** O motor TTS está atualmente falando. */
    object Speaking : TtsSpeakingStatus()
    /** Ocorreu um erro durante a tentativa de fala. */
    data class Error(val message: String) : TtsSpeakingStatus()
}
