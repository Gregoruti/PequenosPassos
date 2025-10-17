# MVP-07 - PANORAMA GERAL E FUNCIONALIDADES

**Data**: 2025-10-16  
**Versão**: 1.0  
**Status**: 📋 Em Planejamento  

---

## 🎯 OBJETIVO DO MVP-07

Implementar as **4 telas principais de interface** do aplicativo com funcionalidades avançadas:
- ✅ Suporte a **imagens** nos steps (câmera/galeria)
- ✅ **Timer configurável** por step (15s a 600s)
- ✅ **Timer visual circular** animado durante execução
- ✅ **Hub de cadastros** centralizado
- ✅ **Navegação condicional** baseada em estado
- ✅ Design System Material 3 (já implementado no MVP-06)

---

## 📱 TELAS A DESENVOLVER

### 1. OnboardingScreen (HUB DE CADASTROS) ⭐ MODIFICADO

**Função**: Não é mais apenas cadastro inicial - agora é um **hub centralizado** sempre acessível

**Componentes**:
```
┌─────────────────────────────┐
│  ONBOARDING (HUB)           │
├─────────────────────────────┤
│                             │
│  ┌────────────────────┐     │
│  │ 👶 Cadastro        │     │
│  │    Criança         │     │ ← Sempre visível
│  └────────────────────┘     │   Permite editar perfil
│                             │
│  ┌────────────────────┐     │
│  │ 📝 Cadastro        │     │
│  │    de Tarefa   →   │     │ ← Sempre visível
│  └────────────────────┘     │   Cria nova tarefa
│                             │
│  ┌────────────────────┐     │
│  │ ✏️ Editar          │     │ ⭐ NOVO
│  │    Tarefa      →   │     │ ← Sempre visível
│  └────────────────────┘     │   Lista tarefas para edição
│                             │   (protege crianças de edição acidental)
│                             │
└─────────────────────────────┘
```

**Novidades**: 
- Acessível a qualquer momento pelo botão "Cadastro" na HomeScreen
- **Três opções** sempre disponíveis (não depende de estado):
  1. **Cadastro Criança**: Editar perfil da criança
  2. **Cadastro de Tarefa**: Criar nova tarefa
  3. **Editar Tarefa**: ⭐ Lista de tarefas → Seleciona → Edita (protege crianças de edição acidental)

**Justificativa de Design** 🛡️:
- A opção "Editar Tarefa" foi **removida da tela de Atividades** para evitar que crianças com TEA façam alterações acidentais
- Agora, a edição só é acessível através do Hub de Cadastros (área de configuração)
- Isso melhora a **acessibilidade** e **segurança** do aplicativo

---

### 2. TaskFormScreen (COM IMAGENS E TIMER) ⭐ NOVO

**Função**: Criar/editar tarefas com suporte a imagens e timer por step

**Novos Campos por Step**:

#### 🖼️ Suporte a Imagens
```kotlin
- Botão "Adicionar Imagem"
  ├── Opção 1: Câmera 📸
  ├── Opção 2: Galeria 🖼️
  ├── Preview thumbnail após seleção
  ├── Botão [✕] para remover
  └── Redimensionamento automático (max 1024px)
```

**Validações de Imagem**:
- Formatos aceitos: JPG, PNG
- Tamanho máximo: 1024x1024px (redimensiona automaticamente)
- Armazenamento: Local (file storage)

#### ⏱️ Timer Configurável
```kotlin
- Slider visual: 5s ════●════ 600s
- Dropdown rápido: 5s, 15s, 30s, 60s, 90s, 120s, 300s, 600s
- Valor exibido em segundos
- Range: 5-600 segundos (5s a 10min)
```

**Validações de Timer**:
- Mínimo: 5 segundos
- Máximo: 600 segundos (10 minutos)
- Padrão: 60 segundos

**Wireframe**: Ver WIREFRAME 2 em `MVP07_WIREFRAMES.md`

---

### 3. TaskListScreen (COM EXPANSÃO INLINE) ⭐ MODIFICADO

**Função**: Listar tarefas com interação expandida

**Comportamento Novo**:
```
TaskCard (fechado)
┌────────────────────┐
│ 🕐 08:30  ⭐⭐⭐⭐⭐│
│ Escovar dentes     │
│ 3 passos           │
│ 🖼️🖼️🖼️ ⏱️ 180s     │ ← Indicadores
└────────────────────┘

    ↓ (ao tocar)

TaskCard (expandido) ⭐ NOVO
┌────────────────────┐
│ 🕐 08:30  ⭐⭐⭐⭐⭐│
│ Escovar dentes     │
│ ─────────────────  │
│ ▶️ Executar        │ ← Opções inline
│ ✏️ Editar          │
│ ✖️ Cancelar        │
│ ─────────────────  │
└────────────────────┘
```

**Novos Indicadores**:
- 🖼️ × quantidade (ex: 🖼️🖼️🖼️ = 3 imagens)
- ⏱️ + tempo total (ex: ⏱️ 180s)

**Wireframe**: Ver WIREFRAME 3 e 4 em `MVP07_WIREFRAMES.md`

---

### 4. TaskExecutionScreen (COM TIMER VISUAL) ⭐ NOVO

**Função**: Executar tarefa com timer circular animado e imagens grandes

**Componentes Novos**:

#### 🖼️ Imagem Grande do Step
```kotlin
- Tamanho: 70% da largura da tela
- Centralizada
- Aspect ratio preservado
- Placeholder se não houver imagem
```

#### ⏱️ Timer Circular Animado
```
       ┌─────────────┐
       │             │
       │     90      │ ← Segundos restantes
       │             │
       │  segundos   │
       │             │
       │   ═══●═══   │ ← Barra circular
       └─────────────┘
```

**Características do Timer**:
- **Tamanho**: 200dp × 200dp
- **Animação**: Suave (atualiza a cada 1s)
- **Cores dinâmicas**:
  - 🟢 Verde: > 60% do tempo restante
  - 🟡 Amarelo: 30-60% do tempo
  - 🔴 Vermelho: < 30% do tempo
- **Texto central**: Segundos restantes em fonte grande
- **Barra circular**: Preenchimento progressivo

#### ⏸️ Controles do Timer
```kotlin
Botão "Pausar/Retomar"
├── ⏸️ quando rodando
└── ▶️ quando pausado
```

#### 🔔 Alarme ao Fim do Timer
```
Quando timer = 0:
├── Som de notificação
├── Vibração (se permitido)
└── Dialog com opções:
    ├── ✅ Concluir step
    └── ⏱️ Adicionar +30s
```

**Wireframe**: Ver WIREFRAME 4 em `MVP07_WIREFRAMES.md`

---

## 🔄 FLUXO DE NAVEGAÇÃO REVISADO

### HomeScreen (Condicional)

```
SEM PERFIL CADASTRADO:
├── 📝 Cadastro (sempre visível)
└── 🐛 Debug

COM PERFIL CADASTRADO:
├── 📝 Cadastro (sempre visível - para editar)
├── ⚡ Teste Rápido (se houver tarefas)
├── 📋 Atividades (se houver perfil)
└── 🐛 Debug
```

### Navegação Completa

```
HomeScreen
├── Cadastro → OnboardingScreen (Hub) 🛡️ ÁREA DE CONFIGURAÇÃO
│              ├── Cadastro Criança → (edita perfil)
│              ├── Cadastro Tarefa → TaskFormScreen (nova tarefa, com imagens/timer)
│              └── Editar Tarefa → TaskListScreen (seleção) → TaskFormScreen ⭐ MOVIDO PARA CÁ
│                                   (protege crianças de edição acidental)
│
├── Teste Rápido → TaskExecutionScreen (última tarefa, com timer visual)
│
├── Atividades → TaskListScreen (com expansão) 👶 ÁREA DA CRIANÇA
│                └── Tarefa expandida
│                    ├── Executar → TaskExecutionScreen (com timer)
│                    └── Cancelar → Dialog
│                    ❌ Editar REMOVIDO (movido para Hub de Cadastros)
│
└── Debug → DebugScreen
            ├── TTS Test
            └── ASR Test
```

**Separação Clara de Áreas** 🛡️:
- **Hub de Cadastros** (OnboardingScreen): Área de **configuração** para adultos
  - Cadastrar/editar perfil
  - Criar tarefas
  - **Editar tarefas** (protegido de acesso acidental)
  
- **Atividades** (TaskListScreen): Área de **execução** para crianças
  - Visualizar tarefas
  - Executar tarefas
  - Cancelar (com confirmação)
  - ❌ SEM opção de editar (previne alterações acidentais)
---

## 🗂️ ALTERAÇÕES NAS ENTIDADES

### Step Entity (MODIFICADO) ⭐

**Novos campos**:
```kotlin
data class Step(
    // ...campos existentes...
    val imageUrl: String? = null,        // ⭐ NOVO - URL/path da imagem
    val durationSeconds: Int = 60        // ⭐ NOVO - Timer em segundos
)
```

**Tabela Room** (steps):
```sql
ALTER TABLE steps ADD COLUMN image_url TEXT;
ALTER TABLE steps ADD COLUMN duration_seconds INTEGER DEFAULT 60;
```

**Validações**:
- `imageUrl`: Opcional, path local do arquivo
- `durationSeconds`: Obrigatório, range 15-600

---

## 🎨 COMPONENTES REUTILIZÁVEIS A CRIAR

### 1. ImagePicker Component
```kotlin
@Composable
fun ImagePicker(
    imageUrl: String?,
    onImageSelected: (Uri) -> Unit,
    onImageRemoved: () -> Unit
)
```

**Funcionalidades**:
- Launcher de câmera
- Launcher de galeria
- Preview de imagem
- Botão remover
- Redimensionamento automático

---

### 2. TimerInput Component
```kotlin
@Composable
fun TimerInput(
    durationSeconds: Int,
    onDurationChange: (Int) -> Unit
)
```

**Funcionalidades**:
- Slider visual (15-600s)
- Dropdown com valores predefinidos
- Exibição clara do valor

---

### 3. CircularTimer Component
```kotlin
@Composable
fun CircularTimer(
    totalSeconds: Int,
    remainingSeconds: Int,
    isPaused: Boolean,
    onComplete: () -> Unit
)
```

**Funcionalidades**:
- Canvas com círculo animado
- Cores dinâmicas
- Texto central
- Callback ao completar

---

### 4. StepItem Component
```kotlin
@Composable
fun StepItem(
    step: Step,
    onStepChange: (Step) -> Unit,
    onRemove: () -> Unit
)
```

**Funcionalidades**:
- Card completo com todos os campos
- Integração com ImagePicker
- Integração com TimerInput
- Validações inline

---

## 📋 CHECKLIST DE IMPLEMENTAÇÃO

### Fase 1: Preparação (1-2 dias)
- [ ] Atualizar entidade Step (adicionar imageUrl e durationSeconds)
- [ ] Criar migration do Room para adicionar colunas
- [ ] Atualizar DAO com novos campos
- [ ] Atualizar Use Cases para suportar novos campos
- [ ] Testes unitários das alterações

### Fase 2: Componentes (2-3 dias)
- [ ] Criar ImagePicker component
  - [ ] Launcher de câmera
  - [ ] Launcher de galeria
  - [ ] Redimensionamento de imagem
  - [ ] Preview e remoção
- [ ] Criar TimerInput component
  - [ ] Slider visual
  - [ ] Dropdown
  - [ ] Validações
- [ ] Criar CircularTimer component
  - [ ] Canvas circular
  - [ ] Animação
  - [ ] Cores dinâmicas
- [ ] Criar StepItem component
  - [ ] Integração completa
- [ ] Testes de componentes

### Fase 3: Telas (3-4 dias)
- [ ] OnboardingScreen (Hub)
  - [ ] UI com dois botões principais
  - [ ] Formulário de cadastro criança
  - [ ] Navegação para TaskFormScreen
  - [ ] ViewModel
- [ ] TaskFormScreen
  - [ ] Integração com ImagePicker
  - [ ] Integração com TimerInput
  - [ ] Lista de steps dinâmica
  - [ ] Validações completas
  - [ ] ViewModel
- [ ] TaskListScreen
  - [ ] Cards expansíveis
  - [ ] Indicadores de imagem/timer
  - [ ] Animação de expansão
  - [ ] ViewModel
- [ ] TaskExecutionScreen
  - [ ] Exibição de imagem grande
  - [ ] CircularTimer integrado
  - [ ] Controles de pausar/retomar
  - [ ] Alarme ao fim
  - [ ] Dialog de conclusão
  - [ ] ViewModel

### Fase 4: Testes e Validação (2-3 dias)
- [ ] Testes de integração de cada tela
- [ ] Testes de fluxo completo
- [ ] Testes de UI (Compose Test)
- [ ] Validação de acessibilidade
- [ ] Testes de performance (imagens, timer)
- [ ] Anti-regressão (MVPs 01-06)

### Fase 5: Documentação (1 dia)
- [ ] Atualizar CHANGELOG.md
- [ ] Atualizar README.md
- [ ] Criar MVP07_VALIDATION_SUMMARY.md
- [ ] Screenshots/vídeos das telas
- [ ] Documentar componentes criados

---

## 🎯 CRITÉRIOS DE SUCESSO

### Funcionalidades
- ✅ Usuário consegue adicionar imagem em cada step (câmera ou galeria)
- ✅ Usuário consegue configurar timer (15-600s) para cada step
- ✅ Timer visual aparece durante execução da tarefa
- ✅ Timer tem cores dinâmicas (verde/amarelo/vermelho)
- ✅ Alarme toca ao fim do timer com opções de ação
- ✅ Hub de cadastros acessível a qualquer momento
- ✅ Tarefas expandem inline na lista
- ✅ Navegação condicional funciona corretamente

### Qualidade
- ✅ Todos os testes passando (unitários + integração)
- ✅ Build: SUCCESS
- ✅ Anti-regressão: MVPs 01-06 funcionando
- ✅ Performance: Timer não trava UI
- ✅ Performance: Imagens carregam rapidamente
- ✅ Acessibilidade: TalkBack funcional
- ✅ Design: Seguindo Material 3 Theme

---

## 📊 IMPACTO NOS MVPs ANTERIORES

### MVP-02 (Entidades) - IMPACTADO ⚠️
**Alteração**: Entidade Step precisa de novos campos
- `imageUrl: String?`
- `durationSeconds: Int`

**Ação**: Atualizar classe de domínio

---

### MVP-03 (Database) - IMPACTADO ⚠️
**Alteração**: Tabela `steps` precisa de novas colunas
- `image_url TEXT`
- `duration_seconds INTEGER DEFAULT 60`

**Ação**: Criar migration do Room

---

### MVP-04 (Repositórios) - IMPACTADO ⚠️
**Alteração**: StepRepository precisa lidar com novos campos

**Ação**: Atualizar métodos de save/update

---

### MVP-05 (Use Cases) - IMPACTADO ⚠️
**Alteração**: SaveTaskUseCase precisa validar novos campos

**Ação**: Adicionar validações:
- Timer entre 15-600s
- Path de imagem válido (se presente)

---

### MVP-06 (Theme) - SEM IMPACTO ✅
**Razão**: Componentes novos usarão o tema já existente

---

## 🔧 TECNOLOGIAS E BIBLIOTECAS

### Já Implementadas
- Jetpack Compose
- Room Database
- Hilt
- Coroutines/Flow
- Material 3

### A Adicionar
```kotlin
// Câmera e Galeria
implementation "androidx.activity:activity-compose:1.8.0"

// Carregamento de imagens
implementation "io.coil-kt:coil-compose:2.5.0"

// Redimensionamento (opcional, pode usar BitmapFactory nativo)

// Timer/Animação (já disponível no Compose)
// Canvas API
// LaunchedEffect
```

---

## ⏱️ ESTIMATIVA DE TEMPO

| Fase | Duração | Descrição |
|------|---------|-----------|
| Preparação | 1-2 dias | Atualizar entidades, database, migrations |
| Componentes | 2-3 dias | ImagePicker, TimerInput, CircularTimer, StepItem |
| Telas | 3-4 dias | OnboardingScreen, TaskFormScreen, TaskListScreen, TaskExecutionScreen |
| Testes | 2-3 dias | Unitários, integração, UI, anti-regressão |
| Documentação | 1 dia | CHANGELOG, README, validation summary |
| **TOTAL** | **9-13 dias** | ~2-3 semanas de desenvolvimento |

---

## 🚀 PRÓXIMOS PASSOS (APÓS MVP-07)

### MVP-08: Estatísticas e Histórico
- Tela de estatísticas de conclusão
- Histórico de tarefas completadas
- Gráficos de progresso
- Sistema de conquistas

### MVP-09: Notificações
- Lembretes por horário
- Notificações push
- Deep links
- Widget de tela inicial

### MVP-10: Acessibilidade Avançada
- ASR completo (Vosk offline)
- Comandos de voz
- Modos de contraste
- Tamanhos de fonte ajustáveis

---

## 📝 NOTAS IMPORTANTES

1. **Imagens**: Armazenar localmente (não usar backend por enquanto)
2. **Timer**: Usar coroutines para não bloquear UI
3. **Animações**: Manter suaves (60fps)
4. **Testes**: Não regredir MVPs anteriores
5. **Documentação**: Manter sincronizada com código
6. **Commits**: Fazer commits atômicos por feature

---

## 📚 REFERÊNCIAS

- **PATHS.md**: Fluxo de navegação completo (ATUALIZADO 2025-10-16)
- **MVP07_WIREFRAMES.md**: Wireframes detalhados das 4 telas
- **SPECIFICATION_FOR_APP.md**: Especificação geral do app
- **GUIDELINES.md**: Diretrizes de desenvolvimento

---

**Documento criado**: 2025-10-16  
**Autor**: GitHub Copilot  
**Versão**: 1.0  
**Status**: ✅ Completo e pronto para implementação

---

## ✅ RESUMO EXECUTIVO

O **MVP-07** introduz funcionalidades visuais e de timer que transformam o app em uma ferramenta muito mais completa para auxiliar crianças:

1. **Imagens nos steps**: Facilita compreensão visual da tarefa
2. **Timer configurável**: Ensina noção de tempo
3. **Timer visual circular**: Feedback claro do progresso
4. **Hub de cadastros**: Facilita manutenção de perfis e tarefas
5. **Navegação aprimorada**: Experiência mais fluida

**Estimativa**: 2-3 semanas de desenvolvimento  
**Complexidade**: Média-Alta  
**Valor entregue**: Alto (funcionalidades essenciais para o público-alvo)
