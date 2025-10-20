# 🐛 CORREÇÕES MVP-07 v1.9.0 - Problemas Identificados

**Data:** 20/10/2025  
**Versão:** 1.9.0 (incrementada de 1.8.1)  
**Branch:** `feature/mvp-07-fase3-integracao`

---

## 📋 PROBLEMAS REPORTADOS

### 1. ✅ **Versionamento não estava visível para debug**
**Problema:** Difícil acompanhar qual versão está instalada durante testes.
**Solução:**
- ✅ Incrementada versão: `1.8.1` → `1.9.0`
- ✅ versionCode: `10` → `11`
- ✅ HomeScreen já exibe versão no rodapé: "Versão 1.9.0"

### 2. ✅ **Script "compilar_e_testar.bat" não instalava efetivamente**
**Problema:** Script não forçava reinstalação quando APK já existia.
**Solução:**
- ✅ Adicionado fallback: se instalação falhar, desinstala e reinstala
- ✅ Comando: `adb uninstall com.example.pequenospassos` antes de reinstalar

### 3. ⚠️ **Imagem do step não aparece durante execução**
**Problema:** Imagens dos steps não aparecem na TaskExecutionScreen.
**Status:** EM INVESTIGAÇÃO
**Ações tomadas:**
- ✅ Adicionados logs de debug no ViewModel:
  - Verifica se `imageUrl` está sendo carregada
  - Verifica se `durationSeconds` está correto
- ✅ Adicionados logs na TaskExecutionScreen:
  - `onSuccess`: Log quando imagem carrega
  - `onError`: Log de erros no carregamento
  - Debug do valor de `imageUrl` para cada step

**Próxima ação:** Compilar e analisar logs no Logcat

### 4. ⚠️ **Timer sempre em 60 segundos (ignora durationSeconds)**
**Problema:** Independente do tempo configurado, timer sempre mostra 60s.
**Status:** EM INVESTIGAÇÃO
**Análise realizada:**
- ✅ ViewModel está correto: carrega `step.durationSeconds`
- ✅ `loadTask()` define: `remainingSeconds = steps[0].durationSeconds`
- ✅ `nextStep()` define: `remainingSeconds = nextStep.durationSeconds`
- ❓ **Hipótese:** Steps não estão salvando `durationSeconds` no banco

**Próxima ação:** Verificar se SaveTaskUseCase está salvando os steps com durationSeconds correto

---

## 🔍 LOGS DE DEBUG ADICIONADOS

### No TaskExecutionViewModel:
```kotlin
steps.forEachIndexed { index, step ->
    println("TaskExecutionVM: Step ${index + 1} - title: '${step.title}', imageUrl: '${step.imageUrl}', duration: ${step.durationSeconds}s")
}
```

### Na TaskExecutionScreen:
```kotlin
AsyncImage(
    model = state.currentStep?.imageUrl,
    onError = { 
        println("TaskExecution: Erro ao carregar imagem: ${state.currentStep?.imageUrl}")
    },
    onSuccess = {
        println("TaskExecution: Imagem carregada com sucesso: ${state.currentStep?.imageUrl}")
    }
)

// Else:
println("TaskExecution: Step ${state.currentStepIndex + 1} - imageUrl: '${state.currentStep?.imageUrl}'")
```

---

## 📊 HIPÓTESES DOS PROBLEMAS

### Para Imagem não Aparecer:
**Possíveis causas:**
1. ❓ imageUrl está null/vazia no banco
2. ❓ Path da imagem está incorreto
3. ❓ Permissão para acessar arquivo
4. ❓ Arquivo foi deletado após salvar

### Para Timer Fixo em 60s:
**Possíveis causas:**
1. ❓ SaveTaskUseCase não está salvando durationSeconds
2. ❓ StepDialog não está passando durationSeconds para o Step
3. ❓ Valor padrão (60s) sempre sendo usado

---

## 🧪 PRÓXIMOS PASSOS PARA DIAGNÓSTICO

### 1. Compilar e Instalar (COM LOGS)
```cmd
.\compilar_e_testar.bat
```

### 2. Executar App e Verificar Logcat
```cmd
adb logcat | findstr "TaskExecution"
```

### 3. Criar Tarefa de Teste
- Adicionar tarefa nova
- Criar 3 steps:
  - Step 1: COM imagem, timer 30s
  - Step 2: COM imagem, timer 90s
  - Step 3: SEM imagem, timer 120s

### 4. Executar e Anotar Logs
No Logcat, verificar:
- ✅ "TaskExecutionVM: Step X" → confirmar imageUrl e duration
- ✅ "TaskExecution: Erro ao carregar imagem" → se houver erro
- ✅ "TaskExecution: Imagem carregada com sucesso" → se carregar

### 5. Ações Baseadas nos Logs

**SE imageUrl está vazia no log do ViewModel:**
→ Problema no salvamento (SaveTaskUseCase ou StepDialog)

**SE imageUrl está presente MAS erro ao carregar:**
→ Problema no path/arquivo da imagem

**SE durationSeconds sempre 60 no log do ViewModel:**
→ Problema no salvamento do durationSeconds

**SE durationSeconds correto no ViewModel MAS sempre 60 na tela:**
→ Bug na UI (improvável, pois código parece correto)

---

## 🔧 CORREÇÕES ADICIONAIS PLANEJADAS

### Se imageUrl estiver vazia:
- [ ] Verificar TaskFormViewModel.saveTask()
- [ ] Verificar se Step.imageUrl está sendo passado
- [ ] Verificar SaveTaskUseCase

### Se durationSeconds for sempre 60:
- [ ] Verificar StepDialog
- [ ] Verificar se TimerInput está funcionando
- [ ] Verificar SaveTaskUseCase

---

## 📝 CHECKLIST DE VALIDAÇÃO

Após correções:
- [ ] Criar tarefa com 3 steps
- [ ] Step 1: imagem + 30s
- [ ] Step 2: imagem + 90s
- [ ] Step 3: sem imagem + 120s
- [ ] Executar tarefa
- [ ] Verificar se imagens aparecem nos steps 1 e 2
- [ ] Verificar se timers são: 30s, 90s, 120s
- [ ] Verificar se step 3 não mostra imagem (correto)

---

**Status:** Aguardando compilação e análise de logs para próxima ação

