# Como Acessar e Analisar Logs do Android

**Data:** 24/10/2025  
**Versão do App:** 1.11.2  
**Objetivo:** Descobrir por que as tarefas não estão sendo marcadas como completadas  

---

## 🔍 PASSOS PARA CAPTURAR LOGS

### Método 1: Via Terminal (Em Execução Agora)

Já iniciei o monitoramento de logs filtrado. Os logs aparecerão automaticamente quando você executar uma tarefa.

**Agora faça o seguinte:**

1. **No seu celular:** 
   - Abra o app Pequenos Passos
   - Vá em "Atividades"
   - Execute UMA tarefa completa (todos os steps)
   - Volte para "Atividades"

2. **Aguarde 10 segundos**

3. **Me avise** que completou a tarefa

Eu vou capturar os logs automaticamente e analisar o que está acontecendo.

---

## 📋 O QUE ESTOU MONITORANDO

Os logs que adicionei vão mostrar:

1. **TaskExecutionVM:**
   - Quando `completeTask()` é chamado
   - Valores de task.id, task.stars
   - Se currentTask é null ou não
   - Resultado da chamada ao repository

2. **TaskRepositoryImpl:**
   - Quando `markTaskAsCompleted()` é chamado
   - Valores de taskId, childId, starsEarned
   - Data e hora atuais (LocalDate e LocalDateTime)
   - Objeto TaskCompletion criado
   - Resultado do INSERT no banco
   - Exceções detalhadas se houver erro

3. **Possíveis Erros:**
   - "no such table: task_completions" → Tabela não foi criada
   - "UNIQUE constraint failed" → Tentando inserir duplicata
   - "FOREIGN KEY constraint failed" → childId ou taskId inválido
   - Problemas com TypeConverter (LocalDate/LocalDateTime)

---

## 🎯 PRÓXIMOS PASSOS

**AGUARDANDO VOCÊ EXECUTAR UMA TAREFA NO CELULAR...**

Assim que você executar, os logs vão aparecer aqui e eu vou analisar o problema.

---

## ⚙️ Comandos Úteis (Para Referência)

### Ver todos os logs do app
```powershell
adb logcat | Select-String "pequenospassos"
```

### Limpar logs
```powershell
adb logcat -c
```

### Ver logs com timestamp
```powershell
adb logcat -v time
```

### Salvar logs em arquivo
```powershell
adb logcat -v time > logs.txt
```

### Filtrar por prioridade (Error, Warning, Info)
```powershell
adb logcat *:E  # Apenas erros
adb logcat *:W  # Warnings e erros
```

---

**Status Atual:** ⏳ Aguardando execução de tarefa no dispositivo...

