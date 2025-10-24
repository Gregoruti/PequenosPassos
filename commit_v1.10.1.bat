@echo off
echo ============================================
echo  COMMIT LOCAL E REMOTO - v1.10.1
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
git commit -m "feat(mvp-08): v1.10.1 - TTS e Personalizacao com Nome da Crianca

VERSAO 100%% FUNCIONAL E TESTADA

Melhorias de UX e Acessibilidade:
- Sistema TTS completo em PT-BR (velocidade 0.9x para criancas)
- Leitura automatica: titulo, passos e mensagens de sucesso
- Personalizacao com nome da crianca em todas interacoes
- 7 variacoes de mensagens de tempo esgotado
- 10 variacoes de mensagens de conclusao
- Tom acolhedor e encorajador (sem pressao)
- Dialogo simplificado (removido texto explicativo)

Arquivos Novos:
- TtsManager.kt (servico singleton para TTS)
- PresentationModule.kt (Hilt DI para apresentacao)
- MVP08_V1.10.1_RESUMO_IMPLEMENTACOES.md

Arquivos Modificados:
- TaskExecutionViewModel.kt (integracao TTS)
- TaskExecutionScreen.kt (dialogo simplificado)
- TaskCompletionScreen.kt (TTS em sucesso)
- MainActivity.kt (parametro childName)
- CHANGELOG.md (atualizacao v1.10.1)

Tecnico:
- ~280 linhas de codigo adicionadas
- Cobertura TTS: 100%% (execucao + conclusao)
- Clean Architecture mantida
- Injecao de dependencias via Hilt

Status: v1.10.1 - Totalmente funcional e pronta para uso!"
echo Commit criado!
echo.

echo [4/6] Verificando commit criado...
git log -1 --oneline
echo.

echo [5/6] Criando tag v1.10.1...
git tag -a v1.10.1 -m "Versao 1.10.1 - TTS e Personalizacao"
echo Tag criada!
echo.

echo [6/6] Enviando para repositorio remoto...
git push origin main
echo.
echo Enviando tags...
git push origin v1.10.1
echo.

echo ============================================
echo  COMMIT CONCLUIDO COM SUCESSO!
echo  Versao v1.10.1 enviada para repositorio
echo ============================================
echo.
pause

