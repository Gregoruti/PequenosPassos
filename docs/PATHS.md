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
    ├── Botão "Cadastro" → OnboardingScreen (HUB) 🛡️
    │                      ├── Botão "Cadastro Criança"
    │                      │   └── Editar Perfil da Criança
    │                      │       (sempre visível para permitir alteração do perfil)
    │                      │
    │                      ├── Botão "Cadastro de Tarefa" → TaskFormScreen
    │                      │   └── Adicionar Tarefa
    │                      │       (sempre visível)
    │                      │
    │                      └── Botão "Editar Tarefa" → TaskListScreen (seleção) → TaskFormScreen ⭐ NOVO
    │                          └── Lista de tarefas para selecionar e editar
    │                              (protege crianças de edição acidental)
    │
    ├── Botão "Teste Rápido" → TaskExecutionScreen
    │                          └── Executar a última "Tarefa cadastrada"
    │                              (visível apenas se houver perfil e tarefas)
    │
    ├── Botão "Atividades" → TaskListScreen 👶
    │                         └── Lista de Tarefas
    │                             (visível apenas se houver perfil de criança - pois "pertence" a criança)
    │                             ├── Tocar na Tarefa → Tarefa "Abre" (expandida)
    │                             │   ├── Executar → TaskExecutionScreen
    │                             │   ├── Cancelar → Dialog de confirmação
    │                             │   └── Voltar → TaskListScreen
    │                             │   ❌ Editar REMOVIDO (movido para Hub de Cadastros)
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
- **Cadastro**: Sempre visível, abre OnboardingScreen que funciona como hub com **três submenu**:
  - "Cadastro Criança": Permite cadastrar/editar perfil (sempre disponível)
  - "Cadastro de Tarefa": Criar nova tarefa (sempre disponível)
  - "Editar Tarefa": Editar tarefas existentes (sempre disponível) ⭐ NOVO
- **Teste Rápido**: Visível apenas se houver perfil e tarefas cadastradas
- **Atividades**: Visível apenas se houver perfil de criança cadastrado
- **Debug**: Sempre visível (apenas em modo desenvolvimento)

**Novidades do MVP-07 e Segurança** 🛡️:
- OnboardingScreen agora funciona como um **hub de cadastros** com **TRÊS caminhos principais**
- **"Editar Tarefa" MOVIDO do TaskListScreen para o Hub de Cadastros** para evitar edições acidentais por crianças com TEA
- TaskListScreen: ao tocar em uma tarefa, ela "abre" (expande) mostrando apenas **"Executar"** e **"Cancelar"**
- **Separação clara** entre:
  - **🛡️ Área de Configuração** (Hub de Cadastros - OnboardingScreen): Para adultos
  - **👶 Área de Execução** (Atividades - TaskListScreen): Para crianças
- Melhora significativa na **acessibilidade** e **segurança** do aplicativo
- Referência visual detalhada: Ver **WIREFRAME 4** em `MVP07_WIREFRAMES.md` para interação expandida

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

#### 4.2.1 OnboardingScreen (HUB DE CADASTROS) ⭐ MODIFICADO
- **Rota**: `"onboarding"`
- **Função**: Hub central de cadastros - permite cadastrar/editar perfil e tarefas
- **Componentes**:
  - **Botão "Cadastro Criança"** (sempre visível)
    - Campo: Nome da criança
    - Campo: Data de nascimento (DatePicker)
    - Campo: Observações (opcional)
    - Botão "Salvar"
    - Botão "Cancelar"
  - **Botão "Cadastro de Tarefa"** → Navega para TaskFormScreen (sempre visível)
  - **Botão "Editar Tarefa"** → Navega para TaskListScreen (modo seleção) → TaskFormScreen ⭐ NOVO
- **Validações** (para perfil):
  - Nome obrigatório (mín. 2 caracteres)
  - Data válida
- **Navegação**:
  - Cadastro Criança (Salvar) → HomeScreen
  - Cadastro de Tarefa → TaskFormScreen
  - Editar Tarefa → TaskListScreen (seleção) → TaskFormScreen
  - Cancelar → HomeScreen
- **Justificativa de Segurança** 🛡️:
  - A opção "Editar Tarefa" está no Hub para evitar que crianças com TEA façam alterações acidentais
  - Separação clara entre área de configuração (adultos) e área de execução (crianças)
- **Status**: 📋 Planejado (MVP-07) - MODIFICADO para ser hub com 3 opções

#### 4.2.2 TaskFormScreen (COM IMAGENS E TIMER) ⭐ MODIFICADO
- **Rota**: `"task_form"` ou `"task_form/{taskId}"`
- **Função**: Criar ou editar tarefa com suporte a imagens e timer por step
- **Componentes**:
  - Campo: Nome da tarefa
  - Campo: Descrição
  - Campo: Horário (TimePicker - formato HH:mm)
  - Campo: Tipo de tarefa (Dropdown)
  - Campo: Número de estrelas (1-5)
  - **Lista de Steps** (adicionar/remover) com:
    - **🖼️ Imagem por step** (câmera ou galeria) ⭐ NOVO
    - **⏱️ Timer configurável** (15s a 600s) ⭐ NOVO
    - Slider visual para timer
    - Preview de imagem selecionada
  - Botão "Salvar"
  - Botão "Cancelar"
- **Validações**:
  - Nome obrigatório
  - Horário válido (HH:mm)
  - Estrelas entre 1 e 5
  - Pelo menos 1 step
  - **Timer entre 15 e 600 segundos** ⭐ NOVO
  - **Imagem redimensionada (max 1024px)** ⭐ NOVO
- **Navegação**:
  - Sucesso → Voltar (TaskListScreen ou HomeScreen)
  - Cancelar → Voltar
- **Status**: 📋 Planejado (MVP-07) - MODIFICADO com imagens e timer
- **Referência**: Ver WIREFRAME 2 em MVP07_WIREFRAMES.md

#### 4.2.3 TaskListScreen (COM EXPANSÃO, SEM EDIÇÃO) ⭐ MODIFICADO
- **Rota**: `"task_list"`
- **Função**: Listar todas as tarefas cadastradas com interação expandida (SOMENTE LEITURA E EXECUÇÃO)
- **Componentes**:
  - Lista de Cards de Tarefas
    - Nome da tarefa
    - Horário
    - Status (badge colorido)
    - Número de estrelas
    - **Indicador de imagens** (🖼️ × quantidade) ⭐ NOVO
    - **Tempo total do timer** ⭐ NOVO
  - FAB "Adicionar Tarefa"
  - Filtros (opcional)
- **Ações no Card** (ao tocar, a tarefa "abre"): ⭐ MODIFICADO
  - **Tarefa expande inline** mostrando opções:
    - ▶️ Executar → TaskExecutionScreen
    - ✖️ Cancelar → Dialog de confirmação
    - ❌ Editar → **REMOVIDO!** (movido para Hub de Cadastros)
- **Navegação**:
  - Card (toque) → Expande opções inline
  - FAB → TaskFormScreen
  - Executar → TaskExecutionScreen
  - ❌ Editar → NÃO DISPONÍVEL (acessar via Hub de Cadastros)
- **Justificativa de Segurança** 🛡️:
  - **Opção "Editar" REMOVIDA desta tela** para prevenir edições acidentais por crianças com TEA
  - Esta é uma tela de **execução e visualização**, não de configuração
  - Edição agora só é acessível através do Hub de Cadastros (OnboardingScreen)
  - Melhora a **acessibilidade** ao reduzir opções que podem confundir
- **Status**: 📋 Planejado (MVP-07) - MODIFICADO com expansão e sem edição
- **Referência**: Ver WIREFRAME 3 e 4 em MVP07_WIREFRAMES.md

#### 4.2.4 TaskExecutionScreen (COM TIMER VISUAL) ⭐ MODIFICADO
- **Rota**: `"task_execution/{taskId}"`
- **Função**: Executar uma tarefa passo a passo com timer circular animado
- **Componentes**:
  - Cabeçalho: Nome da tarefa
  - Progresso: Barra de progresso (steps concluídos/total)
  - Área principal:
    - **🖼️ Imagem grande do step** (70% largura, centralizada) ⭐ NOVO
    - **⏱️ Timer circular animado** (200dp) ⭐ NOVO
      - Cores dinâmicas (Verde > Amarelo > Vermelho)
      - Contagem regressiva visível
      - Animação suave
    - Descrição do step atual
    - Instruções de áudio (TTS)
  - Botões:
    - **⏸️ Pausar/Retomar Timer** ⭐ NOVO
    - "Concluir Step" (checkbox grande)
    - "Voltar Step" (se não for o primeiro)
    - "Pular"
    - "Cancelar Tarefa"
  - **Alarme ao fim do timer** (som + vibração) ⭐ NOVO
  - Rodapé: Estrelas ganhas
- **Fluxo**:
  1. Carregar tarefa e steps
  2. Exibir step atual com imagem
  3. **Iniciar timer automático** ⭐ NOVO
  4. TTS lê a descrição
  5. **Timer conta regressivamente** ⭐ NOVO
  6. **Ao fim do timer: alarme com opções** ⭐ NOVO
     - Concluir step
     - Adicionar +30s
  7. Usuário completa step
  8. Avançar para próximo step
  9. Ao finalizar → Tela de conclusão com estatísticas
- **Navegação**:
  - Conclusão → HomeScreen ou TaskListScreen
  - Cancelar → Voltar com confirmação
- **Status**: 📋 Planejado (MVP-07) - MODIFICADO com timer visual
- **Referência**: Ver WIREFRAME 4 em MVP07_WIREFRAMES.md

#### 4.2.5 TaskCompletionScreen (Opcional)
- **Rota**: `"task_completion/{taskId}"`
- **Função**: Exibir resultado da execução da tarefa
- **Componentes**:
  - Título: "Tarefa Concluída!" 🎉
  - Estatísticas:
    - Steps concluídos
    - **Tempo total real gasto** ⭐ NOVO
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
    composable("onboarding") { OnboardingScreen(navController) } // Hub de cadastros (3 opções)
    composable("task_form") { TaskFormScreen(navController) } // Com imagens e timer
    composable(
        "task_form/{taskId}",
        arguments = listOf(navArgument("taskId") { type = NavType.LongType })
    ) { backStackEntry ->
        TaskFormScreen(
            navController, 
            taskId = backStackEntry.arguments?.getLong("taskId")
        )
    }
    composable("task_list") { TaskListScreen(navController) } // Com expansão (sem edição)
    composable(
        "task_execution/{taskId}",
        arguments = listOf(navArgument("taskId") { type = NavType.LongType })
    ) { backStackEntry ->
        TaskExecutionScreen( // Com timer visual
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
- **Cadastro sempre acessível** → OnboardingScreen como hub ⭐ NOVO
- **Edição protegida** → Apenas via Hub de Cadastros 🛡️ ⭐ NOVO

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
- **Timer: Animação circular suave** ⭐ Implementado no MVP-07

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
    ├── Primeira vez → OnboardingScreen (Hub) → HomeScreen
    └── Já cadastrado → HomeScreen
```

---

## 11. Diagrama de Navegação Completo (MVP-07+) ⭐ ATUALIZADO

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
┌──────────────────┐ │
│ Onboarding (Hub) │ │ 🛡️ HUB DE CADASTROS
│ ┌──────────────┐ │ │
│ │Cadastro      │ │ │
│ │Criança       │ │ │
│ └──────────────┘ │ │
│ ┌──────────────┐ │ │
│ │Cadastro      │─┼─┼──→ TaskFormScreen (com imagens/timer)
│ │Tarefa        │ │ │
│ └──────────────┘ │ │
│ ┌──────────────┐ │ │
│ │Editar ⭐ NOVO│─┼─┼──→ TaskListScreen (seleção) → TaskFormScreen
│ │Tarefa        │ │ │    (protege edição acidental)
│ └──────────────┘ │ │
└──────┬───────────┘ │
       │             │
       ↓             ↓
┌─────────────────────┐
│     HomeScreen      │
└──┬───┬───┬────┬────┘
   │   │   │    │
   │   │   │    └──→ DebugScreen ──┬──→ TtsTestScreen
   │   │   │                        └──→ AsrTestScreen
   │   │   │
   │   │   └──→ TaskListScreen 👶 (SEM edição) ──→ TaskExecutionScreen (timer)
   │   │        Área da Criança
   │   │
   │   └──→ TaskExecutionScreen (quick test com timer)
   │
   └──→ OnboardingScreen 🛡️ (hub sempre acessível)
        Área de Configuração
```

---

## 12. Considerações de UX

### 12.1 Navegação Intuitiva
- Botões de ação claros e grandes (acessibilidade)
- Feedback visual em todas as ações
- Confirmação para ações destrutivas
- **Expansão inline de tarefas** (sem navegar para outra tela) ⭐ NOVO
- **Separação clara entre configuração e execução** 🛡️ ⭐ NOVO

### 12.2 Performance
- Lazy loading de telas
- Cache de dados de navegação
- Pré-carregamento de telas frequentes
- **Otimização de imagens** (redimensionamento automático) ⭐ NOVO
- **Timer eficiente** (coroutines, não bloqueia UI) ⭐ NOVO

### 12.3 Acessibilidade
- Suporte a TalkBack
- Navegação por teclado (quando aplicável)
- Semantic descriptions em todos os elementos
- **Timer visual de alto contraste** ⭐ NOVO
- **Feedback tátil (vibração) no alarme** ⭐ NOVO
- **Proteção contra edições acidentais** 🛡️ ⭐ NOVO

---

## 13. Componentes Reutilizáveis a Criar (MVP-07) ⭐ NOVO

### 13.1 ImagePicker
- Seleção de câmera ou galeria
- Preview de imagem
- Botão remover
- Redimensionamento automático

### 13.2 TimerInput
- Slider visual (15s - 600s)
- Dropdown com valores predefinidos
- Exibição do valor em segundos

### 13.3 CircularTimer
- Timer circular animado
- Cores dinâmicas baseadas no progresso
- Texto central com segundos restantes
- Botão pausar/retomar

### 13.4 StepItem
- Card de step com todos os campos
- Imagem, timer e descrição integrados
- Validações inline

---

## 14. Histórico de Mudanças

| Versão | Data       | MVP    | Mudanças                                              |
|--------|------------|--------|-------------------------------------------------------|
| 1.0    | 2025-10-10 | MVP-01 | Navegação básica inicial                              |
| 1.1    | 2025-10-15 | MVP-07 | Planejamento completo de navegação                    |
| 1.2    | 2025-10-16 | MVP-07 | ⭐ Hub de cadastros, imagens, timer                   |
| 1.3    | 2025-10-16 | MVP-07 | 🛡️ Segurança: Edição movida para Hub (anti-acidental)|

---

**Última atualização**: 2025-10-16  
**Versão do documento**: 1.3  
**Status**: Ativo - **MVP-07 em planejamento com foco em segurança e acessibilidade**

**Modificações aplicadas (2025-10-16)**:
- ✅ OnboardingScreen como hub de cadastros com **3 opções** (sempre acessível)
- ✅ TaskFormScreen com suporte a imagens e timer por step
- ✅ TaskListScreen com expansão inline de tarefas
- ✅ **🛡️ Opção "Editar" REMOVIDA da TaskListScreen** (proteção contra edição acidental)
- ✅ **🛡️ Opção "Editar" MOVIDA para Hub de Cadastros** (área protegida para adultos)
- ✅ TaskExecutionScreen com timer circular visual animado
- ✅ Componentes reutilizáveis detalhados
- ✅ **Separação clara: Área de Configuração (Hub) vs Área de Execução (Atividades)**

