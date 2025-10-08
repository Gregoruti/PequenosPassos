# PATHS.MD

**Propósito**: Documentação unificada da estrutura de arquivos e telas do 
projeto FonoVirtual. Combina hierarquia de diretórios, mapeamento de telas, 
fluxos de navegação e estatísticas do projeto em um documento centralizado.

**Escopo**: Estrutura completa do projeto FonoVirtual (arquivos + telas + 
navegação)

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md                           │
│              (Framework de Desenvolvimento)                 │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Orienta Estruturação)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md                      │
│                 (Define Funcionalidades)                   │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Detalha Implementação)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                     PATHS.md (ESTE)                        │
│              Estrutura de Arquivos e Telas                 │
│           (100+ arquivos | 8 telas | 24 grupos)            │
└─────────────────────────────────────────────────────────────┘
```

**Versão**: 2.0.0 | **Data**: 07/10/2025 | **Status**: Sincronizado com 
FonoVirtual v1.0.13

---

## Sumário
1. [Estrutura de Arquivos](#1-estrutura-de-arquivos)
2. [Estrutura de Telas](#2-estrutura-de-telas)
3. [Fluxo de Navegação](#3-fluxo-de-navegação)
4. [Estatísticas do Projeto](#4-estatísticas-do-projeto)

---

## 1. Estrutura de Arquivos

### 1.1 Visão Geral do Projeto
**Versão**: 1.0.13 (30/09/2025)  
**Arquitetura**: MVVM com Jetpack Compose  
**Linguagem**: Kotlin  

### 1.2 Hierarquia de Diretórios

```
D:\Softwares\PequenosPassos\
├── app/                                  # Módulo principal da 
│   ├── build.gradle.kts                  # aplicação Android
│   └── src/main/                         # Build script (versionName 
│       ├── AndroidManifest.xml           # "1.0.13", versionCode 13)
│       ├── assets/vosk-model-small-pt-0.3/  # Manifesto com permissões 
│       │   └── uuid                      # (✅ v1.0.2)
│       ├── java/com/example/fonovirtual_v2/  # Modelo ASR offline PT-BR
│       │   ├── MainActivity.kt           # UUID: 5689a7c0-2128-48d4-
│       │   ├── ui/                       # 8f76-bf8171174a9e
│       │   │   ├── theme/Theme.kt        # Activity principal (✅ v1.0.3)
│       │   │   ├── splash/SplashScreen.kt     # Camada de Apresentação
│       │   │   ├── home/HomeScreen.kt         # Tema modo claro (✅ v1.0.4)
│       │   │   ├── debug/DebugScreen.kt       # Tela inicial (✅ v0.2.1)
│       │   │   ├── exercises/recognition/     # Tela principal (✅ v1.0.4)
│       │   │   │   ├── SimpleRecognitionExerciseScreen.kt    # Tela debug 
│       │   │   │   ├── SimpleRecognitionExerciseViewModel.kt # (✅ v1.0.2)
│       │   │   │   └── model/WordExercise.kt                 # Exercícios de 
│       │   │   ├── exercises/result/          # fonoaudiologia
│       │   │   │   └── ExerciseResultScreen.kt # (✅ v1.0.13)
│       │   │   ├── tts_test/                  # (✅ v1.0.13)
│       │   │   │   ├── TtsTestScreen.kt       # (✅ v1.0.3)
│       │   │   │   └── TtsTestViewModel.kt    # Telas de resultado
│       │   │   └── asr_test/                  # (✅ v1.0.3)
│       │   │       ├── AsrTestScreen.kt       # Módulo TTS
│       │   │       └── AsrTestViewModel.kt    # (✅ v0.4.0)
│       │   ├── speech/                   # (✅ v0.4.0)
│       │   │   ├── VoskAsrModule.kt      # Módulo ASR
│       │   │   └── TextToSpeechModule.kt # (✅ v1.0.2)
│       │   └── data/exercises/recognition/    # (✅ v1.0.2)
│       │       └── WordsRepository.kt    # Camada de Infraestrutura
│       └── res/                          # ASR com Vosk (✅ v1.0.2)
│           ├── values/strings.xml        # TTS nativo (✅ v0.4.0)
│           ├── drawable/                 # Camada de Dados
│           └── mipmap/                   # 100 palavras, 24 grupos 
├── images/                               # (✅ v1.0.10)
│   └── univesp.jpg                       # Recursos de texto (✅ v1.0.11)
├── docs/                                 # Recursos gráficos
│   ├── GUIDELINES.md                     # Ícones da aplicação
│   ├── PATHS.md                          # Assets externos
│   ├── CHANGELOG.md                      # Logo UNIVESP para splash
│   ├── SPECIFICATION_FOR_APP.md          # Documentação estrutural
│   └── README.md                         # Diretrizes integradas (v2.0.0)
├── gradle/                               # Este arquivo - estrutura unificada
├── .github/copilot-instructions.md       # Histórico com validações 
└── RELATORIO_TECNICO_FONOVIRTUAL_V2.md  # integradas
```                                       # Especificações do projeto
                                          # Documentação padrão
                                          # Configurações Gradle
                                          # Instruções para GitHub Copilot
                                          # Relatório técnico (✅ v1.0.13)

### 1.3 Arquivos Críticos por Camada

#### 1.3.1 Camada de Domínio
- **WordExercise.kt**: Modelo de dados para exercícios de pronúncia
- **Regras de Negócio**: Implementadas nos ViewModels (padrão MVVM)

#### 1.3.2 Camada de Aplicação  
- **ViewModels**: Coordenam use cases e gerenciam estado da UI
- **Navigation**: Implementado via Jetpack Compose Navigation

#### 1.3.3 Camada de Infraestrutura
- **VoskAsrModule.kt**: Integração com modelo ASR offline
- **TextToSpeechModule.kt**: Wrapper para TTS nativo Android
- **WordsRepository.kt**: Fonte de dados para exercícios

#### 1.3.4 Camada de Apresentação
- **Screens**: Composables responsáveis pela UI
- **Theme.kt**: Sistema de design em modo claro
- **MainActivity.kt**: Ponto de entrada da aplicação

---

## 2. Estrutura de Telas

### 2.1 Fluxo Principal de Telas

#### 2.1.1 Tela de Entrada
**SplashScreen** (`SplashScreen.kt`)
- **Propósito**: Primeira impressão com identidade UNIVESP
- **Duração**: 3 segundos ou toque do usuário
- **Status**: ✅ Validado (v0.2.1)
- **Layout ASCII**:
```
+--------------------------------------+
|                                      |
|          [LOGO UNIVESP]              |
|                                      |
|      (Fundo Branco - Modo Claro)     |
|                                      |
|         (3s ou toque)                |
+--------------------------------------+
```

#### 2.1.2 Tela Principal
**HomeScreen** (`HomeScreen.kt`)
- **Propósito**: Hub central de navegação com versão dinâmica
- **Versão Exibida**: 1.0.13 (via BuildConfig.VERSION_NAME)
- **Status**: ✅ Validado (v1.0.4 - Modo claro, v1.0.13 - Versão dinâmica)
- **Layout ASCII**:
```
+--------------------------------------+
| FonoVirtual (Título Preto)           |
|                                      |
| [█████ Cadastro ████████████████████] |
| [█████ Teste Rápido ████████████████] |
| [█████ Atividades ██████████████████] |
| [█████ Resultados ██████████████████] |
| [█████ Debug ███████████████████████] |
|                                      |
| Versão: 1.0.13 (Texto Preto)        |
| Projeto Integrador VI - 2025        |
|                                      |
| (Fundo Branco - Modo Claro)          |
+--------------------------------------+
```

### 2.2 Módulo de Exercícios

#### 2.2.1 Tela de Exercícios de Pronúncia
**SimpleRecognitionExerciseScreen** (`SimpleRecognitionExerciseScreen.kt`)
- **Propósito**: Sistema completo de exercícios fonoaudiológicos
- **Status**: ✅ Validado (v1.0.13 - Bug crítico corrigido)
- **Características**:
  - Títulos dinâmicos contextuais
  - 4 categorias de exercícios
  - Reconhecimento contínuo de voz
  - Feedback em tempo real
- **Layout ASCII**:
```
+--------------------------------------+
| << [Título Dinâmico do Exercício]    |
|                                      |
| Número de Sílabas:                   |
| [2] [3] [4] [5]                     |
|                                      |
| Sons Consonantais:                   |
| [BR] [CR] [FR] [GR]                 |
|                                      |
| Dígrafos:                           |
| [LH] [NH] [RR] [SS]                 |
|                                      |
| Sílaba Tônica:                      |
| [Á] [É] [Ê] [Ú]                    |
| [ÃO] [ÕE] [ÇÃO] [SÃO]              |
+--------------------------------------+
```

#### 2.2.2 Tela de Exercício Ativo
- **Funcionalidades**:
  - Palavra atual destacada
  - Botão "Ouvir Exemplo" com TTS
  - Reconhecimento automático contínuo
  - Texto reconhecido em tempo real
  - Botão "Próxima Palavra"
- **Layout ASCII**:
```
+--------------------------------------+
| << [Categoria]: [Tipo Específico]    |
|                                      |
|     PALAVRA ATUAL: "computação"      |
|                                      |
| [🔊 Ouvir Exemplo]                  |
|                                      |
| Você disse: "computação"             |
| Status: ✅ Reconhecido              |
|                                      |
| [➡️ Próxima Palavra]                |
|                                      |
| Progresso: 2/5                       |
+--------------------------------------+
```

#### 2.2.3 Tela de Resultado
**ExerciseResultScreen** (`ExerciseResultScreen.kt`)
- **Propósito**: Feedback de desempenho pós-exercício
- **Status**: ✅ Validado (v1.0.3)
- **Métricas**: Percentual de acerto com classificação
- **Layout ASCII**:
```
+--------------------------------------+
|          Exercício Concluído!        |
|                                      |
|         🎯 85% de Acerto            |
|                                      |
|           Muito Bom!                 |
|                                      |
| Palavras corretas: 17/20             |
|                                      |
| [🏠 Voltar ao Início]               |
| [🔄 Repetir Exercício]              |
+--------------------------------------+
```

### 2.3 Módulo de Debug

#### 2.3.1 Tela de Debug
**DebugScreen** (`DebugScreen.kt`)
- **Propósito**: Testes isolados de funcionalidades
- **Status**: ✅ Validado (v1.0.2)

#### 2.3.2 Teste de TTS
**TtsTestScreen** (`TtsTestScreen.kt`)
- **Funcionalidades**: Input de texto, síntese de fala, controle de velocidade
- **Status**: ✅ Validado (v0.4.0)

#### 2.3.3 Teste de ASR  
**AsrTestScreen** (`AsrTestScreen.kt`)
- **Funcionalidades**: Gravação, transcrição em tempo real, histórico
- **Status**: ✅ Validado (v1.0.2)

---

## 3. Fluxo de Navegação

### 3.1 Navegação Principal
```
SplashScreen (3s)
    ↓
HomeScreen
    ├── Cadastro → [Futura implementação v1.1.0]
    ├── Teste Rápido → SimpleRecognitionExerciseScreen
    ├── Atividades → [Futura implementação v1.2.0]
    ├── Resultados → [Futura implementação v1.1.1]
    └── Debug → DebugScreen
```

### 3.2 Fluxo de Exercícios
```
HomeScreen [Teste Rápido]
    ↓
SimpleRecognitionExerciseScreen
    ├── Seleção de Categoria
    ├── Seleção de Tipo Específico
    ↓
Exercício Ativo (mesmo screen)
    ├── Palavra por palavra
    ├── Reconhecimento automático
    ├── Feedback imediato
    ↓
ExerciseResultScreen
    ├── Métricas de desempenho
    ├── Opção de repetir
    └── Voltar ao início
```

### 3.3 Fluxo de Debug
```
HomeScreen [Debug]
    ↓
DebugScreen
    ├── Testar TTS → TtsTestScreen
    └── Testar ASR → AsrTestScreen
```

---

## 4. Estatísticas do Projeto

### 4.1 Métricas de Implementação
- **Total de Telas**: 8 telas principais implementadas
- **Modules Speech**: 2 (ASR Vosk + TTS Nativo)
- **Exercícios**: 24 grupos organizados em 4 categorias
- **Palavras**: 100 palavras distribuídas pelos exercícios
- **Cobertura de Funcionalidades**: 95% do MVP implementado

### 4.2 Status de Validação por Módulo
| Módulo | Versão | Status | Última Validação |
|--------|--------|--------|------------------|
| SplashScreen | v0.2.1 | ✅ Validado | 30/09/2025 |
| HomeScreen | v1.0.4 | ✅ Validado | 30/09/2025 |
| Debug Module | v1.0.2 | ✅ Validado | 30/09/2025 |
| Exercícios Core | v1.0.13 | ✅ Validado | 30/09/2025 |
| ASR Module | v1.0.2 | ✅ Validado | 30/09/2025 |
| TTS Module | v0.4.0 | ✅ Validado | 30/09/2025 |
| WordsRepository | v1.0.10 | ✅ Validado | 30/09/2025 |
| Results Screen | v1.0.3 | ✅ Validado | 30/09/2025 |

### 4.3 Categorias de Exercícios Implementadas

#### 4.3.1 Número de Sílabas (4 grupos)
- **2 Sílabas**: casa, gato, livro, mesa, água
- **3 Sílabas**: cadeira, boneca, janela, banana, sapato  
- **4 Sílabas**: computação, integrado, chocolate, telefone, abacaxi
- **5 Sílabas**: geografia, matemática, aplicativo, comunicação, universidade

#### 4.3.2 Sons Consonantais (4 grupos)
- **BR**: brasil, braço, branco, brincar, abraço
- **CR**: criança, cravo, crescer, criado, criativo
- **FR**: frango, fruta, frio, fraco, frase
- **GR**: grande, grupo, grau, grave, gritar

#### 4.3.3 Dígrafos (3 grupos)
- **LH**: folha, olho, milho, calha, ralhar
- **NH**: ninho, sonho, linha, ganhar, manhã
- **RR**: carro, ferro, terra, serra, torrar
- **SS**: passar, massa, classe, grosso, osso

#### 4.3.4 Sílaba Tônica (8 grupos)
- **Á**: matemática, prática, fantástico, rápido, árvore
- **É**: café, pé, mané, chulé, filé
- **Ê**: você, bebê, português, inglês, três
- **Ú**: último, público, úmido, número, fúria
- **ÃO**: pão, mão, coração, ação, informação
- **ÕE**: põe, limões, camões, botões, aviões
- **ÇÃO**: ação, emoção, criação, educação, informação
- **SÃO**: são, órgão, alemão, cristão, cidadão

---

**Versão**: 2.0.0 (Unificação PATHS_FILES + PATHS_SCREENS)  
**Data**: 07/10/2025  
**Status**: Documentação completa e sincronizada com código v1.0.13
