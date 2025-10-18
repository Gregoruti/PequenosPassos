# MVP-07 WIREFRAMES E DRAFTS

## Panorama Visual Completo das Telas

**Data**: 2025-10-16  
**Versão**: 1.1 ⭐ ATUALIZADO  
**MVP**: 07 - Telas de Interface com Imagens, Timer e Categorias  

---

## 🎯 VISÃO GERAL DO MVP-07

### Objetivo
Implementar **4 telas principais** de interface do usuário com suporte a:
- ✅ **Categorias** organizadas (27 categorias em 5 grupos) ⭐ NOVO
- ✅ **Imagem da tarefa** (aparece no card da lista) ⭐ NOVO
- ✅ **Imagens nos steps** (galeria/câmera) - aparecem na execução
- ✅ **Timer** configurável por step (15s-600s)
- ✅ **Timer visual** circular animado
- ✅ Navegação condicional com segurança
- ✅ Design System Material 3

### Telas a Desenvolver
1. **OnboardingScreen** - Hub de Cadastros (3 opções)
2. **TaskFormScreen** - Criar/editar tarefas (com categorias, imagens e timer)
3. **TaskListScreen** - Listar tarefas (com imagem e categoria)
4. **TaskExecutionScreen** - Executar com timer visual e imagens dos steps

---

## 📱 WIREFRAME 1: OnboardingScreen (HUB DE CADASTROS)

```
┌─────────────────────────────────────┐
│  ┌─┐ Pequenos Passos            │ ← TopBar
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  CADASTROS                          │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 👶 Cadastro Criança         │   │ ← Opção 1
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 📝 Cadastro de Tarefa       │   │ ← Opção 2
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ ✏️ Editar Tarefa            │   │ ← Opção 3 (proteção TEA)
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘

───────── AO CLICAR "CADASTRO CRIANÇA" ─────────

┌─────────────────────────────────────┐
│  ← Cadastro da Criança              │ ← TopBar
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
- **Layout**: Hub com 3 opções + formulário de cadastro criança
- **Validações**: 
  - Nome mínimo 2 caracteres
  - Data válida no passado
- **Navegação**: 
  - Opção 1 → Formulário de cadastro criança
  - Opção 2 → TaskFormScreen (nova tarefa)
  - Opção 3 → TaskListScreen (seleção) → TaskFormScreen (editar)
  - Salvar → HomeScreen (com perfil criado)
  - Cancelar → HomeScreen
- **Cores**: Background claro, Primary Button roxo

---

## 📱 WIREFRAME 2: TaskFormScreen (COM CATEGORIAS, IMAGENS E TIMER!) ⭐ MODIFICADO

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
│  Categoria *                        │ ← NOVO! (substituiu "Descrição")
│  ┌─────────────────────────────┐   │
│  │ 🧍‍♀️ Higiene              ▼│   │ ← Dropdown com 27 categorias
│  └─────────────────────────────┘   │
│                                     │
│  Imagem da Tarefa                   │ ← NOVO! (substituiu "Tipo")
│  ┌─────────────────────────────┐   │
│  │ [📷 Câmera] [🖼️ Galeria]    │   │ ← Adicionar imagem principal
│  └─────────────────────────────┘   │
│  ┌─────────────────┐                │
│  │  [Miniatura]    │                │ ← Preview se selecionada
│  │  [🪥 Criança    │                │   Aparece no card da lista
│  │   escovando]    │                │
│  └─────────────────┘   [✕]          │
│                                     │
│  ┌────────────┐  ┌─────────────┐   │
│  │ 🕐 08:30   │  │ ⭐⭐⭐⭐⭐  │   │ ← Time + Stars
│  └────────────┘  └─────────────┘   │
│                                     │
├─────────────────────────────────────┤
│  PASSOS (STEPS)                     │
├─────────────────────────────────────┤
│                                     │
│  Step 1 ┌───────────────────┐ [✕]  │
│         │ Molhar a escova   │      │
│         └───────────────────┘      │
│         Nome do passo: Molhar a escova          │
│         🖼️ Miniatura do Step:                   │
│           ┌──────────────┐                      │
│           │  [thumb.jpg] │                      │ ← Miniatura do Step (exibição)
│           └──────────────┘                      │
│         🖼️ [Adicionar Imagem]  [✕ Remover]     │
│         ⏱️ Timer: [60s ▼]                        │
│         ────────────────────────                │
│         15s ═════●═══════ 600s                  │
│                                     │
│  Step 2 ┌───────────────────┐ [✕]  │
│         │ Colocar creme     │      │
│         └───────────────────┘      │
│         Nome do passo: Colocar creme            │
│         🖼️ Miniatura do Step:                   │
│           ┌──────────────┐                      │
│           │ [🚰 Molhando]│                      │ ← Preview
│           └──────────────┘                      │
│         🖼️ [📷 imagem.jpg]  [✕ Remover]        │
│         ⏱️ Timer: [90s ▼]                        │
│         ────────────────────────                │
│         15s ══════════●══ 600s                  │
│                                     │
│  Step 3 ┌───────────────────┐ [✕]  │
│         │ Escovar dentes    │      │
│         └───────────────────┘      │
│         Nome do passo: Escovar dentes           │
│         🖼️ [Câmera 📸] [Galeria 🖼️]             │ ← Imagem do Step
│         ⏱️ Timer: [120s ▼]                       │
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
- **NOVO - Categoria** (substituiu "Descrição"):
  - Dropdown/Menu com **27 categorias organizadas em 5 grupos**
  - Seleção obrigatória
  - Cada categoria tem emoji próprio
  - Exibida no card da TaskListScreen
- **NOVO - Imagem da Tarefa** (substituiu "Tipo"):
  - **Imagem PRINCIPAL da tarefa**
  - Aparece no **card da TaskListScreen** (miniatura)
  - Opções: Câmera 📸 / Galeria 🖼️
  - Preview thumbnail após seleção
  - Botão [✕] para remover
  - Exemplo: Foto de criança escovando dentes para tarefa "Escovar os dentes"
  - Fallback: Emoji da categoria se não houver imagem
- **Imagem por Step** (para execução):
  - Cada step tem sua própria imagem (opcional)
  - Aparece durante **TaskExecutionScreen**
  - Avança sequencialmente com os steps
  - Exemplo: Step 1 (molhar), Step 2 (creme), Step 3 (escovar)
- **Timer por Step**:
  - Dropdown rápido: 15s, 30s, 60s, 90s, 120s, 300s, 600s
  - Slider visual (15s a 600s)
  - Valor em segundos exibido
- **Validações**: 
  - Nome obrigatório
  - **Categoria obrigatória** ⭐ NOVO
  - Horário HH:mm
  - Estrelas 1-5
  - Min. 1 step
  - Timer 15-600s por step
- **Navegação**: 
  - Voltar (←) → Confirmar se houver mudanças
  - Salvar (✓) → Voltar para origem

### Categorias Disponíveis (27 categorias em 5 grupos):

#### 🧍‍♀️ Autocuidado e Rotina Pessoal (6)
1. 🍽️ Alimentação
2. 🍴 Alimentação Independente
3. 🧼 Higiene
4. 👕 Vestir-se / Trocar de roupa
5. 🚽 Uso do banheiro / Treino de toalete
6. 🛏️ Dormir

#### 🧘‍♂️ Saúde, Regulação e Terapias (9)
7. 💊 Medicação
8. 🏥 Saúde / Médico
9. 🧘 Regulação Emocional / Relaxamento
10. 🎨 Integração Sensorial
11. 🧠 Terapia Comportamental
12. 🏃 Terapia Física
13. 🖐️ Terapia Ocupacional
14. 👥 Terapia Social
15. 🗣️ Terapia de Fala e Linguagem

#### 🧠 Desenvolvimento Cognitivo e Educacional (4)
16. 📚 Estudos
17. 💬 Comunicação / Linguagem Funcional
18. 📱 Uso de Dispositivos / Apps Educativos
19. 📅 Planejamento / Rotina Visual

#### 🤝 Interação e Socialização (3)
20. 🤝 Interação Social
21. 👨‍👩‍👧 Tempo com a Família
22. 🎮 Lazer / Diversão

#### 🏠 Atividades Cotidianas e Funcionais (3)
23. 🧹 Tarefas Domésticas
24. 💼 Trabalho
25. 🚗 Transporte

#### 🧩 Outros (2)
26. ⚽ Atividades Físicas
27. 📋 Outro

### Fluxo de Imagens:

1. **Imagem da Tarefa** (principal) ⭐ NOVO:
   - Definida no TaskFormScreen (campo "Imagem da Tarefa")
   - Aparece no **card da TaskListScreen** (miniatura grande, lado esquerdo)
   - Representa a tarefa como um todo
   - Exemplo: 🪥 Criança escovando dentes
   - Se não houver imagem: exibe emoji da categoria

2. **Imagens dos Steps**:
   - Definidas em cada step no TaskFormScreen
   - Aparecem na **TaskExecutionScreen** durante execução
   - Avançam sequencialmente (step 1 → step 2 → step 3)
   - Exemplo: 
     - Step 1: 🚰 Molhar escova
     - Step 2: 🧴 Colocar creme
     - Step 3: 🪥 Escovar

---

## 📱 WIREFRAME 3: TaskListScreen ⭐ MODIFICADO

```
┌─────────────────────────────────────┐
│  ☰ Atividades              🔍      │ ← TopBar com menu e busca
└─────────────────────────────────────┘

┌─────────────────────────────────────┐ ← LazyColumn
│                                     │
│  ┌─────────────────────────────┐   │
│  │ ┌───────┐  🕐 08:30  ⭐⭐⭐⭐⭐│   │ ← TaskCard 1
│  │ │ 🪥    │  Escovar dentes    │   │   NOVO! Imagem da tarefa
│  │ │[foto] │  ─────────────────  │   │   (miniatura grande)
│  │ └───────┘  3 passos [PENDENTE]│   │
│  │ 🧼 Higiene                    │   │ ← NOVO! Categoria
│  │ 🖼️ 🖼️ 🖼️ ⏱️ 270s              │   │ ← Indicadores
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ ┌───────┐  🕐 12:00  ⭐⭐⭐   │   │ ← TaskCard 2
│  │ │ 🧼    │  Lavar as mãos     │   │   Imagem da tarefa
│  │ │[foto] │  ─────────────────  │   │
│  │ └───────┘  2 passos [CONCLUÍDA]✓│   │
│  │ 🧼 Higiene                    │   │ ← Categoria
│  │ ⏱️ Total: 60s                 │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ ┌───────┐  🕐 19:30  ⭐⭐⭐⭐ │   │ ← TaskCard 3
│  │ │ 🍽️    │  Jantar            │   │   Imagem da tarefa
│  │ │[foto] │  ─────────────────  │   │   (ou emoji)
│  │ └───────┘  5 passos [PENDENTE]│   │
│  │ 🍽️ Alimentação                │   │ ← Categoria
│  │ 🖼️ 🖼️ ⏱️ 300s                 │   │
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

───── AO TOCAR NA TAREFA (EXPANSÃO INLINE) ─────

┌─────────────────────────────────────┐
│  ┌───────┐  🕐 08:30  ⭐⭐⭐⭐⭐     │
│  │ 🪥    │  Escovar dentes         │
│  │[foto] │  ─────────────────      │
│  └───────┘  3 passos [PENDENTE]    │
│  🧼 Higiene                         │
│  🖼️ 🖼️ 🖼️ ⏱️ 270s                   │
│  ─────────────────────────────────  │
│  ▶️ Executar agora                  │ ← Opções inline
│  ✖️ Cancelar                        │   (SEM Editar!)
│  ─────────────────────────────────  │
└─────────────────────────────────────┘
```

### Características:
- **Layout**: LazyColumn com Cards
- **TaskCard** (MODIFICADO): 
  - **🖼️ Imagem da tarefa** (miniatura 60dp, lado esquerdo) ⭐ NOVO
    - Se houver imagem: exibe foto
    - Se não: exibe emoji da categoria em box colorido
  - Horário e estrelas no topo direito
  - Nome da tarefa (headline)
  - Contador de steps e status
  - **📑 Categoria exibida** (emoji + nome) ⭐ NOVO
  - **Indicador de imagens dos steps** (🖼️ × quantidade)
  - **Tempo total do timer** (⏱️ Xs)
- **Expansão inline**: Ao tocar, mostra apenas Executar e Cancelar
- **FAB**: Adicionar nova tarefa
- **Estado vazio**: Ilustração + texto
- **🛡️ Segurança**: Opção "Editar" removida (apenas via Hub de Cadastros)

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
│  │    🖼️ [IMAGEM DO STEP]      │   │ ← Imagem do Step atual
│  │       (se houver)           │   │   (centralizada, grande)
│  │    [🚰 Molhando escova]     │   │   Avança com steps
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│         ⏱️ TIMER CIRCULAR           │ ← Timer visual
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
│  │    ⏸️ PAUSAR TIMER          │   │ ← Pausar/Retomar
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
│     [Vibração + Som]                │ ← Feedback
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
- **Imagem do Step** (não da tarefa!): 
  - Grande (70% da largura)
  - **Avança sequencialmente** com cada step
  - Centralizada
  - Aspect ratio preservado
  - Placeholder se step sem imagem
- **Timer Circular**:
  - Tamanho: 200dp × 200dp
  - Animação suave (1s updates)
  - Mudança de cor progressiva:
    - Verde: > 60% do tempo
    - Amarelo: 30-60%
    - Vermelho: < 30%
  - Texto central: segundos restantes
  - Barra circular preenchida
- **Alarme ao Fim**:
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
│  │ ┌───────┐                   │   │
│  │ │ 🪥    │ Escovar os dentes │   │ ← Imagem da tarefa
│  │ │[foto] │                   │   │
│  │ └───────┘                   │   │
│  │  ✓ 3 passos concluídos      │   │
│  │  ⏱️ Tempo total: 4min 30s    │   │ ← Tempo real gasto
│  │  ⭐⭐⭐⭐⭐                    │   │
│  │  🧼 Higiene                 │   │ ← Categoria
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

### 1. **CategoryPicker Component** ⭐ NOVO
```kotlin
// Enum para categorias
enum class TaskCategory(
    val emoji: String, 
    val title: String, 
    val group: String
) {
    // 🧍‍♀️ Autocuidado e Rotina Pessoal
    ALIMENTACAO("🍽️", "Alimentação", "Autocuidado e Rotina Pessoal"),
    ALIMENTACAO_INDEPENDENTE("🍴", "Alimentação Independente", "Autocuidado e Rotina Pessoal"),
    HIGIENE("🧼", "Higiene", "Autocuidado e Rotina Pessoal"),
    VESTIR("👕", "Vestir-se / Trocar de roupa", "Autocuidado e Rotina Pessoal"),
    BANHEIRO("🚽", "Uso do banheiro / Treino de toalete", "Autocuidado e Rotina Pessoal"),
    DORMIR("🛏️", "Dormir", "Autocuidado e Rotina Pessoal"),
    
    // 🧘‍♂️ Saúde, Regulação e Terapias
    MEDICACAO("💊", "Medicação", "Saúde, Regulação e Terapias"),
    SAUDE("🏥", "Saúde / Médico", "Saúde, Regulação e Terapias"),
    REGULACAO("🧘", "Regulação Emocional / Relaxamento", "Saúde, Regulação e Terapias"),
    INTEGRACAO_SENSORIAL("🎨", "Integração Sensorial", "Saúde, Regulação e Terapias"),
    TERAPIA_COMPORTAMENTAL("🧠", "Terapia Comportamental", "Saúde, Regulação e Terapias"),
    TERAPIA_FISICA("🏃", "Terapia Física", "Saúde, Regulação e Terapias"),
    TERAPIA_OCUPACIONAL("🖐️", "Terapia Ocupacional", "Saúde, Regulação e Terapias"),
    TERAPIA_SOCIAL("👥", "Terapia Social", "Saúde, Regulação e Terapias"),
    TERAPIA_FALA("🗣️", "Terapia de Fala e Linguagem", "Saúde, Regulação e Terapias"),
    
    // 🧠 Desenvolvimento Cognitivo e Educacional
    ESTUDOS("📚", "Estudos", "Desenvolvimento Cognitivo e Educacional"),
    COMUNICACAO("💬", "Comunicação / Linguagem Funcional", "Desenvolvimento Cognitivo e Educacional"),
    DISPOSITIVOS("📱", "Uso de Dispositivos / Apps Educativos", "Desenvolvimento Cognitivo e Educacional"),
    PLANEJAMENTO("📅", "Planejamento / Rotina Visual", "Desenvolvimento Cognitivo e Educacional"),
    
    // 🤝 Interação e Socialização
    INTERACAO_SOCIAL("🤝", "Interação Social", "Interação e Socialização"),
    TEMPO_FAMILIA("👨‍👩‍👧", "Tempo com a Família", "Interação e Socialização"),
    LAZER("🎮", "Lazer / Diversão", "Interação e Socialização"),
    
    // 🏠 Atividades Cotidianas e Funcionais
    TAREFAS_DOMESTICAS("🧹", "Tarefas Domésticas", "Atividades Cotidianas e Funcionais"),
    TRABALHO("💼", "Trabalho", "Atividades Cotidianas e Funcionais"),
    TRANSPORTE("🚗", "Transporte", "Atividades Cotidianas e Funcionais"),
    
    // 🧩 Outros
    ATIVIDADES_FISICAS("⚽", "Atividades Físicas", "Outros"),
    OUTRO("📋", "Outro", "Outros");
    
    val displayText = "$emoji $title"
}

@Composable
fun CategoryPicker(
    selectedCategory: TaskCategory?,
    onCategorySelected: (TaskCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedCategory?.displayText ?: "Selecione uma categoria",
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoria *") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val grouped = TaskCategory.values().groupBy { it.group }
            
            grouped.forEach { (group, categories) ->
                // Header do grupo
                Text(
                    group,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
                
                // Categorias do grupo
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.displayText) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        },
                        leadingIcon = { 
                            Text(category.emoji, fontSize = 20.sp) 
                        }
                    )
                }
                
                if (group != grouped.keys.last()) {
                    Divider()
                }
            }
        }
    }
}
```

### 2. **ImagePicker Component**
```kotlin
@Composable
fun ImagePicker(
    imageUrl: String?,
    onImageSelected: (Uri) -> Unit,
    onImageRemoved: () -> Unit,
    label: String = "Adicionar Imagem"
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        
        if (imageUrl != null) {
            // Preview com botão remover
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Preview",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                IconButton(onClick = onImageRemoved) {
                    Icon(Icons.Default.Close, "Remover")
                }
            }
        } else {
            // Botões para selecionar
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { /* lançar câmera */ }) {
                    Icon(Icons.Default.CameraAlt, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Câmera")
                }
                OutlinedButton(onClick = { /* lançar galeria */ }) {
                    Icon(Icons.Default.Image, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Galeria")
                }
            }
        }
    }
}
```

### 3. **TimerInput Component**
```kotlin
@Composable
fun TimerInput(
    durationSeconds: Int,
    onDurationChange: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "⏱️ Timer: ${durationSeconds}s",
            style = MaterialTheme.typography.labelMedium
        )
        
        Slider(
            value = durationSeconds.toFloat(),
            onValueChange = { onDurationChange(it.toInt()) },
            valueRange = 15f..600f,
            steps = 39, // intervalos de 15s
            modifier = Modifier.fillMaxWidth()
        )
        
        // Atalhos rápidos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            listOf(15, 30, 60, 90, 120, 180, 300, 600).forEach { seconds ->
                FilterChip(
                    selected = durationSeconds == seconds,
                    onClick = { onDurationChange(seconds) },
                    label = { Text("${seconds}s") }
                )
            }
        }
    }
}
```

### 4. **CircularTimer Component**
```kotlin
@Composable
fun CircularTimer(
    totalSeconds: Int,
    remainingSeconds: Int,
    isPaused: Boolean
) {
    val progress = remainingSeconds / totalSeconds.toFloat()
    val color = when {
        progress > 0.6f -> Color(0xFF4CAF50) // Verde
        progress > 0.3f -> Color(0xFFFFC107) // Amarelo
        else -> Color(0xFFF44336) // Vermelho
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            // Círculo de fundo
            drawCircle(
                color = color.copy(alpha = 0.2f),
                radius = size.minDimension / 2
            )
            
            // Arco de progresso
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "$remainingSeconds",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                "segundos",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

### 5. **StepItem Component**
```kotlin
@Composable
fun StepItem(
    step: Step,
    stepNumber: Int,
    onDescriptionChange: (String) -> Unit,
    onImageChange: (Uri?) -> Unit,
    onTimerChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Step $stepNumber",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, "Remover step")
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = step.description,
                onValueChange = onDescriptionChange,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(Modifier.height(12.dp))
            
            ImagePicker(
                imageUrl = step.imageUrl,
                onImageSelected = onImageChange,
                onImageRemoved = { onImageChange(null) },
                label = "Imagem do Step (aparece na execução)"
            )
            
            Spacer(Modifier.height(12.dp))
            
            TimerInput(
                durationSeconds = step.durationSeconds,
                onDurationChange = onTimerChange
            )
        }
    }
}
```

### 6. **TaskCard Component** ⭐ NOVO (para TaskListScreen)
```kotlin
@Composable
fun TaskCard(
    task: Task,
    stepsCount: Int,
    stepsWithImages: Int,
    totalTimerSeconds: Int,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onExecute: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Imagem da tarefa (principal) ou emoji
                if (task.imageUrl != null) {
                    AsyncImage(
                        model = task.imageUrl,
                        contentDescription = task.name,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(task.category.emoji, fontSize = 32.sp)
                    }
                }
                
                // Conteúdo
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🕐 ${task.scheduledTime}")
                        StarRating(task.stars)
                    }
                    
                    Text(
                        task.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Text(
                        "${task.category.emoji} ${task.category.title}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("$stepsCount passos")
                        StatusBadge(task.status)
                        if (stepsWithImages > 0) {
                            repeat(minOf(stepsWithImages, 3)) {
                                Text("🖼️", fontSize = 12.sp)
                            }
                        }
                        if (totalTimerSeconds > 0) {
                            Text("⏱️ ${totalTimerSeconds}s", fontSize = 12.sp)
                        }
                    }
                }
            }
            
            // Opções expandidas (inline)
            if (isExpanded) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                Button(
                    onClick = onExecute,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PlayArrow, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Executar agora")
                }
                
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Close, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Cancelar")
                }
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
    ├─ SIM → OnboardingScreen (Hub) → HomeScreen (perfil criado)
    └─ NÃO → HomeScreen

HomeScreen
├─ Botão "Cadastro" (sempre visível) → OnboardingScreen (Hub)
│  ├─ Opção "Cadastro Criança" → Formulário → Salvar → HomeScreen
│  ├─ Opção "Cadastro de Tarefa" → TaskFormScreen (nova) → Salvar → HomeScreen
│  └─ Opção "Editar Tarefa" → TaskListScreen (seleção) → TaskFormScreen (editar)
│
├─ hasProfile = true
│  ├─ Botão "Atividades" → TaskListScreen
│  ├─ Botão "Teste Rápido" → TaskExecutionScreen (última tarefa)
│  └─ Botão "Debug" → DebugScreen
│
TaskListScreen
├─ FAB (+) → TaskFormScreen (novo)
├─ Card (toque) → Expande inline
│  ├─ Executar → TaskExecutionScreen
│  └─ Cancelar → Dialog → Atualiza status
│
TaskFormScreen ⭐ MODIFICADO
├─ Campo "Categoria" → CategoryPicker (27 categorias)
├─ Campo "Imagem da Tarefa" → ImagePicker (principal, aparece no card)
├─ Adicionar Step → StepItem
│  ├─ Descrição
│  ├─ ImagePicker (imagem do step, aparece na execução)
│  └─ TimerInput (15-600s)
└─ Salvar → Voltar

TaskExecutionScreen
├─ Carregar Task + Steps
├─ Para cada Step:
│  ├─ Exibir Imagem do Step (se houver)
│  ├─ Iniciar Timer circular
│  ├─ TTS lê descrição
│  ├─ Usuário pode: Pausar/Retomar, Concluir, Voltar, Pular
│  └─ Timer fim → Alarme + Vibração → Dialog (+30s ou concluir)
├─ Todos concluídos → TaskCompletionScreen
└─ Cancelar → Confirmação → Voltar
```

---

## 📊 MÉTRICAS E COMPLEXIDADE ⭐ ATUALIZADO

### Por Tela:

| Tela | Componentes | Complexidade | Tempo Est. |
|------|-------------|--------------|------------|
| OnboardingScreen (Hub) | Hub (3 botões) + Formulário (3 inputs) | ⭐⭐ Baixa-Média | 1-2 dias |
| TaskFormScreen | **CategoryPicker (27)**, **2× ImagePicker**, **TimerInput**, lista dinâmica, validações | ⭐⭐⭐⭐⭐ Muito Alta | 4-5 dias |
| TaskListScreen | LazyColumn, **TaskCard com imagem e categoria**, expansão inline, FAB | ⭐⭐⭐⭐ Alta | 3 dias |
| TaskExecutionScreen | **CircularTimer**, Imagem (step), TTS, **Alarme**, controles | ⭐⭐⭐⭐⭐ Muito Alta | 4 dias |

**TOTAL ESTIMADO**: **12-15 dias** (2.5-3 semanas)

---

## 🎯 PRIORIZAÇÃO ⭐ ATUALIZADO

### MVP-07A (Alta Prioridade) - 2-3 dias
1. OnboardingScreen (Hub + formulário criança)
2. Navegação condicional HomeScreen
3. Lógica CheckFirstRun

### MVP-07B (Alta Prioridade) - 5-6 dias
1. **CategoryPicker** (27 categorias em 5 grupos) ⭐ NOVO
2. **ImagePicker para tarefa** (principal, aparece no card) ⭐ NOVO
3. **ImagePicker para steps** (execução)
4. **TimerInput** (slider + atalhos)
5. TaskFormScreen completo
6. Validações (incluindo categoria obrigatória)

### MVP-07C (Média Prioridade) - 5-6 dias
1. TaskListScreen com **TaskCard modificado** (imagem + categoria)
2. Expansão inline (sem menu externo)
3. TaskExecutionScreen com imagens dos steps
4. **CircularTimer** animado
5. **Alarme** + vibração
6. Navegação completa

### MVP-07D (Baixa Prioridade) - Opcional
1. TaskCompletionScreen com estatísticas
2. Animações de transição
3. Filtros por categoria
4. Busca por nome

---

## 🚀 DECISÕES TÉCNICAS ⭐ ATUALIZADO

### Categorias ⭐ NOVO
- **Implementação**: Enum TaskCategory com 27 valores
- **Grupos**: 5 grupos principais para organização visual
- **Exibição**: ExposedDropdownMenuBox hierárquico
- **Armazenamento**: String no banco (nome do enum)
- **Validação**: Obrigatório
- **Emoji**: Cada categoria tem emoji próprio (facilita identificação visual)
- **Fallback**: Categoria "OUTRO" sempre disponível

### Imagens ⭐ MODIFICADO
- **Biblioteca**: Coil 2.5.0 (AsyncImage)
- **Permissões**: 
  - CAMERA
  - READ_EXTERNAL_STORAGE (SDK < 33)
  - READ_MEDIA_IMAGES (SDK >= 33)
- **Armazenamento**: Local (app internal storage, files/)
- **Formato**: JPEG comprimido (qualidade 80%)
- **Tamanho máx**: 1024×1024px (redimensionado automaticamente)
- **Tipos de imagem**:
  1. **Imagem da Tarefa** (principal): 
     - 1 por tarefa
     - Aparece no **card da TaskListScreen** (miniatura 60dp)
     - Representa a tarefa visualmente
     - Fallback: emoji da categoria em box colorido
  2. **Imagens dos Steps**:
     - 1 por step (opcional)
     - Aparecem na **TaskExecutionScreen** durante execução
     - Avançam sequencialmente
     - Fallback: sem imagem, apenas texto
- **Redimensionamento**: BitmapFactory nativo do Android
- **Nomeação**: `task_{taskId}.jpg` para tarefa, `step_{stepId}.jpg` para steps

### Timer
- **Implementação**: Flow + delay(1000) com coroutines
- **Precisão**: ±500ms aceitável
- **Persistência**: Estado salvo em ViewModel (SavedStateHandle)
- **Alarme**: MediaPlayer (notificação) + Vibrator
- **Cancelamento**: Job cancelável
- **Cores dinâmicas**: Verde > Amarelo > Vermelho baseado em progress

### Navegação
- **Biblioteca**: Navigation Compose 2.7.x
- **Args**: SafeArgs ou manual (Long para IDs)
- **BackStack**: PopUpTo home em conclusão
- **Estado**: Salvo em SavedStateHandle
- **Segurança**: Edição apenas via Hub (proteção TEA) 🛡️

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

## ✅ CHECKLIST DE IMPLEMENTAÇÃO ⭐ ATUALIZADO

### Pré-Desenvolvimento
- [x] Wireframes criados e revisados
- [x] Componentes identificados (6 componentes principais)
- [x] **27 categorias definidas** ⭐
- [x] **Fluxo de 2 tipos de imagens documentado** ⭐
- [x] Fluxo completo documentado
- [ ] Aprovação do design ← **APROVAÇÃO NECESSÁRIA**
- [ ] Permissões listadas

### Desenvolvimento - Fase 1 (Entidades e Database)
- [ ] Adicionar campo `category: String` em Task
- [ ] Adicionar campo `imageUrl: String?` em Task (imagem principal)
- [ ] Adicionar campo `imageUrl: String?` em Step (já planejado)
- [ ] Adicionar campo `durationSeconds: Int` em Step (já planejado)
- [ ] Criar migration do Room
- [ ] Atualizar DAOs
- [ ] Atualizar repositórios
- [ ] Testes unitários

### Desenvolvimento - Fase 2 (Componentes)
- [ ] Criar **CategoryPicker** component ⭐
- [ ] Criar **ImagePicker** component (reutilizável para tarefa e steps)
- [ ] Criar **TimerInput** component
- [ ] Criar **CircularTimer** component
- [ ] Criar **StepItem** component
- [ ] Criar **TaskCard** component ⭐
- [ ] Testes de componentes

### Desenvolvimento - Fase 3 (Telas)
- [ ] OnboardingScreen (Hub com 3 opções)
- [ ] TaskFormScreen (com categoria, 2 image pickers, timer)
- [ ] TaskListScreen (com TaskCard modificado)
- [ ] TaskExecutionScreen (com imagens dos steps, timer circular)
- [ ] ViewModels completos
- [ ] Navegação integrada

### Desenvolvimento - Fase 4 (Recursos)
- [ ] Implementar launcher de câmera
- [ ] Implementar launcher de galeria
- [ ] Redimensionamento de imagens
- [ ] Armazenamento local de imagens
- [ ] Alarme sonoro
- [ ] Vibração
- [ ] TTS integrado

### Pós-Desenvolvimento
- [ ] Testes de UI (Compose Test)
- [ ] Testes em dispositivo real
- [ ] Validação anti-regressão (MVPs 01-06)
- [ ] Documentar bugs encontrados
- [ ] Atualizar CHANGELOG
- [ ] Screenshots/vídeos
- [ ] Commit e push

---

## 🎨 PALETA DE CORES (Material 3)

```text
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

// Category Fallback Box
PrimaryContainer: #E8DEF8  (roxo claro para emoji)
```

---

## 📝 MUDANÇAS NESTA VERSÃO (v1.1 - 2025-10-16)

### ✅ Adicionado
- **27 categorias** organizadas em 5 grupos hierárquicos
- **CategoryPicker** component (dropdown com grupos)
- **Imagem da Tarefa** (campo novo que substitui "Tipo")
- **TaskCard** atualizado para exibir imagem da tarefa e categoria
- Distinção clara entre "imagem da tarefa" e "imagens dos steps"
- Enum TaskCategory completo com emojis

### 🔄 Modificado
- Campo "Descrição" → "Categoria" (dropdown obrigatório)
- Campo "Tipo" → "Imagem da Tarefa" (ImagePicker)
- TaskListScreen agora exibe imagem da tarefa nos cards
- TaskExecutionScreen exibe imagens dos steps (não da tarefa)
- Wireframes atualizados com novos campos

### ❌ Removido
- Campo "Tipo" (TaskType enum)
- Campo "Descrição" (texto livre)

---

## 🎬 PRÓXIMOS PASSOS

1. ✅ **APROVAÇÃO DOS WIREFRAMES** ← VOCÊ ESTÁ AQUI
2. Criar branch `feature/mvp-07-categories-images-timer`
3. Atualizar entidades (Task + Step)
4. Criar migration do database
5. Implementar CategoryPicker
6. Implementar ImagePicker (reutilizável)
7. Implementar TaskFormScreen
8. Implementar TaskListScreen
9. Implementar TaskExecutionScreen
10. Testes e validação
11. Commit e documentação

---

**Preparado por**: Equipe de Desenvolvimento  
**Data**: 2025-10-16  
**Versão**: 1.1 (ATUALIZADO com categorias e imagens)  
**Aprovação**: ⏳ Pendente  
**Próxima Ação**: Aguardando aprovação para iniciar implementação
