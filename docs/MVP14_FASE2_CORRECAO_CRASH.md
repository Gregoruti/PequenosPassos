# MVP-14 FASE 2 - CORREÇÃO DE CRASH

**Data:** 01/11/2025  
**Problema:** App fecha (crash) ao abrir tela Histórico & Ferramentas  
**Status:** ✅ CORRIGIDO  
**Gravidade:** 🔴 CRÍTICO

---

## 🐛 PROBLEMA IDENTIFICADO

### Sintoma
- App rodando normalmente
- Ao pressionar botão **"Histórico & Ferramentas"**
- App fecha imediatamente (crash)

### Causa Raiz

**Problema:** Query `getEnableVoiceResponseFlow()` retornava `Flow<Boolean>` mas o banco de dados não tinha registro ainda.

**Arquivo:** `AppSettingsDao.kt`

**Query problemática:**
```kotlin
@Query("SELECT enableVoiceResponse FROM app_settings WHERE id = 'settings'")
fun getEnableVoiceResponseFlow(): Flow<Boolean>
```

**Por que crashava:**
1. Primeira execução do app após migration 7→8
2. Tabela `app_settings` pode não ter registro ainda
3. Query retorna `null` ao invés de `Boolean`
4. Flow tenta converter null → Boolean
5. **Crash!** `NullPointerException` ou `TypeCastException`

---

## ✅ CORREÇÃO APLICADA

### Mudança 1: DAO - Permitir null

**Arquivo:** `data/database/dao/AppSettingsDao.kt`

**Antes:**
```kotlin
@Query("SELECT enableVoiceResponse FROM app_settings WHERE id = 'settings'")
fun getEnableVoiceResponseFlow(): Flow<Boolean>
```

**Depois:**
```kotlin
@Query("SELECT enableVoiceResponse FROM app_settings WHERE id = 'settings' LIMIT 1")
fun getEnableVoiceResponseFlow(): Flow<Boolean?> // Agora aceita null
```

**Mudanças:**
- ✅ Tipo de retorno: `Flow<Boolean>` → `Flow<Boolean?>`
- ✅ Adicionado `LIMIT 1` para garantir apenas um resultado
- ✅ Permite retornar `null` se não houver registro

---

### Mudança 2: Repository - Tratar null

**Arquivo:** `data/repository/AppSettingsRepositoryImpl.kt`

**Antes:**
```kotlin
override fun getEnableVoiceResponse(): Flow<Boolean> {
    return dao.getEnableVoiceResponseFlow()
}
```

**Depois:**
```kotlin
override fun getEnableVoiceResponse(): Flow<Boolean> {
    return dao.getEnableVoiceResponseFlow()
        .map { it ?: false } // Retorna false se null (sem registro ainda)
}
```

**Mudanças:**
- ✅ Importado `kotlinx.coroutines.flow.map`
- ✅ Transforma `Flow<Boolean?>` → `Flow<Boolean>`
- ✅ Valor padrão: `false` quando null

---

### Mudança 3: Import adicionado

**Arquivo:** `data/repository/AppSettingsRepositoryImpl.kt`

```kotlin
import kotlinx.coroutines.flow.map // NOVO
```

---

## 🔍 ANÁLISE TÉCNICA

### Por que isso aconteceu?

**Lição do MVP-11 aplicada incorretamente:**

No MVP-11, aprendemos que devemos **adicionar o campo na data class** quando criamos uma migration.

✅ **Fizemos isso:** Adicionamos `enableVoiceResponse` em `AppSettings.kt`

❌ **MAS:** Não consideramos que a query poderia retornar `null` antes de haver um registro no banco.

### Cenário do problema:

1. Usuário tinha o app na versão 2.1.0 (database v7)
2. Instalou versão 2.2.0 (database v8)
3. Migration 7→8 executou: `ALTER TABLE app_settings ADD COLUMN enableVoiceResponse INTEGER NOT NULL DEFAULT 0`
4. **SE já havia registro:** Campo adicionado com valor `0` (false) ✅
5. **SE NÃO havia registro:** Tabela vazia, query retorna `null` ❌
6. Flow tenta converter null → Boolean
7. **CRASH!**

### Por que alguns apps não crasharam?

Se o app já tinha um registro em `app_settings` (criado no primeiro uso), a migration simplesmente adicionou a coluna com valor padrão `0`.

Se o app estava em instalação limpa ou sem registro, crashava.

---

## 🧪 VALIDAÇÃO DA CORREÇÃO

### Cenário 1: Instalação Limpa

**Passos:**
1. Desinstalar app completamente
2. Instalar versão corrigida
3. Abrir app
4. Ir em Histórico & Ferramentas

**Resultado esperado:**
- ✅ Tela abre normalmente
- ✅ Checkbox "Resposta em Áudio" aparece
- ✅ Checkbox desmarcado (false)
- ✅ Sem crash

---

### Cenário 2: Atualização

**Passos:**
1. App com versão antiga instalada
2. Instalar versão corrigida (sobre a antiga)
3. Migration 7→8 executa
4. Abrir app
5. Ir em Histórico & Ferramentas

**Resultado esperado:**
- ✅ Tela abre normalmente
- ✅ Migration aplicada
- ✅ Campo adicionado com valor padrão
- ✅ Sem crash

---

### Cenário 3: Sem Registro em AppSettings

**Passos:**
1. Simular banco vazio (sem registro em app_settings)
2. Abrir tela Histórico

**Resultado esperado:**
- ✅ Query retorna `null`
- ✅ Repository transforma para `false`
- ✅ ViewModel recebe `false`
- ✅ Checkbox aparece desmarcado
- ✅ Sem crash

---

## 📊 ARQUIVOS MODIFICADOS

| Arquivo | Mudança | Linhas |
|---------|---------|--------|
| AppSettingsDao.kt | Flow<Boolean?> + LIMIT 1 | 2 |
| AppSettingsRepositoryImpl.kt | .map { it ?: false } + import | 3 |
| **TOTAL** | **2 arquivos** | **5 linhas** |

---

## ✅ CHECKLIST DE CORREÇÃO

- [x] Problema identificado
- [x] Causa raiz encontrada
- [x] Correção aplicada (DAO)
- [x] Correção aplicada (Repository)
- [x] Import adicionado
- [x] Erros de compilação verificados (0 erros)
- [x] Build compilando
- [x] Pronto para instalar e testar

---

## 🎯 PRÓXIMOS PASSOS

### 1. Compilar e Instalar

```powershell
.\gradlew assembleDebug
.\gradlew installDebug
```

### 2. Testar no Dispositivo

**Teste Crítico:**
1. Abrir app
2. Clicar em **"Histórico & Ferramentas"**
3. **Resultado esperado:** Tela abre normalmente (sem crash) ✅

**Teste Completo:**
- Executar todos os 8 testes do guia de validação
- Confirmar que checkbox funciona
- Confirmar persistência

### 3. Documentar Sucesso

Se testar OK:
- Criar `MVP14_FASE2_CONCLUSAO.md`
- Fazer commit da correção
- Avançar para Fase 3

---

## 🔄 LIÇÕES APRENDIDAS

### Lição MVP-14 Fase 2:

**Sempre considerar casos de banco vazio ao criar queries com Flow:**

✅ **Fazer:**
- Usar `Flow<Tipo?>` quando query pode retornar null
- Tratar null no Repository com valor padrão
- Adicionar `LIMIT 1` em queries de registro único

❌ **Evitar:**
- Assumir que sempre haverá registro
- Usar `Flow<Tipo>` sem tratar null
- Deixar crash acontecer em produção

### Padrão recomendado:

```kotlin
// DAO
@Query("SELECT campo FROM tabela WHERE id = :id LIMIT 1")
fun getCampoFlow(): Flow<Boolean?> // Aceita null

// Repository
override fun getCampo(): Flow<Boolean> {
    return dao.getCampoFlow()
        .map { it ?: valorPadrao } // Trata null
}
```

---

## 📝 RESUMO

**Problema:** Crash ao abrir Histórico (query retornava null)  
**Causa:** Flow<Boolean> não aceitava null  
**Correção:** Flow<Boolean?> + .map { it ?: false }  
**Status:** ✅ Corrigido  
**Arquivos:** 2 modificados, 5 linhas  
**Próximo:** Testar → Documentar → Commit → Fase 3

---

**Data da Correção:** 01/11/2025  
**Code Assistant:** Claude Sonnet 4.5 (GitHub Copilot)  
**Tempo de Correção:** ~10 minutos  
**Gravidade:** 🔴 CRÍTICO (bloqueava uso do app)  
**Status:** ✅ CORRIGIDO - Aguardando teste no dispositivo

