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
git commit -m "fix(v2.5.1): 4 Correcoes criticas de UX - TTS rotacao landscape debounce"
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

