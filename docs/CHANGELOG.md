# CHANGELOG - Pequenos Passos

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

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
