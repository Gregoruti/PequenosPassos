# CHANGELOG - MVP-07

**Versão:** 1.7.2  
**Data de Release:** 18/10/2025  
**Status:** 🟢 90% Completo - Em finalização

---

## 🎯 Visão Geral do MVP-07

O MVP-07 adiciona funcionalidades avançadas de personalização e visualização para o aplicativo PequenosPassos, focando em:
- **Categorização** de tarefas com 27 categorias
- **Suporte a imagens** em tarefas e steps
- **Timer configurável** por step (5-600 segundos)
- **Timer visual circular** animado durante execução
- **Interface aprimorada** com indicadores visuais

---

## ✅ IMPLEMENTADO E VALIDADO

### 🗄️ Database e Models (Fase 1)

#### Task Model - Novos Campos
```kotlin
// ADICIONADO
val category: String            // Categoria obrigatória (enum TaskCategory)
val imageUrl: String? = null    // Imagem principal da tarefa (opcional)
```

#### Step Model - Novos Campos
```kotlin
// ADICIONADO
val imageUrl: String? = null           // Imagem do step (opcional)
val durationSeconds: Int = 60          // Duração configurável (5-600s)
```

#### Enums Criados
- ✅ **TaskCategory**: 27 categorias com emoji e nome em português
- ✅ **CategoryGroup**: 6 grupos organizadores

#### Database Migration
- ✅ **Migration 3→4** aplicada
- ✅ Novos campos adicionados com valores padrão
- ✅ Índices e foreign keys preservados

**Arquivos:**
- `Task.kt`
- `Step.kt`
- `TaskCategory.kt`
- `CategoryGroup.kt`
- `AppDatabase.kt`
- `TaskDao.kt`
- `StepDao.kt`

---

### 🎨 Componentes UI (Fase 2)

#### CategoryPicker
✅ **VALIDADO**

**Features:**
- Dropdown Material 3 com 27 categorias
- Organizadas em 6 grupos com cabeçalhos
- Emoji + nome em português
- Suporte a erro e dark mode
- Variante simplificada disponível

**Testes:** 22 testes unitários (100% passando)

**Arquivo:** `presentation/components/CategoryPicker.kt`

---

#### TimerInput
✅ **VALIDADO**

**Features:**
- Slider horizontal (5-600 segundos)
- 8 valores rápidos pré-definidos
- Display formatado ("1 min 30 seg")
- Validações de range
- Cores Material 3

**Testes:** 15+ testes unitários (100% passando)

**Arquivo:** `presentation/components/TimerInput.kt`

---

#### ImagePicker
✅ **VALIDADO (COM BUGFIX)**

**Features:**
- Captura via câmera
- Seleção via galeria (**CORRIGIDO 18/10**)
- Preview com botão remover
- Redimensionamento automático (max 1024px)
- Correção de orientação EXIF
- FileProvider configurado

**Bugfix Aplicado (18/10/2025):**
- Problema: Galeria não retornava imagem selecionada
- Solução: Adicionado `takePersistableUriPermission()`
- Status: ✅ Corrigido e validado

**Variantes:**
- `ImagePicker`: Versão completa com preview
- `CompactImagePicker`: Versão compacta

**Arquivo:** `presentation/components/ImagePicker.kt`

**Documentação:** `MVP07_BUGFIX_GALERIA_IMAGENS.md`

---

### 📱 Telas e Integração (Fase 3)

#### TaskFormScreen
✅ **VALIDADO**

**Features Implementadas:**
- Formulário completo de criação/edição
- CategoryPicker integrado (obrigatório)
- ImagePicker para imagem principal (opcional)
- Campos: título, descrição, horário, estrelas
- Gerenciamento completo de steps
- StepDialog com ImagePicker e TimerInput
- StepCard com indicadores visuais
- Validações completas
- Loading e error handling

**Componentes Criados:**
- `TaskFormScreen.kt`
- `TaskFormViewModel.kt`
- `TaskFormState.kt`
- `StepDialog` (interno)
- `StepCard` (interno)

**Documentação:** `MVP07_TASKFORM_IMPLEMENTATION.md`

---

#### TaskListScreen
✅ **VALIDADO (ATUALIZADO 18/10)**

**Melhorias Implementadas:**
- ✅ Emoji da categoria visível no card
- ✅ Nome da categoria exibido
- ✅ Contador de steps com plural inteligente
- ✅ Contador de imagens nos steps (🖼️ × N)
- ✅ Duração total formatada (⏱️)
- ✅ Layout otimizado e responsivo

**Mudança de Segurança:**
- ❌ Botão "Editar" REMOVIDO da lista
- ✅ Edição agora só via Hub de Cadastros
- 🛡️ Protege crianças de edições acidentais

**Componentes:**
- `TaskListScreen.kt` (atualizado)
- `TaskListViewModel.kt` (criado)
- `TaskWithMetadata` (data class)

**Documentação:** `MVP07_TASKLISTSCREEN_IMPLEMENTATION.md`

---

#### TaskExecutionScreen
✅ **VALIDADO**

**Features Implementadas:**
- CircularTimer com cores dinâmicas:
  - 🟢 Verde: > 60% tempo restante
  - 🟡 Amarelo: 30-60% tempo
  - 🔴 Vermelho: < 30% tempo
- Exibição de imagem do step (70% largura)
- Countdown timer baseado em `durationSeconds`
- Indicador de progresso (Passo X de Y)
- Botões: Pausar/Retomar, Próximo/Concluir
- Dialog de tempo esgotado (+30s extra)
- Navegação automática ao concluir

**Componentes Criados:**
- `TaskExecutionScreen.kt`
- `TaskExecutionViewModel.kt`
- `CircularTimer.kt`

---

#### HomeScreen
✅ **ATUALIZADO**

**Mudanças:**
- Botões de navegação atualizados
- Acesso a todas as novas telas

---

## 🚧 EM DESENVOLVIMENTO

### OnboardingScreen → Hub de Cadastros
**Status:** 🟡 PLANEJADO

**Objetivo:** Transformar tela de onboarding em hub centralizado

**Três Opções:**
1. 👶 Cadastro Criança - Editar perfil
2. 📝 Cadastro de Tarefa - Criar nova
3. ✏️ Editar Tarefa - Lista para edição

**Justificativa:**
- Área de configuração separada
- Proteção contra edições acidentais
- Melhor UX para cuidadores

**Estimativa:** 3-4 horas

---

## 🐛 Bugs Corrigidos

### #1 - Seleção de Imagens da Galeria
**Data:** 18/10/2025  
**Severidade:** 🔴 Alta  
**Status:** ✅ Corrigido

**Problema:**
- Usuário selecionava imagem da galeria
- Voltava para tela sem a imagem
- Afetava imagem principal e steps

**Causa:**
- Android revogava permissão temporária da URI
- `resizeAndSaveImage()` não conseguia ler

**Solução:**
```kotlin
// Adicionado no galleryLauncher
context.contentResolver.takePersistableUriPermission(
    uri, 
    Intent.FLAG_GRANT_READ_URI_PERMISSION
)
```

**Arquivos Modificados:**
- `ImagePicker.kt`

**Documentação:** `MVP07_BUGFIX_GALERIA_IMAGENS.md`

---

## 📊 Estatísticas

### Testes
- **Total:** 164 testes
- **Passando:** 164 (100%)
- **Falhando:** 0

### Cobertura por Categoria
| Categoria | Testes | Status |
|-----------|--------|--------|
| Models | 37 | ✅ 100% |
| Repositories | 37 | ✅ 100% |
| Use Cases | 29 | ✅ 100% |
| Components | 37 | ✅ 100% |
| Theme | 24 | ✅ 100% |

### Arquivos Criados/Modificados
- **Fase 1:** 7 arquivos
- **Fase 2:** 3 arquivos
- **Fase 3:** 11 arquivos
- **Total:** 21 arquivos

---

## 🔄 Breaking Changes

### ⚠️ IMPORTANTE: Migration Obrigatória

**Database Migration 3→4:**
```sql
ALTER TABLE tasks ADD COLUMN category TEXT NOT NULL DEFAULT 'OUTROS';
ALTER TABLE tasks ADD COLUMN imageUrl TEXT;
ALTER TABLE steps ADD COLUMN imageUrl TEXT;
ALTER TABLE steps ADD COLUMN durationSeconds INTEGER NOT NULL DEFAULT 60;
```

**Ação Necessária:**
- Migration é aplicada automaticamente no primeiro launch
- Tarefas antigas recebem categoria "OUTROS"
- Steps antigos recebem duração padrão de 60s

**Sem Impacto:** Dados existentes são preservados

---

## 📱 Compatibilidade

### Android Version
- **Mínimo:** API 24 (Android 7.0)
- **Target:** API 34 (Android 14)
- **Testado:** API 29-34

### Permissões Adicionadas
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" android:required="false" />
```

### Storage
- FileProvider configurado
- Imagens salvas em: `external-files-path/Pictures`
- Redimensionamento automático (max 1024px)

---

## 📚 Documentação Criada

1. ✅ `MVP07_TASKFORM_IMPLEMENTATION.md` - TaskFormScreen completo
2. ✅ `MVP07_TASKLISTSCREEN_IMPLEMENTATION.md` - TaskListScreen atualizado
3. ✅ `MVP07_BUGFIX_GALERIA_IMAGENS.md` - Correção de galeria
4. ✅ `MVP07_STATUS_ATUAL.md` - Status consolidado
5. ✅ `MVP07_CHANGELOG.md` - Este arquivo

---

## 🎯 Checklist de Release

### Desenvolvimento
- [x] Todas as features implementadas (exceto Hub)
- [x] Testes unitários passando (100%)
- [x] Sem erros de compilação
- [x] Bugs críticos corrigidos

### QA
- [x] CategoryPicker testado
- [x] TimerInput testado
- [x] ImagePicker testado (câmera + galeria)
- [x] TaskFormScreen testado
- [x] TaskListScreen testado
- [x] TaskExecutionScreen testado
- [x] Fluxo completo: criar → listar → executar

### Documentação
- [x] README atualizado
- [x] Changelog criado
- [x] Guias de implementação
- [x] Documentação de bugs

### Pendente
- [ ] OnboardingScreen → Hub de Cadastros
- [ ] Testes de integração E2E
- [ ] Teste em múltiplos dispositivos
- [ ] Coleta de feedback de usuários

---

## 🚀 Próximos Passos (MVP-08)

### Planejado
1. Hub de Cadastros completo
2. Filtros e busca na TaskListScreen
3. Relatórios de progresso da criança
4. Notificações de lembretes
5. Backup/Restore de dados
6. Modo offline completo

### Melhorias Futuras
- Sistema de recompensas expandido
- Gráficos de evolução
- Compartilhamento de rotinas
- Suporte multi-criança

---

## 👥 Contribuidores

**PequenosPassos Development Team**

---

## 📄 Licença

Copyright © 2025 PequenosPassos. Todos os direitos reservados.

---

**Data desta versão:** 18/10/2025  
**Versão:** 1.7.2  
**Status:** 🟢 90% Completo - Pronto para testes finais

