# MVP: Personalização e Expansão de Funcionalidades (Novembro/2025)

## Objetivo
Adicionar recursos avançados de personalização do fluxo de atividades, integração com ASR, exportação/importação de atividades e filtro por categoria, promovendo maior flexibilidade e usabilidade.

## Funcionalidades

### 1. Personalização do Fluxo de Passos
- Checkbox em "Histórico e Ferramentas" para habilitar/desabilitar a pergunta de tempo extra ao final de cada step.
- Persistência do estado da checkbox.
- Integração com ASR (Vosk): reconhecimento de voz para decidir automaticamente se avança ou concede mais tempo ao final do step.
- Fallback para resposta manual caso o reconhecimento falhe.

### 2. Exportação e Importação de Atividades
- Botão para exportar atividades (JSON/CSV) em "Histórico e Ferramentas".
- Botão para importar atividades, com validação e prevenção de duplicidades.

### 3. Filtro de Categorias em Atividades
- Componente de filtro (dropdown/chips) na tela de atividades para seleção de categoria.
- Persistência do filtro durante a sessão.

## Roadmap Modular
- Fase 1: Personalização do fluxo de passos (checkbox + ASR)
- Fase 2: Exportação/importação de atividades
- Fase 3: Filtro de categorias

## Critérios de Aceitação
- Cada funcionalidade deve ser testável e documentada nas primeiras 50 linhas dos arquivos afetados.
- Atualização do CHANGELOG.md e demais docs a cada entrega.

## Observações
- Recomenda-se criar branches específicas para cada fase.
- Issues/tarefas detalhadas para cada subitem.

