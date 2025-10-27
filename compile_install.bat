@echo off
rem compile_install.bat
rem Execute a partir da raiz do projeto (onde existe gradlew.bat)

setlocal

echo ----------------------------------------
echo Construindo APK (clean + assembleDebug)...
echo ----------------------------------------
call gradlew.bat clean assembleDebug --no-daemon
if errorlevel 1 (
  echo ERRO: build falhou.
  exit /b 1
)

set "APK_PATH=app\build\outputs\apk\debug\app-debug.apk"
if not exist "%APK_PATH%" (
  echo ERRO: APK nao encontrado em %APK_PATH%
  exit /b 1
)

echo ----------------------------------------
echo Listando dispositivos ADB conectados...
echo ----------------------------------------
adb devices

echo ----------------------------------------
echo Instalando APK: %APK_PATH%
echo ----------------------------------------
adb install -r "%APK_PATH%"
if errorlevel 1 (
  echo ERRO: instalacao falhou.
  exit /b 1
)

echo ----------------------------------------
echo SUCESSO: APK instalado.
echo ----------------------------------------
endlocal
exit /b 0

