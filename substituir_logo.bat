@echo off
REM Script para substituir o logo da HomeScreen do app Pequenos Passos
REM Versão: 1.0
REM Data: 03/11/2025

echo ========================================
echo Pequenos Passos - Substituir Logo
echo ========================================
echo.

REM Verifica se o arquivo de origem existe
if not exist "images\Icone com nome - Pequenos Passos.png" (
    echo ERRO: Arquivo nao encontrado em: images\Icone com nome - Pequenos Passos.png
    echo.
    echo Por favor, coloque o novo logo na pasta: images\
    echo Com o nome: Icone com nome - Pequenos Passos.png
    pause
    exit /b 1
)

REM Faz backup do logo antigo
echo [1/5] Fazendo backup do logo antigo...
if exist "app\src\main\res\drawable\logo_pequenos_passos.png" (
    copy /Y "app\src\main\res\drawable\logo_pequenos_passos.png" "app\src\main\res\drawable\logo_pequenos_passos_OLD.png" >nul
    echo ✓ Backup criado: logo_pequenos_passos_OLD.png
) else (
    echo ! Logo antigo nao encontrado (primeira instalacao?)
)
echo.

REM Copia novo logo
echo [2/5] Copiando novo logo...
copy /Y "images\Icone com nome - Pequenos Passos.png" "app\src\main\res\drawable\logo_pequenos_passos.png" >nul
if errorlevel 1 (
    echo ERRO ao copiar o arquivo!
    pause
    exit /b 1
)
echo ✓ Logo copiado com sucesso!
echo.

REM Clean build
echo [3/5] Limpando build anterior...
call gradlew.bat clean
if errorlevel 1 (
    echo ERRO ao executar clean!
    pause
    exit /b 1
)
echo.

REM Compilar
echo [4/5] Compilando nova versao...
call gradlew.bat assembleDebug
if errorlevel 1 (
    echo ERRO ao compilar!
    pause
    exit /b 1
)
echo.

REM Tentar instalar
echo [5/5] Instalando no dispositivo...
echo.
echo Desinstalando versao antiga...
adb uninstall com.example.pequenospassos 2>nul
echo.
echo Instalando nova versao...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if errorlevel 1 (
    echo.
    echo AVISO: Instalacao automatica falhou!
    echo Por favor, instale manualmente o APK:
    echo app\build\outputs\apk\debug\app-debug.apk
) else (
    echo.
    echo ✓ Instalacao concluida com sucesso!
)
echo.

echo ========================================
echo Processo concluido!
echo ========================================
echo.
echo Abra o app e verifique se o logo foi atualizado.
echo Se nao aparecer o novo logo:
echo  1. Force Stop no app (Configuracoes ^> Apps)
echo  2. Limpe o cache do app
echo  3. Abra o app novamente
echo.
pause

