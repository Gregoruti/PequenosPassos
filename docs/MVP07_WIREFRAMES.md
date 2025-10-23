# WIREFRAMES - Pequenos Passos

**Versão do Documento:** 2.0  
**Data:** 23/10/2025  
**Versão do App:** 1.9.6+

---

## 1. HOME SCREEN (TELA INICIAL) - v2.0

### 1.1. Layout Geral

```
┌─────────────────────────────────────────────┐
│  ┌─────────────────────────────────────┐   │
│  │    STATUS BAR (Sistema Android)     │   │
│  └─────────────────────────────────────┘   │
│                                             │
│  ╔═════════════════════════════════════╗   │
│  ║   HEADER - SAUDAÇÃO E INFORMAÇÕES   ║   │
│  ╚═════════════════════════════════════╝   │
│                                             │
│  ╔═════════════════════════════════════╗   │
│  ║     GAMIFICAÇÃO (ESTRELAS)          ║   │
│  ╚═════════════════════════════════════╝   │
│                                             │
│  ╔═════════════════════════════════════╗   │
│  ║     LOGO PEQUENOS PASSOS            ║   │
│  ╚═════════════════════════════════════╝   │
│                                             │
│  ╔═════════════════════════════════════╗   │
│  ║                                     ║   │
│  ║        BOTÕES DE NAVEGAÇÃO          ║   │
│  ║                                     ║   │
│  ║  [  ATIVIDADES  ]                   ║   │
│  ║                                     ║   │
│  ║  [  EDIÇÃO DE ATIVIDADES  ]         ║   │
│  ║                                     ║   │
│  ║  [  CADASTRO  ]                     ║   │
│  ║                                     ║   │
│  ║  [  DEBUG  ]                        ║   │
│  ║                                     ║   │
│  ╚═════════════════════════════════════╝   │
│                                             │
└─────────────────────────────────────────────┘
```

---

## 2. HEADER - SAUDAÇÃO E INFORMAÇÕES

### 2.1. Wireframe Detalhado

```
╔═══════════════════════════════════════════════╗
║                                               ║
║    👋 Olá, [NOME DA CRIANÇA]!                 ║
║        (ou apenas "Olá!" se não cadastrado)   ║
║                                               ║
║    📅 Hoje é [DIA DA SEMANA]                  ║
║    🕐 Agora são [HH:MM]                       ║
║                                               ║
╚═══════════════════════════════════════════════╝
```

### 2.2. Especificações do Header

**Saudação:**
- **Texto:** "Olá, [Nome]!" ou "Olá!"
- **Fonte:** Headline Medium (Material 3)
- **Cor:** Primary color
- **Ícone:** 👋 (emoji ou drawable)
- **Alinhamento:** Centro

**Data e Hora:**
- **Formato Data:** "Hoje é Segunda-feira" (dia da semana por extenso)
- **Formato Hora:** "Agora são 14:30" (formato 24h)
- **Fonte:** Body Large
- **Cor:** OnSurface (87% opacity)
- **Ícones:** 📅 e 🕐 (ou Material Icons equivalentes)

**Comportamento:**
- Hora atualiza automaticamente a cada minuto
- Nome carregado do ChildProfile (se existir)
- Dia da semana em português (Segunda, Terça, etc.)

### 2.3. Exemplo de Código Compose

```kotlin
@Composable
fun HeaderSection(
    childName: String?,
    currentTime: LocalTime,
    currentDayOfWeek: DayOfWeek
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Saudação
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "👋",
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (childName != null) "Olá, $childName!" else "Olá!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Data
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hoje é ${getDayOfWeekName(currentDayOfWeek)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Hora
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Agora são ${currentTime.format(timeFormatter)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

fun getDayOfWeekName(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "Segunda-feira"
        DayOfWeek.TUESDAY -> "Terça-feira"
        DayOfWeek.WEDNESDAY -> "Quarta-feira"
        DayOfWeek.THURSDAY -> "Quinta-feira"
        DayOfWeek.FRIDAY -> "Sexta-feira"
        DayOfWeek.SATURDAY -> "Sábado"
        DayOfWeek.SUNDAY -> "Domingo"
    }
}
```

---

## 3. SEÇÃO DE GAMIFICAÇÃO

### 3.1. Wireframe Detalhado

```
╔═══════════════════════════════════════════════╗
║                                               ║
║    ⭐ Você já tem [X] Estrelas! ⭐           ║
║                                               ║
║    [⭐] [⭐] [⭐] [⭐] [⭐]                     ║
║        (ícones de estrelas visuais)           ║
║                                               ║
╚═══════════════════════════════════════════════╝
```

### 3.2. Especificações da Gamificação

**Texto Principal:**
- **Formato:** "Você já tem [número] Estrelas!"
- **Fonte:** Title Large
- **Cor:** Amarelo dourado (para estrelas) / Primary
- **Alinhamento:** Centro

**Ícones de Estrelas:**
- **Tipo:** Ícone Material Star (filled)
- **Cor:** Amarelo (#FFD700 - Gold)
- **Tamanho:** 32dp
- **Quantidade exibida:** Até 5 estrelas visíveis
- **Se > 5:** Mostrar "⭐ x [número]"

**Comportamento:**
- Carregar total de estrelas do banco de dados (quando MVP08 implementado)
- Animação sutil ao entrar na tela (opcional)
- Placeholder: "0 Estrelas" (enquanto gamificação não estiver completa)

### 3.3. Exemplo de Código Compose

```kotlin
@Composable
fun GamificationSection(totalStars: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9E6) // Amarelo claro
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "⭐ Você já tem $totalStars Estrelas! ⭐",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFB300), // Amarelo escuro
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Exibir até 5 estrelas visualmente
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val starsToShow = minOf(totalStars, 5)
                repeat(starsToShow) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrela",
                        tint = Color(0xFFFFD700), // Dourado
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                if (totalStars > 5) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "x $totalStars",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFFFB300)
                    )
                }
            }
        }
    }
}
```

---

## 4. LOGO PEQUENOS PASSOS

### 4.1. Wireframe Detalhado

```
╔═══════════════════════════════════════════════╗
║                                               ║
║         ┌───────────────────────┐             ║
║         │                       │             ║
║         │   [LOGO/IMAGEM]       │             ║
║         │   Pequenos Passos     │             ║
║         │                       │             ║
║         └───────────────────────┘             ║
║                                               ║
╚═══════════════════════════════════════════════╝
```

### 4.2. Especificações do Logo

**Imagem:**
- **Localização:** `/images/` (conforme mencionado)
- **Formato:** PNG ou SVG
- **Tamanho:** 120dp x 120dp (ou proporcional)
- **Alinhamento:** Centro

**Texto (se aplicável):**
- **Fonte:** Display Small ou Logo Font
- **Cor:** Primary color
- **Posição:** Abaixo ou integrado à imagem

**Comportamento:**
- Apenas decorativo (não clicável)
- Possível animação sutil na entrada (opcional)

### 4.3. Exemplo de Código Compose

```kotlin
@Composable
fun LogoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_pequenos_passos),
            contentDescription = "Logo Pequenos Passos",
            modifier = Modifier.size(120.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Pequenos Passos",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}
```

---

## 5. BOTÕES DE NAVEGAÇÃO

### 5.1. Wireframe Detalhado

```
╔═══════════════════════════════════════════════╗
║                                               ║
║    ┌───────────────────────────────────┐     ║
║    │                                   │     ║
║    │      📋 ATIVIDADES                │     ║
║    │                                   │     ║
║    └───────────────────────────────────┘     ║
║                                               ║
║    ┌───────────────────────────────────┐     ║
║    │                                   │     ║
║    │      ✏️ EDIÇÃO DE ATIVIDADES      │     ║
║    │                                   │     ║
║    └───────────────────────────────────┘     ║
║                                               ║
║    ┌───────────────────────────────────┐     ║
║    │                                   │     ║
║    │      👤 CADASTRO                  │     ║
║    │                                   │     ║
║    └───────────────────────────────────┘     ║
║                                               ║
║    ┌───────────────────────────────────┐     ║
║    │                                   │     ║
║    │      🐛 DEBUG                     │     ║
║    │                                   │     ║
║    └───────────────────────────────────┘     ║
║                                               ║
╚═══════════════════════════════════════════════╝
```

### 5.2. Especificações dos Botões

**Botão 1: ATIVIDADES**
- **Texto:** "Atividades"
- **Ícone:** 📋 ou `Icons.Default.Assignment`
- **Ação:** Navegar para TaskListScreen (lista de atividades)
- **Cor:** Primary (botão preenchido - Filled)
- **Descrição:** Visualizar e executar atividades cadastradas

**Botão 2: EDIÇÃO DE ATIVIDADES**
- **Texto:** "Edição de Atividades"
- **Ícone:** ✏️ ou `Icons.Default.Edit`
- **Ação:** Navegar para TaskManagementScreen (edição)
- **Cor:** Secondary (botão preenchido - Filled)
- **Descrição:** Gerenciar, editar e excluir atividades

**Botão 3: CADASTRO**
- **Texto:** "Cadastro"
- **Ícone:** 👤 ou `Icons.Default.PersonAdd`
- **Ação:** Navegar para TaskFormScreen (criar nova atividade)
- **Cor:** Tertiary (botão preenchido - Filled)
- **Descrição:** Cadastrar nova atividade

**Botão 4: DEBUG**
- **Texto:** "Debug"
- **Ícone:** 🐛 ou `Icons.Default.BugReport`
- **Ação:** Navegar para DebugScreen
- **Cor:** Outline (botão com borda - Outlined)
- **Descrição:** Testes e debug (apenas desenvolvimento)

**Características Gerais:**
- **Altura:** 56dp (mínimo recomendado para acessibilidade)
- **Largura:** `fillMaxWidth` com padding de 16dp
- **Espaçamento:** 12dp entre botões
- **Border Radius:** 12dp (rounded corners)
- **Elevation:** 2dp (sombra sutil)

### 5.3. Exemplo de Código Compose

```kotlin
@Composable
fun NavigationButtons(
    onAtividadesClick: () -> Unit,
    onEdicaoClick: () -> Unit,
    onCadastroClick: () -> Unit,
    onDebugClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Botão Atividades
        Button(
            onClick = onAtividadesClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Assignment,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Atividades",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        // Botão Edição de Atividades
        Button(
            onClick = onEdicaoClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Edição de Atividades",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        // Botão Cadastro
        Button(
            onClick = onCadastroClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cadastro",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        // Botão Debug
        OutlinedButton(
            onClick = onDebugClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.BugReport,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Debug",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
```

---

## 6. MOCKUP VISUAL COMPLETO

### 6.1. Versão com Nome Cadastrado

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                             ┃
┃  ╔═══════════════════════════════════════╗ ┃
┃  ║                                       ║ ┃
┃  ║    👋 Olá, João!                      ║ ┃
┃  ║                                       ║ ┃
┃  ║    📅 Hoje é Terça-feira              ║ ┃
┃  ║    🕐 Agora são 09:15                 ║ ┃
┃  ║                                       ║ ┃
┃  ╚═══════════════════════════════════════╝ ┃
┃                                             ┃
┃  ╔═══════════════════════════════════════╗ ┃
┃  ║                                       ║ ┃
┃  ║  ⭐ Você já tem 23 Estrelas! ⭐       ║ ┃
┃  ║                                       ║ ┃
┃  ║    ⭐ ⭐ ⭐ ⭐ ⭐ x 23                  ║ ┃
┃  ║                                       ║ ┃
┃  ╚═══════════════════════════════════════╝ ┃
┃                                             ┃
┃         ┌─────────────────┐                 ┃
┃         │                 │                 ┃
┃         │   [  LOGO  ]    │                 ┃
┃         │ Pequenos Passos │                 ┃
┃         │                 │                 ┃
┃         └─────────────────┘                 ┃
┃                                             ┃
┃  ┌───────────────────────────────────────┐ ┃
┃  │         📋 ATIVIDADES                 │ ┃
┃  └───────────────────────────────────────┘ ┃
┃                                             ┃
┃  ┌───────────────────────────────────────┐ ┃
┃  │    ✏️ EDIÇÃO DE ATIVIDADES            │ ┃
┃  └───────────────────────────────────────┘ ┃
┃                                             ┃
┃  ┌───────────────────────────────────────┐ ┃
┃  │         👤 CADASTRO                   │ ┃
┃  └───────────────────────────────────────┘ ┃
┃                                             ┃
┃  ┌───────────────────────────────────────┐ ┃
┃  │         🐛 DEBUG                      │ ┃
┃  └───────────────────────────────────────┘ ┃
┃                                             ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

---

## 7. MUDANÇAS EM RELAÇÃO À VERSÃO ANTERIOR

### 7.1. Removido

- ❌ Botão "Teste Rápido"
- ❌ Nomenclatura "Tarefas"

### 7.2. Adicionado

- ✅ Header com saudação personalizada (nome da criança)
- ✅ Informações de data (dia da semana por extenso)
- ✅ Informações de hora (atualização automática)
- ✅ Seção de gamificação com contagem de estrelas
- ✅ Ícones visuais de estrelas
- ✅ Logo Pequenos Passos centralizado

### 7.3. Renomeado

- 🔄 "Tarefas" → "Atividades"
- 🔄 "Gerenciar Tarefas" → "Edição de Atividades"

---

## 8. CHECKLIST DE IMPLEMENTAÇÃO

### Fase 1: Header e Informações (Prioridade ALTA)
- [ ] Implementar HeaderSection composable
- [ ] Carregar nome do ChildProfile do banco
- [ ] Implementar atualização automática de hora (cada minuto)
- [ ] Formatar dia da semana em português
- [ ] Adicionar ícones Material (CalendarToday, AccessTime)
- [ ] Testes unitários

### Fase 2: Gamificação (Prioridade MÉDIA)
- [ ] Implementar GamificationSection composable
- [ ] Criar placeholder para estrelas (0 por padrão)
- [ ] Adicionar ícones Star (Material Icons)
- [ ] Preparar para integração futura com MVP08
- [ ] Testes unitários

### Fase 3: Logo (Prioridade BAIXA)
- [ ] Adicionar imagem do logo em drawable/images
- [ ] Implementar LogoSection composable
- [ ] Ajustar tamanho (120dp x 120dp)
- [ ] Centralizar e aplicar padding

### Fase 4: Botões (Prioridade ALTA)
- [ ] Atualizar NavigationButtons
- [ ] Renomear "Tarefas" para "Atividades"
- [ ] Renomear para "Edição de Atividades"
- [ ] Remover botão "Teste Rápido"
- [ ] Ajustar cores (primary, secondary, tertiary, outline)
- [ ] Adicionar ícones corretos
- [ ] Testes de navegação

### Fase 5: ViewModel (Prioridade ALTA)
- [ ] Criar/Atualizar HomeViewModel
- [ ] Implementar StateFlow para childProfile
- [ ] Implementar StateFlow para totalStars
- [ ] Implementar StateFlow para currentTime
- [ ] Implementar StateFlow para currentDayOfWeek
- [ ] Timer para atualizar hora a cada minuto
- [ ] Testes unitários

### Fase 6: Integração Final
- [ ] Integrar todas as seções na HomeScreen
- [ ] Usar LazyColumn para scroll
- [ ] Testar navegação para todas as telas
- [ ] Validar responsividade
- [ ] Validar acessibilidade (contentDescription)
- [ ] Testes E2E

---

## 9. RECURSOS NECESSÁRIOS

### 9.1. Strings (res/values/strings.xml)

```xml
<!-- Saudações -->
<string name="home_greeting_default">Olá!</string>
<string name="home_greeting_with_name">Olá, %s!</string>

<!-- Data e Hora -->
<string name="home_today_is">Hoje é %s</string>
<string name="home_current_time">Agora são %s</string>

<!-- Dias da Semana -->
<string name="day_monday">Segunda-feira</string>
<string name="day_tuesday">Terça-feira</string>
<string name="day_wednesday">Quarta-feira</string>
<string name="day_thursday">Quinta-feira</string>
<string name="day_friday">Sexta-feira</string>
<string name="day_saturday">Sábado</string>
<string name="day_sunday">Domingo</string>

<!-- Gamificação -->
<string name="home_stars_count">Você já tem %d Estrelas!</string>
<string name="home_stars_count_single">Você já tem %d Estrela!</string>

<!-- Botões -->
<string name="button_atividades">Atividades</string>
<string name="button_edicao_atividades">Edição de Atividades</string>
<string name="button_cadastro">Cadastro</string>
<string name="button_debug">Debug</string>

<!-- Content Descriptions -->
<string name="cd_star_icon">Ícone de estrela</string>
<string name="cd_calendar_icon">Ícone de calendário</string>
<string name="cd_clock_icon">Ícone de relógio</string>
<string name="cd_logo">Logo Pequenos Passos</string>
```

### 9.2. Drawable/Images

```
res/
└── drawable/
    └── logo_pequenos_passos.png  (ou .xml para vector)
```

ou

```
images/
└── logo_pequenos_passos.png
```

---

## 10. NOTAS TÉCNICAS

### 10.1. Atualização de Hora

```kotlin
private fun startTimeUpdater() {
    viewModelScope.launch {
        while (true) {
            _currentTime.value = LocalTime.now()
            _currentDayOfWeek.value = LocalDate.now().dayOfWeek
            delay(60000L) // Atualizar a cada 1 minuto
        }
    }
}
```

### 10.2. Formatação de Hora

```kotlin
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

// Uso:
currentTime.format(timeFormatter) // "14:30"
```

### 10.3. Integração com ChildProfile

```kotlin
private fun loadChildProfile() {
    viewModelScope.launch {
        getChildProfileUseCase().collect { profile ->
            _childProfile.value = profile
        }
    }
}
```

---

## 11. ESTADOS DA TELA

### 11.1. Estado Inicial (Sem Cadastro)

- Saudação: "Olá!"
- Estrelas: "0 Estrelas"
- Todos os botões habilitados

### 11.2. Estado com Perfil Cadastrado

- Saudação: "Olá, [Nome]!"
- Estrelas: Número real carregado do banco
- Todos os botões habilitados

### 11.3. Estado de Loading

```kotlin
if (isLoading) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
```

---

**Documento criado em:** 23/10/2025  
**Versão:** 2.0  
**Status:** ✅ Pronto para implementação  
**Próxima ação:** Implementar HomeScreen conforme especificações

