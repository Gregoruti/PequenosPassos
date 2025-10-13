# CHANGELOG.MD

**Propósito**: Histórico completo de versões e mudanças do projeto PequenosPassos
com status de validação integrado para cada funcionalidade implementada. Serve
como registro oficial de evolução do projeto e guia de referência para
desenvolvimento futuro.

**Escopo**: Rastreamento de versões, validações, bugs corrigidos e roadmap do
PequenosPassos

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md                            │
│              (Define Processo de Versionamento)             │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Estabelece Critérios)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md                       │
│                (Define Funcionalidades)                     │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Implementa Features)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                   CHANGELOG.md (ESTE)                       │
│            Histórico + Validações Integradas                │
│                                                             │  
└─────────────────┬───────────────────────────────────────────┘
                  │ (Informa Status)
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                     PATHS.md                                │
│              (Estrutura Atual Validada)                     │
└─────────────────────────────────────────────────────────────┘
```

**Versão**: 1.3.1 | **Data**: 13/10/2025 | **Status**: TTS e ASR restaurados e funcionais, correções de build e validações integradas

---

## Estrutura Hierárquica de Rotinas
No PequenosPassos, uma Rotina representa o conjunto de atividades (tarefas) que devem ser realizadas em um dia. Cada Rotina é composta por diversas Tarefas, que são as atividades principais do fluxo diário. Cada Tarefa pode ser detalhada em um passo-a-passo, formado por Subtarefas (Steps), que orientam a execução da atividade de forma sequencial e didática.

- Rotina: conjunto de Tarefas do dia.
- Tarefa: atividade principal da rotina.
- Subtarefas (Steps): etapas sequenciais para realizar cada Tarefa.

Essa abordagem facilita o acompanhamento, personalização e gamificação das atividades.

---

## Sumário
1. [Histórico de Versões](#1-histórico-de-versões)
2. [Status de Validação Integrado](#2-status-de-validação-integrado)
3. [Roadmap de Funcionalidades](#3-roadmap-de-funcionalidades)

---

## 1. Histórico de Versões

### Versão 1.0.0 (09/10/2025) - Primeira Versão do App Inicializando após Renomeações

**Status da Versão**: `✅ Implementado e Validado`

#### Mudanças Implementadas
- **Renomeação Completa do Projeto**: Transição de "FonoVirtual_V2" para "PequenosPassos"
- **Atualização de Interface**:
  - Título principal alterado para "Pequenos Passos"
  - Informação do projeto alterada de "Projeto Integrador VI" para "TCC-540"
- **Correção de Configurações**:
  - Ajustes de navegação e temas
  - Remoção de módulos de exercícios e Teste Rápido
- **Funcionalidades Mantidas**:
  - SplashScreen com logo UNIVESP
  - HomeScreen com navegação para Cadastro, Atividades, Resultados, Debug
  - Módulos de ASR (Vosk) e TTS nativo
  - Tela de Debug

#### Validação
| Funcionalidade | Status | Data |
|---------------|--------|------|
| SplashScreen  | OK     | 09/10/2025 |
| HomeScreen    | OK     | 09/10/2025 |
| ASR (Vosk)    | OK     | 09/10/2025 |
| TTS           | OK     | 09/10/2025 |
| DebugScreen   | OK     | 09/10/2025 |

---

### Versão 1.2.0 (12/10/2025) - MVP Definido com Sprint de 3 Dias

**Status da Versão**: `🔄 Em Desenvolvimento - MVP Otimizado`

#### Mudanças Implementadas no MVP
- **Cadastro da Criança Expandido**:
  - Nome da criança (obrigatório)
  - Sexo (seletor visual Menino/Menina)
  - Foto (captura ou galeria, opcional)
- **Ordenação Automática por Horário**:
  - Tarefas ordenadas automaticamente por horário HH:mm
  - Sequenciamento inteligente das atividades do dia
- **Sistema de Status Completo**:
  - ⏳ Pendente (tarefa não iniciada)
  - ✅ Concluída (tarefa finalizada com sucesso)
  - ❌ Cancelada (tarefa não realizada)
- **Sprint Otimizado para 3 Dias**:
  - Dia 1: Fundação completa (configuração + dados + lógica)
  - Dia 2: Interface e navegação (telas + cadastro de tarefas)
  - Dia 3: Execução e polimento (funcionalidades + testes)

#### Estrutura de Dados MVP
- **ChildProfile**: Entidade para cadastro da criança
- **Task**: Tarefas com ordenação por horário
- **Step**: Subtarefas opcionais
- **TaskStatus**: Enum com PENDING, COMPLETED, CANCELED

#### Validação MVP
| Funcionalidade | Status | Critério |
|---------------|--------|----------|
| Cadastro da Criança | 🔄 | Nome, sexo e foto em < 2 min |
| Ordenação por Horário | 🔄 | Tarefas automáticamente ordenadas |
| Status Completo | 🔄 | 3 estados visuais claros |
| Interface TEA | 🔄 | Acessível para 4-6 anos |
| Sprint 3 Dias | 🔄 | MVP funcional em 72h |

#### Objetivos de Validação
- ✅ 80% dos usuários completam fluxo sem ajuda
- ✅ Cadastro da criança < 2 minutos
- ✅ Cadastro de tarefa < 1 minuto
- ✅ Interface compreensível para criança de 4-6 anos
- ✅ Ordenação por horário intuitiva
- ✅ Status (Pendente/Concluída/Cancelada) claros

---

### Versão 1.3.0 (12/10/2025) - Prompts Estruturais MVP e Versão Dinâmica na Tela Inicial

**Status da Versão**: `✅ Implementado e Validado`

#### Mudanças Implementadas
- **Metodologia PROMPT Aplicada**:
  - 15 prompts estruturais seguindo framework PROMPT (Persona, Role/Rules, Objective, Message, Parameters, Task)
  - Ordem de implementação respeitando Clean Architecture: Core → Aplicação → Infraestrutura → Apresentação
  - Critérios de validação específicos para cada módulo
- **Sprint de 3 Dias Detalhado**:
  - **Dia 1**: 5 prompts de fundação (MVP-01 a MVP-05) - estrutura, entidades, database, repositórios, use cases
  - **Dia 2**: 5 prompts de interface (MVP-06 a MVP-10) - theme, componentes, splash, onboarding, formulários
  - **Dia 3**: 5 prompts de execução (MVP-11 a MVP-15) - home, execução, navegação, testes, validação
- **Versão Dinâmica na Tela Inicial**:
  - HomeScreen atualizada para exibir versão dinamicamente usando `BuildConfig.VERSION_NAME`
  - Sincronização automática entre build.gradle.kts (versionName = "1.3.0") e interface
  - Conformidade com guidelines de versionamento automático
- **Documentação Personalizada**:
  - Permite personalização de imagens, áudios, instruções e feedbacks para rotinas, tarefas, subtarefas e recompensas
  - Sincronização completa entre SPECIFICATION_FOR_APP.md, PATHS.md, README.md e CHANGELOG.md
- **Princípios de Modularidade**:
  - Módulos pequenos, isolados e testáveis
  - Contexto mínimo viável para cada prompt
  - Critérios objetivos de validação por prompt
- **Adequação para TEA**:
  - Design System específico para autismo
  - Componentes acessíveis (48dp mínimo, cores adequadas)
  - Interface visual clara e previsível

#### Estrutura dos Prompts Criados
- **MVP-01**: Estrutura Base do Projeto (Clean Architecture + dependências)
- **MVP-02**: Entidades de Domínio MVP (4 entidades essenciais)
- **MVP-03**: Database e DAOs MVP (Room + queries otimizadas)
- **MVP-04**: Repositórios MVP (interfaces + implementações + Result wrapper)
- **MVP-05**: Use Cases MVP (lógica de negócio + validações)
- **MVP-06**: Theme e Design System MVP (Material 3 + cores TEA)
- **MVP-07**: Componentes Reutilizáveis MVP (6 componentes essenciais)
- **MVP-08**: SplashScreen MVP (verificação primeiro acesso + navegação)
- **MVP-09 a MVP-15**: Prompts restantes (em definição)

#### Validação da Versão 1.3.0
| Funcionalidade | Status | Data | Critérios |
|---------------|--------|------|-----------|
| Prompts Estruturais | ✅ | 12/10/2025 | Framework PROMPT aplicado + Clean Architecture |
| Versão Dinâmica | ✅ | 12/10/2025 | BuildConfig.VERSION_NAME exibido na HomeScreen |
| Documentação Sincronizada | ✅ | 12/10/2025 | Todos os docs atualizados com v1.3.0 |
| Personalização TEA | ✅ | 12/10/2025 | Suporte a imagens/áudios/feedbacks customizados |
| MVP-01 a MVP-08 | 🔄 | 12/10/2025 | Prompts criados, aguardando implementação |

#### Benefícios da Metodologia PROMPT
- **Clareza de Contexto**: Cada prompt focado em um módulo específico
- **Precisão**: Critérios objetivos de validação definidos
- **Iteração Rápida**: Validação isolada sem impacto sistêmico
- **Testabilidade**: Targets claros para cada módulo
- **Paralelização**: Possibilidade de desenvolvimento modular
- **Versionamento Automático**: Interface sincronizada com build configuration

#### Conformidade com GUIDELINES.md
- ✅ Modularidade First: Módulos pequenos e isolados
- ✅ Metodologia PROMPT: Framework aplicado rigorosamente
- ✅ Clean Architecture: Ordem Core → Aplicação → Infraestrutura → Apresentação
- ✅ Context-Aware: Contexto mínimo viável para cada prompt
- ✅ Anti-Regressão: Critérios de validação protegem funcionalidades
- ✅ Qualidade: Cobertura de testes e documentação especificadas
- ✅ Versionamento Automático: BuildConfig.VERSION_NAME na interface

---

## [MVP-01] Validação da Estrutura Base

**Data:** 13/10/2025
**Versão:** 1.3.0
**Status:** Estrutura base validada

- Checklist de validação documental criado em `docs/MVP01_VALIDATION.md`.
- Task Gradle `validateMVP01` adicionada para verificação automática da arquitetura base.
- **Tela Debug integrada:** Validações MVP centralizadas na tela Debug acessível via botão na HomeScreen.
- Estrutura de pastas, dependências essenciais e tema Material 3 conferidos.
- App compila e executa sem crash em dispositivo físico.
- Injeção de dependência (Hilt) inicializa sem erros.
- Processo de validação segue padrão das GUIDELINES.md.
- **Interface de validação:** Botões organizados por MVP (MVP-01 a MVP-05) com status visual.

---

## 1.1 Registro de Sincronização com GitHub

- Em 12/10/2025, todos os documentos e estrutura do projeto foram sincronizados e validados conforme especificação técnica.
- Status: Estrutura, funcionalidades e documentação integradas e validadas.

---

## 2. Status de Validação Integrado

Todas as funcionalidades e a estrutura hierárquica foram validadas após a migração e atualização para versão 1.1.3.

---

## 3. Roadmap de Funcionalidades
- Cadastro de Profissionais e Pacientes (em desenvolvimento)
- Atividades (em desenvolvimento)
- Resultados (em desenvolvimento)
- Integração com módulos de ASR/TTS
- Expansão futura para novas rotinas, relatórios e personalização de tarefas/subtarefas

---

**Versão do Documento**: 1.1.3
**Data**: 12/10/2025
**Status**: Documentação completa, sincronizada e validada com projeto PequenosPassos
