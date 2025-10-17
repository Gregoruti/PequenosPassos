# 📋 MVP-07 FASE 3 - PLANEJAMENTO DE INTEGRAÇÃO

**Data de Criação:** 16/10/2025  
**Status:** 📋 **EM PLANEJAMENTO**  
**Branch:** `feature/mvp-07-fase3-integracao`  
**Fase Anterior:** Fase 2 - Componentes UI (✅ Completa)

---

## 🎯 OBJETIVO DA FASE 3

Integrar os **3 componentes UI** criados na Fase 2 nas **4 telas principais** do aplicativo, criando fluxos funcionais completos de criação, edição e execução de tarefas.

### Componentes Disponíveis (Fase 2):
- ✅ **CategoryPicker** - Seleção de categoria (27 opções em 6 grupos)
- ✅ **TimerInput** - Configuração de duração (5-600 segundos)
- ✅ **ImagePicker** - Captura/seleção de imagens

### Telas a Atualizar:
1. **TaskFormScreen** - Formulário de criação/edição de tarefas
2. **StepFormScreen** - Formulário de steps (dentro de TaskFormScreen)
3. **TaskListScreen** - Lista de tarefas com indicadores visuais
4. **TaskExecutionScreen** - Execução de tarefas com timer visual

---

## 📐 ARQUITETURA DA FASE 3

### Estrutura de Arquivos a Criar/Modificar:

```
presentation/
├── screens/
│   ├── taskform/
│   │   ├── TaskFormScreen.kt (MODIFICAR)
│   │   ├── TaskFormViewModel.kt (MODIFICAR)
│   │   └── TaskFormState.kt (CRIAR)
│   │
│   ├── stepform/
│   │   ├── StepFormDialog.kt (CRIAR)
│   │   └── StepItem.kt (CRIAR - componente reutilizável)
│   │
│   ├── tasklist/
│   │   ├── TaskListScreen.kt (MODIFICAR)
│   │   └── TaskCard.kt (MODIFICAR)
│   │
│   └── execution/
│       ├── TaskExecutionScreen.kt (MODIFICAR)
│       └── CircularTimer.kt (CRIAR - componente)
│
└── components/
    └── (já temos CategoryPicker, TimerInput, ImagePicker)

domain/
└── model/
    └── (Task e Step já atualizados na Fase 1)

data/
└── (DAOs já atualizados na Fase 1)
```

---

## 📋 ESCOPO DETALHADO

### 1️⃣ **TaskFormScreen** - Criação/Edição de Tarefas

**Objetivo:** Permitir criar e editar tarefas com todos os novos campos.

#### Componentes a Integrar:
- ✅ CategoryPicker (categoria da tarefa)
- ✅ ImagePicker (imagem principal que aparece no card)

#### Campos do Formulário:
```kotlin
TaskFormState {
    val title: String              // Existente
    val description: String         // Existente
    val time: String               // Existente (HH:mm)
    val stars: Int                 // Existente (1-5)
    val category: TaskCategory?    // NOVO - obrigatório
    val imageUrl: Uri?             // NOVO - opcional
    val steps: List<Step>          // Existente
}
```

#### Validações:
- [x] Título não vazio (já existe)
- [x] Horário válido HH:mm (já existe)
- [x] Estrelas 1-5 (já existe)
- [ ] **Categoria obrigatória** (NOVO)
- [ ] **Mínimo 1 step** (já existe, reforçar)
- [ ] ImageUrl opcional (sem validação necessária)

#### Layout Proposto:
```
TaskFormScreen
┌─────────────────────────────────┐
│ [←] Criar Tarefa                │
├─────────────────────────────────┤
│ Título:                         │
│ [________________]              │
│                                 │
│ Descrição:                      │
│ [________________]              │
│                                 │
│ Horário:                        │
│ [HH:mm] 🕐                      │
│                                 │
│ Estrelas:                       │
│ ⭐⭐⭐⭐⭐                        │
│                                 │
│ ✨ NOVO: Categoria              │
│ [🧼 Higiene Pessoal ▼]         │
│                                 │
│ ✨ NOVO: Imagem da Tarefa       │
│ [Preview da imagem]             │
│ [📷 Câmera] [🖼️ Galeria]       │
│                                 │
│ Steps:                          │
│ [+ Adicionar Step]              │
│ ┌─────────────────────────┐    │
│ │ Step 1: Pegar escova    │    │
│ │ ⏱️ 60s  🖼️ [imagem]    │    │
│ └─────────────────────────┘    │
│                                 │
│ [Cancelar] [Salvar]             │
└─────────────────────────────────┘
```

#### Funcionalidades:
- [ ] Exibir CategoryPicker para seleção
- [ ] Exibir ImagePicker para imagem principal
- [ ] Validar categoria antes de salvar
- [ ] Salvar category como String no banco
- [ ] Salvar imageUrl (se selecionada)
- [ ] Abrir StepFormDialog ao adicionar step

**Estimativa:** 2-3 horas

---

### 2️⃣ **StepFormDialog** - Criação/Edição de Steps

**Objetivo:** Dialog para adicionar/editar steps com imagem e timer.

#### Componentes a Integrar:
- ✅ ImagePicker (imagem do step - aparece na execução)
- ✅ TimerInput (duração do step em segundos)

#### Campos do Dialog:
```kotlin
StepFormState {
    val title: String              // Existente
    val order: Int                 // Existente (sequencial)
    val imageUrl: Uri?             // NOVO - opcional
    val durationSeconds: Int       // NOVO - padrão 60s
}
```

#### Validações:
- [x] Título não vazio (já existe)
- [ ] **DurationSeconds entre 5-600s** (NOVO)
- [ ] ImageUrl opcional (sem validação necessária)

#### Layout Proposto:
```
StepFormDialog
┌─────────────────────────────────┐
│ Adicionar Step                  │
├─────────────────────────────────┤
│ Descrição do Step:              │
│ [________________]              │
│                                 │
│ ✨ NOVO: Duração do Timer       │
│ ⏱️ 1 min                        │
│ [━━━━━●━━━━━] 5s ↔ 600s        │
│ [5s] [15s] [30s] [60s]          │
│ [90s] [120s] [300s] [600s]      │
│                                 │
│ ✨ NOVO: Imagem do Step         │
│ [Preview da imagem]             │
│ [📷 Câmera] [🖼️ Galeria]       │
│                                 │
│ [Cancelar] [Adicionar]          │
└─────────────────────────────────┘
```

#### Funcionalidades:
- [ ] Exibir TimerInput com valor padrão 60s
- [ ] Exibir ImagePicker para imagem do step
- [ ] Validar durationSeconds antes de adicionar
- [ ] Retornar Step completo para TaskFormScreen
- [ ] Suportar modo edição (pré-preencher campos)

**Estimativa:** 2-3 horas

---

### 3️⃣ **StepItem** - Componente de Exibição de Step

**Objetivo:** Componente reutilizável para exibir step na lista.

#### Layout Proposto:
```
StepItem (em TaskFormScreen)
┌─────────────────────────────────┐
│ 1. Pegar a escova de dentes     │
│ ⏱️ 60s  🖼️ [thumbnail]          │
│                    [✏️] [🗑️]   │
└─────────────────────────────────┘
```

#### Funcionalidades:
- [ ] Exibir número do step (order + 1)
- [ ] Exibir título do step
- [ ] Indicador de duração (⏱️ + tempo formatado)
- [ ] Indicador de imagem (🖼️ se houver imageUrl)
- [ ] Botão de editar
- [ ] Botão de remover
- [ ] Drag & drop para reordenar (opcional - futuro)

**Estimativa:** 1-2 horas

---

### 4️⃣ **TaskListScreen** - Lista com Indicadores Visuais

**Objetivo:** Exibir categoria e indicadores de imagem/timer nos cards.

#### Modificações no TaskCard:
```
TaskCard (expandido)
┌─────────────────────────────────┐
│ 🧼 08:30  ⭐⭐⭐⭐⭐            │
│ Escovar os dentes               │
│ 🏷️ Higiene Pessoal ← NOVO      │
│ 🖼️ 3 imagens  ⏱️ 3min ← NOVO   │
│ ─────────────────────────       │
│ ▶️ Executar                     │
│ ✖️ Cancelar                     │
└─────────────────────────────────┘
```

#### Funcionalidades:
- [ ] Exibir emoji da categoria
- [ ] Exibir nome da categoria
- [ ] Contador de imagens nos steps (🖼️ × N)
- [ ] Tempo total dos steps (⏱️ + soma em min)
- [ ] Thumbnail da imagem principal (se houver)

**Estimativa:** 1-2 horas

---

### 5️⃣ **TaskExecutionScreen** - Execução com Timer Visual

**Objetivo:** Exibir imagem do step e timer circular animado.

#### Componente a Criar:
- **CircularTimer** - Timer visual circular com animação

#### Layout Proposto:
```
TaskExecutionScreen
┌─────────────────────────────────┐
│ [←] Escovar os dentes           │
├─────────────────────────────────┤
│                                 │
│ ✨ NOVO: Imagem do Step         │
│   ┌─────────────────────┐       │
│   │                     │       │
│   │   [Imagem grande]   │       │
│   │                     │       │
│   └─────────────────────┘       │
│                                 │
│ ✨ NOVO: Timer Circular         │
│       ┌───────────┐             │
│       │           │             │
│       │    45     │             │
│       │           │             │
│       │ segundos  │             │
│       │           │             │
│       │  ═══●═══  │             │
│       └───────────┘             │
│                                 │
│ Passo 1 de 3                    │
│ Pegar a escova de dentes        │
│                                 │
│ [⏸️ Pausar]                     │
│                                 │
│ [Próximo Step →]                │
└─────────────────────────────────┘
```

#### CircularTimer - Features:
- [ ] Canvas circular com progress
- [ ] Texto central com segundos restantes
- [ ] Cores dinâmicas:
  - 🟢 Verde: > 60% do tempo
  - 🟡 Amarelo: 30-60% do tempo
  - 🔴 Vermelho: < 30% do tempo
- [ ] Animação suave (1s updates)
- [ ] Tamanho: 200dp × 200dp
- [ ] Botão pausar/retomar

#### TaskExecutionScreen - Modificações:
- [ ] Exibir imagem grande do step (70% da largura)
- [ ] Exibir CircularTimer
- [ ] Usar durationSeconds do step (não mais fixo)
- [ ] Alarme ao fim do timer
- [ ] Dialog: "✅ Concluir" ou "⏱️ +30s"

**Estimativa:** 3-4 horas

---

## 🧪 TESTES A CRIAR

### Testes Unitários:

#### TaskFormViewModel
- [ ] Validar categoria obrigatória
- [ ] Validar salvamento com category
- [ ] Validar salvamento com imageUrl
- [ ] Validar salvamento sem imageUrl (null)

#### StepFormState
- [ ] Validar durationSeconds entre 5-600s
- [ ] Validar imageUrl opcional
- [ ] Validar criação de step com todos os campos

#### CircularTimer
- [ ] Testar cálculo de progresso (0-100%)
- [ ] Testar mudança de cor (verde/amarelo/vermelho)
- [ ] Testar countdown
- [ ] Testar pause/resume

**Estimativa:** 2-3 horas  
**Total de testes esperados:** 20-25 novos testes

---

### Testes de Integração:

- [ ] Fluxo completo: Criar tarefa com categoria + imagem
- [ ] Fluxo completo: Adicionar step com imagem + timer
- [ ] Fluxo completo: Executar tarefa com timer visual
- [ ] Fluxo: Salvar e recuperar tarefa com novos campos

**Estimativa:** 1-2 horas

---

## 📦 DEPENDÊNCIAS E PRÉ-REQUISITOS

### Já Disponíveis (Fase 1 e 2):
- ✅ Task com campos category e imageUrl
- ✅ Step com campos imageUrl e durationSeconds
- ✅ Migration 3→4 aplicada
- ✅ DAOs atualizados
- ✅ CategoryPicker component
- ✅ TimerInput component
- ✅ ImagePicker component
- ✅ FileProvider configurado
- ✅ Permissões no manifest

### A Verificar/Criar:
- [ ] TaskFormScreen existe e está funcional
- [ ] TaskExecutionScreen existe e está funcional
- [ ] ViewModels para formulários
- [ ] Navigation entre telas
- [ ] Estados de UI (State classes)

---

## 🎨 DESIGN SYSTEM

### Componentes Visuais Novos:

#### StepItem Card
```kotlin
Card(
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
    modifier = Modifier.fillMaxWidth()
) {
    Row(horizontalArrangement = SpaceBetween) {
        Text("${step.order + 1}. ${step.title}")
        Row {
            if (step.imageUrl != null) Icon(Icons.Default.Image)
            Text(formatDuration(step.durationSeconds))
            IconButton(onClick = onEdit) { Icon(Icons.Default.Edit) }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete) }
        }
    }
}
```

#### CircularTimer
```kotlin
Canvas(modifier = Modifier.size(200.dp)) {
    // Círculo de fundo
    drawCircle(color = backgroundColor)
    
    // Arco de progresso
    drawArc(
        color = progressColor, // Verde/Amarelo/Vermelho
        startAngle = -90f,
        sweepAngle = progress * 360f,
        useCenter = false,
        style = Stroke(width = 12.dp)
    )
}

// Texto central
Text(
    text = "$remainingSeconds",
    style = MaterialTheme.typography.displayLarge,
    fontSize = 48.sp
)
```

### Cores Dinâmicas (CircularTimer):
- **Verde (> 60%):** `MaterialTheme.colorScheme.primary`
- **Amarelo (30-60%):** `Color(0xFFFFA726)` (Orange 400)
- **Vermelho (< 30%):** `MaterialTheme.colorScheme.error`

---

## 📅 CRONOGRAMA ESTIMADO

### Fase 3 - Total: **12-17 horas** (~2-3 dias de desenvolvimento)

| Tarefa | Estimativa | Prioridade |
|--------|-----------|-----------|
| 1. TaskFormScreen (integração) | 2-3h | 🔴 Alta |
| 2. StepFormDialog (criação) | 2-3h | 🔴 Alta |
| 3. StepItem (componente) | 1-2h | 🟡 Média |
| 4. TaskListScreen (indicadores) | 1-2h | 🟡 Média |
| 5. CircularTimer (componente) | 2-3h | 🔴 Alta |
| 6. TaskExecutionScreen (integração) | 1-2h | 🔴 Alta |
| 7. Testes Unitários | 2-3h | 🟡 Média |
| 8. Testes de Integração | 1-2h | 🟢 Baixa |
| 9. Documentação | 1h | 🟢 Baixa |

### Divisão Sugerida:

#### **Dia 1** (4-6 horas):
- TaskFormScreen + CategoryPicker
- TaskFormScreen + ImagePicker
- Validações

#### **Dia 2** (4-6 horas):
- StepFormDialog
- ImagePicker + TimerInput em Step
- StepItem componente

#### **Dia 3** (4-5 horas):
- CircularTimer componente
- TaskExecutionScreen
- TaskListScreen (indicadores)

#### **Dia 4** (opcional - polimento):
- Testes
- Correções
- Documentação

---

## 🔄 FLUXO DE TRABALHO

### Ordem de Implementação (Recomendada):

```
1. TaskFormScreen
   ↓
2. CategoryPicker integrado
   ↓
3. ImagePicker integrado (tarefa)
   ↓
4. StepFormDialog
   ↓
5. ImagePicker integrado (step)
   ↓
6. TimerInput integrado
   ↓
7. StepItem componente
   ↓
8. TaskListScreen (indicadores)
   ↓
9. CircularTimer componente
   ↓
10. TaskExecutionScreen
```

### Por que esta ordem?

1. **TaskFormScreen primeiro** - É o ponto de entrada, onde tarefas são criadas
2. **CategoryPicker depois** - Componente mais simples, validação já pronta
3. **ImagePicker (tarefa)** - Integração direta, sem dependências
4. **StepFormDialog** - Depende de TaskFormScreen estar pronto
5. **ImagePicker + TimerInput (step)** - Dois componentes juntos no dialog
6. **StepItem** - Para exibir steps criados
7. **TaskListScreen** - Usa dados já criados
8. **CircularTimer** - Componente complexo, deixar por último
9. **TaskExecutionScreen** - Integração final, usa tudo

---

## ✅ CRITÉRIOS DE SUCESSO

### Funcionalidades:
- [ ] Usuário consegue criar tarefa com categoria
- [ ] Usuário consegue adicionar imagem à tarefa
- [ ] Usuário consegue criar step com imagem
- [ ] Usuário consegue configurar timer do step (5-600s)
- [ ] Lista exibe emoji da categoria
- [ ] Lista exibe indicadores de imagem e tempo
- [ ] Execução exibe imagem do step
- [ ] Execução exibe timer circular animado
- [ ] Timer muda de cor conforme progresso
- [ ] Alarme toca ao fim do timer

### Qualidade:
- [ ] 20-25 testes novos passando
- [ ] Código compila sem erros
- [ ] Material 3 design consistente
- [ ] Dark mode funcional
- [ ] Formulários validam corretamente
- [ ] Dados persistem no banco

### Integração:
- [ ] CategoryPicker funciona em TaskFormScreen
- [ ] ImagePicker funciona em TaskFormScreen
- [ ] ImagePicker funciona em StepFormDialog
- [ ] TimerInput funciona em StepFormDialog
- [ ] CircularTimer funciona em TaskExecutionScreen
- [ ] Navegação entre telas fluida

---

## 🚧 RISCOS E MITIGAÇÕES

| Risco | Probabilidade | Impacto | Mitigação |
|-------|--------------|---------|-----------|
| TaskFormScreen não existe | Média | Alto | Criar do zero seguindo padrão MVP-05 |
| ViewModels desatualizados | Alta | Médio | Atualizar com novos campos |
| Complexidade do CircularTimer | Média | Médio | Usar Canvas API, exemplos existem |
| Permissões de imagem | Baixa | Baixo | Já tratado no ImagePicker |
| Performance do timer | Baixa | Médio | Usar LaunchedEffect, não bloquear UI |

---

## 📚 REFERÊNCIAS TÉCNICAS

### Componentes Compose a Usar:
- `Canvas` - Para CircularTimer
- `LaunchedEffect` - Para countdown do timer
- `Dialog` - Para StepFormDialog
- `LazyColumn` - Para lista de steps
- `Card` - Para StepItem
- `OutlinedTextField` - Para campos de texto

### APIs Android:
- `MediaPlayer` - Para alarme do timer (opcional)
- `Vibrator` - Para vibração no alarme (opcional)
- `FileProvider` - Já configurado para imagens

### Bibliotecas Já Disponíveis:
- Compose Material 3
- Coil (imagens)
- Hilt (DI)
- Room (database)
- Navigation Compose

---

## 📝 DOCUMENTAÇÃO A CRIAR

Ao final da Fase 3, criar:

1. **MVP07_FASE3_VALIDATION.md**
   - Validação de todas as integrações
   - Testes executados e resultados
   - Screenshots das telas
   - Fluxos completos documentados

2. **MVP07_FASE3_CHECKLIST.md**
   - Checklist de todas as tarefas
   - Status de cada integração
   - Próximos passos para Fase 4

3. **MVP07_INTEGRATION_GUIDE.md** (opcional)
   - Guia de como usar os componentes
   - Exemplos de código
   - Boas práticas

---

## 🎯 DEFINIÇÃO DE PRONTO (DoD)

Uma tarefa da Fase 3 está pronta quando:

- [ ] Código implementado e funcional
- [ ] Testes unitários criados (quando aplicável)
- [ ] Sem erros de compilação
- [ ] Material 3 design aplicado
- [ ] Dark mode funcional
- [ ] Comentários/KDoc quando necessário
- [ ] Testado manualmente no preview/emulador
- [ ] Commit realizado com mensagem descritiva
- [ ] Documentado no checklist de progresso

---

## 🚀 INÍCIO DA FASE 3

### Primeira Tarefa:
**Verificar e atualizar TaskFormScreen**

Passos:
1. Localizar arquivo TaskFormScreen.kt
2. Verificar estrutura atual
3. Identificar ViewModel associado
4. Planejar integração do CategoryPicker
5. Planejar integração do ImagePicker

### Comando para Começar:
```bash
# Já estamos na branch correta
git branch --show-current
# feature/mvp-07-fase3-integracao

# Verificar arquivos existentes
ls app/src/main/java/com/pequenospassos/presentation/screens/
```

---

## 📊 MÉTRICAS DE SUCESSO

Ao final da Fase 3, esperamos:

| Métrica | Meta |
|---------|------|
| Telas atualizadas | 4 |
| Componentes integrados | 3 |
| Novos componentes criados | 2-3 |
| Testes novos | 20-25 |
| Fluxos completos | 3 |
| Coverage estimado | >85% |
| Build status | SUCCESS |
| Errors | 0 |

---

## 🎉 CONCLUSÃO DO PLANEJAMENTO

A **Fase 3** está completamente planejada e documentada!

### Próximos Passos:
1. ✅ Planejamento criado e documentado
2. ⏳ Verificar arquivos existentes
3. ⏳ Começar por TaskFormScreen
4. ⏳ Integrar componentes um a um
5. ⏳ Criar testes
6. ⏳ Documentar resultados

### Status: PRONTO PARA IMPLEMENTAÇÃO 🚀

---

**Criado por:** GitHub Copilot AI Assistant  
**Data:** 16/10/2025  
**Versão:** 1.0  
**Branch:** feature/mvp-07-fase3-integracao  
**Fase Anterior:** Fase 2 - Componentes UI (✅ Completa)  
**Próxima Fase:** Fase 4 - Testes E2E e Validação

