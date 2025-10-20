@echo off
echo ============================================
echo  BUILD E INSTALACAO - MVP-07 BUGFIX GALERIA
echo ============================================
echo.

echo [1/4] Limpando build anterior...
call gradlew.bat clean
echo.

echo [2/4] Compilando APK Debug com logs...
call gradlew.bat assembleDebug
echo.

echo [3/4] Instalando no dispositivo conectado...
call gradlew.bat installDebug
echo.

echo [4/4] Iniciando monitoramento de logs...
echo.
echo INSTRUCOES:
echo 1. O app foi instalado no dispositivo
echo 2. Abra o app e teste a galeria
echo 3. Veja os logs abaixo:
echo.
echo ============================================
echo  LOGS DO IMAGEPICKER (Ctrl+C para parar)
echo ============================================
echo.

adb logcat -s ImagePicker:D -s ImagePicker:W -s ImagePicker:E

pause

