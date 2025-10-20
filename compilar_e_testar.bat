@echo off
echo ========================================
echo   COMPILAR E TESTAR - MVP-07 FASE 3
echo ========================================
echo.

echo [1/4] Limpando projeto anterior...
call gradlew clean
if errorlevel 1 (
    echo ERRO ao limpar projeto!
    pause
    exit /b 1
)

echo.
echo [2/4] Compilando projeto em modo debug...
call gradlew assembleDebug
if errorlevel 1 (
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo.
echo [3/4] Instalando APK no dispositivo...
call gradlew installDebug
if errorlevel 1 (
    echo AVISO: Falha na instalacao normal. Tentando reinstalacao forcada...
    adb uninstall com.example.pequenospassos
    call gradlew installDebug
    if errorlevel 1 (
        echo ERRO ao instalar! Verifique se o dispositivo esta conectado.
        pause
        exit /b 1
    )
)

echo.
echo ========================================
echo   SUCESSO!
echo ========================================
echo.
echo O aplicativo foi compilado e instalado.
echo.
echo PROXIMOS PASSOS:
echo 1. Abra o app "Pequenos Passos" no dispositivo
echo 2. Teste o fluxo completo:
echo    - Criar tarefa com categoria e imagem
echo    - Adicionar steps com imagens e timers
echo    - Visualizar na lista
echo    - Executar tarefa
echo.
echo 3. Anote qualquer bug ou comportamento estranho
echo.
pause

