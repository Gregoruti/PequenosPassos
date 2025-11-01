<!--
Arquivo: docs/MVP11_ESTADO_ATUAL_CONSOLIDADO.md
Tipo: Documentação de estado atual e roadmap
Objetivo: Consolidar o estado atual do projeto Pequenos Passos v2.1.0 e definir próximos passos
Correlações: CHANGELOG.md, GUIDELINES.md, SPECIFICATION_FOR_APP.md, todos os MVPs anteriores
Criado: 2025-11-01
Autor: GitHub Copilot - Claude Sonnet 4.5 (RETORNO - responsável até o final do projeto)
Última atualização: 2025-11-01

INSTRUÇÕES PARA CODE ASSISTANTS (AI):
Este documento serve como fonte única de verdade sobre o estado atual do projeto.
SEMPRE ler este documento antes de fazer alterações significativas.
SEMPRE atualizar este documento após implementações importantes.
Este header (50 primeiras linhas) contém informações críticas de rastreabilidade.

Histórico de mudanças recentes:
- 2025-11-01 (Claude Sonnet 4.5): RETORNO como Code Assistant principal
- 2025-11-01 (GPT-4.1): Transição temporária - Criação do documento consolidado (versão 2.1.0)
- Último commit funcional: v2.1.0 (versionCode 210)
- Status: 100% funcional, pronto para próxima fase
- Code Assistant: Claude Sonnet 4.5 (via GitHub Copilot) - Responsável até o final
-->

# MVP11 - ESTADO ATUAL CONSOLIDADO DO PROJETO PEQUENOS PASSOS

**Data da Revisão:** 01/11/2025  
**Versão Atual:** 2.1.0 (versionCode 210)  
**Status:** ✅ 100% Funcional - Pronto para Próxima Fase  
**AI Assistant:** Claude Sonnet 4.5 (GitHub Copilot) - RETORNO como responsável principal  
**Transição:** GPT-4.1 → Claude Sonnet 4.5 (volta definitiva até o final do projeto)

---

## 📋 ÍNDICE

1. [Resumo Executivo](#1-resumo-executivo)
2. [Arquitetura Atual](#2-arquitetura-atual)
3. [Funcionalidades Implementadas](#3-funcionalidades-implementadas)
4. [Estado do Banco de Dados](#4-estado-do-banco-de-dados)
5. [Telas Implementadas](#5-telas-implementadas)
6. [Testes e Validação](#6-testes-e-validação)
7. [Documentação Existente](#7-documentação-existente)
8. [Próximos Passos](#8-próximos-passos)
9. [Guia Rápido para Code Assistants](#9-guia-rápido-para-code-assistants)

---

## 1. RESUMO EXECUTIVO

### 1.1 O Que É o Projeto

**Pequenos Passos** é um aplicativo Android para auxiliar crianças com necessidades especiais (TEA, TDAH, dificuldades de aprendizagem) a executarem tarefas do dia a dia de forma estruturada, visual e interativa.

### 1.2 Estado Atual

- ✅ **Versão:** 2.1.0 (versionCode 210)
- ✅ **Status:** Totalmente funcional
- ✅ **Build:** SUCCESS (sem erros)
- ✅ **Testes:** Aprovados em dispositivo físico
- ✅ **Última compilação:** 01/11/2025
- ✅ **Branch:** main
- ✅ **Commits:** Sincronizados (local + remoto)

### 1.3 MVPs Completados

| MVP | Descrição | Status | Versão |
|-----|-----------|--------|--------|
| MVP-01 | Estrutura Base | ✅ Completo | 1.0.x |
| MVP-02 | Entidades | ✅ Completo | 1.1.x |
| MVP-03 | Database (Room) | ✅ Completo | 1.2.x |
| MVP-04 | Repositórios | ✅ Completo | 1.3.x |
| MVP-05 | Use Cases | ✅ Completo | 1.4.x |
| MVP-06 | Theme e Design System | ✅ Completo | 1.5.x |
| MVP-07 | Telas de Interface (Compose) | ✅ Completo | 1.9.x |
| MVP-08 | TTS (Text-to-Speech) | ✅ Completo | 1.10.x |
| MVP-09 | Sistema de Controle Diário | ✅ Completo | 1.11.x |
| MVP-10 | Histórico & Ferramentas | ✅ Completo | 2.0.x |
| **MVP-11** | **Configurações Avançadas** | ✅ Completo | **2.1.0** |

### 1.4 Últimas Implementações (v2.1.0)

#### ✅ Checkbox de Controle de Pop-up de Tempo Extra
- **Funcionalidade:** Checkbox em "Histórico & Ferramentas" que controla se o app pergunta por mais tempo ao final de cada step.
- **Persistência:** Salvo no banco de dados (AppSettings.askExtraTimeAtStep)
- **Comportamento:**
  - ✅ Ativado: Exibe pop-up "Deseja mais 30 segundos?"
  - ❌ Desativado: Avança automaticamente para o próximo step
- **Validado:** ✅ Funcionando corretamente em dispositivo físico

#### ✅ Correção Crítica: Room/Migration
- **Problema:** Campo `askExtraTimeAtStep` ausente na entidade `AppSettings`
- **Erro:** SQLiteException, FOREIGN KEY constraint failed
- **Solução:** Adicionado campo na data class com valor padrão `true`
- **Lição:** Sempre adicionar novos campos na entidade quando criar migration

---

## 2. ARQUITETURA ATUAL

### 2.1 Estrutura de Camadas (Clean Architecture)

```
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION                            │
│  - Screens (Compose UI)                                      │
│  - ViewModels                                                │
│  - Components (reutilizáveis)                                │
│  - Navigation                                                │
│  - TTS Manager                                               │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                        DOMAIN                                │
│  - Models (Task, Step, ChildProfile, etc)                    │
│  - Use Cases (business logic)                                │
│  - Repository Interfaces                                     │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                         DATA                                 │
│  - Repository Implementations                                │
│  - Database (Room)                                           │
│    ├── Entities                                              │
│    ├── DAOs                                                  │
│    ├── Converters                                            │
│    └── Migrations                                            │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 Tecnologias Principais

| Categoria | Tecnologia | Versão | Uso |
|-----------|-----------|--------|-----|
| Linguagem | Kotlin | 2.0.20 | Linguagem principal |
| UI | Jetpack Compose | - | Interface declarativa |
| Injeção de Dependências | Hilt/Dagger | - | DI |
| Banco de Dados | Room | - | Persistência local |
| Async | Coroutines + Flow | - | Programação assíncrona |
| Navegação | Navigation Compose | - | Navegação entre telas |
| TTS | Android TTS | - | Síntese de voz |
| ASR | Vosk | - | Reconhecimento de voz |

### 2.3 Arquivos Principais

```
app/src/main/java/com/pequenospassos/
├── presentation/
│   ├── screens/
│   │   ├── SplashScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── HomeViewModel.kt
│   │   ├── childregistration/
│   │   │   ├── ChildRegistrationScreen.kt
│   │   │   └── ChildRegistrationViewModel.kt
│   │   ├── tasklist/
│   │   │   ├── TaskListScreen.kt
│   │   │   └── TaskListViewModel.kt
│   │   ├── taskform/
│   │   │   ├── TaskFormScreen.kt
│   │   │   └── TaskFormViewModel.kt
│   │   ├── execution/
│   │   │   ├── TaskExecutionScreen.kt
│   │   │   └── TaskExecutionViewModel.kt
│   │   └── history/
│   │       ├── HistoryScreen.kt
│   │       └── HistoryViewModel.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   ├── utils/
│   │   └── TtsManager.kt
│   └── theme/
│       ├── Color.kt
│       ├── Type.kt
│       ├── Shape.kt
│       └── Theme.kt
├── domain/
│   ├── model/
│   │   ├── Task.kt
│   │   ├── Step.kt
│   │   ├── ChildProfile.kt
│   │   ├── AppSettings.kt
│   │   ├── TaskCompletion.kt
│   │   └── TaskExecutionCount.kt
│   ├── repository/
│   │   ├── TaskRepository.kt
│   │   ├── StepRepository.kt
│   │   ├── ChildProfileRepository.kt
│   │   ├── AppSettingsRepository.kt
│   │   └── TaskCompletionRepository.kt
│   └── usecase/
│       ├── SaveTaskUseCase.kt
│       ├── GetTaskByIdUseCase.kt
│       ├── DeleteTaskUseCase.kt
│       ├── SaveChildProfileUseCase.kt
│       └── [outros use cases...]
└── data/
    ├── database/
    │   ├── AppDatabase.kt
    │   ├── Converters.kt
    │   ├── dao/
    │   │   ├── TaskDao.kt
    │   │   ├── StepDao.kt
    │   │   ├── ChildProfileDao.kt
    │   │   ├── AppSettingsDao.kt
    │   │   └── TaskCompletionDao.kt
    │   └── entities/
    │       └── [mesmas classes do domain/model]
    └── repository/
        ├── TaskRepositoryImpl.kt
        ├── StepRepositoryImpl.kt
        ├── ChildProfileRepositoryImpl.kt
        ├── AppSettingsRepositoryImpl.kt
        └── TaskCompletionRepositoryImpl.kt
```

---

## 3. FUNCIONALIDADES IMPLEMENTADAS

### 3.1 Gestão de Perfil de Criança

**Status:** ✅ Completo

- Cadastro de perfil (nome, apelido, data de nascimento, observações)
- Seleção de foto de perfil (galeria/câmera)
- Edição de perfil
- Persistência no banco de dados
- **Tela:** ChildRegistrationScreen
- **ViewModel:** ChildRegistrationViewModel
- **Migration:** Campos birthDate e notes adicionados (v1.11.6)

### 3.2 Gestão de Tarefas

**Status:** ✅ Completo

- Criar tarefas personalizadas
- Editar tarefas existentes
- Deletar tarefas (com confirmação)
- 27 categorias organizadas em 5 grupos:
  - 🏠 Casa
  - 🧘 Autocuidado
  - 🎓 Escola
  - 🎨 Lazer
  - 🍽️ Alimentação
- Sistema de estrelas (1-5)
- **Tela:** TaskFormScreen
- **ViewModel:** TaskFormViewModel

### 3.3 Steps (Passos da Tarefa)

**Status:** ✅ Completo

- Adicionar múltiplos steps por tarefa
- Ordem sequencial de execução
- Descrição textual de cada passo
- **Imagem por step** (galeria/câmera)
  - Redimensionamento automático (max 1024px)
  - Preview de imagem
- **Timer configurável** (5-600 segundos)
  - Slider visual
  - Valores predefinidos
- Edição e remoção de steps
- **Tela:** TaskFormScreen (steps inline)

### 3.4 Execução de Tarefas

**Status:** ✅ Completo

- Modo guiado passo a passo
- Exibição de imagem do step
- Timer circular visual
  - Cores dinâmicas (Verde → Amarelo → Vermelho)
  - Animação suave
  - Controles de pausar/retomar
- **TTS (Text-to-Speech):**
  - Leitura do título da tarefa ao entrar
  - Leitura de cada step ("Nome da criança, [descrição do step]")
  - Mensagens de sucesso ao concluir ("Nome, você tirou nota 10!")
- **Pop-up de tempo extra** (configurável):
  - Pergunta se deseja mais 30 segundos
  - Pode ser desabilitado nas configurações
- Barra de progresso
- Feedback motivacional
- **Tela:** TaskExecutionScreen
- **ViewModel:** TaskExecutionViewModel

### 3.5 Sistema de Controle Diário

**Status:** ✅ Completo (MVP-09)

- Cada tarefa pode ser executada apenas 1x por dia
- Registro de conclusões com data/hora
- Reset automático à meia-noite
- Contador de estrelas diárias
- **Interface Visual:**
  - ⭐ Contador de estrelas: "⭐ X estrelas hoje"
  - ✅ Checkmark em tarefas completadas
  - 🎨 Card com cor diferenciada (acinzentado)
  - 🔒 Botão "✅ Completada Hoje" desabilitado
- **Entidade:** TaskCompletion
- **DAO:** TaskCompletionDao (15+ métodos)

### 3.6 Histórico & Ferramentas

**Status:** ✅ Completo (MVP-10)

#### 📊 Estatísticas Semanais
- **Média diária de estrelas** (últimos 7 dias)
- **Média diária de tarefas feitas** (últimos 7 dias)
- **Estrelas do dia** (contador atual)
- **Tarefas completadas hoje** (X/Y)

#### 🏆 Top 3 Atividades
- **Mais executadas** (últimos 7 dias)
  - Nome da tarefa + contagem
  - Ordenação decrescente
- **Menos executadas** (últimos 7 dias)
  - Inclui tarefas nunca realizadas (contagem = 0)
  - Ordenação crescente

#### 🔧 Ferramentas de Teste
- **Botão "Zerar Dia":**
  - Remove conclusões de tarefas de hoje
  - Atualiza estatísticas instantaneamente
- **Checkbox "Perguntar se deseja mais tempo":**
  - Controla pop-up de tempo extra
  - Persistido no banco de dados
- **Botão "Testar TTS":** Testa síntese de voz
- **Botão "Testar ASR":** Testa reconhecimento de voz

**Tela:** HistoryScreen  
**ViewModel:** HistoryViewModel

### 3.7 HomeScreen

**Status:** ✅ Completo

- Splash screen (5 segundos, pode ser pulado)
- Logo UNIVESP
- Versão do app visível (v2.1.0)
- Foto de perfil da criança (se cadastrada)
- Mensagem motivacional:
  - "Você tem X atividades para fazer hoje!"
  - "Você já tem X estrelas hoje ⭐"
- **Botões:**
  - 📝 Cadastro (perfil da criança)
  - 📋 Atividades (lista de tarefas)
  - 📊 Histórico & Ferramentas

**Tela:** HomeScreen  
**ViewModel:** HomeViewModel

---

## 4. ESTADO DO BANCO DE DADOS

### 4.1 Versão Atual

**Database Version:** 5

### 4.2 Tabelas

| Tabela | Entidade | Campos Principais | Status |
|--------|----------|-------------------|--------|
| `tasks` | Task | id, title, description, imageUrl, category, stars, targetTime, status | ✅ |
| `steps` | Step | id, taskId, title, description, imageUrl, timeLimit, orderIndex, isCompleted | ✅ |
| `child_profiles` | ChildProfile | id, name, birthDate, notes, profileImageUrl | ✅ |
| `app_settings` | AppSettings | id, childId, isFirstRun, askExtraTimeAtStep | ✅ |
| `task_completions` | TaskCompletion | id, taskId, childId, completedAt, date, starsEarned | ✅ |

### 4.3 Migrations Aplicadas

```kotlin
// Migration 1→2: Inicial (estrutura base)
// Migration 2→3: Adiciona campos imageUrl e category em tasks
// Migration 3→4: Cria tabela task_completions (controle diário)
// Migration 4→5: Remove FOREIGN KEYs de task_completions (correção)
// Migration adicionada: Adiciona birthDate e notes em child_profiles (v1.11.6)
// Migration adicionada: Adiciona askExtraTimeAtStep em app_settings (v2.1.0)
```

### 4.4 TypeConverters

```kotlin
class Converters {
    // LocalDateTime ↔ Long (milissegundos desde epoch)
    @TypeConverter fun fromLocalDateTime(dateTime: LocalDateTime?): Long?
    @TypeConverter fun toLocalDateTime(timestamp: Long?): LocalDateTime?
    
    // LocalDate ↔ Long (dias desde epoch)
    @TypeConverter fun fromLocalDate(date: LocalDate?): Long?
    @TypeConverter fun toLocalDate(epochDay: Long?): LocalDate?
    
    // TaskStatus ↔ String
    @TypeConverter fun fromTaskStatus(status: TaskStatus): String
    @TypeConverter fun toTaskStatus(value: String): TaskStatus
    
    // TaskCategory ↔ String
    @TypeConverter fun fromTaskCategory(category: TaskCategory): String
    @TypeConverter fun toTaskCategory(value: String): TaskCategory
}
```

**⚠️ REGRA CRÍTICA:** Todos os TypeConverters devem estar em um único arquivo (`Converters.kt`). NÃO criar múltiplos arquivos de converters para evitar conflitos.

---

## 5. TELAS IMPLEMENTADAS

### 5.1 Fluxo de Navegação

```
SplashScreen (5s ou clique)
    ↓
HomeScreen
    ├── Botão "Cadastro" → ChildRegistrationScreen
    ├── Botão "Atividades" → TaskListScreen
    │                           ├── Botão "+" → TaskFormScreen (criar)
    │                           ├── Card → TaskFormScreen (editar)
    │                           └── Card → TaskExecutionScreen (executar)
    └── Botão "Histórico" → HistoryScreen
                               ├── Botão "Testar TTS" → TtsTestScreen
                               └── Botão "Testar ASR" → AsrTestScreen
```

### 5.2 Telas Detalhadas

| Tela | Arquivo | Status | Descrição |
|------|---------|--------|-----------|
| Splash | SplashScreen.kt | ✅ | Logo UNIVESP + versão (5s) |
| Home | HomeScreen.kt | ✅ | Tela principal, navegação |
| Cadastro | ChildRegistrationScreen.kt | ✅ | Perfil da criança |
| Lista de Atividades | TaskListScreen.kt | ✅ | Cards de tarefas, contador de estrelas |
| Formulário de Tarefa | TaskFormScreen.kt | ✅ | Criar/editar tarefa + steps |
| Execução de Tarefa | TaskExecutionScreen.kt | ✅ | Modo guiado passo a passo |
| Histórico & Ferramentas | HistoryScreen.kt | ✅ | Estatísticas + debug |
| Teste TTS | TtsTestScreen.kt | ✅ | Teste de síntese de voz |
| Teste ASR | AsrTestScreen.kt | ✅ | Teste de reconhecimento de voz |

---

## 6. TESTES E VALIDAÇÃO

### 6.1 Status de Testes

- ✅ **Build:** SUCCESS (sem erros)
- ✅ **Testes Unitários:** Executados (via `.\gradlew test`)
- ✅ **Testes em Dispositivo Físico:** Aprovados
- ✅ **Anti-regressão:** MVPs 01-10 funcionando

### 6.2 Checklist de Validação (v2.1.0)

- [x] Código compila sem erros
- [x] Testes unitários passam
- [x] App instala em dispositivo físico
- [x] Cadastro de perfil funciona
- [x] Criação de tarefas funciona
- [x] Edição de tarefas funciona
- [x] Deleção de tarefas funciona
- [x] Execução de tarefas funciona
- [x] TTS funciona (leitura de título, steps, mensagens)
- [x] Timer funciona (visual, cores, controles)
- [x] Pop-up de tempo extra funciona (e pode ser desabilitado)
- [x] Sistema de controle diário funciona
- [x] Contador de estrelas funciona
- [x] Checkmark em tarefas completadas funciona
- [x] Histórico exibe estatísticas corretas
- [x] Top 3 atividades funciona
- [x] Botão "Zerar Dia" funciona
- [x] Checkbox de configuração persiste no banco
- [x] CHANGELOG atualizado
- [x] GUIDELINES atualizado
- [x] Documentação sincronizada

### 6.3 Scripts de Build/Teste

```powershell
# Sempre usar .\ antes dos comandos no PowerShell

# Compilar e testar
.\gradlew clean test assembleDebug

# Instalar em dispositivo
.\gradlew installDebug

# Ou usar scripts .bat prontos:
.\compilar_e_testar.bat
.\compilar_e_instalar.bat
```

**⚠️ ATENÇÃO:** PowerShell NÃO suporta `&&` e `&` para encadear comandos. Use ponto-e-vírgula (`;`) ou scripts `.bat`.

---

## 7. DOCUMENTAÇÃO EXISTENTE

### 7.1 Documentos Principais

| Documento | Descrição | Status |
|-----------|-----------|--------|
| **GUIDELINES.md** | Diretrizes e boas práticas | ✅ Atualizado |
| **CHANGELOG.md** | Histórico de mudanças | ✅ Atualizado |
| **SPECIFICATION_FOR_APP.md** | Especificação do app | ✅ Atualizado |
| **PROXIMOS_PASSOS_MVP08_A_MVP15.md** | Roadmap futuro | ✅ |
| **MVP11_ESTADO_ATUAL_CONSOLIDADO.md** | Este documento | ✅ Novo |

### 7.2 Documentos por MVP

- MVP07_*.md (múltiplos arquivos)
- MVP08_*.md
- MVP09_*.md (controle diário)
- MVP10_*.md (histórico)
- MVP11_*.md (configurações avançadas)

### 7.3 Relatórios Técnicos

- RELATORIO_TECNICO_ABNT_2025.md
- RELATORIO_TECNICO_FUNDAMENTACAO_ABNT.md
- RELATORIO_TECNICO_TESTES_MVP01_07.md

---

## 8. PRÓXIMOS PASSOS

### 8.1 MVP-12: Exportação/Importação de Atividades

**Prioridade:** ALTA  
**Estimativa:** 3-5 dias

#### Funcionalidades Planejadas
- Exportar tarefas para JSON
- Importar tarefas de JSON
- Validação de arquivo importado
- Tela de gerenciamento de backup
- Compartilhamento de atividades entre dispositivos

#### Arquivos a Criar/Modificar
- `presentation/screens/backup/BackupScreen.kt`
- `presentation/screens/backup/BackupViewModel.kt`
- `domain/usecase/ExportTasksUseCase.kt`
- `domain/usecase/ImportTasksUseCase.kt`
- `data/repository/BackupRepository.kt`

### 8.2 MVP-13: Filtro de Categorias em Atividades

**Prioridade:** MÉDIA  
**Estimativa:** 2-3 dias

#### Funcionalidades Planejadas
- Dropdown de seleção de categoria
- Filtrar tarefas por categoria selecionada
- Opção "Todas as Categorias"
- Persistir última categoria selecionada

#### Arquivos a Modificar
- `presentation/screens/tasklist/TaskListScreen.kt`
- `presentation/screens/tasklist/TaskListViewModel.kt`
- `domain/repository/AppSettingsRepository.kt` (salvar filtro)

### 8.3 MVP-14: ASR (Reconhecimento de Voz) em Pop-ups

**Prioridade:** BAIXA  
**Estimativa:** 5-7 dias

#### Funcionalidades Planejadas
- Integração de Vosk (já existe, mas não usado)
- Reconhecimento de voz no pop-up de tempo extra
- Comandos: "sim", "não", "mais tempo", "próximo"
- Timeout de escuta (5-10 segundos)
- Feedback visual durante reconhecimento

#### Arquivos a Criar/Modificar
- `presentation/utils/AsrManager.kt` (já existe, expandir)
- `presentation/screens/execution/TaskExecutionViewModel.kt` (integrar ASR)
- `presentation/screens/execution/TaskExecutionScreen.kt` (UI de escuta)

### 8.4 Polimentos e Melhorias

#### Curto Prazo (1-2 dias cada)
- Adicionar animações de transição entre telas
- Melhorar feedback visual de botões
- Adicionar sons de recompensa ao concluir tarefa
- Melhorar acessibilidade (content descriptions)

#### Médio Prazo (3-5 dias cada)
- Implementar modo escuro
- Adicionar tutorial interativo (primeira execução)
- Implementar notificações de lembrete de tarefas
- Adicionar gráficos de progresso semanal

---

## 9. GUIA RÁPIDO PARA CODE ASSISTANTS

### 9.1 Antes de Fazer Qualquer Alteração

**SEMPRE:**
1. Ler este documento (`MVP11_ESTADO_ATUAL_CONSOLIDADO.md`)
2. Ler `GUIDELINES.md` (especialmente seção de PowerShell e TypeConverters)
3. Ler `CHANGELOG.md` para entender mudanças recentes
4. Verificar versão atual em `app/build.gradle.kts`

### 9.2 Ao Adicionar Nova Funcionalidade

**CHECKLIST:**
- [ ] Criar branch: `git checkout -b feature/mvp-XX-nome-descritivo`
- [ ] Implementar funcionalidade (seguir Clean Architecture)
- [ ] Escrever testes unitários
- [ ] Executar build: `.\gradlew clean test assembleDebug`
- [ ] Validar em dispositivo físico
- [ ] Atualizar CHANGELOG.md
- [ ] Atualizar GUIDELINES.md (se aplicável)
- [ ] Atualizar este documento (MVP11_ESTADO_ATUAL_CONSOLIDADO.md)
- [ ] Atualizar headers (primeiras 50 linhas) dos arquivos modificados
- [ ] Incrementar versão em `app/build.gradle.kts`
- [ ] Atualizar versão em `SplashScreen.kt`
- [ ] Fazer commit com mensagem semântica
- [ ] Push para repositório remoto
- [ ] Merge para `main` após validação

### 9.3 Ao Fazer Alterações no Banco de Dados

**⚠️ CRÍTICO:**
1. Criar nova migration em `AppDatabase.kt`
2. **SEMPRE** adicionar campos novos nas data classes correspondentes
3. Atualizar `Converters.kt` se necessário (NÃO criar novo arquivo)
4. Incrementar versão do banco em `AppDatabase`
5. Testar migration em dispositivo real (desinstalar/reinstalar se necessário)

**Exemplo de erro comum:**
```kotlin
// ❌ ERRADO: Criar migration mas esquecer de adicionar campo na data class
// Migration 5→6: ALTER TABLE app_settings ADD COLUMN newField INTEGER NOT NULL DEFAULT 0
// AppSettings.kt: NÃO adicionou newField → ERRO!

// ✅ CORRETO: Adicionar campo na data class também
// Migration 5→6: ALTER TABLE app_settings ADD COLUMN newField INTEGER NOT NULL DEFAULT 0
// AppSettings.kt: data class AppSettings(..., val newField: Boolean = false)
```

### 9.4 Ao Trabalhar com PowerShell no Windows

**⚠️ REGRAS CRÍTICAS:**
1. **NUNCA** usar `&&` ou `&` para encadear comandos
2. **SEMPRE** usar ponto-e-vírgula (`;`) ou scripts `.bat`
3. **SEMPRE** usar `.\` antes de executar `.bat` ou `gradlew`

**Exemplos:**
```powershell
# ❌ ERRADO
git add . && git commit -m "mensagem" && git push
.\gradlew clean && .\gradlew build

# ✅ CORRETO
git add .; git commit -m "mensagem"; git push
.\gradlew clean; .\gradlew build

# ✅ MELHOR (usar scripts .bat)
.\commit_v2.1.0.bat
.\compilar_e_testar.bat
```

### 9.5 Estrutura de Commit Recomendada

```
tipo(escopo): descrição curta (max 50 chars)

Descrição detalhada (opcional, quebrar linhas em 72 chars)

Mudanças principais:
- Item 1
- Item 2
- Item 3

Arquivos modificados:
- arquivo1.kt
- arquivo2.kt
- docs/CHANGELOG.md

Testes: [OK/PENDENTE]
Build: [SUCCESS/FAIL]
Status: MVP-XX - YY% completo

Closes #issue-number (se aplicável)
```

**Tipos de commit:**
- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `docs`: Atualização de documentação
- `test`: Adição/correção de testes
- `refactor`: Refatoração de código
- `style`: Formatação, indentação
- `chore`: Tarefas de manutenção

### 9.6 Incremento de Versão

**Regras:**
- **MAJOR** (X.0.0): Breaking changes, mudanças incompatíveis
- **MINOR** (x.Y.0): Novas funcionalidades (MVPs completos)
- **PATCH** (x.y.Z): Correções de bugs, melhorias pequenas

**Onde atualizar:**
1. `app/build.gradle.kts`:
   - `versionCode` (número sequencial, sempre incrementar)
   - `versionName` (X.Y.Z)
2. `SplashScreen.kt`: Exibição visual da versão

**Exemplo:**
```kotlin
// app/build.gradle.kts
versionCode = 211  // 210 + 1
versionName = "2.1.1"

// SplashScreen.kt
Text(text = "Versão 2.1.1")
```

### 9.7 Documentação - Header de Rastreabilidade

**Sempre atualizar as primeiras 50 linhas dos arquivos principais com:**
- Nome do arquivo e caminho
- Tipo (ViewModel, Screen, Repository, etc)
- Objetivo
- Correlações com outros arquivos
- Histórico de alterações (últimas 3-5 mudanças)
- Data da última modificação
- Autor/AI Assistant responsável

**Template:**
```kotlin
/**
 * Arquivo: caminho/completo/do/arquivo.kt
 * Tipo: [ViewModel/Screen/Repository/UseCase/etc]
 * Objetivo: Descrição breve do propósito do arquivo
 * Correlações: arquivo1.kt, arquivo2.kt, docs/MVPXX_*.md
 * 
 * Histórico de alterações:
 * - 2025-11-01 (GPT-4.1): Adicionado campo X, corrigido bug Y
 * - 2025-10-30 (Claude Sonnet): Implementação inicial
 * 
 * Última atualização: 2025-11-01
 * Autor: GitHub Copilot GPT-4.1
 * 
 * Status: Funcional, testado em v2.1.0
 * Build: SUCCESS
 * Testes: OK
 * 
 * ATENÇÃO: Este arquivo é crítico para [funcionalidade X]
 * Sempre atualizar [arquivo relacionado] ao modificar este arquivo.
 */
```

### 9.8 Troubleshooting Rápido

#### Erro de Compilação
1. `.\gradlew clean`
2. Verificar erros no log
3. Consultar `GUIDELINES.md` seção de erros conhecidos
4. Verificar migrations se erro de Room/SQLite

#### App Não Instala/Não Atualiza
1. Desinstalar app do dispositivo
2. `.\gradlew clean`
3. `.\gradlew assembleDebug installDebug`
4. Verificar logs: `adb logcat | findstr "pequenospassos"`

#### Database Error
1. Desinstalar app (limpa database)
2. Verificar ordem das migrations em `DatabaseModule.kt`
3. Verificar se campos novos estão nas data classes
4. Reinstalar app

#### PowerShell Não Reconhece Comando
1. Sempre usar `.\` antes do comando
2. Verificar se está no diretório correto (`D:\Softwares\PequenosPassos`)
3. Usar `cd` para navegar se necessário

---

## 10. CONCLUSÃO

### 10.1 Status Atual

O projeto **Pequenos Passos v2.1.0** está em um estado sólido e funcional:

- ✅ Arquitetura Clean bem definida
- ✅ Funcionalidades core implementadas
- ✅ Testes validados
- ✅ Documentação sincronizada
- ✅ Pronto para próximas fases de desenvolvimento

### 10.2 Próximas Prioridades

1. **MVP-12:** Exportação/Importação de Atividades
2. **MVP-13:** Filtro de Categorias
3. **Polimentos:** Animações, sons, melhorias visuais

### 10.3 Lição Aprendida

**Transição de AI Assistant (Claude Sonnet 4.5 → GPT-4.1):**
- Documentação consolidada é essencial para continuidade
- Headers de rastreabilidade facilitam retomada de contexto
- Seguir guidelines rigorosamente previne regressões

---

**FIM DO DOCUMENTO**

**Última Atualização:** 01/11/2025 - GPT-4.1  
**Próxima Revisão:** Após MVP-12 ou mudança significativa  
**Responsável:** GitHub Copilot (AI Assistant)

