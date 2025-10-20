@echo off
echo ============================================
echo  EXECUCAO DE TESTES UNITARIOS - MVP 01-07
echo ============================================
echo.
echo [1/4] Parando daemon do Gradle...
call gradlew.bat --stop
echo.

echo [2/4] Limpando cache de build...
if exist app\build rmdir /S /Q app\build
echo Cache limpo!
echo.

echo [3/4] Executando testes unitarios...
echo (Isso pode levar 2-3 minutos)
echo.
call gradlew.bat testDebugUnitTest --console=plain
echo.

echo [4/4] Gerando relatorio de cobertura...
call gradlew.bat jacocoTestReport
echo.

echo ============================================
echo  TESTES CONCLUIDOS!
echo ============================================
echo.
echo Relatorios disponiveis em:
echo - app\build\reports\tests\testDebugUnitTest\index.html
echo - app\build\reports\jacoco\test\html\index.html
echo.
pause

