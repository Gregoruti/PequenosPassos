# GUIDELINES.MD

**Propósito**: Diretrizes fundamentais para desenvolvimento de aplicações móveis 
utilizando Code Assist (IA) como parceiro de desenvolvimento. Integra 
metodologias de arquitetura modular, engenharia de prompt avançada e práticas 
de excelência em desenvolvimento de software.

**Escopo**: Framework universal aplicável a qualquer projeto de desenvolvimento 
Android com IA

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md (CORE)                    │
│              Framework Universal de Desenvolvimento         │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Estabelece Metodologia)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md                      │
│                 Detalhes do Projeto                        │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Especifica Implementação)
                   ▼
┌──────────────────┬──────────────────┬──────────────────────┐
│    PATHS.md      │   CHANGELOG.md   │      README.md       │
│  (Estruturas)    │  (Histórico)     │   (Documentação)     │
└──────────────────┴──────────────────┴──────────────────────┘
```

**Versão**: 1.0.0 | **Data**: 09/10/2025 | **Status**: Ativo e validado

---

## Sumário
1. [Introdução](#1-introdução)
2. [Contexto de Desenvolvimento](#2-contexto-de-desenvolvimento)
3. [Princípios de Modularidade](#3-princípios-de-modularidade)
4. [Arquitetura e Separação de Preocupações](#4-arquitetura-e-separação-de-preocupações)
5. [Metodologia PROMPT](#5-metodologia-prompt)
6. [Gestão de Contexto](#6-gestão-de-contexto)
7. [Processo Operacional da IA](#7-processo-operacional-da-ia)
8. [Regras de Ouro do Desenvolvimento](#8-regras-de-ouro-do-desenvolvimento)
9. [Mecanismos de Proteção](#9-mecanismos-de-proteção)
10. [Qualidade e Validação](#10-qualidade-e-validação)
11. [Controle de Versão](#11-controle-de-versão)

---

## 1. Introdução

Este documento estabelece as diretrizes fundamentais para desenvolvimento de
aplicações móveis utilizando Code Assist (IA) como parceiro de desenvolvimento.
Integra metodologias de arquitetura modular, engenharia de prompt avançada e
práticas de excelência em desenvolvimento de software.

### 1.2 Filosofia de Desenvolvimento
- **Modularidade First**: Entregar valor incrementalmente através de módulos 
  pequenos, isolados e testáveis
- **Context-Aware Development**: Maximizar a eficiência da IA através de
  documentação clara e contexto atualizado
- **Versionamento**: Todos os arquivos devem conter versão e histórico de mudanças
  no CHANGELOG.md
- **Documentação**: Limite de 90 caracteres por linha em todos os arquivos do
  diretório DOCs

### 1.3 Audiência
- **Code Assistants (IA)**: Guia operacional obrigatório
- **Desenvolvedores**: Framework de melhores práticas
- **Arquitetos**: Padrões de estruturação de projetos

---

## 2. Contexto de Desenvolvimento

### 2.1 Stack Tecnológico Padrão
- **Linguagem Principal**: Kotlin para desenvolvimento Android
- **Arquitetura**: MVVM (Model-View-ViewModel) com Jetpack Compose
- **Build System**: Gradle com Kotlin DSL
- **Versionamento**: Semântico (MAJOR.MINOR.PATCH)
- **Documentação**: Markdown com KDocs para código

### 2.2 Regras Fundamentais de Código
1. **Idioma**: Todo código e documentação em **Português Brasileiro (PT-BR)**
2. **Comentários**: KDocs obrigatórios para classes e métodos públicos
3. **Estrutura**: Seguir padrões Clean Architecture adaptados para Android
4. **Dependências**: Preferir injeção de dependências (Hilt/Dagger)
5. **Testes**: Cobertura mínima de 80% para camada de domínio

### 2.3 Processo de Versionamento
- **MAJOR**: Mudanças que quebram compatibilidade
- **MINOR**: Novas funcionalidades mantendo compatibilidade  
- **PATCH**: Correções de bugs e melhorias menores
- **Sincronização**: Versão deve ser consistente entre build.gradle.kts, 
  documentação e interface

---

## 3. Princípios de Modularidade

### 3.1 Conceito Central
**Entregar valor incrementalmente**, dividindo problemas complexos em módulos 
pequenos, isolados e testáveis. Cada módulo deve ter uma responsabilidade clara 
e interfaces bem definidas.

### 3.2 Benefícios para Code Assist
- **Clareza de Contexto**: Reduz ambiguidade, fornecendo contexto menor e claro
- **Precisão**: Prompts focados resultam em sugestões mais precisas
- **Iteração Rápida**: Permite correções isoladas sem impacto sistêmico
- **Testabilidade**: Facilita criação de testes com targets claros
- **Paralelização**: Melhora colaboração entre desenvolvedores e IAs

### 3.3 Diretiva Chave
**Use prompts específicos por módulo e arquivo-alvo. Evite "fazer tudo de uma 
vez" (Big Bang).**

---

## 4. Arquitetura e Separação de Preocupações

### 4.1 Princípios Fundamentais
- **Coesão Alta, Acoplamento Baixo**: O Santo Graal da arquitetura
- **Interfaces Explícitas**: DTOs, Schemas e Ports devem ser priorizados
- **Evolução Segura**: Feature flags, migrações e compatibilidade garantida

### 4.2 Camadas Arquiteturais

| Camada | Responsabilidade | Diretiva |
|--------|------------------|----------|
| **Domínio (Core)** | Entidades, regras de negócio, exceções | Domínio Primeiro: Regras no core, sem dependências externas |
| **Aplicação** | Use cases, orquestração, transações | Lógica de fluxo e coordenação |
| **Infraestrutura** | Repositórios, DB models, integrações | Persistência e comunicação externa |
| **Apresentação** | UI, APIs, validações, mapeamento | Interface do sistema, entrada/saída |

### 4.3 Ordem de Implementação
1. **Core**: Definir entidades e regras de domínio
2. **Aplicação**: Casos de uso que orquestram
3. **Infraestrutura**: Persistência e integrações
4. **Apresentação**: APIs e interface de usuário
5. **Observabilidade**: Logs, métricas e monitoramento
6. **Testes**: Cobertura por camada com critérios específicos

---

## 5. Metodologia PROMPT

### 5.1 Framework Sistemático
A Metodologia PROMPT estrutura a comunicação com Code Assist garantindo clareza 
e resultados de alta qualidade.

| Componente | Descrição | Exemplo |
|------------|-----------|---------|
| **P**ersona | Papel e nível de experiência | "Desenvolvedor sênior Android especialista em Kotlin" |
| **R**ole/Rules | Expertise ou restrições | "Seguindo Clean Architecture e padrões SOLID" |
| **O**bjective | Objetivo geral e específico | "Implementar funcionalidade de login" |
| **M**essage | Mensagem clara da tarefa | "Criar tela de login com validação de usuário" |
| **P**arameters | Requisitos técnicos | "Jetpack Compose, MVVM, Hilt, testes unitários" |
| **T**ask | Resultado final e critérios | "Tela funcional com 95% de cobertura de testes" |

### 5.2 Boas Práticas de Comunicação
- **Clareza**: Ações inequívocas ("Criar função validar email" vs "fazer 
  validação")
- **Especificidade**: Detalhes de escopo e retorno incluídos
- **Contextualidade**: Informações de ambiente e requisitos específicos
- **Completude**: Tratamento de erro, documentação e testes solicitados
- **Comandos Imperativos**: Linguagem direta com verbos de ação

---

## 6. Gestão de Contexto

### 6.1 Princípios de Contexto Eficiente
- **Foco**: Passar apenas o necessário para a etapa atual
- **Granularidade**: Trabalhar em partes pequenas e verificáveis
- **Contratos**: Explicitar entradas, saídas e invariantes
- **Evidência**: Fornecer trechos concretos, não descrições vagas

### 6.2 Técnicas Avançadas

#### 6.2.1 Contexto Mínimo Viável
| O que Passar | O que Evitar |
|--------------|--------------|
| Objetivo e critérios de aceite | Arquivos longos sem relevância |
| Interfaces e modelos afetados | Código desnecessário (ruído) |
| Regras de negócio essenciais | Features não relacionadas |
| 1-2 trechos de referência | Histórico irrelevante de chat |

#### 6.2.2 Delta Prompts (Mudança Incremental)
- **"Extraia função validar_documento()"**: Menor uso de contexto
- **"Injete repositório via construtor"**: Maior controle sobre resultado
- **"Atualize teste A para cobrir erro X"**: Menor risco de regressão

#### 6.2.3 Testes como Âncoras
Escrever 1-2 testes mínimos antes da implementação. Pedir ao Code Assist para 
fazer o código passar neles, garantindo critérios objetivos de sucesso.

---

## 7. Processo Operacional da IA

### 7.1 Fluxo Obrigatório (Passo a Passo)

#### 7.1.1 Análise Inicial
1. **Interpretar Solicitação**: Entender objetivo principal
2. **Consulta Obrigatória**: Carregar diretrizes deste arquivo
3. **Verificação de Especificações**: Consultar SPECIFICATION_FOR_APP.md

#### 7.1.2 Planejamento e Execução
1. **Identificar Artefatos**: Determinar arquivos impactados
2. **Planejar Alterações**: Formular plano de sincronia código-documentação
3. **Implementação**: Seguir regras arquiteturais e de qualidade
4. **Validação**: Garantir consistência e funcionamento

#### 7.1.3 Verificação de Qualidade
- **Sincronia**: Código, especificações e documentação alinhados
- **Testes**: Cobertura adequada conforme critérios
- **Performance**: Build time e flakiness dentro dos limites
- **Documentação**: KDocs e comentários atualizados

### 7.2 Comportamento na Comunicação
- **Insuficiência de Informações**: Alertar explicitamente sobre lacunas
- **Detecção de Divergências**: Reportar inconsistências encontradas
- **Proatividade Controlada**: Sugerir melhorias, mas solicitar aprovação

---

## 8. Regras de Ouro do Desenvolvimento

### 8.1 Princípios Fundamentais
- **DRY (Don't Repeat Yourself)**: Evitar duplicação, criar abstrações 
  reutilizáveis
- **KISS (Keep It Simple, Stupid)**: Priorizar simplicidade sobre complexidade 
  desnecessária
- **YAGNI (You Aren't Gonna Need It)**: Não implementar funcionalidades 
  especulativas
- **SoC (Separation of Concerns)**: Uma responsabilidade por componente
- **Dependency Injection**: Favorecer DI para desacoplamento e testabilidade
- **Test-Driven Development**: Escrever testes antes ou junto com o código
- **Document While Coding**: Manter documentação sincronizada em tempo real

### 8.2 Padrões de Implementação Android
- **Single Activity**: Usar Navigation Component com Jetpack Compose
- **Reactive Programming**: Flow e StateFlow para streams de dados
- **Material Design**: Seguir guidelines do Material 3
- **Accessibility**: Implementar desde o início, não como afterthought

---

## 9. Mecanismos de Proteção

### 9.1 Anti-Looping Mechanism
**Objetivo**: Prevenir ciclos infinitos de consulta e regeneração

#### 9.1.1 Condições de Ativação
- Releitura das mesmas instruções mais de 2 vezes na mesma interação
- Regeneração do mesmo bloco sem progresso significativo
- Repetição da mesma pergunta de esclarecimento

#### 9.1.2 Ação Automática
```
PARE IMEDIATAMENTE → Consulte Usuário:
"Detectei possível ciclo de repetição. Como prosseguir?
A) Continuar apesar do alerta
B) Explicar onde identifiquei o bloqueio  
C) Abortar e aguardar novas instruções"
```

### 9.2 Anti-Regressão Mechanism
**Objetivo**: Proteger funcionalidades validadas contra degradação

#### 9.2.1 Checklist Obrigatório (ANTES de modificar código)
- [ ] Li o código atual COMPLETO dos arquivos a modificar?
- [ ] Identifiquei TODAS as funcionalidades existentes no escopo?
- [ ] Defini se vou ADICIONAR ou SUBSTITUIR/ALTERAR funcionalidade?
- [ ] Validei que modificação não degrada funcionalidades preserváveis?
- [ ] Verifiquei dependências e impactos em outros módulos?
- [ ] Consultei VALIDATION_STATUS.md para funcionalidades impactadas?
- [ ] Considerei impacto potencial em outros módulos do sistema?

#### 9.2.2 Regra de Ouro para Substituição
**NUNCA SUBSTITUIR código sem:**
- Comentários claros explicando O QUE e POR QUÊ da mudança
- Garantia de manutenção de funcionalidades validadas (✅ status)
- Declaração explícita quando substituição oferece equivalência/melhoria

---

## 10. Qualidade e Validação

### 10.1 Critérios de Conclusão ("Done")
- **Compilação**: Build bem-sucedido sem warnings críticos
- **Testes**: Cobertura mínima por camada (Domínio ≥95%, UI ≥80%)
- **Lint**: Zero violações de regras críticas
- **Contratos**: APIs e repositórios com contract tests
- **Performance**: Tempo de build <30s, zero flaky tests
- **Documentação**: KDocs completos, README atualizado

### 10.2 Formatação de Documentação
**Diretriz Obrigatória**: Todos os arquivos de documentação no diretório `/docs` 
devem seguir o limite de **90 caracteres por linha**. 

#### 10.2.1 Regras de Formatação
- **Limite de Linha**: Máximo 90 caracteres por linha
- **Quebra de Linha**: Usar CR+LF (Windows) ou LF (Unix/Linux)
- **Exceções**: URLs, código em blocos, diagramas ASCII podem exceder o limite
- **Texto Corrido**: Quebrar frases em pontos naturais (vírgulas, conectivos)
- **Listas**: Cada item deve respeitar o limite individualmente
- **Tabelas**: Ajustar colunas para manter legibilidade dentro do limite

#### 10.2.2 Exemplo de Formatação Correta
```markdown
Este é um exemplo de texto que respeita o limite de 90 caracteres por 
linha, garantindo melhor legibilidade e padronização da documentação 
técnica do projeto.
```

#### 10.2.3 Ferramentas Recomendadas
- **VS Code**: Régua vertical em 90 caracteres (`"editor.rulers": [90]`)
- **Markdown Lint**: Validação automática de formatação
- **Git Hooks**: Verificação pré-commit para conformidade

### 10.3 Métricas por Camada
| Camada | Cobertura Mínima | Critérios Específicos |
|--------|------------------|----------------------|
| Domínio | 95% | Regras de negócio 100% testadas |
| Aplicação | 90% | Use cases com cenários felizes/tristes |
| Infraestrutura | 85% | Integração com mocks |
| Apresentação | 80% | UI tests para fluxos críticos |

### 10.4 Anti-padrões a Evitar
- **Big Bang**: PRs massivos sem decomposição
- **Prompts Vagos**: Sem arquivos-alvo ou critérios de aceite
- **Acoplamento**: Violação da Inversão de Dependência
- **Regras na API**: Lógica de negócio fora do domínio
- **Documentação Defasada**: Dessincronia entre código e docs

---

## 11. Controle de Versão

### 11.1 Estratégia de Versionamento
- **Semantic Versioning**: MAJOR.MINOR.PATCH rigorosamente aplicado
- **Sincronização**: Versão consistente em build.gradle.kts, documentação e UI
- **Histórico Detalhado**: CHANGELOG.md com delta entre versões
- **Validação Integrada**: Status de cada versão documentado com testes

### 11.2 Processo de Release
1. **Desenvolvimento**: Feature branches com testes completos
2. **Integração**: Merge apenas após validação completa
3. **Versionamento**: Incremento automático baseado em tipo de mudança
4. **Documentação**: Atualização sincronizada de todos os arquivos
5. **Deploy**: Build, teste e release com rollback automático se falhas

### 11.3 Boas Práticas de Commit
- **Mensagens Descritivas**: Formato: `tipo(escopo): descrição [versão]`
- **Atomicidade**: Um commit por funcionalidade ou correção
- **Referências**: Linking com issues e pull requests
- **Assinatura**: Commits assinados para segurança

---

## 12. Diretiva de Conformidade

### 12.1 Meta-Instrução para Code Assist
O Code Assist **DEVE** tratar este documento como requisito de entrada 
**OBRIGATÓRIO** para qualquer solicitação de desenvolvimento.

### 12.2 Processo de Verificação
1. **Avaliação Inicial**: Verificar se todos os componentes PROMPT estão claros
2. **Ação de Preenchimento**: Solicitar informações faltantes antes de 
   prosseguir
3. **Feedback Educativo**: Explicar qual princípio foi violado e por que é 
   importante
4. **Garantia de Qualidade**: Assegurar critérios de sucesso alinhados com 
   diretrizes

### 12.3 Exemplo de Feedback
```
"Para garantir máxima precisão (PROMPT), por favor especifique:
- Persona (P): Qual especialidade? (ex: sênior em mobile security)
- Parameters (P): Restrições técnicas? (ex: usar regex RFC 5322)
- Task (T): Critério de falha? (o que retornar em validação inválida?)"
```

---

**Versão**: 1.0.0 (Integração completa CONTEXT + RULES + GUIDELINES)  
**Data**: 09/10/2025  
**Status**: Ativo e validado para desenvolvimento FonoVirtual e projetos futuros
