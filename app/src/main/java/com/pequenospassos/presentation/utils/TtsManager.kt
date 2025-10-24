package com.pequenospassos.presentation.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gerenciador de Text-to-Speech (TTS) para o aplicativo.
 *
 * Responsável por:
 * - Inicializar e gerenciar o engine TTS
 * - Configurar idioma PT-BR
 * - Falar textos com diferentes prioridades
 * - Gerenciar fila de fala
 *
 * @since MVP-08 v1.10.1 (23/10/2025)
 */
@Singleton
class TtsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var tts: TextToSpeech? = null
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    init {
        initializeTts()
    }

    /**
     * Inicializa o engine TTS com idioma PT-BR.
     */
    private fun initializeTts() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.let { engine ->
                    // Configurar idioma português brasileiro
                    val result = engine.setLanguage(Locale.forLanguageTag("pt-BR"))

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        _isReady.value = false
                        println("TtsManager: Idioma PT-BR não disponível")
                    } else {
                        // Configurar parâmetros de voz
                        engine.setPitch(1.0f) // Tom normal
                        engine.setSpeechRate(0.9f) // Velocidade um pouco mais lenta para crianças

                        // Listener para acompanhar o estado da fala
                        engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                            override fun onStart(utteranceId: String?) {
                                _isSpeaking.value = true
                            }

                            override fun onDone(utteranceId: String?) {
                                _isSpeaking.value = false
                            }

                            override fun onError(utteranceId: String?) {
                                _isSpeaking.value = false
                                println("TtsManager: Erro ao falar utterance $utteranceId")
                            }
                        })

                        _isReady.value = true
                        println("TtsManager: TTS inicializado com sucesso em PT-BR")
                    }
                }
            } else {
                _isReady.value = false
                println("TtsManager: Erro ao inicializar TTS")
            }
        }
    }

    /**
     * Fala um texto imediatamente, interrompendo qualquer fala em andamento.
     *
     * @param text Texto a ser falado
     * @param utteranceId ID opcional para identificar esta fala
     */
    fun speak(text: String, utteranceId: String = "tts_${System.currentTimeMillis()}") {
        if (!_isReady.value || text.isBlank()) {
            println("TtsManager: TTS não pronto ou texto vazio")
            return
        }

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        println("TtsManager: Falando - '$text'")
    }

    /**
     * Adiciona um texto à fila de fala (não interrompe a fala atual).
     *
     * @param text Texto a ser falado
     * @param utteranceId ID opcional para identificar esta fala
     */
    fun speakQueued(text: String, utteranceId: String = "tts_${System.currentTimeMillis()}") {
        if (!_isReady.value || text.isBlank()) {
            println("TtsManager: TTS não pronto ou texto vazio")
            return
        }

        tts?.speak(text, TextToSpeech.QUEUE_ADD, null, utteranceId)
        println("TtsManager: Adicionado à fila - '$text'")
    }

    /**
     * Para a fala atual e limpa a fila.
     */
    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        println("TtsManager: Fala interrompida")
    }

    /**
     * Verifica se o TTS está falando no momento.
     */
    fun isSpeakingNow(): Boolean {
        return tts?.isSpeaking ?: false
    }

    /**
     * Libera recursos do TTS.
     * Deve ser chamado quando o TTS não for mais necessário.
     */
    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        _isReady.value = false
        _isSpeaking.value = false
        println("TtsManager: TTS finalizado")
    }

    /**
     * Ajusta a velocidade da fala.
     *
     * @param rate Velocidade (0.5 = 50% mais lento, 1.0 = normal, 2.0 = 2x mais rápido)
     */
    fun setSpeechRate(rate: Float) {
        tts?.setSpeechRate(rate.coerceIn(0.5f, 2.0f))
    }

    /**
     * Ajusta o tom da voz.
     *
     * @param pitch Tom (0.5 = mais grave, 1.0 = normal, 2.0 = mais agudo)
     */
    fun setPitch(pitch: Float) {
        tts?.setPitch(pitch.coerceIn(0.5f, 2.0f))
    }
}

