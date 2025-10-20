@echo off
echo ============================================
echo  COMMIT LOCAL E REMOTO - MVP-07
echo ============================================
echo.

echo [1/5] Verificando status do Git...
git status
echo.

echo [2/5] Adicionando arquivos modificados...
git add .
echo Arquivos adicionados!
echo.

echo [3/5] Criando commit local...
git commit -m "feat(mvp-07): Finalizacao Fase 3 - TaskListScreen + Relatorio Tecnico de Testes

- Atualizado TaskListScreen com indicadores visuais (categoria, imagens, duracao)
- Criado TaskListViewModel com calculo de metadados
- Corrigido MainActivity.kt (codigo duplicado removido)
- Criado RELATORIO_TECNICO_TESTES_MVP01_07.md (70+ paginas)
- Documentacao completa com fundamentacao teorica
- Bugfix: Selecao de imagens da galeria (takePersistableUriPermission)
- Atualizados: MVP07_STATUS_ATUAL.md, MVP07_CHANGELOG.md, MVP07_RESUMO_EXECUTIVO.md

Arquivos modificados:
- TaskListScreen.kt
- TaskListViewModel.kt (novo)
- MainActivity.kt (corrigido)
- ImagePicker.kt (bugfix galeria)
- MVP07_*.md (atualizacoes)
- RELATORIO_TECNICO_TESTES_MVP01_07.md (novo)

Status: MVP-07 Fase 3 - 90%% completo (falta OnboardingScreen Hub)"
echo.

echo [4/5] Verificando commit criado...
git log -1 --oneline
echo.

echo [5/5] Enviando para repositorio remoto...
git push origin main
echo.

echo ============================================
echo  COMMIT CONCLUIDO COM SUCESSO!
echo ============================================
echo.
pause

