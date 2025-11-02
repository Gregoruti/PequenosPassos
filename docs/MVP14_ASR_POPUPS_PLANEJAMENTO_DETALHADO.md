<!--
Arquivo: docs/MVP14_ASR_POPUPS_PLANEJAMENTO_DETALHADO.md
Tipo: Planejamento de funcionalidade
Objetivo: Definir passo a passo a implementação de ASR (reconhecimento de voz) em pop-ups de tempo extra
Correlações: TaskExecutionViewModel.kt, HistoryScreen.kt, AppSettings.kt, AsrManager.kt
Criado: 2025-11-01
Autor: Claude Sonnet 4.5 (GitHub Copilot)
Última atualização: 2025-11-01

INSTRUÇÕES PARA CODE ASSISTANTS (AI):
Este documento define DETALHADAMENTE como implementar reconhecimento de voz no pop-up de tempo extra.
SEMPRE ler este documento antes de iniciar a implementação.
Seguir a ordem das fases para desenvolvimento incremental e modular.

Status: PLANEJAMENTO - Aguardando aprovação para implementação
Prioridade: ALTA (reavaliada pelo usuário)
Estimativa: 5-7 dias

Histórico:
- 2025-11-01: Criação do planejamento detalhado (prioridade elevada de BAIXA para ALTA)
-->

# MVP-14: ASR (Reconhecimento de Voz) em Pop-ups

**Versão Base:** 2.1.0  
**Prioridade:** 🔴 ALTA (reavaliada)  
**Estimativa:** 5-7 dias  
**Status:** 📋 PLANEJAMENTO  
**Code Assistant:** Claude Sonnet 4.5 (GitHub Copilot)

---

## 📋 ÍNDICE

1. [Visão Geral](#1-visão-geral)
2. [Requisitos Funcionais](#2-requisitos-funcionais)
3. [Requisitos Técnicos](#3-requisitos-técnicos)
4. [Arquitetura da Solução](#4-arquitetura-da-solução)
5. [Implementação Fase a Fase](#5-implementação-fase-a-fase)
6. [Listas de Comandos de Voz](#6-listas-de-comandos-de-voz)
7. [Fluxogramas](#7-fluxogramas)
8. [Testes e Validação](#8-testes-e-validação)
9. [Riscos e Mitigações](#9-riscos-e-mitigações)
10. [Checklist de Implementação](#10-checklist-de-implementação)

---

## 1. VISÃO GERAL

### 1.1 Objetivo

Implementar reconhecimento de voz (ASR) usando Vosk para permitir que crianças respondam verbalmente à pergunta "Podemos avançar ou quer mais 30 segundos?" durante a execução de tarefas.

### 1.2 Problema Atual

- Criança precisa clicar em botões para responder
- Pode ter dificuldade motora ou estar com as mãos ocupadas
- Interação por voz é mais natural para crianças

### 1.3 Solução Proposta

- Adicionar checkbox "Resposta em Áudio" em Histórico & Ferramentas
- Quando habilitado, ativar ASR Vosk ao exibir pop-up de tempo extra
- Monitorar respostas verbais por 3 segundos
- Reconhecer comandos infantis positivos/negativos
- Executar ação correspondente automaticamente

### 1.4 Benefícios

✅ Maior acessibilidade  
✅ Interação mais natural  
✅ Mãos livres durante execução  
✅ Mais engajamento da criança  
✅ Uso de recurso já existente (Vosk)

---

## 2. REQUISITOS FUNCIONAIS

### 2.1 RF-01: Checkbox de Habilitação

**Descrição:** Adicionar checkbox "Resposta em Áudio" em Histórico & Ferramentas

**Critérios de Aceitação:**
- [ ] Checkbox visível na tela Histórico & Ferramentas
- [ ] Localizada próxima ao checkbox "Perguntar se deseja mais tempo"
- [ ] Estado persiste no banco de dados (AppSettings.enableVoiceResponse)
- [ ] Valor padrão: false (desabilitado)
- [ ] Mudanças refletem imediatamente na execução de tarefas

### 2.2 RF-02: Ativação do ASR no Pop-up

**Descrição:** Ativar reconhecimento de voz quando pop-up é exibido

**Critérios de Aceitação:**
- [ ] ASR inicia automaticamente ao exibir pop-up
- [ ] Somente se checkbox "Resposta em Áudio" estiver habilitado
- [ ] Feedback visual indica que está escutando (ícone de microfone animado)
- [ ] Timeout de 3 segundos
- [ ] Se não reconhecer comando, exibe botões normais

### 2.3 RF-03: Reconhecimento de Comandos

**Descrição:** Reconhecer comandos verbais infantis positivos e negativos

**Critérios de Aceitação:**
- [ ] Reconhece respostas positivas (sim, pode, vamos, etc)
- [ ] Reconhece respostas negativas (não, espera, mais tempo, etc)
- [ ] Não diferencia maiúsculas/minúsculas
- [ ] Tolera pequenas variações de pronúncia
- [ ] Feedback visual ao reconhecer comando

### 2.4 RF-04: Execução de Ação

**Descrição:** Executar ação correspondente ao comando reconhecido

**Critérios de Aceitação:**
- [ ] Comando positivo: avança para próximo step
- [ ] Comando negativo: adiciona 30 segundos ao timer
- [ ] Pop-up fecha automaticamente após reconhecimento
- [ ] Feedback sonoro/visual confirma ação
- [ ] Se timeout, exibe botões para clique manual

### 2.5 RF-05: Fallback para Botões

**Descrição:** Sempre exibir botões como alternativa

**Critérios de Aceitação:**
- [ ] Botões ficam visíveis durante escuta
- [ ] Criança pode clicar a qualquer momento
- [ ] Clique cancela escuta de voz
- [ ] Se ASR falhar, botões continuam funcionando

---

## 3. REQUISITOS TÉCNICOS

### 3.1 RT-01: Vosk Integration

**Descrição:** Usar biblioteca Vosk já existente no projeto

**Detalhes:**
- Arquivo: `app/src/main/java/com/pequenospassos/presentation/utils/AsrManager.kt`
- Modelo: Português BR (já baixado)
- Localização do modelo: `/assets/vosk-model-small-pt-0.3/`

### 3.2 RT-02: Banco de Dados

**Descrição:** Adicionar campo enableVoiceResponse em AppSettings

**Schema:**
```kotlin
@Entity(tableName = "app_settings")
data class AppSettings(
    // ...existing fields...
    val enableVoiceResponse: Boolean = false
)
```

**Migration:** 5→6

### 3.3 RT-03: Permissões

**Descrição:** Garantir permissão de microfone

**AndroidManifest.xml:**
```xml
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
```

**Runtime:** Solicitar em tempo de execução (API 23+)

### 3.4 RT-04: Performance

**Requisitos:**
- Inicialização do ASR: < 1 segundo
- Reconhecimento: < 500ms após comando
- Uso de memória: < 50MB adicional
- Não travar UI durante reconhecimento

---

## 4. ARQUITETURA DA SOLUÇÃO

### 4.1 Componentes Envolvidos

```
┌─────────────────────────────────────────────────────┐
│         TaskExecutionScreen.kt (UI)                  │
│  - Exibe pop-up de tempo extra                      │
│  - Mostra feedback visual de escuta                 │
│  - Exibe botões de fallback                         │
└────────────────┬────────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────────┐
│      TaskExecutionViewModel.kt (Logic)               │
│  - Controla estado do pop-up                        │
│  - Decide se ativa ASR ou não                       │
│  - Processa comandos reconhecidos                   │
│  - Executa ações (avançar/mais tempo)               │
└────────────────┬────────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────────┐
│           AsrManager.kt (ASR Engine)                 │
│  - Inicializa Vosk                                  │
│  - Gerencia ciclo de vida do recognizer            │
│  - Processa áudio do microfone                      │
│  - Retorna texto reconhecido                        │
└────────────────┬────────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────────────────────┐
│       VoiceCommandParser.kt (NEW)                    │
│  - Recebe texto do ASR                              │
│  - Compara com listas de comandos                   │
│  - Retorna: POSITIVE, NEGATIVE, UNKNOWN             │
└─────────────────────────────────────────────────────┘
```

### 4.2 Fluxo de Dados

```
1. Timer do step termina
   ↓
2. ViewModel verifica: askExtraTimeAtStep == true?
   ↓ (sim)
3. ViewModel verifica: enableVoiceResponse == true?
   ↓ (sim)
4. Screen exibe pop-up + inicia ASR
   ↓
5. AsrManager escuta por 3 segundos
   ↓
6. VoiceCommandParser analisa texto reconhecido
   ↓
7. ViewModel executa ação (avançar ou +30s)
   ↓
8. Screen fecha pop-up e atualiza timer
```

---

## 5. IMPLEMENTAÇÃO FASE A FASE

### FASE 1: Preparação do Banco de Dados (1 dia)

#### 5.1.1 Adicionar Campo em AppSettings

**Arquivo:** `domain/model/AppSettings.kt`

```kotlin
@Entity(tableName = "app_settings")
data class AppSettings(
    // ...existing fields...
    val askExtraTimeAtStep: Boolean = true,
    
    // NOVO: Habilita resposta por voz no pop-up de tempo extra
    val enableVoiceResponse: Boolean = false
)
```

#### 5.1.2 Criar Migration 5→6

**Arquivo:** `data/database/AppDatabase.kt`

```kotlin
val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE app_settings ADD COLUMN enableVoiceResponse INTEGER NOT NULL DEFAULT 0"
        )
    }
}
```

#### 5.1.3 Atualizar AppSettingsDao

**Arquivo:** `data/database/dao/AppSettingsDao.kt`

```kotlin
@Dao
interface AppSettingsDao {
    // ...existing methods...
    
    @Query("UPDATE app_settings SET enableVoiceResponse = :enabled WHERE id = 'settings'")
    suspend fun setEnableVoiceResponse(enabled: Boolean)
    
    @Query("SELECT enableVoiceResponse FROM app_settings WHERE id = 'settings'")
    fun getEnableVoiceResponseFlow(): Flow<Boolean>
}
```

#### 5.1.4 Atualizar Repository

**Arquivo:** `domain/repository/AppSettingsRepository.kt`

```kotlin
interface AppSettingsRepository {
    // ...existing methods...
    suspend fun setEnableVoiceResponse(enabled: Boolean)
    fun getEnableVoiceResponse(): Flow<Boolean>
}
```

**Arquivo:** `data/repository/AppSettingsRepositoryImpl.kt`

```kotlin
override suspend fun setEnableVoiceResponse(enabled: Boolean) {
    appSettingsDao.setEnableVoiceResponse(enabled)
}

override fun getEnableVoiceResponse(): Flow<Boolean> {
    return appSettingsDao.getEnableVoiceResponseFlow()
}
```

#### 5.1.5 Checklist Fase 1

- [ ] Campo adicionado em AppSettings.kt
- [ ] Migration 5→6 criada
- [ ] AppSettingsDao atualizado
- [ ] Repository atualizado
- [ ] Versão do banco incrementada (5→6)
- [ ] Build compila sem erros
- [ ] App instala e abre normalmente

---

### FASE 2: Checkbox em Histórico & Ferramentas (1 dia)

#### 5.2.1 Atualizar HistoryViewModel

**Arquivo:** `presentation/screens/history/HistoryViewModel.kt`

```kotlin
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    // ...other dependencies...
) : ViewModel() {

    // ...existing code...
    
    // NOVO: Estado do checkbox de resposta por voz
    val enableVoiceResponse: StateFlow<Boolean> = 
        appSettingsRepository.getEnableVoiceResponse()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )
    
    // NOVO: Atualizar checkbox de resposta por voz
    fun setEnableVoiceResponse(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.setEnableVoiceResponse(enabled)
        }
    }
}
```

#### 5.2.2 Atualizar HistoryScreen

**Arquivo:** `presentation/screens/history/HistoryScreen.kt`

```kotlin
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    // ...other params...
) {
    val askExtraTime by viewModel.askExtraTimeAtStep.collectAsState()
    val enableVoiceResponse by viewModel.enableVoiceResponse.collectAsState() // NOVO
    
    // ...existing code...
    
    // Seção: Ferramentas de Teste
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🔧 Ferramentas de Teste", style = MaterialTheme.typography.titleMedium)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Checkbox existente
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = askExtraTime,
                    onCheckedChange = { viewModel.setAskExtraTimeAtStep(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Perguntar se deseja mais tempo")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // NOVO: Checkbox de resposta por voz
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = enableVoiceResponse,
                    onCheckedChange = { viewModel.setEnableVoiceResponse(it) },
                    enabled = askExtraTime // Só habilita se pop-up estiver ativo
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Resposta em Áudio")
                    if (askExtraTime && enableVoiceResponse) {
                        Text(
                            "O app vai escutar sua resposta por 3 segundos",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
            
            // ...existing buttons (Testar TTS, Testar ASR, Zerar Dia)...
        }
    }
}
```

#### 5.2.3 Checklist Fase 2

- [ ] HistoryViewModel atualizado
- [ ] HistoryScreen atualizado
- [ ] Checkbox visível em Histórico & Ferramentas
- [ ] Estado persiste ao fechar e abrir app
- [ ] Checkbox só habilitado se "Perguntar se deseja mais tempo" estiver ativo
- [ ] Build compila sem erros
- [ ] Teste em dispositivo: checkbox funciona

---

### FASE 3: Criar VoiceCommandParser (1 dia)

#### 5.3.1 Criar Classe VoiceCommandParser

**Arquivo:** `presentation/utils/VoiceCommandParser.kt` (NOVO)

```kotlin
package com.pequenospassos.presentation.utils

/**
 * Parser de comandos de voz para reconhecimento de respostas infantis.
 * 
 * Arquivo: presentation/utils/VoiceCommandParser.kt
 * Tipo: Utility class
 * Objetivo: Analisar texto reconhecido pelo ASR e determinar intenção (positivo/negativo)
 * Correlações: AsrManager.kt, TaskExecutionViewModel.kt
 * 
 * Criado: 2025-11-01
 * Autor: Claude Sonnet 4.5 (GitHub Copilot)
 * Status: MVP-14 - ASR em Pop-ups
 * 
 * @since MVP-14 (v2.2.0)
 */
class VoiceCommandParser {

    /**
     * Resultado do parsing de comando de voz.
     */
    enum class CommandResult {
        POSITIVE,  // Avançar para próximo step
        NEGATIVE,  // Adicionar mais 30 segundos
        UNKNOWN    // Não reconhecido
    }

    companion object {
        /**
         * Lista de comandos POSITIVOS (avançar).
         * Respostas infantis que indicam "sim, pode avançar".
         */
        private val POSITIVE_COMMANDS = setOf(
            // Respostas afirmativas diretas
            "sim", "pode", "vamos", "vai", "continua", "próximo", "avança",
            
            // Variações infantis
            "tá", "ok", "beleza", "isso", "é", "uhum", "aham",
            
            // Frases completas comuns
            "pode ir", "vamos lá", "tá bom", "pode avançar", "próximo passo",
            "já terminei", "terminei", "pronto", "feito",
            
            // Com artigos/preposições
            "pode sim", "vamos sim", "tá certo"
        )

        /**
         * Lista de comandos NEGATIVOS (mais tempo).
         * Respostas infantis que indicam "não, quero mais tempo".
         */
        private val NEGATIVE_COMMANDS = setOf(
            // Respostas negativas diretas
            "não", "espera", "calma", "para", "aguarda",
            
            // Pedidos de mais tempo
            "mais tempo", "mais", "tempo", "demora", "devagar",
            
            // Variações infantis
            "nãããão", "não não", "ainda não", "peraí", "perai",
            
            // Frases completas comuns
            "mais um pouco", "só mais um pouco", "quero mais tempo",
            "ainda tô fazendo", "ainda não terminei", "não terminei",
            "deixa eu terminar", "quase lá"
        )
    }

    /**
     * Analisa o texto reconhecido e retorna o comando correspondente.
     * 
     * @param recognizedText Texto reconhecido pelo ASR (Vosk)
     * @return CommandResult indicando a intenção (POSITIVE, NEGATIVE ou UNKNOWN)
     */
    fun parse(recognizedText: String): CommandResult {
        val normalized = recognizedText.lowercase().trim()
        
        println("VoiceCommandParser: Texto normalizado = '$normalized'")
        
        // Verifica comandos positivos
        if (containsAnyCommand(normalized, POSITIVE_COMMANDS)) {
            println("VoiceCommandParser: Comando POSITIVO reconhecido")
            return CommandResult.POSITIVE
        }
        
        // Verifica comandos negativos
        if (containsAnyCommand(normalized, NEGATIVE_COMMANDS)) {
            println("VoiceCommandParser: Comando NEGATIVO reconhecido")
            return CommandResult.NEGATIVE
        }
        
        println("VoiceCommandParser: Comando DESCONHECIDO")
        return CommandResult.UNKNOWN
    }

    /**
     * Verifica se o texto contém algum dos comandos da lista.
     * 
     * @param text Texto normalizado
     * @param commands Lista de comandos válidos
     * @return true se encontrou algum comando, false caso contrário
     */
    private fun containsAnyCommand(text: String, commands: Set<String>): Boolean {
        return commands.any { command ->
            // Verifica se o texto contém o comando como palavra completa
            // ou se o comando é o texto inteiro (para comandos curtos como "sim")
            text == command || 
            text.contains(" $command ") || 
            text.startsWith("$command ") || 
            text.endsWith(" $command")
        }
    }
}
```

#### 5.3.2 Criar Testes Unitários

**Arquivo:** `app/src/test/java/com/pequenospassos/presentation/utils/VoiceCommandParserTest.kt` (NOVO)

```kotlin
class VoiceCommandParserTest {

    private lateinit var parser: VoiceCommandParser

    @Before
    fun setup() {
        parser = VoiceCommandParser()
    }

    @Test
    fun `parse deve retornar POSITIVE para 'sim'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("sim"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'pode'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("pode"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'não'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("não"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'mais tempo'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("mais tempo"))
    }

    @Test
    fun `parse deve retornar UNKNOWN para texto não reconhecido`() {
        assertEquals(CommandResult.UNKNOWN, parser.parse("banana"))
    }

    @Test
    fun `parse deve ser case insensitive`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("SIM"))
        assertEquals(CommandResult.POSITIVE, parser.parse("Sim"))
        assertEquals(CommandResult.POSITIVE, parser.parse("sIm"))
    }
}
```

#### 5.3.3 Checklist Fase 3

- [ ] VoiceCommandParser.kt criado
- [ ] Listas de comandos positivos/negativos definidas
- [ ] Método parse() implementado
- [ ] Testes unitários criados
- [ ] Todos os testes passando
- [ ] Build compila sem erros

---

### FASE 4: Atualizar AsrManager (1-2 dias)

#### 5.4.1 Expandir AsrManager

**Arquivo:** `presentation/utils/AsrManager.kt`

```kotlin
class AsrManager(private val context: Context) {

    // ...existing code...
    
    /**
     * Listener para reconhecimento de voz em tempo real.
     */
    interface VoiceRecognitionListener {
        fun onResult(text: String)
        fun onError(error: String)
        fun onTimeout()
    }
    
    private var currentListener: VoiceRecognitionListener? = null
    private var timeoutJob: Job? = null
    
    /**
     * Inicia reconhecimento de voz com timeout.
     * 
     * @param timeoutSeconds Tempo máximo de escuta (padrão: 3 segundos)
     * @param listener Callback para resultados
     */
    fun startListeningWithTimeout(
        timeoutSeconds: Int = 3,
        listener: VoiceRecognitionListener
    ) {
        currentListener = listener
        
        // Inicia reconhecimento
        startListening()
        
        // Agenda timeout
        timeoutJob = CoroutineScope(Dispatchers.Main).launch {
            delay(timeoutSeconds * 1000L)
            stopListening()
            currentListener?.onTimeout()
            currentListener = null
        }
    }
    
    /**
     * Para reconhecimento e cancela timeout.
     */
    override fun stopListening() {
        timeoutJob?.cancel()
        super.stopListening()
        currentListener = null
    }
    
    /**
     * Callback quando reconhecimento retorna resultado.
     */
    override fun onResult(hypothesis: String?) {
        hypothesis?.let {
            timeoutJob?.cancel()
            currentListener?.onResult(it)
            stopListening()
        }
    }
    
    /**
     * Callback quando ocorre erro.
     */
    override fun onError(error: Exception) {
        timeoutJob?.cancel()
        currentListener?.onError(error.message ?: "Erro desconhecido")
        stopListening()
    }
}
```

#### 5.4.2 Checklist Fase 4

- [ ] AsrManager.kt atualizado
- [ ] Interface VoiceRecognitionListener criada
- [ ] Método startListeningWithTimeout() implementado
- [ ] Timeout funciona corretamente
- [ ] Callbacks funcionam
- [ ] Build compila sem erros
- [ ] Teste manual: ASR escuta por 3 segundos

---

### FASE 5: Integrar ASR no Pop-up (2 dias)

#### 5.5.1 Atualizar TaskExecutionViewModel

**Arquivo:** `presentation/screens/execution/TaskExecutionViewModel.kt`

```kotlin
@HiltViewModel
class TaskExecutionViewModel @Inject constructor(
    // ...existing dependencies...
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    // ...existing code...
    
    // NOVO: Estado de escuta de voz
    private val _isListeningVoice = MutableStateFlow(false)
    val isListeningVoice: StateFlow<Boolean> = _isListeningVoice.asStateFlow()
    
    private val _voiceRecognitionError = MutableStateFlow<String?>(null)
    val voiceRecognitionError: StateFlow<String?> = _voiceRecognitionError.asStateFlow()
    
    // NOVO: Flag de habilitação de resposta por voz
    private val enableVoiceResponse: StateFlow<Boolean> = 
        appSettingsRepository.getEnableVoiceResponse()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )
    
    /**
     * Chamado quando timer do step termina.
     * Decide se exibe pop-up e se ativa ASR.
     */
    fun onStepTimerFinished() {
        val shouldAsk = askExtraTimeAtStep.value
        
        if (!shouldAsk) {
            // Avança automaticamente
            moveToNextStep()
            return
        }
        
        // Exibe pop-up
        _showExtraTimeDialog.value = true
        
        // Se resposta por voz habilitada, inicia escuta
        if (enableVoiceResponse.value) {
            startVoiceListening()
        }
    }
    
    /**
     * Inicia escuta de voz para reconhecer comando.
     */
    private fun startVoiceListening() {
        _isListeningVoice.value = true
        _voiceRecognitionError.value = null
        
        asrManager.startListeningWithTimeout(
            timeoutSeconds = 3,
            listener = object : AsrManager.VoiceRecognitionListener {
                override fun onResult(text: String) {
                    processVoiceCommand(text)
                }
                
                override fun onError(error: String) {
                    _voiceRecognitionError.value = error
                    _isListeningVoice.value = false
                    // Pop-up continua aberto para clique manual
                }
                
                override fun onTimeout() {
                    _isListeningVoice.value = false
                    // Pop-up continua aberto para clique manual
                }
            }
        )
    }
    
    /**
     * Processa comando de voz reconhecido.
     */
    private fun processVoiceCommand(recognizedText: String) {
        val parser = VoiceCommandParser()
        val result = parser.parse(recognizedText)
        
        _isListeningVoice.value = false
        
        when (result) {
            CommandResult.POSITIVE -> {
                // Avançar para próximo step
                _showExtraTimeDialog.value = false
                moveToNextStep()
            }
            CommandResult.NEGATIVE -> {
                // Adicionar mais 30 segundos
                _showExtraTimeDialog.value = false
                addExtraTime(30)
            }
            CommandResult.UNKNOWN -> {
                // Não reconheceu, deixa pop-up aberto para clique
                _voiceRecognitionError.value = "Não entendi. Use os botões abaixo."
            }
        }
    }
    
    /**
     * Adiciona tempo extra ao step atual.
     */
    private fun addExtraTime(seconds: Int) {
        viewModelScope.launch {
            _remainingTime.value += seconds
            resumeTimer()
        }
    }
    
    /**
     * Usuário clicou em botão (cancela escuta de voz).
     */
    fun onManualButtonClick(action: String) {
        if (_isListeningVoice.value) {
            asrManager.stopListening()
            _isListeningVoice.value = false
        }
        
        when (action) {
            "NEXT" -> {
                _showExtraTimeDialog.value = false
                moveToNextStep()
            }
            "MORE_TIME" -> {
                _showExtraTimeDialog.value = false
                addExtraTime(30)
            }
        }
    }
}
```

#### 5.5.2 Atualizar TaskExecutionScreen

**Arquivo:** `presentation/screens/execution/TaskExecutionScreen.kt`

```kotlin
@Composable
fun TaskExecutionScreen(
    viewModel: TaskExecutionViewModel = hiltViewModel(),
    // ...other params...
) {
    val showExtraTimeDialog by viewModel.showExtraTimeDialog.collectAsState()
    val isListeningVoice by viewModel.isListeningVoice.collectAsState() // NOVO
    val voiceError by viewModel.voiceRecognitionError.collectAsState() // NOVO
    
    // ...existing code...
    
    // Pop-up de tempo extra
    if (showExtraTimeDialog) {
        AlertDialog(
            onDismissRequest = { /* Não permite fechar sem resposta */ },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⏱️ Tempo Esgotado!")
                    
                    // NOVO: Indicador de escuta
                    if (isListeningVoice) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Escutando",
                            tint = Color.Red,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    // Animação de pulsação
                                    scaleX = 1f + (System.currentTimeMillis() % 1000 / 1000f) * 0.2f
                                    scaleY = 1f + (System.currentTimeMillis() % 1000 / 1000f) * 0.2f
                                }
                        )
                    }
                }
            },
            text = {
                Column {
                    Text("Podemos avançar ou quer mais 30 segundos?")
                    
                    // NOVO: Feedback de escuta
                    if (isListeningVoice) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "🎤 Estou te escutando...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    
                    // NOVO: Erro de reconhecimento
                    voiceError?.let { error ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "❌ $error",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.onManualButtonClick("NEXT") }
                ) {
                    Text("➡️ Próximo Passo")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { viewModel.onManualButtonClick("MORE_TIME") }
                ) {
                    Text("⏱️ Mais 30 Segundos")
                }
            }
        )
    }
}
```

#### 5.5.3 Checklist Fase 5

- [ ] TaskExecutionViewModel atualizado
- [ ] TaskExecutionScreen atualizado
- [ ] Pop-up exibe indicador de escuta (microfone animado)
- [ ] ASR inicia automaticamente ao exibir pop-up
- [ ] Timeout de 3 segundos funciona
- [ ] Comandos positivos/negativos funcionam
- [ ] Botões continuam funcionando (fallback)
- [ ] Build compila sem erros
- [ ] Teste em dispositivo: falar "sim" avança step
- [ ] Teste em dispositivo: falar "não" adiciona 30s

---

### FASE 6: Permissões e Polimentos (1 dia)

#### 5.6.1 Solicitar Permissão de Microfone

**Arquivo:** `MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(
                this,
                "Permissão de microfone necessária para resposta por voz",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Solicita permissão de microfone se não tiver
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
        
        // ...existing code...
    }
}
```

#### 5.6.2 Adicionar Feedback Sonoro

**Arquivo:** `TaskExecutionViewModel.kt`

```kotlin
// Adicionar som ao reconhecer comando
private fun processVoiceCommand(recognizedText: String) {
    val parser = VoiceCommandParser()
    val result = parser.parse(recognizedText)
    
    _isListeningVoice.value = false
    
    when (result) {
        CommandResult.POSITIVE -> {
            playSoundEffect(R.raw.success_sound) // Som de sucesso
            _showExtraTimeDialog.value = false
            moveToNextStep()
        }
        CommandResult.NEGATIVE -> {
            playSoundEffect(R.raw.tick_sound) // Som de tick
            _showExtraTimeDialog.value = false
            addExtraTime(30)
        }
        CommandResult.UNKNOWN -> {
            playSoundEffect(R.raw.error_sound) // Som de erro
            _voiceRecognitionError.value = "Não entendi. Use os botões abaixo."
        }
    }
}
```

#### 5.6.3 Atualizar Versão

**Arquivo:** `app/build.gradle.kts`

```kotlin
versionCode = 220
versionName = "2.2.0"
```

**Arquivo:** `SplashScreen.kt`

```kotlin
Text(text = "Versão 2.2.0")
```

#### 5.6.4 Checklist Fase 6

- [ ] Permissão de microfone solicitada
- [ ] Feedback sonoro ao reconhecer comando
- [ ] Versão incrementada (2.1.0 → 2.2.0)
- [ ] SplashScreen atualizada
- [ ] Build compila sem erros
- [ ] Teste completo em dispositivo

---

## 6. LISTAS DE COMANDOS DE VOZ

### 6.1 Comandos POSITIVOS (Avançar)

#### Respostas Afirmativas Diretas
- sim
- pode
- vamos
- vai
- continua
- próximo
- avança

#### Variações Infantis
- tá
- ok
- beleza
- isso
- é
- uhum
- aham

#### Frases Completas
- pode ir
- vamos lá
- tá bom
- pode avançar
- próximo passo
- já terminei
- terminei
- pronto
- feito

#### Com Artigos/Preposições
- pode sim
- vamos sim
- tá certo

### 6.2 Comandos NEGATIVOS (Mais Tempo)

#### Respostas Negativas Diretas
- não
- espera
- calma
- para
- aguarda

#### Pedidos de Mais Tempo
- mais tempo
- mais
- tempo
- demora
- devagar

#### Variações Infantis
- nãããão
- não não
- ainda não
- peraí
- perai

#### Frases Completas
- mais um pouco
- só mais um pouco
- quero mais tempo
- ainda tô fazendo
- ainda não terminei
- não terminei
- deixa eu terminar
- quase lá

---

## 7. FLUXOGRAMAS

### 7.1 Fluxo Principal

```
[Timer do Step Termina]
         ↓
[Checkbox "Perguntar tempo extra" ativo?]
         ↓ NÃO
    [Avançar automaticamente]
         ↓ SIM
[Exibir Pop-up]
         ↓
[Checkbox "Resposta em Áudio" ativo?]
         ↓ NÃO
    [Aguardar clique nos botões]
         ↓ SIM
[Iniciar ASR Vosk]
[Exibir ícone de microfone]
[Timeout: 3 segundos]
         ↓
[Texto reconhecido?]
         ↓ SIM
    [VoiceCommandParser.parse()]
         ↓
    [POSITIVE?] → [Avançar para próximo step]
    [NEGATIVE?] → [Adicionar 30 segundos]
    [UNKNOWN?] → [Exibir erro + aguardar clique]
         ↓ NÃO (timeout)
    [Aguardar clique nos botões]
```

### 7.2 Fluxo de Escuta ASR

```
[startVoiceListening()]
         ↓
[isListeningVoice = true]
[voiceRecognitionError = null]
         ↓
[asrManager.startListeningWithTimeout(3)]
         ↓
[3 cenários possíveis:]
    ↓
┌───────────────────┬──────────────────┬───────────────┐
│                   │                  │               │
[onResult]     [onError]        [onTimeout]
│                   │                  │
[texto reconhecido] [erro do ASR]     [3s sem fala]
│                   │                  │
[processVoiceCommand] [exibir erro]   [parar escuta]
│                   │                  │
[isListening=false] [isListening=false] [isListening=false]
│                   │                  │
[executar ação]     [aguardar clique]  [aguardar clique]
```

---

## 8. TESTES E VALIDAÇÃO

### 8.1 Testes Unitários

**Arquivo:** `VoiceCommandParserTest.kt`

- [ ] Comandos positivos reconhecidos
- [ ] Comandos negativos reconhecidos
- [ ] Comandos desconhecidos retornam UNKNOWN
- [ ] Case insensitive funciona
- [ ] Variações de pronúncia funcionam

### 8.2 Testes de Integração

- [ ] ASR inicia ao exibir pop-up
- [ ] Timeout de 3 segundos funciona
- [ ] Comando positivo avança step
- [ ] Comando negativo adiciona 30s
- [ ] Botões continuam funcionando
- [ ] Checkbox persiste no banco

### 8.3 Testes Manuais em Dispositivo

#### Cenário 1: Comando Positivo
1. Habilitar checkbox "Resposta em Áudio"
2. Iniciar execução de tarefa
3. Aguardar timer terminar
4. Pop-up aparece com microfone
5. Falar "sim"
6. ✅ Esperado: Avança para próximo step

#### Cenário 2: Comando Negativo
1. Habilitar checkbox "Resposta em Áudio"
2. Iniciar execução de tarefa
3. Aguardar timer terminar
4. Pop-up aparece com microfone
5. Falar "não"
6. ✅ Esperado: Adiciona 30 segundos e continua no step

#### Cenário 3: Comando Desconhecido
1. Habilitar checkbox "Resposta em Áudio"
2. Iniciar execução de tarefa
3. Aguardar timer terminar
4. Pop-up aparece com microfone
5. Falar "banana"
6. ✅ Esperado: Exibe erro + botões continuam disponíveis

#### Cenário 4: Timeout
1. Habilitar checkbox "Resposta em Áudio"
2. Iniciar execução de tarefa
3. Aguardar timer terminar
4. Pop-up aparece com microfone
5. Ficar em silêncio por 3+ segundos
6. ✅ Esperado: Para de escutar + botões continuam disponíveis

#### Cenário 5: Clique Manual Durante Escuta
1. Habilitar checkbox "Resposta em Áudio"
2. Iniciar execução de tarefa
3. Aguardar timer terminar
4. Pop-up aparece com microfone
5. Clicar em "Próximo Passo" antes de falar
6. ✅ Esperado: Escuta cancela + avança step

#### Cenário 6: Checkbox Desabilitado
1. Desabilitar checkbox "Resposta em Áudio"
2. Iniciar execução de tarefa
3. Aguardar timer terminar
4. Pop-up aparece SEM microfone
5. ✅ Esperado: Somente botões disponíveis

### 8.4 Testes de Performance

- [ ] Inicialização do ASR < 1s
- [ ] Reconhecimento < 500ms após falar
- [ ] Uso de memória < 50MB adicional
- [ ] UI não trava durante reconhecimento

### 8.5 Testes de Acessibilidade

- [ ] TalkBack anuncia estado de escuta
- [ ] Botões têm contentDescription adequado
- [ ] Feedback visual suficiente para surdos

---

## 9. RISCOS E MITIGAÇÕES

### 9.1 Risco 1: ASR Não Reconhece Comando

**Probabilidade:** ALTA  
**Impacto:** MÉDIO

**Mitigação:**
- ✅ Sempre exibir botões como fallback
- ✅ Lista ampla de comandos infantis
- ✅ Timeout curto (3s) para não frustrar criança
- ✅ Mensagem clara: "Não entendi. Use os botões abaixo."

### 9.2 Risco 2: Permissão de Microfone Negada

**Probabilidade:** MÉDIA  
**Impacto:** ALTO

**Mitigação:**
- ✅ Solicitar permissão na primeira execução
- ✅ Explicar para que serve ("Para você responder por voz")
- ✅ Se negada, checkbox fica desabilitado
- ✅ Toast explicando necessidade da permissão

### 9.3 Risco 3: Ruído Ambiente

**Probabilidade:** ALTA  
**Impacto:** MÉDIO

**Mitigação:**
- ✅ Vosk tem boa tolerância a ruído
- ✅ Timeout curto evita reconhecimento de ruído como comando
- ✅ Parser filtra apenas comandos válidos
- ✅ Botões sempre disponíveis

### 9.4 Risco 4: Performance em Dispositivos Antigos

**Probabilidade:** MÉDIA  
**Impacto:** ALTO

**Mitigação:**
- ✅ Usar modelo Vosk pequeno (já implementado)
- ✅ Reconhecimento assíncrono (não trava UI)
- ✅ Checkbox permite desabilitar se necessário
- ✅ Testar em dispositivo de referência (minSdk 24)

### 9.5 Risco 5: Bateria

**Probabilidade:** BAIXA  
**Impacto:** BAIXO

**Mitigação:**
- ✅ ASR ativo apenas 3 segundos por vez
- ✅ Uso esporádico (só quando timer termina)
- ✅ Não mantém microfone sempre aberto

---

## 10. CHECKLIST DE IMPLEMENTAÇÃO

### 10.1 Antes de Começar

- [ ] Ler MVP11_ESTADO_ATUAL_CONSOLIDADO.md
- [ ] Ler GUIDELINES.md (seção de ASR)
- [ ] Ler este documento completamente
- [ ] Criar branch: `feature/mvp-14-asr-popups`
- [ ] Incrementar versão: 2.1.0 → 2.2.0

### 10.2 Fase 1: Banco de Dados

- [ ] Adicionar campo enableVoiceResponse em AppSettings.kt
- [ ] Criar Migration 5→6
- [ ] Atualizar AppSettingsDao
- [ ] Atualizar AppSettingsRepository
- [ ] Incrementar versão do banco (5→6)
- [ ] Build compila sem erros
- [ ] App instala e abre normalmente

### 10.3 Fase 2: Checkbox

- [ ] Atualizar HistoryViewModel
- [ ] Atualizar HistoryScreen
- [ ] Checkbox visível
- [ ] Estado persiste
- [ ] Build compila sem erros
- [ ] Teste: checkbox funciona

### 10.4 Fase 3: Parser

- [ ] Criar VoiceCommandParser.kt
- [ ] Implementar listas de comandos
- [ ] Implementar método parse()
- [ ] Criar testes unitários
- [ ] Todos os testes passando
- [ ] Build compila sem erros

### 10.5 Fase 4: ASR

- [ ] Atualizar AsrManager.kt
- [ ] Criar interface VoiceRecognitionListener
- [ ] Implementar startListeningWithTimeout()
- [ ] Timeout funciona
- [ ] Build compila sem erros
- [ ] Teste manual: ASR escuta por 3s

### 10.6 Fase 5: Integração

- [ ] Atualizar TaskExecutionViewModel
- [ ] Atualizar TaskExecutionScreen
- [ ] Pop-up exibe microfone animado
- [ ] ASR inicia automaticamente
- [ ] Comandos funcionam
- [ ] Botões funcionam
- [ ] Build compila sem erros
- [ ] Teste: falar "sim" avança
- [ ] Teste: falar "não" adiciona 30s

### 10.7 Fase 6: Polimentos

- [ ] Solicitar permissão de microfone
- [ ] Adicionar feedback sonoro
- [ ] Atualizar versão (2.2.0)
- [ ] Atualizar SplashScreen
- [ ] Build compila sem erros
- [ ] Teste completo em dispositivo

### 10.8 Documentação

- [ ] Atualizar CHANGELOG.md
- [ ] Atualizar SPECIFICATION_FOR_APP.md
- [ ] Criar MVP14_IMPLEMENTACAO_COMPLETA.md
- [ ] Atualizar headers (50 linhas) dos arquivos modificados
- [ ] Atualizar MVP11_ESTADO_ATUAL_CONSOLIDADO.md

### 10.9 Testes Finais

- [ ] Todos os testes unitários passando
- [ ] Todos os cenários manuais testados
- [ ] Anti-regressão: MVPs 01-11 funcionando
- [ ] Performance aceitável

### 10.10 Commit e Release

- [ ] Commit local com mensagem detalhada
- [ ] Criar tag v2.2.0
- [ ] Push para repositório remoto
- [ ] Merge para main (após validação)

---

## 11. ESTIMATIVA DETALHADA

| Fase | Descrição | Tempo Estimado |
|------|-----------|----------------|
| Fase 1 | Banco de Dados | 1 dia |
| Fase 2 | Checkbox | 1 dia |
| Fase 3 | Parser | 1 dia |
| Fase 4 | ASR | 1-2 dias |
| Fase 5 | Integração | 2 dias |
| Fase 6 | Polimentos | 1 dia |
| **TOTAL** | **MVP-14 Completo** | **5-7 dias** |

---

## 12. PRÓXIMOS PASSOS APÓS MVP-14

Após concluir MVP-14, as próximas funcionalidades sugeridas são:

### MVP-15: Melhorias no ASR
- Reconhecimento contínuo durante execução
- Comandos de navegação ("pular", "voltar", "repetir")
- Ajuste de sensibilidade do ASR

### MVP-16: Exportação/Importação de Atividades
- Exportar tarefas para JSON
- Importar tarefas de JSON
- Compartilhamento entre dispositivos

### MVP-17: Filtro de Categorias
- Dropdown de seleção de categoria
- Filtrar tarefas por categoria

---

## 13. CONCLUSÃO

Este documento fornece um plano DETALHADO e INCREMENTAL para implementação de ASR em pop-ups. Seguindo as fases na ordem, o desenvolvimento será:

✅ **Modular** - Cada fase pode ser testada individualmente  
✅ **Rastreável** - Checklist completo para acompanhamento  
✅ **Seguro** - Testes em cada etapa previnem regressões  
✅ **Documentado** - Código e decisões bem documentados

**Aguardando aprovação para iniciar implementação.**

---

**Criado:** 01/11/2025  
**Autor:** Claude Sonnet 4.5 (GitHub Copilot)  
**Status:** 📋 PLANEJAMENTO - AGUARDANDO APROVAÇÃO  
**Versão do Documento:** 1.0

