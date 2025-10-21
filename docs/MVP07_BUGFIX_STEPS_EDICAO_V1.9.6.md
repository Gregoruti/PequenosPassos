# 🐛 BUGFIX REPORT - v1.9.6

**Projeto:** Pequenos Passos  
**Versão:** 1.9.6 (versionCode 16)  
**Data:** 21/10/2025  
**Tipo:** BUGFIX - Edição de Steps  
**Prioridade:** 🔴 ALTA  
**Status:** ✅ RESOLVIDO

---

## 📋 RESUMO EXECUTIVO

Corrigido bug crítico que impedia a edição de steps em tarefas existentes, gerando erro de constraint de chave primária no SQLite. A correção envolveu modificações no `SaveTaskUseCase` e `TaskFormViewModel` para diferenciar corretamente entre criação e edição de tarefas.

---

## 🐛 BUG #1: UNIQUE Constraint Failed ao Editar Steps

### Descrição do Problema
Ao editar uma tarefa existente e salvar alterações nos steps, o app exibia o erro:
```
Erro ao salvar: UNIQUE constraint failed: steps.id 
(code 1555 SQLITE_Constraint_primarykey[1555])
```

### Impacto
- 🔴 **Severidade:** CRÍTICA
- 🎯 **Afetava:** Edição de tarefas existentes
- 👥 **Usuários:** Todos que tentassem editar steps de tarefas
- 📊 **Frequência:** 100% ao editar steps

### Causa Raiz
1. **`SaveTaskUseCase`** sempre fazia INSERT de steps, mesmo quando eram edições
2. Steps editados mantinham seus IDs originais do banco de dados
3. Tentar inserir step com ID já existente causava violação de chave primária
4. Não havia distinção entre criar nova tarefa vs editar tarefa existente

### Análise Técnica

**Arquivo Afetado:** `SaveTaskUseCase.kt`
```kotlin
// ANTES (❌ Bug)
suspend operator fun invoke(
    title: String,
    // ... outros parâmetros
    steps: List<Step> = emptyList()
): AppResult<Long> {
    // ...
    steps.forEachIndexed { index, step ->
        val stepToSave = step.copy(taskId = taskId, order = index)
        stepRepository.insertStep(stepToSave).getOrThrow() // ❌ Sempre INSERT
    }
}
```

**Problema:** Steps editados tinham `id > 0`, mas usávamos `insertStep()` que falhava.

---

## ✅ SOLUÇÃO IMPLEMENTADA

### 1. Modificado `SaveTaskUseCase.kt`

**Mudança 1:** Adicionar parâmetro `taskId` opcional
```kotlin
suspend operator fun invoke(
    taskId: Long? = null,  // ✅ NOVO: null = criar, > 0 = editar
    title: String,
    description: String = "",
    // ... outros parâmetros
)
```

**Mudança 2:** Diferenciar criação vs edição
```kotlin
val task = Task(
    id = taskId ?: 0,  // ✅ Usar taskId se fornecido
    title = title.trim(),
    // ...
)

val finalTaskId = if (taskId != null && taskId > 0) {
    // Edição: UPDATE
    taskRepository.updateTask(task).getOrThrow()
    taskId
} else {
    // Criação: INSERT
    taskRepository.insertTask(task).getOrThrow()
}
```

**Mudança 3:** Deletar steps antigos antes de inserir novos (na edição)
```kotlin
// Deletar steps antigos (se for edição) para evitar conflitos
if (taskId != null && taskId > 0) {
    stepRepository.deleteStepsByTask(finalTaskId).getOrThrow()
}

// Inserir steps novos (sempre insert após deletar os antigos)
steps.forEachIndexed { index, step ->
    if (step.title.isNotBlank()) {
        val stepToSave = step.copy(
            id = 0,  // ✅ Forçar ID = 0 para criar novo registro
            taskId = finalTaskId,
            order = index
        )
        stepRepository.insertStep(stepToSave).getOrThrow()
    }
}
```

### 2. Modificado `TaskFormViewModel.kt`

**Mudança:** Passar `taskId` ao use case quando for edição
```kotlin
val result = saveTaskUseCase(
    taskId = currentState.taskId,  // ✅ null para criar, > 0 para editar
    title = currentState.title,
    description = currentState.description,
    // ... outros parâmetros
    steps = stepsToSave
)
```

---

## 🧪 VALIDAÇÃO

### Testes Manuais Realizados

#### ✅ Teste 1: Criar Nova Tarefa
- **Ação:** Criar tarefa com 3 steps
- **Resultado:** ✅ Sucesso - Tarefa criada com todos steps

#### ✅ Teste 2: Editar Tarefa - Modificar Step
- **Ação:** Editar tarefa existente, alterar texto de um step
- **Resultado:** ✅ Sucesso - Step atualizado sem erro

#### ✅ Teste 3: Editar Tarefa - Remover Step
- **Ação:** Remover step de tarefa existente
- **Resultado:** ✅ Sucesso - Step removido corretamente

#### ✅ Teste 4: Editar Tarefa - Adicionar Step
- **Ação:** Adicionar novo step a tarefa existente
- **Resultado:** ✅ Sucesso - Step adicionado corretamente

#### ✅ Teste 5: Editar Tarefa - Reordenar Steps
- **Ação:** Alterar ordem dos steps
- **Resultado:** ✅ Sucesso - Ordem mantida corretamente

### Compilação
```bash
> gradlew.bat assembleDebug
BUILD SUCCESSFUL in 41s
44 actionable tasks: 10 executed, 34 up-to-date
```

---

## 📊 ARQUIVOS MODIFICADOS

### Core Changes
1. **`SaveTaskUseCase.kt`** - 147 linhas
   - Adicionado parâmetro `taskId` opcional
   - Implementada lógica de update vs insert
   - Deleção de steps antigos antes de inserir novos
   - Documentação atualizada

2. **`TaskFormViewModel.kt`** - 218 linhas
   - Passa `taskId` ao `SaveTaskUseCase`
   - Mantém estado correto entre criação e edição

### Build Configuration
3. **`app/build.gradle.kts`**
   - `versionCode`: 15 → **16**
   - `versionName`: 1.9.5 → **1.9.6**

---

## 🎯 BENEFÍCIOS

### Funcionalidade
- ✅ Edição de steps agora funciona perfeitamente
- ✅ Sem erros de constraint do SQLite
- ✅ Steps mantêm ordem correta
- ✅ Suporte completo a CRUD de steps

### Qualidade
- ✅ Código mais robusto e explícito
- ✅ Separação clara entre criar e editar
- ✅ Melhor tratamento de erros
- ✅ Logs para debug

### Experiência do Usuário
- ✅ Usuário pode editar tarefas sem problemas
- ✅ Mensagens de erro claras
- ✅ Feedback visual adequado

---

## 📚 COMPATIBILIDADE

### Versões Anteriores
- ✅ Compatível com banco de dados v1.9.5
- ✅ Não requer migração de dados
- ✅ Tasks antigas continuam funcionando

### Dependências
- ✅ Nenhuma dependência adicionada
- ✅ Usa APIs existentes do Room
- ✅ Compatível com Hilt DI

---

## 🔄 CONTEXTO DE DESENVOLVIMENTO

### Bug Anterior Relacionado (v1.9.5)
Na v1.9.5, corrigimos o bug onde **steps não apareciam na edição**:
- Problema: `TaskFormViewModel` não carregava steps
- Solução: Adicionado `GetStepsByTaskUseCase` e `loadTask()` reescrita

### Bug Atual (v1.9.6)
Este bugfix complementa a v1.9.5:
- **v1.9.5:** Steps agora aparecem na edição ✅
- **v1.9.6:** Steps editados agora são salvos corretamente ✅

**Resultado:** Funcionalidade de edição 100% operacional 🎉

---

## 🎓 LIÇÕES APRENDIDAS

1. **Sempre diferenciar criação vs edição** em use cases que lidam com persistência
2. **IDs zero (0) vs IDs positivos** são convenção importante no Room
3. **Deletar e reinserir** é estratégia válida quando há mudanças complexas em relacionamentos
4. **Testar fluxo completo** (criar → editar → salvar) em vez de apenas criar

---

## 📝 NOTAS TÉCNICAS

### Estratégia de Persistência de Steps

**Opção 1:** UPDATE individual de cada step (❌ Mais complexo)
```kotlin
// Comparar steps antigos vs novos
// Fazer UPDATE dos existentes
// Fazer INSERT dos novos
// Fazer DELETE dos removidos
```

**Opção 2:** DELETE ALL + INSERT ALL (✅ Implementado - Mais simples)
```kotlin
// Deletar todos steps antigos
stepRepository.deleteStepsByTask(taskId)
// Inserir todos steps novos
steps.forEach { stepRepository.insertStep(it) }
```

**Por que Opção 2?**
- ✅ Código mais simples e legível
- ✅ Menos chance de bugs
- ✅ Performance adequada (poucos steps por tarefa)
- ✅ Cascade delete garante consistência

---

## 🔍 VALIDAÇÃO FINAL

### Status
- ✅ Bug corrigido e validado
- ✅ Compilação sem erros
- ✅ Testes manuais passaram
- ✅ Documentação atualizada
- ✅ Versionamento incrementado

### Checklist de Qualidade
- [x] Código compila sem warnings críticos
- [x] Funcionalidade testada manualmente
- [x] Documentação inline atualizada
- [x] CHANGELOG.md atualizado
- [x] Versionamento seguindo semver
- [x] Commit message descritiva

---

**Desenvolvido por:** PequenosPassos Development Team  
**Revisado em:** 21/10/2025  
**Próxima Versão:** 1.9.7 (features ou bugfixes futuros)

