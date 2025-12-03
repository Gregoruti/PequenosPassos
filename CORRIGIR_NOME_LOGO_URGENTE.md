# ⚠️ PROBLEMA CRÍTICO COM O NOME DO ARQUIVO!

**Data:** 03/11/2025  
**Status:** ⚠️ REQUER AÇÃO IMEDIATA  

---

## ❌ PROBLEMA IDENTIFICADO:

Você nomeou o arquivo como:
```
icone_com_nome_Pequenos_Passos.png
  └─ Tem letras MAIÚSCULAS: P e S
```

**Por que é problema?**
- ❌ O Android **NÃO aceita letras maiúsculas** em nomes de arquivos de recursos
- ❌ Vai causar **erro de compilação**
- ❌ O app **não vai funcionar**

---

## ✅ SOLUÇÃO IMEDIATA:

### OPÇÃO 1: Renomear pelo Windows Explorer (MAIS FÁCIL)

1. **Abra:** `D:\Softwares\PequenosPassos\app\src\main\res\drawable\`
2. **Encontre:** `icone_com_nome_Pequenos_Passos.png`
3. **Clique com botão direito** → Renomear
4. **Novo nome:** `icone_com_nome_pequenos_passos.png`
   - ⚠️ **IMPORTANTE:** Tudo em **minúsculas**!

---

### OPÇÃO 2: Usar Script Automático

Execute no terminal:
```cmd
cd D:\Softwares\PequenosPassos
.\corrigir_nome_logo.bat
```

---

### OPÇÃO 3: Comando Manual

Execute no PowerShell:
```powershell
cd D:\Softwares\PequenosPassos
ren "app\src\main\res\drawable\icone_com_nome_Pequenos_Passos.png" "icone_com_nome_pequenos_passos.png"
```

---

## 📝 REGRA DO ANDROID:

**NOMES DE ARQUIVOS EM `res/` DEVEM SER:**

✅ **PERMITIDO:**
- Letras minúsculas: `a-z`
- Números: `0-9`
- Underscore: `_`

❌ **NÃO PERMITIDO:**
- Letras maiúsculas: `A-Z`
- Espaços: ` `
- Hífens: `-`
- Acentos: `á`, `é`, `ã`, etc.

**Exemplos:**
- ✅ `icone_com_nome_pequenos_passos.png` → OK
- ❌ `icone_com_nome_Pequenos_Passos.png` → ERRO (maiúsculas)
- ❌ `Icone com nome.png` → ERRO (maiúsculas e espaços)
- ❌ `ícone-logo.png` → ERRO (acento e hífen)

---

## 🔄 DEPOIS DE RENOMEAR:

1. **Compile e instale:**
   ```cmd
   .\compilar_e_instalar_v2.2.1.bat
   ```

2. **Ou execute comandos individuais:**
   ```cmd
   .\gradlew.bat clean assembleDebug
   adb install -r app\build\outputs\apk\debug\app-debug.apk
   ```

---

## ✅ VERIFICAÇÃO:

**Após renomear, o arquivo deve estar assim:**

```
D:\Softwares\PequenosPassos\app\src\main\res\drawable\
└── icone_com_nome_pequenos_passos.png  ✅ (tudo minúsculo)
```

**E o código já está atualizado para usar:**
```kotlin
R.drawable.icone_com_nome_pequenos_passos
```

---

## 🚀 AÇÃO NECESSÁRIA:

1. **RENOMEIE O ARQUIVO** (escolha uma das 3 opções acima)
2. **COMPILE:** `.\compilar_e_instalar_v2.2.1.bat`
3. **TESTE** no dispositivo

---

**IMPORTANTE: Renomeie o arquivo ANTES de compilar, ou vai dar erro!** ⚠️

