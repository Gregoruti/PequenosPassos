@echo off
echo ============================================
echo  COMMIT - Atualizacao GUIDELINES.md
echo ============================================
echo.

echo [1/5] Verificando status do Git...
git status
echo.

echo [2/5] Adicionando arquivos modificados...
git add docs/GUIDELINES.md
git add commit_guidelines_update.bat
echo Arquivos adicionados!
echo.

echo [3/5] Criando commit local...
git commit -m "docs(guidelines): adicionar secao critica sobre commits com PowerShell

SECAO 5.3 - Estrategia de Commits com PowerShell (CRITICO)

Problemas Documentados:
- Operador & nao permitido no PowerShell
- Operador && nao suportado (ou limitado em versoes antigas)
- Arquivos .bat precisam de .\ para executar
- gradlew precisa de .\ no PowerShell

Solucoes Implementadas:
- Template completo de arquivo .bat para commits
- Estrategia de commits por versao
- Checklist de commit e boas praticas
- Comandos PowerShell alternativos (ponto-e-virgula)

Secao Critica para IA Assistants:
- Documentacao de comportamento problematico do GitHub Copilot
- Loop de erro: IA insiste em usar && mesmo apos correcoes
- Regra de Ouro para IA: NUNCA usar && ou & no PowerShell
- Template de resposta correta para IA

Arquivos Modificados:
- docs/GUIDELINES.md (secao 5.3 completa adicionada)
- commit_guidelines_update.bat (novo)

Impacto: Critico - Melhora interacao IA + Terminal PowerShell"
echo Commit criado!
echo.

echo [4/5] Verificando commit criado...
git log -1 --oneline
echo.

echo [5/5] Enviando para repositorio remoto...
git push
echo.

echo ============================================
echo  COMMIT CONCLUIDO COM SUCESSO!
echo ============================================
echo.
pause

