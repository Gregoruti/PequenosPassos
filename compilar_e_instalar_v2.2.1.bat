@echo off
REM Compilar e Instalar - Pequenos Passos
REM Versao: 2.3.0
REM Data: 03/11/2025
REM Correcoes: Logo atualizado, Checkbox audio ativado, texto removido, botao Sobre adicionado

echo ========================================
echo Pequenos Passos - Compilar e Instalar
echo Versao 2.3.0
echo ========================================
echo.
echo Mudancas nesta versao:
echo  - Logo da HomeScreen atualizado
echo  - Checkbox "Resposta em Audio" ativado por padrao
echo  - Texto sobre tempo removido
echo  - Botao "Sobre" adicionado (mostra SplashScreen)
echo  - 11 tarefas pre-instaladas
echo.
echo ========================================
echo.

echo [1/4] Limpando build anterior...
call gradlew.bat clean
if errorlevel 1 (
    echo.
    echo ERRO ao executar clean!
    pause
    exit /b 1
)
echo ✓ Clean concluido!
echo.

echo [2/4] Compilando projeto...
call gradlew.bat assembleDebug
if errorlevel 1 (
    echo.
    echo ERRO ao compilar!
    pause
    exit /b 1
)
echo ✓ Compilacao concluida!
echo.

echo [3/4] Desinstalando versao antiga...
adb uninstall com.example.pequenospassos 2>nul
echo.

echo [4/4] Instalando nova versao...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if errorlevel 1 (
    echo.
    echo AVISO: Instalacao automatica falhou!
    echo.
    echo Por favor, instale manualmente:
    echo   1. Conecte o dispositivo via USB
    echo   2. Ative Depuracao USB
    echo   3. Execute: adb install -r app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Ou arraste o APK para o dispositivo:
    echo   app\build\outputs\apk\debug\app-debug.apk
) else (
    echo.
    echo ✓ Instalacao concluida com sucesso!
)
echo.

echo ========================================
echo Processo concluido!
echo ========================================
echo.
echo Testes a realizar no app:
echo  1. Abra Historico e Ferramentas
echo  2. Verifique checkbox "Resposta em Audio" (deve estar ATIVADO)
echo  3. Verifique que NAO aparece texto sobre 3 segundos
echo  4. Verifique botao "Sobre" (deve mostrar SplashScreen)
echo  5. Verifique se tem 11 tarefas pre-instaladas
echo.
pause

