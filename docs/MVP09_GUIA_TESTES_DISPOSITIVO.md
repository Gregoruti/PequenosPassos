# MVP09 - Guia de Testes no Dispositivo

**Versão do App:** 1.11.0  
**Data:** 24/10/2025  
**Funcionalidades:** Fases 3-4 - Controle Diário de Tarefas + Sistema de Estrelas  

---

## ✅ VERIFICAÇÕES INICIAIS

### 1. Confirmar Versão Instalada
- [ ] Abrir o app
- [ ] **Verificar na SplashScreen:** Deve aparecer "Versão: 1.11.0"
- [ ] Se aparecer versão diferente, desinstalar app e reinstalar

---

## 🧪 TESTES DE FUNCIONALIDADE

### TESTE 1: Primeira Execução - Verificar Contador de Estrelas

**Objetivo:** Verificar se o contador de estrelas aparece na tela de Atividades

**Passos:**
1. Na tela inicial (HomeScreen), clicar em **"Atividades"**
2. **VERIFICAR NO TOPO DA TELA:**
   ```
   ⬅ Atividades
      ⭐ 0 estrelas hoje
   ```
3. ✅ **Resultado Esperado:** Contador mostra "⭐ 0 estrelas hoje"

**❌ Se não aparecer:** A implementação não foi instalada corretamente

---

### TESTE 2: Executar Tarefa Completa

**Objetivo:** Completar uma tarefa e verificar se ela é marcada como concluída

**Pré-requisito:** Ter pelo menos 1 tarefa cadastrada (ex: "Escovar os Dentes")

**Passos:**

1. **Na tela de Atividades, verificar tarefa disponível:**
   - Botão deve estar: **"▶️ Executar Tarefa"**
   - Card deve ter fundo normal (branco/claro)
   - Título SEM checkmark (ex: "Escovar os Dentes")

2. **Clicar em "▶️ Executar Tarefa"**

3. **Completar TODOS os steps da tarefa:**
   - Passar por cada passo
   - Aguardar ou pular os timers
   - Ir até o último step

4. **Ao completar último step:**
   - Deve aparecer tela de conclusão com mensagem
   - TTS deve falar: "NOME DA CRIANÇA, você tirou nota 10!" (ou similar)

5. **Voltar para tela de Atividades**

6. **VERIFICAR MUDANÇAS:**

   **A) Contador de Estrelas (topo da tela):**
   ```
   ⭐ 3 estrelas hoje  (ou 1, 2, 4, 5 conforme estrelas da tarefa)
   ```

   **B) Card da Tarefa Completada:**
   - ✅ **Título com checkmark:** "✅ Escovar os Dentes"
   - 🎨 **Fundo diferente:** Card mais acinzentado/opaco
   - 🔒 **Botão desabilitado:** "✅ Completada Hoje" (não clicável)
   - 📝 **Texto mais claro** (onSurfaceVariant)

**✅ Resultado Esperado:**
- Contador de estrelas incrementado
- Tarefa marcada visualmente como completada
- Botão desabilitado
- Não é possível executar novamente

**❌ Se não funcionar:** Reportar qual parte falhou

---

### TESTE 3: Tentar Executar Tarefa Já Completada

**Objetivo:** Verificar bloqueio de execução

**Passos:**
1. Com uma tarefa já completada (do TESTE 2)
2. Tentar clicar no botão "✅ Completada Hoje"
3. ✅ **Resultado Esperado:** Botão não responde (está desabilitado)

---

### TESTE 4: Executar Múltiplas Tarefas

**Objetivo:** Verificar acúmulo de estrelas

**Pré-requisito:** Ter 3+ tarefas cadastradas

**Passos:**
1. Executar **Tarefa 1** (ex: 3 estrelas) → Verificar contador: "⭐ 3 estrelas hoje"
2. Executar **Tarefa 2** (ex: 2 estrelas) → Verificar contador: "⭐ 5 estrelas hoje"
3. Executar **Tarefa 3** (ex: 4 estrelas) → Verificar contador: "⭐ 9 estrelas hoje"

**✅ Resultado Esperado:**
- Contador incrementa corretamente
- Todas as 3 tarefas aparecem como completadas
- Todas com checkmark e botões desabilitados

---

### TESTE 5: Verificar Persistência

**Objetivo:** Garantir que conclusões são salvas no banco

**Passos:**
1. Completar 2-3 tarefas
2. Verificar contador de estrelas (ex: "⭐ 7 estrelas hoje")
3. **FECHAR o app completamente** (não só minimizar)
4. **Reabrir o app**
5. Ir para "Atividades"

**✅ Resultado Esperado:**
- Contador de estrelas mantém valor (ex: "⭐ 7 estrelas hoje")
- Tarefas completadas continuam com checkmark
- Botões continuam desabilitados

**❌ Se zerar:** Problema no banco de dados

---

### TESTE 6: Verificar Tarefas Disponíveis vs Completadas

**Objetivo:** Confirmar que apenas tarefas não completadas são executáveis

**Passos:**
1. Na lista de atividades, verificar visualmente:
   - **Tarefas disponíveis:** Fundo claro + "▶️ Executar Tarefa"
   - **Tarefas completadas:** Fundo acinzentado + "✅ Completada Hoje"

2. Contar quantas de cada tipo

**✅ Resultado Esperado:**
- Separação visual clara entre disponíveis e completadas
- Apenas tarefas disponíveis podem ser executadas

---

### TESTE 7: TTS nas Mensagens de Conclusão

**Objetivo:** Verificar se TTS lê mensagem de sucesso ao completar tarefa

**Pré-requisito:** Volume do celular ligado

**Passos:**
1. Executar uma tarefa completa
2. Ao chegar na tela de conclusão
3. **OUVIR:** TTS deve falar algo como:
   - "NOME DA CRIANÇA, você tirou nota 10!"
   - "NOME DA CRIANÇA, você é incrível!"
   - (Mensagem aleatória de reforço positivo)

**✅ Resultado Esperado:**
- TTS fala a mensagem de conclusão com o nome da criança
- Áudio claro e compreensível

---

## 🐛 TESTES DE EDGE CASES

### TESTE 8: Executar Tarefa Parcialmente e Voltar

**Objetivo:** Verificar que tarefa só é marcada como completa ao FINALIZAR

**Passos:**
1. Começar a executar uma tarefa
2. Completar 2-3 steps (não todos)
3. **Voltar** (botão de voltar ou navegação)
4. Verificar lista de atividades

**✅ Resultado Esperado:**
- Tarefa NÃO está marcada como completada
- Botão continua "▶️ Executar Tarefa"
- Contador de estrelas NÃO incrementou

---

### TESTE 9: Múltiplas Execuções no Mesmo Dia

**Objetivo:** Confirmar que não há duplicação

**Passos:**
1. Completar uma tarefa (ex: 3 estrelas)
2. Contador deve mostrar: "⭐ 3 estrelas hoje"
3. Tentar executar a mesma tarefa novamente
4. Verificar que está bloqueada
5. Fechar e reabrir app
6. Verificar que continua bloqueada

**✅ Resultado Esperado:**
- Impossível executar tarefa duas vezes no mesmo dia
- Estrelas não duplicam

---

## 📊 CHECKLIST GERAL DE VALIDAÇÃO

### Interface Visual
- [ ] Contador de estrelas aparece no topo da tela de Atividades
- [ ] Formato correto: "⭐ X estrelas hoje"
- [ ] Checkmark (✅) aparece no título de tarefas completadas
- [ ] Card de tarefa completada tem fundo diferenciado
- [ ] Botão muda para "✅ Completada Hoje" e fica desabilitado
- [ ] Texto do título fica mais claro (cinza) quando completada

### Funcionalidade
- [ ] Tarefas podem ser executadas completamente
- [ ] Ao completar, tarefa é marcada como concluída
- [ ] Contador de estrelas incrementa corretamente
- [ ] Impossível executar tarefa completada novamente
- [ ] Dados persistem após fechar e reabrir app
- [ ] TTS lê mensagem de conclusão com nome da criança

### Performance
- [ ] App não trava ao executar tarefa
- [ ] Navegação entre telas é fluida
- [ ] Atualização da lista de atividades é imediata após conclusão
- [ ] Sem erros ou crashes

---

## 🔍 O QUE NÃO ESTÁ IMPLEMENTADO (Não Testar)

### MVP-10 (Futuro)
- ❌ Botão "Histórico" (ainda é "Debug")
- ❌ Botão "Zerar Tarefas de Hoje" (Debug)
- ❌ Botão "Zerar Estrelas" (Debug)
- ❌ Tela de estatísticas
- ❌ Gráfico de progresso
- ❌ Dias seguidos completando tarefas
- ❌ Percentual de conclusão

### HomeScreen Melhorada (Futuro)
- ❌ Foto de perfil na tela inicial
- ❌ Mensagem "Você tem X atividades para hoje!"

---

## 📝 RELATÓRIO DE TESTES

**Preencher após testar:**

| Teste | Status | Observações |
|-------|--------|-------------|
| TESTE 1: Contador de Estrelas | ⬜ PASS / ⬜ FAIL | |
| TESTE 2: Executar Tarefa Completa | ⬜ PASS / ⬜ FAIL | |
| TESTE 3: Bloqueio de Reexecução | ⬜ PASS / ⬜ FAIL | |
| TESTE 4: Múltiplas Tarefas | ⬜ PASS / ⬜ FAIL | |
| TESTE 5: Persistência | ⬜ PASS / ⬜ FAIL | |
| TESTE 6: Visual Disponíveis vs Completadas | ⬜ PASS / ⬜ FAIL | |
| TESTE 7: TTS Conclusão | ⬜ PASS / ⬜ FAIL | |
| TESTE 8: Execução Parcial | ⬜ PASS / ⬜ FAIL | |
| TESTE 9: Sem Duplicação | ⬜ PASS / ⬜ FAIL | |

---

## 🚨 PROBLEMAS CONHECIDOS E WORKAROUNDS

### Problema 1: Versão não atualiza
**Sintoma:** SplashScreen mostra versão antiga (não 1.11.0)  
**Solução:**
1. Desinstalar app completamente do celular
2. Reconectar celular ao PC
3. Executar: `.\gradlew.bat installDebug`

### Problema 2: Contador não aparece
**Sintoma:** Topo da tela só mostra "Atividades" sem contador  
**Causa:** Implementação não instalada corretamente  
**Solução:** Verificar versão na SplashScreen

### Problema 3: Tarefa não marca como completada
**Sintoma:** Após completar, tarefa continua sem checkmark  
**Debug:**
1. Verificar logs do app
2. Confirmar que TODOS os steps foram completados
3. Verificar se banco de dados está funcionando

---

## 📸 EVIDÊNCIAS VISUAIS

**Antes de Completar Tarefa:**
```
┌─────────────────────────────────────┐
│  ⬅ Atividades                      │
│     ⭐ 0 estrelas hoje              │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│ 🖼️ [Imagem]  Escovar os Dentes      │
│              🕐 08:00                │
│              Higiene Pessoal         │
│              ⭐⭐⭐                   │
│              📝 5  🖼️ 3  ⏱️ 3 min  │
│                                      │
│  [ ▶️ Executar Tarefa ]             │
└─────────────────────────────────────┘
```

**Depois de Completar Tarefa:**
```
┌─────────────────────────────────────┐
│  ⬅ Atividades                      │
│     ⭐ 3 estrelas hoje              │ ← INCREMENTOU
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│ 🖼️ [Imagem]  ✅ Escovar os Dentes   │ ← CHECKMARK
│              🕐 08:00                │
│              Higiene Pessoal         │
│              ⭐⭐⭐                   │
│              📝 5  🖼️ 3  ⏱️ 3 min  │
│                                      │
│  [ ✅ Completada Hoje ] (desabilitado) │ ← MUDOU
└─────────────────────────────────────┘
```

---

## ✅ CONCLUSÃO

Se **TODOS os 9 testes passarem**, as Fases 3-4 do MVP-09 estão **100% funcionais** e você pode:

1. ✅ Fazer commit da versão 1.11.0
2. ✅ Prosseguir para MVP-10 (Histórico e Estatísticas)
3. ✅ Documentar sucesso da implementação

Se **algum teste falhar**, documente qual falhou e qual o comportamento observado para debug.

**Boa sorte nos testes! 🚀**

