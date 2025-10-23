# RELATÓRIO TÉCNICO: Fundamentação Teórica do Aplicativo Pequenos Passos

**Autor:** PequenosPassos Development Team  
**Data:** 21/10/2025  
**Versão:** 1.9.6  

---

## 1. INTRODUÇÃO

O presente relatório tem como objetivo apresentar a fundamentação teórica do aplicativo
Pequenos Passos, desenvolvido para auxiliar crianças e responsáveis na organização de tarefas
cotidianas, promovendo autonomia e desenvolvimento de hábitos saudáveis. O documento segue
as normas da ABNT (NBR 14724:2011) para trabalhos acadêmicos e técnicos, incluindo referências
bibliográficas e estrutura formal.

## 2. OBJETIVO DO APP

O Pequenos Passos visa facilitar o acompanhamento e execução de tarefas diárias por crianças,
utilizando recursos de gamificação, timers, imagens e categorização de atividades. O app permite
criar, editar e monitorar tarefas e seus respectivos passos (steps), promovendo engajamento e
autonomia.

## 3. FUNDAMENTAÇÃO TEÓRICA

### 3.1. Aprendizagem por Rotina e Autonomia Infantil

Segundo Piaget (1976), o desenvolvimento da autonomia na infância é favorecido por práticas que
estimulam a tomada de decisão e a repetição de rotinas. O uso de listas de tarefas e acompanhamento
visual contribui para a internalização de hábitos (VYGOTSKY, 1998).

### 3.2. Gamificação e Motivação

A gamificação, definida por Deterding et al. (2011), consiste na aplicação de elementos de jogos em
contextos não lúdicos, visando aumentar o engajamento e a motivação. O Pequenos Passos utiliza
estrelas, recompensas visuais e timers para incentivar o cumprimento das tarefas (WERBACH;
HUNTER, 2012).

### 3.3. Tecnologias Educacionais e Usabilidade

O uso de aplicativos móveis para educação tem se mostrado eficaz na promoção de autonomia e
organização (MORAN, 2015). A adoção de interfaces intuitivas e feedback visual é recomendada para
públicos infantis (NIELSEN, 1993).

## 4. FUNCIONALIDADES IMPLEMENTADAS

- Cadastro e edição de tarefas com título, descrição, horário, categoria e imagem
- Gerenciamento de passos (steps) para cada tarefa, com ordem, imagem e timer
- Sistema de estrelas para motivação
- Timer visual para execução de cada passo
- Histórico de tarefas e acompanhamento de progresso
- Interface adaptada para crianças, com ícones, cores e feedback visual
- Persistência de dados local via Room Database
- Testes unitários e de integração para garantir robustez

## 5. ARQUITETURA E TECNOLOGIAS

- **Arquitetura:** Clean Architecture, separando camadas de apresentação, domínio e dados
  (MARTIN, 2017)
- **Framework:** Android Jetpack Compose para UI reativa
- **Persistência:** Room Database (SQLite)
- **Injeção de Dependências:** Hilt
- **Gerenciamento de Estado:** StateFlow e ViewModel
- **Testes:** JUnit, Mockito
- **Documentação:** Markdown, seguindo ABNT para relatórios

## 6. REFERÊNCIAS BIBLIOGRÁFICAS

- PIAGET, J. A formação do símbolo na criança. Rio de Janeiro: Zahar, 1976.
- VYGOTSKY, L. S. A formação social da mente. São Paulo: Martins Fontes, 1998.
- DETERDING, S. et al. Gamification: Using Game-Design Elements in Non-Gaming Contexts. In:
  CHI 2011.
- WERBACH, K.; HUNTER, D. For the Win: How Game Thinking Can Revolutionize Your Business.
  Wharton Digital Press, 2012.
- MORAN, J. M. Mudando a educação com a tecnologia e a inovação. Revista Educação, v. 38,
  n. 3, p. 12-17, 2015.
- NIELSEN, J. Usability Engineering. San Francisco: Morgan Kaufmann, 1993.
- MARTIN, R. C. Clean Architecture: A Craftsman's Guide to Software Structure and Design.
  Prentice Hall, 2017.

---

**Este relatório está em conformidade com as normas da ABNT para trabalhos técnicos e
acadêmicos.**
