# MVP09 - Acessibilidade e Inclusão

**Versão do Documento:** 1.0  
**Data:** 23/10/2025  
**Status:** 📋 Planejado (Base implementada)  
**Prioridade:** 🔴 ALTA

---

## 1. VISÃO GERAL

### 1.1. Objetivo

Tornar o aplicativo completamente acessível para crianças com diferentes 
necessidades, implementando recursos de Text-to-Speech (TTS), 
reconhecimento de voz (ASR), alto contraste e navegação por comandos.

### 1.2. Situação Atual

**✅ Implementado:**
- TtsManager básico com funcionalidades de leitura
- AsrManager básico com reconhecimento de voz
- Telas de teste (TtsTestScreen, AsrTestScreen)
- Permissões de áudio configuradas

**⚠️ Pendente:**
- Integração completa de TTS em todas as telas
- Integração completa de ASR para comandos de navegação
- Configurações de acessibilidade
- Modo de alto contraste
- Tamanhos de fonte ajustáveis
- Feedback tátil (vibração)
- Legendas e descrições completas
- Testes com usuários reais

### 1.3. Público-Alvo Específico

- 👁️ Crianças com deficiência visual
- 👂 Crianças com deficiência auditiva
- ♿ Crianças com dificuldades motoras
- 🧩 Crianças com TEA (Transtorno do Espectro Autista)
- 📚 Crianças em processo de alfabetização
- 🌍 Crianças com dificuldades de leitura

---

## 2. REQUISITOS FUNCIONAIS

### RF01 - Text-to-Speech (TTS) Completo

**Descrição:** Leitura automática de textos em todas as telas

**Critérios de Aceitação:**
- [ ] TTS lê automaticamente título de tarefas
- [ ] TTS lê descrição de cada step
- [ ] TTS lê feedback de conclusão
- [ ] TTS lê botões ao serem focados
- [ ] TTS pode ser pausado/retomado
- [ ] Velocidade de fala é configurável
- [ ] Tom de voz é configurável
- [ ] Pode ser ativado/desativado globalmente

**Configurações:**
- Velocidade: 0.5x a 2.0x
- Tom: -2 a +2
- Idioma: PT-BR (padrão), outros opcionais
- Voz: Masculina/Feminina (se disponível)

### RF02 - Automatic Speech Recognition (ASR)

**Descrição:** Reconhecimento de comandos de voz

**Critérios de Aceitação:**
- [ ] ASR reconhece comandos básicos de navegação
- [ ] ASR pode iniciar/pausar tarefas
- [ ] ASR pode marcar steps como concluídos
- [ ] ASR pode criar tarefas (futuro)
- [ ] Feedback visual durante reconhecimento
- [ ] Tratamento de erros de reconhecimento
- [ ] Funciona em ambiente com ruído moderado

**Comandos Suportados:**
```
"Voltar"
"Próximo"
"Começar"
"Pausar"
"Concluído"
"Repetir"
"Ajuda"
"Ir para início"
"Ler novamente"
```

### RF03 - Modo de Alto Contraste

**Descrição:** Tema com contraste elevado para melhor legibilidade

**Critérios de Aceitação:**
- [ ] Paleta de cores com contraste WCAG AAA
- [ ] Bordas mais visíveis em elementos interativos
- [ ] Ícones com maior destaque
- [ ] Fundo escuro com texto claro (modo escuro)
- [ ] Fundo claro com texto escuro (modo claro)
- [ ] Alternância rápida via configurações

**Proporções de Contraste:**
- Texto normal: mínimo 7:1
- Texto grande: mínimo 4.5:1
- Elementos interativos: mínimo 3:1

### RF04 - Tamanhos de Fonte Ajustáveis

**Descrição:** Permitir ajuste do tamanho de texto

**Critérios de Aceitação:**
- [ ] 5 níveis de tamanho: XS, S, M, L, XL
- [ ] Todos os textos respondem ao ajuste
- [ ] Layout se adapta sem quebrar
- [ ] Persistência da preferência
- [ ] Preview em tempo real nas configurações

**Multiplicadores:**
```kotlin
enum class FontSize(val multiplier: Float) {
    EXTRA_SMALL(0.85f),
    SMALL(0.92f),
    MEDIUM(1.0f),
    LARGE(1.15f),
    EXTRA_LARGE(1.3f)
}
```

### RF05 - Feedback Tátil (Haptic)

**Descrição:** Vibrações para feedback de ações

**Critérios de Aceitação:**
- [ ] Vibração ao completar step
- [ ] Vibração ao completar tarefa
- [ ] Vibração em erro/alerta
- [ ] Vibração ao desbloquear conquista
- [ ] Padrões diferentes para cada tipo
- [ ] Pode ser desativado

**Padrões de Vibração:**
```kotlin
enum class HapticPattern(val duration: Long) {
    SUCCESS(100),
    ERROR(300),
    ACHIEVEMENT(listOf(100, 50, 100)),
    STEP_COMPLETE(50)
}
```

### RF06 - Descrições de Acessibilidade

**Descrição:** Content descriptions completas para todos os elementos

**Critérios de Aceitação:**
- [ ] Todos os ícones têm contentDescription
- [ ] Imagens decorativas marcadas como null
- [ ] Botões têm descrições claras
- [ ] Campos de formulário têm labels
- [ ] Estados são anunciados (selecionado, expandido, etc.)

### RF07 - Navegação por Teclado

**Descrição:** Suporte completo para navegação via teclado externo

**Critérios de Aceitação:**
- [ ] Tab navega entre elementos
- [ ] Enter ativa botões
- [ ] Setas navegam em listas
- [ ] Esc volta/cancela
- [ ] Focus visível claramente
- [ ] Ordem lógica de foco

---

## 3. MODELO DE DADOS

### 3.1. Atualização em AppSettings

```kotlin
@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey val id: Long = 1,
    
    // ...campos existentes...
    
    // Configurações de Acessibilidade
    val ttsEnabled: Boolean = true,
    val ttsSpeechRate: Float = 1.0f,
    val ttsPitch: Float = 1.0f,
    val ttsAutoRead: Boolean = true,
    
    val asrEnabled: Boolean = false,
    val asrSensitivity: Float = 0.7f,
    
    val highContrastEnabled: Boolean = false,
    val fontSize: String = "MEDIUM", // FontSize enum name
    
    val hapticFeedbackEnabled: Boolean = true,
    val soundEffectsEnabled: Boolean = true,
    
    val autoPlayTimer: Boolean = true,
    val readStepsAloud: Boolean = true
)
```

### 3.2. AccessibilitySettings (Data Class)

```kotlin
data class AccessibilitySettings(
    val ttsEnabled: Boolean = true,
    val ttsSpeechRate: Float = 1.0f,
    val ttsPitch: Float = 1.0f,
    val ttsAutoRead: Boolean = true,
    
    val asrEnabled: Boolean = false,
    val asrSensitivity: Float = 0.7f,
    
    val highContrast: Boolean = false,
    val fontSize: FontSize = FontSize.MEDIUM,
    
    val hapticFeedback: Boolean = true,
    val soundEffects: Boolean = true,
    
    val autoPlayTimer: Boolean = true,
    val readStepsAloud: Boolean = true
) {
    companion object {
        fun fromAppSettings(settings: AppSettings): AccessibilitySettings {
            return AccessibilitySettings(
                ttsEnabled = settings.ttsEnabled,
                ttsSpeechRate = settings.ttsSpeechRate,
                ttsPitch = settings.ttsPitch,
                ttsAutoRead = settings.ttsAutoRead,
                asrEnabled = settings.asrEnabled,
                asrSensitivity = settings.asrSensitivity,
                highContrast = settings.highContrastEnabled,
                fontSize = FontSize.valueOf(settings.fontSize),
                hapticFeedback = settings.hapticFeedbackEnabled,
                soundEffects = settings.soundEffectsEnabled,
                autoPlayTimer = settings.autoPlayTimer,
                readStepsAloud = settings.readStepsAloud
            )
        }
    }
}
```

---

## 4. MANAGERS ATUALIZADOS

### 4.1. TtsManager Completo

```kotlin
class TtsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var settings: AccessibilitySettings? = null
    
    fun initialize(onInit: (Boolean) -> Unit) {
        tts = TextToSpeech(context) { status ->
            isInitialized = status == TextToSpeech.SUCCESS
            onInit(isInitialized)
            
            if (isInitialized) {
                tts?.language = Locale("pt", "BR")
                applySettings()
            }
        }
    }
    
    fun updateSettings(settings: AccessibilitySettings) {
        this.settings = settings
        applySettings()
    }
    
    private fun applySettings() {
        settings?.let {
            tts?.setSpeechRate(it.ttsSpeechRate)
            tts?.setPitch(it.ttsPitch)
        }
    }
    
    fun speak(
        text: String,
        priority: Int = TextToSpeech.QUEUE_ADD,
        onComplete: (() -> Unit)? = null
    ) {
        if (!isInitialized || settings?.ttsEnabled == false) return
        
        val utteranceId = UUID.randomUUID().toString()
        
        onComplete?.let {
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) { onComplete() }
                override fun onError(utteranceId: String?) {}
            })
        }
        
        tts?.speak(text, priority, null, utteranceId)
    }
    
    fun stop() {
        tts?.stop()
    }
    
    fun pause() {
        // TextToSpeech não tem pause nativo, precisamos parar
        stop()
    }
    
    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        isInitialized = false
    }
    
    fun isSpeaking(): Boolean = tts?.isSpeaking ?: false
}
```

### 4.2. AsrManager Completo

```kotlin
class AsrManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false
    private var settings: AccessibilitySettings? = null
    
    fun initialize() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        }
    }
    
    fun updateSettings(settings: AccessibilitySettings) {
        this.settings = settings
    }
    
    fun startListening(
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (settings?.asrEnabled == false) {
            onError("ASR desabilitado nas configurações")
            return
        }
        
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                isListening = true
            }
            
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(
                    SpeechRecognizer.RESULTS_RECOGNITION
                )
                matches?.firstOrNull()?.let { result ->
                    onResult(result)
                    processCommand(result)
                }
                isListening = false
            }
            
            override fun onError(error: Int) {
                val errorMessage = getErrorMessage(error)
                onError(errorMessage)
                isListening = false
            }
            
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
        
        speechRecognizer?.startListening(intent)
    }
    
    fun stopListening() {
        speechRecognizer?.stopListening()
        isListening = false
    }
    
    private fun processCommand(command: String): VoiceCommand? {
        return when (command.lowercase().trim()) {
            "voltar", "anterior" -> VoiceCommand.BACK
            "próximo", "proximo", "avançar" -> VoiceCommand.NEXT
            "começar", "começar", "iniciar" -> VoiceCommand.START
            "pausar", "parar" -> VoiceCommand.PAUSE
            "concluído", "concluido", "terminei" -> VoiceCommand.COMPLETE
            "repetir", "ler novamente" -> VoiceCommand.REPEAT
            "ajuda" -> VoiceCommand.HELP
            "início", "inicio", "home" -> VoiceCommand.HOME
            else -> null
        }
    }
    
    private fun getErrorMessage(error: Int): String {
        return when (error) {
            SpeechRecognizer.ERROR_AUDIO -> "Erro de áudio"
            SpeechRecognizer.ERROR_CLIENT -> "Erro do cliente"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Sem permissão"
            SpeechRecognizer.ERROR_NETWORK -> "Erro de rede"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Timeout de rede"
            SpeechRecognizer.ERROR_NO_MATCH -> "Nenhuma correspondência"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Reconhecedor ocupado"
            SpeechRecognizer.ERROR_SERVER -> "Erro do servidor"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Timeout de fala"
            else -> "Erro desconhecido"
        }
    }
    
    fun destroy() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
    
    fun isListening(): Boolean = isListening
}

enum class VoiceCommand {
    BACK, NEXT, START, PAUSE, COMPLETE, REPEAT, HELP, HOME
}
```

### 4.3. HapticManager

```kotlin
class HapticManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) 
            as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    
    private var settings: AccessibilitySettings? = null
    
    fun updateSettings(settings: AccessibilitySettings) {
        this.settings = settings
    }
    
    fun vibrate(pattern: HapticPattern) {
        if (settings?.hapticFeedback == false) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = when (pattern) {
                HapticPattern.SUCCESS -> 
                    VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                HapticPattern.ERROR -> 
                    VibrationEffect.createWaveform(longArrayOf(0, 100, 50, 100), -1)
                HapticPattern.ACHIEVEMENT -> 
                    VibrationEffect.createWaveform(longArrayOf(0, 100, 50, 100, 50, 100), -1)
                HapticPattern.STEP_COMPLETE -> 
                    VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            }
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            when (pattern) {
                HapticPattern.SUCCESS -> vibrator.vibrate(100)
                HapticPattern.ERROR -> vibrator.vibrate(longArrayOf(0, 100, 50, 100), -1)
                HapticPattern.ACHIEVEMENT -> 
                    vibrator.vibrate(longArrayOf(0, 100, 50, 100, 50, 100), -1)
                HapticPattern.STEP_COMPLETE -> vibrator.vibrate(50)
            }
        }
    }
}

enum class HapticPattern {
    SUCCESS, ERROR, ACHIEVEMENT, STEP_COMPLETE
}
```

---

## 5. TELAS E COMPONENTES

### 5.1. AccessibilitySettingsScreen

```kotlin
@Composable
fun AccessibilitySettingsScreen(
    viewModel: AccessibilitySettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações de Acessibilidade") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Seção TTS
            item {
                Text(
                    text = "Leitura de Tela (TTS)",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                SwitchPreference(
                    title = "Ativar leitura de tela",
                    checked = settings.ttsEnabled,
                    onCheckedChange = { viewModel.updateTtsEnabled(it) }
                )
            }
            
            item {
                SliderPreference(
                    title = "Velocidade da fala",
                    value = settings.ttsSpeechRate,
                    valueRange = 0.5f..2.0f,
                    onValueChange = { viewModel.updateSpeechRate(it) },
                    enabled = settings.ttsEnabled
                )
            }
            
            // Seção ASR
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Comandos de Voz (ASR)",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                SwitchPreference(
                    title = "Ativar comandos de voz",
                    checked = settings.asrEnabled,
                    onCheckedChange = { viewModel.updateAsrEnabled(it) }
                )
            }
            
            // Seção Visual
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Visual",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                SwitchPreference(
                    title = "Alto contraste",
                    checked = settings.highContrast,
                    onCheckedChange = { viewModel.updateHighContrast(it) }
                )
            }
            
            item {
                FontSizePreference(
                    currentSize = settings.fontSize,
                    onSizeChange = { viewModel.updateFontSize(it) }
                )
            }
            
            // Seção Tátil
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Feedback Tátil",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            item {
                SwitchPreference(
                    title = "Vibração",
                    checked = settings.hapticFeedback,
                    onCheckedChange = { viewModel.updateHapticFeedback(it) }
                )
            }
        }
    }
}
```

---

## 6. CRONOGRAMA DE IMPLEMENTAÇÃO

### Fase 1 - TTS Completo (2 dias)

**Tarefas:**
- [ ] Expandir TtsManager com todas funcionalidades
- [ ] Integrar TTS em TaskExecutionScreen
- [ ] Integrar TTS em TaskFormScreen
- [ ] Integrar TTS em TaskListScreen
- [ ] Integrar TTS em TaskCompletionScreen
- [ ] Configurações de TTS
- [ ] Testes

### Fase 2 - ASR Completo (2 dias)

**Tarefas:**
- [ ] Expandir AsrManager com processamento de comandos
- [ ] Implementar VoiceCommandHandler
- [ ] Integrar ASR em navegação
- [ ] Implementar comandos de controle
- [ ] Feedback visual durante reconhecimento
- [ ] Testes de comandos

### Fase 3 - Visual e Tátil (2 dias)

**Tarefas:**
- [ ] Criar tema de alto contraste
- [ ] Implementar sistema de tamanho de fonte
- [ ] Criar HapticManager
- [ ] Integrar vibração em eventos
- [ ] Testes de acessibilidade visual

### Fase 4 - Configurações (1 dia)

**Tarefas:**
- [ ] Criar AccessibilitySettingsScreen
- [ ] Criar ViewModel correspondente
- [ ] Persistir configurações
- [ ] Adicionar navegação

### Fase 5 - Testes e Validação (1 dia)

**Tarefas:**
- [ ] Testes com TalkBack
- [ ] Testes com Switch Access
- [ ] Validação WCAG
- [ ] Testes com usuários
- [ ] Ajustes finais

**Total Estimado: 8 dias**

---

## 7. MÉTRICAS DE SUCESSO

- [ ] Passa em todos os testes do Accessibility Scanner
- [ ] Compatível com TalkBack
- [ ] Compatível com Switch Access
- [ ] Contraste WCAG AAA em modo alto contraste
- [ ] Taxa de reconhecimento ASR > 85%
- [ ] TTS funcional em 100% das telas
- [ ] Feedback positivo de usuários com necessidades especiais

---

**Status:** Documento aprovado, base já implementada  
**Próxima Ação:** Iniciar após conclusão do MVP08

