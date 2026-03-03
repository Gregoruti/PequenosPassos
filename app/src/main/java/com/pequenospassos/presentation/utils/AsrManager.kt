package com.pequenospassos.presentation.utils

import android.content.Context
import android.content.res.AssetManager
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
 * Correlações: VoiceCommandParser.kt, TaskExecutionViewModel.kt, AsrTestScreen.kt
 *
 * Histórico de alterações:
 * - 2025-11-01 (Claude Sonnet 4.5): MVP-14 Fase 4 - Criação inicial
 *   - Interface VoiceRecognitionListener com callbacks
 *   - Método startListeningWithTimeout() com timeout configurável
 *   - Gerenciamento de timeout com Coroutines
 *   - Cleanup automático ao parar reconhecimento
 * - 2026-03-03 (GitHub Copilot / Claude Sonnet 4.5): Correção crítica
 *   - initialize() agora copia automaticamente o modelo Vosk dos assets
 *   - Eliminada dependência de passar pela tela AsrTestScreen antes de usar
 *   - Corrige falha silenciosa após desinstalar/reinstalar o app
 *   - Adicionado copyAssetFolder() reutilizando lógica do AsrTestScreen
 *
 * Última atualização: 2026-03-03
 * Status: Funcional - MVP-14
 * Build: PENDING
 *
 * Criado: 2025-11-01
 * Autor: Claude Sonnet 4.5 / GitHub Copilot
 *
 * @since MVP-14 Fase 4 (v2.2.0)
 * @updated v2.5.2 (2026-03-03) - Auto-cópia modelo Vosk no initialize()
 * @validationStatus 🔄 Pendente compilação
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
     * Copia recursivamente uma pasta/arquivo dos assets para o filesDir.
     * Mesmo algoritmo usado em AsrTestScreen para garantir compatibilidade.
     *
     * @param srcPath Caminho no assets
     * @param dstPath Destino no sistema de arquivos
     * @return true se copiou com sucesso
     */
    private fun copyAssetFolder(assetManager: AssetManager, srcPath: String, dstPath: File): Boolean {
        return try {
            val assets = assetManager.list(srcPath)
            if (assets == null || assets.isEmpty()) {
                // É um arquivo — copia direto
                assetManager.open(srcPath).use { input ->
                    dstPath.parentFile?.mkdirs()
                    dstPath.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                true
            } else {
                // É uma pasta — cria e copia recursivo
                if (!dstPath.exists()) dstPath.mkdirs()
                var success = true
                for (asset in assets) {
                    val newSrcPath = if (srcPath.isEmpty()) asset else "$srcPath/$asset"
                    val newDstPath = File(dstPath, asset)
                    if (!copyAssetFolder(assetManager, newSrcPath, newDstPath)) {
                        success = false
                    }
                }
                success
            }
        } catch (e: Exception) {
            println("[AsrManager] ❌ Erro ao copiar asset '$srcPath': ${e.message}")
            false
        }
    }

    /**
     * Inicializa o modelo Vosk.
     * Se o modelo não existir no filesDir, copia automaticamente dos assets.
     * Não é mais necessário passar pela tela de teste ASR primeiro.
     *
     * Correção (2026-03-03): modelo era exigido já copiado — agora copia
     * automaticamente na primeira inicialização após instalação/reinstalação.
     *
     * @param onSuccess Callback chamado quando inicialização completa
     * @param onError Callback chamado se houver erro
     */
    fun initialize(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (isInitialized) {
            onSuccess()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val modelPath = File(context.filesDir, "vosk-model-small-pt-0.3")
                val assetManager = context.assets

                // Verifica se precisa copiar (arquivos de marcação uuid e ivector)
                val uuidFile = File(modelPath, "uuid")
                val ivectorDir = File(modelPath, "ivector")

                if (!modelPath.exists() || !uuidFile.exists() || !ivectorDir.exists()) {
                    println("[AsrManager] 📦 Modelo não encontrado — extraindo dos assets...")

                    // Remove diretório parcial se existir
                    if (modelPath.exists()) {
                        modelPath.deleteRecursively()
                    }

                    val copied = copyAssetFolder(assetManager, "vosk-model-small-pt-0.3", modelPath)
                    if (!copied) {
                        withContext(Dispatchers.Main) {
                            onError("Falha ao extrair modelo Vosk dos assets.")
                        }
                        return@launch
                    }
                    println("[AsrManager] ✅ Modelo extraído com sucesso para ${modelPath.absolutePath}")
                }

                // Carrega o modelo
                println("[AsrManager] ⏳ Carregando modelo Vosk...")
                val loadedModel = Model(modelPath.absolutePath)

                withContext(Dispatchers.Main) {
                    model = loadedModel
                    isInitialized = true
                    println("[AsrManager] ✅ Modelo Vosk inicializado com sucesso")
                    onSuccess()
                }

            } catch (e: Exception) {
                println("[AsrManager] ❌ Erro ao inicializar modelo: ${e.message}")
                withContext(Dispatchers.Main) {
                    onError("Erro ao inicializar ASR: ${e.message}")
                }
            }
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

