# PLANEJAMENTO MVP08 - Personalização e Expansão de Funcionalidades

## Objetivo
Implementar recursos avançados de personalização e ferramentas para o usuário, tornando o app mais flexível, inteligente e adaptável ao perfil da criança e do responsável.

## Fases e Funcionalidades

### Fase 1: Personalização do Fluxo de Passos
- [ ] Checkbox em “Histórico e Ferramentas” para habilitar/desabilitar a pergunta de tempo extra ao final de cada step.
- [ ] Persistência do estado da checkbox no banco de dados (AppSettings).
- [ ] Integração com ASR (Vosk): reconhecimento de voz para decidir automaticamente se avança ou concede mais tempo ao final do step.
- [ ] Fallback para resposta manual caso o reconhecimento falhe.
- [ ] Testes unitários e de integração para o novo fluxo.

### Fase 2: Exportação/Importação de Atividades
- [ ] Botão para exportar atividades (JSON/CSV) em “Histórico e Ferramentas”.
- [ ] Botão para importar atividades, com validação e prevenção de duplicidades.
- [ ] Testes de exportação/importação e validação de integridade dos dados.

### Fase 3: Filtro de Categorias
- [ ] Componente de filtro (dropdown/chips) na tela de atividades para seleção de categoria.
- [ ] Persistência do filtro durante a sessão.
- [ ] Testes de usabilidade e integração do filtro.

## Critérios de Aceitação
- Cada funcionalidade deve ser testável e documentada nas primeiras 50 linhas dos arquivos afetados.
- Atualização do CHANGELOG.md e demais docs a cada entrega.
- Validação prática no app (dispositivo real ou emulador).

## Roadmap Modular
- Fase 1: Personalização do fluxo de passos (checkbox + ASR)
- Fase 2: Exportação/importação de atividades
- Fase 3: Filtro de categorias

## Observações
- Recomenda-se criar branches específicas para cada fase.
- Issues/tarefas detalhadas para cada subitem.
- Checklist de build, testes e documentação obrigatórios antes de merge.

---

**Data de início:** 2025-10-31
**Responsável:** PequenosPassos Development Team
**Status:** Em planejamento

