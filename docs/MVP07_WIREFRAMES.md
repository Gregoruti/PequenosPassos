# MVP-07 WIREFRAMES E DRAFTS

## Panorama Visual Completo das Telas

**Data**: 2025-10-15  
**Versão**: 1.0  
**MVP**: 07 - Telas de Interface com Imagens e Timer  

---

## 🎯 VISÃO GERAL DO MVP-07

### Objetivo
Implementar **4 telas principais** de interface do usuário com suporte a:
- ✅ **Imagens** nos steps (galeria/câmera)
- ✅ **Timer** configurável por step (15s-600s)
- ✅ **Timer visual** circular animado
- ✅ Navegação condicional
- ✅ Design System Material 3

### Telas a Desenvolver
1. **OnboardingScreen** - Cadastro da criança
2. **TaskFormScreen** - Criar/editar tarefas (com imagens e timer)
3. **TaskListScreen** - Listar tarefas
4. **TaskExecutionScreen** - Executar com timer visual

---

## 📱 WIREFRAME 1: OnboardingScreen

```
┌─────────────────────────────────────┐
│  ┌─┐ Pequenos Passos            │ ← TopBar
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│                                     │
│         👶                          │ ← Ícone grande
│   Vamos começar!                    │
│                                     │
│  Qual é o nome da criança?          │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Nome da criança...          │   │ ← TextField
│  └─────────────────────────────┘   │
│                                     │
│  Data de nascimento:                │
│  ┌─────────────────────────────┐   │
│  │ 📅 DD/MM/AAAA              │   │ ← DatePicker
│  └─────────────────────────────┘   │
│                                     │
│  Observações (opcional):            │
│  ┌─────────────────────────────┐   │
│  │ Ex: preferências, alergias │   │ ← TextField
│  │                             │   │   multiline
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │      💾 SALVAR              │   │ ← Primary Button
│  └─────────────────────────────┘   │
│                                     │
│        [ Cancelar ]                 │ ← Text Button
│                                     │
└─────────────────────────────────────┘
```

### Características:
- **Layout**: Column centralizado
- **Validações**: 
  - Nome mínimo 2 caracteres
  - Data válida no passado
- **Navegação**: 
  - Salvar → HomeScreen (com perfil criado)
  - Cancelar → HomeScreen
- **Cores**: Background claro, Primary Button roxo

---

## 📱 WIREFRAME 2: TaskFormScreen (COM IMAGENS E TIMER!)

```
┌─────────────────────────────────────┐
│  ← Nova Tarefa              [✓]    │ ← TopBar com voltar e salvar
└─────────────────────────────────────┘

┌─────────────────────────────────────┐ ← ScrollableColumn
│  INFORMAÇÕES DA TAREFA              │
│                                     │
│  Nome da tarefa *                   │
│  ┌─────────────────────────────┐   │
│  │ Ex: Escovar os dentes       │   │
│  └─────────────────────────────┘   │
│                                     │
│  Descrição                          │
│  ┌─────────────────────────────┐   │
│  │ Após o café da manhã        │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌────────────┐  ┌─────────────┐   │
│  │ 🕐 08:30   │  │ ⭐⭐⭐⭐⭐  │   │ ← Time + Stars
│  └────────────┘  └─────────────┘   │
│                                     │
│  Tipo: [Manhã ▼]                    │
│                                     │
├─────────────────────────────────────┤
│  PASSOS (STEPS)                     │
├─────────────────────────────────────┤
│                                     │
│  Step 1 ┌───────────────────┐ [✕]  │
│         │ Molhar a escova   │      │
│         └───────────────────┘      │
│         🖼️ [Adicionar Imagem]       │ ← NOVO! Image Picker
│         ⏱️ Timer: [60s ▼]           │ ← NOVO! Timer Input
│         ────────────────────────    │
│         15s ═════●═══════ 600s      │ ← NOVO! Slider visual
│                                     │
│  Step 2 ┌───────────────────┐ [✕]  │
│         │ Colocar creme     │      │
│         └───────────────────┘      │
│         🖼️ [📷 imagem.jpg]  [✕]    │ ← NOVO! Imagem selecionada
│         ┌─────────────────┐        │
│         │ [Miniatura]     │        │ ← Preview da imagem
│         └─────────────────┘        │
│         ⏱️ Timer: [90s ▼]           │
│         ────────────────────────    │
│         15s ══════════●══ 600s      │
│                                     │
│  Step 3 ┌───────────────────┐ [✕]  │
│         │ Escovar dentes    │      │
│         └───────────────────┘      │
│         🖼️ [Câmera 📸] [Galeria 🖼️] │ ← NOVO! Opções de imagem
│         ⏱️ Timer: [120s ▼]          │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  ➕ Adicionar Passo         │   │ ← Add Step Button
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │      💾 SALVAR TAREFA       │   │ ← Primary Button
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

### Características:
- **Layout**: LazyColumn scrollável
- **NOVO - Imagem por Step**:
  - Botão "Adicionar Imagem"
  - Opções: Câmera 📸 / Galeria 🖼️
  - Preview thumbnail após seleção
  - Botão [✕] para remover
  - Redimensionamento automático (max 1024px)
- **NOVO - Timer por Step**:
  - Dropdown rápido: 15s, 30s, 60s, 90s, 120s, 300s, 600s
  - Slider visual (15s a 600s)
  - Valor em segundos exibido
- **Validações**: 
  - Nome obrigatório
  - Horário HH:mm
  - Estrelas 1-5
  - Min. 1 step
  - Timer 15-600s
- **Navegação**: 
  - Voltar (←) → Confirmar se houver mudanças
  - Salvar (✓) → Voltar para origem

---

## 📱 WIREFRAME 3: TaskListScreen

```
┌─────────────────────────────────────┐
│  ☰ Atividades              🔍      │ ← TopBar com menu e busca
└─────────────────────────────────────┘

┌─────────────────────────────────────┐ ← LazyColumn
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 🕐 08:30      ⭐⭐⭐⭐⭐     │   │ ← TaskCard 1
│  │ Escovar os dentes           │   │
│  │ ─────────────────           │   │
│  │ 3 passos  [PENDENTE]        │   │
│  │ 🖼️ 🖼️ 🖼️ (3 imagens)         │   │ ← NOVO! Indicador imagens
│  │                         [⋮] │   │ ← Menu
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 🕐 12:00      ⭐⭐⭐         │   │ ← TaskCard 2
│  │ Lavar as mãos               │   │
│  │ ─────────────────           │   │
│  │ 2 passos  [CONCLUÍDA] ✓     │   │
│  │ ⏱️ Total: 60s                │   │ ← NOVO! Tempo total
│  │                         [⋮] │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 🕐 19:30      ⭐⭐⭐⭐       │   │ ← TaskCard 3
│  │ Jantar                      │   │
│  │ ─────────────────           │   │
│  │ 5 passos  [PENDENTE]        │   │
│  │ 🖼️ ⏱️ 300s                   │   │ ← Ícones features
│  │                         [⋮] │   │
│  └─────────────────────────────┘   │
│                                     │
│  [Estado Vazio se sem tarefas]     │
│  ┌─────────────────────────────┐   │
│  │         📝                  │   │
│  │  Nenhuma tarefa ainda       │   │
│  │  Toque no + para criar      │   │
│  └─────────────────────────────┘   │
│                                     │
│                                     │
│                          ┌─────┐   │
│                          │  +  │   │ ← FAB (Floating Action)
│                          └─────┘   │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐ ← Menu contextual (⋮)
│  ▶️ Executar agora                  │
│  ✏️ Editar                          │
│  🗑️ Excluir                         │
│  ✖️ Cancelar                        │
└─────────────────────────────────────┘
```

### Características:
- **Layout**: LazyColumn com Cards
- **TaskCard**: 
  - Horário e estrelas no topo
  - Nome da tarefa (headline)
  - Contador de steps
  - Badge de status (cor)
  - **NOVO**: Indicador de imagens (🖼️ × quantidade)
  - **NOVO**: Tempo total do timer
- **FAB**: Adicionar nova tarefa
- **Menu**: Ações por tarefa
- **Estado vazio**: Ilustração + texto

---

## 📱 WIREFRAME 4: TaskExecutionScreen (COM TIMER VISUAL!)

```
┌─────────────────────────────────────┐
│  ← Escovar os dentes          [✕]  │ ← TopBar
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  ════════════════════════════       │ ← Progresso: 2/3 steps
│  Passo 2 de 3                       │
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │    🖼️ [IMAGEM GRANDE]       │   │ ← NOVO! Imagem do step
│  │       (se houver)           │   │   (centralizada, grande)
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│         ⏱️ TIMER CIRCULAR           │ ← NOVO! Timer visual
│       ┌─────────────────┐           │
│       │       90        │           │   Animado, cores:
│       │                 │           │   Verde (>60%)
│       │    segundos     │           │   Amarelo (30-60%)
│       │                 │           │   Vermelho (<30%)
│       │    ═══●═══      │           │   Barra circular
│       └─────────────────┘           │
│                                     │
│  🔊 Colocar o creme dental          │ ← TTS lê automaticamente
│     na escova                       │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    ⏸️ PAUSAR TIMER          │   │ ← NOVO! Pausar/Retomar
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    ✅ CONCLUIR PASSO        │   │ ← Primary Action
│  └─────────────────────────────┘   │
│                                     │
│  ┌──────────┐  ┌──────────────┐    │
│  │ ⏮️ Voltar │  │  ⏭️ Pular    │    │ ← Secondary Actions
│  └──────────┘  └──────────────┘    │
│                                     │
│  ⭐⭐⭐⭐⭐ (5 estrelas desta tarefa) │ ← Motivação
│                                     │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐ ← Ao fim do timer
│         🔔 ALARME!                  │
│                                     │
│     [Vibração + Som]                │ ← NOVO! Feedback
│                                     │
│  Tempo esgotado!                    │
│  Pronto para o próximo passo?       │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    ✅ SIM, CONCLUÍDO!       │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    ⏱️ MAIS 30 SEGUNDOS      │   │
│  └─────────────────────────────┘   │
└─────────────────────────────────────┘
```

### Características:
- **Layout**: Column scrollável
- **Imagem do Step**: 
  - Grande (70% da largura)
  - Centralizada
  - Aspect ratio preservado
  - Placeholder se sem imagem
- **NOVO - Timer Circular**:
  - Tamanho: 200dp × 200dp
  - Animação suave (1s updates)
  - Mudança de cor progressiva:
    - Verde: > 60% do tempo
    - Amarelo: 30-60%
    - Vermelho: < 30%
  - Texto central: segundos restantes
  - Barra circular preenchida
- **NOVO - Alarme ao Fim**:
  - Som de notificação
  - Vibração (se permitido)
  - Dialog com opções:
    - Concluir step
    - Adicionar +30s
- **Botão Pausar/Retomar**:
  - Ícone alterna (⏸️ / ▶️)
  - Mantém progresso
- **TTS**: 
  - Lê descrição ao carregar
  - Pode ser silenciado
- **Progresso**: 
  - Barra no topo (LinearProgressIndicator)
  - "Passo X de Y"

---

## 📱 WIREFRAME 5: Tela de Conclusão (Bônus!)

```
┌─────────────────────────────────────┐
│  ← Tarefa Concluída!                │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│                                     │
│         🎉 🎉 🎉                    │
│                                     │
│      Parabéns!                      │
│   Você conseguiu!                   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │  Escovar os dentes          │   │
│  │                             │   │
│  │  ✓ 3 passos concluídos      │   │
│  │  ⏱️ Tempo total: 4min 30s    │   │ ← NOVO! Tempo real gasto
│  │  ⭐⭐⭐⭐⭐                    │   │
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│  Você ganhou 5 estrelas! ⭐         │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    🏠 VOLTAR PARA CASA      │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    📋 VER OUTRAS TAREFAS    │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

---

## 🎨 COMPONENTES REUTILIZÁVEIS A CRIAR

### 1. **ImagePicker Component**
```kotlin
@Composable
fun ImagePicker(
    imageUrl: String?,
    onImageSelected: (Uri) -> Unit,
    onImageRemoved: () -> Unit
) {
    if (imageUrl != null) {
        // Exibe preview com botão remover
        Row {
            AsyncImage(imageUrl)
            IconButton(onClick = onImageRemoved) {
                Icon(Icons.Default.Close)
            }
        }
    } else {
        // Botões para selecionar
        Row {
            Button("Câmera 📸") { /* lançar câmera */ }
            Button("Galeria 🖼️") { /* lançar galeria */ }
        }
    }
}
```

### 2. **TimerInput Component**
```kotlin
@Composable
fun TimerInput(
    durationSeconds: Int,
    onDurationChange: (Int) -> Unit
) {
    Column {
        Text("⏱️ Timer: ${durationSeconds}s")
        Slider(
            value = durationSeconds.toFloat(),
            onValueChange = { onDurationChange(it.toInt()) },
            valueRange = 15f..600f,
            steps = 39 // intervalos de 15s
        )
        // Ou Dropdown com valores predefinidos
        DropdownMenu {
            items = listOf(15, 30, 60, 90, 120, 180, 300, 600)
        }
    }
}
```

### 3. **CircularTimer Component**
```kotlin
@Composable
fun CircularTimer(
    totalSeconds: Int,
    remainingSeconds: Int,
    isPaused: Boolean
) {
    val progress = remainingSeconds / totalSeconds.toFloat()
    val color = when {
        progress > 0.6f -> Color.Green
        progress > 0.3f -> Color.Yellow
        else -> Color.Red
    }
    
    Box(contentAlignment = Alignment.Center) {
        Canvas(size = 200.dp) {
            drawCircle(color.copy(alpha = 0.2f))
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = 12.dp)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$remainingSeconds", fontSize = 48.sp)
            Text("segundos", fontSize = 16.sp)
        }
    }
}
```

### 4. **StepItem Component (com imagem e timer)**
```kotlin
@Composable
fun StepItem(
    step: Step,
    onDescriptionChange: (String) -> Unit,
    onImageChange: (Uri?) -> Unit,
    onTimerChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card {
        Column {
            OutlinedTextField(
                value = step.description,
                onValueChange = onDescriptionChange,
                label = { Text("Descrição") }
            )
            
            ImagePicker(
                imageUrl = step.imageUrl,
                onImageSelected = onImageChange,
                onImageRemoved = { onImageChange(null) }
            )
            
            TimerInput(
                durationSeconds = step.durationSeconds,
                onDurationChange = onTimerChange
            )
            
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete)
            }
        }
    }
}
```

---

## 🔄 FLUXO COMPLETO DE NAVEGAÇÃO

```
SplashScreen (3s)
    ↓
CheckFirstRun?
    ├─ SIM → OnboardingScreen → HomeScreen (perfil criado)
    └─ NÃO → HomeScreen

HomeScreen
├─ hasProfile = false
│  └─ Botão "Cadastrar Criança" → OnboardingScreen
│
├─ hasProfile = true
│  ├─ Botão "Nova Tarefa" → TaskFormScreen
│  ├─ Botão "Atividades" → TaskListScreen
│  ├─ Botão "Teste Rápido" → TaskExecutionScreen
│  └─ Botão "Debug" → DebugScreen
│
TaskListScreen
├─ FAB (+) → TaskFormScreen (novo)
├─ Card Menu → Executar → TaskExecutionScreen
└─ Card Menu → Editar → TaskFormScreen (editar)

TaskFormScreen
├─ Adicionar Step → StepItem com ImagePicker + TimerInput
├─ Imagem → Câmera/Galeria → Crop → Redimensionar → Salvar URI
├─ Timer → Slider/Dropdown → Validar (15-600s) → Salvar
└─ Salvar → Voltar

TaskExecutionScreen
├─ Carregar Task + Steps
├─ Para cada Step:
│  ├─ Exibir Imagem (se houver)
│  ├─ Iniciar Timer
│  ├─ TTS lê descrição
│  ├─ Usuário pode:
│  │  ├─ Pausar/Retomar
│  │  ├─ Concluir (marca step)
│  │  ├─ Voltar (step anterior)
│  │  └─ Pular (confirmação)
│  └─ Timer fim → Alarme + Vibração
├─ Todos concluídos → TaskCompletionScreen
└─ Cancelar → Confirmação → Voltar
```

---

## 📊 MÉTRICAS E COMPLEXIDADE

### Por Tela:

| Tela | Componentes | Complexidade | Tempo Est. |
|------|-------------|--------------|------------|
| OnboardingScreen | 3 inputs, 2 buttons | ⭐⭐ Baixa | 1 dia |
| TaskFormScreen | 6 inputs, **ImagePicker**, **TimerInput**, lista dinâmica | ⭐⭐⭐⭐ Alta | 3 dias |
| TaskListScreen | LazyColumn, Cards, FAB, Menu | ⭐⭐⭐ Média | 2 dias |
| TaskExecutionScreen | **CircularTimer**, Image, TTS, **Alarme** | ⭐⭐⭐⭐⭐ Muito Alta | 4 dias |

**TOTAL ESTIMADO**: **10-12 dias** (2-2.5 semanas)

---

## 🎯 PRIORIZAÇÃO

### MVP-07A (Alta Prioridade) - 2-3 dias
1. OnboardingScreen (simples)
2. Navegação condicional HomeScreen
3. Lógica CheckFirstRun

### MVP-07B (Alta Prioridade) - 4-5 dias
1. TaskFormScreen básico
2. **ImagePicker** (câmera + galeria)
3. **TimerInput** (slider + dropdown)
4. Adicionar/remover steps dinâmicos
5. Validações completas

### MVP-07C (Média Prioridade) - 5-6 dias
1. TaskListScreen com Cards
2. TaskExecutionScreen básico
3. **CircularTimer** animado
4. **Alarme** sonoro + vibração
5. Navegação completa

### MVP-07D (Baixa Prioridade) - Opcional
1. TaskCompletionScreen com estatísticas
2. Animações de transição
3. Polimento visual

---

## 🚀 DECISÕES TÉCNICAS

### Imagens
- **Biblioteca**: Coil (AsyncImage)
- **Permissões**: CAMERA, READ_EXTERNAL_STORAGE
- **Armazenamento**: Local (app files)
- **Formato**: JPEG comprimido (qualidade 80%)
- **Tamanho máx**: 1024×1024px
- **Fallback**: Placeholder se erro

### Timer
- **Implementação**: Flow + delay(1000)
- **Precisão**: ±500ms aceitável
- **Persistência**: Estado salvo em ViewModel
- **Alarme**: MediaPlayer + Vibrator
- **Cancelamento**: Job cancelável

### Navegação
- **Biblioteca**: Navigation Compose
- **Args**: SafeArgs ou manual
- **BackStack**: Pop até home em conclusão
- **Estado**: Salvo em SavedStateHandle

---

## ✅ CHECKLIST DE IMPLEMENTAÇÃO

### Pré-Desenvolvimento
- [x] Wireframes criados
- [x] Componentes identificados
- [x] Fluxo documentado
- [ ] Aprovação do design ← **VOCÊ ESTÁ AQUI**
- [ ] Permissões listadas

### Desenvolvimento
- [ ] Criar ImagePicker component
- [ ] Criar TimerInput component
- [ ] Criar CircularTimer component
- [ ] Implementar OnboardingScreen
- [ ] Implementar TaskFormScreen
- [ ] Implementar TaskListScreen
- [ ] Implementar TaskExecutionScreen
- [ ] Adicionar permissões AndroidManifest
- [ ] Testar em dispositivo real

### Pós-Desenvolvimento
- [ ] Testes de UI básicos
- [ ] Validação anti-regressão
- [ ] Documentar bugs encontrados
- [ ] Atualizar CHANGELOG
- [ ] Commit e push

---

## 🎨 PALETA DE CORES (Material 3)

```kotlin
// Modo Claro
Primary: #6750A4      (roxo)
Secondary: #625B71    (cinza-roxo)
Tertiary: #7D5260     (rosa)
Background: #FFFBFE   (quase branco)
Surface: #FFFBFE
OnPrimary: #FFFFFF
OnSecondary: #FFFFFF

// Timer Colors
TimerGreen: #4CAF50   (>60%)
TimerYellow: #FFC107  (30-60%)
TimerRed: #F44336     (<30%)

// Status Badge
Pending: #FF9800      (laranja)
Completed: #4CAF50    (verde)
Cancelled: #9E9E9E    (cinza)
```

---

## 📱 RESPONSIVIDADE

### Tamanhos Suportados
- **Compact**: Phones (width < 600dp)
- **Medium**: Tablets 7" (width 600-840dp)
- **Expanded**: Tablets 10"+ (width > 840dp)

### Adaptações
- **Compact**: Layouts verticais
- **Medium**: Alguns layouts horizontais
- **Expanded**: Two-pane layouts (futuro)

---

## 🔐 PERMISSÕES NECESSÁRIAS

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
                 android:maxSdkVersion="32" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.VIBRATE" />
```

---

## 🎬 PRÓXIMOS PASSOS

1. **Revisar wireframes** e aprovar design
2. **Criar branch** `feature/mvp-07`
3. **Implementar MVP-07A** (Onboarding)
4. **Testar** em dispositivo
5. **Implementar MVP-07B** (TaskForm com imagens/timer)
6. **Implementar MVP-07C** (List + Execution)
7. **Validar** anti-regressão
8. **Documentar** e fazer commit

---

**Preparado por**: Equipe de Desenvolvimento  
**Data**: 2025-10-15  
**Aprovação**: Pendente  
**Próxima Ação**: Revisar e aprovar wireframes

