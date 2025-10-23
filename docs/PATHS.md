# PATHS - Estrutura de Diretórios do Projeto

**Pequenos Passos - Versão 1.9.6**  
**Atualizado em:** 23/10/2025

---

## 📂 Estrutura Completa do Projeto

```
D:\Softwares\PequenosPassos/
│
├── 📁 app/                                    # Módulo principal do aplicativo
│   ├── 📄 build.gradle.kts                    # Configuração de build do app
│   ├── 📄 proguard-rules.pro                  # Regras ProGuard
│   │
│   ├── 📁 build/                              # Arquivos gerados (ignorado no Git)
│   │   └── outputs/                           # APKs e AABs gerados
│   │
│   └── 📁 src/
│       ├── 📁 main/                           # Código fonte principal
│       │   ├── 📄 AndroidManifest.xml
│       │   │
│       │   ├── 📁 java/com/pequenospassos/
│       │   │   │
│       │   │   ├── 📁 data/                   # CAMADA DE DADOS
│       │   │   │   │
│       │   │   │   ├── 📁 database/           # Room Database
│       │   │   │   │   ├── 📄 AppDatabase.kt  # Database principal
│       │   │   │   │   ├── 📄 Converters.kt   # Type Converters
│       │   │   │   │   │
│       │   │   │   │   └── 📁 dao/            # Data Access Objects
│       │   │   │   │       ├── 📄 AppSettingsDao.kt
│       │   │   │   │       ├── 📄 ChildProfileDao.kt
│       │   │   │   │       ├── 📄 StepDao.kt
│       │   │   │   │       └── 📄 TaskDao.kt
│       │   │   │   │
│       │   │   │   └── 📁 repository/         # Implementações de Repository
│       │   │   │       ├── 📄 AppSettingsRepositoryImpl.kt
│       │   │   │       ├── 📄 ChildProfileRepositoryImpl.kt
│       │   │   │       ├── 📄 StepRepositoryImpl.kt
│       │   │   │       └── 📄 TaskRepositoryImpl.kt
│       │   │   │
│       │   │   ├── 📁 domain/                 # CAMADA DE DOMÍNIO
│       │   │   │   │
│       │   │   │   ├── 📁 model/              # Entidades de Domínio
│       │   │   │   │   ├── 📄 AppSettings.kt
│       │   │   │   │   ├── 📄 ChildProfile.kt
│       │   │   │   │   ├── 📄 Step.kt
│       │   │   │   │   ├── 📄 Task.kt
│       │   │   │   │   └── 📄 TaskCategory.kt
│       │   │   │   │
│       │   │   │   ├── 📁 repository/         # Interfaces de Repository
│       │   │   │   │   ├── 📄 AppSettingsRepository.kt
│       │   │   │   │   ├── 📄 ChildProfileRepository.kt
│       │   │   │   │   ├── 📄 StepRepository.kt
│       │   │   │   │   └── 📄 TaskRepository.kt
│       │   │   │   │
│       │   │   │   └── 📁 usecase/            # Casos de Uso
│       │   │   │       ├── 📄 CheckFirstRunUseCase.kt
│       │   │   │       ├── 📄 CompleteOnboardingUseCase.kt
│       │   │   │       ├── 📄 DeleteTaskUseCase.kt
│       │   │   │       ├── 📄 GetChildProfileUseCase.kt
│       │   │   │       ├── 📄 GetStepsByTaskUseCase.kt
│       │   │   │       ├── 📄 GetTaskByIdUseCase.kt
│       │   │   │       ├── 📄 GetTasksOrderedByTimeUseCase.kt
│       │   │   │       ├── 📄 SaveChildProfileUseCase.kt
│       │   │   │       ├── 📄 SaveTaskUseCase.kt
│       │   │   │       ├── 📄 UpdateStepCompletionUseCase.kt
│       │   │   │       └── 📄 UpdateTaskStatusUseCase.kt
│       │   │   │
│       │   │   ├── 📁 presentation/           # CAMADA DE APRESENTAÇÃO
│       │   │   │   │
│       │   │   │   ├── 📁 screens/            # Telas Compose
│       │   │   │   │   ├── 📄 HomeScreen.kt
│       │   │   │   │   ├── 📄 SplashScreen.kt
│       │   │   │   │   ├── 📄 DebugScreen.kt
│       │   │   │   │   ├── 📄 AsrTestScreen.kt
│       │   │   │   │   ├── 📄 TtsTestScreen.kt
│       │   │   │   │   │
│       │   │   │   │   ├── 📁 completion/
│       │   │   │   │   │   ├── 📄 TaskCompletionScreen.kt
│       │   │   │   │   │   └── 📄 TaskCompletionViewModel.kt
│       │   │   │   │   │
│       │   │   │   │   ├── 📁 execution/
│       │   │   │   │   │   ├── 📄 TaskExecutionScreen.kt
│       │   │   │   │   │   └── 📄 TaskExecutionViewModel.kt
│       │   │   │   │   │
│       │   │   │   │   ├── 📁 taskform/
│       │   │   │   │   │   ├── 📄 TaskFormScreen.kt
│       │   │   │   │   │   └── 📄 TaskFormViewModel.kt
│       │   │   │   │   │
│       │   │   │   │   ├── 📁 tasklist/
│       │   │   │   │   │   ├── 📄 TaskListScreen.kt
│       │   │   │   │   │   └── 📄 TaskListViewModel.kt
│       │   │   │   │   │
│       │   │   │   │   └── 📁 taskmanagement/
│       │   │   │   │       ├── 📄 TaskManagementScreen.kt
│       │   │   │   │       └── 📄 TaskManagementViewModel.kt
│       │   │   │   │
│       │   │   │   ├── 📁 components/         # Componentes Reutilizáveis
│       │   │   │   │   ├── 📄 CategoryPicker.kt
│       │   │   │   │   ├── 📄 CircularTimer.kt
│       │   │   │   │   ├── 📄 ImagePicker.kt
│       │   │   │   │   └── 📄 TimerInput.kt
│       │   │   │   │
│       │   │   │   ├── 📁 theme/              # Design System
│       │   │   │   │   ├── 📄 Color.kt
│       │   │   │   │   ├── 📄 Shape.kt
│       │   │   │   │   ├── 📄 Theme.kt
│       │   │   │   │   └── 📄 Type.kt
│       │   │   │   │
│       │   │   │   ├── 📁 navigation/         # Sistema de Navegação
│       │   │   │   │   └── 📄 Navigation.kt
│       │   │   │   │
│       │   │   │   ├── 📁 tts/                # Text-to-Speech
│       │   │   │   │   └── 📄 TtsManager.kt
│       │   │   │   │
│       │   │   │   └── 📁 asr/                # Speech Recognition
│       │   │   │       └── 📄 AsrManager.kt
│       │   │   │
│       │   │   ├── 📁 di/                     # Injeção de Dependências (Hilt)
│       │   │   │   ├── 📄 DatabaseModule.kt
│       │   │   │   ├── 📄 RepositoryModule.kt
│       │   │   │   └── 📄 UseCaseModule.kt
│       │   │   │
│       │   │   ├── 📁 utils/                  # Utilitários
│       │   │   │   └── 📄 Extensions.kt
│       │   │   │
│       │   │   ├── 📄 MainActivity.kt         # Activity Principal
│       │   │   └── 📄 PequenosPassosApplication.kt
│       │   │
│       │   ├── 📁 res/                        # Recursos Android
│       │   │   ├── 📁 drawable/
│       │   │   ├── 📁 mipmap-*/               # Ícones do app
│       │   │   ├── 📁 values/
│       │   │   │   ├── 📄 colors.xml
│       │   │   │   ├── 📄 strings.xml
│       │   │   │   └── 📄 themes.xml
│       │   │   └── 📁 xml/
│       │   │
│       │   └── 📁 assets/                     # Assets (se necessário)
│       │
│       ├── 📁 test/                           # TESTES UNITÁRIOS
│       │   └── java/com/pequenospassos/
│       │       │
│       │       ├── 📁 domain/
│       │       │   ├── 📁 model/
│       │       │   │   ├── 📄 AppSettingsTest.kt
│       │       │   │   ├── 📄 ChildProfileTest.kt
│       │       │   │   ├── 📄 StepTest.kt
│       │       │   │   ├── 📄 TaskCategoryTest.kt
│       │       │   │   └── 📄 TaskTest.kt
│       │       │   │
│       │       │   └── 📁 usecase/
│       │       │       ├── 📄 CheckFirstRunUseCaseTest.kt
│       │       │       ├── 📄 CompleteOnboardingUseCaseTest.kt
│       │       │       ├── 📄 DeleteTaskUseCaseTest.kt
│       │       │       ├── 📄 GetChildProfileUseCaseTest.kt
│       │       │       ├── 📄 GetStepsByTaskUseCaseTest.kt
│       │       │       ├── 📄 GetTaskByIdUseCaseTest.kt
│       │       │       ├── 📄 GetTasksOrderedByTimeUseCaseTest.kt
│       │       │       ├── 📄 SaveChildProfileUseCaseTest.kt
│       │       │       ├── 📄 SaveTaskUseCaseTest.kt
│       │       │       ├── 📄 UpdateStepCompletionUseCaseTest.kt
│       │       │       └── 📄 UpdateTaskStatusUseCaseTest.kt
│       │       │
│       │       ├── 📁 data/
│       │       │   └── 📁 repository/
│       │       │       ├── 📄 AppSettingsRepositoryImplTest.kt
│       │       │       ├── 📄 ChildProfileRepositoryImplTest.kt
│       │       │       ├── 📄 StepRepositoryImplTest.kt
│       │       │       └── 📄 TaskRepositoryImplTest.kt
│       │       │
│       │       └── 📁 presentation/
│       │           ├── 📁 screens/
│       │           │   ├── 📁 execution/
│       │           │   │   └── 📄 TaskExecutionViewModelTest.kt
│       │           │   │
│       │           │   └── 📁 taskform/
│       │           │       └── 📄 TaskFormViewModelTest.kt
│       │           │
│       │           ├── 📁 components/
│       │           │   ├── 📄 CategoryPickerTest.kt
│       │           │   ├── 📄 CircularTimerTest.kt
│       │           │   ├── 📄 ImagePickerTest.kt
│       │           │   └── 📄 TimerInputTest.kt
│       │           │
│       │           ├── 📁 theme/
│       │           │   ├── 📄 ColorTest.kt
│       │           │   ├── 📄 ShapeTest.kt
│       │           │   └── 📄 TypographyTest.kt
│       │           │
│       │           ├── 📁 navigation/
│       │           │   └── 📄 NavigationTest.kt
│       │           │
│       │           ├── 📁 tts/
│       │           │   └── 📄 TtsManagerTest.kt
│       │           │
│       │           └── 📁 asr/
│       │               └── 📄 AsrManagerTest.kt
│       │
│       └── 📁 androidTest/                    # TESTES INSTRUMENTADOS
│           └── java/com/pequenospassos/
│               └── 📁 data/
│                   └── 📁 database/
│                       └── 📁 dao/
│                           ├── 📄 AppSettingsDaoTest.kt
│                           ├── 📄 ChildProfileDaoTest.kt
│                           ├── 📄 StepDaoTest.kt
│                           └── 📄 TaskDaoTest.kt
│
├── 📁 build/                                  # Arquivos de build (ignorado)
│
├── 📁 gradle/                                 # Gradle Wrapper
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── 📁 docs/                                   # DOCUMENTAÇÃO DO PROJETO
│   ├── 📄 ANALISE_STATUS_ATUAL_E_PROXIMOS_PASSOS.md
│   ├── 📄 CHANGELOG.md
│   ├── 📄 GUIDELINES.md
│   ├── 📄 PATHS.md                            # Este arquivo
│   ├── 📄 README.md
│   ├── 📄 RELATORIO_TECNICO_FUNDAMENTACAO_ABNT.md
│   ├── 📄 VERSIONAMENTO_DOCUMENTACAO.md
│   │
│   ├── 📄 MVP02_TESTING_GUIDE.md
│   ├── 📄 MVP03_DATABASE_GUIDE.md
│   ├── 📄 MVP04_REPOSITORY_GUIDE.md
│   ├── 📄 MVP04_VALIDATION_SUMMARY.md
│   ├── 📄 MVP05_VALIDATION_SUMMARY.md
│   ├── 📄 MVP06_REGRESSION_CHECK.md
│   ├── 📄 MVP06_VALIDATION_SUMMARY.md
│   │
│   ├── 📄 MVP07_ANTI_REGRESSAO_MVP01_06.md
│   ├── 📄 MVP07_BUGFIX_CRASH_V1.9.4.md
│   ├── 📄 MVP07_BUGFIX_GALERIA_IMAGENS.md
│   ├── 📄 MVP07_BUGFIX_REPORT.md
│   ├── 📄 MVP07_BUGFIX_STEPS_EDICAO_V1.9.5.md
│   ├── 📄 MVP07_BUGFIX_STEPS_EDICAO_V1.9.6.md
│   ├── 📄 MVP07_CHANGELOG.md
│   ├── 📄 MVP07_COMPILACAO_TESTES_SUCESSO.md
│   ├── 📄 MVP07_CORRECAO_BOTAO_CADASTRO.md
│   ├── 📄 MVP07_CORRECAO_BOTAO_SALVAR.md
│   ├── 📄 MVP07_CORRECAO_ERRO_COMPILACAO_TESTES.md
│   ├── 📄 MVP07_CORRECAO_GALERIA_BITMAP.md
│   ├── 📄 MVP07_CORRECAO_GALERIA_RESUMO.md
│   ├── 📄 MVP07_CORRECOES_COMPILACAO_TESTES_DETALHADO.md
│   ├── 📄 MVP07_CORREÇÕES_IMPLEMENTADAS.md
│   ├── 📄 MVP07_CORRECOES_V1.9.0.md
│   ├── 📄 MVP07_CORRECOES_V1.9.1.md
│   ├── 📄 MVP07_DEBUG_IMAGEM_TAREFA.md
│   ├── 📄 MVP07_DOCUMENTATION_STATUS.md
│   ├── 📄 MVP07_FASE1_CHECKLIST.md
│   ├── 📄 MVP07_FASE1_VALIDATION.md
│   ├── 📄 MVP07_FASE2_CHECKLIST.md
│   ├── 📄 MVP07_FASE2_VALIDATION.md
│   ├── 📄 MVP07_FASE3_CHECKLIST_TESTES.md
│   ├── 📄 MVP07_FASE3_PLANNING.md
│   ├── 📄 MVP07_FASE3_STATUS.md
│   ├── 📄 MVP07_FASE3_TESTES_AUTOMATIZADOS_RESUMO.md
│   ├── 📄 MVP07_PANORAMA_GERAL.md
│   ├── 📄 MVP07_RELATORIO_TESTES_AUTOMATIZADOS.md
│   ├── 📄 MVP07_RELATORIO_TESTES_V1.8.0.md
│   ├── 📄 MVP07_REORGANIZACAO_V1.9.2.md
│   ├── 📄 MVP07_RESPOSTA_3_PONTOS_CRITICOS.md
│   ├── 📄 MVP07_RESUMO_EXECUTIVO_ATUAL.md
│   ├── 📄 MVP07_RESUMO_EXECUTIVO_FINAL.md
│   ├── 📄 MVP07_RESUMO_EXECUTIVO.md
│   ├── 📄 MVP07_RESUMO_V1.9.6.md
│   ├── 📄 MVP07_STATUS_ATUAL.md
│   ├── 📄 MVP07_TASKFORM_IMPLEMENTATION.md
│   ├── 📄 MVP07_TASKFORM_RESUMO.md
│   ├── 📄 MVP07_TASKLISTSCREEN_IMPLEMENTATION.md
│   ├── 📄 MVP07_V1.9.3_RESUMO_IMPLEMENTACOES.md
│   ├── 📄 MVP07_VALIDACAO_TESTES.md
│   └── 📄 MVP07_WIREFRAMES.md
│
├── 📁 images/                                 # Imagens do projeto
│
├── 📄 build.gradle.kts                        # Build raiz do projeto
├── 📄 settings.gradle.kts                     # Configurações do projeto
├── 📄 gradle.properties                       # Propriedades do Gradle
├── 📄 gradlew                                 # Gradle Wrapper (Linux/Mac)
├── 📄 gradlew.bat                             # Gradle Wrapper (Windows)
├── 📄 local.properties                        # Configurações locais (ignorado)
│
├── 📄 .gitignore
├── 📄 README.md                               # README principal
├── 📄 LICENSE
│
└── 📁 Scripts de Automação/
    ├── 📄 compilar_e_instalar.bat
    ├── 📄 compilar_e_testar.bat
    ├── 📄 executar_testes.bat
    ├── 📄 fazer_commit.bat
    └── 📄 instalar_e_testar.bat
```

---

## 📊 Estatísticas da Estrutura

### Contagem de Arquivos por Camada

**Domain Layer:**
- 5 Models (Entidades)
- 4 Repository Interfaces
- 11 Use Cases
- **Total: 20 arquivos**

**Data Layer:**
- 1 AppDatabase
- 1 Converters
- 4 DAOs
- 4 Repository Implementations
- **Total: 10 arquivos**

**Presentation Layer:**
- 11 Screens/ViewModels
- 4 Components reutilizáveis
- 4 Theme files
- 1 Navigation
- 2 Managers (TTS, ASR)
- **Total: 22 arquivos**

**Tests:**
- 16 Unit Tests (Domain)
- 12 Unit Tests (Presentation)
- 4 Unit Tests (Data)
- 4 Instrumented Tests (DAOs)
- **Total: 36 arquivos de teste**

**Dependency Injection:**
- 3 Modules (Database, Repository, UseCase)
- **Total: 3 arquivos**

---

## 🔍 Mapeamento de Responsabilidades

### Camada de Dados (data/)

**Propósito:** Gerenciar fontes de dados e persistência

**database/AppDatabase.kt**
- Define o banco Room
- Gerencia versões e migrations
- Provê instância única (Singleton via Hilt)

**database/Converters.kt**
- Converte LocalDateTime ↔ Long
- Converte LocalTime ↔ String
- Converte TaskCategory ↔ String

**database/dao/***
- Define operações CRUD
- Queries customizadas com Flow
- Operações suspend para coroutines

**repository/***
- Implementa interfaces de Repository
- Mapeia entre entities e domain models (se necessário)
- Gerencia cache e estratégias de dados

---

### Camada de Domínio (domain/)

**Propósito:** Lógica de negócio pura, independente de frameworks

**model/***
- Entidades de negócio com validações
- Anotadas com @Entity para Room
- Imutáveis (data class)

**repository/*** (interfaces)
- Contratos para acesso a dados
- Retornam Flow para reatividade
- Operações suspend

**usecase/***
- Encapsulam lógica de negócio específica
- Uma responsabilidade por UseCase
- Invocáveis via operator fun invoke()

---

### Camada de Apresentação (presentation/)

**Propósito:** Interface com usuário e gerenciamento de estado

**screens/***
- Composables principais
- ViewModels com StateFlow
- Gerenciam estado de UI

**components/***
- Componentes reutilizáveis
- Stateless quando possível
- Parâmetros customizáveis

**theme/***
- Colors: Paleta de cores
- Typography: Definições de texto
- Shape: Formas e bordas
- Theme: Tema geral Material3

**navigation/Navigation.kt**
- Define rotas do app
- NavHost com Compose Navigation
- Deep links (futuro)

**tts/TtsManager.kt**
- Text-to-Speech
- Configurações de voz
- Callbacks de eventos

**asr/AsrManager.kt**
- Speech Recognition
- Processamento de comandos
- Permissões de microfone

---

## 🗂️ Convenções de Nomenclatura

### Arquivos Kotlin

**Models:**
- `NomeEntidade.kt` (ex: Task.kt, Step.kt)
- PascalCase, singular

**Screens:**
- `NomeDaTelaScreen.kt` (ex: TaskFormScreen.kt)
- Sempre termina com "Screen"

**ViewModels:**
- `NomeDaTelaViewModel.kt` (ex: TaskFormViewModel.kt)
- Sempre termina com "ViewModel"

**Repositories:**
- Interface: `NomeRepository.kt` (ex: TaskRepository.kt)
- Implementação: `NomeRepositoryImpl.kt`

**Use Cases:**
- `VerboNomeUseCase.kt` (ex: SaveTaskUseCase.kt)
- Verbo no infinitivo + substantivo

**DAOs:**
- `NomeDao.kt` (ex: TaskDao.kt)
- Sempre termina com "Dao"

**Tests:**
- `NomeClasseTest.kt` (ex: TaskTest.kt)
- Mesmo nome da classe + "Test"

---

## 📦 Packages e Organização

### Package Base
```
com.pequenospassos
```

### Sub-packages
- `.data.*` - Camada de dados
- `.domain.*` - Camada de domínio
- `.presentation.*` - Camada de apresentação
- `.di.*` - Injeção de dependências
- `.utils.*` - Utilitários gerais

### Regra de Dependências
```
presentation → domain → data
     ↓           ↓
    di ← ← ← ← ← ←
```

**Nunca:**
- domain não depende de data
- domain não depende de presentation
- data não depende de presentation

---

## 🧪 Estrutura de Testes

### test/ - Testes Unitários
- Executam na JVM
- Rápidos e isolados
- Usam Mockito/MockK para mocks
- Não requerem dispositivo Android

### androidTest/ - Testes Instrumentados
- Executam em dispositivo/emulador
- Testam componentes Android (Room, UI)
- Acesso a Context e recursos Android
- Mais lentos mas mais realistas

---

## 📝 Arquivos de Configuração

**build.gradle.kts (raiz)**
- Plugins buildscript
- Repositórios globais
- Configurações compartilhadas

**build.gradle.kts (app)**
- Dependências do app
- Configurações de compilação
- Build variants (debug, release)

**settings.gradle.kts**
- Define módulos do projeto
- Repositórios de plugins
- Configurações de dependências

**gradle.properties**
- Flags do Gradle
- Configurações de memória JVM
- Android build options

**local.properties**
- SDK path local
- NDK path (se usado)
- Não versionado (ignorado no Git)

---

## 🚀 Scripts de Automação

Localizados na raiz do projeto:

**compilar_e_instalar.bat**
```batch
gradlew clean assembleDebug installDebug
```

**compilar_e_testar.bat**
```batch
gradlew clean test assembleDebug
```

**executar_testes.bat**
```batch
gradlew test
```

**instalar_e_testar.bat**
```batch
gradlew installDebug connectedAndroidTest
```

---

## 📖 Documentação

Ver pasta `docs/` para toda documentação técnica e de planejamento.

**Principais documentos:**
- `README.md` - Visão geral do projeto
- `GUIDELINES.md` - Diretrizes de desenvolvimento
- `CHANGELOG.md` - Histórico de versões
- `PATHS.md` - Este arquivo
- `ANALISE_STATUS_ATUAL_E_PROXIMOS_PASSOS.md` - Roadmap

---

**Última atualização:** v1.9.6 - 23/10/2025

