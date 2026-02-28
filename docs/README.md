<!--
Arquivo: docs/README.md
Tipo: Documento principal do projeto Pequenos Passos
Objetivo: Apresentação geral do projeto, status, tecnologias, arquitetura e roadmap
Correlações: CHANGELOG.md, GUIDELINES.md, SPECIFICATION_FOR_APP.md, MVP11_ESTADO_ATUAL_CONSOLIDADO.md
Histórico de alterações:
- 2025-02-26 (Claude Opus 4): v2.5.1 - Nova branch, revisão geral de documentação, transição para Claude Opus 4
- 2025-12-02 (Claude Sonnet 4.5): v2.5.0 Fase 1 - Estrutura Export/Import + Perfil Masculino
- 2025-11-07 (Claude Sonnet 4.5): v2.4.0 - Versão de apresentação TCC
- 2025-11-03 (GPT-4.1): v2.3.0 - Correções UI, logo, ícone
- 2025-11-01 (Claude Sonnet 4.5): v2.1.0 - Consolidação MVP-11
Status Atual: v2.5.0 → v2.5.1 (nova branch) - Code Assistant: Claude Opus 4 (GitHub Copilot)
-->

# Pequenos Passos 🎯

**Aplicativo Android de Auxílio para Rotinas Infantis**

[![Versão](https://img.shields.io/badge/versão-2.5.0-blue.svg)](https://github.com)
[![MVP](https://img.shields.io/badge/MVP--15%20Concluído-green.svg)](https://github.com)
[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com)
[![Status](https://img.shields.io/badge/status-100%25%20funcional-success.svg)](https://github.com)

---

## 📢 Status do Projeto

**O aplicativo está 100% funcional e estável na versão 2.5.0 (02/12/2025).**

### 🔄 Transição de Code Assistant (26/02/2026)
- **Anterior:** Claude Sonnet 4.5 (GitHub Copilot) — responsável de v1.0 a v2.5.0
- **Temporário:** GPT-4.1 (GitHub Copilot) — v2.1.0 a v2.3.0
- **Atual:** Claude Opus 4 (GitHub Copilot) — a partir de v2.5.1

### ✨ Funcionalidades Implementadas (v2.5.0)
- ✅ 11 tarefas pré-instaladas com 143 passos detalhados
- ✅ TTS (Text-to-Speech) em toda a execução de tarefas
- ✅ ASR (Reconhecimento de Voz) em pop-ups de tempo extra
- ✅ Sistema de estrelas e gamificação completo
- ✅ Tela de Histórico e Ferramentas com estatísticas semanais
- ✅ Controle de tarefas completadas por dia (reset diário)
- ✅ Estrutura de Export/Import de tarefas (JSON + Base64)
- ✅ Perfil padrão masculino configurável
- ✅ Emails de feedback na SplashScreen (v2.4.0)

---

> 📚 **Documentação completa** em `/docs`:
> - **MVP11_ESTADO_ATUAL_CONSOLIDADO.md** - Estado atual detalhado
> - **GUIDELINES.md** - Diretrizes e boas práticas
> - **CHANGELOG.md** - Histórico de mudanças
> - **SPECIFICATION_FOR_APP.md** - Especificação técnica

## 📋 Sobre o Projeto

O **Pequenos Passos** é um aplicativo Android desenvolvido para auxiliar crianças da primeira infância (até 6 anos) com necessidades especiais (TEA, TDAH, dificuldades de aprendizagem) a executarem tarefas do dia a dia de forma estruturada, visual e interativa.

### 🎯 Objetivo Principal
Facilitar a execução de rotinas diárias através de:
- Interface visual e intuitiva
- Instruções passo a passo com imagens
- Síntese de voz (TTS) para instruções e reforço positivo
- Reconhecimento de voz (ASR) para interação natural
- Sistema de recompensas (estrelas) e gamificação
- Funcionamento 100% offline (incluindo ASR via Vosk)

---

## 🚀 MVPs Concluídos

| MVP | Descrição | Status | Versão |
|-----|-----------|--------|--------|
| MVP-01 | Estrutura Base (Clean Architecture) | ✅ | 1.2.0 |
| MVP-02 | Entidades e Modelos | ✅ | 1.3.0 |
| MVP-03 | Database (Room + Migrations) | ✅ | 1.4.0 |
| MVP-04 | Repositórios | ✅ | 1.5.0 |
| MVP-05 | Use Cases | ✅ | 1.6.0 |
| MVP-06 | Theme/Design System | ✅ | 1.7.0 |
| MVP-07 | UI Completa (Galeria, Edição) | ✅ | 1.9.6 |
| MVP-08 | Gamificação + TTS | ✅ | 1.10.1 |
| MVP-09 | Histórico + Estrelas + Reset Diário | ✅ | 1.11.x |
| MVP-10 | Histórico & Ferramentas + Estatísticas | ✅ | 2.0.x |
| MVP-11 | Consolidação + Documentação | ✅ | 2.1.0 |
| MVP-14 | ASR em Pop-ups (6 fases) | ✅ | 2.2.0 |
| MVP-15 | 11 Tarefas Pré-Instaladas (143 passos) | ✅ | 2.3.0 |
| v2.4.0 | Apresentação TCC (feedback emails) | ✅ | 2.4.0 |
| v2.5.0 | Export/Import Estrutura (Fase 1) | ✅ | 2.5.0 |

**Total de Testes**: 142+ testes unitários ✅

---

## 🏗️ Arquitetura

### Clean Architecture (3 Camadas)

```
📦 com.pequenospassos
 ┣ 📂 presentation/          # UI (Jetpack Compose)
 ┃ ┣ 📂 screens/            # Telas da aplicação
 ┃ ┃ ┣ 📂 home/            # HomeScreen
 ┃ ┃ ┣ 📂 history/         # Histórico & Ferramentas
 ┃ ┃ ┣ 📂 taskexecution/   # Execução de tarefas (TTS + ASR)
 ┃ ┃ ┣ 📂 taskmanagement/  # Edição de tarefas + Export/Import
 ┃ ┃ ┗ 📂 registration/    # Cadastro de criança
 ┃ ┣ 📂 components/         # Componentes reutilizáveis
 ┃ ┣ 📂 navigation/         # Sistema de navegação
 ┃ ┣ 📂 utils/              # TtsManager, AsrManager, VoiceCommandParser
 ┃ ┗ 📂 theme/              # Design System (Material 3)
 ┃
 ┣ 📂 domain/               # Regras de negócio
 ┃ ┣ 📂 model/             # Entidades (7 tabelas)
 ┃ ┣ 📂 repository/        # Interfaces
 ┃ ┗ 📂 usecase/           # Casos de uso
 ┃
 ┗ 📂 data/                # Fontes de dados
   ┣ 📂 database/          # Room Database (8 migrations)
   ┃ ┣ 📂 dao/            # DAOs
   ┃ ┗ 📂 entities/       # Entidades Room
   ┣ 📂 export/            # Export/Import Manager (JSON)
   ┗ 📂 repository/        # Implementações
```

---

## 🛠️ Tecnologias

### Core
- **Kotlin** 100%
- **Jetpack Compose** (UI moderna e declarativa)
- **Hilt/Dagger** (Injeção de dependências)
- **Coroutines + Flow** (Programação reativa)
- **kotlinx-serialization** (Export/Import JSON)

### Database
- **Room** (SQLite local) — 7 tabelas, 8 migrations
- **TypeConverters** para LocalDate/LocalDateTime

### Acessibilidade
- **TTS** (Text-to-Speech) — Android nativo
- **ASR** (Automatic Speech Recognition) — Vosk (100% offline)
- **VoiceCommandParser** — 62 comandos infantis (32 positivos + 30 negativos)

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

## 📱 Telas Implementadas

1. **SplashScreen** — Tela inicial com versão e emails de feedback
2. **HomeScreen** — Menu principal com estrelas do dia e tarefas pendentes
3. **ChildRegistrationScreen** — Cadastro com nome, data de nascimento, gênero, foto
4. **TaskListScreen** — Lista de atividades com status de conclusão
5. **TaskFormScreen** — Criar/editar tarefas com steps, imagens e timer
6. **TaskManagementScreen** — Edição de atividades + botões Export/Import
7. **TaskExecutionScreen** — Execução passo a passo com TTS, timer, imagens
8. **TaskCompletionScreen** — Tela de parabéns com estrelas e reforço positivo (TTS)
9. **HistoryScreen** — Histórico & Ferramentas com estatísticas semanais
10. **TtsTestScreen** — Teste de síntese de voz
11. **AsrTestScreen** — Teste de reconhecimento de voz

---

## 📊 Tarefas Pré-Instaladas (MVP-15)

| # | Tarefa | Passos | Categoria |
|---|--------|--------|-----------|
| 1 | Escovar os Dentes | 12 | Higiene Pessoal |
| 2 | Lavar as Mãos | 8 | Higiene Pessoal |
| 3 | Lavar o Rosto | 11 | Higiene Pessoal |
| 4 | Fazer Cocô | 8 | Higiene Pessoal |
| 5 | Tomar Banho | 20 | Higiene Pessoal |
| 6 | Fazer Xixi | 8 | Higiene Pessoal |
| 7 | Vestir-se | 12 | Autonomia |
| 8 | Arrumar a Cama | 10 | Autonomia |
| 9 | Recolher Brinquedos | 10 | Organização |
| 10 | Escolher Brinquedo do Banho | 8 | Lazer |
| 11 | Andando Fora de Casa | 15 | Segurança |

**Total:** 11 tarefas, 143 passos detalhados

---

## 🎓 Contexto Acadêmico

**Instituição**: UNIVESP — Universidade Virtual do Estado de São Paulo  
**Curso**: Engenharia de Computação  
**Disciplina**: Projeto Integrador VI  
**Ano**: 2025  

### Feedbacks
- 📧 2100394@aluno.univesp.br
- 📧 gregoruti@gmail.com

---

## 🚀 Como Executar

### Pré-requisitos
- Android Studio (última versão estável)
- JDK 11+
- Android SDK (API 24+)

### Passos

1. Clone o repositório
2. Abra no Android Studio (File → Open)
3. Aguarde Sync Gradle
4. Conecte dispositivo Android (API 24+) ou use emulador
5. Run → Run 'app'

### Compilar via Terminal (PowerShell)
```powershell
# IMPORTANTE: Sempre usar .\ no PowerShell
# NUNCA usar && ou & para encadear comandos
.\gradlew clean
.\gradlew assembleDebug
.\gradlew installDebug
```

### APK
O APK de debug fica em: `app/build/outputs/apk/debug/app-debug.apk`

---

## 📖 Documentação Completa

| Arquivo | Descrição |
|---------|-----------|
| [CHANGELOG.md](CHANGELOG.md) | Histórico completo de mudanças |
| [GUIDELINES.md](GUIDELINES.md) | Diretrizes e boas práticas |
| [SPECIFICATION_FOR_APP.md](SPECIFICATION_FOR_APP.md) | Especificação técnica |
| [MVP11_ESTADO_ATUAL_CONSOLIDADO.md](MVP11_ESTADO_ATUAL_CONSOLIDADO.md) | Estado consolidado |
| [MATERIAL_APRESENTACAO_TCC.md](MATERIAL_APRESENTACAO_TCC.md) | Material para apresentação |
| [RELATORIO_TECNICO_ABNT_2025.md](RELATORIO_TECNICO_ABNT_2025.md) | Relatório técnico ABNT |

---

## 🤝 Code Assistants Utilizados

| Modelo | Período | Versões |
|--------|---------|---------|
| Claude Sonnet 4.5 | Início → v2.1.0 | v1.0 a v2.1.0 |
| GPT-4.1 | Transição temporária | v2.1.0 a v2.3.0 |
| Claude Sonnet 4.5 | Retorno | v2.3.0 a v2.5.0 |
| Claude Opus 4 | Atual | v2.5.1+ |

> **Nota:** Todos os modelos via GitHub Copilot. Transições ocorreram por fim de requisições premium dos respectivos modelos.

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

**Última atualização**: 26/02/2026  
**Versão**: 2.5.0 (APK gerado) → 2.5.1 (nova fase)  
**Status**: Funcional e em desenvolvimento ativo 🚀  
**Code Assistant**: Claude Opus 4 (GitHub Copilot)
