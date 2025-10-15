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

### 2.3 Tarefas com Steps
- Dividir tarefas em pequenos passos
- Ordem sequencial de execução
- Descrição textual de cada passo
- Suporte a imagens (futuro)
- Marcação de conclusão por step

### 2.4 Execução de Tarefas
- Modo guiado passo a passo
- Leitura das instruções por voz (TTS)
- Barra de progresso visual
- Feedback motivacional
- Estatísticas de conclusão

### 2.5 Sistema de Recompensas
- Estrelas por tarefa concluída
- Feedback visual positivo
- Mensagens motivacionais
- Histórico de conquistas (futuro)

---

## 3. Tecnologias Utilizadas

### 3.1 Plataforma
- **Android**: minSdk 24 (Android 7.0)
- **Linguagem**: Kotlin 100%
- **Framework UI**: Jetpack Compose

### 3.2 Arquitetura
- **Padrão**: Clean Architecture
- **Camadas**: Presentation, Domain, Data
- **Injeção de Dependências**: Hilt/Dagger

### 3.3 Banco de Dados
- **Room**: Banco local SQLite
- **Tabelas**: app_settings, child_profiles, tasks, steps

### 3.4 Recursos de Acessibilidade
- **TTS**: Text-to-Speech nativo do Android
- **ASR**: Vosk (reconhecimento offline) - planejado
- **Cores**: Alto contraste e modo claro/escuro

---

## 4. Estrutura de Dados

### 4.1 Entidades Principais

#### ChildProfile
```kotlin
- id: Long
- name: String (obrigatório, mín. 2 caracteres)
- birthDate: String (formato YYYY-MM-DD)
- notes: String (opcional)
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
- status: TaskStatus (PENDING, COMPLETED, CANCELLED)
- stars: Int (1-5)
- createdAt: Long
```

#### Step
```kotlin
- id: Long
- taskId: Long (FK)
- order: Int
- description: String
- isCompleted: Boolean
- imageUrl: String? (futuro)
```

#### AppSettings
```kotlin
- id: Long (sempre 1)
- isFirstRun: Boolean
- onboardingCompleted: Boolean
```

---

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

- GetTaskStatisticsUseCase
- DeleteTaskUseCase
- ReorderStepsUseCase
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

**Modo Claro:**
- Primary: #6750A4 (roxo)
- Secondary: #625B71 (cinza-roxo)
- Tertiary: #7D5260 (rosa-acinzentado)
- Background: #FFFBFE
- Surface: #FFFBFE

**Modo Escuro:**
- Primary: #D0BCFF
- Secondary: #CCC2DC
- Tertiary: #EFB8C8
- Background: #1C1B1F
- Surface: #1C1B1F

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
- Suporte a imagens nos steps não implementado
- Um único perfil de criança por dispositivo

### 13.2 Planejado para Futuro
- Múltiplos perfis
- Sincronização entre dispositivos
- Gamificação mais elaborada
- Estatísticas e relatórios

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

