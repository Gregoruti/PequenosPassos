package com.example.fonovirtual_v2.ui.exercises.recognition

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fonovirtual_v2.data.exercises.recognition.WordsRepository
import com.example.fonovirtual_v2.speech.AsrInitializationStatus
import com.example.fonovirtual_v2.speech.AsrRecognitionStatus
import com.example.fonovirtual_v2.speech.TextToSpeechModule
import com.example.fonovirtual_v2.speech.VoskAsrModule
import com.example.fonovirtual_v2.ui.exercises.recognition.model.WordExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para o exercício de reconhecimento simples de palavras.
 * Gerencia a lógica de negócios, integrando ASR e TTS para o exercício.
 * Suporta exercícios por sílabas e por grupos consonantais (Xr).
 *
 * @param application Contexto da aplicação necessário para ASR e TTS
 * @since 1.0.3
 * @updated 1.0.5 (30/09/2025) - Adicionado suporte para exercícios de sons consonantais
 * @validationStatus Em Desenvolvimento
 */
class SimpleRecognitionExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val voskAsrModule = VoskAsrModule(application.applicationContext)
    private val ttsModule = TextToSpeechModule(application.applicationContext)

    // Estado atual do exercício
    private val _currentWord = MutableStateFlow<WordExercise?>(null)
    val currentWord: StateFlow<WordExercise?> = _currentWord.asStateFlow()

    // Lista de palavras do nível atual
    private var currentWordsList: List<WordExercise> = emptyList()
    private var currentWordIndex = 0

    // Estado da UI - tipos de exercício
    var selectedSyllableCount by mutableStateOf<Int?>(null)
        private set

    var selectedConsonantGroup by mutableStateOf<String?>(null)
        private set

    var selectedConsonantGroupXL by mutableStateOf<String?>(null)
        private set

    var selectedDigraph by mutableStateOf<String?>(null)
        private set

    var selectedTonicAccent by mutableStateOf<String?>(null)
        private set

    // Estados do ASR e TTS
    val asrStatus = voskAsrModule.recognitionStatus
    val asrResult = voskAsrModule.lastPartialResult
    val initializationStatus = voskAsrModule.initializationStatus

    // Estatísticas do exercício
    private var totalWords = 0
    private var correctWords = 0

    /**
     * Calcula a porcentagem de acerto do exercício atual.
     * @return Porcentagem de 0 a 100
     */
    fun getAccuracyPercentage(): Int {
        return if (totalWords > 0) {
            ((correctWords.toFloat() / totalWords.toFloat()) * 100).toInt()
        } else 0
    }

    /**
     * Retorna o total de palavras do exercício.
     */
    fun getTotalWords(): Int = totalWords

    /**
     * Retorna o número de palavras corretas.
     */
    fun getCorrectWords(): Int = correctWords

    init {
        // Monitora o estado de inicialização do ASR
        viewModelScope.launch {
            initializationStatus.collect { status ->
                if (status is AsrInitializationStatus.Initialized) {
                    startListening() // Inicia escuta quando inicializado
                }
            }
        }
    }

    /**
     * Seleciona o número de sílabas para o exercício e carrega as palavras correspondentes.
     * @param syllableCount Número de sílabas (2 a 5)
     */
    fun selectSyllableCount(syllableCount: Int) {
        selectedSyllableCount = syllableCount
        selectedConsonantGroup = null // Reset dos grupos consonantais
        selectedConsonantGroupXL = null
        selectedDigraph = null // Reset dos dígrafos
        selectedTonicAccent = null // Reset do acento tônico
        currentWordsList = WordsRepository.getWordsBySyllables(syllableCount)
        currentWordIndex = 0
        totalWords = currentWordsList.size
        correctWords = 0

        if (currentWordsList.isNotEmpty()) {
            _currentWord.value = currentWordsList[0]
        }
    }

    /**
     * Seleciona o grupo consonantal Xr para o exercício e carrega as palavras correspondentes.
     * @param consonantGroup Grupo consonantal (BR, CR, FR, GR)
     */
    fun selectConsonantGroup(consonantGroup: String) {
        selectedConsonantGroup = consonantGroup
        selectedSyllableCount = null // Reset da contagem de sílabas
        selectedConsonantGroupXL = null // Reset do grupo XL
        selectedDigraph = null // Reset dos dígrafos
        selectedTonicAccent = null // Reset do acento tônico
        currentWordsList = WordsRepository.getWordsByConsonantGroup(consonantGroup)
        currentWordIndex = 0
        totalWords = currentWordsList.size
        correctWords = 0

        if (currentWordsList.isNotEmpty()) {
            _currentWord.value = currentWordsList[0]
        }
    }

    /**
     * Seleciona o grupo consonantal Xl para o exercício e carrega as palavras correspondentes.
     * @param consonantGroup Grupo consonantal (CL, FL, PL, BL)
     */
    fun selectConsonantGroupXL(consonantGroup: String) {
        selectedConsonantGroupXL = consonantGroup
        selectedSyllableCount = null // Reset da contagem de sílabas
        selectedConsonantGroup = null // Reset do grupo Xr
        selectedDigraph = null // Reset dos dígrafos
        selectedTonicAccent = null // Reset do acento tônico
        currentWordsList = WordsRepository.getWordsByConsonantGroupXL(consonantGroup)
        currentWordIndex = 0
        totalWords = currentWordsList.size
        correctWords = 0

        if (currentWordsList.isNotEmpty()) {
            _currentWord.value = currentWordsList[0]
        }
    }

    /**
     * Seleciona um dígrafo para o exercício e carrega as palavras correspondentes.
     * @param digraph Dígrafo selecionado (ex: "ch", "lh", "nh")
     */
    fun selectDigraph(digraph: String) {
        selectedDigraph = digraph
        selectedSyllableCount = null // Reset da contagem de sílabas
        selectedConsonantGroup = null // Reset do grupo Xr
        selectedConsonantGroupXL = null // Reset do grupo XL
        selectedTonicAccent = null // Reset do acento tônico
        currentWordsList = WordsRepository.getWordsByDigraph(digraph)
        currentWordIndex = 0
        totalWords = currentWordsList.size
        correctWords = 0

        if (currentWordsList.isNotEmpty()) {
            _currentWord.value = currentWordsList[0]
        }
    }

    /**
     * Seleciona um acento tônico para o exercício e carrega as palavras correspondentes.
     * @param tonicAccent Acento tônico selecionado (ex: "á", "é", "í", "ó", "ú")
     */
    fun selectTonicAccent(tonicAccent: String) {
        selectedTonicAccent = tonicAccent
        selectedSyllableCount = null // Reset da contagem de sílabas
        selectedConsonantGroup = null // Reset do grupo Xr
        selectedConsonantGroupXL = null // Reset do grupo XL
        selectedDigraph = null // Reset dos dígrafos
        currentWordsList = WordsRepository.getWordsByTonicAccent(tonicAccent)
        currentWordIndex = 0
        totalWords = currentWordsList.size
        correctWords = 0

        if (currentWordsList.isNotEmpty()) {
            _currentWord.value = currentWordsList[0]
        }
    }

    /**
     * Carrega a próxima palavra do exercício.
     * @return true se há uma próxima palavra, false se o exercício terminou
     */
    fun loadNextWord(): Boolean {
        currentWordIndex++
        if (currentWordIndex < currentWordsList.size) {
            _currentWord.value = currentWordsList[currentWordIndex]
            // Reinicia a escuta para a nova palavra
            if (asrStatus.value !is AsrRecognitionStatus.Listening) {
                startListening()
            }
            return true
        }
        return false
    }

    /**
     * Reproduz a palavra atual usando TTS.
     */
    fun speakCurrentWord() {
        _currentWord.value?.let { word ->
            ttsModule.speak(word.word)
        }
    }

    /**
     * Inicia a gravação para reconhecimento de voz.
     */
    fun startListening() {
        if (asrStatus.value !is AsrRecognitionStatus.Listening &&
            initializationStatus.value is AsrInitializationStatus.Initialized) {
            voskAsrModule.startListening()
        }
    }

    /**
     * Para a gravação.
     */
    fun stopListening() {
        if (asrStatus.value is AsrRecognitionStatus.Listening) {
            voskAsrModule.stopListening()
        }
    }

    /**
     * Reseta o exercício atual.
     */
    fun resetExercise() {
        currentWordIndex = 0
        correctWords = 0 // Reseta o contador de acertos
        currentWordsList = currentWordsList.map { it.copy(attempts = 0, lastAttempt = null, isCorrect = false) }
        loadNextWord()
        startListening()
    }

    /**
     * Incrementa o contador de palavras corretas.
     */
    fun incrementCorrectWords() {
        correctWords++
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
        voskAsrModule.release()
        ttsModule.shutdown()
    }
}
