# PATHS.MD

**Propósito**: Documentação unificada da estrutura de arquivos e telas do projeto
PequenosPassos. Combina hierarquia de diretórios, mapeamento de telas, fluxos de
navegação e estatísticas do projeto em um documento centralizado.

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
└─────────────────┬───────────────────────────────────────────┘
                  │ (Documenta Estrutura)
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                   CHANGELOG.md                              │
│       (Histórico de Versões + Status de Validação)         │
└─────────────────────────────────────────────────────────────┘
```

**Versão**: 1.4.0 | **Data**: 13/10/2025 | **Status**: Sincronizado com MVP-02
implementado e testado

---

## Status de Validação de Testes

### 📊 Resumo Executivo de Testes

**Última Execução:** 13/10/2025 23:40
**Build Status:** ✅ **SUCCESSFUL** (41s)
**Resultado:** 58/58 testes passando (100%)

| Categoria | Testes | Passaram | Falharam | Status |
|-----------|--------|----------|----------|--------|
| **MVP-02: Domain** | 58 | 58 | 0 | ✅ 100% |
| ChildProfileTest | 13 | 13 | 0 | ✅ |
| TaskTest | 21 | 21 | 0 | ✅ |
| StepTest | 16 | 16 | 0 | ✅ |
| AppSettingsTest | 14 | 14 | 0 | ✅ |

**Cobertura:** 100% dos métodos públicos das entidades de domínio
**Relatório HTML:** `app/build/reports/tests/testDebugUnitTest/index.html`

**Referência Completa:** Ver CHANGELOG.md seções 2 e 3 para estratégia de validação
e testes

---

## MVP - Estrutura Simplificada
Para o MVP (Produto Mínimo Viável), a estrutura foi otimizada para 3 dias de
desenvolvimento:

### Entidades MVP
- **ChildProfile**: Cadastro da criança (nome, sexo, foto)
- **Task**: Tarefas ordenadas por horário
- **Step**: Subtarefas/passos opcionais
- **TaskStatus**: PENDING, COMPLETED, CANCELED

### Telas MVP (5 principais)
1. **SplashScreen**: Verificação de primeiro uso
2. **OnboardingScreen**: Cadastro da criança (nome, sexo, foto)
3. **TaskFormScreen**: Cadastro simplificado de tarefas
4. **HomeScreen**: Lista de tarefas ordenadas por horário
5. **TaskExecutionScreen**: Execução de tarefas com passos

### Funcionalidades MVP
- ✅ Cadastro único da criança
- ✅ Tarefas ordenadas automaticamente por horário (HH:mm)
- ✅ Status completo: ⏳ Pendente | ✅ Concluída | ❌ Cancelada
- ✅ Sistema básico de estrelas
- ✅ Interface TEA adaptada

---

## Atualização
A estrutura do app segue a hierarquia:
- Rotina: conjunto de tarefas do dia
- Tarefa: atividade principal (ordenada por horário)
- Subtarefas (Steps): etapas sequenciais de cada tarefa

Cada rotina é composta por tarefas ordenadas por horário, e cada tarefa pode ser detalhada em passos
(subtarefas), facilitando o acompanhamento e personalização.

O MVP foca no essencial: cadastro da criança e execução de tarefas com status completo.

O app possui base de dados padrão de imagens e mensagens (texto/áudio) para rotinas, tarefas, subtarefas e recompensas, mas todos esses itens podem ser personalizados pelo usuário. Veja detalhes em [SPECIFICATION_FOR_APP.md](./SPECIFICATION_FOR_APP.md).

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
