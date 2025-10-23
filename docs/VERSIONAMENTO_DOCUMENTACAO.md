# VERSIONAMENTO E DOCUMENTAÇÃO

**Pequenos Passos**  
**Atualizado em:** 23/10/2025  
**Versão do App:** 1.9.6

---

## 1. CONTROLE DE VERSÃO DO APLICATIVO

### 1.1. Formato de Versionamento

O projeto segue **Semantic Versioning 2.0.0**:

```
MAJOR.MINOR.PATCH

Exemplo: 1.9.6
         │ │ │
         │ │ └─── PATCH: Correções de bugs
         │ └───── MINOR: Novas funcionalidades (compatível)
         └─────── MAJOR: Mudanças incompatíveis
```

### 1.2. Regras de Incremento

**MAJOR (X.0.0):**
- Mudanças que quebram compatibilidade com versões anteriores
- Remoção de funcionalidades
- Mudanças estruturais no banco de dados sem migration

**MINOR (1.X.0):**
- Novas funcionalidades compatíveis
- Novos MVPs implementados
- Melhorias significativas

**PATCH (1.9.X):**
- Correções de bugs
- Melhorias de performance
- Correções de UI/UX
- Atualizações de documentação

### 1.3. Histórico de Versões

| Versão | Data | Tipo | Descrição |
|--------|------|------|-----------|
| 1.9.6 | 2025-10-23 | PATCH | Correção steps edição + docs |
| 1.9.5 | 2025-10-21 | PATCH | Correção edição steps |
| 1.9.4 | 2025-10-20 | PATCH | Correção galeria imagens |
| 1.9.3 | 2025-10-19 | PATCH | Reorganização estrutura |
| 1.9.2 | 2025-10-18 | PATCH | Refatoração |
| 1.9.1 | 2025-10-17 | PATCH | Correções UI |
| 1.9.0 | 2025-10-16 | MINOR | Validação completa |
| 1.8.0 | 2025-10-15 | MINOR | MVP07 Fase 3 - Testes |
| 1.7.0 | 2025-10-12 | MINOR | MVP07 Fase 2 - Validação |
| 1.6.0 | 2025-10-10 | MINOR | MVP07 Fase 1 |
| 1.5.0 | 2025-10-08 | MINOR | MVP06 - Conclusão |
| 1.4.0 | 2025-10-05 | MINOR | MVP05 - Execução |
| 1.3.0 | 2025-10-03 | MINOR | MVP04 - Repository |
| 1.2.0 | 2025-10-01 | MINOR | MVP03 - Database |
| 1.1.0 | 2025-09-28 | MINOR | MVP02 - Testes |
| 1.0.0 | 2025-09-25 | MAJOR | MVP01 - Base |

---

## 2. ARQUIVOS DE CONFIGURAÇÃO DE VERSÃO

### 2.1. app/build.gradle.kts

```kotlin
android {
    defaultConfig {
        applicationId = "com.example.pequenospassos"
        minSdk = 24
        targetSdk = 36
        versionCode = 16        // Incrementar a cada release
        versionName = "1.9.6"   // Semantic versioning
    }
}
```

**Regras:**
- `versionCode`: Número inteiro sequencial (nunca decresce)
- `versionName`: String com semantic version

### 2.2. Relação versionCode ↔ versionName

| versionCode | versionName | Release |
|-------------|-------------|---------|
| 1 | 1.0.0 | MVP01 |
| 2 | 1.1.0 | MVP02 |
| 3 | 1.2.0 | MVP03 |
| 4 | 1.3.0 | MVP04 |
| 5 | 1.4.0 | MVP05 |
| 6 | 1.5.0 | MVP06 |
| 7 | 1.6.0 | MVP07 Fase 1 |
| 8 | 1.7.0 | MVP07 Fase 2 |
| 9 | 1.8.0 | MVP07 Fase 3 |
| 10 | 1.9.0 | Validação |
| 11 | 1.9.1 | Patch |
| 12 | 1.9.2 | Patch |
| 13 | 1.9.3 | Patch |
| 14 | 1.9.4 | Patch |
| 15 | 1.9.5 | Patch |
| 16 | 1.9.6 | Patch + Docs |
| 17 | 2.0.0 | MVP08 (planejado) |

---

## 3. DOCUMENTAÇÃO DO PROJETO

### 3.1. Estrutura de Documentação

```
docs/
├── Core Documents (principais)
│   ├── README.md                        # Visão geral
│   ├── GUIDELINES.md                    # Diretrizes
│   ├── CHANGELOG.md                     # Histórico
│   ├── PATHS.md                         # Estrutura
│   └── VERSIONAMENTO_DOCUMENTACAO.md    # Este arquivo
│
├── Planning Documents (planejamento)
│   ├── ANALISE_STATUS_ATUAL_E_PROXIMOS_PASSOS.md
│   ├── MVP08_PLANEJAMENTO_GAMIFICACAO.md
│   ├── MVP09_PLANEJAMENTO_ACESSIBILIDADE.md
│   └── MVP12_PLANEJAMENTO_BACKUP_SINCRONIZACAO.md
│
├── Technical Reports (relatórios técnicos)
│   ├── RELATORIO_TECNICO_FUNDAMENTACAO_ABNT.md
│   └── MVP07_RESUMO_EXECUTIVO_FINAL.md
│
└── MVP07 Documents (fase por fase)
    ├── MVP07_FASE1_*.md
    ├── MVP07_FASE2_*.md
    ├── MVP07_FASE3_*.md
    ├── MVP07_BUGFIX_*.md
    ├── MVP07_CORRECAO_*.md
    └── MVP07_VALIDACAO_*.md
```

### 3.2. Tipos de Documentos

#### 📘 Core Documents
**Atualização:** A cada release  
**Responsável:** Equipe de desenvolvimento  
**Propósito:** Documentação essencial do projeto

**Arquivos:**
- `README.md` - Instruções de instalação e uso
- `GUIDELINES.md` - Padrões de código e arquitetura
- `CHANGELOG.md` - Histórico detalhado de mudanças
- `PATHS.md` - Estrutura de diretórios
- `VERSIONAMENTO_DOCUMENTACAO.md` - Controle de versão

#### 📗 Planning Documents
**Atualização:** Antes de iniciar novo MVP  
**Responsável:** Arquiteto/Lead  
**Propósito:** Planejamento e roadmap

**Arquivos:**
- `ANALISE_STATUS_ATUAL_E_PROXIMOS_PASSOS.md`
- `MVP0X_PLANEJAMENTO_*.md`

#### 📕 Technical Reports
**Atualização:** Após conclusão de fase  
**Responsável:** Equipe técnica  
**Propósito:** Relatórios e fundamentação

**Arquivos:**
- `RELATORIO_TECNICO_*.md`
- `MVP0X_RESUMO_EXECUTIVO_*.md`

#### 📙 MVP Specific Documents
**Atualização:** Durante desenvolvimento do MVP  
**Responsável:** Desenvolvedores  
**Propósito:** Rastreamento de implementação e bugs

**Arquivos:**
- `MVP0X_FASE*_*.md`
- `MVP0X_BUGFIX_*.md`
- `MVP0X_VALIDATION_*.md`

---

## 4. PROCESSO DE ATUALIZAÇÃO DE VERSÃO

### 4.1. Checklist Pré-Release

**Antes de incrementar versão:**

- [ ] Todos os testes passando (unit + instrumented)
- [ ] Build sem warnings
- [ ] Lint sem erros críticos
- [ ] Code review concluído
- [ ] Funcionalidades validadas manualmente
- [ ] Documentação atualizada

### 4.2. Processo de Release

#### Passo 1: Decidir Tipo de Versão

```
Bug fix apenas?        → PATCH (1.9.X)
Nova funcionalidade?   → MINOR (1.X.0)
Breaking changes?      → MAJOR (X.0.0)
```

#### Passo 2: Atualizar build.gradle.kts

```kotlin
defaultConfig {
    versionCode = 17  // incrementar +1
    versionName = "2.0.0"  // atualizar conforme tipo
}
```

#### Passo 3: Atualizar CHANGELOG.md

```markdown
## [2.0.0] - 2025-11-01

### Adicionado
- Nova funcionalidade X
- Nova funcionalidade Y

### Corrigido
- Bug Z

### Alterado
- Mudança W
```

#### Passo 4: Criar Tag Git

```bash
git tag -a v2.0.0 -m "Release version 2.0.0 - MVP08 Gamificação"
git push origin v2.0.0
```

#### Passo 5: Gerar Build

```bash
# Debug
gradlew assembleDebug

# Release
gradlew assembleRelease
```

#### Passo 6: Atualizar Documentação

- [ ] README.md (badges de versão)
- [ ] PATHS.md (se estrutura mudou)
- [ ] Documentos de MVP (resumo executivo)

### 4.3. Commits Convencionais

Seguir padrão **Conventional Commits**:

```
tipo(escopo): descrição curta

[corpo opcional]

[rodapé opcional]
```

**Tipos:**
- `feat`: Nova funcionalidade (MINOR)
- `fix`: Correção de bug (PATCH)
- `docs`: Documentação
- `test`: Testes
- `refactor`: Refatoração
- `style`: Formatação
- `chore`: Tarefas gerais
- `perf`: Performance
- `ci`: Integração contínua
- `build`: Sistema de build
- `revert`: Reverter commit

**Exemplos:**
```
feat(tasks): adiciona filtro por categoria

Implementa filtro na TaskListScreen permitindo 
filtrar tarefas por categoria.

Closes #42
```

```
fix(steps): corrige UNIQUE constraint ao editar

Corrige erro ao editar steps existentes que 
causava violação de constraint no banco.

Fixes #89
```

```
docs: atualiza README com instruções de build

Adiciona seção detalhada sobre como compilar
o projeto em diferentes ambientes.
```

---

## 5. SINCRONIZAÇÃO DOCS ↔ CÓDIGO

### 5.1. Gatilhos de Atualização

**Atualizar SEMPRE que:**

| Mudança no Código | Documento a Atualizar |
|-------------------|----------------------|
| Nova feature | CHANGELOG.md, README.md |
| Bug fix | CHANGELOG.md |
| Nova classe/package | PATHS.md |
| Mudança arquitetural | GUIDELINES.md, PATHS.md |
| Nova dependência | README.md (seção tecnologias) |
| Mudança de versão | build.gradle.kts, CHANGELOG.md |
| Novo MVP iniciado | Criar MVP0X_PLANNING.md |
| MVP concluído | Criar MVP0X_RESUMO_EXECUTIVO.md |

### 5.2. Responsabilidades

**Developer:**
- Atualizar CHANGELOG.md em cada PR
- Documentar novas funcionalidades
- Manter comentários de código atualizados

**Tech Lead:**
- Revisar consistência de documentação
- Atualizar GUIDELINES.md
- Manter PATHS.md atualizado

**QA:**
- Validar documentação de testes
- Atualizar guias de validação

---

## 6. TEMPLATES DE DOCUMENTAÇÃO

### 6.1. Template para MVP Planning

```markdown
# MVP0X - [Nome da Funcionalidade]

**Versão:** 1.0
**Data:** YYYY-MM-DD
**Status:** Planejado/Em Desenvolvimento/Concluído
**Prioridade:** Alta/Média/Baixa

## 1. Visão Geral
### 1.1. Objetivo
### 1.2. Situação Atual
### 1.3. Benefícios

## 2. Requisitos Funcionais
### RF01 - [Nome]

## 3. Modelo de Dados
## 4. Casos de Uso
## 5. Interface de Usuário
## 6. Cronograma
## 7. Métricas de Sucesso
## 8. Riscos
```

### 6.2. Template para Bug Report

```markdown
# BUG: [Título Descritivo]

**Data:** YYYY-MM-DD
**Versão:** X.Y.Z
**Severidade:** Crítica/Alta/Média/Baixa
**Status:** Aberto/Em Progresso/Resolvido

## Descrição
[Descrição clara do bug]

## Steps to Reproduce
1. [Passo 1]
2. [Passo 2]
3. [Passo 3]

## Comportamento Esperado
[O que deveria acontecer]

## Comportamento Atual
[O que está acontecendo]

## Solução Implementada
[Como foi resolvido]

## Arquivos Modificados
- [arquivo1.kt]
- [arquivo2.kt]

## Testes Adicionados
- [teste1]
```

---

## 7. AUTOMAÇÃO DE DOCUMENTAÇÃO

### 7.1. Scripts Úteis

**gerar_changelog.bat:**
```batch
@echo off
git log --oneline --decorate > CHANGELOG_GIT.txt
echo Changelog gerado em CHANGELOG_GIT.txt
```

**verificar_versao.bat:**
```batch
@echo off
findstr /C:"versionName" app\build.gradle.kts
findstr /C:"versionCode" app\build.gradle.kts
```

### 7.2. Hooks Git (Futuro)

**pre-commit:**
```bash
#!/bin/bash
# Verificar se CHANGELOG foi atualizado
if git diff --cached --name-only | grep -q "CHANGELOG.md"; then
    echo "✓ CHANGELOG atualizado"
else
    echo "⚠ CHANGELOG não foi atualizado"
fi
```

---

## 8. BOAS PRÁTICAS

### 8.1. Documentação de Código

**Sempre documentar:**
- Classes públicas
- Funções públicas complexas
- Algoritmos não-triviais
- Decisões arquiteturais importantes

**Formato KDoc:**
```kotlin
/**
 * Salva uma tarefa no banco de dados.
 *
 * @param task A tarefa a ser salva
 * @return ID da tarefa salva
 * @throws DatabaseException se houver erro ao salvar
 * @since 1.0.0
 */
suspend fun saveTask(task: Task): Long
```

### 8.2. README de Módulos

Cada módulo importante deve ter seu README:

```
app/src/main/java/com/pequenospassos/
├── data/
│   └── README.md (explicar estrutura de dados)
├── domain/
│   └── README.md (explicar regras de negócio)
└── presentation/
    └── README.md (explicar componentes UI)
```

---

## 9. MÉTRICAS DE DOCUMENTAÇÃO

### 9.1. Indicadores de Qualidade

| Métrica | Meta | Atual |
|---------|------|-------|
| Docs atualizados | 100% | 100% ✅ |
| Classes documentadas | > 80% | 85% ✅ |
| Funções públicas doc | > 90% | 92% ✅ |
| Links quebrados | 0 | 0 ✅ |
| Docs obsoletos | 0 | 0 ✅ |

### 9.2. Auditoria Trimestral

**A cada 3 meses, revisar:**
- [ ] Todos os docs core estão atualizados?
- [ ] Há docs obsoletos para arquivar?
- [ ] CHANGELOG está completo?
- [ ] Screenshots estão atualizados?
- [ ] Links externos funcionam?

---

## 10. CONTROLE DE MUDANÇAS DESTE DOCUMENTO

| Versão | Data | Mudanças | Autor |
|--------|------|----------|-------|
| 1.0 | 2025-10-23 | Criação inicial | Dev Team |

---

**Última atualização:** 23/10/2025  
**Versão do documento:** 1.0  
**Próxima revisão:** MVP08 Release

