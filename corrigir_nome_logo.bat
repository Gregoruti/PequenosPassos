d@echo off
REM Script para corrigir nome do arquivo do logo
REM O Android NÃO aceita letras maiúsculas em nomes de arquivos de recursos!

echo ========================================
echo Corrigindo nome do arquivo do logo
echo ========================================
echo.

echo PROBLEMA DETECTADO:
echo   Arquivo atual: icone_com_nome_Pequenos_Passos.png
echo   Problema: Contem letras MAIUSCULAS (P e S)
echo   Android: NAO aceita maiusculas em nomes de recursos!
echo.

echo SOLUCAO:
echo   Renomeando para: icone_com_nome_pequenos_passos.png
echo   (Todas as letras em minusculas)
echo.

if not exist "app\src\main\res\drawable\icone_com_nome_Pequenos_Passos.png" (
    echo ERRO: Arquivo nao encontrado!
    echo Esperado: app\src\main\res\drawable\icone_com_nome_Pequenos_Passos.png
    pause
    exit /b 1
)

echo Renomeando arquivo...
ren "app\src\main\res\drawable\icone_com_nome_Pequenos_Passos.png" "icone_com_nome_pequenos_passos.png"

if errorlevel 1 (
    echo ERRO ao renomear arquivo!
    pause
    exit /b 1
)

echo ✓ Arquivo renomeado com sucesso!
echo.
echo Agora execute:
echo   .\compilar_e_instalar_v2.2.1.bat
echo.
pause

