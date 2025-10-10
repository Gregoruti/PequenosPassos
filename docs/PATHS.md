# PATHS.MD

**Propósito**: Documentação unificada da estrutura de arquivos e telas do
projeto PequenosPassos. Combina hierarquia de diretórios, mapeamento de telas,
fluxos de navegação e estatísticas do projeto em um documento centralizado.

**Escopo**: Estrutura completa do projeto PequenosPassos (arquivos + telas +
navegação)

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md                            │
│              (Framework de Desenvolvimento)                 │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Orienta Estruturação)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md                       │
│                 (Define Funcionalidades)                    │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Detalha Implementação)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                     PATHS.md (ESTE)                         │
│              Estrutura de Arquivos e Telas                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Versão**: 1.0.0 | **Data**: 09/10/2025 | **Status**: Sincronizado com PequenosPassos

---

## Sumário
1. [Estrutura de Arquivos](#1-estrutura-de-arquivos)
2. [Estrutura de Telas](#2-estrutura-de-telas)
3. [Fluxo de Navegação](#3-fluxo-de-navegação)
4. [Estatísticas do Projeto](#4-estatísticas-do-projeto)

---

## 1. Estrutura de Arquivos

### 1.1 Visão Geral do Projeto
**Versão**: 1.0.0 (09/10/2025)  
**Arquitetura**: MVVM com Jetpack Compose  
**Linguagem**: Kotlin  

### 1.2 Hierarquia de Diretórios

```
D:\Softwares\PequenosPassos\
├── app/                                  # Módulo principal da aplicação Android
│   ├── build.gradle.kts                  # Build script
│   └── src/main/                         # Código fonte
│       ├── AndroidManifest.xml           # Manifesto
│       ├── assets/vosk-model-small-pt-0.3/  # Modelo ASR offline PT-BR
│       └── java/com/example/fonovirtual_v2/ # Código principal
├── docs/                                 # Documentação
│   ├── CHANGELOG.md
│   ├── PATHS.md
│   ├── README.md
│   ├── GUIDELINES.md
│   └── SPECIFICATION_FOR_APP.md
```

---

## 2. Estrutura de Telas

- **SplashScreen**: Tela inicial com logo UNIVESP
- **HomeScreen**: Tela principal com navegação para Cadastro, Atividades, Resultados, Debug
- **DebugScreen**: Tela para testes de ASR/TTS
- **TtsTestScreen**: Teste de síntese de fala
- **AsrTestScreen**: Teste de reconhecimento de voz

---

## 3. Fluxo de Navegação

```
SplashScreen (3s)
    ↓
HomeScreen
    ├── Cadastro → [Futura implementação v1.1.0]
    ├── Atividades → [Futura implementação v1.2.0]
    ├── Resultados → [Futura implementação v1.1.1]
    └── Debug → DebugScreen
```

---

## 4. Estatísticas do Projeto
- Módulos ASR/TTS: Implementados e validados
- Exercícios e Teste Rápido: Removidos na migração
- Cadastro, Atividades, Resultados: Em desenvolvimento

---

## Sincronização de Estrutura

- Estrutura de arquivos e telas sincronizada com o repositório remoto em 09/10/2025.
- Repositório oficial: [https://github.com/Gregoruti/PequenosPassos](https://github.com/Gregoruti/PequenosPassos)
