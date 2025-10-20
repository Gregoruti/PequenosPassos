@echo off
echo ============================================
echo  INSTALACAO RAPIDA - DEBUG GALERIA v2
echo ============================================
echo.

echo Compilando e instalando versao com logs detalhados...
call gradlew.bat installDebug
echo.

echo ============================================
echo  TESTE A GALERIA AGORA!
echo ============================================
echo.
echo INSTRUCOES:
echo 1. Abra o app no dispositivo
echo 2. Va em TaskForm e clique em "Galeria"
echo 3. Selecione UMA IMAGEM
echo 4. Volte aqui e veja os logs abaixo
echo.
echo Pressione ENTER quando estiver pronto...
pause > nul
echo.
echo ============================================
echo  LOGS DETALHADOS (aguardando sua acao...)
echo ============================================
echo.

adb logcat -c
adb logcat ImagePicker:* *:S

