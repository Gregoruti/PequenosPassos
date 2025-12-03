<!--
Arquivo: docs/WIREFRAME_VISAO_GERAL_APP.md
Tipo: Wireframe visual da estrutura completa do app
Objetivo: Fornecer visão macro para apresentação de TCC - Slide introdutório
Criado: 2025-11-03
Autor: Claude Sonnet 4.5 (GitHub Copilot)

INSTRUÇÕES PARA USO:
Este wireframe fornece uma visão geral de alto nível do aplicativo.
Ideal para usar como slide introdutório antes de detalhar as etapas de desenvolvimento.

Correlações: WIREFRAMES_ETAPAS_DESENVOLVIMENTO.md, MATERIAL_APRESENTACAO_TCC.md
-->

# WIREFRAME: VISÃO GERAL DO APP PEQUENOS PASSOS

**Versão:** 1.0  
**Data:** 03/11/2025  
**Uso:** Slide Introdutório de Apresentação de TCC

---

## VISÃO MACRO: DIAGRAMA COMPLETO DO SISTEMA

```
╔═══════════════════════════════════════════════════════════════════════════════════════╗
║                     📱 PEQUENOS PASSOS - VISÃO GERAL DO SISTEMA                       ║
╚═══════════════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                                    🎯 OBJETIVO                                          │
│     Auxiliar crianças de 0-6 anos com TEA/TDAH a executarem tarefas diárias           │
│                    de forma estruturada, visual e motivadora                           │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              👥 ATORES DO SISTEMA                                       │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│   👶 CRIANÇA                 👨‍👩‍👧 RESPONSÁVEL              🏥 TERAPEUTA                  │
│   ┌──────────────┐          ┌──────────────┐           ┌──────────────┐               │
│   │ • Executa    │          │ • Cadastra   │           │ • Acompanha  │               │
│   │   tarefas    │          │   tarefas    │           │   evolução   │               │
│   │ • Ganha      │          │ • Gerencia   │           │ • Ajusta     │               │
│   │   estrelas   │          │   perfil     │           │   estratégia │               │
│   │ • Recebe     │          │ • Monitora   │           │ • Analisa    │               │
│   │   feedback   │          │   progresso  │           │   dados      │               │
│   └──────┬───────┘          └──────┬───────┘           └──────┬───────┘               │
│          │                         │                          │                        │
│          └─────────────────────────┼──────────────────────────┘                        │
│                                    │                                                   │
│                                    ▼                                                   │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           📱 CAMADAS DO APLICATIVO                                      │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  ┌────────────────────────────────────────────────────────────────────────────────┐    │
│  │                          🎨 CAMADA DE APRESENTAÇÃO                             │    │
│  ├────────────────────────────────────────────────────────────────────────────────┤    │
│  │                                                                                │    │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │    │
│  │  │   Splash    │  │    Home     │  │ Atividades  │  │  Execução   │         │    │
│  │  │   Screen    │─►│   Screen    │─►│   (Lista)   │─►│  Passo a    │         │    │
│  │  │             │  │             │  │             │  │   Passo     │         │    │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘         │    │
│  │                                                             │                 │    │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐       ▼                 │    │
│  │  │  Cadastro   │  │   Edição    │  │  Histórico  │  ┌─────────────┐         │    │
│  │  │   Perfil    │  │  Tarefas    │  │     &       │  │  Conclusão  │         │    │
│  │  │             │  │             │  │ Ferramentas │  │  (Feedback) │         │    │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘         │    │
│  │                                                                                │    │
│  └────────────────────────────────────────────────────────────────────────────────┘    │
│                                       │                                                │
│                                       ▼                                                │
│  ┌────────────────────────────────────────────────────────────────────────────────┐    │
│  │                           💼 CAMADA DE DOMÍNIO                                 │    │
│  ├────────────────────────────────────────────────────────────────────────────────┤    │
│  │                                                                                │    │
│  │  📦 MODELOS                    🎯 USE CASES                 🔌 REPOSITORIES   │    │
│  │  ┌──────────────┐             ┌──────────────┐            ┌──────────────┐   │    │
│  │  │ Task         │             │ SaveTask     │            │ ITaskRepo    │   │    │
│  │  │ Step         │             │ DeleteTask   │            │ IStepRepo    │   │    │
│  │  │ ChildProfile │             │ GetTasks     │            │ IChildRepo   │   │    │
│  │  │ AppSettings  │             │ MarkComplete │            │ ISettingsRepo│   │    │
│  │  │ Completion   │             │ GetStats     │            │ ICompletionRepo   │   │
│  │  └──────────────┘             └──────────────┘            └──────────────┘   │    │
│  │                                                                                │    │
│  └────────────────────────────────────────────────────────────────────────────────┘    │
│                                       │                                                │
│                                       ▼                                                │
│  ┌────────────────────────────────────────────────────────────────────────────────┐    │
│  │                            💾 CAMADA DE DADOS                                  │    │
│  ├────────────────────────────────────────────────────────────────────────────────┤    │
│  │                                                                                │    │
│  │  🗄️ ROOM DATABASE                                                              │    │
│  │  ┌────────────────────────────────────────────────────────────────────────┐   │    │
│  │  │ AppSettings │ ChildProfile │ Task │ Step │ TaskCompletion │ Category │   │    │
│  │  └────────────────────────────────────────────────────────────────────────┘   │    │
│  │                                                                                │    │
│  │  📝 DAOs              🔄 TypeConverters         📊 Migrations                 │    │
│  │  ┌──────────┐        ┌──────────────┐         ┌──────────┐                   │    │
│  │  │ TaskDao  │        │ LocalDate    │         │ V1 → V8  │                   │    │
│  │  │ StepDao  │        │ LocalDateTime│         │ (7 migs) │                   │    │
│  │  │ ChildDao │        │ List<String> │         └──────────┘                   │    │
│  │  └──────────┘        └──────────────┘                                         │    │
│  │                                                                                │    │
│  └────────────────────────────────────────────────────────────────────────────────┘    │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         🎯 RECURSOS DE ACESSIBILIDADE                                   │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│   🔊 TTS (Text-to-Speech)          🎤 ASR (Speech Recognition)      🖼️ Visual         │
│   ┌─────────────────────┐          ┌──────────────────────┐       ┌──────────────┐    │
│   │ • Lê instruções     │          │ • Vosk offline       │       │ • Imagens    │    │
│   │ • Fala nome criança │          │ • Português BR       │       │   ilustradas │    │
│   │ • Feedback sonoro   │          │ • Comandos infantis  │       │ • Timer em   │    │
│   │ • Mensagens         │          │ • Pop-up interativo  │       │   barra      │    │
│   │   motivacionais     │          │ • 50 comandos        │       │ • Ícones     │    │
│   └─────────────────────┘          └──────────────────────┘       │   grandes    │    │
│                                                                    └──────────────┘    │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            🎮 GAMIFICAÇÃO                                               │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│   ⭐ SISTEMA DE ESTRELAS               📊 ESTATÍSTICAS              🏆 RECOMPENSAS     │
│   ┌─────────────────────┐            ┌──────────────────┐         ┌──────────────┐    │
│   │ • 1-5 estrelas/     │            │ • Diárias        │         │ • Mensagens  │    │
│   │   tarefa            │            │ • Semanais       │         │   variadas   │    │
│   │ • Acumulador diário │            │ • Top 3 mais     │         │ • Feedback   │    │
│   │ • Visual colorido   │            │   executadas     │         │   visual     │    │
│   │ • Motivação clara   │            │ • Top 3 menos    │         │ • TTS lê     │    │
│   │                     │            │   executadas     │         │   resultado  │    │
│   └─────────────────────┘            └──────────────────┘         └──────────────┘    │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                       📦 ORGANIZAÇÃO DE CONTEÚDO                                        │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│                        27 CATEGORIAS EM 5 GRUPOS TEMÁTICOS                             │
│                                                                                         │
│   🏠 Casa (7)    🧘 Autocuidado (8)    🎓 Escola (4)    🎨 Lazer (4)    🍽️ Alim (4)    │
│                                                                                         │
│   ┌────────────────────────────────────────────────────────────────────────────┐       │
│   │ Cada tarefa pode ter:                                                      │       │
│   │ • Título e descrição                                                       │       │
│   │ • Categoria (com emoji)                                                    │       │
│   │ • 1-N passos sequenciais                                                   │       │
│   │ • Imagem principal + imagem por passo                                      │       │
│   │ • Timer configurável por passo (5s a 10min)                                │       │
│   │ • Número de estrelas (1-5)                                                 │       │
│   └────────────────────────────────────────────────────────────────────────────┘       │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                      💻 STACK TECNOLÓGICO RESUMIDO                                      │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│   Kotlin 2.0.20  │  Jetpack Compose  │  Room Database  │  Hilt DI  │  Coroutines      │
│   TTS (Android)  │  ASR (Vosk)       │  Material 3     │  Flow     │  Clean Arch      │
│                                                                                         │
│   ✅ 100% Offline    ✅ Open Source    ✅ Android 10+    ✅ Kotlin Nativo              │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           📊 MÉTRICAS DO PROJETO                                        │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│   📁 Linhas de Código: ~15.000+        📋 Telas Implementadas: 8                       │
│   📝 Arquivos Documentação: 100+       🧪 Testes Criados: 50+                          │
│   🔄 Commits: 100+                     📦 MVPs Completos: 14                           │
│   🗃️ Tabelas Banco: 7                  🏗️ Migrations: 7                                │
│   ⭐ Versão Atual: 2.1.0               ✅ Status: 100% Funcional                        │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## FLUXO DE USO TÍPICO

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                        👣 JORNADA DO USUÁRIO (CRIANÇA)                                  │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  📱 PRIMEIRA VEZ                                                                        │
│  ┌──────────────────────────────────────────────────────────────────────────────────┐  │
│  │ 1. Abre o app → Splash Screen (3s)                                              │  │
│  │ 2. Onboarding/Cadastro                                                           │  │
│  │    • Responsável cadastra: Nome, Data Nascimento, Observações                    │  │
│  │ 3. Responsável acessa "Edição"                                                   │  │
│  │    • Cria primeira tarefa (ex: "Escovar os Dentes")                              │  │
│  │    • Define categoria: 🧘 Autocuidado → 🪥 Higiene Bucal                         │  │
│  │    • Adiciona passos:                                                            │  │
│  │      Step 1: "Pegar escova e pasta" (30s) [foto da escova]                       │  │
│  │      Step 2: "Colocar pasta na escova" (20s) [foto]                              │  │
│  │      Step 3: "Escovar os dentes" (120s) [foto escovando]                         │  │
│  │      Step 4: "Enxaguar a boca" (30s) [foto torneira]                             │  │
│  │      Step 5: "Guardar a escova" (20s) [foto porta-escovas]                       │  │
│  │    • Define: 3 estrelas                                                          │  │
│  │    • Salva tarefa                                                                │  │
│  └──────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                         │
│  🎯 USO DIÁRIO (PELA CRIANÇA)                                                           │
│  ┌──────────────────────────────────────────────────────────────────────────────────┐  │
│  │ 1. Criança abre o app                                                            │  │
│  │    → Tela inicial: "Olá, Maria! Você tem 5 atividades para fazer"               │  │
│  │                    "Você já tem 0 estrelas hoje ⭐"                               │  │
│  │                                                                                  │  │
│  │ 2. Toca em [📋 Atividades]                                                       │  │
│  │    → Lista de tarefas com cards coloridos                                       │  │
│  │    → Vê: "🪥 Escovar os Dentes | 5 passos | ~3min | 3⭐"                         │  │
│  │                                                                                  │  │
│  │ 3. Toca em [▶️ Executar]                                                         │  │
│  │    → TTS fala: "Maria, Escovar os Dentes"                                       │  │
│  │    → Tela de execução aparece                                                   │  │
│  │                                                                                  │  │
│  │ 4. PASSO 1 INICIA AUTOMATICAMENTE                                               │  │
│  │    → TTS fala: "Maria, pegar escova e pasta"                                    │  │
│  │    → Mostra imagem da escova                                                    │  │
│  │    → Timer em barra: 30 segundos (verde → amarelo → vermelho)                   │  │
│  │    → Barra de progresso: "Passo 1 de 5"                                         │  │
│  │                                                                                  │  │
│  │ 5. TIMER TERMINA                                                                │  │
│  │    → Pop-up (se habilitado): "Podemos avançar ou quer mais 30 segundos?"       │  │
│  │    → Opção A: Criança clica "Avançar"                                           │  │
│  │    → Opção B: Criança fala "Vamos" (ASR detecta e avança)                       │  │
│  │    → Opção C: Criança clica "Mais tempo" (+30s)                                 │  │
│  │                                                                                  │  │
│  │ 6. REPETE PASSOS 2-5 ATÉ O ÚLTIMO PASSO                                         │  │
│  │                                                                                  │  │
│  │ 7. CONCLUSÃO                                                                    │  │
│  │    → Tela colorida: "🎉 Parabéns, Maria!"                                        │  │
│  │    → "Você ganhou 3 estrelas! ⭐⭐⭐"                                              │  │
│  │    → TTS fala: "Maria, você ganhou 3 estrelas"                                  │  │
│  │    → Mensagem aleatória: "Você é incrível!"                                     │  │
│  │    → Botão: [Voltar às Atividades]                                              │  │
│  │                                                                                  │  │
│  │ 8. DE VOLTA À LISTA                                                             │  │
│  │    → Card "Escovar os Dentes" agora mostra ✅ e está desabilitado               │  │
│  │    → Tom de cor mais claro (já completada hoje)                                 │  │
│  └──────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                         │
│  📊 ACOMPANHAMENTO (RESPONSÁVEL/TERAPEUTA)                                              │
│  ┌──────────────────────────────────────────────────────────────────────────────────┐  │
│  │ 1. Acessa [📊 Histórico & Ferramentas]                                          │  │
│  │    → Vê estatísticas:                                                            │  │
│  │      • Média diária de estrelas: 12.5 ⭐                                         │  │
│  │      • Média diária de tarefas: 4.2 ✅                                           │  │
│  │      • Estrelas do dia: 3 ⭐                                                     │  │
│  │      • Tarefas completadas hoje: 1/5                                            │  │
│  │                                                                                  │  │
│  │    → Top 3 mais executadas (7 dias):                                            │  │
│  │      1. Escovar os Dentes (7 vezes) ⭐⭐⭐                                         │  │
│  │      2. Lavar as Mãos (6 vezes) ⭐⭐                                              │  │
│  │      3. Guardar Brinquedos (5 vezes) ⭐                                          │  │
│  │                                                                                  │  │
│  │    → Top 3 menos executadas (7 dias):                                           │  │
│  │      1. Fazer Lição (1 vez) ⭐⭐⭐⭐                                                │  │
│  │      2. Arrumar a Cama (2 vezes) ⭐⭐⭐                                            │  │
│  │      3. Regar Plantas (0 vezes) ⭐                                               │  │
│  │                                                                                  │  │
│  │ 2. Insights obtidos:                                                            │  │
│  │    ✅ Higiene está sendo bem executada                                           │  │
│  │    ⚠️ Tarefas escolares precisam de atenção                                     │  │
│  │    💡 Criar incentivo extra para "Arrumar a Cama"                               │  │
│  └──────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## COMO USAR ESTE WIREFRAME NA APRESENTAÇÃO

### **Slide Sugerido: "Visão Geral do Sistema"**

**Duração:** 2-3 minutos

**Estrutura:**
1. **Mostrar diagrama completo** (visão macro)
2. **Destacar atores** (criança, responsável, terapeuta)
3. **Explicar camadas** (Presentation, Domain, Data)
4. **Ressaltar acessibilidade** (TTS, ASR, Visual)
5. **Mencionar métricas** (14 MVPs, 100% funcional)

**Fala Sugerida:**

"Este diagrama apresenta a visão geral completa do Pequenos Passos.

**Sobre os Atores:** O sistema foi projetado para três atores principais:
- A **criança**, que executa as tarefas e ganha estrelas
- O **responsável**, que cadastra tarefas e monitora o progresso
- O **terapeuta**, que pode acompanhar a evolução e ajustar estratégias

**Sobre a Arquitetura:** Utilizamos Clean Architecture com três camadas bem definidas:
- **Apresentação**: 8 telas em Jetpack Compose
- **Domínio**: Regras de negócio isoladas com Use Cases
- **Dados**: Room Database com 7 tabelas relacionadas

**Sobre Acessibilidade:** Implementamos três pilares de acessibilidade:
- **TTS** para leitura de instruções
- **ASR** para resposta por voz
- **Interface visual** com imagens, ícones e timer em barra

**Sobre Gamificação:** Sistema de estrelas motiva a criança, com estatísticas detalhadas que permitem acompanhamento terapêutico.

**Sobre Organização:** 27 categorias em 5 grupos temáticos cobrem toda a rotina diária da criança.

**Resultado:** Versão 2.1.0 atual está 100% funcional, com 14 MVPs completos e mais de 100 commits documentados."

---

**Documento criado em:** 03/11/2025  
**Versão:** 1.0  
**Uso:** Slide introdutório de apresentação  
**Status:** ✅ COMPLETO E PRONTO PARA USO

---

**💡 DICA:** Use este wireframe como primeiro slide técnico, logo após a introdução do problema. Ele contextualiza toda a solução antes de entrar nos detalhes de cada etapa de desenvolvimento.

