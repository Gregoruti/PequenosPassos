ovar <!--
Arquivo: docs/ANALISE_TAREFAS_PRE_INSTALADAS.md
Tipo: Análise técnica e planejamento
Objetivo: Avaliar viabilidade e implementar sistema de tarefas pré-instaladas
Criado: 2025-11-03
Autor: Claude Sonnet 4.5 (GitHub Copilot)

INSTRUÇÕES:
Este documento analisa a viabilidade técnica de pré-instalar tarefas/rotinas no app
e fornece um plano de implementação detalhado.

Correlações: AppDatabase.kt, Task.kt, Step.kt, TaskRepository.kt
-->

# ANÁLISE: TAREFAS PRÉ-INSTALADAS NO APP PEQUENOS PASSOS

**Data:** 03/11/2025  
**Versão Atual do App:** 2.1.0  
**Tipo:** Análise Técnica + Planejamento de Implementação  
**Status:** 📋 PLANEJAMENTO

---

## 📋 ÍNDICE

1. [Pergunta do Usuário](#1-pergunta-do-usuário)
2. [Resposta Técnica](#2-resposta-técnica)
3. [Análise de Viabilidade](#3-análise-de-viabilidade)
4. [Benefícios](#4-benefícios)
5. [Possíveis Problemas e Soluções](#5-possíveis-problemas-e-soluções)
6. [Proposta de Implementação](#6-proposta-de-implementação)
7. [Tarefas Sugeridas para Pré-Instalação](#7-tarefas-sugeridas-para-pré-instalação)
8. [Plano de Implementação Técnica](#8-plano-de-implementação-técnica)
9. [Checklist de Validação](#9-checklist-de-validação)

---

## 1. PERGUNTA DO USUÁRIO

> "Seria possível deixar 'pré-instalado' algumas das rotinas e seus respectivos passos (de forma que eu só precise selecionar as imagens) posteriormente? Isto poderia gerar algum problema no código e no funcionamento do App?"

---

## 2. RESPOSTA TÉCNICA

### ✅ **SIM, É TOTALMENTE POSSÍVEL E RECOMENDADO**

**Resposta Curta:**
- ✅ Tecnicamente viável
- ✅ Não causa problemas no código
- ✅ Melhora experiência do usuário
- ✅ Implementação relativamente simples

**Resposta Detalhada:**

Room Database (usado no app) suporta nativamente **pré-população de dados** através de:
1. **Callback `onCreate()`** - Executa apenas na primeira criação do banco
2. **Callback `onOpen()`** - Executa toda vez que o banco é aberto (não recomendado para este caso)
3. **Database inicial (createFromAsset)** - Copia banco pré-populado de assets

A melhor abordagem para este caso é usar **`onCreate()` callback**, que:
- Executa **apenas uma vez** na primeira instalação
- Permite inserir tarefas/passos padrão
- Não interfere com dados do usuário
- Pode ser atualizado em migrations futuras

---

## 3. ANÁLISE DE VIABILIDADE

### 3.1 Compatibilidade com Arquitetura Atual

| Componente | Compatível? | Observações |
|------------|-------------|-------------|
| **Room Database** | ✅ SIM | Suporta callbacks nativamente |
| **Clean Architecture** | ✅ SIM | Mantém separação de camadas |
| **Migrations** | ✅ SIM | Não interfere com migrations existentes |
| **Repository Pattern** | ✅ SIM | Dados são inseridos via DAO normal |
| **MVVM** | ✅ SIM | ViewModels continuam funcionando igual |
| **Flow/StateFlow** | ✅ SIM | Reatividade mantida |

### 3.2 Impacto no Código Existente

**Arquivos que PRECISAM ser modificados:**
1. ✏️ `AppDatabase.kt` - Adicionar callback onCreate
2. ✏️ Nova classe `DefaultTasksPopulator.kt` - Lógica de população

**Arquivos que NÃO precisam ser modificados:**
- ✅ `Task.kt`, `Step.kt` (models)
- ✅ `TaskDao.kt`, `StepDao.kt` (DAOs)
- ✅ `TaskRepository.kt` (repositórios)
- ✅ `TaskFormScreen.kt`, `TaskListScreen.kt` (UI)
- ✅ Todas as ViewModels

**Impacto:** ⚡ **MUITO BAIXO** (apenas 1 arquivo modificado + 1 arquivo novo)

### 3.3 Impacto na Experiência do Usuário

**ANTES (sem tarefas pré-instaladas):**
```
1. Usuário instala app
2. Cadastra perfil da criança
3. Home Screen mostra "0 atividades para fazer"
4. Usuário precisa:
   - Ir em "Edição"
   - Criar primeira tarefa do zero
   - Definir categoria
   - Adicionar todos os passos manualmente
   - Adicionar imagens (ou deixar sem)
   - Configurar timers
   - Definir estrelas
   - Salvar
   - Repetir para cada tarefa
```
⏱️ **Tempo estimado para 5 tarefas:** 30-60 minutos

**DEPOIS (com tarefas pré-instaladas):**
```
1. Usuário instala app
2. Cadastra perfil da criança
3. Home Screen mostra "Você tem 5 atividades para fazer"
4. Usuário pode:
   OPÇÃO A: Usar as tarefas imediatamente
   OPÇÃO B: Editar e adicionar imagens personalizadas
   OPÇÃO C: Criar novas tarefas adicionais
```
⏱️ **Tempo para começar a usar:** 2 minutos (apenas cadastro)

**Melhoria:** 🚀 **15x-30x mais rápido** para começar a usar

---

## 4. BENEFÍCIOS

### 4.1 Para o Usuário (Responsável)

✅ **Onboarding mais rápido**
- App utilizável imediatamente após cadastro
- Não precisa criar tudo do zero
- Reduz barreira de entrada

✅ **Exemplos práticos**
- Usuário vê como estruturar tarefas
- Aprende padrão de passos sequenciais
- Entende quanto tempo alocar por passo

✅ **Customização gradual**
- Pode editar tarefas pré-instaladas
- Adicionar imagens aos poucos
- Ajustar timers conforme necessidade

✅ **Menos fricção**
- Reduz chance de desistência
- Aumenta taxa de retenção
- Melhora satisfação inicial

### 4.2 Para a Criança

✅ **Experiência imediata**
- Pode começar a usar o app no mesmo dia
- Vê tarefas familiares (escovar dentes, lavar mãos)
- Recebe feedback positivo desde o início

✅ **Aprendizado**
- Vê exemplos de rotinas estruturadas
- Entende formato de passos
- Familiariza-se com o app

### 4.3 Para o Desenvolvimento/Manutenção

✅ **Demonstração**
- App tem conteúdo na primeira abertura
- Screenshots ficam mais interessantes
- Demonstrações em vídeo são mais rápidas

✅ **Testes**
- QA pode testar funcionalidades imediatamente
- Validação de fluxo completo mais fácil
- Identificação de bugs facilitada

✅ **Marketing**
- "5 rotinas pré-instaladas prontas para usar"
- "Comece a usar em 2 minutos"
- Diferencial competitivo

---

## 5. POSSÍVEIS PROBLEMAS E SOLUÇÕES

### ❌ PROBLEMA 1: Tarefas duplicadas se usuário reinstalar

**Cenário:**
- Usuário instala app → 5 tarefas criadas
- Usuário desinstala app
- Usuário reinstala → Tenta criar 5 tarefas novamente

**Impacto:** ⚠️ Médio - Duplicação de dados

**Solução:**
```kotlin
// Verificar se já existem tarefas antes de popular
val existingTasks = taskDao.getTasksCount()
if (existingTasks == 0) {
    // Popular apenas se não houver tarefas
    insertDefaultTasks()
}
```

**Status:** ✅ Resolvível facilmente

---

### ❌ PROBLEMA 2: childId não existe ainda

**Cenário:**
- Tarefas são populadas no onCreate do banco
- Mas perfil da criança só é criado depois no onboarding

**Impacto:** 🔴 Alto - Violação de Foreign Key

**Solução A (Recomendada):**
```kotlin
// Usar childId = 0 (especial) ou -1
// Depois na tela de Cadastro, atualizar childId para o real
task.copy(childId = realChildId)
```

**Solução B:**
```kotlin
// Popular tarefas DEPOIS do cadastro do perfil
// No ViewModel de Onboarding, após salvar perfil:
if (isFirstTime) {
    defaultTasksPopulator.populate(savedChildId)
}
```

**Solução C (Melhor):**
```kotlin
// Remover Foreign Key constraint de Task → Child
// Tornar childId nullable temporariamente
// Ou usar childId = 1 padrão e criar child "Sistema"
```

**Status:** ✅ Soluções viáveis existem

---

### ❌ PROBLEMA 3: Imagens placeholder

**Cenário:**
- Tarefas pré-instaladas não têm imagens
- Campo imageUrl fica null

**Impacto:** 🟡 Baixo - Visual menos atrativo

**Solução A (Rápida):**
```kotlin
// Deixar imageUrl = null
// UI já trata isso mostrando ícone de categoria
```

**Solução B (Ideal):**
```kotlin
// Incluir imagens genéricas em /res/drawable/
// Exemplo: default_brush.png, default_hands.png
// imageUrl = "drawable://default_brush"
```

**Solução C (Futura):**
```kotlin
// Baixar imagens de servidor em background
// Ou incluir em /assets/ e copiar para storage
```

**Status:** ✅ Funciona sem imagens, melhorável depois

---

### ❌ PROBLEMA 4: Migração futura para atualizar tarefas

**Cenário:**
- Queremos adicionar nova tarefa pré-instalada
- Ou corrigir passos de tarefa existente
- Usuários antigos não terão a atualização

**Impacto:** 🟡 Médio - Inconsistência entre versões

**Solução:**
```kotlin
// Migration customizada que:
// 1. Verifica versão anterior
// 2. Adiciona apenas tarefas novas
// 3. Mantém tarefas customizadas do usuário

MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Adicionar nova tarefa "Tomar Banho"
        // Apenas se não existir
        database.execSQL("""
            INSERT OR IGNORE INTO tasks (id, name, ...) 
            VALUES (100, 'Tomar Banho', ...)
        """)
    }
}
```

**Status:** ✅ Planejável

---

### ❌ PROBLEMA 5: Usuário deleta tarefa pré-instalada

**Cenário:**
- Usuário deleta "Escovar os Dentes"
- Tarefa não volta a aparecer

**Impacto:** 🟢 Muito Baixo - Comportamento esperado

**Solução:**
```kotlin
// Adicionar botão "Restaurar Tarefas Padrão"
// Em Histórico & Ferramentas
// Que re-insere tarefas pré-instaladas
```

**Status:** ✅ Feature futura opcional

---

## 6. PROPOSTA DE IMPLEMENTAÇÃO

### 6.1 Abordagem Escolhida

**✅ SOLUÇÃO RECOMENDADA:**

1. **Popular tarefas no onCreate() do banco**
2. **Usar childId = 1 (padrão)**
3. **Criar perfil "Sistema" com id = 1 automaticamente**
4. **Após onboarding real, manter childId = 1 ou migrar para novo childId**
5. **Deixar imageUrl = null (usar imagens de drawable futuramente)**
6. **Verificar se já existe tarefas antes de popular**

**Vantagens:**
- ✅ Simples de implementar
- ✅ Não quebra código existente
- ✅ Facilmente testável
- ✅ Permite evolução futura

---

## 7. TAREFAS SUGERIDAS PARA PRÉ-INSTALAÇÃO

### 7.1 Critérios de Seleção

✅ **Universalidade** - Comum para maioria das crianças  
✅ **Simplicidade** - Fácil de entender e executar  
✅ **Frequência** - Executada diariamente ou quase  
✅ **Importância** - Contribui para autonomia  
✅ **Adequação etária** - Apropriado para 0-6 anos  

### 7.2 Top 10 Tarefas Sugeridas

#### 🧘 **1. ESCOVAR OS DENTES**
**Categoria:** Autocuidado → Higiene Bucal  
**Estrelas:** 3 ⭐⭐⭐  
**Passos:**
1. Pegar escova e pasta (30s)
2. Colocar pasta na escova (20s)
3. Escovar os dentes (120s)
4. Enxaguar a boca (30s)
5. Guardar a escova (20s)

**Total:** 5 passos, ~3min 40s

---

#### 🧼 **2. LAVAR AS MÃOS**
**Categoria:** Autocuidado → Higiene  
**Estrelas:** 2 ⭐⭐  
**Passos:**
1. Abrir a torneira (10s)
2. Molhar as mãos (10s)
3. Passar sabonete (20s)
4. Esfregar bem (40s)
5. Enxaguar (20s)
6. Secar com toalha (20s)

**Total:** 6 passos, ~2min

---

#### 🛏️ **3. ARRUMAR A CAMA**
**Categoria:** Casa → Quarto  
**Estrelas:** 4 ⭐⭐⭐⭐  
**Passos:**
1. Puxar lençol (30s)
2. Alisar o lençol (30s)
3. Colocar travesseiro (20s)
4. Arrumar cobertor (40s)
5. Alinhar tudo (30s)

**Total:** 5 passos, ~2min 30s

---

#### 🧸 **4. GUARDAR BRINQUEDOS**
**Categoria:** Casa → Organização  
**Estrelas:** 3 ⭐⭐⭐  
**Passos:**
1. Juntar brinquedos espalhados (60s)
2. Separar por tipo (40s)
3. Colocar na caixa (60s)
4. Fechar a caixa (20s)
5. Guardar no lugar (30s)

**Total:** 5 passos, ~3min 30s

---

#### 🍽️ **5. COLOCAR A MESA**
**Categoria:** Alimentação → Almoço/Jantar  
**Estrelas:** 4 ⭐⭐⭐⭐  
**Passos:**
1. Pegar pratos (30s)
2. Colocar pratos na mesa (30s)
3. Pegar talheres (20s)
4. Colocar talheres (30s)
5. Colocar copos (20s)
6. Colocar guardanapos (20s)

**Total:** 6 passos, ~2min 30s

---

#### 👗 **6. VESTIR-SE**
**Categoria:** Autocuidado → Vestir  
**Estrelas:** 5 ⭐⭐⭐⭐⭐  
**Passos:**
1. Escolher roupa (40s)
2. Vestir a calcinha/cueca (30s)
3. Vestir a calça/saia (40s)
4. Vestir a blusa (30s)
5. Vestir meias (30s)
6. Calçar sapatos (40s)

**Total:** 6 passos, ~3min 30s

---

#### 🪴 **7. REGAR AS PLANTAS**
**Categoria:** Casa → Plantas  
**Estrelas:** 2 ⭐⭐  
**Passos:**
1. Pegar regador (20s)
2. Encher com água (30s)
3. Ir até as plantas (20s)
4. Regar cada planta (60s)
5. Guardar regador (20s)

**Total:** 5 passos, ~2min 30s

---

#### 🍳 **8. TOMAR CAFÉ DA MANHÃ**
**Categoria:** Alimentação → Café da Manhã  
**Estrelas:** 3 ⭐⭐⭐  
**Passos:**
1. Sentar à mesa (10s)
2. Comer devagar (300s)
3. Beber o leite/suco (60s)
4. Limpar a boca (20s)
5. Levar prato para pia (30s)

**Total:** 5 passos, ~7min

---

#### 📚 **9. FAZER LIÇÃO DE CASA**
**Categoria:** Escola → Lição de Casa  
**Estrelas:** 5 ⭐⭐⭐⭐⭐  
**Passos:**
1. Pegar material escolar (30s)
2. Sentar na mesa (20s)
3. Abrir caderno (20s)
4. Fazer os exercícios (600s)
5. Guardar material (40s)

**Total:** 5 passos, ~11min 50s

---

#### 😴 **10. PREPARAR PARA DORMIR**
**Categoria:** Autocuidado → Sono  
**Estrelas:** 4 ⭐⭐⭐⭐  
**Passos:**
1. Escovar os dentes (220s - referencia tarefa #1)
2. Vestir pijama (60s)
3. Ir ao banheiro (40s)
4. Arrumar travesseiro (20s)
5. Deitar na cama (30s)

**Total:** 5 passos, ~6min 10s

---

### 7.3 Resumo das Tarefas Pré-Instaladas

| # | Tarefa | Categoria | Passos | Duração | Estrelas |
|---|--------|-----------|--------|---------|----------|
| 1 | Escovar os Dentes | 🧘 Autocuidado | 5 | ~4min | 3⭐ |
| 2 | Lavar as Mãos | 🧘 Autocuidado | 6 | ~2min | 2⭐ |
| 3 | Arrumar a Cama | 🏠 Casa | 5 | ~3min | 4⭐ |
| 4 | Guardar Brinquedos | 🏠 Casa | 5 | ~4min | 3⭐ |
| 5 | Colocar a Mesa | 🍽️ Alimentação | 6 | ~3min | 4⭐ |
| 6 | Vestir-se | 🧘 Autocuidado | 6 | ~4min | 5⭐ |
| 7 | Regar as Plantas | 🏠 Casa | 5 | ~3min | 2⭐ |
| 8 | Tomar Café | 🍽️ Alimentação | 5 | ~7min | 3⭐ |
| 9 | Fazer Lição | 🎓 Escola | 5 | ~12min | 5⭐ |
| 10 | Preparar Dormir | 🧘 Autocuidado | 5 | ~6min | 4⭐ |

**Distribuição por Categoria:**
- 🧘 Autocuidado: 4 tarefas
- 🏠 Casa: 3 tarefas
- 🍽️ Alimentação: 2 tarefas
- 🎓 Escola: 1 tarefa

**Total:** 53 passos, ~35 estrelas disponíveis

---

## 8. PLANO DE IMPLEMENTAÇÃO TÉCNICA

### 8.1 FASE 1: Criação da Classe Populadora

**Arquivo:** `app/src/main/java/com/pequenospassos/data/database/DefaultTasksPopulator.kt`

```kotlin
package com.pequenospassos.data.database

import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.Step
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Popula o banco de dados com tarefas padrão na primeira execução.
 *
 * Tarefas pré-instaladas para facilitar onboarding:
 * - 10 tarefas comuns do dia a dia
 * - 53 passos detalhados
 * - Prontas para uso imediato
 * - Personalizáveis pelo usuário
 *
 * @since MVP-15 (03/11/2025)
 * @author Claude Sonnet 4.5
 */
class DefaultTasksPopulator(
    private val taskDao: TaskDao,
    private val stepDao: StepDao
) {
    
    fun populate() {
        CoroutineScope(Dispatchers.IO).launch {
            // Verificar se já existem tarefas
            val existingTasksCount = taskDao.getTasksCount()
            if (existingTasksCount > 0) {
                return@launch // Já populado, sair
            }
            
            // Popular tarefas
            populateTask1_EscovarDentes()
            populateTask2_LavarMaos()
            populateTask3_ArrumarCama()
            populateTask4_GuardarBrinquedos()
            populateTask5_ColocarMesa()
            populateTask6_Vestirse()
            populateTask7_RegarPlantas()
            populateTask8_TomarCafe()
            populateTask9_FazerLicao()
            populateTask10_PrepararDormir()
        }
    }
    
    private suspend fun populateTask1_EscovarDentes() {
        val taskId = taskDao.insert(
            Task(
                id = 0,
                childId = 1, // ID padrão do sistema
                name = "Escovar os Dentes",
                description = "Rotina completa de higiene bucal",
                category = "hygiene_oral",
                stars = 3,
                imageUrl = null // Imagem pode ser adicionada depois
            )
        )
        
        stepDao.insertAll(listOf(
            Step(0, taskId, 1, "Pegar escova e pasta", null, 30),
            Step(0, taskId, 2, "Colocar pasta na escova", null, 20),
            Step(0, taskId, 3, "Escovar os dentes", null, 120),
            Step(0, taskId, 4, "Enxaguar a boca", null, 30),
            Step(0, taskId, 5, "Guardar a escova", null, 20)
        ))
    }
    
    // ... implementar outras 9 tarefas similarmente
}
```

---

### 8.2 FASE 2: Modificar AppDatabase

**Arquivo:** `AppDatabase.kt`

```kotlin
// Adicionar no companion object:

.addCallback(object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        
        // Popular tarefas padrão
        CoroutineScope(Dispatchers.IO).launch {
            val database = getInstance(context)
            val populator = DefaultTasksPopulator(
                database.taskDao(),
                database.stepDao()
            )
            populator.populate()
        }
    }
})
```

---

### 8.3 FASE 3: Adicionar getTasksCount() no DAO

**Arquivo:** `TaskDao.kt`

```kotlin
@Query("SELECT COUNT(*) FROM tasks")
suspend fun getTasksCount(): Int
```

---

### 8.4 FASE 4: Testar

**Checklist de Testes:**
- [ ] Desinstalar app completamente
- [ ] Instalar app novamente
- [ ] Verificar banco de dados (via Database Inspector)
- [ ] Confirmar 10 tarefas inseridas
- [ ] Confirmar 53 passos inseridos
- [ ] Abrir app e verificar lista de tarefas
- [ ] Executar uma tarefa pré-instalada
- [ ] Editar uma tarefa pré-instalada
- [ ] Adicionar imagem a uma tarefa
- [ ] Deletar uma tarefa pré-instalada
- [ ] Criar nova tarefa customizada

---

## 9. CHECKLIST DE VALIDAÇÃO

### 9.1 Funcionalidade

- [ ] Tarefas aparecem na primeira execução
- [ ] Não duplicam em reinstalações
- [ ] Podem ser editadas normalmente
- [ ] Podem ser deletadas normalmente
- [ ] Novas tarefas podem ser criadas
- [ ] Imagens podem ser adicionadas depois
- [ ] Timers funcionam corretamente
- [ ] Estrelas são contabilizadas
- [ ] TTS lê corretamente
- [ ] ASR funciona nos pop-ups

### 9.2 Performance

- [ ] Tempo de criação do banco < 3s
- [ ] Não trava a UI
- [ ] Primeira abertura suave
- [ ] Transições fluidas

### 9.3 Testes

- [ ] Testes unitários passam
- [ ] Testes de integração passam
- [ ] Testes em dispositivo real
- [ ] Nenhuma regressão identificada

---

## 10. CONCLUSÃO E RECOMENDAÇÕES

### ✅ **VIABILIDADE TÉCNICA: 100%**

**Resumo:**
- ✅ Totalmente viável tecnicamente
- ✅ Não causa problemas no código
- ✅ Melhora significativamente UX
- ✅ Implementação simples e rápida
- ✅ Facilmente testável
- ✅ Permite evolução futura

### 🎯 **RECOMENDAÇÕES:**

1. **✅ IMPLEMENTAR** - Benefícios superam riscos
2. **📌 INICIAR COM 5-10 TAREFAS** - Não sobrecarregar
3. **🖼️ DEIXAR IMAGENS PARA DEPOIS** - Funciona sem, melhora com
4. **📊 MONITORAR FEEDBACK** - Usuários gostam das tarefas escolhidas?
5. **🔄 ITERAR** - Adicionar/remover tarefas em versões futuras

### 📦 **MVP SUGERIDO:**

**MVP-15: Tarefas Pré-Instaladas**
- Implementar 10 tarefas padrão
- 53 passos detalhados
- Verificação de duplicação
- Documentação completa
- Testes de validação

**Estimativa:** 1-2 dias de desenvolvimento + testes

---

**Documento criado em:** 03/11/2025  
**Status:** ✅ COMPLETO - Pronto para implementação  
**Próximo Passo:** Aprovar proposta e iniciar MVP-15

---

**🎓 Esta funcionalidade pode ser um diferencial importante para o TCC, mostrando preocupação com UX desde o primeiro uso!**

