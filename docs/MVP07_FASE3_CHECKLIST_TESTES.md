# ✅ CHECKLIST DE TESTES - MVP-07 FASE 3

**Data:** 20/10/2025  
**Versão:** 1.9.0 (em teste)  
**Testador:** _______________

---

## 📱 PRÉ-REQUISITOS

- [ ] Dispositivo Android conectado via USB (depuração ativada)
- [ ] OU Emulador rodando
- [ ] Script `compilar_e_testar.bat` executado com sucesso
- [ ] App "Pequenos Passos" instalado e aberto

---

## 🧪 TESTE 1: TASKFORMSCREEN - CRIAR NOVA TAREFA

### 1.1 Navegação
- [x] Abrir app → clicar em "Nova Tarefa" (ou equivalente)
- [x] TaskFormScreen abre corretamente

### 1.2 Campos Básicos
- [x] Campo "Título" aceita texto
- [x] Campo "Descrição" aceita texto (opcional)
- [x] Campo "Horário" aceita formato HH:mm
- [x] Seletor de estrelas (1-5) funciona

### 1.3 CategoryPicker ⭐ NOVO MVP-07
- [x] Dropdown de categoria abre ao clicar
- [x] Exibe 6 grupos de categorias
- [x] Exibe 27 categorias com emojis
- [x] Consegue selecionar uma categoria
- [x] Categoria selecionada aparece no campo
- [x] Validação: não permite salvar sem categoria

### 1.4 ImagePicker - Imagem da Tarefa ⭐ NOVO MVP-07
- [x] Botão "Câmera" presente
- [x] Botão "Galeria" presente
- [x] **Testar Galeria:**
  - [ ] Abre galeria de fotos
  - [ ] Consegue selecionar uma imagem
  - [ ] Preview da imagem aparece
  - [ ] Imagem não está cortada/distorcida
  - [ ] ✅ **Bug "recycled bitmap" RESOLVIDO** (não deve crashar)
- [x] **Testar Câmera:**
  - [ ] Abre app de câmera
  - [ ] Tira foto
  - [ ] Foto aparece no preview
- [ ] Botão "Remover imagem" funciona (se houver)

### 1.5 StepDialog - Adicionar Steps ⭐ NOVO MVP-07
- [x] Botão "Adicionar Step" abre dialog
- [x] Dialog exibe título "Adicionar Step 1"
- [x] Campo de título do step aceita texto
- [x] **CompactImagePicker funciona:**
  - [ ] Botões câmera/galeria presentes
  - [ ] Consegue adicionar imagem ao step
  - [ ] Preview aparece no dialog
- [x] **TimerInput funciona:**
  - [ ] Slider de duração aparece
  - [ ] Valores rápidos (5s, 15s, 30s, etc) funcionam
  - [ ] Consegue arrastar slider (5-600s)
  - [ ] Tempo exibido em formato legível (ex: "1 min 30 seg")
- [x] Botão "Confirmar" adiciona step
- [x] Botão "Cancelar" fecha dialog sem salvar

### 1.6 StepCard - Visualização de Steps
- [ ] Step adicionado aparece em card
- [ ] Título do step visível
- [ ] Miniatura da imagem aparece (se adicionada)
- [ ] Duração em segundos exibida (ex: "⏱️ 60s")
- [ ] Botão "Editar" abre dialog de edição
- [ ] Botão "Deletar" remove step
- [x] **Adicionar 3 steps** para testar lista completa

### 1.7 Validações ao Salvar
- [ ] Sem título: mostra erro "Título é obrigatório"
- [ ] Sem categoria: mostra erro "Categoria é obrigatória"
- [ ] Sem steps: mostra erro "Adicione pelo menos 1 step"
- [ ] Horário inválido: mostra erro
- [x] Com todos campos válidos: salva com sucesso

### 1.8 Salvamento
- [ ] Botão "Salvar" funciona
- [ ] Loading aparece durante salvamento
- [ ] Mensagem de sucesso aparece
- [x] Volta para tela anterior automaticamente

**✅ Status:** __________ (Passou/Falhou)  
**🐛 Bugs encontrados:** _________________________________

---

## 🧪 TESTE 2: TASKLISTSCREEN - VISUALIZAR TAREFAS

### 2.1 Navegação
- [x] Após criar tarefa, volta para lista
- [x] Tarefa recém-criada aparece na lista

### 2.2 TaskCard - Indicadores Visuais ⭐ NOVO MVP-07
- [x] **Emoji da categoria** aparece no card
- [x] **Nome da categoria** aparece (ex: "Higiene Pessoal")
- [x] **Título da tarefa** aparece
- [x] **Horário** aparece (ex: "🕐 08:00")
- [x] **Estrelas** aparecem (ex: "⭐⭐⭐")
- [x] **Contador de steps** aparece (ex: "📝 3 passos")
- [ ] **Contador de imagens** aparece (ex: "🖼️ × 2")
- [x] **Duração total** aparece formatada (ex: "⏱️ 3 min 30 seg")
- [x] Descrição aparece (se foi preenchida)
- [NÂO] Botão "▶️ Executar Tarefa" funciona

### 2.3 Lista de Tarefas
- [ ] Todas as tarefas criadas aparecem
- [ ] Ordenação está correta (por horário)
- [ ] Scroll funciona se houver muitas tarefas

### 2.4 Estados
- [ ] Estado vazio: mensagem "Nenhuma tarefa cadastrada" (se não houver tarefas)
- [ ] FAB "+" para adicionar nova tarefa funciona
- [ ] Loading aparece ao carregar (se aplicável)

**✅ Status:** __________ (Passou/Falhou)  
**🐛 Bugs encontrados:** _________________________________

---

## 🧪 TESTE 3: TASKEXECUTIONSCREEN - EXECUTAR TAREFA

### 3.1 Navegação
- [ ] Clicar em "▶️ Executar Tarefa" no card
- [ ] TaskExecutionScreen abre

### 3.2 Layout Geral
- [ ] TopBar exibe título da tarefa
- [ ] Botão voltar funciona
- [ ] Indicador de progresso aparece (ex: "Passo 1 de 3")

### 3.3 Conteúdo do Step Atual
- [ ] Imagem do step aparece (se tiver imagem)
- [ ] Título do step aparece em card
- [ ] Título é legível e bem formatado

### 3.4 CircularTimer ⭐ NOVO MVP-07
- [ ] Timer circular aparece no centro
- [ ] Tamanho adequado (~200dp)
- [ ] **Cores dinâmicas funcionam:**
  - [ ] Verde quando > 60% do tempo
  - [ ] Amarelo quando 30-60% do tempo
  - [ ] Vermelho quando < 30% do tempo
- [ ] **Animação:**
  - [ ] Círculo diminui suavemente
  - [ ] Sem travamentos ou lags
- [ ] **Display de tempo:**
  - [ ] Número de segundos aparece no centro
  - [ ] Atualiza a cada segundo
  - [ ] Legível e bem formatado

### 3.5 Controles de Timer ⚠️ (PODE ESTAR INCOMPLETO)
- [ ] Botão "Pausar" existe e funciona
- [ ] Botão "Retomar" existe e funciona
- [ ] Botão "Pular" existe e funciona
- [ ] Botão "+30s" existe e funciona (adicionar tempo)
- [ ] **Se NÃO existir:** anotar como pendência

### 3.6 Navegação entre Steps
- [ ] Quando timer chega a 0, avança automaticamente
- [ ] Ou dialog aparece com opções (+30s / Concluir)
- [ ] Próximo step carrega corretamente
- [ ] Imagem e timer do próximo step aparecem

### 3.7 Conclusão
- [ ] Último step executado: tarefa é marcada como concluída
- [ ] Volta para tela inicial/home
- [ ] Feedback visual de sucesso (opcional)

### 3.8 Som e Vibração ⚠️ (PODE ESTAR INCOMPLETO)
- [ ] Som toca ao finalizar timer
- [ ] Vibração acontece ao finalizar
- [ ] **Se NÃO existir:** anotar como pendência

**✅ Status:** __________ (Passou/Falhou)  
**🐛 Bugs encontrados:** _________________________________

---

## 🧪 TESTE 4: EDIÇÃO DE TAREFA

### 4.1 Abrir Edição
- [ ] TaskListScreen tem opção de editar tarefa
- [ ] Abre TaskFormScreen em modo edição
- [ ] Todos os campos carregam com valores salvos:
  - [ ] Título
  - [ ] Descrição
  - [ ] Horário
  - [ ] Estrelas
  - [ ] Categoria selecionada
  - [ ] Imagem da tarefa
  - [ ] Lista de steps completa

### 4.2 Editar Campos
- [ ] Consegue alterar título
- [ ] Consegue alterar categoria
- [ ] Consegue trocar imagem
- [ ] Consegue editar steps existentes
- [ ] Consegue adicionar novos steps
- [ ] Consegue deletar steps

### 4.3 Salvar Edição
- [ ] Botão "Salvar" salva alterações
- [ ] Volta para lista
- [ ] Alterações aparecem no card

**✅ Status:** __________ (Passou/Falhou)  
**🐛 Bugs encontrados:** _________________________________

---

## 🧪 TESTE 5: CASOS EXTREMOS

### 5.1 Performance
- [ ] App não trava com 10+ tarefas
- [ ] Scroll é fluido
- [ ] Imagens carregam rápido
- [ ] Animações são suaves

### 5.2 Imagens Grandes
- [ ] Foto de alta resolução (câmera): não crasha
- [ ] Imagem é redimensionada automaticamente
- [ ] Preview não demora muito para carregar

### 5.3 Steps Longos
- [ ] Tarefa com 10+ steps funciona
- [ ] Execução não trava
- [ ] Navegação entre steps fluida

### 5.4 Timers Extremos
- [ ] Timer de 5s (mínimo) funciona
- [ ] Timer de 600s (10 min - máximo) funciona
- [ ] Timer não trava ou pula segundos

### 5.5 Dark Mode
- [ ] Alternar para dark mode
- [ ] Todas as telas visíveis e legíveis
- [ ] Cores se adaptam corretamente

**✅ Status:** __________ (Passou/Falhou)  
**🐛 Bugs encontrados:** _________________________________

---

## 📊 RESUMO GERAL

### ✅ Funcionalidades Testadas e Aprovadas:
- [ ] TaskFormScreen: Criar tarefa
- [ ] TaskFormScreen: CategoryPicker
- [ ] TaskFormScreen: ImagePicker (galeria)
- [ ] TaskFormScreen: ImagePicker (câmera)
- [ ] TaskFormScreen: StepDialog com TimerInput
- [ ] TaskFormScreen: StepDialog com CompactImagePicker
- [ ] TaskFormScreen: Validações
- [ ] TaskListScreen: Cards com indicadores visuais
- [ ] TaskListScreen: Metadados (contadores, duração)
- [ ] TaskExecutionScreen: CircularTimer
- [ ] TaskExecutionScreen: Exibição de imagens
- [ ] Edição de tarefas

### ⚠️ Funcionalidades Pendentes/Incompletas:
- [ ] TaskExecutionScreen: Controles (pausar/retomar/pular)
- [ ] TaskExecutionScreen: Som e vibração
- [ ] TaskExecutionScreen: Dialog ao fim do timer
- [ ] Outras: _________________________________

### 🐛 Bugs Críticos Encontrados:
1. _______________________________________________
2. _______________________________________________
3. _______________________________________________

### 🐛 Bugs Menores Encontrados:
1. _______________________________________________
2. _______________________________________________

### 💡 Melhorias Sugeridas:
1. _______________________________________________
2. _______________________________________________

---

## 🎯 DECISÃO FINAL

**A Fase 3 está pronta para produção?**
- [ ] ✅ SIM - Tudo funcionando perfeitamente
- [ ] ⚠️ QUASE - Faltam pequenos ajustes (listar acima)
- [ ] ❌ NÃO - Bugs críticos impedem uso (listar acima)

**Próximos passos recomendados:**
_________________________________________________________________
_________________________________________________________________

---

**Testado por:** _______________  
**Data:** 20/10/2025  
**Tempo de teste:** _____ minutos

