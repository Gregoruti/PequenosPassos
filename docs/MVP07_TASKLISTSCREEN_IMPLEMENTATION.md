# TaskListScreen Implementation - MVP-07 Fase 3

## Resumo da Implementação

Este documento descreve a atualização completa da tela de lista de tarefas (TaskListScreen) para o projeto PequenosPassos, incluindo indicadores visuais avançados conforme planejamento da Fase 3 do MVP-07.

## Data
**18/10/2025**

## Status
✅ **IMPLEMENTADO**

---

## Arquivos Criados/Modificados

### 1. TaskListViewModel.kt
**Localização:** `app/src/main/java/com/pequenospassos/presentation/screens/tasklist/TaskListViewModel.kt`

**Descrição:** ViewModel que gerencia o carregamento e exibição de tarefas com metadados dos steps.

**Features implementadas:**
- ✅ Carregamento de todas as tarefas ordenadas por horário
- ✅ Cálculo de metadados para cada tarefa:
  - Contagem de steps
  - Contagem de imagens nos steps
  - Duração total (soma de todos os steps)
- ✅ Loading state durante carregamento
- ✅ Error handling com tratamento de exceções
- ✅ Reactive data com StateFlow

**Componentes principais:**
- `TaskListViewModel`: ViewModel com injeção via Hilt
- `TaskWithMetadata`: Data class que combina Task com metadados calculados
- `getFormattedDuration()`: Método helper para formatação de duração

**Exemplo de formatação:**
```kotlin
totalDurationSeconds = 150  →  "2 min 30 seg"
totalDurationSeconds = 45   →  "45 seg"
totalDurationSeconds = 120  →  "2 min"
```

---

### 2. TaskListScreen.kt (ATUALIZADO)
**Localização:** `app/src/main/java/com/pequenospassos/presentation/screens/tasklist/TaskListScreen.kt`

**Descrição:** Tela de lista de tarefas com indicadores visuais MVP-07 Fase 3.

**Melhorias Implementadas:**

#### 🎨 Layout Aprimorado do TaskCard

**Linha 1: Emoji + Título + Horário**
```
🧼 Escovar os dentes                    🕐 08:00
```
- Emoji da categoria (extraído de TaskCategory)
- Título da tarefa (ellipsis se muito longo)
- Horário agendado

**Linha 2: Nome da Categoria + Estrelas**
```
Higiene Pessoal                         ⭐⭐⭐
```
- Nome completo da categoria (ex: "Higiene Pessoal")
- Estrelas de recompensa (1-5)

**Linha 3: Indicadores de Steps** (somente se houver steps)
```
📝 3 passos    🖼️ × 2    ⏱️ 2 min 30 seg
```
- **📝 N passos**: Quantidade total de steps
- **🖼️ × N**: Quantidade de steps com imagem (oculto se = 0)
- **⏱️ Duração**: Tempo total formatado

**Linha 4: Descrição** (opcional)
```
Lavar as mãos, aplicar pasta...
```
- Máximo 2 linhas com ellipsis

**Linha 5: Botão de Execução**
```
┌──────────────────────────────┐
│  ▶️ Executar Tarefa           │
└──────────────────────────────┘
```
- Full width para facilitar toque
- Navega para TaskExecutionScreen

---

## Mudanças Importantes

### 🛡️ Segurança e Acessibilidade

**Botão "Editar" REMOVIDO da TaskListScreen**

**Justificativa:**
- Protege crianças com TEA de edições acidentais
- A edição agora só é acessível via Hub de Cadastros (OnboardingScreen)
- Melhora a experiência de uso para o público-alvo
- Segue as diretrizes de segurança do MVP-07 Panorama Geral

**Antes:**
```kotlin
Row {
    Button("▶️ Executar")
    OutlinedButton("✏️ Editar")  // ❌ REMOVIDO
}
```

**Depois:**
```kotlin
Button("▶️ Executar Tarefa", fullWidth)  // ✅ Único botão
```

---

## Integração de Dados

### Fluxo de Carregamento

```
TaskListViewModel
    ↓
TaskRepository.getAllTasksOrderedByTime()
    ↓
Para cada Task:
    ↓
StepRepository.getStepsByTask(taskId)
    ↓
Calcular metadados:
    - stepCount = steps.size
    - imageCount = steps.count { !imageUrl.isNullOrEmpty() }
    - totalDurationSeconds = steps.sumOf { durationSeconds }
    ↓
TaskWithMetadata
    ↓
StateFlow<List<TaskWithMetadata>>
    ↓
UI (collectAsState)
```

---

## Arquitetura

### Camadas Utilizadas

```
Presentation Layer
├── TaskListScreen (UI)
├── TaskListViewModel (Business Logic)
└── TaskWithMetadata (State Model)
    │
    └── Domain Layer
        ├── TaskRepository
        ├── StepRepository
        └── Models (Task, Step, TaskCategory)
            │
            └── Data Layer
                ├── TaskDao
                └── StepDao
```

---

## Indicadores Visuais Implementados

### 1️⃣ Categoria com Emoji
- ✅ Emoji visível no card (ex: 🧼, 🗣️, 📚)
- ✅ Nome da categoria em texto legível
- ✅ Suporte a todas as 27 categorias
- ✅ Fallback para categoria "Outros" se inválida

### 2️⃣ Contador de Steps
- ✅ Exibe quantidade total de passos
- ✅ Singular/Plural automático: "1 passo" vs "3 passos"
- ✅ Oculto se a tarefa não tem steps (não deveria acontecer)

### 3️⃣ Contador de Imagens
- ✅ Exibe quantidade de steps com imagem anexada
- ✅ Formato: "🖼️ × N"
- ✅ Oculto se nenhum step tem imagem (imageCount = 0)
- ✅ Conta apenas steps com imageUrl não-nulo e não-vazio

### 4️⃣ Duração Total
- ✅ Soma de durationSeconds de todos os steps
- ✅ Formato legível: "2 min 30 seg", "45 seg", "10 min"
- ✅ Cor destaque (primary) para chamar atenção
- ✅ Emoji ⏱️ para identificação visual rápida

---

## Navegação

### Rotas Integradas

**TaskListScreen:**
- Origem: HomeScreen → "Atividades"
- Rota: `task_list`

**Navegação a partir do TaskListScreen:**
- ✅ FAB (+) → `task_form` (criar nova tarefa)
- ✅ Botão "Executar Tarefa" → `task_execution/{taskId}`
- ✅ Botão "Voltar" (TopBar) → `navigateUp()`

**Edição de Tarefas:**
- ❌ NÃO disponível diretamente na TaskListScreen
- ✅ Disponível via Hub de Cadastros (OnboardingScreen → "Editar Tarefa")

---

## Estados da Tela

### 1. Loading
```
┌─────────────────┐
│                 │
│   ⏳ Loading    │
│  (Progress)     │
│                 │
└─────────────────┘
```

### 2. Empty State
```
┌─────────────────────────────┐
│          📝                 │
│                             │
│  Nenhuma tarefa cadastrada  │
│                             │
│  Adicione sua primeira      │
│  tarefa para começar!       │
│                             │
│   ┌──────────────────┐      │
│   │ + Adicionar      │      │
│   │   Tarefa         │      │
│   └──────────────────┘      │
└─────────────────────────────┘
```

### 3. Lista com Tarefas
```
┌─────────────────────────────┐
│ Atividades            [←]   │
├─────────────────────────────┤
│                             │
│  ┌────────────────────────┐ │
│  │ 🧼 Escovar dentes  🕐08│ │
│  │ Higiene Pessoal    ⭐⭐⭐│ │
│  │ 📝 3 passos 🖼️×2 ⏱️2min│ │
│  │ ▶️ Executar Tarefa     │ │
│  └────────────────────────┘ │
│                             │
│  ┌────────────────────────┐ │
│  │ 🗣️ Terapia Fono   🕐10│ │
│  │ Terapia Fono...    ⭐⭐│ │
│  │ 📝 5 passos 🖼️×1 ⏱️5min│ │
│  │ ▶️ Executar Tarefa     │ │
│  └────────────────────────┘ │
│                             │
│            [+]              │
└─────────────────────────────┘
```

---

## Validações e Tratamento de Erros

### ViewModel
- ✅ Try-catch no carregamento de tarefas
- ✅ Loading state enquanto busca dados
- ✅ Empty list em caso de erro
- ✅ Reatividade com Flow (auto-atualização)

### UI
- ✅ Verificação de lista vazia
- ✅ Verificação de stepCount antes de exibir indicadores
- ✅ Verificação de imageCount > 0 antes de exibir contador
- ✅ Fallback para categoria padrão (OUTROS)

---

## Testes Recomendados

### Unitários (ViewModel)
- [ ] Carregamento de tarefas com sucesso
- [ ] Tratamento de erro no carregamento
- [ ] Cálculo correto de metadados
- [ ] Formatação de duração (vários casos)
- [ ] Lista vazia

### UI (Compose)
- [ ] Exibição de loading state
- [ ] Exibição de empty state
- [ ] Exibição de lista com tarefas
- [ ] Indicadores condicionais (imagens, steps)
- [ ] Navegação para execução

### Integração
- [ ] Fluxo completo: carregamento → exibição → execução
- [ ] Atualização automática ao adicionar tarefa
- [ ] Emoji correto para cada categoria

---

## Dependências

**Repositories:**
- `TaskRepository` - getAllTasksOrderedByTime()
- `StepRepository` - getStepsByTask(taskId)

**Models:**
- `Task` - Entidade principal
- `Step` - Steps da tarefa
- `TaskCategory` - Enum com 27 categorias
- `CategoryGroup` - 6 grupos de categorias

**Compose:**
- Material 3 (Cards, Buttons, TopBar, FAB)
- Hilt Navigation Compose
- StateFlow + collectAsState

---

## Próximos Passos

### Fase 3 - Restante
- [ ] Atualizar OnboardingScreen para Hub de Cadastros
  - Opção 1: Cadastro Criança
  - Opção 2: Cadastro de Tarefa
  - Opção 3: Editar Tarefa (com lista de seleção)

### Fase 4 - Testes
- [ ] Testes unitários do TaskListViewModel
- [ ] Testes de UI do TaskListScreen
- [ ] Testes de integração completos
- [ ] Validação manual no dispositivo

### Melhorias Futuras (Pós-MVP-07)
- [ ] Filtro por categoria
- [ ] Ordenação customizada
- [ ] Busca de tarefas
- [ ] Thumbnail da imagem principal da tarefa
- [ ] Swipe actions (executar, editar via hub)

---

## Conformidade com Guidelines

✅ **Clean Architecture**: Separação clara presentation/domain/data  
✅ **SOLID**: Responsabilidade única por classe  
✅ **DRY**: Componentes reutilizáveis (EmptyState, TaskCard)  
✅ **KISS**: Simplicidade na UI e lógica  
✅ **Material 3**: Design System consistente  
✅ **Acessibilidade**: Botões grandes, emojis visuais  
✅ **Documentação**: KDoc em todos os componentes  

---

## Conclusão

A TaskListScreen foi **completamente atualizada** conforme o planejamento da Fase 3 do MVP-07, incluindo:

- ✅ Indicadores visuais de categoria (emoji + nome)
- ✅ Contador de steps e imagens
- ✅ Duração total formatada
- ✅ Remoção do botão "Editar" por segurança
- ✅ Layout otimizado e responsivo
- ✅ Integração completa com ViewModels e Repositories

**Status Final:** 🟢 **PRONTO PARA TESTES**

**Progresso MVP-07 Fase 3:** 100% (4/4 telas completas)
- ✅ TaskFormScreen
- ✅ HomeScreen
- ✅ TaskListScreen (AGORA COMPLETO)
- ✅ TaskExecutionScreen

**Próxima Etapa:** Transformar OnboardingScreen em Hub de Cadastros centralizado.

