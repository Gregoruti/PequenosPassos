package com.pequenospassos.presentation.utils

import android.content.Context
import kotlinx.coroutines.*
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.json.JSONObject
import java.io.File

/**
 * Gerenciador de ASR (Automatic Speech Recognition) com suporte a timeout.
 *
 * Arquivo: presentation/utils/AsrManager.kt
 * Tipo: Manager/Utility class
 * Objetivo: Gerenciar reconhecimento de voz com Vosk incluindo timeout automático
 * Correlações: VoiceCommandParser.kt, TaskExecutionViewModel.kt
 *
 * Histórico de alterações:
 * - 2025-11-01 (Claude Sonnet 4.5): MVP-14 Fase 4 - Criação inicial
 *   - Interface VoiceRecognitionListener com callbacks
 *   - Método startListeningWithTimeout() com timeout configurável
 *   - Gerenciamento de timeout com Coroutines
 *   - Cleanup automático ao parar reconhecimento
 *
 * Última atualização: 2025-11-01 (MVP-14 Fase 4)
 * Status: Em desenvolvimento - MVP-14
 * Build: PENDING
 *
 * Criado: 2025-11-01
 * Autor: Claude Sonnet 4.5 (GitHub Copilot)
 *
 * @since MVP-14 Fase 4 (v2.2.0)
 * @validationStatus 🔄 Em desenvolvimento - MVP-14 Fase 4
 */
class AsrManager(private val context: Context) {

    private var speechService: SpeechService? = null
    private var model: Model? = null
    private var currentListener: VoiceRecognitionListener? = null
    private var timeoutJob: Job? = null
    private var isInitialized = false

    /**
     * Listener para reconhecimento de voz em tempo real.
     *
     * Callbacks:
     * - onResult: Chamado quando reconhecimento finaliza com sucesso
     * - onPartialResult: Chamado durante reconhecimento (opcional)
     * - onError: Chamado quando ocorre erro
     * - onTimeout: Chamado quando tempo limite é atingido
     */
    interface VoiceRecognitionListener {
        /**
         * Resultado final do reconhecimento.
         * @param text Texto reconhecido
         */
        fun onResult(text: String)

        /**
         * Resultado parcial durante reconhecimento (opcional).
         * @param text Texto parcial reconhecido
         */
        fun onPartialResult(text: String) {}

        /**
         * Erro durante reconhecimento.
         * @param error Mensagem de erro
         */
        fun onError(error: String)

        /**
         * Timeout atingido sem reconhecimento.
         */
        fun onTimeout()
    }

    /**
     * Inicializa o modelo Vosk.
     * Deve ser chamado antes de startListeningWithTimeout().
     *
     * @param onSuccess Callback chamado quando inicialização completa
     * @param onError Callback chamado se houver erro
     */
    fun initialize(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (isInitialized) {
            onSuccess()
            return
        }

        try {
            val modelPath = File(context.filesDir, "vosk-model-small-pt-0.3")

            if (!modelPath.exists()) {
                onError("Modelo Vosk não encontrado. Execute a tela de teste ASR primeiro.")
                return
            }

            model = Model(modelPath.absolutePath)
            isInitialized = true

            println("[AsrManager] Modelo Vosk inicializado com sucesso")
            onSuccess()

        } catch (e: Exception) {
            println("[AsrManager] ❌ Erro ao inicializar modelo: ${e.message}")
            onError("Erro ao inicializar ASR: ${e.message}")
        }
    }

    /**
     * Inicia reconhecimento de voz com timeout.
     *
     * Fluxo:
     * 1. Verifica se está inicializado
     * 2. Inicia SpeechService do Vosk
     * 3. Agenda timeout
     * 4. Aguarda resultado ou timeout
     *
     * @param timeoutSeconds Tempo máximo de escuta em segundos (padrão: 3)
     * @param listener Callback para resultados
     */
    fun startListeningWithTimeout(
        timeoutSeconds: Int = 3,
        listener: VoiceRecognitionListener
    ) {
        if (!isInitialized || model == null) {
            listener.onError("ASR não inicializado. Chame initialize() primeiro.")
            return
        }

        currentListener = listener

        println("[AsrManager] 🎤 Iniciando reconhecimento com timeout de ${timeoutSeconds}s")

        try {
            // Para reconhecimento anterior se existir
            stopListening()

            // Cria recognizer
            val recognizer = Recognizer(model, 16000.0f)

            // Cria SpeechService com listener
            speechService = SpeechService(recognizer, 16000.0f)

            speechService?.startListening(object : RecognitionListener {
                override fun onPartialResult(hypothesis: String?) {
                    hypothesis?.let { json ->
                        try {
                            val partial = JSONObject(json).optString("partial", "")
                            if (partial.isNotEmpty()) {
                                println("[AsrManager] 📝 Parcial: '$partial'")
                                currentListener?.onPartialResult(partial)
                            }
                        } catch (e: Exception) {
                            println("[AsrManager] Erro ao processar parcial: ${e.message}")
                        }
                    }
                }

                override fun onResult(hypothesis: String?) {
                    hypothesis?.let { json ->
                        try {
                            val text = JSONObject(json).optString("text", "")
                            if (text.isNotEmpty()) {
                                println("[AsrManager] ✅ Resultado final: '$text'")

                                // Cancela timeout
                                timeoutJob?.cancel()

                                // Para reconhecimento
                                stopListening()

                                // Notifica listener
                                currentListener?.onResult(text)
                                currentListener = null
                            }
                        } catch (e: Exception) {
                            println("[AsrManager] Erro ao processar resultado: ${e.message}")
                            handleError("Erro ao processar áudio: ${e.message}")
                        }
                    }
                }

                override fun onFinalResult(hypothesis: String?) {
                    // Mesmo tratamento que onResult
                    onResult(hypothesis)
                }

                override fun onError(exception: Exception?) {
                    println("[AsrManager] ❌ Erro no SpeechService: ${exception?.message}")
                    handleError(exception?.message ?: "Erro desconhecido no ASR")
                }

                override fun onTimeout() {
                    println("[AsrManager] ⏱️ Timeout do Vosk")
                    handleTimeout()
                }
            })

            // Agenda timeout customizado
            timeoutJob = CoroutineScope(Dispatchers.Main).launch {
                delay(timeoutSeconds * 1000L)
                println("[AsrManager] ⏱️ Timeout de ${timeoutSeconds}s atingido")
                handleTimeout()
            }

        } catch (e: Exception) {
            println("[AsrManager] ❌ Erro ao iniciar reconhecimento: ${e.message}")
            handleError("Erro ao iniciar reconhecimento: ${e.message}")
        }
    }

    /**
     * Para reconhecimento e cancela timeout.
     */
    fun stopListening() {
        println("[AsrManager] 🛑 Parando reconhecimento")

        timeoutJob?.cancel()
        timeoutJob = null

        speechService?.stop()
        speechService?.shutdown()
        speechService = null
    }

    /**
     * Trata erro e notifica listener.
     */
    private fun handleError(message: String) {
        timeoutJob?.cancel()
        stopListening()
        currentListener?.onError(message)
        currentListener = null
    }

    /**
     * Trata timeout e notifica listener.
     */
    private fun handleTimeout() {
        if (currentListener != null) {
            timeoutJob?.cancel()
            stopListening()
            currentListener?.onTimeout()
            currentListener = null
        }
    }

    /**
     * Libera recursos do ASR.
     * Chamado quando não vai mais usar.
     */
    fun release() {
        println("[AsrManager] 🗑️ Liberando recursos do ASR")

        stopListening()

        model?.close()
        model = null

        isInitialized = false
        currentListener = null
    }
}

