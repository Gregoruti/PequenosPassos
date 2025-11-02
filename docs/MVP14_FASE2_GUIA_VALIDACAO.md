# MVP-14 FASE 2 - GUIA DE VALIDAÇÃO PRÁTICA

**Data:** 01/11/2025  
**Fase:** 2 de 6 - Checkbox em Histórico & Ferramentas  
**Status:** ✅ IMPLEMENTADA - Pronta para Teste  

---

## 📋 COMANDOS PARA COMPILAR E INSTALAR

### 1. Compilar o Projeto

Abra o PowerShell e execute:

```powershell
cd D:\Softwares\PequenosPassos
.\gradlew clean assembleDebug
```

**Aguarde:** ~2-3 minutos  
**Resultado esperado:** BUILD SUCCESSFUL

---

### 2. Instalar no Dispositivo

```powershell
.\gradlew installDebug
```

**Ou use o script:**
```powershell
.\compilar_e_instalar.bat
```

---

## ✅ TESTES A REALIZAR NO SMARTPHONE

### 🎯 TESTE 1: Verificar se Checkbox Aparece

**Objetivo:** Validar que o novo checkbox está visível na tela

**Passos:**
1. Abra o app "Pequenos Passos"
2. Na tela inicial, clique em **"Histórico & Ferramentas"** (ou **"Histórico"**)
3. Role a tela até a seção **"Ferramentas de Teste"**

**✅ Resultado Esperado:**
- Você deve ver 2 checkboxes:
  1. ✅ "Perguntar se deseja mais tempo"
  2. ✅ **"Resposta em Áudio"** ← NOVO!

**❌ Se não aparecer:**
- Verifique se a versão foi instalada corretamente
- Desinstale o app e reinstale

---

### 🎯 TESTE 2: Verificar Estado Inicial

**Objetivo:** Validar que o checkbox começa desabilitado

**Passos:**
1. Observe os 2 checkboxes
2. Verifique o estado de "Resposta em Áudio"

**✅ Resultado Esperado:**
- [ ] "Resposta em Áudio" está **desmarcado** (padrão: false)
- Checkbox pode estar **acinzentado/desabilitado** (se "Perguntar se deseja mais tempo" estiver desmarcado)

---

### 🎯 TESTE 3: Habilitar Checkbox de Tempo Extra

**Objetivo:** Validar dependência entre checkboxes

**Passos:**
1. Se "Perguntar se deseja mais tempo" estiver desmarcado:
   - Clique nele para marcar ✅
2. Observe "Resposta em Áudio"

**✅ Resultado Esperado:**
- Checkbox "Resposta em Áudio" agora está **habilitado** (não acinzentado)
- Ainda está desmarcado, mas você pode clicar nele

**❌ Se checkbox não habilitar:**
- Há um erro na lógica de enabled
- Reporte o problema

---

### 🎯 TESTE 4: Marcar "Resposta em Áudio"

**Objetivo:** Validar que checkbox pode ser marcado

**Passos:**
1. Certifique-se que "Perguntar se deseja mais tempo" está ✅ marcado
2. Clique em "Resposta em Áudio" para marcar ✅

**✅ Resultado Esperado:**
- Checkbox fica marcado ✅
- Aparece um texto explicativo abaixo:
  - **"🎤 O app vai escutar sua resposta por 3 segundos"**
- Texto do checkbox fica em **negrito**

**❌ Se não aparecer o texto:**
- Há um erro na UI
- Reporte o problema

---

### 🎯 TESTE 5: Persistência - Fechar e Reabrir App

**Objetivo:** Validar que a preferência é salva no banco de dados

**Passos:**
1. Com "Resposta em Áudio" ✅ marcado
2. **Feche completamente o app** (não apenas minimizar)
   - Vá em Configurações → Apps → Pequenos Passos → Forçar Parada
   - Ou deslize o app para fora da lista de recentes
3. **Reabra o app**
4. Vá em "Histórico & Ferramentas"
5. Verifique o checkbox "Resposta em Áudio"

**✅ Resultado Esperado:**
- Checkbox continua ✅ marcado
- Texto explicativo continua aparecendo
- Preferência foi **persistida** no banco de dados

**❌ Se voltar desmarcado:**
- **PROBLEMA CRÍTICO** - Não está salvando no banco
- Verifique logs do Logcat
- Reporte o problema

---

### 🎯 TESTE 6: Desmarcar e Verificar Persistência

**Objetivo:** Validar que desmarcação também persiste

**Passos:**
1. Com checkbox ✅ marcado
2. Clique para **desmarcar** ❌
3. Texto explicativo deve **desaparecer**
4. Feche o app completamente
5. Reabra o app
6. Vá em "Histórico & Ferramentas"
7. Verifique o checkbox

**✅ Resultado Esperado:**
- Checkbox está ❌ desmarcado
- Texto explicativo não aparece
- Estado foi persistido

---

### 🎯 TESTE 7: Desabilitar Tempo Extra Desabilita Voz

**Objetivo:** Validar que desmarcar "Perguntar se deseja mais tempo" desabilita "Resposta em Áudio"

**Passos:**
1. Marque ✅ "Perguntar se deseja mais tempo"
2. Marque ✅ "Resposta em Áudio"
3. Agora **desmarque** "Perguntar se deseja mais tempo"
4. Observe "Resposta em Áudio"

**✅ Resultado Esperado:**
- Checkbox "Resposta em Áudio" fica **acinzentado/desabilitado**
- Ainda pode estar marcado, mas não pode ser clicado
- Texto explicativo desaparece (ou fica cinza)

**❌ Se continuar habilitado:**
- Lógica de `enabled` não está funcionando
- Reporte o problema

---

### 🎯 TESTE 8: Texto Muda de Cor/Estilo

**Objetivo:** Validar feedback visual ao habilitar/desabilitar

**Passos:**
1. Alterne entre estados:
   - "Perguntar tempo extra" ❌ → "Resposta em Áudio" fica cinza
   - "Perguntar tempo extra" ✅ → "Resposta em Áudio" fica preto/normal
   - "Resposta em Áudio" ❌ → Texto normal
   - "Resposta em Áudio" ✅ → Texto em **negrito**

**✅ Resultado Esperado:**
- Cores e estilos mudam conforme estado
- Feedback visual claro

---

## 📊 CHECKLIST DE VALIDAÇÃO

Marque cada teste conforme for executando:

- [ ] TESTE 1: Checkbox aparece ✅
- [ ] TESTE 2: Estado inicial correto (desmarcado) ✅
- [ ] TESTE 3: Habilita quando tempo extra ativo ✅
- [ ] TESTE 4: Texto explicativo aparece ✅
- [ ] TESTE 5: Persistência ao fechar/reabrir ✅
- [ ] TESTE 6: Desmarcação persiste ✅
- [ ] TESTE 7: Desabilita com tempo extra inativo ✅
- [ ] TESTE 8: Feedback visual correto ✅

**Meta:** 8/8 testes passando ✅

---

## 🐛 SE ENCONTRAR PROBLEMAS

### Problema: Checkbox não aparece

**Possíveis causas:**
1. Versão antiga do app instalada
2. Compilação falhou
3. Instalação incompleta

**Solução:**
1. Desinstale o app completamente
2. Recompile: `.\gradlew clean assembleDebug`
3. Reinstale: `.\gradlew installDebug`

---

### Problema: Checkbox não persiste

**Possíveis causas:**
1. Migration não executou
2. Erro no repository
3. Banco de dados corrompido

**Solução:**
1. Verifique logs do Logcat:
   ```
   adb logcat | findstr "pequenospassos"
   ```
2. Desinstale app (limpa banco)
3. Reinstale app (aplica migrations)
4. Teste novamente

---

### Problema: Checkbox não habilita/desabilita

**Possíveis causas:**
1. Lógica de `enabled` incorreta
2. Estado não está sendo observado

**Solução:**
1. Verifique código em `HistoryScreen.kt` linha ~218
2. Confirme: `enabled = askExtraTimeChecked`
3. Se erro persistir, reporte

---

## 📱 COMO ACESSAR LOGS (SE NECESSÁRIO)

### 1. Via Android Studio

1. Abra Android Studio
2. Conecte o dispositivo via USB
3. Aba "Logcat" na parte inferior
4. Filtro: "pequenospassos"

### 2. Via ADB (Linha de Comando)

```powershell
adb logcat | findstr "pequenospassos"
```

**Procure por:**
- `HistoryViewModel`
- `AppSettingsRepository`
- `enableVoiceResponse`
- Erros de SQLite

---

## 🎯 PRÓXIMOS PASSOS APÓS VALIDAÇÃO

### ✅ Se todos os testes passarem:

1. **Documentar sucesso** em `MVP14_FASE2_CONCLUSAO.md`
2. **Fazer commit** da Fase 2
3. **Iniciar Fase 3** (VoiceCommandParser)

### ❌ Se algum teste falhar:

1. **Documentar problema** específico
2. **Coletar logs** do Logcat
3. **Reportar problema** com detalhes
4. **Corrigir** antes de avançar

---

## 📝 NOTAS IMPORTANTES

### O que esta fase NÃO faz ainda:

- ❌ Não ativa o ASR (reconhecimento de voz)
- ❌ Não escuta comandos ainda
- ❌ Não interfere na execução de tarefas
- ✅ Apenas SALVA a preferência no banco

### O que será implementado nas próximas fases:

- **Fase 3:** Parser de comandos de voz
- **Fase 4:** ASR com timeout
- **Fase 5:** Integração com pop-up
- **Fase 6:** Polimentos finais

---

## 🎉 CONCLUSÃO

A Fase 2 implementa **APENAS o checkbox de configuração**.

**Objetivo:** Preparar a interface para que, nas próximas fases, possamos ler esta preferência e decidir se ativa ou não o ASR.

**Validação bem-sucedida = Pronto para Fase 3!**

---

**Criado:** 01/11/2025  
**Code Assistant:** Claude Sonnet 4.5 (GitHub Copilot)  
**Status:** 📋 Aguardando Validação Prática no Dispositivo

