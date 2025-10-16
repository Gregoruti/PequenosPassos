# MVP-07 DOCUMENTATION STATUS

## Status da Documentação e Planejamento do MVP-07

**Data**: 2025-10-15  
**Versão**: 1.0  
**Status**: PLANEJAMENTO COMPLETO ✅

---

## 1. Documentação Criada/Atualizada

### ✅ Documentos Completos

- [x] **CHANGELOG.md** - Histórico completo MVPs 01-06 + estrutura para MVP-07
- [x] **GUIDELINES.md** - Diretrizes de desenvolvimento, padrões, processo MVP
- [x] **PATHS.md** - Navegação completa atual e planejada (MVP-07+)
- [x] **SPECIFICATION_FOR_APP.md** - Especificação técnica detalhada
- [x] **README.md** - Visão geral do projeto, tecnologias, status
- [x] **RESUMO_EXECUTIVO.md** - Resumo para compartilhar com colegas
- [x] **MVP07_DOCUMENTATION_STATUS.md** - Este arquivo (meta-documentação)

---

## 2. Estado da Documentação por Tópico

### 2.1 Navegação e Fluxos (PATHS.md)

**Status**: ✅ COMPLETO

#### Fluxo Atual Documentado (MVP-06):
```
SplashScreen → HomeScreen → DebugScreen → [TtsTest/AsrTest]
```

#### Fluxo Planejado Documentado (MVP-07):
```
SplashScreen 
    ↓
HomeScreen
    ├── "Cadastro" → TaskFormScreen (adicionar tarefa)
    ├── "Cadastro" → OnboardingScreen (adicionar usuário)
    ├── "Teste Rápido" → TaskExecutionScreen
    ├── "Atividades" → TaskListScreen
    │                   └── Menu → Executar/Editar/Cancelar
    └── "Debug" → DebugScreen
```

**Observação do Usuário**: O fluxo inclui TaskFormScreen e OnboardingScreen no mesmo botão "Cadastro". Isso pode ser refinado com lógica condicional ou menu contextual.

---

### 2.2 Telas Planejadas (Detalhamento Completo)

#### ✅ OnboardingScreen
- Rota: `"onboarding"`
- Campos: Nome, Data de Nascimento, Observações
- Validações: Nome min. 2 caracteres, Data válida
- Navegação: Sucesso/Cancelar → HomeScreen

#### ✅ TaskFormScreen
- Rota: `"task_form"` ou `"task_form/{taskId}"`
- Modo: Criar ou Editar
- Campos: Nome, Descrição, Horário, Tipo, Estrelas, Steps
- Validações: Nome obrigatório, Horário HH:mm, Estrelas 1-5, Min. 1 step
- Navegação: Salvar/Cancelar → Voltar

#### ✅ TaskListScreen
- Rota: `"task_list"`
- Componentes: Lista de Cards, FAB "Adicionar"
- Ações: Executar, Editar, Cancelar, Excluir
- Navegação: Card → Menu, FAB → TaskFormScreen

#### ✅ TaskExecutionScreen
- Rota: `"task_execution/{taskId}"`
- Componentes: Progresso, Step atual, TTS, Botões (Concluir/Voltar/Pular)
- Fluxo: Carregar → Exibir steps → TTS → Conclusão
- Navegação: Conclusão → Home/TaskList, Cancelar → Voltar

---

### 2.3 Rotas de Navegação

**Rotas Implementadas** (MVP-06):
```kotlin
composable("splash") { SplashScreen(navController) }
composable("home") { HomeScreen(navController) }
composable("debug") { DebugScreen(navController) }
composable("tts_test") { TtsTestScreen(navController) }
composable("asr_test") { AsrTestScreen(navController) }
```

**Rotas Planejadas** (MVP-07):
```kotlin
composable("onboarding") { OnboardingScreen(navController) }
composable("task_form") { TaskFormScreen(navController) }
composable("task_form/{taskId}", arguments = ...) { TaskFormScreen(...) }
composable("task_list") { TaskListScreen(navController) }
composable("task_execution/{taskId}", arguments = ...) { TaskExecutionScreen(...) }
```

---

## 3. Revisão do Fluxo de Navegação (Sugestão do Usuário)

### 3.1 Modificações Propostas

O usuário sugeriu clarificar o botão "Cadastro" que pode levar a:
- **Adicionar Tarefa** → TaskFormScreen
- **Adicionar Usuário** → OnboardingScreen

### 3.2 Solução Proposta

**Opção 1: Lógica Condicional**
```kotlin
Botão "Cadastro" onClick:
    if (childProfileExists) {
        navigate("task_form")
    } else {
        navigate("onboarding")
    }
```

**Opção 2: Dois Botões Separados** (RECOMENDADO)
```kotlin
HomeScreen:
    - "Cadastrar Criança" (visível apenas se não houver perfil)
    - "Nova Tarefa" (visível apenas se houver perfil)
    - "Atividades"
    - "Teste Rápido"
    - "Debug"
```

**Opção 3: Menu Contextual**
```kotlin
Botão "Cadastro" → DropdownMenu:
    - "Cadastrar Criança"
    - "Cadastrar Tarefa"
```

### 3.3 Decisão de Design

**RECOMENDAÇÃO**: **Opção 2** (Dois botões separados)

**Justificativa**:
- Mais claro para usuário final (crianças/pais)
- Evita menu extra (menos cliques)
- Visibilidade condicional é fácil de implementar
- UX mais direta e intuitiva

**Implementação**:
```kotlin
@Composable
fun HomeScreen(navController: NavController, hasProfile: Boolean) {
    if (!hasProfile) {
        Button(onClick = { navController.navigate("onboarding") }) {
            Text("Cadastrar Criança")
        }
    } else {
        Button(onClick = { navController.navigate("task_form") }) {
            Text("Nova Tarefa")
        }
        Button(onClick = { navController.navigate("task_list") }) {
            Text("Atividades")
        }
        Button(onClick = { navController.navigate("task_execution/1") }) {
            Text("Teste Rápido")
        }
    }
    Button(onClick = { navController.navigate("debug") }) {
        Text("Debug")
    }
}
```

---

## 4. Estratégia de Implementação MVP-07

### 4.1 Abordagem Incremental (RECOMENDADO)

**MVP-07A**: Onboarding + Navegação Básica
- OnboardingScreen
- Lógica de primeira execução
- Navegação condicional na HomeScreen
- **Estimativa**: 2-3 dias

**MVP-07B**: Formulário de Tarefas + Imagens/Timer
- TaskFormScreen (criar/editar)
- **Seleção de imagens** por step (galeria/câmera)
- **Configuração de timer** por step
- Validações
- Adicionar/remover steps dinamicamente
- **Estimativa**: 4-5 dias (aumentado devido às novas features)

**MVP-07C**: Lista e Execução com Timer Visual
- TaskListScreen
- TaskExecutionScreen (com **timer funcional** e **exibição de imagens**)
- **Timer circular** animado
- **Alarme sonoro** e vibração
- Navegação completa
- **Estimativa**: 5-6 dias (aumentado devido ao timer)

**TOTAL MVP-07**: 11-14 dias (2-3 semanas)

---

## 5. Componentes Reutilizáveis a Criar

### 5.1 Componentes de Input
- [ ] `PrimaryButton` - Botão principal com estilo padrão
- [ ] `TextFieldWithLabel` - Campo de texto com validação
- [ ] `TimePickerField` - Seletor de horário (HH:mm)
- [ ] `DatePickerField` - Seletor de data
- [ ] `StarRatingPicker` - Seletor de estrelas (1-5)
- [ ] `TaskTypeDropdown` - Dropdown para tipo de tarefa

### 5.2 Componentes de Display
- [ ] `TaskCard` - Card de tarefa para lista
- [ ] `StepItem` - Item de step (com checkbox)
- [ ] `ProgressBar` - Barra de progresso customizada
- [ ] `EmptyState` - Estado vazio (sem tarefas)
- [ ] `ConfirmationDialog` - Dialog de confirmação

### 5.3 Componentes de Layout
- [ ] `ScreenScaffold` - Scaffold padrão com TopBar
- [ ] `LoadingOverlay` - Indicador de carregamento
- [ ] `ErrorMessage` - Mensagem de erro padronizada

---

## 6. ViewModels Necessários (MVP-08)

Planejado para próximo MVP, mas já documentado:

- [ ] `OnboardingViewModel`
- [ ] `TaskFormViewModel`
- [ ] `TaskListViewModel`
- [ ] `TaskExecutionViewModel`

Cada ViewModel terá:
- StateFlow para UI state
- Integração com Use Cases
- Tratamento de erros
- Validações

---

## 7. Alterações em MVPs Anteriores

### 7.1 Impacto em MVPs Implementados

**MVP-01 a MVP-05**: ❌ SEM IMPACTO
- Entidades, Database, Repositórios, Use Cases permanecem inalterados
- Navegação é aditiva (novas rotas)

**MVP-06 (Theme)**: ✅ UTILIZARÁ
- Telas usarão Design System implementado
- Componentes seguirão paleta de cores
- Tipografia e espaçamento já definidos

### 7.2 Necessidade de Redocumentação

**Arquivos que NÃO precisam mudança**:
- MVP01-06 VALIDATION_SUMMARY (histórico)
- Testes existentes (continuam válidos)

**Arquivos que FORAM atualizados**:
- ✅ CHANGELOG.md (novo)
- ✅ PATHS.md (expandido)
- ✅ GUIDELINES.md (novo)
- ✅ README.md (criado)
- ✅ SPECIFICATION_FOR_APP.md (criado)

---

## 8. Checklist de Implementação MVP-07

### Pré-Implementação
- [x] Documentação completa criada
- [x] Fluxo de navegação definido
- [ ] Wireframes/mockups (PRÓXIMO PASSO)
- [ ] Aprovação do design/fluxo

### Durante Implementação
- [ ] Criar componentes reutilizáveis
- [ ] Implementar OnboardingScreen
- [ ] Implementar TaskFormScreen
- [ ] Implementar TaskListScreen
- [ ] Implementar TaskExecutionScreen
- [ ] Adicionar rotas de navegação
- [ ] Conectar navegação condicional
- [ ] Testes básicos de UI (smoke tests)

### Pós-Implementação
- [ ] Validação anti-regressão (MVPs 01-06)
- [ ] Build: SUCCESS
- [ ] Testes em dispositivo real
- [ ] Atualizar CHANGELOG.md
- [ ] Criar MVP07_VALIDATION_SUMMARY.md
- [ ] Incrementar versão (1.7.0 → 1.8.0)
- [ ] Commit e push

---

## 9. Perguntas em Aberto (Para Discussão)

1. **Botão "Cadastro"**: Usar Opção 2 (dois botões separados)?
2. **Wireframes**: Criar antes de implementar?
3. **TaskExecutionScreen**: Incluir TTS no MVP-07 ou deixar para MVP-08?
4. **Testes de UI**: Criar testes básicos ou deixar para MVP-09?
5. **Divisão MVP-07**: Fazer em 3 partes (A, B, C) ou tudo de uma vez?

---

## 10. Recursos Necessários

### Tempo Estimado
- **MVP-07 completo**: 9-12 dias de desenvolvimento
- **Com testes**: +2-3 dias
- **TOTAL**: 2-3 semanas

### Conhecimentos Necessários
- ✅ Jetpack Compose (já temos)
- ✅ Navegação Compose (já implementada)
- ⚠️ Compose UI Testing (a aprender)
- ⚠️ Material Design 3 components avançados (a aprender)

---

## 11. Riscos e Mitigações

| Risco | Probabilidade | Impacto | Mitigação |
|-------|---------------|---------|-----------|
| TaskExecutionScreen muito complexa | Alta | Alto | Dividir em MVP-07C separado |
| **Timer preciso pode ter atrasos** | Média | Médio | Usar Flow com delay preciso |
| **Imagens grandes podem travar UI** | Média | Alto | Redimensionar antes de salvar (max 1024px) |
| **Permissões de câmera rejeitadas** | Baixa | Médio | Fallback para galeria apenas |
| Validações de formulário bugs | Média | Médio | Usar Use Cases já testados |
| UX confusa para crianças | Média | Alto | Criar protótipos e validar |
| Regressão em MVPs anteriores | Baixa | Alto | Testes anti-regressão rigorosos |

---

## 12. Conclusão

### Status Atual: ✅ PRONTO PARA IMPLEMENTAÇÃO

**Documentação**: 100% completa  
**Planejamento**: Detalhado e viável  
**Riscos**: Identificados e mitigados  
**Timeline**: Realista (2-3 semanas)  

### Recomendação: 🚀 SEGUIR EM FRENTE

**Próximo Passo Imediato**:
1. Criar wireframes/drafts das telas
2. Validar UX com stakeholders
3. Iniciar MVP-07A (Onboarding)

---

**Preparado por**: Equipe de Desenvolvimento  
**Aprovado por**: (Pendente)  
**Data de Início Planejada**: 2025-10-15  
**Data de Conclusão Estimada**: 2025-11-05
