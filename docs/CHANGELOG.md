# CHANGELOG - Pequenos Passos

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [1.9.6] - 2025-10-21 - CORREÇÃO: UNIQUE Constraint ao Editar Steps 🐛

### Corrigido
- **🐛 BUG CRÍTICO: Erro ao salvar steps editados**
  - **Problema:** Ao editar steps de tarefa existente, app exibia erro "UNIQUE constraint failed: steps.id"
  - **Causa:** `SaveTaskUseCase` sempre fazia INSERT de steps, mesmo para edições (steps com IDs existentes)
  - **Solução:**
    - Adicionado parâmetro `taskId` opcional ao `SaveTaskUseCase` (null = criar, > 0 = editar)
    - Implementada lógica para diferenciar UPDATE vs INSERT de tarefa
    - Steps antigos são deletados antes de inserir os novos (evita conflito de IDs)
    - Steps novos sempre inseridos com `id = 0` (forçar novo registro)
    - `TaskFormViewModel` agora passa `taskId` ao use case corretamente
  - **Impacto:** Edição completa de steps agora funciona perfeitamente
  - **Arquivos:** 
    - `SaveTaskUseCase.kt` - Reescrito para suportar edição
    - `TaskFormViewModel.kt` - Passa taskId ao use case
  - **Documentação:** `MVP07_BUGFIX_STEPS_EDICAO_V1.9.6.md`

### Modificado
- **SaveTaskUseCase:**
  - Constructor agora aceita `taskId: Long? = null` como primeiro parâmetro
  - Usa `updateTask()` quando taskId > 0 (edição)
  - Usa `insertTask()` quando taskId é null (criação)
  - Deleta steps antigos antes de inserir novos (edição)
  - Documentação atualizada com versão 1.9.6

- **TaskFormViewModel:**
  - Método `saveTask()` passa `currentState.taskId` ao use case
  - Comentários atualizados explicando criação vs edição

### Técnico
- **Estratégia de Persistência:** DELETE ALL + INSERT ALL (mais simples e confiável)
- **Room Database:** Cascade delete garante consistência de dados
- **Validação:** Testes manuais completos (criar, editar, adicionar, remover, reordenar steps)

### Versionamento
- **versionCode:** 15 → **16**
- **versionName:** 1.9.5 → **1.9.6**

---

## [1.9.5] - 2025-10-20 - CORREÇÃO: Steps Não Apareciam na Edição 🐛

### Corrigido
- **🐛 BUG: Steps não apareciam ao editar tarefa existente**
  - **Problema:** Ao clicar em "✏️ Editar" em uma tarefa, os steps não eram carregados
  - **Causa:** `TaskFormViewModel` não estava usando `GetStepsByTaskUseCase` para carregar steps
  - **Solução:** 
    - Adicionado `GetStepsByTaskUseCase` ao construtor do ViewModel
    - Implementada função de carregamento de steps na `loadTask()`
    - Steps agora são carregados e ordenados corretamente
    - Adicionados logs de debug para rastreamento
  - **Impacto:** Edição de tarefas agora mostra todos os steps existentes
  - **Arquivo:** `TaskFormViewModel.kt` - função `loadTask()`

### Modificado
- **TaskFormViewModel:**
  - Constructor agora recebe 3 use cases (incluindo `GetStepsByTaskUseCase`)
  - Função `loadTask()` completamente reescrita
  - Carregamento de task e steps em paralelo via coroutines
  - Try-catch para melhor tratamento de erros

### Versionamento
- **versionCode:** 14 → **15**
- **versionName:** 1.9.4 → **1.9.5**

---

## [1.9.4] - 2025-10-20 - CORREÇÃO CRÍTICA: Bug de Crash na Conclusão 🐛

### Corrigido
- **🐛 BUG CRÍTICO: App crashava ao concluir última tarefa**
  - **Problema:** App fechava ao clicar "✓ Concluir" no último step
  - **Causa:** Erro de sintaxe no `MainActivity.kt` - chave `}` faltante na rota `task_execution`
  - **Solução:** 
    - Adicionada chave `}` faltante após composable `task_execution`
    - Removidas chaves `}` duplicadas/extras
    - Sintaxe Kotlin corrigida completamente
  - **Impacto:** Tela de conclusão agora funciona perfeitamente
  - **Arquivo:** `MainActivity.kt` - linhas 80-110
  - **Documentação:** `MVP07_BUGFIX_CRASH_V1.9.4.md`

### Modificado
- **📱 Navegação Corrigida**
  - Tela de conclusão navega corretamente para `task_list`
  - Botão atualizado: "✓ Voltar para Atividades"
  - Decode correto do título da tarefa (URLDecoder)

### Versionamento
- **versionCode:** 13 → **14**
- **versionName:** 1.9.3 → **1.9.4**

---

## [1.9.3] - 2025-10-20 - Preview de Imagem e Tela de Conclusão ⭐

### Adicionado
- **🖼️ Preview de Imagem no StepDialog** ✅
  - Card verde com "✅ Imagem Selecionada" após seleção
  - Miniatura da imagem (80dp) aparece no dialog
  - Feedback visual claro para o usuário
  - Confirmação imediata de que imagem foi adicionada

- **🎉 Tela de Conclusão com Feedback Positivo** ⭐ NOVO
  - 10 mensagens de parabéns diferentes (aleatórias)
  - 8 mensagens de sucesso variadas (aleatórias)
  - Exibição de estrelas ganhas com animação
  - Estrelas aparecem uma por uma com rotação
  - Animações suaves (spring, bounce)
  - Emoji grande de celebração
  - Evita repetição/mecanização
  - Reforço positivo adequado para crianças com TEA
  - Arquivo: `TaskCompletionScreen.kt`

### Modificado
- **TaskExecutionScreen:** Navegação para tela de conclusão ao concluir
- **TaskExecutionViewModel:** Campo `taskStars` adicionado ao estado
- **MainActivity:** Rota `task_completion/{taskTitle}/{stars}` adicionada

---

## [1.9.2] - 2025-10-20 - MVP-07 Reorganização da Arquitetura de Navegação 🏗️

### 🎯 Motivação
Separar área de **configuração** (adultos) da área de **execução** (crianças) para prevenir exclusões e edições acidentais por crianças com TEA.

### Adicionado
- **📱 TaskManagementScreen** - Nova tela de gerenciamento (Edição de Tarefas) ⭐ NOVO
  - Lista todas as tarefas com opções de edição e exclusão
  - Botão ✏️ (editar tarefa) - navega para TaskFormScreen
  - Botão 🗑️ (deletar tarefa) - com dialog de confirmação
  - FAB para adicionar nova tarefa
  - **Área de Configuração** - protegida para adultos
  - Arquivo: `presentation/screens/taskmanagement/TaskManagementScreen.kt`

- **🖼️ Miniatura de Imagem nas Tarefas**
  - TaskListScreen agora exibe miniatura (80dp × 80dp) da imagem principal da tarefa
  - Posicionada à esquerda do card
  - Fallback: emoji da categoria em box colorido se não houver imagem
  - Melhora visual e identificação rápida das tarefas

### Modificado
- **🏠 HomeScreen**
  - Botão "Nova Tarefa" **renomeado** para **"Edição de Tarefas"**
  - Navegação alterada: `task_form` → `task_management`
  - Clareza no propósito: área de configuração separada

- **📱 TaskListScreen** (Atividades) - **SIMPLIFICADA** 🔒
  - **REMOVIDO:** Botão 🗑️ de exclusão (prevenção de acidentes)
  - **REMOVIDO:** FloatingActionButton de adicionar tarefa
  - **ADICIONADO:** Miniatura da imagem da tarefa à esquerda
  - Foco exclusivo: **executar tarefas**
  - Área de execução segura para crianças
  - EmptyState atualizado: direciona para "Edição de Tarefas"

- **🗺️ Navegação (MainActivity)**
  - Nova rota: `task_management` → TaskManagementScreen
  - Separação clara:
    - **Configuração:** task_management (com edição/exclusão)
    - **Execução:** task_list (somente executar)

### Arquitetura
```
HomeScreen
  ├── "Edição de Tarefas" → TaskManagementScreen (Área de Configuração)
  │    ├── ✏️ Editar → TaskFormScreen
  │    ├── 🗑️ Deletar → Dialog de confirmação
  │    └── ➕ FAB → Nova tarefa
  │
  └── "Atividades" → TaskListScreen (Área de Execução) 🔒
       └── ▶️ Executar → TaskExecutionScreen
```

### Segurança 🛡️
- Crianças com TEA não podem mais deletar ou editar tarefas acidentalmente
- Área de execução (Atividades) completamente isolada
- Área de configuração (Edição de Tarefas) para adultos/responsáveis

### Documentação Atualizada
- `MVP07_WIREFRAMES.md` - Wireframes da nova arquitetura
- `SPECIFICATION_FOR_APP.md` - Navegação atualizada

---

## [1.9.1] - 2025-10-20 - MVP-07 Fase 3 - Funcionalidade de Exclusão de Tarefas

### Adicionado
- **🗑️ Exclusão de Tarefas**
  - **DeleteTaskUseCase**: Use case para deletar tarefas com validação
  - Exclusão em cascata: ao deletar tarefa, todos os steps são removidos automaticamente
  - Validação: verifica se tarefa existe antes de deletar
  - Arquivo: `domain/usecase/DeleteTaskUseCase.kt`

### Modificado (MOVIDO para TaskManagementScreen na v1.9.2)
- **📱 TaskManagementScreen** (era TaskListScreen)
  - Botão de exclusão (ícone de lixeira) nos cards de tarefa
  - Dialog de confirmação antes de deletar
  - Feedback visual após exclusão bem-sucedida
  - Tratamento de erros na exclusão

- **🎨 TaskListViewModel**
  - Adicionado `deleteTask(taskId: Long)` para processar exclusões
  - Estados para controlar confirmação de exclusão
  - Atualização automática da lista após exclusão


---

## [1.9.0] - 2025-10-20 - MVP-07 Fase 3 - Correções Críticas

### Corrigido
- **🐛 Bug: Imagem dos steps não aparecia durante execução**
  - **Causa:** TaskExecutionViewModel carregava steps sem incluir imageUrl e durationSeconds
  - **Solução:** Modificado `loadTask()` para usar `getStepsByTask()` que retorna steps completos
  - **Impacto:** Imagens agora aparecem corretamente na TaskExecutionScreen
  - **Arquivo:** `TaskExecutionViewModel.kt`

- **🐛 Bug: Timer sempre fixo em 60 segundos**
  - **Causa:** Mesma do bug anterior - steps incompletos sem durationSeconds
  - **Solução:** Com steps completos, `durationSeconds` agora é respeitado
  - **Impacto:** Timer usa duração configurada em cada step
  - **Arquivo:** `TaskExecutionViewModel.kt`

### Modificado
- **🔧 Script compilar_e_testar.bat**
  - Adicionado fallback: desinstala e reinstala quando instalação falha
  - Melhoria na detecção de erros
  - Mensagens mais claras de status

- **📱 Versionamento**
  - Incrementado versionCode: `10` → `11`
  - Versão exibida na HomeScreen para facilitar debug

### Documentação
- **📝 MVP07_CORRECOES_V1.9.0.md**
  - Documentação detalhada dos problemas e soluções
  - Logs de debug adicionados para diagnóstico
  - Hipóteses e análise dos bugs

---

## [1.8.1] - 2025-10-19 - Correção de Bug Crítico na Galeria

### Corrigido
- **🐛 Bug Crítico: Seleção de imagens via galeria**
  - **Erro:** `IllegalArgumentException: cannot use a recycled source in createBitmap`
  - **Causa:** Bitmap estava sendo reciclado prematuramente na função `correctImageOrientation()`
  - **Solução:** Adicionada verificação `if (rotatedBitmap != bitmap)` antes de reciclar
  - **Impacto:** Seleção de galeria agora funciona 100%
  - **Arquivo:** `ImagePicker.kt` - função `correctImageOrientation()`
  - **Documentação:** `MVP07_CORRECAO_GALERIA_BITMAP.md`

### Adicionado
- **Script de compilação e instalação:** `compilar_e_instalar.bat`
  - Facilita o processo de build e deploy durante desenvolvimento
  - Verifica erros de compilação e instalação
  - Exibe mensagens de sucesso/erro claras

---

## [1.8.0] - 2025-10-16 - MVP-07 Planejamento e Documentação

### Planejado
- **4 Telas principais de interface com funcionalidades avançadas**:
  - OnboardingScreen (Hub de Cadastros com 3 opções)
  - TaskFormScreen (com suporte a imagens e timer)
  - TaskListScreen (com expansão inline)
  - TaskExecutionScreen (com timer circular visual)

### Adicionado à Documentação
- **PATHS.md v1.3**: Fluxo de navegação completo do MVP-07
  - OnboardingScreen como Hub de Cadastros (3 opções sempre visíveis)
  - Separação clara: Área de Configuração (Hub) vs Área de Execução (Atividades)
  - Proteção contra edições acidentais (edição movida para Hub)
- **MVP07_PANORAMA_GERAL.md**: Documento completo do MVP-07
  - Checklist de implementação (5 fases)
  - Componentes reutilizáveis detalhados
  - Estimativa de tempo: 9-13 dias (2-3 semanas)
  - Impacto nos MVPs anteriores documentado
- **MVP07_WIREFRAMES.md**: Wireframes detalhados das 4 telas
  - Wireframes com imagens e timer visual
  - Componentes reutilizáveis especificados

### Funcionalidades Planejadas

#### Suporte a Imagens nos Steps ⭐ NOVO
- Seleção via câmera ou galeria
- Preview de imagem
- Redimensionamento automático (max 1024px)
- Armazenamento local
- Validações: JPG/PNG, tamanho máximo

#### Timer Configurável por Step ⭐ NOVO
- Range: 5-600 segundos (5s a 10min)
- Slider visual
- Dropdown com valores predefinidos
- Padrão: 60 segundos

#### Timer Circular Visual Animado ⭐ NOVO
- Tamanho: 200dp × 200dp
- Cores dinâmicas (Verde > Amarelo > Vermelho)
- Animação suave (1s updates)
- Controles de pausar/retomar
- Alarme ao fim (som + vibração)
- Dialog com opções (+30s ou concluir)

#### Hub de Cadastros (OnboardingScreen) ⭐ NOVO
- **3 opções sempre disponíveis**:
  1. Cadastro Criança (editar perfil)
  2. Cadastro de Tarefa (criar nova)
  3. Editar Tarefa (acessar lista e editar)
- Acessível a qualquer momento
- Área de configuração para adultos

#### Navegação com Segurança 🛡️ ⭐ NOVO
- **Opção "Editar" REMOVIDA da TaskListScreen**
- Edição protegida no Hub de Cadastros
- Previne alterações acidentais por crianças com TEA
- TaskListScreen: apenas Executar e Cancelar
- Melhora significativa na acessibilidade

#### TaskListScreen com Expansão Inline ⭐ NOVO
- Cards de tarefas expandem ao tocar
- Opções inline (sem navegar)
- Indicadores de imagens (🖼️ × quantidade)
- Tempo total do timer exibido

### Alterações nas Entidades (Planejadas)

#### Step Entity - Novos Campos
```kotlin
- imageUrl: String? (path local da imagem)
- durationSeconds: Int (timer 15-600s, padrão 60)
```

### Alterações no Database (Planejadas)

#### Tabela steps - Novas Colunas
```sql
- image_url TEXT (nullable)
- duration_seconds INTEGER DEFAULT 60
```

#### Migration Necessária
- Criar migration do Room para adicionar 2 colunas
- Manter compatibilidade com dados existentes

### Impacto nos MVPs Anteriores

#### MVP-02 (Entidades) - ⚠️ IMPACTADO
- Atualizar classe Step com novos campos

#### MVP-03 (Database) - ⚠️ IMPACTADO
- Criar migration para tabela steps
- Adicionar colunas image_url e duration_seconds

#### MVP-04 (Repositórios) - ⚠️ IMPACTADO
- Atualizar StepRepository para novos campos

#### MVP-05 (Use Cases) - ⚠️ IMPACTADO
- Adicionar validações em SaveTaskUseCase:
  - Timer entre 15-600s
  - Path de imagem válido (se presente)

#### MVP-06 (Theme) - ✅ SEM IMPACTO
- Componentes novos usarão tema já existente

### Componentes Reutilizáveis a Criar

1. **ImagePicker**: Seleção câmera/galeria com preview
2. **TimerInput**: Slider + dropdown para timer
3. **CircularTimer**: Timer visual animado
4. **StepItem**: Card completo de step

### Bibliotecas a Adicionar
```kotlin
- androidx.activity:activity-compose:1.8.0 (câmera/galeria)
- io.coil-kt:coil-compose:2.5.0 (carregamento imagens)
```

### Checklist de Implementação (5 Fases)
- [ ] Fase 1: Preparação (1-2 dias) - Entidades, database, migrations
- [ ] Fase 2: Componentes (2-3 dias) - ImagePicker, TimerInput, CircularTimer, StepItem
- [ ] Fase 3: Telas (3-4 dias) - 4 telas principais
- [ ] Fase 4: Testes (2-3 dias) - Unitários, integração, UI, anti-regressão
- [ ] Fase 5: Documentação (1 dia) - Atualizar docs, screenshots

### Estimativa
- **Tempo total**: 9-13 dias (2-3 semanas)
- **Complexidade**: Média-Alta
- **Valor entregue**: Alto (funcionalidades essenciais para público-alvo)

### Referências
- PATHS.md v1.3
- MVP07_PANORAMA_GERAL.md
- MVP07_WIREFRAMES.md
- SPECIFICATION_FOR_APP.md

---

## [1.7.0] - 2025-10-15 - MVP-06 Completo

### Adicionado
- Sistema completo de Theme e Design System
- PequenosPassosTheme com suporte a modo claro e escuro
- Paleta de cores personalizada (Primary, Secondary, Tertiary)
- Tipografia customizada com famílias de fontes
- Sistema de espaçamentos padronizado
- Testes de tema completos

### Validação
- ✅ Todos os testes passando (142 testes)
- ✅ Build: SUCCESS
- ✅ Anti-regressão: MVPs 01-05 funcionando

---

## [1.6.0] - 2025-10-14 - MVP-05 Completo

### Adicionado
- 10 Use Cases implementados:
  - CheckFirstRunUseCase
  - CompleteOnboardingUseCase
  - GetChildProfileUseCase
  - SaveChildProfileUseCase
  - GetTaskByIdUseCase
  - GetTasksOrderedByTimeUseCase
  - SaveTaskUseCase
  - UpdateTaskStatusUseCase
  - GetStepsByTaskUseCase
  - UpdateStepCompletionUseCase

### Testes
- 44 testes unitários para Use Cases (100% passando)
- Cobertura completa de cenários de sucesso e erro

### Validações
- Nome obrigatório (mínimo 2 caracteres)
- Horário formato HH:mm válido
- Estrelas entre 1 e 5
- AppResult para tratamento de erros
- Flow para dados reativos

---

## [1.5.0] - 2025-10-13 - MVP-04 Completo

### Adicionado
- Camada de Repositórios completa
- 4 interfaces de repositório no domain/repository
- 4 implementações em data/repository com Hilt
- RepositoryModule para injeção de dependências
- Testes unitários para repositórios (37 testes)

### Repositórios Implementados
- AppSettingsRepository
- ChildProfileRepository
- TaskRepository
- StepRepository

---

## [1.4.0] - 2025-10-12 - MVP-03 Completo

### Adicionado
- Banco de dados Room completo
- AppDatabase com 4 tabelas
- 4 DAOs implementados
- Converters para tipos complexos
- DatabaseModule para Hilt
- Testes de integração do banco (32 testes)

### Tabelas
- app_settings
- child_profiles
- tasks
- steps

---

## [1.3.0] - 2025-10-11 - MVP-02 Completo

### Adicionado
- 5 entidades de domínio
- Modelos de dados completos
- Enums TaskStatus e TaskType
- Classe AppResult para tratamento de erros
- Testes unitários para entidades (29 testes)

### Entidades
- AppSettings
- ChildProfile
- Task
- Step
- AppResult

---

## [1.2.0] - 2025-10-10 - MVP-01 Completo

### Adicionado
- Estrutura base do projeto Android
- Configuração Hilt/Dagger
- Clean Architecture (data, domain, presentation)
- 5 telas iniciais (Splash, Home, Debug, TtsTest, AsrTest)
- Sistema de navegação básico
- Tema temporário

### Infraestrutura
- Gradle configurado
- Dependências principais
- Estrutura de pastas Clean Architecture

---

## Legenda de Status

- ✅ Implementado e validado
- ⏳ Em desenvolvimento
- 📋 Planejado
- ❌ Bloqueado

---

## Próximos Passos

### MVP-07 - Telas de Interface (Planejado)
- Implementar telas de cadastro
- Implementar telas de execução de tarefas
- Refinar navegação entre telas
- Adicionar componentes de UI reutilizáveis

### MVP-08 - Integração Completa (Futuro)
- Conectar todas as camadas
- Implementar ViewModels
- Fluxo end-to-end funcionando

### MVP-09 - Testes E2E (Futuro)
- Testes de interface
- Testes de integração completos
- Validação em dispositivos reais

### MVP-10 - Polimento e Release (Futuro)
- Ajustes finais de UI/UX
- Otimizações de performance
- Preparação para produção
