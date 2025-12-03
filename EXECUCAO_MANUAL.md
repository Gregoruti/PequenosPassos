# ⚡ EXECUÇÃO MANUAL - COMPILAR E INSTALAR

**Versão:** 2.2.1  
**Data:** 03/11/2025  
**Status:** Pronto para compilar  

---

## 🚀 EXECUTE ESTES COMANDOS NO TERMINAL:

### PowerShell ou CMD:

```cmd
cd D:\Softwares\PequenosPassos
.\compilar_e_instalar_v2.2.1.bat
```

**OU execute os comandos individualmente:**

```cmd
# 1. Navegue para o projeto
cd D:\Softwares\PequenosPassos

# 2. Limpe builds anteriores
.\gradlew.bat clean

# 3. Compile o projeto
.\gradlew.bat assembleDebug

# 4. Desinstale versão antiga (opcional)
adb uninstall com.example.pequenospassos

# 5. Instale nova versão
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

---

## ✅ CORREÇÕES APLICADAS NESTA VERSÃO:

1. ✅ **Checkbox "Resposta em Áudio"** - Ativado por padrão
2. ✅ **Texto removido** - Não aparece mais "🎤 O app vai escutar..."
3. ✅ **Botão "Sobre"** - Adicionado (mostra SplashScreen)
4. ✅ **11 Tarefas pré-instaladas** - Prontas para uso

---

## 🧪 TESTES A REALIZAR APÓS INSTALAÇÃO:

### Teste 1: Histórico & Ferramentas
- [ ] Abra "Histórico & Ferramentas"
- [ ] Verifique se checkbox "Resposta em Áudio" está **ATIVADO** por padrão
- [ ] Verifique que **NÃO aparece** texto sobre 3 segundos
- [ ] Clique no botão "Sobre" e verifique se mostra a **SplashScreen**

### Teste 2: Tarefas Pré-instaladas
- [ ] Vá para "Atividades"
- [ ] Verifique se aparecem **11 tarefas**:
  1. Lavar o Rosto (06:45)
  2. Fazer Xixi (06:50)
  3. Vestir-se (06:55)
  4. Lavar as Mãos (07:00)
  5. Escovar os Dentes (07:05)
  6. Sair de Casa (07:15)
  7. Arrumar a Cama (17:10)
  8. Fazer Cocô (17:30)
  9. Escolher Brinquedo Banho (18:55)
  10. Tomar Banho (19:00)
  11. Guardar Brinquedos (20:30)

### Teste 3: Execução de Tarefa
- [ ] Execute uma tarefa qualquer
- [ ] Verifique se o **TTS lê os passos**
- [ ] Ao final, verifique se o **pop-up de tempo extra** aparece
- [ ] Se "Resposta em Áudio" estiver ativado, verifique se o **microfone ativa**

---

## 📱 SE O ÍCONE/LOGO NÃO ATUALIZOU:

**Lembre-se:** O logo deve estar em:
```
app\src\main\res\drawable\logo_pequenos_passos.png
```

**NÃO em:**
```
images\Ícone com nome - Pequenos Passos.png
```

**Para trocar o logo, execute:**
```cmd
.\substituir_logo.bat
```

---

## 📊 ARQUIVOS ÚTEIS:

- `compilar_e_instalar_v2.2.1.bat` - Compila e instala automaticamente
- `substituir_logo.bat` - Substitui logo automaticamente
- `GUIA_RAPIDO_TROCAR_LOGO.md` - Guia para trocar logo
- `MVP15_CORRECOES_UI_ICONES.md` - Documentação técnica completa

---

## ⚠️ TROUBLESHOOTING:

**Se compilação falhar:**
```cmd
.\gradlew.bat clean
.\gradlew.bat assembleDebug
```

**Se instalação falhar:**
1. Conecte o dispositivo via USB
2. Ative "Depuração USB" nas configurações do Android
3. Execute: `adb devices` (deve listar seu dispositivo)
4. Execute: `adb install -r app\build\outputs\apk\debug\app-debug.apk`

**Se o app não abrir:**
1. Desinstale completamente
2. Reinstale
3. Limpe cache: Configurações → Apps → Pequenos Passos → Limpar Cache

---

**EXECUTE: `.\compilar_e_instalar_v2.2.1.bat` 🚀**

