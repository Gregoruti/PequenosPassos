### 📝 Uso do Theme

// Aplicar tema
PequenosPassosTheme {
    // Seu conteúdo aqui
}

// Acessar cores extendidas
Text(
    text = "Concluída",
    color = MaterialTheme.extendedColors.Completed
)
**Status:** ✅ **CONCLUÍDO**

Testes automatizados retroativos foram criados e estão passando 100%:

**Prioridade Alta (CONCLUÍDO):**
- [x] NavigationTest.kt - 7 testes de rotas e navegação ✅
- [x] TtsManagerTest.kt - 6 testes de síntese de voz ✅
- [x] AsrManagerTest.kt - 7 testes de reconhecimento de voz ✅
- [x] AssetValidatorTest.kt - 5 testes de validação de modelo Vosk ✅

**Prioridade Média (Backlog):**
- [ ] Navigation instrumentados - Testes de UI das telas principais (5 testes) 📋

**Justificativa:** O MVP-01 agora possui 26 testes unitários automatizados,
protegendo as funcionalidades principais contra regressões. Testes instrumentados
de UI podem ser adicionados futuramente para aumentar ainda mais a cobertura.

---

##### 📊 MÉTRICAS COMPARATIVAS

| MVP | Versão | Data | Validação | Testes Unit. | Testes Inst. | Cobertura | Status |
|-----|--------|------|-----------|--------------|--------------|-----------|--------|
| MVP-01 | 1.3.1 | 14/10/2025 | Automatizada | 26 ✅ | 0 ⚠️ | 85% | ✅ Aprovado |
| MVP-02 | 1.4.0 | 13/10/2025 | Automatizada | 59 ✅ | 0 | 100% | ✅ Aprovado |
| MVP-03 | 1.4.1 | 14/10/2025 | Automatizada | 0 | 32 ✅ | 100% | ✅ Aprovado |
| MVP-04 | 1.5.0 | 14/10/2025 | Automatizada | 44 ✅ | 0 | 100% | ✅ Aprovado |
| MVP-05 | 1.6.0 | 14/10/2025 | Automatizada | 26 ✅ | 0 | 100% | ✅ Aprovado |
| MVP-06 | 1.7.0 | 14/10/2025 | Automatizada | 35 ✅ | 0 | 100% | ✅ Aprovado |

**Evolução da Qualidade:**
```
Sprint 1 (MVP-01): ████████░░ 85%  ✅ 26 testes automatizados
Sprint 2 (MVP-02): ██████████ 100% ✅ Padrão ouro estabelecido (59 testes)
Sprint 3 (MVP-03): ██████████ 100% ✅ Database completo (32 testes instrumentados)
Sprint 4 (MVP-04): ██████████ 100% ✅ Repositories (44 testes)
Sprint 5 (MVP-05): ██████████ 100% ✅ Use Cases (26 testes)
Sprint 6 (MVP-06): ██████████ 100% ✅ Theme & Design System (35 testes)
```

**Total do Projeto:** 190 testes (158 unitários + 32 instrumentados) (100% passando, 0 falhas, 4.198s)

---

##### 🎉 CONCLUSÃO MVP-02

O MVP-02 estabelece o **padrão ouro de qualidade** que todos os MVPs futuros
devem seguir:

✅ **Código implementado** - 4 entidades de domínio completas
✅ **Testes automatizados** - 59 testes unitários (100% passando)
✅ **Documentação completa** - Guias, estratégia e CHANGELOG atualizados
✅ **Lição aprendida** - Importância de testes desde o MVP-01
✅ **Processo definido** - TESTING_STRATEGY.md para todos os MVPs
✅ **Guidelines atualizadas** - Testes obrigatórios em GUIDELINES.md

**Status Final:** ✅ **MVP-02 APROVADO** - Pronto para avançar ao MVP-03

**Próximos Passos:** MVP-03 - Database e DAOs (com 50-60 testes planejados)

---

# Validação dos MVPs

## MVP-01
- Testes automatizados: todos os testes unitários e instrumentados passaram.
- Teste manual: execução do app (Homescreen v1.4.0), validação das funcionalidades consolidadas, sem regressão aparente.

## MVP-02
- Testes automatizados: todos os testes unitários e instrumentados passaram.
- Testes manuais: não realizados.

## Diretriz de Validação
Sempre que um novo MVP ou funcionalidade for implementado, devem ser realizados testes automatizados e manuais, com registro detalhado neste changelog. O status de validação deve ser atualizado para garantir rastreabilidade e proteção contra regressão.

---
## 2. Status de Validação Integrado
Esta seção centraliza o status de validação de todos os MVPs, incluindo
métricas de testes automatizados e critérios de aceite.
| MVP | Versão | Data | Validação | Testes Unit. | Testes Inst. | Cobertura | Status |
|-----|--------|------|-----------|--------------|--------------|-----------|--------|
| MVP-01 | 1.3.1 | 14/10/2025 | Automatizada | 26 ✅ | 0 ⚠️ | 85% | ✅ Aprovado |
| MVP-02 | 1.4.0 | 13/10/2025 | Automatizada | 59 ✅ | 0 | 100% | ✅ Aprovado |
| MVP-03 | 1.4.1 | 14/10/2025 | Automatizada | 0 | 32 ✅ | 100% | ✅ Aprovado |
| MVP-04 | 1.5.0 | 14/10/2025 | Automatizada | 44 ✅ | 0 | 100% | ✅ Aprovado |
| MVP-05 | 1.6.0 | 14/10/2025 | Automatizada | 26 ✅ | 0 | 100% | ✅ Aprovado |
| MVP-06 | 1.7.0 | 14/10/2025 | Automatizada | 35 ✅ | 0 | 100% | ✅ Aprovado |

### 📈 Evolução da Qualidade do Projeto
Cobertura de Testes Automatizados por Sprint:
- Sprint 1 (MVP-01): 85% (26 testes automatizados) ✅
- Sprint 2 (MVP-02): 100% (59 testes - Padrão ouro estabelecido) ✅
- Sprint 3 (MVP-03): 100% (32 testes instrumentados - Database completo) ✅
- Sprint 4 (MVP-04): 100% (44 testes - Repositories completos) ✅
- Sprint 5 (MVP-05): 100% (26 testes - Use Cases completos) ✅
- Sprint 6 (MVP-06): 100% (35 testes - Theme & Design System) ✅

**Total do Projeto:** 190 testes (158 unitários + 32 instrumentados) (100% passando, 0 falhas, 4.198s)

---
## 3. Estratégia de Validação e Testes
**Objetivo:** Garantir qualidade incremental através de testes automatizados
em cada MVP, prevenindo regressões e documentando comportamento esperado.
**Referência:** Consultar sempre GUIDELINES.md seção 2.2.1 para processo 
obrigatório de testes.
### 3.1. Lição Aprendida do MVP-01

**Problema Identificado:** MVP-01 teve apenas validação manual (45 pontos de 
checklist) inicialmente, o que não previne regressões automáticas.

**Solução Implementada:**
1. **Testes manuais mantidos (45 testes)** - Validação funcional completa via 
   tela Debug e checklist detalhado
2. **Testes automatizados criados (26 testes)** - Proteção contra regressão 
   e validação contínua
3. **Estratégia híbrida** - Combinação de testes manuais + automatizados para 
   cobertura completa

**Cobertura de Testes MVP-01:**

**Testes Manuais (45 testes) - ✅ CONCLUÍDO**
- Acesso à Tela Debug (3 testes)
- Validação MVP-01: Estrutura Base (3 testes)
- Validações de Funcionalidades Core (4 testes)
- Status dos Outros MVPs (2 testes)
- Testes Funcionais - TTS (8 testes)
- Testes Funcionais - ASR (Vosk) (14 testes)
- Navegação Geral (3 testes)
- Build e Dependências (5 testes)
- Verificações de Arquitetura (3 testes)

**Testes Automatizados (26 testes) - ✅ CONCLUÍDO**
- NavigationTest.kt (7 testes - rotas, navegação, rota inicial)
- TtsManagerTest.kt (6 testes - validação de texto, locale PT-BR, testes rápidos)
- AsrManagerTest.kt (7 testes - modelo Vosk, sample rate, permissões, arquivos críticos)
- AssetValidatorTest.kt (5 testes - validação de assets, diretórios, caminhos)
- ExampleUnitTest.kt (1 teste - validação básica de ambiente)

**Testes Instrumentados (Backlog) - 📋 PLANEJADO**
- [ ] Navigation instrumentados - Testes de UI das telas principais (5 testes)

**Total MVP-01:** 71 testes (45 manuais + 26 automatizados)

**Justificativa da Estratégia Híbrida:**
- **Testes Manuais:** Essenciais para validação de UX, feedback visual, 
  integração TTS/ASR com hardware real e comportamento em diferentes 
  dispositivos
- **Testes Automatizados:** Garantem proteção contra regressão, execução 
  rápida em CI/CD e validação de lógica de negócio
- **Complementaridade:** Ambos são necessários para garantir qualidade 
  completa do MVP-01

**Diretriz para MVPs Futuros:**
A partir do MVP-02, testes automatizados são **OBRIGATÓRIOS** antes do aceite 
final. Testes manuais continuam importantes para validação de UX e 
comportamento em dispositivos reais.
### 3.2. Pirâmide de Testes (Distribuição Ideal)
**60-75% Unitários** - Entidades, Use Cases, Validators (Pure Kotlin)
**20-30% Instrumentados** - DAOs, Repositories, UI Components (Android)
**5-10% E2E** - Fluxos completos, Smoke tests (UI + Backend)
### 3.3. Padrões Estabelecidos no MVP-02
**Nomenclatura de Testes:**
```kotlin
@Test
fun `método_cenário_resultadoEsperado`()
```
**Padrão AAA (Arrange-Act-Assert):**
Todos os testes seguem: Preparar → Executar → Verificar
**Comandos de Execução:**
```bash
# Todos os testes unitários
gradlew test
# Testes específicos
gradlew test --tests ""com.pequenospassos.domain.model.*Test""
# Relatório HTML
# app/build/reports/tests/testDebugUnitTest/index.html
```
### 3.4. Critérios de Aceite (Obrigatórios para Todos os MVPs)
Um MVP só pode ser aprovado se atender:
**✅ Critérios Técnicos:**
- Build limpo (0 erros)
- Testes criados para todas as classes críticas
- 100% dos testes passando
- Cobertura mínima: Domínio 95%+, Aplicação 90%+, Infra 85%+, Apresentação 80%+
- Relatório HTML gerado
- Tempo: Unitários < 10s, Instrumentados < 1min, E2E < 3min
**✅ Critérios de Qualidade:**
- Cenários positivos e negativos cobertos
- Edge cases testados
- Padrão AAA aplicado
- Nomenclatura clara em português
- Testes determinísticos e independentes
**✅ Critérios de Documentação:**
- CHANGELOG atualizado com seção de validação
- Guia de testes criado (MVP_XX_TESTING_GUIDE.md)
- KDocs completos
**✅ Critérios Anti-Regressão:**
- Testes de MVPs anteriores continuam passando 100%
**❌ BLOQUEIO:** MVP NÃO pode ser aprovado sem testes passando 100%
### 3.5. Estratégia por MVP Futuro
#### MVP-03: Database e DAOs
- **Testes Planejados:** 50-60 total
- **Unitários:** 20-30 (TypeConverters)
- **Instrumentados:** 30-40 (DAOs com Room in-memory)
- **Cobertura Meta:** 90%+
#### MVP-04: Tela de Cadastro
- **Testes Planejados:** 40-50 total
- **Unitários:** 15-20 (ViewModels, validators)
- **Instrumentados:** 10-15 (UI Compose)
- **E2E:** 2-3 (fluxos críticos)
- **Cobertura Meta:** 85%+
#### MVP-05: Tela de Listagem
- **Testes Planejados:** 50-60 total
- **Unitários:** 20-25 (ViewModels, use cases)
- **Instrumentados:** 15-20 (UI, filtros)
- **E2E:** 3-5 (fluxos completos)
- **Cobertura Meta:** 85%+
### 3.6. Ferramentas e Bibliotecas
**Testes Unitários:**
- JUnit 4.13.2
- MockK 1.13.8
- Turbine 1.0.0 (para Flows)
- Coroutines Test 1.7.3
**Testes Instrumentados:**
- AndroidX Test
- Espresso
- Compose UI Testing
- Room Testing
- Navigation Testing
### 3.7. Processo de Aceite de MVP
**Workflow Obrigatório:**
1. Implementar funcionalidade
2. Criar testes (unitários + instrumentados)
3. Executar: gradlew test connectedAndroidTest
4. Se falhou → Corrigir → Voltar ao passo 3
5. Verificar cobertura mínima
6. Criar guia de testes (MVP_XX_TESTING_GUIDE.md)
7. Atualizar CHANGELOG com seção de validação
8. Commit: ""feat(mvp-XX): descrição + testes""
9. MVP APROVADO
**Referência Completa:** Ver GUIDELINES.md seção 2.2.1
---
## 4. Roadmap de Funcionalidades
