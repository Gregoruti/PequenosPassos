# MVP-08: Correção de Migration do Banco de Dados

**Data:** 23/10/2025  
**Versão:** 1.0.0  
**Status:** ✅ CORRIGIDO

---

## 🔴 Problema Encontrado

Ao tentar salvar uma nova tarefa, o aplicativo apresentou erro de migração:

```
Migration didn't properly handle: achievements (com.pequenospassos.domain.model.Achievement)
```

### Causa Raiz

**Incompatibilidade de tipos entre Migration SQL e TypeConverter:**

1. **Migration SQL (ERRADO):**
   - `completedAt TEXT NOT NULL` (coluna como texto)
   - `unlockedAt TEXT` (coluna como texto)

2. **TypeConverter Kotlin (CORRETO):**
   ```kotlin
   @TypeConverter
   fun fromLocalDateTime(dateTime: LocalDateTime?): Long?
   @TypeConverter
   fun toLocalDateTime(timestamp: Long?): LocalDateTime?
   ```
   - Converte LocalDateTime para **Long (INTEGER)** no banco

3. **Resultado:** Room esperava INTEGER mas encontrou TEXT na migration.

---

## ✅ Correções Implementadas

### 1. Correção na Migration 2→3 (AppDatabase.kt)

**Arquivo:** `data/database/AppDatabase.kt`

#### Tabela `achievements`:
```sql
-- ANTES (ERRADO):
completedAt TEXT NOT NULL

-- DEPOIS (CORRETO):
completedAt INTEGER NOT NULL
```

#### Tabela `rewards`:
```sql
-- ANTES (ERRADO):
unlockedAt TEXT

-- DEPOIS (CORRETO):
unlockedAt INTEGER
```

### 2. Arquivos Modificados

- ✅ `app/src/main/java/com/pequenospassos/data/database/AppDatabase.kt`
  - Linha ~154: `completedAt TEXT` → `completedAt INTEGER`
  - Linha ~189: `unlockedAt TEXT` → `unlockedAt INTEGER`

---

## 🔧 Solução para o Usuário

Para aplicar as correções no dispositivo, você precisa **limpar os dados do app**:

### Opção 1: Via Configurações do Android
1. Abra **Configurações** do Android
2. Vá em **Apps** → **PequenosPassos**
3. Toque em **Armazenamento**
4. Toque em **Limpar dados**
5. Confirme a ação

### Opção 2: Desinstalar e Reinstalar
```bash
adb uninstall com.pequenospassos
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Opção 3: Via Android Studio
1. **Run** → **Edit Configurations**
2. Marque **Clear application data before launch**
3. Execute o app normalmente

---

## 📊 Estrutura Final das Tabelas

### Tabela `achievements`
```sql
CREATE TABLE achievements (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    childId INTEGER NOT NULL,
    taskId INTEGER NOT NULL,
    starsEarned INTEGER NOT NULL,
    completedAt INTEGER NOT NULL,           -- ✅ CORRIGIDO: INTEGER (timestamp)
    executionTime INTEGER NOT NULL,
    wasOnTime INTEGER NOT NULL,
    allStepsCompleted INTEGER NOT NULL,
    needsHelp INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY(childId) REFERENCES child_profile(id) ON DELETE CASCADE,
    FOREIGN KEY(taskId) REFERENCES tasks(id) ON DELETE CASCADE
)
```

### Tabela `rewards`
```sql
CREATE TABLE rewards (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    childId INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    starsRequired INTEGER NOT NULL,
    rewardType TEXT NOT NULL,
    iconResId INTEGER,
    imageUri TEXT,
    isUnlocked INTEGER NOT NULL DEFAULT 0,
    unlockedAt INTEGER,                     -- ✅ CORRIGIDO: INTEGER (timestamp)
    isActive INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY(childId) REFERENCES child_profile(id) ON DELETE CASCADE
)
```

---

## 🧪 Validação

### Testes Necessários
- [ ] Criar nova tarefa (deve salvar sem erro)
- [ ] Verificar que o banco foi criado corretamente
- [ ] Executar testes instrumentados do DAO

### Compilação
```bash
./gradlew assembleDebug
# Status: ✅ BUILD SUCCESSFUL
```

---

## 📝 Notas Técnicas

### Por que Long (INTEGER)?

1. **Eficiência:** INTEGER ocupa menos espaço que TEXT
2. **Performance:** Comparações numéricas são mais rápidas
3. **Padrão:** Timestamps Unix são amplamente utilizados
4. **Compatibilidade:** Funciona em todas as APIs do Android

### TypeConverter Utilizado

```kotlin
/**
 * Converte LocalDateTime para Long timestamp (segundos desde epoch)
 */
@TypeConverter
fun fromLocalDateTime(dateTime: LocalDateTime?): Long? {
    return dateTime?.atZone(ZoneId.systemDefault())?.toEpochSecond()
}

/**
 * Converte Long timestamp para LocalDateTime
 */
@TypeConverter
fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
    return timestamp?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
    }
}
```

---

## 🎯 Resumo Executivo

| Item | Status |
|------|--------|
| Erro identificado | ✅ Sim |
| Causa encontrada | ✅ Incompatibilidade TEXT vs INTEGER |
| Correção aplicada | ✅ Migration corrigida |
| Compilação | ✅ Sucesso |
| Documentação | ✅ Completa |

**Próximo passo:** Limpar dados do app e testar criação de tarefas.

---

**Autor:** GitHub Copilot  
**Revisão:** MVP-08 - Sistema de Gamificação  
**Referência:** `AppDatabase.kt` - MIGRATION_2_3

