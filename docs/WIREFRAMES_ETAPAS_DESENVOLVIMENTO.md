<!--
Arquivo: docs/WIREFRAMES_ETAPAS_DESENVOLVIMENTO.md
Tipo: Guia visual das etapas de desenvolvimento
Objetivo: Fornecer wireframes/diagramas detalhados das 7 etapas para uso em apresentação de TCC
Criado: 2025-11-03
Autor: Claude Sonnet 4.5 (GitHub Copilot)

INSTRUÇÕES PARA USO:
Este documento contém wireframes ASCII art das 7 etapas de desenvolvimento.
Use-os como base para criar versões visuais profissionais no PowerPoint/Google Slides.

Correlações: MATERIAL_APRESENTACAO_TCC.md, ATUALIZACAO_METODOLOGIA_DESIGN_THINKING.md
-->

# WIREFRAMES DAS 7 ETAPAS DE DESENVOLVIMENTO
## Aplicativo Pequenos Passos - Design Thinking + Ágil

**Versão:** 1.1  
**Data:** 03/11/2025  
**Última Atualização:** 03/11/2025 (Adicionada Estrutura Geral do App)  
**Autor:** Claude Sonnet 4.5 (GitHub Copilot)  
**Uso:** Material de Apresentação de TCC  
**Total de Wireframes:** 8 (Estrutura Geral + 7 Etapas)

---

## 📋 ÍNDICE

1. [Estrutura Geral do Aplicativo](#estrutura-geral-do-aplicativo) ⭐ **NOVO**
2. [Etapa 1: Empatia e Levantamento de Requisitos](#etapa-1-empatia-e-levantamento-de-requisitos)
3. [Etapa 2: Definição e Ideação](#etapa-2-definição-e-ideação)
4. [Etapa 3: Prototipagem](#etapa-3-prototipagem)
5. [Etapa 4: Arquitetura e Modelagem](#etapa-4-arquitetura-e-modelagem)
6. [Etapa 5: Implementação Incremental](#etapa-5-implementação-incremental-14-mvps)
7. [Etapa 6: Teste e Validação Contínua](#etapa-6-teste-e-validação-contínua)
8. [Etapa 7: Documentação](#etapa-7-documentação)
9. [Como Converter para PowerPoint](#como-converter-para-powerpoint)

---

## ESTRUTURA GERAL DO APLICATIVO

### Wireframe: Visão Macro do Sistema

```
┌─────────────────────────────────────────────────────────────────────────┐
│                🏠 PEQUENOS PASSOS - ESTRUTURA GERAL                     │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  👥 PÚBLICO-ALVO                    🎯 OBJETIVO                         │
│  ┌─────────────────────┐           ┌──────────────────────┐            │
│  │ Crianças 0-6 anos   │           │ Auxiliar na execução │            │
│  │ TEA, TDAH           │  ───────► │ de tarefas diárias   │            │
│  │ Dificuldades de     │           │ de forma estruturada │            │
│  │ Aprendizagem        │           │ e motivadora         │            │
│  └─────────────────────┘           └──────────────────────┘            │
│                                                                         │
│  📱 CARACTERÍSTICAS PRINCIPAIS                                          │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ ✓ 100% Offline          ✓ TTS (Voz)        ✓ ASR (Reconhecimento)│ │
│  │ ✓ Interface Visual      ✓ Timer em Barra   ✓ Sistema de Estrelas │ │
│  │ ✓ 27 Categorias         ✓ Passos Ilustrados ✓ Histórico/Stats   │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Wireframe: Fluxo Principal de Navegação

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    📱 FLUXO PRINCIPAL DO APP                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│                         ┌──────────────┐                               │
│                         │   SPLASH     │                               │
│                         │  v2.1.0      │                               │
│                         └──────┬───────┘                               │
│                                │                                        │
│                                ▼                                        │
│                    ┌───────────────────────┐                           │
│              ┌────►│  ONBOARDING/CADASTRO  │                           │
│              │     │  (Primeira Execução)  │                           │
│              │     └───────────┬───────────┘                           │
│              │                 │                                        │
│              │                 ▼                                        │
│       [Não]  │     ┌───────────────────────┐                           │
│              └─────┤   Perfil Cadastrado?  │                           │
│                    └───────────┬───────────┘                           │
│                            [Sim]│                                       │
│                                 ▼                                        │
│                    ┌────────────────────────┐                          │
│                    │    🏠 HOME SCREEN      │                          │
│                    │ ┌────────────────────┐ │                          │
│                    │ │ Olá, [Nome]!       │ │                          │
│                    │ │ Data/Hora          │ │                          │
│                    │ │ X estrelas hoje ⭐ │ │                          │
│                    │ │ Y atividades       │ │                          │
│                    │ └────────────────────┘ │                          │
│                    │                        │                          │
│                    │ [📋 Atividades    ]    │                          │
│                    │ [⚙️  Edição       ]    │                          │
│                    │ [👤 Cadastro      ]    │                          │
│                    │ [📊 Histórico     ]    │                          │
│                    └────┬───┬───┬───┬──────┘                          │
│                         │   │   │   │                                  │
│         ┌───────────────┘   │   │   └──────────────┐                  │
│         │                   │   │                  │                  │
│         ▼                   ▼   ▼                  ▼                  │
│  ┌─────────────┐   ┌─────────────┐        ┌──────────────┐           │
│  │ ATIVIDADES  │   │   EDIÇÃO    │        │  HISTÓRICO   │           │
│  │ (Executar)  │   │ (Gerenciar) │        │ & FERRAMENTAS│           │
│  └──────┬──────┘   └─────────────┘        └──────────────┘           │
│         │                                                              │
│         ▼                                                              │
│  ┌─────────────┐                                                      │
│  │  EXECUÇÃO   │                                                      │
│  │  PASSO A    │                                                      │
│  │  PASSO      │                                                      │
│  └──────┬──────┘                                                      │
│         │                                                              │
│         ▼                                                              │
│  ┌─────────────┐                                                      │
│  │  CONCLUSÃO  │                                                      │
│  │  Parabéns!  │                                                      │
│  │  X estrelas │                                                      │
│  └──────┬──────┘                                                      │
│         │                                                              │
│         └──────────► [Voltar ao Home]                                 │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Wireframe: Organização por Módulos

```
┌─────────────────────────────────────────────────────────────────────────┐
│              🗂️ ORGANIZAÇÃO DO APP POR MÓDULOS                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  📦 MÓDULO 1: GERENCIAMENTO DE PERFIL                                   │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ • Cadastro de criança (nome, data nascimento, observações)        │ │
│  │ • Edição de perfil                                                │ │
│  │ • Armazenamento local (Room Database)                             │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  📦 MÓDULO 2: GERENCIAMENTO DE TAREFAS (Área Protegida)                │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ • Criar tarefas personalizadas                                    │ │
│  │ • Editar tarefas existentes                                       │ │
│  │ • Deletar tarefas (com confirmação)                               │ │
│  │ • Categorizar (27 categorias em 5 grupos)                         │ │
│  │ • Adicionar passos (steps) sequenciais                            │ │
│  │ • Configurar imagens e timers por passo                           │ │
│  │ • Definir número de estrelas (1-5)                                │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  📦 MÓDULO 3: EXECUÇÃO DE TAREFAS (Área da Criança)                    │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ • Listar tarefas disponíveis                                      │ │
│  │ • Executar tarefa passo a passo                                   │ │
│  │ • Timer em barra visual por passo                                 │ │
│  │ • Exibir imagem ilustrativa de cada passo                         │ │
│  │ • TTS: Leitura automática ([Nome], [passo])                       │ │
│  │ • Pop-up de tempo extra (opcional)                                │ │
│  │ • ASR: Resposta por voz (opcional)                                │ │
│  │ • Barra de progresso                                              │ │
│  │ • Tela de conclusão com feedback positivo                         │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  📦 MÓDULO 4: GAMIFICAÇÃO E RECOMPENSAS                                │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ • Sistema de estrelas (1-5 por tarefa)                            │ │
│  │ • Contador de estrelas do dia                                     │ │
│  │ • Mensagens motivacionais variadas                                │ │
│  │ • Feedback visual e sonoro                                        │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  📦 MÓDULO 5: HISTÓRICO E ESTATÍSTICAS                                 │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ • Registro de tarefas completadas                                 │ │
│  │ • Estatísticas semanais                                           │ │
│  │ • Média de estrelas diárias                                       │ │
│  │ • Média de tarefas completadas                                    │ │
│  │ • Top 3 atividades mais executadas (7 dias)                       │ │
│  │ • Top 3 atividades menos executadas (7 dias)                      │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  📦 MÓDULO 6: FERRAMENTAS E CONFIGURAÇÕES                              │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ • Toggle: Pop-up de tempo extra (on/off)                          │ │
│  │ • Toggle: Resposta por voz (on/off)                               │ │
│  │ • Botão: Zerar dia (remove conclusões de hoje)                    │ │
│  │ • Teste de TTS                                                    │ │
│  │ • Teste de ASR                                                    │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Wireframe: Categorias e Organização de Conteúdo

```
┌─────────────────────────────────────────────────────────────────────────┐
│            🗂️ SISTEMA DE CATEGORIAS (27 Categorias)                     │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  🏠 CASA (7 categorias)                                                 │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ 🧹 Limpeza  │ 📦 Organização │ 🛏️ Quarto │ 🧺 Roupa │ 🗑️ Lixo    │ │
│  │ 🪴 Plantas  │ 🐕 Pets                                              │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🧘 AUTOCUIDADO (8 categorias)                                          │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ 🪥 Higiene Bucal │ 🚿 Banho │ 👗 Vestir │ 💤 Sono │ 💊 Medicação │ │
│  │ 🧘 Relaxamento   │ 🏃 Exercício │ 🩺 Saúde                        │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🎓 ESCOLA (4 categorias)                                               │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ 📚 Lição de Casa │ 📖 Leitura │ ✍️ Estudos │ 🎨 Projetos          │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🎨 LAZER (4 categorias)                                                │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ 🎮 Jogos │ 🎨 Arte │ 🎵 Música │ 📺 Entretenimento                │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🍽️ ALIMENTAÇÃO (4 categorias)                                          │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ 🍳 Café da Manhã │ 🍱 Almoço │ 🍪 Lanche │ 🍝 Jantar              │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  💡 BENEFÍCIO DA CATEGORIZAÇÃO:                                         │
│  • Organização visual clara                                            │
│  • Facilita encontrar tarefas                                          │
│  • Permite filtros futuros                                             │
│  • Contexto imediato para a criança                                    │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Wireframe: Tecnologias e Stack Técnico

```
┌─────────────────────────────────────────────────────────────────────────┐
│                💻 STACK TECNOLÓGICO COMPLETO                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  🔧 LINGUAGEM E FRAMEWORK                                               │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ Kotlin 2.0.20          │ 100% do código                            │ │
│  │ Jetpack Compose        │ UI declarativa e reativa                  │ │
│  │ Material Design 3      │ Design System                             │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🏗️ ARQUITETURA                                                         │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ Clean Architecture     │ Separação em 3 camadas                    │ │
│  │ MVVM Pattern          │ ViewModel + StateFlow                      │ │
│  │ Repository Pattern    │ Abstração de dados                         │ │
│  │ Use Cases             │ Lógica de negócio isolada                  │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  💾 PERSISTÊNCIA DE DADOS                                               │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ Room Database         │ SQLite + ORM                               │ │
│  │ 7 Tabelas             │ Relacionamentos bem definidos              │ │
│  │ TypeConverters        │ LocalDate, LocalDateTime                   │ │
│  │ Migrations            │ 7 migrations documentadas                  │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🔄 PROGRAMAÇÃO ASSÍNCRONA                                              │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ Coroutines            │ Suspend functions                          │ │
│  │ Flow                  │ Streams reativos                           │ │
│  │ StateFlow             │ Estado reativo na UI                       │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🎤 ACESSIBILIDADE                                                      │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ TTS Android Nativo    │ Síntese de voz em português                │ │
│  │ Vosk (ASR)            │ Reconhecimento offline                     │ │
│  │ Modelo PT-BR          │ vosk-model-small-pt-0.3                    │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🔌 INJEÇÃO DE DEPENDÊNCIAS                                             │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ Hilt / Dagger         │ DI automática                              │ │
│  │ @HiltAndroidApp       │ Application class                          │ │
│  │ @AndroidEntryPoint    │ Activities, Fragments                      │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  🧪 TESTES                                                              │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ JUnit                 │ Testes unitários                           │ │
│  │ Mockito               │ Mocking de dependências                    │ │
│  │ Truth                 │ Assertions fluentes                        │ │
│  │ Room Testing          │ Testes de banco de dados                   │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
│  📦 BUILD E VERSIONAMENTO                                               │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │ Gradle KTS            │ Build system                               │ │
│  │ Git + GitHub          │ Controle de versão                         │ │
│  │ Scripts .bat          │ Automação de build/install                 │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:

**Para Slide de Estrutura Geral:**
- **Cores:** 
  - Azul #2196F3 (características técnicas)
  - Verde #4CAF50 (módulos funcionais)
  - Laranja #FF9800 (categorias)
  - Roxo #9C27B0 (tecnologias)
- **Layout:** Dividir em quadrantes ou colunas
- **Ícones:** Usar emojis ou ícones profissionais do Flaticon
- **Animação:** Aparecer módulo por módulo

**Para Fluxo de Navegação:**
- **Cores:** Gradiente de azul (início) para verde (conclusão)
- **Layout:** Diagrama de fluxo vertical ou horizontal
- **Conectores:** Setas grossas e claras
- **Destaque:** Home Screen em tamanho maior

**Para Categorias:**
- **Cores:** Uma cor por grupo temático
- **Layout:** Grid 5x2 ou tabela
- **Ícones:** Emojis grandes e coloridos
- **Agrupamento:** Separar visualmente os 5 grupos

**Para Stack Técnico:**
- **Cores:** Cinza escuro/azul tech
- **Layout:** Blocos empilhados ou grid
- **Logos:** Logos oficiais de Kotlin, Jetpack, Room, etc
- **Badges:** Versões em badges estilo GitHub

### Pontos a Destacar na Apresentação:

**Estrutura Geral:**
- "App modular dividido em 6 módulos principais"
- "100% offline - privacidade e disponibilidade garantidas"
- "27 categorias organizadas em 5 grupos temáticos"

**Fluxo de Navegação:**
- "Fluxo linear e intuitivo adequado para crianças"
- "Splash → Cadastro → Home → Execução → Feedback"
- "Áreas separadas: criança (executar) e adulto (gerenciar)"

**Categorias:**
- "Organização por contexto facilita compreensão"
- "Emojis universais como identificadores visuais"
- "Cobertura completa da rotina diária"

**Stack Técnico:**
- "Tecnologias modernas e suportadas pelo Google"
- "Arquitetura profissional (Clean Architecture)"
- "TTS e ASR para máxima acessibilidade"

---

## ETAPA 1: EMPATIA E LEVANTAMENTO DE REQUISITOS

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│          🎯 FASE 1: EMPATIA E PESQUISA                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  📚 PESQUISA                  👥 PÚBLICO-ALVO              │
│  ┌──────────────┐            ┌──────────────┐             │
│  │ TEA/TDAH     │            │ Crianças     │             │
│  │ Características│  ──────► │ 0-6 anos     │             │
│  │ Necessidades │            │ Necessidades │             │
│  │ Especiais    │            │ Especiais    │             │
│  └──────────────┘            └──────────────┘             │
│         │                           │                      │
│         └───────────┬───────────────┘                      │
│                     ▼                                      │
│         ┌───────────────────────┐                         │
│         │  REQUISITOS ESSENCIAIS │                         │
│         ├───────────────────────┤                         │
│         │ ✓ Rotina estruturada  │                         │
│         │ ✓ Feedback visual     │                         │
│         │ ✓ Feedback sonoro     │                         │
│         │ ✓ Passos concretos    │                         │
│         │ ✓ Reforço positivo    │                         │
│         │ ✓ Interface simples   │                         │
│         └───────────────────────┘                         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Azul claro (pesquisa), Verde (público-alvo), Amarelo (requisitos)
- **Ícones:** Livro, lupa, grupo de pessoas, checklist
- **Setas:** Use setas animadas para mostrar fluxo
- **Fonte:** Arial ou Calibri, mínimo 14pt

### Pontos a Destacar na Apresentação:
- "Esta fase foi fundamental - não assumimos, PESQUISAMOS"
- "Compreendemos profundamente TEA e TDAH"
- "Requisitos vieram das NECESSIDADES REAIS, não de achismos"

---

## ETAPA 2: DEFINIÇÃO E IDEAÇÃO

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│        💡 FASE 2: DEFINIÇÃO DE PROBLEMAS → SOLUÇÕES         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ❌ PROBLEMAS              ✅ SOLUÇÕES CRIATIVAS            │
│  ┌─────────────────┐      ┌─────────────────┐             │
│  │ Dificuldade com │      │ 🔊 TTS          │             │
│  │ leitura         │ ───► │ Leitura em voz  │             │
│  └─────────────────┘      └─────────────────┘             │
│                                                             │
│  ┌─────────────────┐      ┌─────────────────┐             │
│  │ Sequências      │      │ 🖼️ Imagens       │             │
│  │ complexas       │ ───► │ ilustrativas    │             │
│  └─────────────────┘      └─────────────────┘             │
│                                                             │
│  ┌─────────────────┐      ┌─────────────────┐             │
│  │ Noção temporal  │      │ ⏱️ Timer visual  │             │
│  └─────────────────┘ ───► │ em barra        │             │
│                            └─────────────────┘             │
│  ┌─────────────────┐      ┌─────────────────┐             │
│  │ Falta de        │      │ ⭐ Sistema de    │             │
│  │ motivação       │ ───► │ estrelas        │             │
│  └─────────────────┘      └─────────────────┘             │
│                                                             │
│  ┌─────────────────┐      ┌─────────────────┐             │
│  │ Interação       │      │ 🎤 ASR (Vosk)   │             │
│  │ complexa        │ ───► │ Reconhecimento  │             │
│  └─────────────────┘      │ de voz          │             │
│                            └─────────────────┘             │
│                                                             │
│         💻 STACK TECNOLÓGICO DEFINIDO                       │
│    Kotlin | Compose | Room | TTS | ASR (Vosk)             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Vermelho (problemas), Verde (soluções)
- **Layout:** Duas colunas (problema ← → solução)
- **Animação:** Aparecer linha por linha
- **Ícones:** X vermelho, Check verde, logos de tecnologias

### Pontos a Destacar na Apresentação:
- "Para cada PROBLEMA identificado, criamos SOLUÇÃO específica"
- "Brainstorming resultou em stack tecnológico robusto"
- "TTS e ASR escolhidos por acessibilidade"

---

## ETAPA 3: PROTOTIPAGEM

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│           🎨 FASE 3: PROTOTIPAGEM E VALIDAÇÃO               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  📝 BAIXA FIDELIDADE              🖥️ ALTA FIDELIDADE        │
│  (Papel/Esboço)                   (Digital/Interativo)     │
│                                                             │
│  ┌──────────────┐                ┌──────────────────────┐  │
│  │ ┌──────────┐ │                │ ╔════════════════╗  │  │
│  │ │  TÍTULO  │ │                │ ║   Olá, Maria!  ║  │  │
│  │ ├──────────┤ │                │ ╠════════════════╣  │  │
│  │ │  [ 📋 ]  │ │   EVOLUÇÃO     │ ║  📅 03/11/2025 ║  │  │
│  │ │  [ ⚙️ ]  │ │   ─────────►   │ ║  🕐 14:30      ║  │  │
│  │ │  [ 📊 ]  │ │                │ ╠════════════════╣  │  │
│  │ └──────────┘ │                │ ║ Você tem 5     ║  │  │
│  └──────────────┘                │ ║ atividades ⭐  ║  │  │
│                                   │ ╠════════════════╣  │  │
│  ┌──────────────┐                │ ║ [Atividades]   ║  │  │
│  │ FLUXO TESTE  │                │ ║ [Edição]       ║  │  │
│  │ Home → Lista │                │ ║ [Cadastro]     ║  │  │
│  │ → Execução   │                │ ║ [Histórico]    ║  │  │
│  │ → Conclusão  │                │ ╚════════════════╝  │  │
│  └──────────────┘                └──────────────────────┘  │
│         │                                   │               │
│         └─────────┬───────────────────────┘               │
│                   ▼                                        │
│         ✅ VALIDAÇÃO DE UX                                 │
│         • Navegação intuitiva                              │
│         • Botões grandes o suficiente                      │
│         • Contraste adequado                               │
│         • Fluxo sem confusão                               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Cinza (baixa fidelidade), Azul/Laranja (alta fidelidade)
- **Layout:** Antes e Depois lado a lado
- **Fotos:** Se possível, foto real de wireframe em papel
- **Screenshots:** Mockup digital real (Figma/Sketch)

### Pontos a Destacar na Apresentação:
- "Prototipamos ANTES de codificar - economizou semanas"
- "Validamos UX com wireframes, identificando problemas antecipadamente"
- "Evolução de papel para digital mostra processo iterativo"

---

## ETAPA 4: ARQUITETURA E MODELAGEM

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│        🏗️ FASE 4: ARQUITETURA E MODELAGEM DE DADOS         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  🏛️ CLEAN ARCHITECTURE (3 CAMADAS)                         │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              PRESENTATION LAYER                     │   │
│  │  ┌────────────┐  ┌────────────┐  ┌─────────────┐  │   │
│  │  │  Screens   │  │ ViewModels │  │ Components  │  │   │
│  │  │ (Compose)  │  │   (State)  │  │  (UI/UX)    │  │   │
│  │  └────────────┘  └────────────┘  └─────────────┘  │   │
│  └─────────────────────┬───────────────────────────────┘   │
│                        │ ▼                                 │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                DOMAIN LAYER                         │   │
│  │  ┌────────────┐  ┌────────────┐  ┌─────────────┐  │   │
│  │  │   Models   │  │  Use Cases │  │ Repository  │  │   │
│  │  │ (Entities) │  │  (Logic)   │  │ Interfaces  │  │   │
│  │  └────────────┘  └────────────┘  └─────────────┘  │   │
│  └─────────────────────┬───────────────────────────────┘   │
│                        │ ▼                                 │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                  DATA LAYER                         │   │
│  │  ┌────────────┐  ┌────────────┐  ┌─────────────┐  │   │
│  │  │    Room    │  │    DAOs    │  │ Repository  │  │   │
│  │  │  Database  │  │  (Queries) │  │    Impl     │  │   │
│  │  └────────────┘  └────────────┘  └─────────────┘  │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  💾 BANCO DE DADOS (7 TABELAS)                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ AppSettings ────┐                                   │   │
│  │ ChildProfile ───┼──┐                                │   │
│  │ Task ───────────┤  │                                │   │
│  │ Step ───────────┘  │                                │   │
│  │ TaskCompletion ────┤                                │   │
│  │ TaskExecutionCount─┤                                │   │
│  │ CategoryEntity ────┘                                │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Azul (Presentation), Verde (Domain), Laranja (Data)
- **Layout:** Camadas empilhadas com setas de dependência
- **Diagrama:** Usar ferramenta draw.io ou Lucidchart
- **Tabelas:** Diagrama ER (Entity-Relationship) para banco

### Pontos a Destacar na Apresentação:
- "Clean Architecture garante SEPARAÇÃO DE RESPONSABILIDADES"
- "Cada camada independente = fácil de testar e manter"
- "7 tabelas relacionadas garantem integridade dos dados"

---

## ETAPA 5: IMPLEMENTAÇÃO INCREMENTAL (14 MVPs)

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│      💻 FASE 5: IMPLEMENTAÇÃO INCREMENTAL (14 MVPs)         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  📦 TIMELINE DE MVPs                                        │
│                                                             │
│  MVP-01 ▓▓▓  Estrutura Base                                │
│  MVP-02 ▓▓▓  Entidades                                      │
│  MVP-03 ▓▓▓  Database (Room)                               │
│  MVP-04 ▓▓▓  Repositórios                                   │
│  MVP-05 ▓▓▓  Use Cases                                      │
│  MVP-06 ▓▓▓  Theme e Design System                         │
│         └──┬──┘ FUNDAÇÃO (v1.0.x - v1.5.x)                 │
│            │                                                │
│  MVP-07 ▓▓▓▓▓▓  Interface Completa (Compose)               │
│         └──┬──┘ INTERFACE (v1.9.x)                         │
│            │                                                │
│  MVP-08 ▓▓▓  TTS (Text-to-Speech)                          │
│         └──┬──┘ ACESSIBILIDADE (v1.10.x)                   │
│            │                                                │
│  MVP-09 ▓▓▓  Sistema de Controle Diário                    │
│         └──┬──┘ CONTROLE (v1.11.x)                         │
│            │                                                │
│  MVP-10 ▓▓▓  Histórico & Ferramentas                       │
│         └──┬──┘ ESTATÍSTICAS (v2.0.x)                      │
│            │                                                │
│  MVP-11 ▓▓  Configurações Avançadas                        │
│  MVP-12 ▓▓  Backup/Sincronização (planejado)               │
│  MVP-13 ▓▓  Múltiplos Perfis (planejado)                   │
│  MVP-14 ▓▓▓  ASR em Pop-ups                                │
│         └──┬──┘ FEATURES AVANÇADAS (v2.1.0+)               │
│            │                                                │
│            ▼                                                │
│   ✅ VERSÃO ATUAL: 2.1.0                                    │
│   ✅ 100% FUNCIONAL                                         │
│                                                             │
│  🔄 CICLO DE CADA MVP:                                      │
│  ┌──────────────────────────────────────────┐              │
│  │ Planejar → Implementar → Testar →       │              │
│  │ Documentar → Validar → Commit → Próximo │              │
│  └──────────────────────────────────────────┘              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Gradiente de cinza (completos) para azul (atual)
- **Layout:** Timeline vertical ou horizontal
- **Gráfico:** Gráfico de Gantt mostrando duração de cada MVP
- **Ícones:** Checkbox para MVPs completos, engrenagem para em andamento

### Pontos a Destacar na Apresentação:
- "14 MVPs = 14 entregas validadas"
- "Desenvolvimento incremental reduziu riscos"
- "Cada MVP testado antes de avançar"
- "Versão 2.1.0 atual = 100% funcional"

---

## ETAPA 6: TESTE E VALIDAÇÃO CONTÍNUA

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│         🧪 FASE 6: TESTE E VALIDAÇÃO CONTÍNUA               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  🏔️ PIRÂMIDE DE TESTES                                      │
│                                                             │
│                    ┌─────┐                                 │
│                    │ E2E │  10%                            │
│                    │ UI  │  Testes em                      │
│                    └─────┘  Dispositivo Real               │
│                  ┌─────────┐                               │
│                  │  INTEGR  │  20%                         │
│                  │  Database│  Room, DAOs                  │
│                  │  Queries │  Migrations                  │
│                  └─────────┘                               │
│              ┌───────────────┐                             │
│              │   UNIT TESTS   │  70%                       │
│              │   Use Cases    │  ViewModels                │
│              │   Repositories │  Business Logic            │
│              └───────────────┘                             │
│                                                             │
│  ✅ VALIDAÇÕES REALIZADAS:                                  │
│  ┌─────────────────────────────────────────────┐           │
│  │ [✓] Testes Unitários                        │           │
│  │     • SaveTaskUseCase                       │           │
│  │     • DeleteTaskUseCase                     │           │
│  │     • TaskRepositoryImpl                    │           │
│  │                                              │           │
│  │ [✓] Testes de Integração                    │           │
│  │     • Room Database                         │           │
│  │     • 7 Migrations validadas                │           │
│  │     • DAOs (insert, update, delete, query)  │           │
│  │                                              │           │
│  │ [✓] Testes em Dispositivo Real              │           │
│  │     • Performance (< 100ms)                 │           │
│  │     • Usabilidade (botões, contraste)       │           │
│  │     • TTS (qualidade de voz)                │           │
│  │     • ASR (precisão de reconhecimento)      │           │
│  └─────────────────────────────────────────────┘           │
│                                                             │
│  🐛 BUGS IDENTIFICADOS E CORRIGIDOS: 15+                    │
│  • Migration crashes → SOLUCIONADO                          │
│  • TypeConverter errors → SOLUCIONADO                       │
│  • Navigation issues → SOLUCIONADO                          │
│  • Performance lags → SOLUCIONADO                           │
│                                                             │
│  🔄 ITERAÇÃO CONSTANTE baseada em feedback                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Verde (testes passando), Vermelho (bugs), Azul (pirâmide)
- **Layout:** Pirâmide invertida com percentuais
- **Gráfico:** Pizza chart mostrando distribuição 70-20-10
- **Ícones:** Check verde, Bug vermelho com X

### Pontos a Destacar na Apresentação:
- "Pirâmide de testes: 70% unitários, 20% integração, 10% E2E"
- "Todos os bugs documentados e rastreados"
- "Validação em dispositivo real foi crítica"

---

## ETAPA 7: DOCUMENTAÇÃO

### Wireframe ASCII

```
┌─────────────────────────────────────────────────────────────┐
│            📚 FASE 7: DOCUMENTAÇÃO COMPLETA                 │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  📋 ESTRUTURA DE DOCUMENTAÇÃO                               │
│                                                             │
│  docs/                                                      │
│  ├── 📘 GUIDELINES.md                                       │
│  │   └── Padrões de desenvolvimento                        │
│  │                                                          │
│  ├── 📘 SPECIFICATION_FOR_APP.md                            │
│  │   └── Especificação completa do app                     │
│  │                                                          │
│  ├── 📗 CHANGELOG.md                                        │
│  │   └── Histórico de todas as mudanças                    │
│  │                                                          │
│  ├── 📂 MVPs/                                               │
│  │   ├── MVP01_*.md                                        │
│  │   ├── MVP02_*.md                                        │
│  │   ├── ...                                               │
│  │   └── MVP14_*.md                                        │
│  │                                                          │
│  ├── 📂 Técnicos/                                           │
│  │   ├── RELATORIO_TECNICO_ABNT.md                         │
│  │   ├── MATERIAL_APRESENTACAO_TCC.md                      │
│  │   └── ATUALIZACAO_*.md                                  │
│  │                                                          │
│  └── 📂 Guias/                                              │
│      ├── Como_Executar.md                                  │
│      ├── Como_Testar.md                                    │
│      └── Como_Contribuir.md                                │
│                                                             │
│  💻 DOCUMENTAÇÃO DE CÓDIGO (KDoc)                           │
│  ┌─────────────────────────────────────────┐               │
│  │ /**                                     │               │
│  │  * Salva uma tarefa no banco de dados  │               │
│  │  *                                      │               │
│  │  * @param task Tarefa a ser salva      │               │
│  │  * @return ID da tarefa salva          │               │
│  │  * @throws Exception se falhar         │               │
│  │  * @since MVP-05                        │               │
│  │  */                                     │               │
│  │ suspend fun saveTask(task: Task): Long │               │
│  └─────────────────────────────────────────┘               │
│                                                             │
│  📊 ESTATÍSTICAS DE DOCUMENTAÇÃO                            │
│  • Total de arquivos .md: 100+                             │
│  • Commits documentados: 100+                              │
│  • MVPs documentados: 14                                   │
│  • Bugs rastreados: 15+                                    │
│  • Migrations documentadas: 7                              │
│                                                             │
│  ✅ RASTREABILIDADE COMPLETA                                │
│  Cada mudança → documentada → versionada → rastreável      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Elementos Visuais Sugeridos:
- **Cores:** Azul (docs técnicos), Verde (guias), Amarelo (código)
- **Layout:** Árvore de diretórios estilizada
- **Screenshot:** Print de um arquivo .md real
- **Números:** Destacar estatísticas em negrito/cor diferente

### Pontos a Destacar na Apresentação:
- "100+ arquivos de documentação técnica"
- "Rastreabilidade completa de todas as mudanças"
- "Cada função documentada com KDoc"
- "Facilita manutenção futura e transferência de conhecimento"

---

## COMO CONVERTER PARA POWERPOINT

### Opção 1: Conversão Manual (Recomendado)

1. **Copie o wireframe ASCII**
2. **Abra PowerPoint/Google Slides**
3. **Insira Formas e Caixas de Texto:**
   - Use retângulos para caixas
   - Use conectores para setas
   - Use caixas de texto para labels
4. **Aplique Cores do Projeto:**
   - Azul: #2196F3
   - Laranja: #FF9800
   - Verde: #4CAF50
   - Roxo: #9C27B0
5. **Adicione Ícones:**
   - Flaticon.com
   - Icons8.com
   - Noun Project

### Opção 2: Ferramentas Online

**Asciiflow:** https://asciiflow.com/
- Cole o ASCII art
- Edite se necessário
- Exporte como imagem

**Draw.io:** https://app.diagrams.net/
- Recrie o diagrama visualmente
- Use biblioteca de ícones integrada
- Exporte como PNG/SVG

**Mermaid Live Editor:** https://mermaid.live/
- Converta para sintaxe Mermaid
- Gere diagrama profissional
- Exporte como PNG

### Opção 3: Gerar Imagem Diretamente

**Carbon:** https://carbon.now.sh/
- Cole o código ASCII
- Escolha tema escuro ou claro
- Exporte como PNG de alta qualidade
- Ótimo para wireframes de código

### Dicas de Formatação no PowerPoint:

1. **Use Fonte Monospace para ASCII:**
   - Courier New
   - Consolas
   - Fira Code

2. **Mantenha Proporções:**
   - Não distorça as formas
   - Use grade do PowerPoint

3. **Animações Sugeridas:**
   - Etapa 1-2: Aparecer item por item
   - Etapa 3: Transição de baixa → alta fidelidade
   - Etapa 5: Timeline aparecer sequencialmente
   - Etapa 6: Pirâmide construir de baixo para cima

4. **Consistência Visual:**
   - Use mesma paleta de cores em todos os slides
   - Mesma fonte para títulos e corpo
   - Mesmo estilo de ícones

---

## EXEMPLOS DE USO EM APRESENTAÇÃO

### Slide de Apresentação Sugerido:

**Título:** "Etapa 2: Definição e Ideação"

**Conteúdo:**
- [LADO ESQUERDO] Lista de Problemas (fundo vermelho claro)
- [CENTRO] Setas grandes animadas
- [LADO DIREITO] Soluções Criativas (fundo verde claro)
- [RODAPÉ] Stack tecnológico com logos

**Animação:**
1. Problema 1 aparece
2. Seta desliza da esquerda
3. Solução 1 aparece
4. Repetir para cada par
5. Stack tecnológico aparece no final

**Tempo:** 2-3 minutos de fala

---

## CHECKLIST DE QUALIDADE

Antes de usar na apresentação, verifique:

- [ ] Todos os wireframes foram convertidos para formato visual
- [ ] Cores consistentes em todos os slides
- [ ] Fontes legíveis (mínimo 18pt para corpo, 28pt para títulos)
- [ ] Ícones de alta qualidade (vetorial se possível)
- [ ] Animações não excessivas (máximo 3 por slide)
- [ ] Contraste adequado (texto legível em projetor)
- [ ] Slides numerados
- [ ] Rodapé com nome do projeto em todos os slides
- [ ] Revisão ortográfica completa
- [ ] Testado em modo apresentação

---

**Documento criado em:** 03/11/2025  
**Última atualização:** 03/11/2025 (Adicionada Estrutura Geral do App)  
**Versão:** 1.1  
**Total de wireframes:** 8 (1 Estrutura Geral + 7 Etapas de Desenvolvimento)  
**Formato:** ASCII Art (conversível para PowerPoint)  
**Status:** ✅ COMPLETO E PRONTO PARA USO

---

**Boa apresentação! 🎓📱⭐**

