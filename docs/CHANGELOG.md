# CHANGELOG - Pequenos Passos

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

---

## [1.8.0] - 2025-10-16 - MVP-07 Planejamento e Documentação

### Planejado
- **4 Telas principais de interface com funcionalidades avançadas**:
  - OnboardingScreen (Hub de Cadastros com 3 opções)
  - TaskFormScreen (com suporte a imagens e timer)
  - TaskListScreen (com expansão inline)
  - TaskExecutionScreen (com timer circular visual)

### Adicionado à Documentação
- **PATHS.md v1.3**: Fluxo de navegação completo do MVP-07
  - OnboardingScreen como Hub de Cadastros (3 opções sempre visíveis)
  - Separação clara: Área de Configuração (Hub) vs Área de Execução (Atividades)
  - Proteção contra edições acidentais (edição movida para Hub)
- **MVP07_PANORAMA_GERAL.md**: Documento completo do MVP-07
  - Checklist de implementação (5 fases)
  - Componentes reutilizáveis detalhados
  - Estimativa de tempo: 9-13 dias (2-3 semanas)
  - Impacto nos MVPs anteriores documentado
- **MVP07_WIREFRAMES.md**: Wireframes detalhados das 4 telas
  - Wireframes com imagens e timer visual
  - Componentes reutilizáveis especificados

### Funcionalidades Planejadas

#### Suporte a Imagens nos Steps ⭐ NOVO
- Seleção via câmera ou galeria
- Preview de imagem
- Redimensionamento automático (max 1024px)
- Armazenamento local
- Validações: JPG/PNG, tamanho máximo

#### Timer Configurável por Step ⭐ NOVO
- Range: 5-600 segundos (5s a 10min)
- Slider visual
- Dropdown com valores predefinidos
- Padrão: 60 segundos

#### Timer Circular Visual Animado ⭐ NOVO
- Tamanho: 200dp × 200dp
- Cores dinâmicas (Verde > Amarelo > Vermelho)
- Animação suave (1s updates)
- Controles de pausar/retomar
- Alarme ao fim (som + vibração)
- Dialog com opções (+30s ou concluir)

#### Hub de Cadastros (OnboardingScreen) ⭐ NOVO
- **3 opções sempre disponíveis**:
  1. Cadastro Criança (editar perfil)
  2. Cadastro de Tarefa (criar nova)
  3. Editar Tarefa (acessar lista e editar)
- Acessível a qualquer momento
- Área de configuração para adultos

#### Navegação com Segurança 🛡️ ⭐ NOVO
- **Opção "Editar" REMOVIDA da TaskListScreen**
- Edição protegida no Hub de Cadastros
- Previne alterações acidentais por crianças com TEA
- TaskListScreen: apenas Executar e Cancelar
- Melhora significativa na acessibilidade

#### TaskListScreen com Expansão Inline ⭐ NOVO
- Cards de tarefas expandem ao tocar
- Opções inline (sem navegar)
- Indicadores de imagens (🖼️ × quantidade)
- Tempo total do timer exibido

### Alterações nas Entidades (Planejadas)

#### Step Entity - Novos Campos
```kotlin
- imageUrl: String? (path local da imagem)
- durationSeconds: Int (timer 15-600s, padrão 60)
```

### Alterações no Database (Planejadas)

#### Tabela steps - Novas Colunas
```sql
- image_url TEXT (nullable)
- duration_seconds INTEGER DEFAULT 60
```

#### Migration Necessária
- Criar migration do Room para adicionar 2 colunas
- Manter compatibilidade com dados existentes

### Impacto nos MVPs Anteriores

#### MVP-02 (Entidades) - ⚠️ IMPACTADO
- Atualizar classe Step com novos campos

#### MVP-03 (Database) - ⚠️ IMPACTADO
- Criar migration para tabela steps
- Adicionar colunas image_url e duration_seconds

#### MVP-04 (Repositórios) - ⚠️ IMPACTADO
- Atualizar StepRepository para novos campos

#### MVP-05 (Use Cases) - ⚠️ IMPACTADO
- Adicionar validações em SaveTaskUseCase:
  - Timer entre 15-600s
  - Path de imagem válido (se presente)

#### MVP-06 (Theme) - ✅ SEM IMPACTO
- Componentes novos usarão tema já existente

### Componentes Reutilizáveis a Criar

1. **ImagePicker**: Seleção câmera/galeria com preview
2. **TimerInput**: Slider + dropdown para timer
3. **CircularTimer**: Timer visual animado
4. **StepItem**: Card completo de step

### Bibliotecas a Adicionar
```kotlin
- androidx.activity:activity-compose:1.8.0 (câmera/galeria)
- io.coil-kt:coil-compose:2.5.0 (carregamento imagens)
```

### Checklist de Implementação (5 Fases)
- [ ] Fase 1: Preparação (1-2 dias) - Entidades, database, migrations
- [ ] Fase 2: Componentes (2-3 dias) - ImagePicker, TimerInput, CircularTimer, StepItem
- [ ] Fase 3: Telas (3-4 dias) - 4 telas principais
- [ ] Fase 4: Testes (2-3 dias) - Unitários, integração, UI, anti-regressão
- [ ] Fase 5: Documentação (1 dia) - Atualizar docs, screenshots

### Estimativa
- **Tempo total**: 9-13 dias (2-3 semanas)
- **Complexidade**: Média-Alta
- **Valor entregue**: Alto (funcionalidades essenciais para público-alvo)

### Referências
- PATHS.md v1.3
- MVP07_PANORAMA_GERAL.md
- MVP07_WIREFRAMES.md
- SPECIFICATION_FOR_APP.md

---

## [1.7.0] - 2025-10-15 - MVP-06 Completo

### Adicionado
- Sistema completo de Theme e Design System
- PequenosPassosTheme com suporte a modo claro e escuro
- Paleta de cores personalizada (Primary, Secondary, Tertiary)
- Tipografia customizada com famílias de fontes
- Sistema de espaçamentos padronizado
- Testes de tema completos

### Validação
- ✅ Todos os testes passando (142 testes)
- ✅ Build: SUCCESS
- ✅ Anti-regressão: MVPs 01-05 funcionando

---

## [1.6.0] - 2025-10-14 - MVP-05 Completo

### Adicionado
- 10 Use Cases implementados:
  - CheckFirstRunUseCase
  - CompleteOnboardingUseCase
  - GetChildProfileUseCase
  - SaveChildProfileUseCase
  - GetTaskByIdUseCase
  - GetTasksOrderedByTimeUseCase
  - SaveTaskUseCase
  - UpdateTaskStatusUseCase
  - GetStepsByTaskUseCase
  - UpdateStepCompletionUseCase

### Testes
- 44 testes unitários para Use Cases (100% passando)
- Cobertura completa de cenários de sucesso e erro

### Validações
- Nome obrigatório (mínimo 2 caracteres)
- Horário formato HH:mm válido
- Estrelas entre 1 e 5
- AppResult para tratamento de erros
- Flow para dados reativos

---

## [1.5.0] - 2025-10-13 - MVP-04 Completo

### Adicionado
- Camada de Repositórios completa
- 4 interfaces de repositório no domain/repository
- 4 implementações em data/repository com Hilt
- RepositoryModule para injeção de dependências
- Testes unitários para repositórios (37 testes)

### Repositórios Implementados
- AppSettingsRepository
- ChildProfileRepository
- TaskRepository
- StepRepository

---

## [1.4.0] - 2025-10-12 - MVP-03 Completo

### Adicionado
- Banco de dados Room completo
- AppDatabase com 4 tabelas
- 4 DAOs implementados
- Converters para tipos complexos
- DatabaseModule para Hilt
- Testes de integração do banco (32 testes)

### Tabelas
- app_settings
- child_profiles
- tasks
- steps

---

## [1.3.0] - 2025-10-11 - MVP-02 Completo

### Adicionado
- 5 entidades de domínio
- Modelos de dados completos
- Enums TaskStatus e TaskType
- Classe AppResult para tratamento de erros
- Testes unitários para entidades (29 testes)

### Entidades
- AppSettings
- ChildProfile
- Task
- Step
- AppResult

---

## [1.2.0] - 2025-10-10 - MVP-01 Completo

### Adicionado
- Estrutura base do projeto Android
- Configuração Hilt/Dagger
- Clean Architecture (data, domain, presentation)
- 5 telas iniciais (Splash, Home, Debug, TtsTest, AsrTest)
- Sistema de navegação básico
- Tema temporário

### Infraestrutura
- Gradle configurado
- Dependências principais
- Estrutura de pastas Clean Architecture

---

## Legenda de Status

- ✅ Implementado e validado
- ⏳ Em desenvolvimento
- 📋 Planejado
- ❌ Bloqueado

---

## Próximos Passos

### MVP-07 - Telas de Interface (Planejado)
- Implementar telas de cadastro
- Implementar telas de execução de tarefas
- Refinar navegação entre telas
- Adicionar componentes de UI reutilizáveis

### MVP-08 - Integração Completa (Futuro)
- Conectar todas as camadas
- Implementar ViewModels
- Fluxo end-to-end funcionando

### MVP-09 - Testes E2E (Futuro)
- Testes de interface
- Testes de integração completos
- Validação em dispositivos reais

### MVP-10 - Polimento e Release (Futuro)
- Ajustes finais de UI/UX
- Otimizações de performance
- Preparação para produção
