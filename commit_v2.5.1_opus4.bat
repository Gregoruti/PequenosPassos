@echo off
echo ============================================
echo  COMMIT v2.5.1 - Transicao Claude Opus 4
echo ============================================
echo.

D:
cd D:\Softwares\PequenosPassos

echo [1/8] Verificando status do Git...
git status
echo.

echo [2/8] Adicionando arquivos modificados...
git add .
echo Arquivos adicionados!
echo.

echo [3/8] Criando commit local na branch atual...
git commit -m "docs(v2.5.1): Transicao de Code Assistant para Claude Opus 4 - Revisao geral de documentacao antes de nova fase - README.md: Reescrito com estado real do projeto (v2.5.0) - CHANGELOG.md: Adicionada entrada v2.5.1 com historico de Code Assistants - GUIDELINES.md: Header atualizado com nova fase - APK v2.5.0 gerado e validado (app-debug.apk) - Historico de transicoes: Claude Sonnet 4.5 -> GPT-4.1 -> Claude Sonnet 4.5 -> Claude Opus 4 - Motivo: Fim das requisicoes premium do Claude Sonnet 4.5 no GitHub Copilot - Code Assistant: Claude Opus 4 (GitHub Copilot) - Status: 100%% funcional - Build: SUCCESS"
echo Commit criado!
echo.

echo [4/8] Verificando commit criado...
git log -1 --oneline
echo.

echo [5/8] Enviando branch atual para remoto...
git push origin feature/mvp-14-asr-popups
echo.

echo [6/8] Criando nova branch feature/v2.5.1-opus4-review...
git checkout -b feature/v2.5.1-opus4-review
echo Nova branch criada!
echo.

echo [7/8] Enviando nova branch para remoto...
git push -u origin feature/v2.5.1-opus4-review
echo.

echo [8/8] Criando tag v2.5.1...
git tag -a v2.5.1 -m "v2.5.1 - Transicao para Claude Opus 4 - Documentacao revisada"
git push origin v2.5.1
echo.

echo ============================================
echo  COMMIT E BRANCH CONCLUIDOS COM SUCESSO!
echo ============================================
echo.
echo  Branch anterior: feature/mvp-14-asr-popups
echo  Nova branch:     feature/v2.5.1-opus4-review
echo  Tag criada:      v2.5.1
echo  Code Assistant:  Claude Opus 4
echo.
pause

