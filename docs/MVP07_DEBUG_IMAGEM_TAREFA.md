# 🖼️ DEBUG: Problema com Miniatura de Imagem da Tarefa

**Data:** 20/10/2025  
**Versão:** 1.9.2  
**Status:** 🔍 Em Investigação

---

## 🐛 PROBLEMA REPORTADO

**Sintoma:** Apenas o ícone da categoria aparece no lugar da miniatura da imagem da tarefa.

**Comportamento Esperado:**
- Miniatura da **imagem "Capa"** da tarefa (80dp × 80dp) à esquerda do card
- Fallback para emoji da categoria **SOMENTE** se a imagem não foi inserida

**Comportamento Atual:**
- Sempre exibe o emoji da categoria
- A imagem da tarefa não aparece, mesmo quando foi inserida

---

## 🔍 ANÁLISE INICIAL

### Possíveis Causas:

1. **❓ imageUrl não está sendo salvo no banco**
   - TaskFormViewModel pode não estar passando `imageUrl` corretamente
   - SaveTaskUseCase pode não estar salvando o campo

2. **❓ imageUrl está null/vazio ao recuperar**
   - TaskListViewModel pode não estar carregando o campo
   - Query do banco pode estar omitindo `imageUrl`

3. **❓ Path da imagem está incorreto**
   - Caminho relativo vs absoluto
   - Permissões de leitura do arquivo

4. **❓ Coil não está carregando a imagem**
   - Erro silencioso de carregamento
   - Formato de URI incorreto

---

## ✅ CORREÇÕES IMPLEMENTADAS

### 1. Removida Verificação Problemática

**ANTES:**
```kotlin
if (!task.imageUrl.isNullOrEmpty() && File(task.imageUrl).exists()) {
    AsyncImage(...)
}
```

**DEPOIS:**
```kotlin
if (!task.imageUrl.isNullOrEmpty()) {
    AsyncImage(...)
}
```

**Motivo:** A verificação `File(task.imageUrl).exists()` pode falhar por:
- Problemas de permissão
- Path incorreto
- Verificação síncrona no thread principal

O **Coil** (AsyncImage) já lida automaticamente com arquivos locais.

---

### 2. Adicionados Logs de Debug Detalhados

**Arquivo:** `TaskListScreen.kt`

**Logs implementados:**
```kotlin
// 1. Verificar valor de imageUrl
println("TaskListScreen: Task '${task.title}' - imageUrl: '${task.imageUrl}'")

// 2. Verificar se arquivo existe
val imageFile = File(task.imageUrl)
println("TaskListScreen: Verificando arquivo - exists: ${imageFile.exists()}, path: ${imageFile.absolutePath}")

// 3. Callback de sucesso
onSuccess = {
    println("TaskListScreen: Imagem carregada com sucesso para '${task.title}'")
}

// 4. Callback de erro
onError = { error ->
    println("TaskListScreen: Erro ao carregar imagem para '${task.title}': ${error.result.throwable?.message}")
}
```

---

## 📱 INSTRUÇÕES PARA TESTE

### Passo 1: Instalar APK Manualmente

**Opção A: Via Android Studio**
1. Abrir Android Studio
2. Menu: Run > Run 'app'
3. Selecionar dispositivo conectado

**Opção B: Via Prompt de Comando (não PowerShell)**
1. Abrir **CMD** (não PowerShell)
2. Navegar para a pasta do projeto:
   ```cmd
   cd D:\Softwares\PequenosPassos
   ```
3. Instalar APK:
   ```cmd
   adb install -r app\build\outputs\apk\debug\app-debug.apk
   ```

**Opção C: Via Script Batch**
```cmd
.\compilar_e_instalar.bat
```

---

### Passo 2: Capturar Logs do Logcat

**Abrir CMD** e executar:

```cmd
adb logcat -c
adb logcat | findstr "TaskListScreen"
```

Ou salvar em arquivo:
```cmd
adb logcat | findstr "TaskListScreen" > logs_imagem.txt
```

---

### Passo 3: Testar Fluxo Completo

1. **Criar uma nova tarefa:**
   - HomeScreen > "Edição de Tarefas" > FAB (+)
   - Preencher título: "Teste Imagem"
   - Selecionar categoria: "🧼 Higiene"
   - **IMPORTANTE:** Selecionar imagem "Capa" (botão de câmera/galeria)
   - Adicionar pelo menos 1 step
   - Salvar

2. **Verificar na lista:**
   - HomeScreen > "Atividades"
   - Procurar a tarefa "Teste Imagem"
   - **Observar:** Aparece imagem ou emoji?

3. **Verificar logs no CMD:**
   - Procurar mensagens como:
     ```
     TaskListScreen: Task 'Teste Imagem' - imageUrl: '[VALOR]'
     TaskListScreen: Verificando arquivo - exists: [true/false]
     TaskListScreen: Imagem carregada com sucesso / Erro ao carregar
     ```

---

## 📊 EXEMPLOS DE LOGS ESPERADOS

### ✅ Caso de Sucesso (Imagem Carregada)
```
TaskListScreen: Task 'Escovar dentes' - imageUrl: '/storage/emulated/0/Android/data/com.example.pequenospassos/files/Pictures/task_1729456789.jpg'
TaskListScreen: Verificando arquivo - exists: true, path: /storage/emulated/0/...
TaskListScreen: Imagem carregada com sucesso para 'Escovar dentes'
```

### ❌ Caso de Erro (imageUrl null)
```
TaskListScreen: Task 'Escovar dentes' - imageUrl: 'null'
TaskListScreen: Usando fallback (emoji) para 'Escovar dentes'
```

### ❌ Caso de Erro (Arquivo não existe)
```
TaskListScreen: Task 'Escovar dentes' - imageUrl: '/storage/...'
TaskListScreen: Verificando arquivo - exists: false, path: /storage/...
TaskListScreen: Erro ao carregar imagem para 'Escovar dentes': [mensagem de erro]
```

---

## 🔍 PRÓXIMAS AÇÕES BASEADAS NOS LOGS

### Se `imageUrl: 'null'` ou `imageUrl: ''`

**Problema:** Imagem não está sendo salva no banco.

**Verificar:**
1. TaskFormViewModel está passando `imageUrl` para SaveTaskUseCase?
2. SaveTaskUseCase está salvando `imageUrl` na entidade Task?
3. TaskDAO está inserindo o campo `imageUrl` corretamente?

**Correção:**
- Verificar código do TaskFormViewModel
- Adicionar logs no SaveTaskUseCase
- Verificar schema da tabela `tasks`

---

### Se `imageUrl: '/path/to/image.jpg'` mas `exists: false`

**Problema:** Path da imagem está incorreto ou arquivo foi deletado.

**Verificar:**
1. Como está sendo gerado o path no ImagePicker?
2. Arquivo está sendo salvo corretamente?
3. Permissões de leitura/escrita?

**Correção:**
- Revisar código do ImagePicker
- Verificar pasta de destino das imagens
- Testar permissões de storage

---

### Se `exists: true` mas erro ao carregar

**Problema:** Coil não consegue carregar o arquivo.

**Verificar:**
1. Formato do URI está correto?
2. Permissões do app para ler o arquivo?
3. Coil está configurado corretamente?

**Correção:**
- Verificar se precisa converter para `Uri.fromFile()`
- Adicionar permissões no Manifest
- Verificar configuração do Coil

---

## 🛠️ CÓDIGO ATUAL (TaskListScreen)

**Arquivo:** `presentation/screens/tasklist/TaskListScreen.kt`

**Linhas 183-213 (aproximadamente):**
```kotlin
// Miniatura da imagem da tarefa (à esquerda)
println("TaskListScreen: Task '${task.title}' - imageUrl: '${task.imageUrl}'")

if (!task.imageUrl.isNullOrEmpty()) {
    val imageFile = File(task.imageUrl)
    println("TaskListScreen: Verificando arquivo - exists: ${imageFile.exists()}, path: ${imageFile.absolutePath}")
    
    AsyncImage(
        model = task.imageUrl,
        contentDescription = "Imagem da tarefa ${task.title}",
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp),
        contentScale = ContentScale.Crop,
        onSuccess = {
            println("TaskListScreen: Imagem carregada com sucesso para '${task.title}'")
        },
        onError = { error ->
            println("TaskListScreen: Erro ao carregar imagem para '${task.title}': ${error.result.throwable?.message}")
        }
    )
} else {
    println("TaskListScreen: Usando fallback (emoji) para '${task.title}'")
    // Fallback: Box com emoji da categoria
    Surface(
        modifier = Modifier.size(80.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = category.emoji,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}
```

---

## 📝 INFORMAÇÕES NECESSÁRIAS

Para continuar o debug, precisamos saber:

1. **O que os logs mostram?**
   - Qual o valor de `imageUrl`?
   - O arquivo existe?
   - Qual o erro ao carregar (se houver)?

2. **A imagem foi salva durante a criação?**
   - Preview apareceu no TaskFormScreen?
   - Mensagem de "Tarefa salva com sucesso"?

3. **Tipo de imagem testada:**
   - Câmera ou galeria?
   - Formato (JPG/PNG)?
   - Tamanho aproximado?

---

## 🎯 OBJETIVO FINAL

**TaskListScreen (Atividades) deve exibir:**
```
┌────────────────────────────────────┐
│ ┌────┐                             │
│ │🖼️  │ Escovar dentes    🕐 08:30  │ ← Miniatura 80dp
│ │foto│ Higiene          ⭐⭐⭐⭐⭐   │   da imagem
│ └────┘ 📝 3  🖼️ 3  ⏱️ 4min 30s     │
│        ▶️ Executar Tarefa          │
└────────────────────────────────────┘
```

**Se imagem não foi inserida:**
```
┌────────────────────────────────────┐
│ ┌────┐                             │
│ │ 🧼 │ Lavar mãos        🕐 12:00  │ ← Fallback:
│ │    │ Higiene            ⭐⭐⭐    │   emoji 
│ └────┘ 📝 2  ⏱️ 1min               │   categoria
│        ▶️ Executar Tarefa          │
└────────────────────────────────────┘
```

---

## ✅ STATUS ATUAL

- ✅ Código compilado com sucesso (v1.9.2)
- ✅ Logs de debug adicionados
- ✅ Verificação problemática removida
- ⏳ **AGUARDANDO TESTE MANUAL E LOGS**

---

## 📞 PRÓXIMO PASSO

**POR FAVOR, EXECUTE:**

1. Instalar APK (via CMD, Android Studio ou batch)
2. Criar tarefa com imagem
3. Verificar lista de atividades
4. Capturar e enviar os logs

Com os logs, poderei identificar exatamente onde está o problema e implementar a correção específica.

