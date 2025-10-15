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
```kotlin
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
```kotlin
@Test
fun `should return success when valid data`() {
    // Given (Arrange)
    val input = ...
    
    // When (Act)
    val result = ...
    
    // Then (Assert)
    assertEquals(expected, result)
}
```

---

## 5. Controle de Versão

### 5.1 Versionamento Semântico
- **MAJOR.MINOR.PATCH** (Ex: 1.7.0)
- **MAJOR**: Mudanças incompatíveis
- **MINOR**: Novas funcionalidades (MVPs)
- **PATCH**: Correções de bugs

### 5.2 Commits Semânticos
```
feat(mvp-XX): descrição curta
fix(component): descrição do bug corrigido
docs(file): atualização de documentação
test(usecase): adição de testes
refactor(repository): melhoria de código
```

### 5.3 Branches
- **main**: Código estável e validado
- **develop**: Desenvolvimento ativo (se necessário)
- **feature/mvp-XX**: Desenvolvimento de MVP específico

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

**Última atualização**: 2025-10-15  
**Versão do documento**: 1.0  
**Status**: Ativo

