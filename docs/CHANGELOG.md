# CHANGELOG - Pequenos Passos

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [Em Desenvolvimento] - MVP08 (v1.10.0) - 2025-10-23

### 🎮 Sistema de Gamificação - Fase 1 (Arquitetura Base)

#### Adicionado
- ✨ **Entidades de domínio:**
  - `Achievement`: Registra conquistas ao completar tarefas (estrelas, tempo, detalhes)
  - `Reward`: Recompensas desbloqueáveis com sistema de gamificação
  - `RewardType`: Enum com 5 tipos (THEME, AVATAR, SOUND, BADGE, EFFECT)
  - `GamificationStats`: Modelo calculado com estatísticas agregadas

- ✨ **DAOs (Data Access Objects):**
  - `AchievementDao`: 14 métodos (CRUD + queries de estatísticas)
  - `RewardDao`: 12 métodos (CRUD + queries especializadas)

- ✨ **Repositories:**
  - `AchievementRepository`: Interface + Implementation
  - `RewardRepository`: Interface + Implementation

- ✨ **Use Cases (5 casos de uso):**
  - `SaveAchievementUseCase`: Salva achievement após completar tarefa
  - `CalculateStarsUseCase`: Calcula estrelas (1-5) baseado em critérios
  - `GetGamificationStatsUseCase`: Calcula todas as estatísticas
  - `UnlockRewardsUseCase`: Desbloqueia recompensas automaticamente
  - `InitializeDefaultRewardsUseCase`: Cria 12 recompensas padrão

- ✨ **Banco de Dados:**
  - Migration 2→3: Cria tabelas `achievements` e `rewards`
  - Índices otimizados para queries de estatísticas
  - Foreign Keys com CASCADE para integridade referencial

- ✨ **Regras de Negócio:**
  - Cálculo de estrelas: Base (1-3) + Bônus (0-2) = 1-5 estrelas
  - No tempo: 3⭐ | Atraso ≤30min: 2⭐ | Atraso >30min: 1⭐
  - Bônus: +1⭐ todos steps | +1⭐ sem ajuda

#### Modificado
- 🔧 `AppDatabase`: Atualizado para versão 3 com novas entidades
- 🔧 `Converters`: Adicionado TypeConverter para RewardType
- 🔧 `DatabaseModule`: Providers para novos DAOs + Migration 2→3
- 🔧 `RepositoryModule`: Bindings para novos repositories

#### Técnico
- 📦 ~1.200 linhas de código adicionadas
- 📦 9 novos arquivos criados
- 📦 4 arquivos existentes atualizados
- 📦 Arquitetura Clean mantida
- 📦 Injeção de dependências (Hilt) configurada

---

## [1.10.1] - MVP08 (Correção de Migração) - 2025-10-23

### Corrigido
- 🛠 Erro crítico de migração do banco de dados:
  - Migration didn't properly handle: achievements (com.pequenospassos.domain.model.Achievement)
  - Causa: incompatibilidade entre o tipo da coluna (TEXT) e o TypeConverter (INTEGER/Long) para campos de data/hora.
  - Correção: alterado tipo das colunas `completedAt` (achievements) e `unlockedAt` (rewards) para INTEGER na migration SQL.
- 🛠 Documentação detalhada da correção adicionada em `docs/MVP08_CORRECAO_MIGRATION_DATABASE.md`.
- 🛠 Orientações para limpar dados do app e garantir migração correta.

---

## [1.9.6] - MVP07 (Correção Crítica) - 2025-10-22

### 🐛 Correção de Bug Crítico - Steps na Edição

#### Corrigido
- 🐛 **CRÍTICO:** Steps não apareciam ao editar tarefa
- 🐛 **CRÍTICO:** Erro `UNIQUE constraint failed: steps.id` ao salvar
- 🐛 Carregamento assíncrono de steps corrigido
- 🐛 Sincronização de estado UI/ViewModel

#### Técnico
- 🔧 Correção na lógica de update do StepDao
- 🔧 Implementação correta de StateFlow
- 🔧 Aguardar carregamento antes de exibir UI

---

## [1.9.5] - MVP07 (Correções de Edição) - 2025-10-21

### 🐛 Correções de Edição de Steps

#### Corrigido
- 🐛 Problemas ao editar steps existentes
- 🐛 Validações de formulários
- 🐛 Melhorias na navegação entre telas

---

## [1.9.4] - MVP07 (Correção de Galeria) - 2025-10-20

### 🐛 Correção Crítica - Galeria de Imagens

#### Corrigido
- 🐛 **CRÍTICO:** Crash ao abrir galeria no Android 13+
- 🐛 Permissões de mídia corrigidas (READ_MEDIA_IMAGES)
- 🐛 Memory leaks de Bitmap resolvidos
- 🐛 Tratamento correto de URIs

#### Técnico
- 🔧 Verificação de versão Android para permissões
- 🔧 Gestão adequada de recursos de imagem
- 🔧 Fallback para permissões legadas

---

## [1.9.3] - MVP07 (Melhorias Gerais) - 2025-10-19

### 🔧 Melhorias e Correções

#### Corrigido
- 🐛 Correções em botões de cadastro/salvar
- 🐛 Melhorias de navegação entre telas
- 🐛 Ajustes em validações de formulários

---

## [1.9.2] - MVP07 (Reorganização) - 2025-10-18

### 🔧 Reorganização de Estrutura

#### Modificado
- 🔧 Reorganização da estrutura de arquivos
- 🔧 Melhorias na organização de packages
- 🔧 Limpeza de código não utilizado

---

## [1.9.1] - MVP07 (Correções Menores) - 2025-10-17

### 🐛 Correções Diversas

#### Corrigido
- 🐛 Correções menores em navegação
- 🐛 Ajustes de layout
- 🐛 Melhorias de performance

---

## [1.9.0] - MVP07 (Refinamentos e Testes) - 2025-10-16

### ✅ Conclusão MVP07 - Refinamentos

#### Adicionado
- ✨ Suite completa de testes automatizados
- ✨ Testes unitários para Models, Use Cases, Repositories
- ✨ Testes de integração para DAOs e Database
- ✨ Cobertura de testes >85%

#### Modificado
- 🔧 Migration 1→2 do banco de dados
- 🔧 Adicionado campo `category` em tasks
- 🔧 Adicionado campo `imageUrl` em tasks e steps
- 🔧 Adicionado campo `durationSeconds` em steps

#### Técnico
- 📦 26 arquivos de teste criados
- 📦 Todos os testes passando
- 📦 Build limpo sem erros

---

## [1.8.0] - MVP06 (Tela de Conclusão) - 2025-10-15

### 🎉 Tela de Conclusão de Tarefas

#### Adicionado
- ✨ TaskCompletionScreen com celebração
- ✨ Sistema básico de estrelas
- ✨ Mensagens motivacionais
- ✨ Animações de confete
- ✨ Botão para retornar à home

#### Técnico
- 📦 Feedback visual de sucesso
- 📦 Preparação para sistema de gamificação

---

## [1.7.0] - MVP05 (Execução de Tarefas) - 2025-10-14

### ⏱️ Sistema de Execução de Tarefas

#### Adicionado
- ✨ TaskExecutionScreen com navegação entre steps
- ✨ Timer visual animado (CircularProgressIndicator)
- ✨ Contador de progresso ("Passo X de Y")
- ✨ Marcação de conclusão de steps
- ✨ Navegação sequencial automática

#### Técnico
- 📦 Gerenciamento de estado com ViewModel
- 📦 Controle de timer com Coroutines
- 📦 Animações fluidas

---

## [1.6.0] - MVP04 (Interface de Usuário) - 2025-10-13

### 🎨 Design System e UI

#### Adicionado
- ✨ Design System completo:
  - Paleta de cores (Color.kt)
  - Tipografia (Type.kt)
  - Formas (Shape.kt)
  - Tema (Theme.kt)
- ✨ HomeScreen redesenhada
- ✨ Componentes reutilizáveis
- ✨ Material Design 3

#### Técnico
- 📦 Jetpack Compose modularizado
- 📦 Tema adaptado para crianças
- 📦 Cores vibrantes e acessíveis

---

## [1.5.0] - MVP03 (Sistema de Passos) - 2025-10-12

### 📝 CRUD de Steps

#### Adicionado
- ✨ Entidade Step (passos/subtarefas)
- ✨ StepDao com queries otimizadas
- ✨ StepRepository + Implementation
- ✨ Use Cases: GetStepsByTaskUseCase
- ✨ Relação Many-to-One com Task
- ✨ Ordenação sequencial de steps

#### Técnico
- 📦 Foreign Key com CASCADE DELETE
- 📦 Índices para performance
- 📦 Suporte a Flow para reatividade

---

## [1.4.0] - MVP02 (Gerenciamento de Tarefas) - 2025-10-11

### 📋 CRUD Completo de Tarefas

#### Adicionado
- ✨ TaskFormScreen (criar/editar tarefas)
- ✨ TaskListScreen (listar tarefas)
- ✨ TaskManagementScreen (gerenciar)
- ✨ Categorização de tarefas (27 categorias)
- ✨ Ordenação por horário
- ✨ Upload de imagens

#### Use Cases Implementados
- ✨ SaveTaskUseCase
- ✨ GetTaskByIdUseCase
- ✨ GetTasksOrderedByTimeUseCase
- ✨ DeleteTaskUseCase
- ✨ UpdateTaskStatusUseCase

---

## [1.3.0] - MVP01 (Banco de Dados) - 2025-10-10

### 🗄️ Fundação - Banco de Dados

#### Adicionado
- ✨ AppDatabase (Room Database)
- ✨ Entidades principais:
  - ChildProfile
  - Task
  - AppSettings
- ✨ DAOs com operações CRUD
- ✨ TypeConverters (Gender, TaskStatus)
- ✨ Migrations configuradas

#### Técnico
- 📦 Room Database v1
- 📦 TypeConverters para enums
- 📦 Foreign Keys configuradas

---

## [1.2.0] - MVP01 (Arquitetura) - 2025-10-09

### 🏗️ Clean Architecture

#### Adicionado
- ✨ Estrutura de packages (domain/data/presentation)
- ✨ Injeção de dependências (Hilt)
- ✨ Navegação (Jetpack Navigation)
- ✨ ViewModel base

#### Módulos Hilt
- 📦 DatabaseModule
- 📦 RepositoryModule

---

## [1.1.0] - MVP01 (Onboarding) - 2025-10-08

### 👋 Tela de Onboarding

#### Adicionado
- ✨ SplashScreen com logo
- ✨ Verificação de primeiro acesso
- ✨ Cadastro de perfil da criança
- ✨ Validações de formulário

---

## [1.0.0] - Projeto Inicial - 2025-10-07

### 🎯 Configuração Inicial

#### Adicionado
- ✨ Projeto Android criado
- ✨ Configuração Gradle
- ✨ Dependências base:
  - Jetpack Compose
  - Room Database
  - Hilt (Dependency Injection)
  - Navigation Compose
  - Coroutines
- ✨ Estrutura de pastas inicial
- ✨ README.md
- ✨ .gitignore

#### Configuração
- 📦 minSdk: 26 (Android 8.0)
- 📦 targetSdk: 34 (Android 14)
- 📦 compileSdk: 34
- 📦 Kotlin: 1.9.0
- 📦 Compose: 1.5.1

---

## Legenda de Ícones

- ✨ **Adicionado:** Novas funcionalidades
- 🔧 **Modificado:** Mudanças em funcionalidades existentes
- 🐛 **Corrigido:** Correções de bugs
- ❌ **Removido:** Funcionalidades removidas
- 🔒 **Segurança:** Correções de segurança
- 📦 **Técnico:** Detalhes técnicos e dependências
- 🎨 **UI/UX:** Melhorias de interface
- ⚡ **Performance:** Melhorias de performance
- 📝 **Documentação:** Atualizações de documentação

---

**Mantido por:** Equipe PequenosPassos  
**Última atualização:** 23/10/2025
