# Pequenos Passos 🎯

**Aplicativo Android de Auxílio para Rotinas Infantis**

[![Versão](https://img.shields.io/badge/versão-1.7.0-blue.svg)](https://github.com)
[![MVP](https://img.shields.io/badge/MVP-06%20Concluído-green.svg)](https://github.com)
[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com)
[![Testes](https://img.shields.io/badge/testes-142%20passando-success.svg)](https://github.com)

---

## 📋 Sobre o Projeto

O **Pequenos Passos** é um aplicativo Android desenvolvido para auxiliar crianças com necessidades especiais (TEA, TDAH, dificuldades de aprendizagem) a executarem tarefas do dia a dia de forma estruturada, visual e interativa.

### 🎯 Objetivo Principal
Facilitar a execução de rotinas diárias através de:
- Interface visual e intuitiva
- Instruções passo a passo
- Síntese de voz (TTS)
- Sistema de recompensas (estrelas)
- Funcionamento 100% offline

---

## 🚀 Status do Projeto

### MVPs Concluídos ✅

| MVP | Descrição | Status | Testes | Versão |
|-----|-----------|--------|--------|--------|
| MVP-01 | Estrutura Base | ✅ | - | 1.2.0 |
| MVP-02 | Entidades | ✅ | 29 testes | 1.3.0 |
| MVP-03 | Database (Room) | ✅ | 32 testes | 1.4.0 |
| MVP-04 | Repositórios | ✅ | 37 testes | 1.5.0 |
| MVP-05 | Use Cases | ✅ | 44 testes | 1.6.0 |
| MVP-06 | Theme/Design | ✅ | - | 1.7.0 |

**Total de Testes**: 142 testes unitários (100% passando) ✅

### Próximos Passos 📋

- **MVP-07**: Telas de Interface (EM PLANEJAMENTO)
  - OnboardingScreen
  - TaskFormScreen
  - TaskListScreen
  - TaskExecutionScreen

- **MVP-08**: ViewModels e Integração
- **MVP-09**: Testes E2E
- **MVP-10**: Polimento e Release

---

## 🏗️ Arquitetura

### Clean Architecture

```
📦 com.pequenospassos
 ┣ 📂 presentation/          # UI (Jetpack Compose)
 ┃ ┣ 📂 screens/            # Telas da aplicação
 ┃ ┣ 📂 components/         # Componentes reutilizáveis
 ┃ ┣ 📂 navigation/         # Sistema de navegação
 ┃ ┗ 📂 theme/              # Design System
 ┃
 ┣ 📂 domain/               # Regras de negócio
 ┃ ┣ 📂 model/             # Entidades
 ┃ ┣ 📂 repository/        # Interfaces
 ┃ ┗ 📂 usecase/           # Casos de uso
 ┃
 ┗ 📂 data/                # Fontes de dados
   ┣ 📂 database/          # Room Database
   ┃ ┣ 📂 dao/            # DAOs
   ┃ ┗ 📂 entities/       # Entidades Room
   ┗ 📂 repository/        # Implementações
```

---

## 🛠️ Tecnologias

### Core
- **Kotlin** 100%
- **Jetpack Compose** (UI moderna e declarativa)
- **Hilt/Dagger** (Injeção de dependências)
- **Coroutines + Flow** (Programação reativa)

### Database
- **Room** (SQLite local)
- **TypeConverters** para tipos complexos

### Testes
- **JUnit 5** (Testes unitários)
- **Mockk** (Mocks)
- **Truth** (Assertions)
- **Turbine** (Testes de Flow)

### Design
- **Material Design 3**
- **Modo claro/escuro**
- **Acessibilidade** (TalkBack, contraste)

---

## 📊 Estrutura de Dados

### Entidades Principais

#### 🧒 ChildProfile
- Nome da criança
- Data de nascimento
- Observações

#### 📝 Task (Tarefa)
- Nome e descrição
- Horário agendado (HH:mm)
- Tipo (manhã, tarde, noite, qualquer hora)
- Status (pendente, concluída, cancelada)
- Estrelas (1-5)

#### 🔢 Step (Passo)
- Descrição do passo
- Ordem de execução
- Status de conclusão
- Imagem (futuro)

---

## 🎨 Design System

### Cores Principais
- **Primary**: Roxo (#6750A4)
- **Secondary**: Cinza-roxo (#625B71)
- **Tertiary**: Rosa-acinzentado (#7D5260)

### Tipografia
- Display, Headline, Title, Body, Label

### Espaçamento
- 4dp, 8dp, 16dp, 24dp, 32dp (sistema padronizado)

---

## 🧪 Qualidade e Testes

### Cobertura de Testes
- **Use Cases**: 100% ✅
- **Repositórios**: ~90% ✅
- **DAOs**: ~85% ✅

### Métricas de Build
- Build: **SUCCESS** ✅
- Tempo médio: ~40-45s
- Warnings: 0 críticos

### Validação Anti-Regressão
Todos os MVPs anteriores continuam funcionando após cada novo desenvolvimento.

---

## 🚀 Como Executar

### Pré-requisitos
- Android Studio (última versão estável)
- JDK 11+
- Android SDK (API 24+)

### Passos

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/pequenospassos.git
   cd pequenospassos
   ```

2. **Abra no Android Studio**
   - File → Open → Selecione a pasta do projeto

3. **Sync Gradle**
   - Aguarde sincronização automática das dependências

4. **Execute**
   - Conecte um dispositivo Android (API 24+) ou use emulador
   - Run → Run 'app'

---

## 📱 Telas Implementadas

### Telas Funcionais (MVP-06)
1. **SplashScreen** - Tela inicial (3s)
2. **HomeScreen** - Menu principal
3. **DebugScreen** - Informações de desenvolvimento
4. **TtsTestScreen** - Teste de síntese de voz
5. **AsrTestScreen** - Teste de reconhecimento de voz

### Telas Planejadas (MVP-07)
6. **OnboardingScreen** - Cadastro inicial
7. **TaskFormScreen** - Criar/editar tarefas
8. **TaskListScreen** - Listar tarefas
9. **TaskExecutionScreen** - Executar tarefas passo a passo

---

## 🧩 Use Cases Implementados (MVP-05)

1. ✅ CheckFirstRunUseCase
2. ✅ CompleteOnboardingUseCase
3. ✅ GetChildProfileUseCase
4. ✅ SaveChildProfileUseCase
5. ✅ GetTaskByIdUseCase
6. ✅ GetTasksOrderedByTimeUseCase
7. ✅ SaveTaskUseCase
8. ✅ UpdateTaskStatusUseCase
9. ✅ GetStepsByTaskUseCase
10. ✅ UpdateStepCompletionUseCase

---

## 📖 Documentação

- [CHANGELOG.md](docs/CHANGELOG.md) - Histórico de mudanças
- [GUIDELINES.md](docs/GUIDELINES.md) - Diretrizes de desenvolvimento
- [PATHS.md](docs/PATHS.md) - Navegação e fluxos
- [SPECIFICATION_FOR_APP.md](docs/SPECIFICATION_FOR_APP.md) - Especificação completa
- [MVP0X_VALIDATION_SUMMARY.md](docs/) - Resumos de validação

---

## 🎓 Contexto Acadêmico

**Instituição**: UNIVESP - Universidade Virtual do Estado de São Paulo  
**Curso**: Engenharia de Computação  
**Disciplina**: Projeto Integrador VI  
**Ano**: 2025  

---

## 🤝 Contribuindo

Este é um projeto acadêmico. Sugestões e feedback são bem-vindos!

### Processo de Desenvolvimento
1. Criar branch `feature/mvp-XX`
2. Desenvolver seguindo Clean Architecture
3. Escrever testes (cobertura mínima)
4. Atualizar documentação
5. Validar anti-regressão
6. Pull Request

---

## 📄 Licença

Este projeto é desenvolvido para fins acadêmicos.

---

## 📞 Contato

Para dúvidas ou sugestões sobre o projeto, abra uma issue no repositório.

---

## 🏆 Conquistas

- ✅ 142 testes unitários passando
- ✅ Clean Architecture implementada
- ✅ 6 MVPs concluídos
- ✅ Zero regressões
- ✅ Build sempre verde

---

**Última atualização**: 2025-10-15  
**Versão**: 1.7.0 (MVP-06)  
**Status**: Em desenvolvimento ativo 🚀

