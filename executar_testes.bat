@echo off
echo ========================================
echo Executando Testes Unitarios - MVP-07
echo ========================================
echo.

echo [1/3] Compilando projeto...
call gradlew.bat compileDebugUnitTestKotlin
if %ERRORLEVEL% NEQ 0 (
    echo ERRO na compilacao dos testes!
    pause
    exit /b 1
)

echo.
echo [2/3] Executando testes unitarios...
call gradlew.bat test --continue

echo.
echo [3/3] Gerando relatorio HTML...
echo.
echo Relatorio gerado em: app\build\reports\tests\testDebugUnitTest\index.html
echo.

if %ERRORLEVEL% EQU 0 (
    echo ========================================
    echo TESTES CONCLUIDOS COM SUCESSO!
    echo ========================================
) else (
    echo ========================================
    echo ALGUNS TESTES FALHARAM - Verifique o relatorio
    echo ========================================
)

echo.
echo Pressione qualquer tecla para abrir o relatorio...
pause >nul
start app\build\reports\tests\testDebugUnitTest\index.html

