# CHANGELOG - Pequenos Passos

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

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

