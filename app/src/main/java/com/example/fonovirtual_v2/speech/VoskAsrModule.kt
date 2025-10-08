package com.example.fonovirtual_v2.speech

import android.content.Context
import android.util.Log
import com.example.pequenospassos.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.io.IOException

/**
 * Módulo de Reconhecimento Automático de Fala (ASR) utilizando a biblioteca Vosk.
 *
 * Este módulo é responsável por carregar o modelo de linguagem Vosk PT-BR, inicializar o reconhecedor,
 * gerenciar o serviço de escuta, processar os resultados do reconhecimento e expor o status
 * e os resultados (última palavra e frase completa) através de StateFlows.
 *
 * @param context O contexto da aplicação, necessário para acessar os assets e o serviço de armazenamento.
 * @property modelPath Caminho para o modelo Vosk dentro da pasta assets.
 * @constructor Cria uma instância de VoskAsrModule.
 * @since 0.5.0
 * @validationStatus Pendente
 */
class VoskAsrModule(private val context: Context, private val modelPath: String = "vosk-model-small-pt-0.3") :
    RecognitionListener {

    private var model: Model? = null
    private var speechService: SpeechService? = null
    private var recognizer: Recognizer? = null // Adicionado para uso futuro, se necessário para controle mais fino

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _initializationStatus = MutableStateFlow<AsrInitializationStatus>(AsrInitializationStatus.Idle)
    val initializationStatus: StateFlow<AsrInitializationStatus> = _initializationStatus.asStateFlow()

    private val _recognitionStatus = MutableStateFlow<AsrRecognitionStatus>(AsrRecognitionStatus.Idle)
    val recognitionStatus: StateFlow<AsrRecognitionStatus> = _recognitionStatus.asStateFlow()

    private val _lastPartialResult = MutableStateFlow("")
    val lastPartialResult: StateFlow<String> = _lastPartialResult.asStateFlow()

    private val _finalResult = MutableStateFlow("")
    val finalResult: StateFlow<String> = _finalResult.asStateFlow()

    companion object {
        private const val TAG = "VoskAsrModule"
        private const val SAMPLE_RATE = 16000.0f
    }

    init {
        _initializationStatus.value = AsrInitializationStatus.Initializing

        // A API do StorageService é assíncrona e usa callbacks.
        // O callback de sucesso fornece diretamente um objeto Model já carregado.
        StorageService.unpack(
            context,
            modelPath,
            "model", // Nome do diretório de destino no armazenamento interno
            { loadedModel -> // loadedModel é do tipo org.vosk.Model
                try {
                    this@VoskAsrModule.model = loadedModel // Atribuição direta do modelo carregado
                    _initializationStatus.value = AsrInitializationStatus.Initialized
                    Log.i(TAG, "Modelo Vosk carregado e inicializado com sucesso.")
                } catch (e: Exception) { // Captura genérica para qualquer erro pós-carregamento do Model
                    Log.e(TAG, "Falha inesperada ao lidar com o modelo Vosk carregado: ${e.message}", e)
                    val mensagemErroInesperado = e.localizedMessage ?: "Ocorreu um erro inesperado com o modelo."
                    _initializationStatus.value = AsrInitializationStatus.Error(
                        context.getString(R.string.asr_model_load_error, mensagemErroInesperado)
                    )
                }
            },
            { exception -> // Callback de erro para falha na descompactação/carregamento inicial
                Log.e(TAG, "Erro ao descompactar ou carregar o modelo Vosk: ${exception.message}", exception)
                val mensagemErro = exception.localizedMessage ?: "Erro inesperado durante o processo de descompactação/carregamento."
                _initializationStatus.value = AsrInitializationStatus.Error(
                    context.getString(R.string.asr_model_load_error, mensagemErro)
                )
            }
        )
    }

    /**
     * Inicia o reconhecimento de fala.
     * Requer que o modelo Vosk já esteja inicializado com sucesso.
     * A permissão de RECORD_AUDIO deve ser concedida antes de chamar este método.
     */
    fun startListening() {
        if (model == null || _initializationStatus.value !is AsrInitializationStatus.Initialized) {
            Log.w(TAG, "Modelo Vosk não inicializado. Não é possível iniciar a escuta.")
            _recognitionStatus.value = AsrRecognitionStatus.Error("Modelo não inicializado")
            return
        }

        // Verificar se já existe uma sessão ativa
        if (speechService != null) {
            Log.d(TAG, "Encerrando sessão anterior de reconhecimento")
            stopListening() // Garante limpeza adequada da sessão anterior
        }

        _recognitionStatus.value = AsrRecognitionStatus.Starting
        _lastPartialResult.value = ""
        _finalResult.value = ""

        try {
            recognizer = Recognizer(model, SAMPLE_RATE)
            speechService = SpeechService(recognizer, SAMPLE_RATE)
            speechService?.startListening(this)
            _recognitionStatus.value = AsrRecognitionStatus.Listening
            Log.i(TAG, "Serviço de escuta Vosk iniciado.")
        } catch (e: IOException) {
            Log.e(TAG, "Erro ao acessar o microfone: ${e.message}", e)
            _recognitionStatus.value = AsrRecognitionStatus.Error("Falha ao iniciar gravação. O microfone pode estar em uso por outro aplicativo.")
            cleanup()
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao iniciar o SpeechService Vosk: ${e.message}", e)
            _recognitionStatus.value = AsrRecognitionStatus.Error(
                context.getString(R.string.asr_start_listening_error, e.localizedMessage ?: "Erro desconhecido")
            )
            cleanup()
        }
    }

    /**
     * Para o reconhecimento de fala e libera o SpeechService.
     */
    fun stopListening() {
        try {
            speechService?.stop()
            cleanup()
            _recognitionStatus.value = AsrRecognitionStatus.Idle
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao parar o serviço de escuta: ${e.message}", e)
            _recognitionStatus.value = AsrRecognitionStatus.Error("Falha ao parar o reconhecimento")
        }
    }

    /**
     * Libera recursos do reconhecedor e serviço de fala
     */
    private fun cleanup() {
        try {
            speechService?.shutdown()
            recognizer?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Erro durante cleanup: ${e.message}", e)
        } finally {
            speechService = null
            recognizer = null
        }
    }

    // Implementação dos métodos da interface RecognitionListener

    /**
     * Chamado quando um resultado parcial do reconhecimento é disponibilizado.
     * Atualiza o _lastPartialResult com o texto parcial reconhecido.
     * @param hypothesis Hipótese do reconhecimento em formato JSON string.
     */
    override fun onPartialResult(hypothesis: String?) {
        hypothesis?.let {
            try {
                val jsonObject = JSONObject(it)
                val partialText = jsonObject.optString("partial", "")
                _lastPartialResult.value = partialText
                // Log.d(TAG, "Resultado Parcial: $partialText")
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao parsear resultado parcial JSON: $it", e)
            }
        }
    }

    /**
     * Chamado quando um resultado final do reconhecimento é disponibilizado.
     * Atualiza o _finalResult com o texto final reconhecido.
     * @param hypothesis Hipótese do reconhecimento em formato JSON string.
     */
    override fun onResult(hypothesis: String?) {
        hypothesis?.let {
            try {
                val jsonObject = JSONObject(it)
                val finalText = jsonObject.optString("text", "")
                if (finalText.isNotBlank()) {
                    _finalResult.value = finalText
                    Log.i(TAG, "Resultado Final: $finalText")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao parsear resultado final JSON: $it", e)
            }
        }
        // Após um resultado final, consideramos que a escuta "ativa" para aquela frase terminou,
        // mas o serviço pode continuar escutando para a próxima se não for explicitamente parado.
        // Para o comportamento de "um clique, uma escuta", o stopListening() deve ser chamado no ViewModel/Screen.
    }

    /**
     * Chamado quando um resultado final é obtido, mas é considerado de baixa confiança ou silêncio.
     * @param hypothesis Hipótese do reconhecimento em formato JSON string.
     */
    override fun onFinalResult(hypothesis: String?) {
        // Similar ao onResult, mas podemos querer tratar diferente (ex: se for só silêncio)
        onResult(hypothesis) // Por enquanto, trata da mesma forma
        // Aqui, tipicamente, o speechService pode parar de escutar automaticamente dependendo da configuração
        // ou se detectou um longo período de silêncio.
        // Para o nosso caso de teste, o stopListening() será chamado explicitamente.
    }

    /**
     * Chamado quando ocorre um erro durante o reconhecimento.
     * @param e Exceção que descreve o erro.
     */
    override fun onError(e: Exception?) {
        e?.let {
            Log.e(TAG, "Erro durante reconhecimento: ${it.message}", it)
            val errorMessage = when {
                it is IOException -> "Erro ao acessar microfone. Verifique se não está sendo usado por outro app."
                it.message?.contains("microphone", ignoreCase = true) == true -> "Erro ao acessar microfone. Verifique permissões e se não está em uso."
                else -> "Erro durante reconhecimento: ${it.localizedMessage ?: "Erro desconhecido"}"
            }
            _recognitionStatus.value = AsrRecognitionStatus.Error(errorMessage)
        }
        cleanup()
    }

    /**
     * Chamado quando o serviço de escuta excede o tempo limite.
     */
    override fun onTimeout() {
        Log.w(TAG, "Timeout no listener de reconhecimento Vosk.")
        _recognitionStatus.value = AsrRecognitionStatus.Error(context.getString(R.string.asr_timeout_error))
        // Pode ser útil chamar stopListening() aqui para resetar.
        // speechService?.stop() // O SpeechService geralmente para sozinho no timeout.
    }

    /**
     * Libera os recursos do módulo, incluindo o modelo Vosk e o Job da coroutine.
     * Deve ser chamado quando o módulo não for mais necessário (ex: no onCleared de um ViewModel).
     */
    fun release() {
        Log.i(TAG, "Liberando recursos do VoskAsrModule.")
        stopListening() // Garante que o speechService seja liberado
        viewModelJob.cancel()
        // A biblioteca Vosk não parece ter um método explícito para descarregar/liberar o `Model` da memória
        // além de permitir que seja coletado pelo GC quando não houver mais referências.
        model = null
        _initializationStatus.value = AsrInitializationStatus.Idle
    }
}

/**
 * Representa os possíveis estados de inicialização do [VoskAsrModule].
 */
sealed class AsrInitializationStatus {
    object Idle : AsrInitializationStatus() // Ocioso, antes de iniciar a inicialização.
    object Initializing : AsrInitializationStatus() // Modelo Vosk está sendo carregado/descompactado.
    object Initialized : AsrInitializationStatus() // Modelo Vosk carregado com sucesso.
    data class Error(val message: String) : AsrInitializationStatus() // Erro ao carregar o modelo.
}

/**
 * Representa os possíveis estados do processo de reconhecimento de fala do [VoskAsrModule].
 */
sealed class AsrRecognitionStatus {
    object Idle : AsrRecognitionStatus() // Ocioso, não está escutando.
    object Starting: AsrRecognitionStatus() // Iniciando o processo de escuta.
    object Listening : AsrRecognitionStatus() // Ativamente escutando e processando áudio.
    data class Error(val message: String) : AsrRecognitionStatus() // Erro durante o reconhecimento.
}
