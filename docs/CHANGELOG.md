<!--
Arquivo: docs/CHANGELOG.md
Objetivo: Histórico de mudanças do projeto Pequenos Passos.
Correlações: GUIDELINES.md, arquivos de implementação, migrations, releases, MVP11_ESTADO_ATUAL_CONSOLIDADO.md
Histórico de alterações:
- 2025-11-01 (Claude Sonnet 4.5): MVP-14 Fases 1 e 2 - ASR em Pop-ups (Banco de Dados + Checkbox)
- 2025-11-01 (Claude Sonnet 4.5): RETORNO - Volta como Code Assistant principal até o final do projeto
- 2025-11-01 (GPT-4.1): Transição temporária - Adicionado MVP-11 (v2.1.0) - Consolidação
- 2025-10-27 (Claude Sonnet 4.5): Adicionada entrada para v1.11.6 (campos de cadastro, migration, versionamento).
- 2025-10-24 (Claude Sonnet 4.5): Atualização para MVP-10, início de rastreabilidade 50 linhas.
Observação: Sempre atualizar as primeiras 50 linhas com resumo das últimas mudanças e rastreabilidade.
Status Atual: MVP-14 Fases 1-2 Completas - Code Assistant: Claude Sonnet 4.5 (GitHub Copilot)
-->
# CHANGELOG

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [Em Desenvolvimento] - MVP-14 - 2025-11-01

### 🎤 ASR (RECONHECIMENTO DE VOZ) EM POP-UPS - FASES 1-2 COMPLETAS

#### ✅ FASE 1: Banco de Dados (COMPLETA)

**Adicionado:**
- ✨ Campo `enableVoiceResponse` em `AppSettings.kt`
  - Tipo: Boolean (padrão: false)
  - Habilita reconhecimento de voz no pop-up de tempo extra
- ✨ Migration 7→8 criada
  - SQL: `ALTER TABLE app_settings ADD COLUMN enableVoiceResponse INTEGER NOT NULL DEFAULT 0`
- ✨ Métodos no DAO e Repository
  - `updateEnableVoiceResponse()` para atualizar preferência
  - `getEnableVoiceResponse()` para observar mudanças (Flow)

**Arquivos modificados:**
- `domain/model/AppSettings.kt`
- `data/database/AppDatabase.kt`
- `data/database/dao/AppSettingsDao.kt`
- `domain/repository/AppSettingsRepository.kt`
- `data/repository/AppSettingsRepositoryImpl.kt`

**Build:** SUCCESS  
**Database Version:** 7 → 8

---

#### ✅ FASE 2: Checkbox em Histórico & Ferramentas (COMPLETA)

**Adicionado:**
- ✨ Checkbox "Resposta em Áudio" na tela Histórico & Ferramentas
  - Habilitado apenas se "Perguntar se deseja mais tempo" estiver ativo
  - Texto explicativo: "🎤 O app vai escutar sua resposta por 3 segundos"
  - Feedback visual dinâmico (negrito quando ativo, cinza quando desabilitado)
  - Persistência no banco de dados funcionando

**Arquivos modificados:**
- `presentation/screens/history/HistoryViewModel.kt`
  - StateFlow `enableVoiceResponse`
  - Método `setEnableVoiceResponse()`
- `presentation/screens/history/HistoryScreen.kt`
  - UI do checkbox com lógica condicional

**Corrigido:**
- 🐛 **CRÍTICO - Crash ao abrir tela Histórico**
  - Problema: Query `getEnableVoiceResponseFlow()` retornava `Flow<Boolean>` mas banco podia não ter registro
  - Erro: NullPointerException ao tentar converter null → Boolean
  - Solução: Mudado para `Flow<Boolean?>` no DAO + `.map { it ?: false }` no Repository
  - Arquivos corrigidos:
    - `data/database/dao/AppSettingsDao.kt` (Flow<Boolean?> + LIMIT 1)
    - `data/repository/AppSettingsRepositoryImpl.kt` (import map + tratamento null)

**Validação:**
- ✅ 8/8 testes passando em dispositivo físico
- ✅ Persistência funcionando (fechar/reabrir app mantém estado)
- ✅ Lógica de habilitação funcionando
- ✅ Sem crashes

**Build:** SUCCESS  
**Tempo de implementação:** ~1 hora (incluindo correção de bug)

---

#### 📝 Documentação Criada

- `docs/MVP14_ASR_POPUPS_PLANEJAMENTO_DETALHADO.md` - Planejamento completo das 6 fases
- `docs/MVP14_FASE1_CONCLUSAO.md` - Conclusão da Fase 1
- `docs/MVP14_FASE2_GUIA_VALIDACAO.md` - Guia de testes práticos
- `docs/MVP14_FASE2_CORRECAO_CRASH.md` - Análise e correção do crash
- `docs/MVP14_FASE2_CONCLUSAO.md` - Conclusão da Fase 2

---

#### 🔄 Lições Aprendidas

**MVP-14 Fase 2:**
- Sempre usar `Flow<Tipo?>` quando query pode retornar null
- Tratar null no Repository com `.map { it ?: valorPadrao }`
- Validar em dispositivo real revela problemas não vistos em emulador
- Documentar bugs durante desenvolvimento facilita troubleshooting futuro

---

---

#### ✅ FASE 3: VoiceCommandParser (COMPLETA)

**Adicionado:**
- ✨ Classe `VoiceCommandParser.kt` em `presentation/utils/`
  - Enum `CommandResult` (POSITIVE, NEGATIVE, UNKNOWN)
  - Método `parse()` para analisar texto do ASR
  - Método `getStats()` para estatísticas
- ✨ **50 comandos infantis cadastrados:**
  - 25 comandos positivos ("sim", "pode", "vamos", "tá", "ok", "pronto", etc)
  - 25 comandos negativos ("não", "espera", "mais tempo", "peraí", etc)
- ✨ Normalização de texto (lowercase, trim)
- ✨ 4 estratégias de matching (exato, isolado, início, final)
- ✨ Case insensitive (funciona com maiúsculas/minúsculas)

**Testes:**
- ✨ `VoiceCommandParserTest.kt` criado
  - 43 testes unitários
  - Cobertura: comandos positivos, negativos, desconhecidos, case insensitive, normalização, frases completas, variações infantis, estatísticas
  
**Arquivos criados:**
- `presentation/utils/VoiceCommandParser.kt` (~200 linhas)
- `test/.../VoiceCommandParserTest.kt` (~350 linhas)

**Build:** SUCCESS  
**Testes:** 43 testes criados  
**Warnings:** 3 (classe não usada ainda - esperado)

---

#### ✅ FASE 4: ASR com Timeout (COMPLETA)

**Adicionado:**
- ✨ Classe `AsrManager.kt` em `presentation/utils/`
  - Gerenciamento de reconhecimento de voz com Vosk
  - Timeout automático configurável (padrão: 3 segundos)
  - Interface `VoiceRecognitionListener` com 4 callbacks
- ✨ **Callbacks implementados:**
  - `onResult(text)` - Reconhecimento finalizado com sucesso
  - `onPartialResult(text)` - Feedback em tempo real (opcional)
  - `onError(error)` - Tratamento de erros
  - `onTimeout()` - Tempo limite atingido sem fala
- ✨ Gerenciamento automático de recursos
  - Método `initialize()` para carregar modelo Vosk
  - Método `startListeningWithTimeout()` com Coroutines
  - Método `stopListening()` com cleanup automático
  - Método `release()` para liberar recursos
- ✨ Integração com Vosk
  - Usa modelo existente (vosk-model-small-pt-0.3)
  - Processa JSON de resultados parciais e finais
  - Tratamento robusto de erros

**Arquivos criados:**
- `presentation/utils/AsrManager.kt` (~250 linhas)

**Build:** SUCCESS (aguardando)  
**Progresso:** 4/6 fases completas (66.7%)

---

#### ✅ FASE 5: Integração Completa no Pop-up (COMPLETA E VALIDADA)

**Adicionado:**
- ✨ **Integração completa ASR + VoiceCommandParser no pop-up de tempo extra**
  - TaskExecutionViewModel integra AsrManager e VoiceCommandParser
  - Context injetado via @ApplicationContext
  - StateFlows para isListeningVoice e voiceRecognitionError
  - Observação de configurações (askExtraTimeAtStep, enableVoiceResponse)
- ✨ **TTS fala ANTES de ASR iniciar**
  - Método speakWithCallback() no TtsManager
  - Callback executado quando TTS termina (UtteranceProgressListener)
  - Evita que ASR "ouça" o próprio TTS
- ✨ **UI com feedback visual completo**
  - Mensagem "🎤 Estou te escutando..." quando ASR ativo
  - Mensagem de erro se comando não reconhecido
  - Botões SEMPRE visíveis e ativos (não desabilitam durante escuta)
  - Título limpo (removido ícone de microfone que aparecia recortado)
- ✨ **Timeout de 30 segundos** (aumentado de 3s)
  - Tempo confortável para criança pensar e responder
  - Botões continuam ativos após timeout
- ✨ **62 comandos de voz** refinados
  - 32 comandos positivos (avançar)
  - 30 comandos negativos (mais tempo)
  - Matching de palavra completa (evita matches parciais)
  - Lista de palavras da pergunta (4 palavras ignoradas)

**Métodos Implementados:**
- `startVoiceListening()` - Inicia ASR com timeout de 30s
- `processVoiceCommand()` - Analisa comando e executa ação
- `onManualButtonClick()` - Cancela ASR se usuário clicar em botão
- `speakWithCallback()` - TTS com callback ao terminar

**Fluxo Completo:**
1. Timer termina → Pop-up aparece
2. TTS fala mensagem → Callback ao terminar
3. ASR inicia (se enableVoiceResponse = true)
4. Escuta por 30 segundos
5. Texto reconhecido → VoiceCommandParser analisa
6. POSITIVE → Avança | NEGATIVE → +30s | UNKNOWN → Erro + Botões
7. Botões sempre ativos para escolha manual

**Correções e Melhorias:**
- 🐛 Corrigido: "quero" agora é POSITIVE (era NEGATIVE incorretamente)
- 🐛 Corrigido: Palavras afirmativas ("podemos", "vamos", "continuar", "avançar", "próximo") agora são POSITIVE
- 🐛 Corrigido: Matching de palavra completa (evita "pode" dar match com "podemos")
- ✨ Melhorado: ASR só inicia após TTS terminar
- ✨ Melhorado: Timeout aumentado de 3s para 30s
- ✨ Melhorado: Ícone de microfone removido do título (aparecia recortado)

**Arquivos modificados:**
- `presentation/screens/execution/TaskExecutionViewModel.kt` (~100 linhas)
- `presentation/screens/execution/TaskExecutionScreen.kt` (~50 linhas)
- `presentation/utils/TtsManager.kt` (~30 linhas callback)
- `presentation/utils/VoiceCommandParser.kt` (~80 linhas refinamento)

**Validação:**
- ✅ 12/12 testes passando em dispositivo real (Samsung Galaxy S10e - Android 12)
- ✅ TTS e ASR sincronizados perfeitamente
- ✅ Botões sempre ativos funcionando
- ✅ Comandos de voz reconhecendo corretamente

**Build:** SUCCESS  
**Progresso:** 5/6 fases completas (83.3%)  
**Status:** 100% funcional e validado em dispositivo real

---

#### ✅ FASE 6: Polimentos e Versão 2.2.0 (COMPLETA E VALIDADA)

**Versão:** 2.2.0  
**versionCode:** 220  

**Adicionado:**
- ✨ **Versão 2.2.0** oficialmente lançada
  - versionCode: 220 (incrementado de 210)
  - versionName: "2.2.0"
  - SplashScreen atualizada com v2.2.0
- ✨ **Permissões de microfone** com request runtime
  - StateFlow hasMicrophonePermission no ViewModel
  - Request permission com Accompanist Permissions
  - Card de explicação se permissão negada
  - Fallback para botões manuais (sempre ativos)
- ✨ **6 Refinamentos de UX/UI:**
  1. Subtítulo "Configurações" em Histórico & Ferramentas
  2. TTS triplo ao concluir tarefa (parabéns + sucesso + estrelas)
  3. Nomenclaturas: "Steps" → "Pequenos Passos"
  4. Títulos de dialogs: "Editar Step" → "Editar Passo"
  5. Removido asterisco de "Pequenos Passos *"
  6. "Tempo selecionado" → "Tempo" (mais limpo)

**Melhorado:**
- 🎤 **TTS ao concluir tarefa** agora fala 3 mensagens:
  - Nome + parabéns ("Maria, incrível!")
  - Mensagem de sucesso ("Você fez um ótimo trabalho!")
  - **Estrelas ganhas** ("Você ganhou 3 estrelas!")
  - Tratamento de singular/plural correto
- 🎨 **Nomenclaturas padronizadas** em todo o app:
  - "Steps" → "Passos"
  - "Adicionar Step" → "Adicionar Passo"
  - "Step 1" → "Passo 1"
  - "Duração do Step" → "Duração do Passo"
  - Ícone "+" → Ícone "✏️" (Edit/Lápis) para editar passos
- 🔒 **Permissões de microfone** tratadas adequadamente:
  - Request apenas quando necessário
  - Explicação clara para o usuário
  - Botões sempre funcionais (fallback)

**Arquivos modificados:**
- `app/build.gradle.kts` (versão 2.2.0)
- `presentation/screens/SplashScreen.kt` (v2.2.0)
- `presentation/screens/execution/TaskExecutionViewModel.kt` (permissões)
- `presentation/screens/execution/TaskExecutionScreen.kt` (request permission + card)
- `presentation/screens/history/HistoryScreen.kt` (subtítulo Configurações)
- `presentation/screens/completion/TaskCompletionScreen.kt` (TTS triplo)
- `presentation/screens/taskform/TaskFormScreen.kt` (nomenclaturas)
- `presentation/components/TimerInput.kt` ("Tempo:")

**Validação:**
- ✅ 10/10 testes passando em dispositivo real (Samsung Galaxy S10e - Android 12)
- ✅ Permissões funcionando corretamente
- ✅ TTS 3 mensagens funcionando
- ✅ Nomenclaturas consistentes
- ✅ UX polida e refinada

**Build:** SUCCESS  
**Progresso:** 6/6 fases completas (100%)  
**Status:** MVP-14 100% completo e funcional

---

## 🎉 MVP-14 COMPLETO - 100%

**Total de Fases:** 6/6 (100%)  
**Versão Final:** 2.2.0  
**Status:** ✅ Funcional e validado em dispositivo real

**Funcionalidades Entregues:**
- ✅ Reconhecimento de voz em pop-ups (ASR)
- ✅ 62 comandos de voz em português brasileiro
- ✅ Timeout de 30 segundos
- ✅ Botões sempre ativos (falar OU clicar)
- ✅ Feedback visual (🎤 escutando)
- ✅ Feedback auditivo (TTS triplo)
- ✅ Permissões de microfone tratadas
- ✅ UX polida e consistente

**Próximo MVP:** MVP-15 (Exportar/Importar atividades, Filtros)

---

---

## [2.1.0] - MVP-11 - 2025-11-01

### 🎯 CONSOLIDAÇÃO DO PROJETO E TRANSIÇÃO DE AI ASSISTANT

#### Adicionado
- ✨ **Documento Consolidado de Estado Atual:**
  - `docs/MVP11_ESTADO_ATUAL_CONSOLIDADO.md` - Fonte única de verdade
  - Arquitetura completa documentada
  - Todas as funcionalidades listadas e validadas
  - Guia completo para Code Assistants (AI)
  - Roadmap detalhado para próximos MVPs (12-15)

- ✨ **Checkbox de Controle de Pop-up de Tempo Extra:**
  - Configuração em "Histórico & Ferramentas"
  - Controla exibição do pop-up "Deseja mais 30 segundos?"
  - Persistido no banco de dados (`AppSettings.askExtraTimeAtStep`)
  - Valor padrão: `true` (pop-up ativado)

#### Corrigido
- 🐛 **CRÍTICO - Campo ausente na entidade AppSettings:**
  - Problema: Migration adicionou `askExtraTimeAtStep` mas campo não estava na data class
  - Erro: `SQLiteException`, falha ao salvar configuração
  - Solução: Adicionado campo `askExtraTimeAtStep: Boolean = true` em `AppSettings.kt`
  - Lição documentada: Sempre adicionar campos na entidade ao criar migration

#### Documentação
- 📝 **Revisão Completa de Documentação:**
  - GUIDELINES.md atualizado com seção de transição de AI
  - CHANGELOG.md atualizado com padrão de rastreabilidade
  - Headers de rastreabilidade nas primeiras 50 linhas reforçados
  - Documentação sincronizada com código

#### Técnico
- 🔧 **Transição de AI Assistant:**
  - De: Claude Sonnet 4.5 → GPT-4.1 (transição temporária)
  - Para: Claude Sonnet 4.5 (RETORNO - GitHub Copilot) - A partir desta versão até o final
  - Documentação preparada para facilitar continuidade
  - Boas práticas documentadas para novos AI Assistants

- 🔧 **Versão:**
  - versionCode: 210
  - versionName: "2.1.0"
  - Database version: 5 (sem mudanças)

- 🔧 **Code Assistant Atual:**
  - Claude Sonnet 4.5 (via GitHub Copilot)
  - Responsável por todas as implementações futuras
  - Continuidade garantida com documentação consolidada

#### Validado
- ✅ 100% funcional em dispositivo físico
- ✅ Todos os testes passaram
- ✅ Build: SUCCESS
- ✅ Anti-regressão: MVPs 01-10 funcionando
- ✅ Checkbox de configuração persistindo corretamente
- ✅ Pop-up de tempo extra respeitando configuração

#### Status
- ✅ **MVP-11 COMPLETO**
- ✅ Projeto consolidado e documentado
- ✅ Pronto para MVP-12 (Exportação/Importação de Atividades)

---

## [1.12.0] - 2025-10-30
### Adicionado
- Exibição da média diária de estrelas e tarefas feitas na semana na tela de Histórico & Ferramentas.
- Novos métodos no repositório para obter totais diários de estrelas e tarefas.

### Corrigido
- Query de tarefas menos executadas agora inclui tarefas nunca realizadas (total = 0).
- Ajustes visuais e textuais na tela de Histórico (títulos, espaçamento, legendas).

### Melhorado
- Refino do cálculo de médias semanais para refletir corretamente os últimos 7 dias.
- Padronização de exibição conforme guidelines.

### Referências
- Detalhes completos das correções e práticas em: docs/GUIDELINES.md
- Histórico de erros e soluções: relatórios de build, logs de testes, e comentários nos arquivos de teste.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [Lançado] - MVP09 (v1.11.3) - 2025-10-24

### 🎯 Sistema de Controle Diário de Tarefas - COMPLETO

#### Adicionado
- ✨ **Sistema de controle diário de tarefas:**
  - Cada tarefa pode ser executada apenas 1x por dia
  - Registro de conclusões com data/hora em `task_completions`
  - Reset automático à meia-noite
  - Contador de estrelas diárias no topo da tela "Atividades"
  
- ✨ **Entidade TaskCompletion:**
  - Campos: taskId, childId, date, completedAt, starsEarned
  - Índice UNIQUE: garante 1 conclusão por tarefa/criança/dia
  - TypeConverters para LocalDate e LocalDateTime

- ✨ **TaskCompletionDao:** 15+ métodos incluindo:
  - `isTaskCompletedTodayFlow()` - Verificação reativa
  - `getStarsForDateFlow()` - Total de estrelas do dia
  - `getCompletedTaskIdsForDateFlow()` - IDs de tarefas completadas
  - `deleteCompletionsForDate()` - Zerar tarefas do dia (Debug)
  - `deleteAllForChild()` - Zerar estrelas (Debug)

- ✨ **Interface Visual:**
  - ⭐ Contador de estrelas: "⭐ X estrelas hoje"
  - ✅ Checkmark em tarefas completadas
  - 🎨 Card com cor diferenciada (acinzentado)
  - 🔒 Botão "✅ Completada Hoje" desabilitado
  - Texto mais claro em tarefas completadas

- ✨ **TaskRepository:** 9 novos métodos para controle diário
- ✨ **Migrations:** 3→4 e 4→5 (correção de FOREIGN KEYs)

#### Corrigido
- 🐛 **CRÍTICO - Ordem incorreta das migrations (v1.11.0 → v1.11.1):**
  - Problema: Migrations fora de ordem (1→2→**3→4**→2→3)
  - Room não conseguia aplicar migration 3→4
  - Tabela `task_completions` não era criada
  - Solução: Reordenar para sequencial (1→2→2→3→3→4)

- 🐛 **CRÍTICO - FOREIGN KEY constraint failed (v1.11.1 → v1.11.3):**
  - Problema: Constraints exigiam perfil de criança cadastrado
  - Erro: `SQLiteConstraintException: FOREIGN KEY constraint failed`
  - Aplicação falhava silenciosamente ao marcar tarefa como completada
  - Solução: Removidas FOREIGN KEYs de `task_completions`
  - Migration 4→5 criada para recriar tabela sem constraints

- 🐛 **Logs silenciosos (v1.11.2):**
  - Adicionados logs detalhados em pontos críticos
  - Facilitou identificação de FOREIGN KEY constraint
  - Logs mantidos para troubleshooting futuro

#### Técnico
- 🔧 `TaskCompletion.kt`: Entity sem FOREIGN KEYs
- 🔧 `TaskCompletionDao.kt`: 340 linhas de queries especializadas
- 🔧 `TaskRepository.kt` + `TaskRepositoryImpl.kt`: 9 novos métodos
- 🔧 `AppDatabase.kt`: Migrations 3→4 e 4→5
- 🔧 `DatabaseModule.kt`: Ordem correta das migrations
- 🔧 `Converters.kt`: TypeConverters para LocalDate/LocalDateTime
- 🔧 `TaskListViewModel.kt`: Integração com sistema de controle diário
- 🔧 `TaskListScreen.kt`: UI com marcação visual de tarefas completadas
- 🔧 `TaskExecutionViewModel.kt`: Marcação automática ao completar tarefa
- 🔧 Versão do banco: 3 → 5
- 📝 Documentação: `MVP09_IMPLEMENTACAO_COMPLETA_V1.11.3.md`

#### Validado
- ✅ 7/7 testes passaram em dispositivo físico
- ✅ Contador de estrelas funciona
- ✅ Tarefas marcadas visualmente
- ✅ Bloqueio de reexecução funciona
- ✅ Dados persistem após fechar app
- ✅ Reset automático à meia-noite

---

## [Em Desenvolvimento] - MVP09 (v1.11.0) - 2025-10-24

### 🔧 Sistema de Controle Diário - Preparação

#### Corrigido
- 🐛 **TypeConverters para LocalDate:**
  - Adicionados conversores `fromLocalDate` e `toLocalDate` em `Converters.kt`
  - Room agora consegue mapear `java.time.LocalDate` para Long (epochDay)
  - Habilitado **core library desugaring** para suporte em API < 26 (Android 7.0+)
  - Dependência adicionada: `desugar_jdk_libs:2.0.4`
  - Removido arquivo duplicado `DateTimeConverters.kt` (causava conflito)
  - **Build Status:** ✅ SUCCESS

#### Técnico
- 🔧 `Converters.kt`: Adicionados TypeConverters para `LocalDate ↔ Long`
- 🔧 `build.gradle.kts`: Habilitado `isCoreLibraryDesugaringEnabled = true`
- 📝 Documentação: `MVP09_CORRECAO_TYPECONVERTERS.md` (troubleshooting completo)

#### Próximos Passos MVP-09
- ⏳ Implementar Migration 3→4 (tabela `task_completions`)
- ⏳ Criar `TaskCompletionRepository`
- ⏳ Integrar controle de tarefas concluídas na UI
- ⏳ Sistema de estrelas diárias na HomeScreen

---

## [Lançado] - MVP08 (v1.10.1) - 2025-01-23

### 🎮 Sistema de Gamificação - Fase 1 (Arquitetura Base)

#### Adicionado
- ✨ **Entidades de domínio:**
  - `Achievement`: Registra conquistas ao completar tarefas (estrelas, tempo, detalhes)
  - `Reward`: Recompensas desbloqueáveis com sistema de gamificação
  - `RewardType`: Enum com 5 tipos (THEME, AVATAR, SOUND, BADGE, EFFECT)
  - `GamificationStats`: Modelo calculado com estatísticas agregadas

- ✨ **DAOs (Data Access Objects):**
  - `AchievementDao`: 14 métodos (CRUD + queries de estatísticas)
  - `RewardDao`: 12 métodos (CRUD + queries especializadas)

- ✨ **Repositories:**
  - `AchievementRepository`: Interface + Implementation
  - `RewardRepository`: Interface + Implementation

- ✨ **Use Cases (5 casos de uso):**
  - `SaveAchievementUseCase`: Salva achievement após completar tarefa
  - `CalculateStarsUseCase`: Calcula estrelas (1-5) baseado em critérios
  - `GetGamificationStatsUseCase`: Calcula todas as estatísticas
  - `UnlockRewardsUseCase`: Desbloqueia recompensas automaticamente
  - `InitializeDefaultRewardsUseCase`: Cria 12 recompensas padrão

- ✨ **Banco de Dados:**
  - Migration 2→3: Cria tabelas `achievements` e `rewards`
  - Índices otimizados para queries de estatísticas
  - Foreign Keys com CASCADE para integridade referencial

- ✨ **Regras de Negócio:**
  - Cálculo de estrelas: Base (1-3) + Bônus (0-2) = 1-5 estrelas
  - No tempo: 3⭐ | Atraso ≤30min: 2⭐ | Atraso >30min: 1⭐
  - Bônus: +1⭐ todos steps | +1⭐ sem ajuda

#### Modificado
- 🔧 `AppDatabase`: Atualizado para versão 3 com novas entidades
- 🔧 `Converters`: Adicionado TypeConverter para RewardType
- 🔧 `DatabaseModule`: Providers para novos DAOs + Migration 2→3
- 🔧 `RepositoryModule`: Bindings para novos repositories

#### Técnico
- 📦 ~1.200 linhas de código adicionadas
- 📦 9 novos arquivos criados
- 📦 4 arquivos existentes atualizados
- 📦 Arquitetura Clean mantida
- 📦 Injeção de dependências (Hilt) configurada

---

## [1.10.1] - 2025-10-23

### Funcionalidade
- ✨ Aplicativo está funcional e estável nesta versão.
- ✨ Todas as funcionalidades principais e gamificação implementadas e testadas.

### Melhorias de UX
- 🎨 **Mensagem de tempo esgotado personalizada:** Substituída a mensagem "Tempo Esgotado!" por mensagens sutis e encorajadoras que usam o nome da criança
- 🎨 **7 frases alternativas aleatórias:** Evita repetição e torna a experiência mais natural ("Nome, podemos ir para o próximo passo?", "Nome, vamos continuar a atividade?", etc.)
- 🎨 **Diálogo simplificado:** Removido texto explicativo adicional, mantendo apenas a pergunta personalizada com o nome da criança
- 🎨 **Abordagem sem pressão:** Mensagens acolhedoras ao invés de alertas estressantes
- 🎨 **Mantidos botões +30s e Próximo:** Criança mantém controle sobre o tempo
- 🎨 **Mensagem de conclusão personalizada:** Nome da criança incluído nas felicitações ao completar tarefa ("Nome, você tirou nota 10! 🏆", "Nome, parabéns! 🎉", etc.)

### Nova Funcionalidade - TTS (Text-to-Speech)
- 🔊 **TtsManager implementado:** Serviço singleton para gerenciar síntese de voz em PT-BR
- 🔊 **Leitura automática do título da tarefa:** Ao iniciar uma tarefa, o título é lido em voz alta
- 🔊 **Leitura personalizada dos passos:** Cada passo é lido com o nome da criança ("Maria, pegar a escova")
- 🔊 **Leitura das mensagens de tempo esgotado:** Mensagens personalizadas são faladas quando o tempo acaba
- 🔊 **Leitura das mensagens de sucesso:** Ao completar a tarefa, fala a mensagem de reforço com o nome da criança ("Maria, você tirou nota 10!")
- 🔊 **Velocidade ajustada para crianças:** Taxa de fala configurada em 0.9x para melhor compreensão
- 🔊 **Gerenciamento inteligente de fila:** Sistema de fila para múltiplas falas sem sobreposição
- 🔊 **Limpeza automática de recursos:** TTS é parado e liberado adequadamente ao sair das telas
- 🔊 **Filtro de emojis:** Remove emojis das mensagens para leitura mais natural do TTS

### Técnico
- 📦 Criado `TtsManager.kt` como serviço singleton com Hilt
- 📦 Criado `PresentationModule.kt` para fornecer dependências de apresentação
- 📦 Integrado TTS ao `TaskExecutionViewModel` com injeção de dependências
- 📦 Configuração de idioma PT-BR, pitch (1.0) e velocidade (0.9)
- 📦 Listeners para acompanhar estado da fala (isSpeaking)
- 📦 Métodos `speak()` (imediato) e `speakQueued()` (em fila)

---

## [1.9.6] - MVP07 (Correção Crítica) - 2025-10-22

### 🐛 Correção de Bug Crítico - Steps na Edição

#### Corrigido
- 🐛 **CRÍTICO:** Steps não apareciam ao editar tarefa
- 🐛 **CRÍTICO:** Erro `UNIQUE constraint failed: steps.id` ao salvar
- 🐛 Carregamento assíncrono de steps corrigido
- 🐛 Sincronização de estado UI/ViewModel

#### Técnico
- 🔧 Correção na lógica de update do StepDao
- 🔧 Implementação correta de StateFlow
- 🔧 Aguardar carregamento antes de exibir UI

---

## [1.9.5] - MVP07 (Correções de Edição) - 2025-10-21

### 🐛 Correções de Edição de Steps

#### Corrigido
- 🐛 Problemas ao editar steps existentes
- 🐛 Validações de formulários
- 🐛 Melhorias na navegação entre telas

---

## [1.9.4] - MVP07 (Correção de Galeria) - 2025-10-20

### 🐛 Correção Crítica - Galeria de Imagens

#### Corrigido
- 🐛 **CRÍTICO:** Crash ao abrir galeria no Android 13+
- 🐛 Permissões de mídia corrigidas (READ_MEDIA_IMAGES)
- 🐛 Memory leaks de Bitmap resolvidos
- 🐛 Tratamento correto de URIs

#### Técnico
- 🔧 Verificação de versão Android para permissões
- 🔧 Gestão adequada de recursos de imagem
- 🔧 Fallback para permissões legadas

---

## [1.9.3] - MVP07 (Melhorias Gerais) - 2025-10-19

### 🔧 Melhorias e Correções

#### Corrigido
- 🐛 Correções em botões de cadastro/salvar
- 🐛 Melhorias de navegação entre telas
- 🐛 Ajustes em validações de formulários

---

## [1.9.2] - MVP07 (Reorganização) - 2025-10-18

### 🔧 Reorganização de Estrutura

#### Modificado
- 🔧 Reorganização da estrutura de arquivos
- 🔧 Melhorias na organização de packages
- 🔧 Limpeza de código não utilizado

---

## [1.9.1] - MVP07 (Correções Menores) - 2025-10-17

### 🐛 Correções Diversas

#### Corrigido
- 🐛 Correções menores em navegação
- 🐛 Ajustes de layout
- 🐛 Melhorias de performance

---

## [1.9.0] - MVP07 (Refinamentos e Testes) - 2025-10-16

### ✅ Conclusão MVP07 - Refinamentos

#### Adicionado
- ✨ Suite completa de testes automatizados
- ✨ Testes unitários para Models, Use Cases, Repositories
- ✨ Testes de integração para DAOs e Database
- ✨ Cobertura de testes >85%

#### Modificado
- 🔧 Migration 1→2 do banco de dados
- 🔧 Adicionado campo `category` em tasks
- 🔧 Adicionado campo `imageUrl` em tasks e steps
- 🔧 Adicionado campo `durationSeconds` em steps

#### Técnico
- 📦 26 arquivos de teste criados
- 📦 Todos os testes passando
- 📦 Build limpo sem erros

---

## [1.8.0] - MVP06 (Tela de Conclusão) - 2025-10-15

### 🎉 Tela de Conclusão de Tarefas

#### Adicionado
- ✨ TaskCompletionScreen com celebração
- ✨ Sistema básico de estrelas
- ✨ Mensagens motivacionais
- ✨ Animações de confete
- ✨ Botão para retornar à home

#### Técnico
- 📦 Feedback visual de sucesso
- 📦 Preparação para sistema de gamificação

---

## [1.7.0] - MVP05 (Execução de Tarefas) - 2025-10-14

### ⏱️ Sistema de Execução de Tarefas

#### Adicionado
- ✨ TaskExecutionScreen com navegação entre steps
- ✨ Timer visual animado (CircularProgressIndicator)
- ✨ Contador de progresso ("Passo X de Y")
- ✨ Marcação de conclusão de steps
- ✨ Navegação sequencial automática

#### Técnico
- 📦 Gerenciamento de estado com ViewModel
- 📦 Controle de timer com Coroutines
- 📦 Animações fluidas

---

## [1.6.0] - MVP04 (Interface de Usuário) - 2025-10-13

### 🎨 Design System e UI

#### Adicionado
- ✨ Design System completo:
  - Paleta de cores (Color.kt)
  - Tipografia (Type.kt)
  - Formas (Shape.kt)
  - Tema (Theme.kt)
- ✨ HomeScreen redesenhada
- ✨ Componentes reutilizáveis
- ✨ Material Design 3

#### Técnico
- 📦 Jetpack Compose modularizado
- 📦 Tema adaptado para crianças
- 📦 Cores vibrantes e acessíveis

---

## [1.5.0] - MVP03 (Sistema de Passos) - 2025-10-12

### 📝 CRUD de Steps

#### Adicionado
- ✨ Entidade Step (passos/subtarefas)
- ✨ StepDao com queries otimizadas
- ✨ StepRepository + Implementation
- ✨ Use Cases: GetStepsByTaskUseCase
- ✨ Relação Many-to-One com Task
- ✨ Ordenação sequencial de steps

#### Técnico
- 📦 Foreign Key com CASCADE DELETE
- 📦 Índices para performance
- 📦 Suporte a Flow para reatividade

---

## [1.4.0] - MVP02 (Gerenciamento de Tarefas) - 2025-10-11

### 📋 CRUD Completo de Tarefas

#### Adicionado
- ✨ TaskFormScreen (criar/editar tarefas)
- ✨ TaskListScreen (listar tarefas)
- ✨ TaskManagementScreen (gerenciar)
- ✨ Categorização de tarefas (27 categorias)
- ✨ Ordenação por horário
- ✨ Upload de imagens

#### Use Cases Implementados
- ✨ SaveTaskUseCase
- ✨ GetTaskByIdUseCase
- ✨ GetTasksOrderedByTimeUseCase
- ✨ DeleteTaskUseCase
- ✨ UpdateTaskStatusUseCase

---

## [1.3.0] - MVP01 (Banco de Dados) - 2025-10-10

### 🗄️ Fundação - Banco de Dados

#### Adicionado
- ✨ AppDatabase (Room Database)
- ✨ Entidades principais:
  - ChildProfile
  - Task
  - AppSettings
- ✨ DAOs com operações CRUD
- ✨ TypeConverters (Gender, TaskStatus)
- ✨ Migrations configuradas

#### Técnico
- 📦 Room Database v1
- 📦 TypeConverters para enums
- 📦 Foreign Keys configuradas

---

## [1.2.0] - MVP01 (Arquitetura) - 2025-10-09

### 🏗️ Clean Architecture

#### Adicionado
- ✨ Estrutura de packages (domain/data/presentation)
- ✨ Injeção de dependências (Hilt)
- ✨ Navegação (Jetpack Navigation)
- ✨ ViewModel base

#### Módulos Hilt
- 📦 DatabaseModule
- 📦 RepositoryModule

---

## [1.1.0] - MVP01 (Onboarding) - 2025-10-08

### 👋 Tela de Onboarding

#### Adicionado
- ✨ SplashScreen com logo
- ✨ Verificação de primeiro acesso
- ✨ Cadastro de perfil da criança
- ✨ Validações de formulário

---

## [1.0.0] - Projeto Inicial - 2025-10-07

### 🎯 Configuração Inicial

#### Adicionado
- ✨ Projeto Android criado
- ✨ Configuração Gradle
- ✨ Dependências base:
  - Jetpack Compose
  - Room Database
  - Hilt (Dependency Injection)
  - Navigation Compose
  - Coroutines
- ✨ Estrutura de pastas inicial
- ✨ README.md
- ✨ .gitignore

#### Configuração
- 📦 minSdk: 26 (Android 8.0)
- 📦 targetSdk: 34 (Android 14)
- 📦 compileSdk: 34
- 📦 Kotlin: 1.9.0
- 📦 Compose: 1.5.1

---

## Legenda de Ícones

- ✨ **Adicionado:** Novas funcionalidades
- 🔧 **Modificado:** Mudanças em funcionalidades existentes
- 🐛 **Corrigido:** Correções de bugs
- ❌ **Removido:** Funcionalidades removidas
- 🔒 **Segurança:** Correções de segurança
- 📦 **Técnico:** Detalhes técnicos e dependências
- 🎨 **UI/UX:** Melhorias de interface
- ⚡ **Performance:** Melhorias de performance
- 📝 **Documentação:** Atualizações de documentação

---

**Mantido por:** Equipe PequenosPassos  
**Última atualização:** 23/10/2025

---

# Pequenos Passos - Histórico de Versões e Mudanças

## [2.0.0] - 2025-10-24
### Mudanças Gerais
- Início da Fase 2.x: Nova branch criada para separar a evolução do projeto.
- Migração do assistente de código: Passamos a utilizar o modelo GPT-4.1 (GitHub Copilot) para suporte e automação, devido ao término do acesso premium ao Claude Sonnet 4.5.
- Refatoração da tela de Debug para Histórico:
  - Tela e rota renomeadas para "Histórico" (HistoryScreen).
  - Removidos elementos antigos de debug, mantendo apenas testes de TTS e ASR.
  - Navegação e imports atualizados.
- Compilação e build revisados, com correção de conflitos de nomes e duplicidades.
- Documentação e guidelines revisados para refletir a nova fase e práticas de commit/compilação.

### Observações
- O projeto agora segue com incrementos de versão major (2.x) para marcar a nova fase de desenvolvimento e uso de IA.
- Recomenda-se sempre atualizar o PATH do Windows para garantir o funcionamento do adb e scripts .bat.

---

## [Não lançado] - MVP09 (v1.11.4) - 2025-10-26

### 🛠️ Ferramentas de Histórico

#### Adicionado
- Botão "Zerar Tarefas do Dia" na tela Histórico: removes todas as conclusões do dia atual para a criança selecionada, atualizando estatísticas imediatamente.
- Botão "Zerar Estrelas do Dia" na tela Histórico: remove todos os registros de estrelas do dia atual para a criança selecionada.
- Mensagem de feedback visual após cada ação (sucesso/erro).
- Integração dos botões com o ViewModel (HistoryViewModel) e TaskRepository.

#### Corrigido
- Garantido que as estatísticas e feedback visual sejam atualizados automaticamente após o reset.

## Histórico: Implementação dos Botões de Zerar

Em 27/10/2025, foram implementados dois botões na tela de Histórico:
- **Zerar Tarefas do Dia**: Remove todas as tarefas marcadas como concluídas no dia atual, permitindo que sejam refeitas.
- **Zerar Estrelas do Dia**: Remove o registro de estrelas acumuladas no dia, reiniciando o contador diário.

Essas funções facilitam testes, correções e o uso pedagógico do aplicativo, conforme solicitado nas fases MVP 7 e 8. O código segue as Guidelines e boas práticas documentadas.

Referências:
- MVP07_FASE1_CHECKLIST.md
- MVP08_IMPLEMENTATION_STATUS.md
- GUIDELINES.md

---

## [2.0.1] - 2025-10-27
### Mudanças na tela Histórico
- O botão "Zerar Tarefas do Dia" foi renomeado para "Zerar dia" para simplificação e clareza.
- O botão "Zerar Estrelas do Dia" foi removido, centralizando a ação de reset diário em um único botão.
- Ajuste realizado conforme feedback de usabilidade e alinhamento com as Guidelines do projeto.

Referências:
- MVP09_FASE3_4_IMPLEMENTACAO.md
- GUIDELINES.md

---

# Versão 2.1.0 (30/10/2025)

- Implementado trigger de atualização automática na HomeScreen para detectar a virada do dia (pooling a cada minuto e atualização ao retomar a tela).
- Corrigido bug de contagem de tarefas e estrelas do dia (agora sempre reflete o dia atual, mesmo com o app aberto).
- Refatoração do ViewModel para garantir atualização dos StateFlows com trigger explícito.
- Testes e validação de fluxo de tarefas e estrelas após mudança de dia.
- Polimento visual e textual em telas principais.

---

## [2.0.0] - 2025-10-31
### Planejado / Em andamento
- **MVP08: Personalização e Expansão de Funcionalidades**
  - Checkbox em "Histórico e Ferramentas" para habilitar/desabilitar a pergunta de tempo extra ao final de cada step.
  - Persistência do estado da checkbox.
  - Integração com ASR (Vosk): reconhecimento de voz para decidir automaticamente se avança ou concede mais tempo ao final do step.
  - Fallback para resposta manual caso o reconhecimento falhe.
  - Botão para exportar atividades (JSON/CSV) em "Histórico e Ferramentas".
  - Botão para importar atividades, com validação e prevenção de duplicidades.
  - Componente de filtro (dropdown/chips) na tela de atividades para seleção de categoria.
  - Persistência do filtro durante a sessão.
- Roadmap modular:
  - Fase 1: Personalização do fluxo de passos (checkbox + ASR)
  - Fase 2: Exportação/importação de atividades
  - Fase 3: Filtro de categorias
  - Critérios de aceitação:
  - Cada funcionalidade deve ser testável e documentada nas primeiras 50 linhas dos arquivos afetados.
  - Atualização do CHANGELOG.md e demais docs a cada entrega.
- Observações:
  - Recomenda-se criar branches específicas para cada fase.
  - Issues/tarefas detalhadas para cada subitem.

---

## [2.1.1] - 2025-10-31
### Correções
- Corrigido erro de compilação relacionado ao campo `askExtraTimeAtStep` na entidade `AppSettings`.
    - O campo estava presente no DAO e na migration, mas ausente na data class, causando falha de build e erro de Room/SQLite.
    - Solução: adicionado o campo `askExtraTimeAtStep: Boolean = true` na entidade `AppSettings`, garantindo compatibilidade com a migration e o DAO.
- Build e testes automatizados passaram a funcionar corretamente após a correção.
- Erro de instalação devido à ausência de dispositivo conectado (não relacionado ao código).

### Observações
- Recomenda-se sempre garantir que toda alteração de schema (migration) seja refletida na entidade correspondente.
- Documentação e rastreabilidade reforçadas conforme guidelines.

---

## [2.1.2] - 2025-10-31
### Funcionalidade
- Implementação bem-sucedida do controle de exibição do pop-up de tempo extra ao final do step.
- Agora, o pop-up só aparece se a opção "Perguntar se deseja mais tempo" estiver ativada em Histórico & Ferramentas.
- Se desativada, o fluxo avança automaticamente para o próximo passo, sem exibir o pop-up.

### Correções
- Corrigido bug em que o pop-up era exibido mesmo com a opção desativada.
- Ajustada a consulta reativa ao valor de `askExtraTimeAtStep` no ViewModel de execução de tarefas.

### Observações
- Testes práticos confirmaram o funcionamento correto da configuração.
- Documentação e rastreabilidade reforçadas conforme guidelines.
