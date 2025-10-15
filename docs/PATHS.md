# PATHS - Pequenos Passos

## Documentação de Navegação e Fluxos do Aplicativo

---

## 1. Visão Geral

Este documento descreve todos os caminhos de navegação, telas e fluxos de usuário do aplicativo Pequenos Passos.

---

## 2. Arquitetura de Navegação

### 2.1 Sistema Utilizado
- **Framework**: Jetpack Compose Navigation
- **Padrão**: Single Activity Architecture
- **Gerenciamento**: NavController centralizado

---

## 3. Fluxos de Navegação

### 3.1 Fluxo Atual (MVP-06)

```
SplashScreen (3s)
    ↓
HomeScreen
    ├── Botão "Cadastro" → [Em desenvolvimento]
    ├── Botão "Teste Rápido" → [Em desenvolvimento]
    ├── Botão "Atividades" → [Em desenvolvimento]
    └── Botão "Debug" → DebugScreen
                          ├── MVP-01: Estrutura Base ✅
                          ├── MVP-02: Entidades ✅
                          ├── MVP-03: Database ✅
                          ├── MVP-04: Repositórios ✅
                          ├── MVP-05: Use Cases ✅
                          ├── MVP-06: Theme ✅
                          ├── TTS Test → TtsTestScreen
                          └── ASR Test → AsrTestScreen
```

### 3.2 Fluxo Planejado (MVP-07+)

```
SplashScreen (3s)
    ↓
HomeScreen (v1.8.0)
    ├── Botão "Cadastrar Criança" → OnboardingScreen
    │                                └── Adicionar Perfil da Criança
    │                                    (visível apenas se não houver perfil)
    │
    ├── Botão "Nova Tarefa" → TaskFormScreen
    │                          └── Adicionar Tarefa
    │                              (visível apenas se houver perfil)
    │
    ├── Botão "Teste Rápido" → TaskExecutionScreen
    │                          └── Executar Tarefa Rápida
    │                              (visível apenas se houver perfil e tarefas)
    │
    ├── Botão "Atividades" → TaskListScreen
    │                         └── Lista de Tarefas
    │                             (visível apenas se houver perfil)
    │                             ├── Tocar na Tarefa → Menu de Ações
    │                             │   ├── Executar → TaskExecutionScreen
    │                             │   ├── Editar → TaskFormScreen (com taskId)
    │                             │   ├── Cancelar → Dialog de confirmação
    │                             │   └── Voltar → TaskListScreen
    │                             └── FAB (+) → TaskFormScreen (nova tarefa)
    │
    └── Botão "Debug" → DebugScreen
                          ├── MVP-01: Estrutura Base ✅
                          ├── MVP-02: Entidades ✅
                          ├── MVP-03: Database ✅
                          ├── MVP-04: Repositórios ✅
                          ├── MVP-05: Use Cases ✅
                          ├── MVP-06: Theme ✅
                          ├── TTS Test → TtsTestScreen
                          └── ASR Test → AsrTestScreen
```

**Observação importante**: Os botões da HomeScreen serão exibidos condicionalmente baseado no estado:
- Sem perfil cadastrado → Mostra apenas "Cadastrar Criança" e "Debug"
- Com perfil → Mostra "Nova Tarefa", "Atividades", "Teste Rápido" e "Debug"

---

## 4. Detalhamento de Telas

### 4.1 Telas Implementadas (MVP-01 a MVP-06)

#### 4.1.1 SplashScreen
- **Rota**: `"splash"`
- **Função**: Tela inicial de boas-vindas
- **Duração**: 3 segundos ou toque para pular
- **Navegação**: Automática para HomeScreen
- **Status**: ✅ Implementado

#### 4.1.2 HomeScreen
- **Rota**: `"home"`
- **Função**: Tela principal com menu de navegação
- **Componentes**:
  - Botão "Cadastro" (placeholder)
  - Botão "Teste Rápido" (placeholder)
  - Botão "Atividades" (placeholder)
  - Botão "Debug" (funcional)
- **Status**: ✅ Implementado (parcial)

#### 4.1.3 DebugScreen
- **Rota**: `"debug"`
- **Função**: Tela de desenvolvimento e testes
- **Componentes**:
  - Lista de status de MVPs
  - Botões para testes de TTS e ASR
- **Navegação**:
  - → TtsTestScreen
  - → AsrTestScreen
  - ← Voltar para HomeScreen
- **Status**: ✅ Implementado

#### 4.1.4 TtsTestScreen
- **Rota**: `"tts_test"`
- **Função**: Testar funcionalidade Text-to-Speech
- **Componentes**:
  - Campo de texto para entrada
  - Botão "Falar"
  - Botão voltar
- **Status**: ✅ Implementado

#### 4.1.5 AsrTestScreen
- **Rota**: `"asr_test"`
- **Função**: Testar funcionalidade de reconhecimento de voz
- **Componentes**:
  - Área de exibição de texto reconhecido
  - Botão "Iniciar/Parar gravação"
  - Botão voltar
- **Status**: ✅ Implementado

---

### 4.2 Telas Planejadas (MVP-07)

#### 4.2.1 OnboardingScreen
- **Rota**: `"onboarding"`
- **Função**: Cadastro inicial do perfil da criança
- **Componentes**:
  - Campo: Nome da criança
  - Campo: Data de nascimento (DatePicker)
  - Campo: Observações (opcional)
  - Botão "Salvar"
  - Botão "Cancelar"
- **Validações**:
  - Nome obrigatório (mín. 2 caracteres)
  - Data válida
- **Navegação**:
  - Sucesso → HomeScreen
  - Cancelar → HomeScreen
- **Status**: 📋 Planejado (MVP-07)

#### 4.2.2 TaskFormScreen
- **Rota**: `"task_form"` ou `"task_form/{taskId}"`
- **Função**: Criar ou editar tarefa
- **Componentes**:
  - Campo: Nome da tarefa
  - Campo: Descrição
  - Campo: Horário (TimePicker - formato HH:mm)
  - Campo: Tipo de tarefa (Dropdown)
  - Campo: Número de estrelas (1-5)
  - Lista de Steps (adicionar/remover)
  - Botão "Salvar"
  - Botão "Cancelar"
- **Validações**:
  - Nome obrigatório
  - Horário válido (HH:mm)
  - Estrelas entre 1 e 5
  - Pelo menos 1 step
- **Navegação**:
  - Sucesso → Voltar (TaskListScreen ou HomeScreen)
  - Cancelar → Voltar
- **Status**: 📋 Planejado (MVP-07)

#### 4.2.3 TaskListScreen
- **Rota**: `"task_list"`
- **Função**: Listar todas as tarefas cadastradas
- **Componentes**:
  - Lista de Cards de Tarefas
    - Nome da tarefa
    - Horário
    - Status (badge colorido)
    - Número de estrelas
  - FAB "Adicionar Tarefa"
  - Filtros (opcional)
- **Ações no Card**:
  - Toque longo ou menu → Opções
    - Executar
    - Editar
    - Cancelar
    - Excluir (com confirmação)
- **Navegação**:
  - Card → Menu de ações
  - FAB → TaskFormScreen
  - Executar → TaskExecutionScreen
  - Editar → TaskFormScreen (com taskId)
- **Status**: 📋 Planejado (MVP-07)

#### 4.2.4 TaskExecutionScreen
- **Rota**: `"task_execution/{taskId}"`
- **Função**: Executar uma tarefa passo a passo
- **Componentes**:
  - Cabeçalho: Nome da tarefa
  - Progresso: Barra de progresso (steps concluídos/total)
  - Área principal:
    - Descrição do step atual
    - Imagem/mídia (se houver)
    - Instruções de áudio (TTS)
  - Botões:
    - "Concluir Step" (checkbox grande)
    - "Voltar Step" (se não for o primeiro)
    - "Pular"
    - "Cancelar Tarefa"
  - Rodapé: Estrelas ganhas
- **Fluxo**:
  1. Carregar tarefa e steps
  2. Exibir step atual
  3. TTS lê a descrição
  4. Usuário completa step
  5. Avançar para próximo step
  6. Ao finalizar → Tela de conclusão com estatísticas
- **Navegação**:
  - Conclusão → HomeScreen ou TaskListScreen
  - Cancelar → Voltar com confirmação
- **Status**: 📋 Planejado (MVP-07)

#### 4.2.5 TaskCompletionScreen (Opcional)
- **Rota**: `"task_completion/{taskId}"`
- **Função**: Exibir resultado da execução da tarefa
- **Componentes**:
  - Título: "Tarefa Concluída!" 🎉
  - Estatísticas:
    - Steps concluídos
    - Tempo total
    - Estrelas ganhas
  - Mensagem motivacional
  - Botão "Voltar"
- **Status**: 📋 Planejado (MVP-08)

---

## 5. Definição de Rotas

### 5.1 Rotas Implementadas
```kotlin
NavHost(navController, startDestination = "splash") {
    composable("splash") { SplashScreen(navController) }
    composable("home") { HomeScreen(navController) }
    composable("debug") { DebugScreen(navController) }
    composable("tts_test") { TtsTestScreen(navController) }
    composable("asr_test") { AsrTestScreen(navController) }
}
```

### 5.2 Rotas Planejadas (MVP-07)
```kotlin
NavHost(navController, startDestination = "splash") {
    // Rotas existentes
    composable("splash") { SplashScreen(navController) }
    composable("home") { HomeScreen(navController) }
    composable("debug") { DebugScreen(navController) }
    composable("tts_test") { TtsTestScreen(navController) }
    composable("asr_test") { AsrTestScreen(navController) }
    
    // Novas rotas MVP-07
    composable("onboarding") { OnboardingScreen(navController) }
    composable("task_form") { TaskFormScreen(navController) }
    composable(
        "task_form/{taskId}",
        arguments = listOf(navArgument("taskId") { type = NavType.LongType })
    ) { backStackEntry ->
        TaskFormScreen(
            navController, 
            taskId = backStackEntry.arguments?.getLong("taskId")
        )
    }
    composable("task_list") { TaskListScreen(navController) }
    composable(
        "task_execution/{taskId}",
        arguments = listOf(navArgument("taskId") { type = NavType.LongType })
    ) { backStackEntry ->
        TaskExecutionScreen(
            navController,
            taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
        )
    }
}
```

---

## 6. Estados de Navegação

### 6.1 Back Stack
- **SplashScreen**: Não volta (remove da pilha)
- **HomeScreen**: Raiz da navegação principal
- **Outras telas**: Podem voltar para tela anterior

### 6.2 Navegação Condicional
- Primeira execução → OnboardingScreen obrigatório
- Perfil já cadastrado → Direto para HomeScreen

---

## 7. Deep Links (Futuro)

Planejado para MVP-09+:
- `pequenospassos://task/{taskId}` → Abrir tarefa específica
- `pequenospassos://execute/{taskId}` → Executar tarefa diretamente

---

## 8. Animações de Transição (Futuro)

Planejado para MVP-08+:
- Slide: Telas principais
- Fade: Dialogs e overlays
- Scale: Modais

---

## 9. Navegação Bottom Bar (Futuro)

Considerado para MVP-10:
```
Bottom Navigation:
├── Home (ícone casa)
├── Atividades (ícone lista)
├── Adicionar (ícone +, central, elevado)
├── Estatísticas (ícone gráfico)
└── Perfil (ícone usuário)
```

---

## 10. Fluxo de Primeira Execução

```
App Launch
    ↓
SplashScreen (3s)
    ↓
CheckFirstRun (UseCase)
    ├── Primeira vez → OnboardingScreen → HomeScreen
    └── Já cadastrado → HomeScreen
```

---

## 11. Diagrama de Navegação Completo (MVP-07+)

```
┌─────────────────┐
│  SplashScreen   │
└────────┬────────┘
         │ (3s)
         ↓
    ┌────────────┐
    │ First Run? │
    └──┬─────┬───┘
       │Yes  │No
       ↓     ↓
┌──────────┐ │
│Onboarding│ │
└────┬─────┘ │
     │       │
     ↓       ↓
┌─────────────────────┐
│     HomeScreen      │
└──┬───┬───┬────┬────┘
   │   │   │    │
   │   │   │    └──→ DebugScreen ──┬──→ TtsTestScreen
   │   │   │                        └──→ AsrTestScreen
   │   │   │
   │   │   └──→ TaskListScreen ──┬──→ TaskExecutionScreen
   │   │                         └──→ TaskFormScreen (edit)
   │   │
   │   └──→ TaskExecutionScreen (quick)
   │
   └──→ TaskFormScreen (new)
```

---

## 12. Considerações de UX

### 12.1 Navegação Intuitiva
- Botões de ação claros e grandes (acessibilidade)
- Feedback visual em todas as ações
- Confirmação para ações destrutivas

### 12.2 Performance
- Lazy loading de telas
- Cache de dados de navegação
- Pré-carregamento de telas frequentes

### 12.3 Acessibilidade
- Suporte a TalkBack
- Navegação por teclado (quando aplicável)
- Semantic descriptions em todos os elementos

---

## 13. Histórico de Mudanças

| Versão | Data       | MVP    | Mudanças                          |
|--------|------------|--------|-----------------------------------|
| 1.0    | 2025-10-10 | MVP-01 | Navegação básica inicial          |
| 1.1    | 2025-10-15 | MVP-07 | Planejamento completo de navegação|

---

**Última atualização**: 2025-10-15  
**Versão do documento**: 1.1  
**Status**: Ativo e em expansão
