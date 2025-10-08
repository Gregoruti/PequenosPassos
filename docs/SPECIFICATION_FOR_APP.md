# SPECIFICATION_FOR_APP.MD

**Propósito**: Especificações técnicas completas e detalhadas exclusivamente
para o projeto Pequenos Passos. Define arquitetura, funcionalidades, regras de
negócio, status de desenvolvimento e roadmap específico da aplicação de apoio a
Atividades da Vida Diária para crianças com TEA.

**Escopo**: Detalhamento técnico específico do projeto Pequenos Passos
(funcionalidades + arquitetura + progresso)

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md                           │
│              (Framework Universal de Desenvolvimento)       │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Aplica Metodologia)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md (ESTE)               │
│              Especificações do Pequenos Passos             │
│         (Funcionalidades | Arquitetura | Status)           │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Implementa Detalhes)
                   ▼
┌──────────────────┬──────────────────┬──────────────────────┐
│    PATHS.md      │   CHANGELOG.md   │      README.md       │
│  (Estrutura)     │  (Histórico)     │   (Documentação)     │
└──────────────────┴──────────────────┴──────────────────────┘
```

**Versão**: 0.1.0 | **Data**: 07/10/2025 | **Status**: Especificação Inicial

---

**Instruções para Code Assistant**: Sempre responda em Português do Brasil.
Você é um Code Assistant que deve manter atualizado este arquivo de
especificações do App "Pequenos Passos". Sua tarefa é **adicionar, remover ou
atualizar** seções sempre que novos módulos, funcionalidades, regras ou
decisões arquiteturais específicas deste projeto forem criadas. Respeite a
estrutura em tópicos e escreva de forma clara e organizada.

## Visão Geral e Objetivos do Projeto "Pequenos Passos"

Este projeto é um aplicativo Android voltado para auxiliar crianças de 0 a 6
anos com Transtorno do Espectro Autista (TEA) na execução de Atividades da
Vida Diária (AVDs) de higiene, promovendo maior autonomia infantil e reduzindo
a sobrecarga familiar. O aplicativo atua como recurso complementar ao
acompanhamento terapêutico profissional.

### Objetivos Principais

-   **Reduzir Ansiedade Relacionada à Rotina**: Fornecer estrutura previsível,
    clara e visual para atividades diárias
-   **Aprimorar Aquisição de Habilidades**: Desmembrar AVDs complexas em
    etapas simples e visualmente guiadas
-   **Capacitar Cuidadores**: Fornecer ferramenta eficaz para criar, gerenciar
    e implementar rotinas diárias consistentemente
-   **Melhorar Colaboração Família-Terapeuta**: Criar plataforma compartilhada
    onde rotinas terapêuticas podem ser implementadas em casa

### Público-Alvo

**Usuários Primários:**
-   Crianças de 0 a 6 anos com Transtorno do Espectro Autista (TEA)
-   Características: Aprendizes visuais, desafios com funções executivas,
    afinidade natural por dispositivos móveis

**Usuários Secundários:**
-   **Pais e Cuidadores**: Administradores do aplicativo, responsáveis por
    criar e gerenciar rotinas
-   **Profissionais Terapêuticos**: Terapeutas ocupacionais, psicólogos que
    prescrevem rotinas e monitoram progresso

## Fundamentação Teórica

### Metodologias de Base

-   **TEACCH (Treatment and Education of Autistic and Communication
    Handicapped Children)**: Uso de suporte visual estruturado
-   **PECS (Picture Exchange Communication System)**: Comunicação através de
    pictogramas
-   **ABA (Applied Behavior Analysis)**: Análise Comportamental Aplicada com
    ênfase em reforço positivo
-   **Gamificação**: Elementos de jogo para engajamento e motivação

### Princípios de Design

-   **Aprendizagem Visual**: Pessoas com TEA respondem melhor a pistas visuais
    que a mensagens escritas ou faladas
-   **Simplicidade e Previsibilidade**: Interface minimalista com poucos
    botões e elementos claros
-   **Reforço Positivo Imediato**: Feedback curto e eficaz após conclusão de
    etapas
-   **Personalização**: Cada criança possui necessidades únicas que devem ser
    contempladas

## Funcionalidades Planejadas (FINAL_TARGET)

### F1. Apoio Visual e Sequencial para AVDs (ESSENCIAL)

**História de Usuário**: Como criança com TEA, eu quero que o aplicativo me
mostre o passo a passo de uma atividade de higiene de forma clara e visual,
para que eu possa entender e realizar a tarefa com mais autonomia.

**Componentes Essenciais:**
-   Mapas de rotina visuais (Visual Schedules)
-   Sequências ilustradas passo a passo para AVDs de higiene:
    * Escovação dos dentes
    * Lavagem das mãos
    * Uso do vaso sanitário
    * Banho
-   Ícones grandes e comandos por toque simples
-   Duas versões de personagens (menino e menina) para identificação
-   Priorização de acessibilidade para primeira infância (0-6 anos)

**Requisitos Funcionais Associados:**
-   RF 04: Cadastrar rotina
-   RF 05: Editar rotina
-   RF 06: Remover rotina
-   RF 07: Pesquisar rotina
-   RF 19: Cadastrar atividade
-   RF 20: Editar atividade
-   RF 21: Pesquisar atividade
-   RF 22: Remover atividade

### F2. Sistema de Reforço e Engajamento (ESSENCIAL)

**História de Usuário**: Como criança com TEA, eu quero receber reforço
positivo imediato após completar uma etapa da atividade, para que eu me sinta
motivada a continuar e repetir o comportamento correto.

**Componentes Essenciais:**
-   Feedbacks positivos com mensagens de elogio e motivação
-   Personagens com semblante alegre
-   Feedback curto (tempo de exibição otimizado)
-   Narração e sons de apoio (aplausos)
-   Cores atrativas e elementos claros
-   Interface com poucos botões para evitar confusão

**Requisitos Funcionais Associados:**
-   RF 24: Enviar feedback sobre atividade

### F3. Antecipação de Imprevistos e Rotina Flexível (INOVADOR)

**História de Usuário**: Como pai/cuidador, eu quero uma ferramenta que me
permita notificar mudanças e preparar visualmente a criança para imprevistos
ou alterações na rotina de higiene, a fim de minimizar a ansiedade e
desorganização.

**Componentes Inovadores:**
-   Notificação de mudanças na rotina (RF 09)
-   Suporte visual para antecipação (37.5% estratégia mais usada)
-   Organização de "Imprevistos" e viagens como dia normal de rotina
-   Uso de fotos pré-cadastradas para antecipação
-   Arquitetura de dois módulos:
    * Módulo Administrativo para Profissionais
    * Aplicativo Móvel para Pais/Pacientes
-   Garantia de carry-over entre ambiente clínico e domiciliar

**Requisitos Funcionais Associados:**
-   RF 09: Notificar mudanças na rotina (ESSENCIAL)
-   RF 08: Compartilhar rotina (IMPORTANTE)
-   RF 23: Notificar usuário de atividade (ESSENCIAL)

### F4. Interação por Fala e Escuta (INOVADOR)

**História de Usuário**: Como criança com TEA na fase de pré-alfabetização, eu
quero interagir com o aplicativo usando minha voz para confirmar que realizei
uma etapa da atividade, para que eu não precise usar botões complexos.

**Componentes Inovadores:**
-   Reconhecimento por voz para atividade (RF 25)
-   Narração e dublagens pré-gravadas para todo o texto
-   Instruções e narrativas com reforço de áudio
-   Interação vocal como alternativa ao toque
-   Suporte para crianças em fase de pré-alfabetização
-   Implementação de estratégia "Primeiro isso e Depois aquilo" via voz

**Requisitos Funcionais Associados:**
-   RF 25: Ativar reconhecimento por voz (IMPORTANTE)

### F5. Personalização e Hiperfoco (INOVADOR)

**História de Usuário**: Como pai/cuidador, eu quero usar os interesses
específicos (Hiperfoco) do meu filho no aplicativo, associando-os às
atividades de higiene, para aumentar o engajamento dele nas tarefas mais
resistentes.

**Componentes Inovadores:**
-   Módulo completo de gestão de Hiperfoco (RF 26 a RF 30)
-   Cadastro de hiperfoco com categoria, nome, descrição e intensidade
-   Associação de hiperfoco às atividades (RF 30)
-   Personalização do quadro de rotina (cores/fontes) (RF 10)
-   Inclusão de fotos e áudios personalizáveis
-   Alinhamento com interesses específicos da criança

**Requisitos Funcionais Associados:**
-   RF 26: Cadastrar hiperfoco (IMPORTANTE)
-   RF 27: Editar hiperfoco (IMPORTANTE)
-   RF 28: Pesquisar hiperfoco (IMPORTANTE)
-   RF 29: Remover hiperfoco (IMPORTANTE)
-   RF 30: Associar hiperfoco às atividades (IMPORTANTE)
-   RF 10: Personalizar quadro de rotinas (IMPORTANTE)

## Requisitos Funcionais Completos (31 RFs)

### Módulo de Autenticação e Acesso

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 01 | Efetuar login | Essencial |
| RF 02 | Recuperar senha | Essencial |
| RF 03 | Realizar logout | Essencial |

### Módulo de Gestão de Rotinas

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 04 | Cadastrar rotina | Essencial |
| RF 05 | Editar rotina | Essencial |
| RF 06 | Remover rotina | Essencial |
| RF 07 | Pesquisar rotina | Essencial |
| RF 08 | Compartilhar rotina | Importante |
| RF 09 | Notificar mudanças na rotina | Essencial |
| RF 10 | Personalizar quadro de rotinas | Importante |
| RF 31 | Inserir datas comemorativas | Desejável |

### Módulo de Gestão de Usuários

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 11 | Cadastrar pessoa com TEA | Essencial |
| RF 12 | Pesquisar pessoa com TEA | Essencial |
| RF 13 | Editar pessoa com TEA | Essencial |
| RF 14 | Remover pessoa com TEA | Essencial |
| RF 15 | Cadastrar profissional | Essencial |
| RF 16 | Pesquisar profissional | Essencial |
| RF 17 | Editar profissional | Essencial |
| RF 18 | Remover profissional | Essencial |

### Módulo de Gestão de Atividades

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 19 | Cadastrar atividade | Essencial |
| RF 20 | Editar atividade | Essencial |
| RF 21 | Pesquisar atividade | Essencial |
| RF 22 | Remover atividade | Essencial |
| RF 23 | Notificar usuário de atividade | Essencial |
| RF 24 | Enviar feedback sobre atividade | Importante |

### Módulo de Acessibilidade e Interação

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 25 | Ativar reconhecimento por voz | Importante |

### Módulo de Hiperfoco

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 26 | Cadastrar hiperfoco | Importante |
| RF 27 | Editar hiperfoco | Importante |
| RF 28 | Pesquisar hiperfoco | Importante |
| RF 29 | Remover hiperfoco | Importante |
| RF 30 | Associar hiperfoco às atividades | Importante |

## Escopo do Projeto

### Funcionalidades In-Scope (MVP - Versão Inicial)

Baseado na pesquisa de usuário que identificou higiene pessoal como a rotina
mais problemática, o escopo inicial focará em:

**Templates Pré-Estruturados:**
-   Rotina de Higiene Matinal (Escovar Dentes, Lavar Mãos)
-   Rotina de Uso do Banheiro
-   Rotina de Hora de Dormir (relacionada à higiene)

**Funcionalidades Essenciais do MVP:**
-   Sistema de autenticação básico (RF 01, RF 02, RF 03)
-   Cadastro e gestão de pessoas com TEA (RF 11, RF 12, RF 13, RF 14)
-   Cadastro e gestão de rotinas (RF 04, RF 05, RF 06, RF 07)
-   Cadastro e gestão de atividades (RF 19, RF 20, RF 21, RF 22)
-   Cronograma visual com sequências passo a passo
-   Sistema básico de reforço positivo (feedback visual e sonoro)
-   Notificações de atividades (RF 23)
-   Biblioteca de conteúdo com fotos e ícones pré-definidos
-   Capacidade de upload de fotos personalizadas
-   Interface com duas versões de personagens (menino/menina)

### Funcionalidades Out-of-Scope (Versões Futuras)

**Versão 1.1 - Funcionalidades Avançadas:**
-   Reconhecimento por voz (RF 25)
-   Áudios personalizados e narração
-   Módulo completo de Hiperfoco (RF 26-30)
-   Personalização avançada de quadro de rotinas (RF 10)

**Versão 1.2 - Colaboração e Gestão:**
-   Cadastro e gestão de profissionais (RF 15-18)
-   Compartilhamento de rotinas (RF 08)
-   Notificação de mudanças na rotina (RF 09)
-   Sistema de feedback sobre atividades (RF 24)

**Versão 1.3 - Recursos Adicionais:**
-   Datas comemorativas (RF 31)
-   Análise de progresso e relatórios
-   Integração com prontuários (fora do escopo inicial)
-   Gamificação avançada

**Explicitamente Fora do Escopo:**
-   Atividades Instrumentais Complexas da Vida Diária (AIVDs)
-   Conteúdo curricular acadêmico
-   Integração com Prontuários Eletrônicos de Saúde (EHRs)
-   Chat multiusuário em tempo real
-   Gerenciamento financeiro ou de medicação

## Arquitetura e Especificações Técnicas

### Stack Tecnológico

-   **Plataforma**: Android (nativo)
-   **Linguagem**: Kotlin
-   **Arquitetura**: MVVM (Model-View-ViewModel)
-   **UI Framework**: Jetpack Compose
-   **Build System**: Gradle com Kotlin DSL
-   **Injeção de Dependências**: Hilt/Dagger
-   **Banco de Dados Local**: Room Database
-   **Armazenamento de Mídia**: Armazenamento local no dispositivo
-   **Versionamento**: Semântico (MAJOR.MINOR.PATCH)

### Arquitetura de Camadas

```
┌─────────────────────────────────────────────────────────────┐
│                    Camada de Apresentação                  │
│         (UI - Jetpack Compose, ViewModels, Navigation)     │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────────┐
│                   Camada de Aplicação                      │
│              (Use Cases, Orquestração, DTOs)               │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────────┐
│                    Camada de Domínio                       │
│        (Entidades, Regras de Negócio, Interfaces)          │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────────┐
│                 Camada de Infraestrutura                   │
│      (Repositórios, Room DB, File Storage, Services)       │
└─────────────────────────────────────────────────────────────┘
```

### Requisitos Não-Funcionais

| Aspecto | Especificação |
|---------|---------------|
| **Compatibilidade** | Android 8.0 (API 26) ou superior |
| **Usabilidade** | Interface intuitiva com carga cognitiva mínima, pistas visuais claras, navegação direta |
| **Privacidade e Segurança** | Armazenamento local no dispositivo, sem transmissão de dados sensíveis para servidores remotos, conformidade com LGPD |
| **Performance** | Tempos de resposta < 3s para funções centrais, transições suaves, sem lag perceptível |
| **Acessibilidade** | Suporte a TalkBack, contraste adequado (WCAG AA), fontes ajustáveis, ícones grandes |
| **Offline-First** | Funcionalidade completa sem conexão com internet |
| **Armazenamento** | Dados de usuário, fotos e áudios armazenados localmente |

### Estrutura de Módulos

```
app/
├── data/                    # Camada de Dados
│   ├── local/              # Banco de dados local (Room)
│   ├── repository/         # Implementação de repositórios
│   └── model/              # Modelos de dados
├── domain/                  # Camada de Domínio
│   ├── entity/             # Entidades de negócio
│   ├── usecase/            # Casos de uso
│   └── repository/         # Interfaces de repositório
├── presentation/            # Camada de Apresentação
│   ├── ui/                 # Telas Compose
│   ├── viewmodel/          # ViewModels
│   ├── navigation/         # Navegação
│   └── theme/              # Tema e design system
└── di/                      # Injeção de Dependências (Hilt)
```

## Metodologia de Desenvolvimento

### Design Thinking Aplicado

**Fase 1 - Empatizar:**
-   Entrevistas com pais, terapeutas ocupacionais e psicólogos
-   Questionários qualitativos com cuidadores
-   Identificação de pontos problemáticos e recursos desejados

**Fase 2 - Definir:**
-   Análise de transcrições e dados de questionários
-   Desenvolvimento de personas detalhadas (criança, pai, terapeuta)
-   Formulação de declaração de problema concisa

**Fase 3 - Idear:**
-   Sessões colaborativas de brainstorming
-   Desenvolvimento de histórias de usuário
-   Priorização de funcionalidades (MoSCoW)

**Fase 4 - Prototipar:**
-   Protótipos de baixa fidelidade em papel
-   Protótipos digitais interativos (Figma/Balsamiq)
-   Validação de fluxos de navegação

**Fase 5 - Testar:**
-   Sessões de teste de usabilidade com crianças com TEA e pais
-   Coleta de métricas quantitativas e qualitativas
-   Iteração baseada em feedback antes do desenvolvimento em escala

### Abordagem de Desenvolvimento

-   **Metodologia Ágil**: Sprints de 2 semanas
-   **Modularidade First**: Entrega incremental de valor
-   **TDD (Test-Driven Development)**: Testes unitários antes da implementação
-   **Code Review**: Revisão obrigatória de código
-   **CI/CD**: Integração e deployment contínuos
-   **Documentação Contínua**: Sincronização em tempo real

## Funcionalidades Desenvolvidas e Validadas (DEVELOPED)

Atualmente não há funcionalidades desenvolvidas. O projeto está em fase de
especificação inicial.

## Funcionalidades em Desenvolvimento (UNDER_DEVELOPMENT)

Atualmente não há funcionalidades em desenvolvimento ativo. O projeto está em
fase de planejamento.

## Roadmap de Desenvolvimento

### Versão 0.1.0 - Setup Inicial (Sprint 1-2)
-   [ ] Configuração do projeto Android
-   [ ] Estrutura de módulos e camadas
-   [ ] Configuração de build e dependências
-   [ ] Sistema de tema e design tokens
-   [ ] Tela de splash e navegação básica

### Versão 0.2.0 - Autenticação e Cadastros Básicos (Sprint 3-4)
-   [ ] RF 01: Efetuar login
-   [ ] RF 02: Recuperar senha
-   [ ] RF 03: Realizar logout
-   [ ] RF 11-14: CRUD de Pessoa com TEA
-   [ ] Banco de dados local (Room)

### Versão 0.3.0 - Gestão de Rotinas e Atividades (Sprint 5-6)
-   [ ] RF 04-07: CRUD de Rotinas
-   [ ] RF 19-22: CRUD de Atividades
-   [ ] Biblioteca de ícones e imagens padrão
-   [ ] Sistema de upload de fotos personalizadas

### Versão 0.4.0 - Visualização e Execução de Rotinas (Sprint 7-8)
-   [ ] Cronograma visual com sequências passo a passo
-   [ ] Navegação entre etapas de atividades
-   [ ] Personagens (menino/menina)
-   [ ] Interface acessível para crianças

### Versão 0.5.0 - Sistema de Reforço Positivo (Sprint 9-10)
-   [ ] Feedback visual (estrelas, checkmarks)
-   [ ] Feedback sonoro (aplausos, elogios)
-   [ ] Animações de celebração
-   [ ] Sistema de conquistas simples

### Versão 1.0.0 - MVP Release (Sprint 11-12)
-   [ ] RF 23: Notificar usuário de atividade
-   [ ] Templates pré-estruturados completos
-   [ ] Testes de usabilidade finalizados
-   [ ] Documentação completa
-   [ ] Deploy em beta teste

### Versão 1.1.0 - Funcionalidades Avançadas
-   [ ] RF 25: Reconhecimento por voz
-   [ ] Áudios personalizados e narração
-   [ ] RF 26-30: Módulo de Hiperfoco
-   [ ] RF 10: Personalização avançada

### Versão 1.2.0 - Colaboração Profissional
-   [ ] RF 15-18: CRUD de Profissionais
-   [ ] RF 08: Compartilhar rotina
-   [ ] RF 09: Notificar mudanças
-   [ ] RF 24: Feedback sobre atividades

### Versão 1.3.0 - Recursos Complementares
-   [ ] RF 31: Datas comemorativas
-   [ ] Sistema de relatórios e progresso
-   [ ] Gamificação avançada
-   [ ] Integração com backup em nuvem (opcional)

## Referências e Aplicativos Similares

### Benchmarking de Mercado

1. **AuTime**: Agenda digital para rotina de crianças com autismo
2. **Rotina Divertida (Fun Routine)**: Gestão de rotinas com suporte visual
3. **Goally**: Visual Schedule Planner para Autismo
4. **PictogramAgenda**: Agenda com pictogramas
5. **Thruday**: Visual Daily Planning App para ADHD, Autismo e Epilepsia
6. **Tarefas de Casa para Crianças**: Gestão de tarefas com gamificação

### Diferenciais do Pequenos Passos

-   **Foco Específico**: AVDs de higiene para primeira infância (0-6 anos)
-   **Fundamentação Científica**: Baseado em TEACCH, PECS, ABA
-   **Inovações Tecnológicas**: Reconhecimento de voz, gestão de hiperfoco
-   **Antecipação de Mudanças**: Sistema proativo de notificações
-   **Privacidade Total**: Armazenamento local, sem servidores remotos
-   **Colaboração Família-Terapeuta**: Arquitetura dual com módulos separados

---

**Versão**: 0.1.0 (Especificação Inicial)  
**Data**: 07/10/2025  
**Status**: Em Planejamento - Aguardando Início de Desenvolvimento

## Reaproveitamento de Base Tecnológica (FonoVirtual_V2)

### Contexto do Reaproveitamento

O projeto **Pequenos Passos** reutiliza a base tecnológica desenvolvida no 
projeto anterior **FonoVirtual_V2**, aproveitando componentes validados e 
funcionalidades já implementadas que são compatíveis com os objetivos da nova 
aplicação. Esta abordagem permite acelerar o desenvolvimento e focar nos 
recursos específicos para crianças com TEA.

### Componentes Reutilizados da Base FonoVirtual_V2

#### 1. Interface Base e Navegação
**Status**: ✅ Validado e Mantido

- **SplashScreen**: Tela inicial com logo UNIVESP
  - Duração: 3 segundos ou toque do usuário
  - Transição suave para tela principal
  - Identidade visual acadêmica mantida
  
- **HomeScreen**: Estrutura base da tela principal
  - Layout com botões centralizados e responsivos
  - Sistema de versioning dinâmico (BuildConfig.VERSION_NAME)
  - Modo claro implementado (fundo branco, textos pretos)
  - **Adaptação Futura**: Botões serão renomeados e redirecionados para 
    funcionalidades específicas do Pequenos Passos

#### 2. Módulos de Reconhecimento de Voz (ASR) e Síntese de Fala (TTS)
**Status**: ✅ Validado e Diretamente Aplicável

**Módulo ASR (Automatic Speech Recognition)**:
- **VoskAsrModule.kt**: Implementação completa do reconhecimento de voz offline
- **Tecnologia**: Vosk PT-BR (vosk-model-small-pt-0.3)
- **Funcionalidades**:
  - Reconhecimento contínuo sem necessidade de botões
  - Processamento local (offline-first)
  - Tratamento de permissões de microfone
  - Feedback em tempo real
- **Aplicação no Pequenos Passos**: Essencial para RF 25 (Ativar 
  reconhecimento por voz) e interações vocais com crianças em 
  pré-alfabetização

**Módulo TTS (Text-to-Speech)**:
- **TextToSpeechModule.kt**: Síntese de fala nativa Android
- **Funcionalidades**:
  - Narração em português brasileiro (pt-BR)
  - Controle de velocidade e tom
  - Inicialização e gerenciamento de estado
  - Tratamento de erros de áudio
- **Aplicação no Pequenos Passos**: Fundamental para narração de instruções, 
  reforço positivo por áudio e suporte a crianças não-alfabetizadas

#### 3. Sistema de Debug e Testes
**Status**: ✅ Validado e Mantido para Desenvolvimento

**DebugScreen e Funcionalidades**:
- **DebugScreen.kt**: Tela de acesso a ferramentas de desenvolvimento
- **TtsTestScreen.kt**: Teste isolado de síntese de fala
- **AsrTestScreen.kt**: Teste isolado de reconhecimento de voz
- **Justificativa**: Mantido para facilitar desenvolvimento e validação dos 
  módulos de voz durante implementação das funcionalidades específicas para TEA

#### 4. Arquitetura Base
**Status**: ✅ Validado e Expandido

**Componentes Arquiteturais Reutilizados**:
- **MainActivity.kt**: Sistema de navegação com Jetpack Compose Navigation
- **Estrutura MVVM**: ViewModels, StateFlow, e padrões reativo
- **Sistema de Tema**: Theme.kt com Material Design 3
- **Build Configuration**: Gradle KTS, Java 17, dependências validadas

**Adaptações Necessárias**:
- Namespace atualizado: `com.example.pequenospassos`
- ApplicationId ajustado para nova identidade
- Configurações específicas para módulos de TEA

### Benefícios do Reaproveitamento

#### 1. Aceleração do Desenvolvimento
- **Time-to-Market Reduzido**: Módulos de voz prontos e validados
- **Foco na Especialização**: Concentrar esforços nas funcionalidades 
  específicas para TEA
- **Infraestrutura Testada**: Base arquitetural comprovadamente funcional

#### 2. Qualidade Garantida
- **Componentes Validados**: ASR e TTS com 95%+ de funcionalidade testada
- **Padrões Estabelecidos**: Arquitetura limpa já implementada
- **Performance Otimizada**: Build system e dependências já otimizadas

#### 3. Consistency Tecnológica
- **Stack Unificado**: Kotlin, Jetpack Compose, Material Design 3
- **Padrões de Código**: Nomenclatura e estrutura consistentes
- **Documentação Sincronizada**: Metodologia de documentação já estabelecida

### Roadmap de Integração

#### Fase 1: Adaptação da Base (Versão 0.1.0)
- [x] Renomeação completa do projeto (FonoVirtual_V2 → PequenosPassos)
- [x] Ajuste de namespace e configurações
- [x] Validação de build e funcionamento básico
- [ ] Adaptação visual para público infantil (cores, ícones, fontes)

#### Fase 2: Especialização TEA (Versão 0.2.0)
- [ ] Implementação de rotinas visuais usando base ASR/TTS
- [ ] Integração de módulos de voz com atividades de higiene
- [ ] Adaptação da HomeScreen para botões específicos do Pequenos Passos
- [ ] Implementação de feedback positivo com áudio

#### Fase 3: Funcionalidades Avançadas (Versão 0.3.0+)
- [ ] Módulo de Hiperfoco usando reconhecimento de voz
- [ ] Personalização de rotinas com narração TTS
- [ ] Sistema completo de reforço positivo audiovisual

### Considerações Técnicas

#### Compatibilidade e Migração
- **Versioning**: Iniciando do 0.1.0 para nova identidade do projeto
- **Dependencies**: Mantidas as dependências validadas (Vosk, Compose, etc.)
- **Target SDK**: Mantido Android 34 (API Level 34)
- **Min SDK**: Mantido Android 24 (API Level 24) para compatibilidade

#### Qualidade e Validação
- **Testes Existentes**: Aproveitamento de testes dos módulos de voz
- **Documentação**: Sincronização com nova especificação Pequenos Passos
- **Performance**: Baseline já estabelecida (BUILD SUCCESSFUL < 30s)

## Funcionalidades Planejadas (FINAL_TARGET)

### F1. Apoio Visual e Sequencial para AVDs (ESSENCIAL)

**História de Usuário**: Como criança com TEA, eu quero que o aplicativo me
mostre o passo a passo de uma atividade de higiene de forma clara e visual,
para que eu possa entender e realizar a tarefa com mais autonomia.

**Componentes Essenciais:**
-   Mapas de rotina visuais (Visual Schedules)
-   Sequências ilustradas passo a passo para AVDs de higiene:
    * Escovação dos dentes
    * Lavagem das mãos
    * Uso do vaso sanitário
    * Banho
-   Ícones grandes e comandos por toque simples
-   Duas versões de personagens (menino e menina) para identificação
-   Priorização de acessibilidade para primeira infância (0-6 anos)

**Requisitos Funcionais Associados:**
-   RF 04: Cadastrar rotina
-   RF 05: Editar rotina
-   RF 06: Remover rotina
-   RF 07: Pesquisar rotina
-   RF 19: Cadastrar atividade
-   RF 20: Editar atividade
-   RF 21: Pesquisar atividade
-   RF 22: Remover atividade

### F2. Sistema de Reforço e Engajamento (ESSENCIAL)

**História de Usuário**: Como criança com TEA, eu quero receber reforço
positivo imediato após completar uma etapa da atividade, para que eu me sinta
motivada a continuar e repetir o comportamento correto.

**Componentes Essenciais:**
-   Feedbacks positivos com mensagens de elogio e motivação
-   Personagens com semblante alegre
-   Feedback curto (tempo de exibição otimizado)
-   Narração e sons de apoio (aplausos)
-   Cores atrativas e elementos claros
-   Interface com poucos botões para evitar confusão

**Requisitos Funcionais Associados:**
-   RF 24: Enviar feedback sobre atividade

### F3. Antecipação de Imprevistos e Rotina Flexível (INOVADOR)

**História de Usuário**: Como pai/cuidador, eu quero uma ferramenta que me
permita notificar mudanças e preparar visualmente a criança para imprevistos
ou alterações na rotina de higiene, a fim de minimizar a ansiedade e
desorganização.

**Componentes Inovadores:**
-   Notificação de mudanças na rotina (RF 09)
-   Suporte visual para antecipação (37.5% estratégia mais usada)
-   Organização de "Imprevistos" e viagens como dia normal de rotina
-   Uso de fotos pré-cadastradas para antecipação
-   Arquitetura de dois módulos:
    * Módulo Administrativo para Profissionais
    * Aplicativo Móvel para Pais/Pacientes
-   Garantia de carry-over entre ambiente clínico e domiciliar

**Requisitos Funcionais Associados:**
-   RF 09: Notificar mudanças na rotina (ESSENCIAL)
-   RF 08: Compartilhar rotina (IMPORTANTE)
-   RF 23: Notificar usuário de atividade (ESSENCIAL)

### F4. Interação por Fala e Escuta (INOVADOR)

**História de Usuário**: Como criança com TEA na fase de pré-alfabetização, eu
quero interagir com o aplicativo usando minha voz para confirmar que realizei
uma etapa da atividade, para que eu não precise usar botões complexos.

**Componentes Inovadores:**
-   Reconhecimento por voz para atividade (RF 25)
-   Narração e dublagens pré-gravadas para todo o texto
-   Instruções e narrativas com reforço de áudio
-   Interação vocal como alternativa ao toque
-   Suporte para crianças em fase de pré-alfabetização
-   Implementação de estratégia "Primeiro isso e Depois aquilo" via voz

**Requisitos Funcionais Associados:**
-   RF 25: Ativar reconhecimento por voz (IMPORTANTE)

### F5. Personalização e Hiperfoco (INOVADOR)

**História de Usuário**: Como pai/cuidador, eu quero usar os interesses
específicos (Hiperfoco) do meu filho no aplicativo, associando-os às
atividades de higiene, para aumentar o engajamento dele nas tarefas mais
resistentes.

**Componentes Inovadores:**
-   Módulo completo de gestão de Hiperfoco (RF 26 a RF 30)
-   Cadastro de hiperfoco com categoria, nome, descrição e intensidade
-   Associação de hiperfoco às atividades (RF 30)
-   Personalização do quadro de rotina (cores/fontes) (RF 10)
-   Inclusão de fotos e áudios personalizáveis
-   Alinhamento com interesses específicos da criança

**Requisitos Funcionais Associados:**
-   RF 26: Cadastrar hiperfoco (IMPORTANTE)
-   RF 27: Editar hiperfoco (IMPORTANTE)
-   RF 28: Pesquisar hiperfoco (IMPORTANTE)
-   RF 29: Remover hiperfoco (IMPORTANTE)
-   RF 30: Associar hiperfoco às atividades (IMPORTANTE)
-   RF 10: Personalizar quadro de rotinas (IMPORTANTE)

## Requisitos Funcionais Completos (31 RFs)

### Módulo de Autenticação e Acesso

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 01 | Efetuar login | Essencial |
| RF 02 | Recuperar senha | Essencial |
| RF 03 | Realizar logout | Essencial |

### Módulo de Gestão de Rotinas

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 04 | Cadastrar rotina | Essencial |
| RF 05 | Editar rotina | Essencial |
| RF 06 | Remover rotina | Essencial |
| RF 07 | Pesquisar rotina | Essencial |
| RF 08 | Compartilhar rotina | Importante |
| RF 09 | Notificar mudanças na rotina | Essencial |
| RF 10 | Personalizar quadro de rotinas | Importante |
| RF 31 | Inserir datas comemorativas | Desejável |

### Módulo de Gestão de Usuários

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 11 | Cadastrar pessoa com TEA | Essencial |
| RF 12 | Pesquisar pessoa com TEA | Essencial |
| RF 13 | Editar pessoa com TEA | Essencial |
| RF 14 | Remover pessoa com TEA | Essencial |
| RF 15 | Cadastrar profissional | Essencial |
| RF 16 | Pesquisar profissional | Essencial |
| RF 17 | Editar profissional | Essencial |
| RF 18 | Remover profissional | Essencial |

### Módulo de Gestão de Atividades

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 19 | Cadastrar atividade | Essencial |
| RF 20 | Editar atividade | Essencial |
| RF 21 | Pesquisar atividade | Essencial |
| RF 22 | Remover atividade | Essencial |
| RF 23 | Notificar usuário de atividade | Essencial |
| RF 24 | Enviar feedback sobre atividade | Importante |

### Módulo de Acessibilidade e Interação

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 25 | Ativar reconhecimento por voz | Importante |

### Módulo de Hiperfoco

| ID | Funcionalidade | Prioridade |
|----|----------------|-----------|
| RF 26 | Cadastrar hiperfoco | Importante |
| RF 27 | Editar hiperfoco | Importante |
| RF 28 | Pesquisar hiperfoco | Importante |
| RF 29 | Remover hiperfoco | Importante |
| RF 30 | Associar hiperfoco às atividades | Importante |

## Escopo do Projeto

### Funcionalidades In-Scope (MVP - Versão Inicial)

Baseado na pesquisa de usuário que identificou higiene pessoal como a rotina
mais problemática, o escopo inicial focará em:

**Templates Pré-Estruturados:**
-   Rotina de Higiene Matinal (Escovar Dentes, Lavar Mãos)
-   Rotina de Uso do Banheiro
-   Rotina de Hora de Dormir (relacionada à higiene)

**Funcionalidades Essenciais do MVP:**
-   Sistema de autenticação básico (RF 01, RF 02, RF 03)
-   Cadastro e gestão de pessoas com TEA (RF 11, RF 12, RF 13, RF 14)
-   Cadastro e gestão de rotinas (RF 04, RF 05, RF 06, RF 07)
-   Cadastro e gestão de atividades (RF 19, RF 20, RF 21, RF 22)
-   Cronograma visual com sequências passo a passo
-   Sistema básico de reforço positivo (feedback visual e sonoro)
-   Notificações de atividades (RF 23)
-   Biblioteca de conteúdo com fotos e ícones pré-definidos
-   Capacidade de upload de fotos personalizadas
-   Interface com duas versões de personagens (menino/menina)

### Funcionalidades Out-of-Scope (Versões Futuras)

**Versão 1.1 - Funcionalidades Avançadas:**
-   Reconhecimento por voz (RF 25)
-   Áudios personalizados e narração
-   Módulo completo de Hiperfoco (RF 26-30)
-   Personalização avançada de quadro de rotinas (RF 10)

**Versão 1.2 - Colaboração e Gestão:**
-   Cadastro e gestão de profissionais (RF 15-18)
-   Compartilhamento de rotinas (RF 08)
-   Notificação de mudanças na rotina (RF 09)
-   Sistema de feedback sobre atividades (RF 24)

**Versão 1.3 - Recursos Adicionais:**
-   Datas comemorativas (RF 31)
-   Análise de progresso e relatórios
-   Integração com prontuários (fora do escopo inicial)
-   Gamificação avançada

**Explicitamente Fora do Escopo:**
-   Atividades Instrumentais Complexas da Vida Diária (AIVDs)
-   Conteúdo curricular acadêmico
-   Integração com Prontuários Eletrônicos de Saúde (EHRs)
-   Chat multiusuário em tempo real
-   Gerenciamento financeiro ou de medicação

## Arquitetura e Especificações Técnicas

### Stack Tecnológico

-   **Plataforma**: Android (nativo)
-   **Linguagem**: Kotlin
-   **Arquitetura**: MVVM (Model-View-ViewModel)
-   **UI Framework**: Jetpack Compose
-   **Build System**: Gradle com Kotlin DSL
-   **Injeção de Dependências**: Hilt/Dagger
-   **Banco de Dados Local**: Room Database
-   **Armazenamento de Mídia**: Armazenamento local no dispositivo
-   **Versionamento**: Semântico (MAJOR.MINOR.PATCH)

### Arquitetura de Camadas

```
┌─────────────────────────────────────────────────────────────┐
│                    Camada de Apresentação                  │
│         (UI - Jetpack Compose, ViewModels, Navigation)     │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────────┐
│                   Camada de Aplicação                      │
│              (Use Cases, Orquestração, DTOs)               │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────────┐
│                    Camada de Domínio                       │
│        (Entidades, Regras de Negócio, Interfaces)          │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────────────┐
│                 Camada de Infraestrutura                   │
│      (Repositórios, Room DB, File Storage, Services)       │
└─────────────────────────────────────────────────────────────┘
```

### Requisitos Não-Funcionais

| Aspecto | Especificação |
|---------|---------------|
| **Compatibilidade** | Android 8.0 (API 26) ou superior |
| **Usabilidade** | Interface intuitiva com carga cognitiva mínima, pistas visuais claras, navegação direta |
| **Privacidade e Segurança** | Armazenamento local no dispositivo, sem transmissão de dados sensíveis para servidores remotos, conformidade com LGPD |
| **Performance** | Tempos de resposta < 3s para funções centrais, transições suaves, sem lag perceptível |
| **Acessibilidade** | Suporte a TalkBack, contraste adequado (WCAG AA), fontes ajustáveis, ícones grandes |
| **Offline-First** | Funcionalidade completa sem conexão com internet |
| **Armazenamento** | Dados de usuário, fotos e áudios armazenados localmente |

### Estrutura de Módulos

```
app/
├── data/                    # Camada de Dados
│   ├── local/              # Banco de dados local (Room)
│   ├── repository/         # Implementação de repositórios
│   └── model/              # Modelos de dados
├── domain/                  # Camada de Domínio
│   ├── entity/             # Entidades de negócio
│   ├── usecase/            # Casos de uso
│   └── repository/         # Interfaces de repositório
├── presentation/            # Camada de Apresentação
│   ├── ui/                 # Telas Compose
│   ├── viewmodel/          # ViewModels
│   ├── navigation/         # Navegação
│   └── theme/              # Tema e design system
└── di/                      # Injeção de Dependências (Hilt)
```

## Metodologia de Desenvolvimento

### Design Thinking Aplicado

**Fase 1 - Empatizar:**
-   Entrevistas com pais, terapeutas ocupacionais e psicólogos
-   Questionários qualitativos com cuidadores
-   Identificação de pontos problemáticos e recursos desejados

**Fase 2 - Definir:**
-   Análise de transcrições e dados de questionários
-   Desenvolvimento de personas detalhadas (criança, pai, terapeuta)
-   Formulação de declaração de problema concisa

**Fase 3 - Idear:**
-   Sessões colaborativas de brainstorming
-   Desenvolvimento de histórias de usuário
-   Priorização de funcionalidades (MoSCoW)

**Fase 4 - Prototipar:**
-   Protótipos de baixa fidelidade em papel
-   Protótipos digitais interativos (Figma/Balsamiq)
-   Validação de fluxos de navegação

**Fase 5 - Testar:**
-   Sessões de teste de usabilidade com crianças com TEA e pais
-   Coleta de métricas quantitativas e qualitativas
-   Iteração baseada em feedback antes do desenvolvimento em escala

### Abordagem de Desenvolvimento

-   **Metodologia Ágil**: Sprints de 2 semanas
-   **Modularidade First**: Entrega incremental de valor
-   **TDD (Test-Driven Development)**: Testes unitários antes da implementação
-   **Code Review**: Revisão obrigatória de código
-   **CI/CD**: Integração e deployment contínuos
-   **Documentação Contínua**: Sincronização em tempo real

## Funcionalidades Desenvolvidas e Validadas (DEVELOPED)

Atualmente não há funcionalidades desenvolvidas. O projeto está em fase de
especificação inicial.

## Funcionalidades em Desenvolvimento (UNDER_DEVELOPMENT)

Atualmente não há funcionalidades em desenvolvimento ativo. O projeto está em
fase de planejamento.

## Roadmap de Desenvolvimento

### Versão 0.1.0 - Setup Inicial (Sprint 1-2)
-   [ ] Configuração do projeto Android
-   [ ] Estrutura de módulos e camadas
-   [ ] Configuração de build e dependências
-   [ ] Sistema de tema e design tokens
-   [ ] Tela de splash e navegação básica

### Versão 0.2.0 - Autenticação e Cadastros Básicos (Sprint 3-4)
-   [ ] RF 01: Efetuar login
-   [ ] RF 02: Recuperar senha
-   [ ] RF 03: Realizar logout
-   [ ] RF 11-14: CRUD de Pessoa com TEA
-   [ ] Banco de dados local (Room)

### Versão 0.3.0 - Gestão de Rotinas e Atividades (Sprint 5-6)
-   [ ] RF 04-07: CRUD de Rotinas
-   [ ] RF 19-22: CRUD de Atividades
-   [ ] Biblioteca de ícones e imagens padrão
-   [ ] Sistema de upload de fotos personalizadas

### Versão 0.4.0 - Visualização e Execução de Rotinas (Sprint 7-8)
-   [ ] Cronograma visual com sequências passo a passo
-   [ ] Navegação entre etapas de atividades
-   [ ] Personagens (menino/menina)
-   [ ] Interface acessível para crianças

### Versão 0.5.0 - Sistema de Reforço Positivo (Sprint 9-10)
-   [ ] Feedback visual (estrelas, checkmarks)
-   [ ] Feedback sonoro (aplausos, elogios)
-   [ ] Animações de celebração
-   [ ] Sistema de conquistas simples

### Versão 1.0.0 - MVP Release (Sprint 11-12)
-   [ ] RF 23: Notificar usuário de atividade
-   [ ] Templates pré-estruturados completos
-   [ ] Testes de usabilidade finalizados
-   [ ] Documentação completa
-   [ ] Deploy em beta teste

### Versão 1.1.0 - Funcionalidades Avançadas
-   [ ] RF 25: Reconhecimento por voz
-   [ ] Áudios personalizados e narração
-   [ ] RF 26-30: Módulo de Hiperfoco
-   [ ] RF 10: Personalização avançada

### Versão 1.2.0 - Colaboração Profissional
-   [ ] RF 15-18: CRUD de Profissionais
-   [ ] RF 08: Compartilhar rotina
-   [ ] RF 09: Notificar mudanças
-   [ ] RF 24: Feedback sobre atividades

### Versão 1.3.0 - Recursos Complementares
-   [ ] RF 31: Datas comemorativas
-   [ ] Sistema de relatórios e progresso
-   [ ] Gamificação avançada
-   [ ] Integração com backup em nuvem (opcional)

## Referências e Aplicativos Similares

### Benchmarking de Mercado

1. **AuTime**: Agenda digital para rotina de crianças com autismo
2. **Rotina Divertida (Fun Routine)**: Gestão de rotinas com suporte visual
3. **Goally**: Visual Schedule Planner para Autismo
4. **PictogramAgenda**: Agenda com pictogramas
5. **Thruday**: Visual Daily Planning App para ADHD, Autismo e Epilepsia
6. **Tarefas de Casa para Crianças**: Gestão de tarefas com gamificação

### Diferenciais do Pequenos Passos

-   **Foco Específico**: AVDs de higiene para primeira infância (0-6 anos)
-   **Fundamentação Científica**: Baseado em TEACCH, PECS, ABA
-   **Inovações Tecnológicas**: Reconhecimento de voz, gestão de hiperfoco
-   **Antecipação de Mudanças**: Sistema proativo de notificações
-   **Privacidade Total**: Armazenamento local, sem servidores remotos
-   **Colaboração Família-Terapeuta**: Arquitetura dual com módulos separados

---

**Versão**: 0.1.0 (Especificação Inicial)  
**Data**: 07/10/2025  
**Status**: Em Planejamento - Aguardando Início de Desenvolvimento
