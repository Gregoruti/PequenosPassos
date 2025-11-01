<!--
Arquivo: docs/GUIDELINES.md
Tipo: Diretrizes e boas práticas do projeto
Objetivo: Centralizar padrões de desenvolvimento, versionamento, documentaç��o e rastreabilidade do Pequenos Passos.
Correlações: CHANGELOG.md, arquivos de implementação, scripts .bat, resumos de MVPs
Histórico de alterações:
- 2025-10-31 (user): Correção de Falha Crítica em Room/Migration
- 2025-10-27 (user): Reforço: Sempre mencionar mudanças e rastreabilidade nas primeiras 50 linhas dos arquivos afetados.
- 2025-10-24 (user): Atualização de práticas de commit e documentação.
Observação: Atualizar as primeiras 50 linhas dos arquivos críticos após cada alteração relevante.
-->

# GUIDELINES - Pequenos Passos

## Diretrizes de Desenvolvimento do Projeto

---

## 1. Filosofia do Projeto

### 1.1 Abordagem MVP (Minimum Viable Product)
- Desenvolvimento incremental e modular
- Cada MVP é completo e funcional
- Validação rigorosa antes de avançar
- Documentação sincronizada com código

### 1.2 Princípios Fundamentais
- **Clean Architecture**: Separação clara de responsabilidades
- **SOLID**: Código sustentável e extensível
- **DRY**: Evitar repetição desnecessária
- **KISS**: Manter simplicidade sempre que possível
- **TDD**: Testes guiam o desenvolvimento

---

## 2. Estrutura Arquitetural

### 2.1 Camadas da Aplicação

```
presentation/          # UI e ViewModels
├── screens/          # Telas Compose
├── components/       # Componentes reutilizáveis
├── navigation/       # Sistema de navegação
└── theme/           # Design System

domain/               # Regras de negócio
├── model/           # Entidades de domínio
├── repository/      # Interfaces de repositório
└── usecase/         # Casos de uso

data/                # Fontes de dados
├── database/        # Room Database
│   ├── dao/        # Data Access Objects
│   └── entities/   # Entidades Room (se diferentes do domain)
└── repository/     # Implementações de repositório
```

### 2.2 Fluxo de Dados
- **UI → ViewModel → UseCase → Repository → Database**
- Dados reativos usando Flow
- Estado gerenciado com StateFlow
- Injeção de dependências via Hilt

---

## 3. Padrões de Código

### 3.1 Nomenclatura
- **Classes**: PascalCase (Ex: `ChildProfile`, `TaskRepository`)
- **Funções**: camelCase (Ex: `saveTask`, `getChildProfile`)
- **Constantes**: UPPER_SNAKE_CASE (Ex: `MAX_STARS`, `DEFAULT_TIME`)
- **Arquivos**: Nome da classe principal (Ex: `HomeScreen.kt`)

### 3.2 Organização de Arquivos
- Um arquivo por classe principal
- Agrupar classes relacionadas em packages
- Testes no mesmo package, mas em src/test ou src/androidTest

### 3.3 Comentários e Documentação
``` 
/**
 * Descrição da classe/função.
 *
 * @param parametro Descrição do parâmetro
 * @return Descrição do retorno
 * @since MVP-XX
 */
```

---

## 4. Estratégia de Testes

### 4.1 Pirâmide de Testes
- **70% Testes Unitários**: UseCase, Repository, Models
- **20% Testes de Integração**: Database, API
- **10% Testes E2E**: UI completa

### 4.2 Cobertura Mínima
- Use Cases: 100%
- Repositories: 90%
- ViewModels: 80%
- UI: Testes críticos

### 4.3 Padrão de Testes
```
@Test
fun `should return success when valid data`() {
    // Given (Arrange)
    // val input = /* placeholder */
    
    // When (Act)
    // val result = /* placeholder */
    
    // Then (Assert)
    // assertEquals(expected, result)
}
```

---

## 5. Controle de Versão

### 5.1 Versionamento Semântico
- **MAJOR.MINOR.PATCH** (Ex: 1.7.0)
- **MAJOR**: Mudanças incompatíveis
- **MINOR**: Novas funcionalidades (MVPs)
- **PATCH**: Correções de bugs

### 5.2 Commits Semánticos
```
feat(mvp-XX): descrição curta
fix(component): descrição do bug corrigido
docs(file): atualização de documentação
test(usecase): adição de testes
refactor(repository): melhoria de código
```

### 5.3 Estratégia de Commits com PowerShell ⚠️ CRÍTICO

#### 5.3.1 Problemas Conhecidos do PowerShell

**ATENÇÃO:** O PowerShell do Windows possui limitações críticas que afetam a execução de comandos Git e Gradle.

##### ❌ PROBLEMA 1: Operador `&` (E comercial)

**Erro comum:**
```powershell
PS> git add . & git commit -m "mensagem"
# ERRO: O caráter de E comercial (&) não é permitido.
# O operador & está reservado para uso futuro
```

**Causa:**
- No PowerShell, `&` é usado para executar comandos em background/paralelo
- Não é equivalente ao `&` do Bash (que executa em sequência se o primeiro falhar)

##### ❌ PROBLEMA 2: Operador `&&` (Encadeamento condicional)

**Erro comum:**
```powershell
PS> git add . && git commit -m "mensagem" && git push
# ERRO: O token '&&' não é um operador de instrução válido
```

**Causa:**
- PowerShell **NÃO suporta** o operador `&&` do Bash/CMD
- `&&` só foi adicionado no PowerShell 7.0+, mas não funciona em versões anteriores
- Mesmo no PowerShell 7.0+, o comportamento é diferente do Bash

##### ❌ PROBLEMA 3: Executar arquivos `.bat` sem `.\`

**Erro comum:**
```powershell
PS> compilar_e_testar.bat
# ERRO: O termo 'compilar_e_testar.bat' não é reconhecido como nome de cmdlet
```

**Causa:**
- PowerShell **NÃO carrega comandos do diretório atual** por padrão (segurança)
- É necessário usar `.\` para indicar que o arquivo está no diretório atual

**✅ CORREÇÃO:**
```powershell
PS> .\compilar_e_testar.bat
```

##### ❌ PROBLEMA 4: Executar `gradlew` sem `.\`

**Erro comum:**
```powershell
PS> gradlew clean
# ERRO: O termo 'gradlew' não é reconhecido como nome de cmdlet
```

**✅ CORREÇÃO:**
```powershell
PS> .\gradlew clean
```

#### 5.3.2 Soluções Implementadas

##### ✅ SOLUÇÃO 1: Usar Arquivos `.bat` (Recomendado)

**Vantagens:**
- Funciona em qualquer shell (CMD, PowerShell, Git Bash)
- Comandos padronizados e reutilizáveis
- Menos chance de erro humano
- Processo visualmente acompanhável
- Facilita automação

**Scripts `.bat` disponíveis no projeto:**

```
📁 Raiz do Projeto
├── compilar_e_testar.bat      # Limpa, compila e testa
├── compilar_e_instalar.bat    # Compila e instala no dispositivo
├── executar_testes.bat        # Executa apenas testes
├── fazer_commit.bat           # Template genérico de commit
├── commit_docs.bat            # Commit específico de documentação
├── commit_v1.10.1.bat         # Commit da versão 1.10.1
└── commit_mvp06.bat           # Commit do MVP06
```

**Como usar:**
```powershell
# Sempre use .\ antes do nome do arquivo
PS> .\compilar_e_testar.bat
PS> .\commit_v1.10.1.bat
```

##### ✅ SOLUÇÃO 2: Estrutura de Arquivo `.bat` para Commits

**Template recomendado:**

```bat
@echo off
echo ============================================
echo  COMMIT LOCAL E REMOTO - v1.X.X
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
git commit -m "tipo(escopo): descrição curta

Descrição detalhada linha 1
Descrição detalhada linha 2

Arquivos modificados:
- arquivo1.kt
- arquivo2.kt

Status: MVP-XX - X%% completo"
echo Commit criado!
echo.

echo [4/6] Verificando commit criado...
git log -1 --oneline
echo.

echo [5/6] Criando tag vX.X.X (opcional)...
git tag -a vX.X.X -m "Versao X.X.X - Descrição"
echo Tag criada!
echo.

echo [6/6] Enviando para repositorio remoto...
git push origin main
echo.
echo Enviando tags (se criadas)...
git push origin vX.X.X
echo.

echo ============================================
echo  COMMIT CONCLUIDO COM SUCESSO!
echo ============================================
echo.
pause
```

**Observações importantes:**
1. **Cada comando Git em uma linha separada** (não use `&&` ou `&`)
2. **Use `echo.` para linhas em branco** (melhor visualização)
3. **Use `echo` entre comandos** para feedback visual
4. **Use `pause` no final** para ver resultado antes de fechar
5. **Escape `%` com `%%`** em mensagens de commit (ex: `100%%`)

##### ✅ SOLUÇÃO 3: Comandos PowerShell Alternativos

**Se não quiser usar `.bat`, use ponto-e-vírgula (`;`):**

```powershell
# Sequência de comandos Git
PS> git add .; git commit -m "mensagem"; git push

# Sequência de comandos Gradle
PS> .\gradlew clean; .\gradlew assembleDebug; .\gradlew installDebug
```

**Ou execute comandos separadamente:**
```powershell
PS> git add .
PS> git commit -m "mensagem"
PS> git push
```

#### 5.3.3 Estratégia de Commits por Versão

**Para cada versão estável, criar um arquivo `.bat` dedicado:**

**Exemplo: `commit_v1.10.1.bat`**
- Mensagem de commit pré-formatada
- Lista de arquivos modificados
- Tag automática da versão
- Push para repositório remoto
- Feedback visual em cada etapa

**Vantagens:**
- ✅ Histórico organizado (arquivo `.bat` fica no repositório)
- ✅ Fácil de replicar para próximas versões
- ✅ Mensagens de commit consistentes
- ✅ Reduz erros humanos
- ✅ Processo auditável

#### 5.3.4 Checklist de Commit

Antes de executar o script de commit:

- [ ] Código compilando sem erros (`.\gradlew build`)
- [ ] Testes passando (`.\gradlew test`)
- [ ] CHANGELOG.md atualizado
- [ ] Documentação atualizada (se aplicável)
- [ ] Versão incrementada em `build.gradle.kts` (se aplicável)
- [ ] Arquivo `.bat` de commit criado/atualizado
- [ ] Mensagem de commit descritiva e completa

#### 5.3.5 Boas Práticas de Commit

**✅ FAZER:**
- Criar arquivo `.bat` específico para versões importantes
- Usar mensagens descritivas e completas
- Incluir lista de arquivos modificados na mensagem
- Criar tags para versões estáveis
- Executar testes antes de commitar
- Atualizar CHANGELOG.md antes do commit
- Usar `.\` antes de executar `.bat` ou `gradlew`

**❌ EVITAR:**
- Usar `&&` ou `&` no PowerShell para encadear comandos
- Commits genéricos ("fix", "update", "changes")
- Commitar código que não compila
- Commitar sem atualizar documentação relevante
- Executar `.bat` sem `.\` no PowerShell

#### 5.3.6 ⚠️ Problemas Críticos do GitHub Copilot Agent com Terminal

**ATENÇÃO DESENVOLVEDORES E IA ASSISTANTS:**

O GitHub Copilot Agent (e outros AI Code Assistants) frequentemente **insiste em usar `&&` e `&`** ao gerar comandos para terminal no Windows PowerShell, mesmo após múltiplas correções.

**Comportamento problemático observado:**
1. **IA gera comando com `&&`** → Usuário recebe erro → IA gera novamente com `&&`
2. **Loop de erro:** IA não aprende com feedback imediato do terminal
3. **Ignora contexto:** Mesmo com guidelines claras, IA repete o erro
4. **Solução proposta pela IA:** "Use ponto-e-vírgula" → Na prática, gera `&&` novamente

**INSTRUÇÃO PARA AI ASSISTANTS:**

Ao trabalhar com **Windows PowerShell**:

```
NUNCA use: &&, &
SEMPRE use:
1. Arquivos .bat (PREFERENCIAL)
2. Ponto-e-vírgula (;) entre comandos
3. Comandos em linhas separadas
4. .\ antes de .bat e gradlew
```

**Regra de Ouro:**
```
SE (sistema = Windows E shell = PowerShell) ENTÃO
    USAR arquivos .bat OU ponto-e-vírgula (;)
    NUNCA usar && ou &
FIM SE
```

**Template de resposta correta para IA:**
```
Vou executar o script de commit:

.\commit_v1.10.1.bat

(Não usar: git add . && git commit && git push)
```

**Esta seção deve ser lida e seguida rigorosamente por qualquer AI Assistant trabalhando neste projeto.**

### 5.4 Estratégia de Branches ⭐ NOVO

#### 5.3.1 Estrutura de Branches

**Branch Principal:**
- **`main`**: Código estável, sempre funcional, MVPs validados
  - Protegida contra push direto
  - Apenas aceita merges de branches de feature após validação

**Branches de Feature/MVP:**
- **`feature/mvp-XX-nome-descritivo`**: Desenvolvimento de novo MVP completo
- **`feature/mvp-XX-faseY-nome`**: Desenvolvimento de fase específica de MVP complexo
- **`hotfix/descricao`**: Correções urgentes em produção
- **`bugfix/descricao`**: Correções de bugs não urgentes

#### 5.3.2 Quando Criar uma Nova Branch

**✅ CRIAR BRANCH quando:**
1. Iniciar um novo MVP
2. Implementar fase complexa que pode afetar múltiplos arquivos
3. Adicionar funcionalidade que requer mais de 1 dia de desenvolvimento
4. Fazer refatoração significativa
5. Implementar feature que pode introduzir breaking changes

**❌ NÃO criar branch para:**
- Correções de typos simples na documentação
- Ajustes de formatação
- Mudanças triviais em comentários

#### 5.3.3 Nomenclatura de Branches

**Padrão Geral:**
```
tipo/mvp-numero-fase-descricao-curta
```

**Exemplos:**
```
feature/mvp-07-implementacao-completa
feature/mvp-07-fase1-entidades-database
feature/mvp-07-fase2-componentes-ui
feature/mvp-08-estatisticas
hotfix/crash-ao-salvar-tarefa
bugfix/timer-nao-para-corretamente
```

**Regras:**
- Usar kebab-case (palavras separadas por hífen)
- Máximo 50 caracteres
- Descritivo mas conciso
- Incluir número do MVP quando aplicável

#### 5.3.4 Fluxo de Trabalho com Branches

**1. Criar Branch a partir da `main`:**
```bash
git checkout main
git pull origin main
git checkout -b feature/mvp-07-fase1-entidades-database
```

**2. Desenvolver na Branch:**
```bash
# Fazer modificações no código
git add .
git commit -m "feat(mvp-07): adicionar campos imageUrl e category em Task"
git push origin feature/mvp-07-fase1-entidades-database
```

**3. Manter Branch Atualizada:**
```bash
# Periodicamente, atualizar com mudanças da main
git checkout main
git pull origin main
git checkout feature/mvp-07-fase1-entidades-database
git merge main
# Resolver conflitos se houver
git push origin feature/mvp-07-fase1-entidades-database
```

**4. Finalizar e Integrar (Merge):**
```bash
# Após validação completa (testes passando, build OK)
git checkout main
git pull origin main
git merge feature/mvp-07-fase1-entidades-database
git push origin main

# Deletar branch local e remota (opcional, após merge)
git branch -d feature/mvp-07-fase1-entidades-database
git push origin --delete feature/mvp-07-fase1-entidades-database
```

#### 5.3.5 Estratégia para MVPs Complexos (Como MVP-07)

Para MVPs grandes (>10 dias de desenvolvimento), **dividir em fases com branches separadas**:

**Exemplo MVP-07:**
```
main (MVP-06 estável)
  │
  ├─→ feature/mvp-07-fase1-entidades-database (1-2 dias)
  │   ├─ Atualizar entidades (Step, Task)
  │   ├─ Criar migrations
  │   ├─ Atualizar DAOs
  │   ├─ Atualizar repositórios
  │   ├─ Testes unitários
  │   └─ MERGE → main (validado)
  │
  ├─→ feature/mvp-07-fase2-componentes (2-3 dias)
  │   ├─ CategoryPicker
  │   ├─ ImagePicker
  │   ├─ TimerInput
  │   ├─ CircularTimer
  │   ├─ Testes de componentes
  │   └─ MERGE → main (validado)
  │
  ├─→ feature/mvp-07-fase3-telas (3-4 dias)
  │   ├─ OnboardingScreen
  │   ├─ TaskFormScreen
  │   ├─ TaskListScreen
  │   ├─ TaskExecutionScreen
  │   ├─ ViewModels
  │   ├─ Navegação
  │   ├─ Testes de UI
  │   └─ MERGE → main (validado)
  │
  └─→ feature/mvp-07-fase4-integracao (2-3 dias)
      ├─ Integração completa
      ├─ Testes E2E
      ├─ Anti-regressão (MVPs 01-06)
      ├─ Polimento
      └─ MERGE → main (MVP-07 completo!)
```

**Vantagens desta Estratégia:**
- ✅ Cada fase é pequena e gerenciável
- ✅ Validação incremental (menos risco)
- ✅ Rollback fácil se fase falhar
- ✅ Histórico Git organizado
- ✅ Facilita code review
- ✅ Permite pausar entre fases
- ✅ Main sempre funcional

#### 5.3.6 Boas Práticas

**✅ FAZER:**
- Criar branch descritiva antes de começar trabalho significativo
- Fazer commits pequenos e frequentes na branch
- Atualizar branch com `main` antes de merge final
- Executar todos os testes antes de merge
- Validar build antes de merge
- Documentar mudanças no CHANGELOG antes de merge
- Deletar branch após merge bem-sucedido (manter histórico limpo)

**❌ EVITAR:**
- Trabalhar direto na `main` para features complexas
- Criar branches com nomes genéricos ("teste", "temp", "wip")
- Manter branches por muito tempo sem merge (>2 semanas)
- Fazer merge sem executar testes
- Deixar branches abandonadas no repositório

#### 5.3.7 Resolução de Conflitos

**Quando houver conflitos durante merge:**

1. **Identificar arquivos em conflito:**
```bash
git status
# Arquivos marcados com "both modified"
```

2. **Abrir arquivo e resolver manualmente:**
```
// Exemplo de conflito (resolva escolhendo ou combinando as versões):
//  - Versão da main: mantenha ou copie trechos necessários
//  - Versão da branch: mantenha ou copie trechos necessários
// Após combinar, remova os marcadores de conflito (<<<<<<<, =======, >>>>>>>) e deixe o código final desejado.
```

3. **Escolher qual versão manter ou combinar ambas**

4. **Marcar como resolvido:**
```bash
git add arquivo-resolvido.kt
git commit -m "merge: resolver conflitos entre main e feature/mvp-07-fase1"
```

#### 5.3.8 Comandos Úteis

**Ver branches:**
```bash
git branch          # Branches locais
git branch -a       # Todas as branches (local + remoto)
```

**Trocar de branch:**
```bash
git checkout nome-da-branch
git switch nome-da-branch  # Comando novo (Git 2.23+)
```

**Deletar branch:**
```bash
git branch -d nome-da-branch        # Local (seguro, só se merged)
git branch -D nome-da-branch        # Local (forçado)
git push origin --delete nome-da-branch  # Remoto
```

**Ver diferenças entre branches:**
```bash
git diff main..feature/mvp-07-fase1
```

#### 5.3.9 Checklist de Merge para Main

Antes de fazer merge de uma branch de feature para `main`:

- [ ] Todos os testes unitários passando
- [ ] Todos os testes de integração passando
- [ ] Build: SUCCESS (sem erros)
- [ ] Anti-regressão: MVPs anteriores funcionando
- [ ] Documentação atualizada (CHANGELOG, README, etc)
- [ ] Code review realizado (se trabalho em equipe)
- [ ] Branch atualizada com última versão da main
- [ ] Sem conflitos pendentes
- [ ] Commits organizados e com mensagens claras

---

## 6. Processo de Desenvolvimento MVP

### 6.1 Ciclo de Cada MVP

1. **Planejamento**
   - Definir escopo do MVP
   - Listar funcionalidades
   - Criar draft de arquitetura

2. **Implementação**
   - Desenvolver código seguindo Clean Architecture
   - Seguir padrões estabelecidos
   - Documentar conforme desenvolve

3. **Testes**
   - Escrever testes unitários
   - Executar testes de integração
   - Validar anti-regressão (MVPs anteriores)

4. **Documentação**
   - Atualizar CHANGELOG.md
   - Atualizar MVPXX_VALIDATION_SUMMARY.md
   - Atualizar documentação técnica

5. **Validação**
   - Build completo sem erros
   - Todos os testes passando
   - Revisão de código
   - Commit e push

6. **Avançar**
   - Incrementar versão
   - Planejar próximo MVP

---

## 7. Checklist de Validação MVP

Antes de considerar um MVP completo:

- [ ] Código implementado e funcionando
- [ ] Testes unitários escritos e passando (cobertura adequada)
- [ ] Testes de integração (se aplicável)
- [ ] Build gradle: SUCCESS
- [ ] Anti-regressão: MVPs anteriores funcionando
- [ ] CHANGELOG.md atualizado
- [ ] MVPXX_VALIDATION_SUMMARY.md criado
- [ ] Versão incrementada (build.gradle.kts)
- [ ] Commit com mensagem semântica
- [ ] Push para repositório remoto

---

## 8. Boas Práticas Específicas

### 8.1 Jetpack Compose
- Componentes pequenos e reutilizáveis
- State hoisting quando apropriado
- Preview para cada componente
- Acessibilidade (contentDescription, semantics)

### 8.2 Hilt/Dagger
- Um módulo por camada/feature
- Providers claros e específicos
- Evitar dependências circulares

### 8.3 Room Database
- Migrations versionadas
- Testes de integração para DAOs
- Índices em campos de busca frequente
- Converters para tipos complexos

#### 8.3.1 TypeConverters - Boas Práticas ⭐ CRÍTICO

**REGRA 1: Um Único Arquivo de Converters**
- ✅ Manter todos os TypeConverters em `Converters.kt`
- ❌ NÃO criar múltiplos arquivos de TypeConverters
- ❌ EVITAR duplicação de conversores (causa erro de compilação)

**Erro Comum:**
```kotlin
// ❌ ERRADO - Arquivo separado causa conflito
// DateTimeConverters.kt
@TypeConverter
fun toLocalDateTime(timestamp: Long?): LocalDateTime? { ... }

// Converters.kt (já existe!)
@TypeConverter
fun toLocalDateTime(timestamp: Long?): LocalDateTime? { ... }

// ERRO: Multiple functions define the same conversion
```

**REGRA 2: Verificar Converters Existentes ANTES de Adicionar Novos**
```kotlin
// ✅ CORRETO - Verificar Converters.kt primeiro
// Já existe: LocalDateTime ↔ Long
// Adicionar apenas: LocalDate ↔ Long (se não existir)

class Converters {
    // Existentes (MVP-08)
    @TypeConverter fun fromLocalDateTime(dateTime: LocalDateTime?): Long?
    @TypeConverter fun toLocalDateTime(timestamp: Long?): LocalDateTime?
    
    // Novos (MVP-09) - Apenas adicionar ao mesmo arquivo
    @TypeConverter fun fromLocalDate(date: LocalDate?): Long?
    @TypeConverter fun toLocalDate(epochDay: Long?): LocalDate?
}
```

**REGRA 3: Desugaring para java.time em API < 26**

Se usar `java.time.*` (LocalDate, LocalDateTime, etc) e `minSdk < 26`:

**build.gradle.kts (módulo app):**
```kotlin
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true  // ← OBRIGATÓRIO
    }
}

dependencies {
    // Obrigatório para java.time em API < 26
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}
```

**REGRA 4: Estratégias de Conversão por Tipo**

| Tipo Java | Tipo SQL | Estratégia | Exemplo |
|-----------|----------|------------|---------|
| `LocalDate` | `LONG` | `toEpochDay()` | Dias desde 1970-01-01 |
| `LocalDateTime` | `LONG` | `toEpochSecond()` ou `toEpochMilli()` | Segundos/millis desde epoch |
| `Enum` | `TEXT` | `name` / `valueOf()` | "ACTIVE" ↔ TaskStatus.ACTIVE |
| `List<String>` | `TEXT` | `joinToString()` / `split()` | "a,b,c" ↔ listOf("a","b","c") |
| `UUID` | `TEXT` | `toString()` / `fromString()` | String ↔ UUID |

**REGRA 5: Documentar Converters Claramente**

```kotlin
/**
 * Converte LocalDate para Long (epochDay) (armazenamento)
 * MVP09 - Sistema de Controle Diário
 *
 * Armazena número de dias desde 1970-01-01
 */
@TypeConverter
fun fromLocalDate(date: LocalDate?): Long? = date?.toEpochDay()
```

**Checklist TypeConverters:**
- [ ] Verificar se converter JÁ existe em `Converters.kt`
- [ ] Adicionar ao arquivo existente (não criar novo)
- [ ] Habilitar desugaring se usar java.time e minSdk < 26
- [ ] Registrar apenas uma vez em `@TypeConverters(Converters::class)`
- [ ] Documentar estratégia de conversão
- [ ] Testar compilação: `.\gradlew clean build`

**Referência:** `docs/MVP09_CORRECAO_TYPECONVERTERS.md`

### 8.4 Coroutines e Flow
- Usar Dispatchers.IO para operações de I/O
- Flow para streams de dados reativos
- StateFlow para estado de UI
- Tratamento adequado de exceções

---

## 9. Design System

### 9.1 Cores
- Seguir Material Design 3
- Paleta definida em Theme
- Cores semânticas (success, error, warning)

### 9.2 Tipografia
- Famílias de fontes consistentes
- Hierarquia clara (Display, Headline, Title, Body, Label)
- Tamanhos e pesos definidos

### 9.3 Espaçamentos
- Sistema padronizado (4dp, 8dp, 16dp, 24dp, 32dp)
- Margens e paddings consistentes

---

## 10. Ferramentas e Ambiente

### 10.1 Requisitos
- Android Studio (última versão estável)
- JDK 11+
- Gradle 8.0+
- Git

### 10.2 Dependências Principais
- Kotlin
- Jetpack Compose
- Hilt/Dagger
- Room
- Coroutines/Flow
- JUnit, Mockk, Truth (testes)

### 10.3 Comandos Gradle no PowerShell ⚠️ IMPORTANTE

**O PowerShell NÃO suporta o operador `&&` para encadear comandos.**

#### ❌ ERRADO (NÃO FUNCIONA NO POWERSHELL):
```bash
# Isto NÃO funciona no PowerShell:
.\gradlew clean && .\gradlew assembleDebug && .\gradlew installDebug
git add . && git commit -m "mensagem" && git push
```

#### ✅ CORRETO (Use espaços simples ou ponto-e-vírgula):

**Opção 1: Comandos em sequência na mesma linha (recomendado):**
```powershell
.\gradlew clean assembleDebug installDebug
```

**Opção 2: Usar ponto-e-vírgula (`;`) para separar comandos:**
```powershell
.\gradlew clean; .\gradlew assembleDebug; .\gradlew installDebug
```

**Opção 3: Usar scripts `.bat` prontos (mais prático):**
```powershell
# Use os scripts batch disponíveis na raiz do projeto:
.\compilar_e_testar.bat
.\compilar_e_instalar.bat
.\executar_testes.bat
```

#### Comandos Gradle Comuns:

**Limpar e compilar:**
```powershell
.\gradlew clean assembleDebug
```

**Compilar e instalar no dispositivo:**
```powershell
.\gradlew assembleDebug installDebug
```

**Executar testes unitários:**
```powershell
.\gradlew test
```

**Executar testes instrumentados:**
```powershell
.\gradlew connectedAndroidTest
```

**Compilar, testar e instalar (sequência completa):**
```powershell
.\gradlew clean test assembleDebug installDebug
```

#### Comandos Git:

**Adicionar, commitar e fazer push:**
```powershell
# ERRADO (não funciona):
git add . && git commit -m "mensagem" && git push

# CORRETO:
git add .
git commit -m "mensagem"
git push
```
Ou usando ponto-e-vírgula:
```powershell
git add .; git commit -m "mensagem"; git push
```

#### Scripts Batch Disponíveis:

O projeto possui scripts `.bat` prontos para facilitar operações comuns:

| Script | Descrição | Comando |
|--------|-----------|---------|
| `compilar_e_testar.bat` | Limpa, compila e executa testes | `.\compilar_e_testar.bat` |
| `compilar_e_instalar.bat` | Compila e instala no dispositivo | `.\compilar_e_instalar.bat` |
| `executar_testes.bat` | Executa apenas os testes | `.\executar_testes.bat` |

**Vantagens de usar scripts `.bat`:**
- ✅ Funciona em qualquer shell (CMD, PowerShell)
- ✅ Comandos padronizados
- ✅ Menos chance de erro de digitação
- ✅ Inclui tratamento de erros

---

## 11. Documentação Obrigatória

Para cada MVP, criar/atualizar:

1. **CHANGELOG.md**: Histórico de mudanças
2. **MVPXX_VALIDATION_SUMMARY.md**: Resumo de validação
3. **PATHS.md**: Fluxo de navegação (se aplicável)
4. **Comentários no código**: Documentação inline

---

## 12. Anti-Regressão

Sempre que um novo MVP é desenvolvido:

1. Executar todos os testes existentes
2. Verificar se MVPs anteriores continuam funcionando
3. Validar navegação completa
4. Testar build em dispositivo real (quando possível)

---

## 13. Estratégia de Navegação

### 13.1 Estrutura Atual
```
SplashScreen (3s)
    ↓
HomeScreen
    ├── Botão "Cadastro" → [Em desenvolvimento]
    ├── Botão "Teste Rápido" → [Em desenvolvimento]
    ├── Botão "Atividades" → [Em desenvolvimento]
    └── Botão "Debug" → DebugScreen
                          ├── TTS Test → TtsTestScreen
                          └── ASR Test → AsrTestScreen
```

### 13.2 Expansão Planejada (MVP-07)
- TaskFormScreen (cadastro de tarefas)
- OnboardingScreen (cadastro de usuário)
- TaskExecutionScreen (execução de tarefas)
- TaskListScreen (lista de atividades)

---

## 14. Roadmap de MVPs

- **MVP-01**: Estrutura Base ✅
- **MVP-02**: Entidades ✅
- **MVP-03**: Database ✅
- **MVP-04**: Repositórios ✅
- **MVP-05**: Use Cases ✅
- **MVP-06**: Theme e Design System ✅
- **MVP-07**: Telas de Interface 📋 (PRÓXIMO)
- **MVP-08**: ViewModels e Integração 📋
- **MVP-09**: Testes E2E 📋
- **MVP-10**: Polimento e Release 📋

---

## Histórico de Erros e Soluções Recentes (v1.11.7)

### Erros Apresentados
- Falhas de compilação em testes unitários devido a mudanças em assinaturas de construtores e parâmetros obrigatórios não fornecidos.
- Erros de tipo e ordem de argumentos em SaveTaskUseCaseTest.kt.
- Parâmetros obrigatórios ausentes em TaskExecutionViewModelTest.kt.

### Soluções Aplicadas
- Atualização de todos os testes para refletir as novas assinaturas dos construtores e métodos.
- Inclusão de todos os mocks obrigatórios nos testes de ViewModel.
- Ajuste da ordem e tipos dos argumentos em todos os testes afetados.
- Execução e validação de build e testes unitários após as correções.

### Últimas Implementações
- Incremento da versão para 1.11.7 (SplashScreen e build.gradle.kts).
- Atualização de documentação para rastreabilidade e registro de status de build/testes.
- Prática obrigatória: registrar sempre nas primeiras 50 linhas dos arquivos principais as mudanças, status de build e testes.

# Atualização 2025-10-31

## Controle de Exibição do Pop-up de Tempo Extra
- O pop-up de tempo extra ao final do step deve respeitar a configuração `askExtraTimeAtStep`.
- Se desativada, o fluxo avança automaticamente para o próximo passo.
- Correção aplicada no ViewModel de execução: consulta reativa ao valor de configuração.
- Testes práticos validados.

## Histórico de Falhas e Soluções
- [2.1.2] Corrigido bug de exibição indevida do pop-up de tempo extra.
- [2.1.1] Correção de persistência da configuração no banco de dados.

## Checklist de Validação
- [x] Checkbox alterna corretamente e persiste valor
- [x] Pop-up só aparece se opção estiver ativada
- [x] Fluxo avança automaticamente se desativada
- [x] Documentação e changelog atualizados

## Correção de Falha Crítica em Room/Migration
- Sempre que adicionar um novo campo via migration, adicione o mesmo campo na data class da entidade correspondente.
- Exemplo: Campo `askExtraTimeAtStep` foi adicionado via migration e DAO, mas estava ausente em `AppSettings.kt`, causando erro de compilação e falha de Room/SQLite.
- Solução: Adicionar o campo na entidade com valor padrão compatível com a migration.
- Após correção, build e testes passaram normalmente.

## Checklist para futuras alterações em banco de dados
- [ ] Migration criada e registrada em ordem em `AppDatabase`
- [ ] Campo novo adicionado na entidade correspondente
- [ ] Valor padrão compatível com a migration
- [ ] Testes de build e execução realizados
- [ ] Documentação atualizada (CHANGELOG, GUIDELINES)

## Observação
- Falha de instalação por ausência de dispositivo não é erro de código.

---

## Versão 1.11.7 (27/10/2025)
- Compilação e testes unitários obrigatórios corrigidos.
- Build validado sem erros.
- Sempre registrar no CHANGELOG e nas primeiras 50 linhas dos arquivos principais o status de compilação e testes da versão.

**Última atualização**: 2025-10-15  
**Versão do documento**: 1.0  
**Status**: Ativo
