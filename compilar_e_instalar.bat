@echo off
echo ========================================
echo Compilando o APK com a correção...
echo ========================================
call gradlew.bat assembleDebug
if %ERRORLEVEL% NEQ 0 (
    echo ERRO na compilação!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Instalando o APK no dispositivo...
echo ========================================
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %ERRORLEVEL% NEQ 0 (
    echo ERRO na instalação!
    pause
    exit /b 1
)

echo.
echo ========================================
echo APK instalado com sucesso!
echo ========================================
pause

