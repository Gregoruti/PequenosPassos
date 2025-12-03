# 🎯 GUIA RÁPIDO: Como Trocar o Logo da HomeScreen

**Data:** 03/11/2025  
**Status:** ⚠️ AÇÃO NECESSÁRIA  

---

## ❌ O QUE VOCÊ FEZ (E NÃO FUNCIONOU):

```
Substituiu o arquivo em:
D:\Softwares\PequenosPassos\images\Ícone com nome - Pequenos Passos.png
```

**Por que não funcionou?**
- ❌ A pasta `images/` **NÃO É RECONHECIDA** pelo Android
- ❌ Android só lê arquivos de `app/src/main/res/`

---

## ✅ O QUE VOCÊ PRECISA FAZER:

### 🚀 OPÇÃO 1: AUTOMÁTICO (MAIS FÁCIL)

**Execute o script criado para você:**

```cmd
cd D:\Softwares\PequenosPassos
.\substituir_logo.bat
```

**O script vai:**
1. ✅ Fazer backup do logo antigo
2. ✅ Copiar o novo logo para o lugar correto
3. ✅ Compilar o app
4. ✅ Instalar automaticamente

---

### 🔧 OPÇÃO 2: MANUAL (PASSO A PASSO)

#### Passo 1: Copie o arquivo para o lugar correto

**Origem (onde você colocou):**
```
D:\Softwares\PequenosPassos\images\Ícone com nome - Pequenos Passos.png
```

**Destino (onde deve estar):**
```
D:\Softwares\PequenosPassos\app\src\main\res\drawable\logo_pequenos_passos.png
```

**Como fazer:**
1. Abra o Windows Explorer
2. Navegue até: `D:\Softwares\PequenosPassos\images\`
3. Copie o arquivo: `Ícone com nome - Pequenos Passos.png`
4. Cole em: `D:\Softwares\PequenosPassos\app\src\main\res\drawable\`
5. **Renomeie para:** `logo_pequenos_passos.png`
   - ⚠️ **IMPORTANTE:** Substitua o arquivo existente!

#### Passo 2: Compile o app

```cmd
cd D:\Softwares\PequenosPassos
.\gradlew.bat clean assembleDebug
```

#### Passo 3: Instale no dispositivo

```cmd
adb uninstall com.example.pequenospassos
adb install app\build\outputs\apk\debug\app-debug.apk
```

---

## 📁 RESUMO VISUAL:

```
❌ ERRADO (onde você colocou):
D:\Softwares\PequenosPassos\
└── images\
    └── Ícone com nome - Pequenos Passos.png  ❌ Android NÃO vê aqui!

✅ CORRETO (onde deve estar):
D:\Softwares\PequenosPassos\
└── app\
    └── src\
        └── main\
            └── res\
                └── drawable\
                    └── logo_pequenos_passos.png  ✅ Android LÊ daqui!
```

---

## ⚠️ REGRAS DE NOME DE ARQUIVO:

**Seu arquivo:**
```
❌ Ícone com nome - Pequenos Passos.png
   (Maiúsculas, espaços, hífens, acento)
```

**Nome correto no Android:**
```
✅ logo_pequenos_passos.png
   (Só minúsculas e underscores)
```

---

## 🔍 COMO VERIFICAR SE FUNCIONOU:

1. ✅ Abra o app
2. ✅ Vá para HomeScreen (tela inicial)
3. ✅ O logo deve aparecer no topo

**Se não aparecer:**
- Force Stop no app (Configurações → Apps)
- Limpe cache e dados
- Abra novamente

---

## 📝 ARQUIVOS CRIADOS PARA AJUDAR:

- ✅ `substituir_logo.bat` - Script automático
- ✅ `MVP15_CORRECOES_UI_ICONES.md` - Documentação completa
- ✅ `GUIA_RAPIDO_TROCAR_LOGO.md` - Este arquivo

---

**Execute `.\substituir_logo.bat` e pronto! 🚀**

