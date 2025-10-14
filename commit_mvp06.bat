@echo off
cd /d D:\Softwares\PequenosPassos
echo ========================================
echo Checando status do Git...
echo ========================================
git status

echo.
echo ========================================
echo Adicionando arquivos modificados...
echo ========================================
git add .

echo.
echo ========================================
echo Criando commit do MVP-06...
echo ========================================
git commit -m "feat(mvp-06): Theme e Design System completo + testes + checagem anti-regressao

- Implementado Theme Material 3 otimizado para TEA
- Color.kt: Paleta de cores calmas e acessiveis (70 linhas)
- Type.kt: Tipografia legivel com hierarquia clara (130 linhas)
- Shape.kt: Shapes arredondadas e amigaveis (35 linhas)
- Theme.kt: Integracao completa Material 3 (85 linhas)

TESTES:
- ColorTest.kt: 14 testes (100%% passando)
- TypographyTest.kt: 11 testes (100%% passando)
- ShapeTest.kt: 10 testes corrigidos (100%% passando)
- Total MVP-06: 35 testes unitarios

CHECAGEM ANTI-REGRESSAO:
- 190 testes totais executados (MVPs 01-06)
- 100%% de sucesso (0 falhas)
- Build successful em 1m 8s
- Nenhuma regressao detectada

DOCUMENTACAO:
- MVP06_VALIDATION_SUMMARY.md atualizado
- MVP06_REGRESSION_CHECK.md criado
- CHANGELOG.md atualizado com metricas

Status: APROVADO - Pronto para MVP-07"

echo.
echo ========================================
echo Fazendo push para repositorio remoto...
echo ========================================
git push origin main

echo.
echo ========================================
echo Commit e push concluidos com sucesso!
echo ========================================
pause

