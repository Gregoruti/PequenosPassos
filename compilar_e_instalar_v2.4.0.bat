@echo off
REM Script de compilação e instalação - Versão 2.4.0
REM Versão de apresentação com emails de feedback
REM Atualizado em: 07/11/2025
REM Code Assistant: Claude Sonnet 4.5 (GitHub Copilot)

echo ========================================
echo PEQUENOS PASSOS - VERSAO 2.4.0
echo Versao de Apresentacao com Contatos
echo ========================================
echo.

echo [1/5] Limpando build anterior...
call gradlew.bat clean
if %ERRORLEVEL% NEQ 0 (
    echo ERRO: Falha ao limpar build anterior
    pause
    exit /b 1
)
echo OK - Build anterior limpo
echo.

echo [2/5] Compilando aplicativo (modo debug)...
call gradlew.bat assembleDebug
if %ERRORLEVEL% NEQ 0 (
    echo ERRO: Falha na compilacao
    pause
    exit /b 1
)
echo OK - Aplicativo compilado
echo.

echo [3/5] Verificando dispositivo Android conectado...
adb devices
echo.

echo [4/5] Instalando aplicativo no dispositivo...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %ERRORLEVEL% NEQ 0 (
    echo ERRO: Falha na instalacao
    echo Verifique se o dispositivo esta conectado e com depuracao USB habilitada
    pause
    exit /b 1
)
echo OK - Aplicativo instalado
echo.

echo [5/5] Iniciando aplicativo...
adb shell am start -n com.pequenospassos/.MainActivity
echo.

echo ========================================
echo INSTALACAO CONCLUIDA COM SUCESSO!
echo ========================================
echo.
echo Versao instalada: 2.4.0
echo Novidades:
echo - Emails de contato na SplashScreen
echo - 2100394@aluno.univesp.br
echo - gregoruti@gmail.com
echo.
echo TESTES A REALIZAR:
echo 1. Verificar SplashScreen (Versao 2.4.0 + emails)
echo 2. Testar botao "Sobre" (deve mostrar emails)
echo 3. Validar todas funcionalidades (11 tarefas, ASR, TTS)
echo.
pause

