@echo off
echo ============================================
echo  COMMIT v2.5.1 - 4 Correcoes Criticas de UX
echo ============================================
echo.

echo [1/5] Verificando status do Git...
git status
echo.

echo [2/5] Adicionando arquivos modificados...
git add .
echo Arquivos adicionados!
echo.

echo [3/5] Criando commit local...
git commit -m "fix(v2.5.1): 4 Correcoes criticas de UX - TTS, rotacao, landscape, debounce

Correcao 1: TTS concordancia genero feminino (duas estrelas)
- TaskCompletionScreen.kt: numero por extenso feminino (uma, duas, tres...)

Correcao 2: TTS nao repete ao rotacionar (2 iteracoes)
- TaskCompletionScreen.kt: rememberSaveable + remocao DisposableEffect
- TaskExecutionViewModel.kt: lastSpokenStepIndex + taskTitleAlreadySpoken

Correcao 3: Imagem visivel em landscape
- TaskExecutionScreen.kt: verticalScroll + heightIn adaptativo por orientacao

Correcao 4: Debounce avanco de passos (1.5s)
- TaskExecutionViewModel.kt: ADVANCE_DEBOUNCE_MS entre avancos

Documentacao:
- CHANGELOG.md: 4 correcoes detalhadas + erro Gradle documentado
- GUIDELINES.md: Secao 10.4 Erros Conhecidos de Compilacao
- V2.5.1_CORRECOES_PASSO_A_PASSO.md: Historico completo
- Headers atualizados nos 3 arquivos de codigo (rastreabilidade)"
echo Commit local criado!
echo.

echo [4/5] Fazendo push para repositorio remoto...
git push
echo Push concluido!
echo.

echo [5/5] Verificando log do ultimo commit...
git log --oneline -3
echo.

echo ============================================
echo  COMMIT v2.5.1 CONCLUIDO COM SUCESSO!
echo ============================================
pause

