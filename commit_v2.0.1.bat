@echo off
echo ============================================
echo  COMMIT LOCAL E REMOTO - v2.0.1 (2025-10-27)
echo ============================================
echo.
echo [1/6] Verificando status do Git...
git status
echo.
echo [2/6] Adicionando arquivos modificados...
git add .
echo Arquivos adicionados!
echo.
echo [3/6] Criando commit local...
git commit -m "feat(history): renomeia botão para 'Zerar dia' e remove botão de zerar estrelas\n\n- O botão 'Zerar Tarefas do Dia' foi renomeado para 'Zerar dia' na tela Histórico.\n- O botão 'Zerar Estrelas do Dia' foi removido.\n- Documentação e CHANGELOG.md atualizados.\n\nStatus: v2.0.1 - 100%% completo"
echo Commit criado!
echo.
echo [4/6] Verificando commit criado...
git log -1 --oneline
echo.
echo [5/6] Criando tag v2.0.1...
git tag -a v2.0.1 -m "Versão 2.0.1 - Botão 'Zerar dia' e documentação atualizada"
echo Tag criada!
echo.
echo [6/6] Enviando para repositório remoto...
git push origin main
echo.
echo Enviando tags...
git push origin v2.0.1
echo.
echo ============================================
echo  COMMIT CONCLUÍDO COM SUCESSO!
echo ============================================
echo.
pause

