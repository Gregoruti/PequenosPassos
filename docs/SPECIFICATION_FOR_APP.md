# ESPECIFICAÇÃO DO APLICATIVO - Pequenos Passos

## Aplicativo de Auxílio para Rotinas Infantis

---

## 1. Visão Geral do Projeto

### 1.1 Propósito
O **Pequenos Passos** é um aplicativo Android desenvolvido para auxiliar crianças com necessidades especiais (TEA, TDAH, dificuldades de aprendizagem) a executarem tarefas do dia a dia de forma estruturada, visual e interativa.

### 1.2 Público-Alvo
- Crianças de 4 a 12 anos
- Pais e cuidadores
- Terapeutas e educadores

### 1.3 Diferencial
- Interface visual e intuitiva
- Síntese de voz (TTS) para instruções
- Reconhecimento de voz (ASR) para interação
- Sistema de recompensas (estrelas)
- Funcionamento offline

---

## 2. Funcionalidades Principais

### 2.1 Gestão de Perfil
- Cadastro de perfil da criança
- Informações: nome, data de nascimento, observações
- Primeira execução com onboarding

### 2.2 Gestão de Tarefas
- Criar tarefas personalizadas
- Editar tarefas existentes
- Definir horários para tarefas
- Organizar tarefas por tipo
- Sistema de estrelas (1-5)

### 2.3 Tarefas com Steps ⭐ IMPLEMENTADO (v1.9.0)
- Dividir tarefas em pequenos passos
- Ordem sequencial de execução
- Descrição textual de cada passo
- **✅ Suporte a imagens** por step (galeria ou câmera)
  - Seleção via câmera ou galeria
  - Preview de imagem
  - Redimensionamento automático (max 1024px)
  - Armazenamento local seguro
- **✅ Timer configurável** por step (5-600 segundos)
  - Slider visual para seleção
  - Dropdown com valores predefinidos
  - Padrão: 60 segundos
  - Validação de range (5s a 10min)
- Marcação de conclusão por step
### 2.7 Sistema de Recompensas
- Estrelas por tarefa concluída (1-5)
- Modo guiado passo a passo
- **✅ Exibição de imagens** dos steps durante execução
- Histórico de conquistas (planejado)
  - Cores dinâmicas (Verde > Amarelo > Vermelho)
  - Animação suave
  - Controles de pausar/retomar
- Leitura das instruções por voz (TTS) - planejado
- Barra de progresso visual
- Feedback motivacional
- Estatísticas de conclusão

### 2.5 Organização por Categorias ⭐ IMPLEMENTADO (v1.9.0)
- **27 categorias** organizadas em 5 grupos temáticos:
  - 🏠 Casa (limpeza, organização, tarefas domésticas)
  - 🧘 Autocuidado (higiene, saúde, bem-estar)
  - 🎓 Escola (estudos, lições, projetos)
  - 🎨 Lazer (hobbies, jogos, criatividade)
  - 🍽️ Alimentação (refeições, lanches, culinária)
- Cada categoria com emoji visual
- Campo obrigatório no cadastro de tarefas
- Exibição de categoria nos cards de lista

### 2.6 Gestão de Tarefas ⭐ IMPLEMENTADO (v1.9.1)
- Criar tarefas personalizadas
- Editar tarefas existentes
- **✅ Deletar tarefas** com confirmação
  - Dialog de confirmação antes de excluir
  - Exclusão em cascata (remove todos os steps)
  - Feedback visual após exclusão
- Definir horários para tarefas
- Organizar tarefas por categoria
- Sistema de estrelas (1-5)

### 2.5 Sistema de Recompensas
- Estrelas por tarefa concluída
- Feedback visual positivo
- Mensagens motivacionais
- Histórico de conquistas (futuro)

---

## 3. Tecnologias Utilizadas

### 3.1 Plataforma
#### Task ⭐ ATUALIZADO (v1.9.0)
- **Linguagem**: Kotlin 100%
- **Framework UI**: Jetpack Compose

### 3.2 Arquitetura
- **Padrão**: Clean Architecture
- **Camadas**: Presentation, Domain, Data
- **Injeção de Dependências**: Hilt/Dagger

### 3.3 Banco de Dados
- categoryId: String (obrigatório) ✅ IMPLEMENTADO
- imageUrl: String? (imagem principal da tarefa) ✅ IMPLEMENTADO
- **Room**: Banco local SQLite
- **Tabelas**: app_settings, child_profiles, tasks, steps

#### Step ⭐ ATUALIZADO (v1.9.0)
- **TTS**: Text-to-Speech nativo do Android
- **ASR**: Vosk (reconhecimento offline) - planejado
- **Imagens visuais**: Suporte a fotos nos steps para auxílio visual
- **Timers visuais**: Contagem regressiva clara e grande
- title: String (obrigatório)
- description: String (opcional)

- imageUrl: String? (path local da imagem) ✅ IMPLEMENTADO
- durationSeconds: Int (5-600s, padrão 60) ✅ IMPLEMENTADO

## 4. Estrutura de Dados

### 4.1 Entidades Principais

#### ChildProfile
```kotlin
- id: Long
- name: String (obrigatório, mín. 2 caracteres)
### 5.1 Implementados
- notes: String (opcional)
**MVP-05 (Base):**
- createdAt: Long (timestamp)
```

#### Task
```kotlin
- id: Long
- childId: Long (FK)
- name: String (obrigatório)
- description: String
- scheduledTime: String (HH:mm)
- type: TaskType (MORNING, AFTERNOON, NIGHT, ANYTIME)
**MVP-07 Fase 3 (v1.9.1):** ⭐ NOVO
11. **DeleteTaskUseCase**: Deleta tarefa com validação e cascata de steps

- status: TaskStatus (PENDING, COMPLETED, CANCELLED)
- stars: Int (1-5)
- createdAt: Long

#### Step
```kotlin
- id: Long
- taskId: Long (FK)
### 6.1 Implementadas
- description: String
**MVP-01 a MVP-06:**
- isCompleted: Boolean
2. **HomeScreen**: Menu principal com botões e indicador de versão
```

#### AppSettings
```kotlin
**MVP-07 (v1.9.0-1.9.5):** ⭐ IMPLEMENTADO
6. **OnboardingScreen**: Cadastro inicial do perfil da criança
7. **TaskFormScreen**: Criar/editar tarefas com categorias, imagens e timer
   - CategoryPicker integrado
   - ImagePicker para imagem principal
   - StepDialog com imagens e timer configurável
   - **Preview de imagem no StepDialog** ✅ (v1.9.3)
   - **Carregamento de steps na edição** ✅ (v1.9.5)
   - Validações completas
8. **TaskManagementScreen**: Gerenciamento de tarefas (Área de Configuração) ⭐ (v1.9.2)
   - Lista todas as tarefas
   - Botão ✏️ editar tarefa
   - Botão 🗑️ deletar tarefa com confirmação
   - FAB para adicionar nova tarefa
   - **Área protegida para adultos/responsáveis**
9. **TaskListScreen**: Listar tarefas (Área de Execução - Simplificada) 🔒 (v1.9.2)
   - **Miniatura da imagem da tarefa** (80dp × 80dp) à esquerda ⭐ (v1.9.2)
   - Indicadores visuais (categoria, imagens, duração)
   - Botão executar tarefa (único)
   - **SEM opções de edição/exclusão** (segurança para crianças TEA)
10. **TaskExecutionScreen**: Executar tarefa passo a passo
   - Timer circular visual
   - Exibição de imagens dos steps
   - Progresso visual
   - Duração configurável por step
   - **Navegação para tela de conclusão** ✅ (v1.9.3-1.9.4)
11. **TaskCompletionScreen**: Tela de conclusão com feedback positivo ⭐ NOVO (v1.9.3)
   - 10 mensagens de parabéns variadas (aleatórias)
   - 8 mensagens de sucesso diferentes (aleatórias)
   - Exibição de estrelas ganhas com animação
   - Reforço positivo adequado para crianças com TEA
   - Botão para voltar às atividades

### 6.2 Planejadas (MVP-08+)

10. **TaskCompletionScreen**: Resultado da execução com estatísticas
11. **SettingsScreen**: Configurações do aplicativo
12. **StatisticsScreen**: Histórico e estatísticas de conclusão
## 5. Casos de Uso (Use Cases)

### 5.1 Implementados (MVP-05)

1. **CheckFirstRunUseCase**: Verifica se é primeira execução
2. **CompleteOnboardingUseCase**: Marca onboarding como concluído
3. **GetChildProfileUseCase**: Obtém perfil da criança (Flow reativo)
4. **SaveChildProfileUseCase**: Salva perfil com validações
5. **GetTaskByIdUseCase**: Busca tarefa por ID
6. **GetTasksOrderedByTimeUseCase**: Lista tarefas ordenadas por horário
7. **SaveTaskUseCase**: Salva tarefa com steps e validações
8. **UpdateTaskStatusUseCase**: Atualiza status da tarefa
9. **GetStepsByTaskUseCase**: Obtém steps de uma tarefa
10. **UpdateStepCompletionUseCase**: Marca step como concluído/não concluído

### 5.2 Planejados (MVP-08+)

- **Categoria**: Obrigatória (seleção de 27 categorias) ⭐ NOVO
- GetTaskStatisticsUseCase
- DeleteTaskUseCase
- ReorderStepsUseCase
- **Título do step**: Obrigatório (mínimo 1 caractere) ⭐ NOVO
- **Timer do step**: Entre 5-600 segundos (5s a 10min) ⭐ NOVO
- **Imagens**: Formato JPG/PNG, redimensionamento automático (max 1024px) ⭐ NOVO
- GetCompletedTasksUseCase
- GetPendingTasksUseCase

---

## 6. Telas do Aplicativo

### 6.1 Implementadas (MVP-01 a MVP-06)

1. **SplashScreen**: Tela inicial com logo (3s)
2. **HomeScreen**: Menu principal com botões
3. **DebugScreen**: Tela de desenvolvimento (exibe status dos MVPs)
4. **TtsTestScreen**: Teste de síntese de voz
5. **AsrTestScreen**: Teste de reconhecimento de voz

### 6.2 Planejadas (MVP-07)

6. **OnboardingScreen**: Cadastro inicial do perfil
7. **TaskFormScreen**: Criar/editar tarefas
8. **TaskListScreen**: Listar todas as tarefas
9. **TaskExecutionScreen**: Executar tarefa passo a passo
10. **TaskCompletionScreen**: Resultado da execução (MVP-08)

---

## 7. Fluxo de Uso Principal

```
1. Primeira execução
   ↓
2. OnboardingScreen (cadastrar criança)
   ↓
3. HomeScreen (menu principal)
   ↓
4. Opções:
   a) Cadastrar nova tarefa → TaskFormScreen
   b) Ver tarefas → TaskListScreen
   c) Teste rápido → TaskExecutionScreen
   d) Debug → DebugScreen
   ↓
5. Executar tarefa
   ↓
6. Seguir steps um a um
   ↓
7. Conclusão com estatísticas e estrelas
```

---

## 8. Regras de Negócio

### 8.1 Validações

- **Nome da criança**: Mínimo 2 caracteres
- **Nome da tarefa**: Obrigatório
- **Horário**: Formato HH:mm (ex: 08:30, 14:00)
- **Estrelas**: Entre 1 e 5
- **Steps**: Pelo menos 1 step por tarefa

### 8.2 Comportamentos

- Tarefas pendentes aparecem primeiro na lista
- Tarefas são ordenadas por horário agendado
- Steps devem ser executados em ordem
- Não é possível pular steps (decisão de design para aprendizado)
- Tarefa só é marcada como concluída quando todos os steps forem concluídos

---

## 9. Sistema de Temas

### 9.1 Cores (Material Design 3)
- **MVP-07**: Telas de interface ✅ IMPLEMENTADO (v1.9.0-1.9.5)
**Modo Claro:**
  - ✅ TaskFormScreen (com categorias, imagens, timer e preview)
  - ✅ TaskManagementScreen (edição/exclusão de tarefas)
  - ✅ TaskListScreen (com miniaturas e metadados)
- Tertiary: #7D5260 (rosa-acinzentado)
  - ✅ TaskCompletionScreen (feedback positivo com estrelas)
- Background: #FFFBFE
- Surface: #FFFBFE

### Fase 2: Interface (MVPs 07-08) ✅ MVP-07 CONCLUÍDO
  - **🐛 Bugs Corrigidos:**
    - ✅ v1.8.1: Galeria de imagens (bitmap recycled)
    - ✅ v1.9.0: Imagens dos steps não apareciam
    - ✅ v1.9.0: Timer fixo em 60 segundos
    - ✅ v1.9.4: Crash ao concluir tarefa (sintaxe MainActivity)
    - ✅ v1.9.5: Steps não apareciam na edição
- **MVP-07**: Telas de interface ✅ IMPLEMENTADO (v1.9.0-1.9.1)
  - ✅ OnboardingScreen (cadastro de perfil)
  - ✅ TaskFormScreen (com categorias, imagens e timer)
  - ✅ TaskListScreen (com metadados e exclusão)
  - ✅ TaskExecutionScreen (com timer visual e imagens)
  - ✅ CategoryPicker (27 categorias em 5 grupos)
  - ✅ ImagePicker (galeria e câmera)
  - ✅ CircularTimer (timer visual animado)
  - ✅ DeleteTaskUseCase (exclusão de tarefas)
- **MVP-08**: Melhorias e features avançadas 📋 PRÓXIMO
  - Timer em barra (substituindo circular)
  - Síntese de voz (TTS) integrada
  - Reconhecimento de voz (ASR)
  - Estatísticas e histórico
### 9.2 Tipografia

- Display: Grande, impactante
- Headline: Títulos de seções
- Title: Títulos de cards
- Body: Texto padrão
- Label: Rótulos e botões

### 9.3 Espaçamento

- Extra Small: 4dp
- Small: 8dp
- Medium: 16dp
- Large: 24dp
- Extra Large: 32dp

---

## 10. Requisitos Não Funcionais

### 10.1 Performance
- Tempo de carregamento < 2s
- Animações fluidas (60 fps)
- Resposta imediata a toques
- **Carregamento otimizado de imagens** (redimensionamento automático)
- **Timer preciso** (usando coroutines/Flow)

### 10.2 Acessibilidade
- Suporte a TalkBack
- Tamanhos de fonte ajustáveis
- Alto contraste
- Botões grandes (mínimo 48dp)

### 10.3 Compatibilidade
- Android 7.0 (API 24) ou superior
- Suporte a tablets
- Orientação portrait e landscape

### 10.4 Segurança
- Dados armazenados localmente
- Sem coleta de dados pessoais
- Privacidade total (offline-first)

---

## 11. Roadmap de Desenvolvimento

### Fase 1: Fundação (MVPs 01-06) ✅ CONCLUÍDO
- Estrutura base do projeto
- Entidades de domínio
- Banco de dados Room
- Repositórios
- Use Cases com lógica de negócio
- Theme e Design System

### Fase 2: Interface (MVPs 07-08) 📋 ATUAL
- **MVP-07**: Telas de interface
  - OnboardingScreen
  - TaskFormScreen
  - TaskListScreen
  - TaskExecutionScreen
- **MVP-08**: ViewModels e integração completa

### Fase 3: Testes e Polimento (MVPs 09-10) 📋 FUTURO
- **MVP-09**: Testes E2E e validação em dispositivos
- **MVP-10**: Polimento, otimizações e release

### Fase 4: Expansão (Futuro)
- Suporte a imagens nos steps
- Sistema de backup/restore
- Múltiplos perfis
- Relatórios para pais/terapeutas
- Integração com calendário

---

## 12. Métricas de Qualidade

### 12.1 Testes Atuais
- **Testes Unitários**: 142 testes (100% passando)
  - Entidades: 29 testes
  - Database: 32 testes
  - Repositórios: 37 testes
  - Use Cases: 44 testes

### 12.2 Cobertura de Código
- Use Cases: 100%
- Repositórios: ~90%
- DAOs: ~85%

### 12.3 Build
- Status: SUCCESS
- Tempo médio: ~40-45s
- Sem warnings críticos

---

## 13. Limitações Conhecidas

### 13.1 Versão Atual (1.7.0)
- Telas de interface ainda não implementadas (em desenvolvimento MVP-07)
- Reconhecimento de voz (ASR) planejado mas não integrado
- Um único perfil de criança por dispositivo

### 13.2 Planejado para Futuro
- Múltiplos perfis
- Sincronização entre dispositivos
- Gamificação mais elaborada
- Estatísticas e relatórios
- **Banco de imagens predefinidas** para steps comuns

---

## 14. Equipe e Contato

**Projeto Acadêmico**: UNIVESP - Engenharia de Computação  
**Disciplina**: Projeto Integrador VI  
**Ano**: 2025  
**Versão Atual**: 1.7.0 (MVP-06 concluído)

---

**Última atualização**: 2025-10-15  
**Versão do documento**: 1.0  
**Status**: Documento Vivo (atualizado continuamente)

# Especificação de Funcionalidades Avançadas (Novembro/2025)

## 1. Personalização do Fluxo de Passos
- Adicionar checkbox em "Histórico e Ferramentas" para habilitar/desabilitar a pergunta de tempo extra ao final de cada step.
- Persistir o estado da checkbox (armazenamento local).
- Integrar ASR (Vosk) para reconhecimento de voz ao final do step, permitindo decisão automática.
- Fallback para resposta manual caso o reconhecimento falhe.

## 2. Exportação e Importação de Atividades
- Botão para exportar atividades (JSON/CSV) em "Histórico e Ferramentas".
- Botão para importar atividades, com validação e prevenção de duplicidades.

## 3. Filtro de Categorias em Atividades
- Componente de filtro (dropdown/chips) na tela de atividades para seleção de categoria.
- Persistência do filtro durante a sessão.

## Roadmap Modular
- Fase 1: Personalização do fluxo de passos (checkbox + ASR)
- Fase 2: Exportação/importação de atividades
- Fase 3: Filtro de categorias

## Critérios de Aceitação
- Funcionalidades testáveis e documentadas nas primeiras 50 linhas dos arquivos afetados.
- Atualização do CHANGELOG.md e docs a cada entrega.

## Observações
- Recomenda-se branches específicas para cada fase.
- Issues/tarefas detalhadas para cada subitem.
