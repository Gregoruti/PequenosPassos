# ESPECIFICATION_FOR_APP.MD

**Propósito**: Especificação completa das funcionalidades, requisitos e contexto do
projeto PequenosPassos, servindo como referência técnica para desenvolvimento,
validação e evolução do app.

**Escopo**: Detalhamento de requisitos, funcionalidades, arquitetura, fluxos e
critérios de validação do aplicativo PequenosPassos para crianças com TEA.

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md                            │
│              (Framework Universal de Desenvolvimento)       │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Orienta Especificação)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md (ESTE)                │
│                 Detalhes do Projeto                         │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Define Funcionalidades)
                   ▼
┌──────────────────┬──────────────────┬──────────────────────┐
│    PATHS.md      │   CHANGELOG.md   │      README.md       │
│  (Estruturas)    │  (Histórico)     │   (Documentação)     │
└──────────────────┴──────────────────┴──────────────────────┘
```

**Versão**: 1.3.0 | **Data**: 12/10/2025 | **Status**: MVP definido com sprint de 3
dias e prompts estruturais

# 🎯 MVP - PRODUTO MÍNIMO VIÁVEL (PRIORIDADE MÁXIMA)

**Objetivo**: Validar conceitos centrais do PequenosPassos em 3 dias de
desenvolvimento com foco nas funcionalidades essenciais de cadastro da criança e
execução de atividades.

**Filosofia MVP**: "Fazer menos, mas fazer bem" - concentrar nos fluxos críticos
que validam a proposta de valor para crianças com TEA.

## 🚀 MODIFICAÇÕES IMPLEMENTADAS NO MVP

### ✅ CADASTRO DA CRIANÇA EXPANDIDO
- **Nome da criança**: Campo obrigatório
- **Sexo**: Seletor visual (Menino/Menina) com ícones
- **Foto**: Captura da câmera ou galeria (opcional)
- Integrado no OnboardingScreen na primeira execução

### ✅ ORDENAÇÃO AUTOMÁTICA POR HORÁRIO
- Tarefas ordenadas automaticamente pelo campo `time` (HH:mm)
- Sequenciamento inteligente das atividades do dia
- Propriedade `order` calculada baseada no horário

### ✅ STATUS COMPLETO DE TAREFAS
- ⏳ **Pendente**: Tarefa não iniciada
- ✅ **Concluída**: Tarefa finalizada com sucesso
- ❌ **Cancelada**: Tarefa não realizada
- Botões correspondentes: "FAZER" | "✓ FEITO" | "✗ CANCELAR"

### ✅ SPRINT OTIMIZADO PARA 3 DIAS
- **Dia 1**: Fundação (configuração + dados + lógica) - 8h
- **Dia 2**: Interface (telas + cadastro de tarefas) - 8h
- **Dia 3**: Execução (funcionalidades + testes) - 8h

---

# 🏗️ PROMPTS ESTRUTURAIS MVP - METODOLOGIA PROMPT

**Baseado em**: GUIDELINES.md - Seções 3 (Modularidade), 4 (Arquitetura), 5
(Metodologia PROMPT)
**Ordem de Implementação**: Core → Aplicação → Infraestrutura → Apresentação
**Filosofia**: "Entregar valor incrementalmente através de módulos pequenos, isolados
e testáveis"

## 📋 ÍNDICE DE PROMPTS MVP

### DIA 1 - FUNDAÇÃO (Core + Infraestrutura)
- [MVP-01](#mvp-01) - Estrutura Base do Projeto
- [MVP-02](#mvp-02) - Entidades de Domínio MVP
- [MVP-03](#mvp-03) - Database e DAOs MVP
- [MVP-04](#mvp-04) - Repositórios MVP
- [MVP-05](#mvp-05) - Use Cases MVP

### DIA 2 - INTERFACE (Apresentação)
- [MVP-06](#mvp-06) - Theme e Design System MVP
- [MVP-07](#mvp-07) - Componentes Reutilizáveis MVP
- [MVP-08](#mvp-08) - SplashScreen MVP
- [MVP-09](#mvp-09) - OnboardingScreen MVP
- [MVP-10](#mvp-10) - TaskFormScreen MVP

### DIA 3 - EXECUÇÃO (Funcionalidades + Testes)
- [MVP-11](#mvp-11) - HomeScreen MVP
- [MVP-12](#mvp-12) - TaskExecutionScreen MVP
- [MVP-13](#mvp-13) - Navegação MVP
- [MVP-14](#mvp-14) - Testes Unitários MVP
- [MVP-15](#mvp-15) - Validação Final MVP

---

## DIA 1 - FUNDAÇÃO (8h)

### MVP-01: Estrutura Base do Projeto

**Persona**: Desenvolvedor sênior Android especialista em Clean Architecture e Kotlin
**Role/Rules**: Seguindo Clean Architecture, MVVM, Jetpack Compose e padrões SOLID
**Objective**: Configurar estrutura inicial do projeto MVP PequenosPassos
**Message**: Criar projeto Android com arquitetura base para desenvolvimento MVP de 3 dias
**Parameters**: 
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34
- Jetpack Compose + Material 3
- Hilt para DI
- Room Database
- Navigation Compose
**Task**: Projeto compilando com estrutura de pacotes Clean Architecture e dependências configuradas

```kotlin
// Estrutura de Pacotes MVP
com.pequenospassos/
├── data/
│   ├── database/
│   │   ├── entities/     // ChildProfile, Task, Step, AppSettings
│   │   ├── dao/          // DAOs MVP
│   │   └── AppDatabase.kt
│   └── repository/       // Implementações
├── domain/
│   ├── model/           // Modelos de domínio
│   ├── repository/      // Interfaces
│   └── usecase/         // Use cases MVP
├── presentation/
│   ├── components/      // Componentes reutilizáveis
│   ├── screens/        // 5 telas MVP
│   ├── navigation/     // NavGraph MVP
│   └── theme/          // Design System MVP
├── di/                 // Módulos Hilt
└── utils/              // Helpers
```

**Dependências Essenciais MVP**:
```kotlin
// Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
implementation("androidx.activity:activity-compose:1.8.2")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.02.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.6")

// Room
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// Hilt
implementation("com.google.dagger:hilt-android:2.48")
kapt("com.google.dagger:hilt-compiler:2.48")

// Image Loading
implementation("io.coil-kt:coil-compose:2.5.0")
```

**Critérios de Validação**:
- [ ] Projeto compila sem erros
- [ ] Estrutura de pacotes Clean Architecture criada
- [ ] Dependências MVP configuradas
- [ ] MainActivity com Compose configurada
- [ ] Hilt Application criada

---

### MVP-02: Entidades de Domínio MVP

**Persona**: Desenvolvedor especialista em modelagem de dados e Room Database
**Role/Rules**: Seguindo princípios DDD e Clean Architecture - entidades no domínio
**Objective**: Implementar entidades de domínio para MVP com foco em simplicidade
**Message**: Criar 4 entidades essenciais para MVP com relacionamentos mínimos
**Parameters**: 
- Room Database entities
- TypeConverters para enums
- Relacionamentos 1:N simples
- Validações básicas
**Task**: 4 entidades funcionais com Room annotations e TypeConverters

```kotlin
// Entidades MVP Simplificadas

@Entity(tableName = "child_profile")
data class ChildProfile(
    @PrimaryKey val id: String = "default_child",
    val name: String,
    val gender: Gender,
    val photoUri: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class Gender { MALE, FEMALE }

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val iconRes: Int,
    val time: String, // HH:mm para ordenação
    val stars: Int, // 1-5
    val status: TaskStatus = TaskStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
)

enum class TaskStatus { PENDING, COMPLETED, CANCELED }

@Entity(tableName = "steps")
data class Step(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val taskId: Long,
    val title: String,
    val order: Int,
    val isCompleted: Boolean = false
)

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey val id: String = "settings",
    val isFirstRun: Boolean = true,
    val totalStars: Int = 0,
    val currentDate: String = ""
)
```

**TypeConverters Necessários**:
```kotlin
class Converters {
    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name
    
    @TypeConverter
    fun toGender(gender: String): Gender = Gender.valueOf(gender)
    
    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name
    
    @TypeConverter
    fun toTaskStatus(status: String): TaskStatus = TaskStatus.valueOf(status)
}
```

**Critérios de Validação**:
- [ ] 4 entidades criadas com annotations Room
- [ ] TypeConverters funcionando
- [ ] Enums TaskStatus e Gender implementados
- [ ] Relacionamentos 1:N definidos
- [ ] KDocs completos nas entidades

---

### MVP-03: Database e DAOs MVP

**Persona**: Desenvolvedor especialista em Room Database e SQLite
**Role/Rules**: Seguindo padrões Room, queries otimizadas e Flow para reatividade
**Objective**: Implementar AppDatabase e DAOs para operações CRUD MVP
**Message**: Criar database Room com 4 DAOs essenciais e queries otimizadas
**Parameters**:
- Room Database versão 1
- DAOs com Flow para reatividade
- Queries específicas para MVP
- Migrations preparadas
**Task**: Database funcional com DAOs testáveis e queries otimizadas

```kotlin
// DAOs MVP Essenciais

@Dao
interface ChildProfileDao {
    @Query("SELECT * FROM child_profile WHERE id = :id")
    fun getProfile(id: String = "default_child"): Flow<ChildProfile?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: ChildProfile)
    
    @Query("SELECT COUNT(*) FROM child_profile")
    suspend fun getProfileCount(): Int
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY time ASC")
    fun getAllTasksOrderedByTime(): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Long): Flow<Task?>
    
    @Insert
    suspend fun insertTask(task: Task): Long
    
    @Update
    suspend fun updateTask(task: Task)
    
    @Delete
    suspend fun deleteTask(task: Task)
    
    @Query("UPDATE tasks SET status = :status WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Long, status: TaskStatus)
}

@Dao
interface StepDao {
    @Query("SELECT * FROM steps WHERE taskId = :taskId ORDER BY `order` ASC")
    fun getStepsByTask(taskId: Long): Flow<List<Step>>
    
    @Insert
    suspend fun insertStep(step: Step)
    
    @Update
    suspend fun updateStep(step: Step)
    
    @Delete
    suspend fun deleteStep(step: Step)
}

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = :id")
    fun getSettings(id: String = "settings"): Flow<AppSettings?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: AppSettings)
}
```

**AppDatabase**:
```kotlin
@Database(
    entities = [ChildProfile::class, Task::class, Step::class, AppSettings::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun childProfileDao(): ChildProfileDao
    abstract fun taskDao(): TaskDao
    abstract fun stepDao(): StepDao
    abstract fun appSettingsDao(): AppSettingsDao
}
```

**Critérios de Validação**:
- [ ] Database compila sem erros
- [ ] 4 DAOs com operações CRUD
- [ ] Queries otimizadas com Flow
- [ ] TypeConverters integrados
- [ ] Módulo Hilt para Database criado

---

### MVP-04: Repositórios MVP

**Persona**: Desenvolvedor especialista em Repository Pattern e Clean Architecture
**Role/Rules**: Seguindo Repository Pattern, Single Source of Truth e tratamento de erros
**Objective**: Implementar repositórios para abstrair acesso aos dados
**Message**: Criar interfaces de repositório no domínio e implementações na infraestrutura
**Parameters**:
- Interfaces no domain/repository
- Implementações em data/repository
- Result wrapper para tratamento de erros
- Flow para reatividade
**Task**: 4 repositórios funcionais com interfaces e implementações

```kotlin
// Domain - Interfaces

interface ChildProfileRepository {
    fun getProfile(): Flow<ChildProfile?>
    suspend fun saveProfile(profile: ChildProfile): Result<Unit>
    suspend fun hasProfile(): Boolean
}

interface TaskRepository {
    fun getAllTasksOrderedByTime(): Flow<List<Task>>
    fun getTaskById(id: Long): Flow<Task?>
    suspend fun insertTask(task: Task): Result<Long>
    suspend fun updateTask(task: Task): Result<Unit>
    suspend fun updateTaskStatus(taskId: Long, status: TaskStatus): Result<Unit>
    suspend fun deleteTask(task: Task): Result<Unit>
}

interface StepRepository {
    fun getStepsByTask(taskId: Long): Flow<List<Step>>
    suspend fun insertStep(step: Step): Result<Unit>
    suspend fun updateStep(step: Step): Result<Unit>
    suspend fun deleteStep(step: Step): Result<Unit>
}

interface AppSettingsRepository {
    fun getSettings(): Flow<AppSettings?>
    suspend fun updateSettings(settings: AppSettings): Result<Unit>
    suspend fun isFirstRun(): Boolean
    suspend fun markFirstRunCompleted(): Result<Unit>
}
```

**Data - Implementações**:
```kotlin
@Singleton
class ChildProfileRepositoryImpl @Inject constructor(
    private val dao: ChildProfileDao
) : ChildProfileRepository {
    
    override fun getProfile(): Flow<ChildProfile?> = dao.getProfile()
    
    override suspend fun saveProfile(profile: ChildProfile): Result<Unit> {
        return try {
            dao.insertOrUpdate(profile)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun hasProfile(): Boolean {
        return dao.getProfileCount() > 0
    }
}

// Similar implementations for other repositories...
```

**Result Wrapper**:
```kotlin
sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val exception: Throwable) : AppResult<Nothing>()
    
    inline fun onSuccess(action: (value: T) -> Unit): AppResult<T> {
        if (this is Success) action(data)
        return this
    }
    
    inline fun onError(action: (exception: Throwable) -> Unit): AppResult<T> {
        if (this is Error) action(exception)
        return this
    }
}
```

**Critérios de Validação**:
- [ ] 4 interfaces no domain/repository
- [ ] 4 implementações em data/repository
- [ ] Result wrapper implementado
- [ ] Tratamento de erros em todas as operações
- [ ] Módulos Hilt para injeção criados

---

### MVP-05: Use Cases MVP

**Persona**: Desenvolvedor especialista em Clean Architecture e lógica de negócio
**Role/Rules**: Seguindo princípios SOLID, Single Responsibility e Domain-Driven Design
**Objective**: Implementar Use Cases para orquestrar lógica de negócio MVP
**Message**: Criar Use Cases essenciais para operações MVP com validações
**Parameters**:
- Use Cases no domain/usecase
- Validações de regras de negócio
- Composição de operações
- Tratamento de erros
**Task**: Use Cases funcionais com lógica de negócio e validações

```kotlin
// Use Cases MVP Essenciais

@Singleton
class SaveChildProfileUseCase @Inject constructor(
    private val repository: ChildProfileRepository
) {
    suspend operator fun invoke(
        name: String,
        gender: Gender,
        photoUri: String? = null
    ): AppResult<Unit> {
        // Validações
        if (name.isBlank()) {
            return AppResult.Error(IllegalArgumentException("Nome é obrigatório"))
        }
        
        if (name.length < 2) {
            return AppResult.Error(IllegalArgumentException("Nome deve ter pelo menos 2 caracteres"))
        }
        
        val profile = ChildProfile(
            name = name.trim(),
            gender = gender,
            photoUri = photoUri
        )
        
        return try {
            repository.saveProfile(profile)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}

@Singleton
class GetChildProfileUseCase @Inject constructor(
    private val repository: ChildProfileRepository
) {
    operator fun invoke(): Flow<ChildProfile?> = repository.getProfile()
}

@Singleton
class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String = "",
        iconRes: Int,
        time: String,
        stars: Int,
        steps: List<String> = emptyList()
    ): AppResult<Long> {
        // Validações
        if (title.isBlank()) {
            return AppResult.Error(IllegalArgumentException("Título é obrigatório"))
        }
        
        if (!isValidTime(time)) {
            return AppResult.Error(IllegalArgumentException("Horário inválido (formato HH:mm)"))
        }
        
        if (stars !in 1..5) {
            return AppResult.Error(IllegalArgumentException("Estrelas devem estar entre 1 e 5"))
        }
        
        val task = Task(
            title = title.trim(),
            description = description.trim(),
            iconRes = iconRes,
            time = time,
            stars = stars
        )
        
        return try {
            val taskId = taskRepository.insertTask(task).getOrThrow()
            
            // Inserir steps se houver
            steps.forEachIndexed { index, stepTitle ->
                val step = Step(
                    taskId = taskId,
                    title = stepTitle.trim(),
                    order = index + 1
                )
                stepRepository.insertStep(step)
            }
            
            AppResult.Success(taskId)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
    
    private fun isValidTime(time: String): Boolean {
        return try {
            val parts = time.split(":")
            if (parts.size != 2) return false
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()
            hour in 0..23 && minute in 0..59
        } catch (e: Exception) {
            false
        }
    }
}

@Singleton
class GetTasksOrderedByTimeUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> = repository.getAllTasksOrderedByTime()
}

@Singleton
class UpdateTaskStatusUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val settingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(
        taskId: Long,
        status: TaskStatus
    ): AppResult<Unit> {
        return try {
            taskRepository.updateTaskStatus(taskId, status)
            
            // Se completada, adicionar estrelas
            if (status == TaskStatus.COMPLETED) {
                // Lógica para adicionar estrelas será implementada
            }
            
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}

@Singleton
class CheckFirstRunUseCase @Inject constructor(
    private val repository: AppSettingsRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isFirstRun()
    }
}

@Singleton
class CompleteOnboardingUseCase @Inject constructor(
    private val repository: AppSettingsRepository
) {
    suspend operator fun invoke(): AppResult<Unit> {
        return repository.markFirstRunCompleted()
    }
}
```

**Critérios de Validação**:
- [ ] 6 Use Cases essenciais implementados
- [ ] Validações de regras de negócio
- [ ] Tratamento de erros consistente
- [ ] Composição de operações (task + steps)
- [ ] KDocs completos
- [ ] Testes unitários básicos

---

## DIA 2 - INTERFACE (8h)

### MVP-06: Theme e Design System MVP

**Persona**: Designer de UX/UI especialista em Material Design e acessibilidade para TEA
**Role/Rules**: Seguindo Material 3, Design System e guidelines de acessibilidade TEA
**Objective**: Criar tema consistente e acessível para crianças com autismo
**Message**: Implementar Design System MVP com cores, tipografia e componentes base
**Parameters**:
- Material 3 Design System
- Cores adequadas para TEA
- Tipografia legível
- Acessibilidade (48dp mínimo para touch)
- Modo claro otimizado
**Task**: Theme funcional com cores, tipografia e shapes adequados para MVP

```kotlin
// Theme MVP para TEA

@Composable
fun PequenosPassosTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PequenosPassosColors,
        typography = PequenosPassosTypography,
        shapes = PequenosPassosShapes,
        content = content
    )
}

// Cores otimizadas para TEA
val PequenosPassosColors = lightColorScheme(
    primary = Color(0xFF4A90E2),        // Azul calmo
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF1565C0),
    
    secondary = Color(0xFF4CAF50),      // Verde sucesso
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8F5E8),
    
    tertiary = Color(0xFFFF9500),       // Laranja atenção
    onTertiary = Color.White,
    
    error = Color(0xFFF44336),          // Vermelho cancelada
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    
    surface = Color.White,
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF757575),
    
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF212121),
    
    outline = Color(0xFFE0E0E0),
    outlineVariant = Color(0xFFEEEEEE)
)

// Tipografia legível para crianças
val PequenosPassosTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

// Shapes arredondadas e amigáveis
val PequenosPassosShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)
```

**Cores Auxiliares**:
```kotlin
object PequenosPassosExtendedColors {
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9500)
    val Info = Color(0xFF2196F3)
    val Pending = Color(0xFFFF9500)
    val Completed = Color(0xFF4CAF50)
    val Canceled = Color(0xFFF44336)
    
    // Cores para status de tarefas
    val TaskPending = Color(0xFFFFF3E0)      // Laranja claro
    val TaskCompleted = Color(0xFFE8F5E8)    // Verde claro
    val TaskCanceled = Color(0xFFFFEBEE)     // Vermelho claro
}
```

**Critérios de Validação**:
- [ ] Theme Material 3 implementado
- [ ] Cores adequadas para TEA validadas
- [ ] Tipografia legível configurada
- [ ] Shapes arredondadas definidas
- [ ] Cores de status implementadas
- [ ] Preview themes funcionando

---

### MVP-07: Componentes Reutilizáveis MVP

**Persona**: Desenvolvedor especialista em Jetpack Compose e componentes reutilizáveis
**Role/Rules**: Seguindo princípios de reutilização, acessibilidade e design consistente
**Objective**: Criar componentes base para construção das telas MVP
**Message**: Implementar componentes reutilizáveis específicos para MVP
**Parameters**:
- Componentes Material 3 customizados
- Acessibilidade integrada
- Estados visuais claros
- Tamanhos adequados para TEA (48dp mínimo)
**Task**: 6 componentes essenciais funcionais e acessíveis

```kotlin
// Componentes MVP Essenciais

@Composable
fun ChildProfileCard(
    name: String,
    photoUri: String?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = photoUri ?: R.drawable.ic_child_placeholder,
                contentDescription = "Foto de $name",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Meu perfil",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onExecute: () -> Unit,
    onComplete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (task.status) {
                TaskStatus.PENDING -> PequenosPassosExtendedColors.TaskPending
                TaskStatus.COMPLETED -> PequenosPassosExtendedColors.TaskCompleted
                TaskStatus.CANCELED -> PequenosPassosExtendedColors.TaskCanceled
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = task.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = task.time,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                TaskStatusBadge(status = task.status)
            }
            
            if (task.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            StarRating(
                stars = task.stars,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TaskActionButtons(
                status = task.status,
                onExecute = onExecute,
                onComplete = onComplete,
                onCancel = onCancel
            )
        }
    }
}

@Composable
fun TaskStatusBadge(
    status: TaskStatus,
    modifier: Modifier = Modifier
) {
    val (text, color, icon) = when (status) {
        TaskStatus.PENDING -> Triple("Pendente", PequenosPassosExtendedColors.Pending, Icons.Default.Schedule)
        TaskStatus.COMPLETED -> Triple("Concluída", PequenosPassosExtendedColors.Completed, Icons.Default.CheckCircle)
        TaskStatus.CANCELED -> Triple("Cancelada", PequenosPassosExtendedColors.Canceled, Icons.Default.Cancel)
    }
    
    Row(
        modifier = modifier
            .background(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun StarRating(
    stars: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < stars) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = if (index < stars) "Estrela preenchida" else "Estrela vazia",
                modifier = Modifier.size(20.dp),
                tint = if (index < stars) Color(0xFFFFD700) else Color(0xFFE0E0E0)
            )
        }
    }
}

@Composable
fun TaskActionButtons(
    status: TaskStatus,
    onExecute: () -> Unit,
    onComplete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        when (status) {
            TaskStatus.PENDING -> {
                Button(
                    onClick = onExecute,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("FAZER")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CANCELAR")
                }
            }
            
            TaskStatus.COMPLETED -> {
                Button(
                    onClick = { /* Disabled */ },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PequenosPassosExtendedColors.Completed
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CONCLUÍDA")
                }
            }
            
            TaskStatus.CANCELED -> {
                Button(
                    onClick = { /* Reset to pending */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("TENTAR NOVAMENTE")
                }
            }
        }
    }
}

@Composable
fun GenderSelector(
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GenderOption(
            gender = Gender.MALE,
            isSelected = selectedGender == Gender.MALE,
            onClick = { onGenderSelected(Gender.MALE) }
        )
        
        GenderOption(
            gender = Gender.FEMALE,
            isSelected = selectedGender == Gender.FEMALE,
            onClick = { onGenderSelected(Gender.FEMALE) }
        )
    }
}

@Composable
private fun GenderOption(
    gender: Gender,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val (text, icon) = when (gender) {
        Gender.MALE -> "Menino" to Icons.Default.Person
        Gender.FEMALE -> "Menina" to Icons.Default.Person
    }
    
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(48.dp),
                tint = if (isSelected) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = if (isSelected) 
                    MaterialTheme.colorScheme.onPrimaryContainer 
                else 
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
```

**Critérios de Validação**:
- [ ] 6 componentes implementados e funcionais
- [ ] Acessibilidade (contentDescription, tamanhos mínimos)
- [ ] Estados visuais claros para TaskStatus
- [ ] Componentes responsivos e reutilizáveis
- [ ] Previews Compose funcionando
- [ ] Integração com theme MVP

---

### MVP-08: SplashScreen MVP

**Persona**: Desenvolvedor especialista em UI/UX e inicialização de aplicações Android
**Role/Rules**: Seguindo Android App Startup, Splash Screen API e boas práticas de UX
**Objective**: Implementar SplashScreen funcional com verificação de primeiro acesso
**Message**: Criar SplashScreen que verifica onboarding e inicializa app MVP
**Parameters**:
- Splash Screen API nativa
- Verificação de primeiro acesso
- Inicialização do banco de dados
- Transição suave para próxima tela
- Tempo mínimo/máximo configurável
**Task**: SplashScreen funcional com navegação condicional

```kotlin
// SplashScreen MVP

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configurar Splash Screen nativo
        installSplashScreen()
        
        setContent {
            PequenosPassosTheme {
                PequenosPassosApp()
            }
        }
    }
}

@Composable
fun PequenosPassosApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(navController = navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        // ... outras rotas MVP
    }
}

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
    
    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            SplashNavigationEvent.NavigateToOnboarding -> {
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            SplashNavigationEvent.NavigateToHome -> {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            null -> { /* Aguardando inicialização */ }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo do app
            Icon(
                painter = painterResource(id = R.drawable.ic_logo_pequenos_passos),
                contentDescription = "Logo Pequenos Passos",
                modifier = Modifier.size(120.dp),
                tint = Color.White
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Pequenos Passos",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Autonomia através de rotinas",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Loading indicator
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = Color.White,
                    strokeWidth = 3.dp
                )
            }
            
            // Erro se houver
            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Erro ao inicializar: ${uiState.error}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { viewModel.retry() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Tentar Novamente")
                }
            }
        }
    }
}

// ViewModel
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkFirstRunUseCase: CheckFirstRunUseCase,
    private val childProfileRepository: ChildProfileRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()
    
    fun initialize() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Aguardar tempo mínimo para mostrar splash
                delay(1500)
                
                // Verificar se é primeiro acesso
                val isFirstRun = checkFirstRunUseCase()
                val hasProfile = childProfileRepository.hasProfile()
                
                val navigationEvent = when {
                    isFirstRun || !hasProfile -> SplashNavigationEvent.NavigateToOnboarding
                    else -> SplashNavigationEvent.NavigateToHome
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    navigationEvent = navigationEvent
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun retry() {
        initialize()
    }
}

// Estados
data class SplashUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigationEvent: SplashNavigationEvent? = null
)

sealed class SplashNavigationEvent {
    object NavigateToOnboarding : SplashNavigationEvent()
    object NavigateToHome : SplashNavigationEvent()
}

// Rotas
object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val TASK_FORM = "task_form"
    const val TASK_EXECUTION = "task_execution/{taskId}"
}
```

**Critérios de Validação**:
- [ ] SplashScreen exibe logo e loading
- [ ] Verificação de primeiro acesso funcional
- [ ] Navegação condicional implementada
- [ ] Tratamento de erros
- [ ] Tempo mínimo de splash respeitado
- [ ] Transições suaves

---

## 📝 CONTINUAÇÃO DOS PROMPTS

Os prompts MVP-09 a MVP-15 serão implementados na sequência, mantendo a mesma
estrutura PROMPT e critérios de validação rigorosos definidos no GUIDELINES.md.

**Próximos Prompts**:
- MVP-09: OnboardingScreen MVP (Cadastro da criança)
- MVP-10: TaskFormScreen MVP (Cadastro de tarefas)
- MVP-11: HomeScreen MVP (Lista ordenada por horário)
- MVP-12: TaskExecutionScreen MVP (Execução com steps)
- MVP-13: Navegação MVP (NavGraph completo)
- MVP-14: Testes Unitários MVP (Cobertura essencial)
- MVP-15: Validação Final MVP (Build e deploy)

**Status de Validação**: Cada prompt deve ser validado antes de prosseguir para o
próximo, com status atualizado no CHANGELOG.md conforme diretrizes.

---

# Estrutura Hierárquica de Rotinas
No PequenosPassos, uma Rotina representa o conjunto de atividades (tarefas) que
devem ser realizadas em um dia. Cada Rotina é composta por diversas Tarefas, que
são as atividades principais do fluxo diário. Cada Tarefa, por sua vez, pode ser
detalhada em um passo-a-passo, formado por Subtarefas (Steps), que orientam a
execução da atividade de forma sequencial e didática.

Estrutura:
- Rotina: conjunto de Tarefas do dia.
- Tarefa: atividade principal da rotina.
- Subtarefas (Steps): etapas sequenciais para realizar cada Tarefa.

Essa abordagem facilita o acompanhamento, personalização e gamificação das
atividades diárias, promovendo autonomia e organização para crianças com TEA.

# ESPECIFICAÇÃO COMPLETA - APP PEQUENOS PASSOS

## Personalização
O app permite personalizar:
- Imagens de cada rotina, tarefa, subtarefa e recompensa
- Áudios para cada rotina, tarefa, subtarefa e recompensa (mensagens, instruções, feedbacks positivos)
- Mensagens de instrução e feedback positivo (texto e áudio)

O aplicativo será preenchido inicialmente com uma base de dados padrão de:
- Imagens padrão para rotinas, tarefas, subtarefas e recompensas
- Mensagens padrão (texto e áudio) para cada tarefa, subtarefa e recompensa

Todos esses itens podem ser personalizados pelo usuário conforme necessidade.

## 🗂️ Modelagem das Entidades

### 3. Entidade Task (Tarefa)
- id (autoincrement)
- routineId (Foreign Key)
- title (String)
- iconRes (Int - recurso drawable)
- time (String - formato HH:mm)
- stars (Int - 1 a 5)
- observation (String - nullable)
- daysOfWeek (List<DayOfWeek>)
- isActive (Boolean)
- steps (List<Step>) // NOVO: lista de subtarefas/passos

#### Estrutura Step (Passo)
- id (autoincrement)
- taskId (Foreign Key)
- title (String)
- description (String - nullable)
- order (Int - sequência do passo)
- estimatedTime (String - formato MM:ss)
- isCompleted (Boolean)

> Cada Task pode conter múltiplos Steps, cada um com tempo determinado e ordem de execução.

## 📋 Visão Geral do Projeto

**Aplicativo de Atividades de Vida Diária (AVDs) para crianças com TEA até 6 anos**

Sistema de rotinas gamificado com perfis múltiplos, tarefas visuais e sistema de
recompensas, desenvolvido para auxiliar no desenvolvimento de autonomia e
habilidades sociais.

## 🔄 Contexto do Projeto

### Evolução do FonoVirtual
Este projeto representa a evolução do aplicativo "FonoVirtual", sendo renomeado
para "Pequenos Passos" com foco específico em crianças com TEA (Transtorno do
Espectro Autista).

### Funcionalidades Mantidas
- Módulos ASR (Automatic Speech Recognition) usando Vosk
- Módulos TTS (Text-to-Speech) nativo do Android
- Arquitetura base com MVVM e Clean Architecture
- SplashScreen e navegação básica

### Funcionalidades Removidas
- Telas de exercícios fonoaudiológicos gerais
- Módulos não relacionados ao público TEA
- Interfaces complexas inadequadas para crianças
- Sistema de "Teste Rápido" com exercícios de pronúncia

### 🎯 Objetivo Principal
Facilitar o desenvolvimento de autonomia em crianças com TEA através de rotinas
estruturadas, feedback visual e sistema de recompensas motivador.

### 🧩 Requisitos Específicos para TEA

#### Interface Adaptada:
- Cores suaves e não estimulantes excessivamente
- Botões grandes (mínimo 48dp) para facilitar toque
- Animações sutis (evitar estímulos excessivos)
- Feedback visual claro e imediato
- Navegação linear e previsível
- Consistência visual entre telas

#### Comunicação:
- Suporte a pictogramas (PECS) ou imagens personalizadas
- Instruções visuais com áudios complementando
- Linguagem simples e direta
- Feedback positivo constante

#### Comportamento:
- Tempo de resposta estendido para interações
- Redução de elementos distrativos
- Estrutura previsível e repetitiva
- Reforço positivo imediato

---

## 🏗️ FASE 1: CONFIGURAÇÃO E ARQUITETURA BASE

### Prompt 1.1 - Splash Screen e Ícone

```
Crie a splash screen e recursos visuais finais:

SplashScreen:
- Exibe logo do "Pequenos Passos" centralizado
- Animação de loading sutil (pulso ou rotação)
- Gradiente de fundo com cores do tema
- Duração: 2-3 segundos
- Transição suave para Home ou Onboarding

Durante o splash:
- Inicializar banco de dados
- Verificar se é primeira execução
- Carregar configurações padrão
- Preparar TTS se habilitado

Design do Ícone do App:
- Elementos visuais:
  * Pegadas pequenas (simbolizando "passos")
  * Cores vibrantes mas suaves
  * Forma circular ou quadrada com cantos arredondados
- Versões adaptativas para Android
- Tamanhos: 48dp, 72dp, 96dp, 144dp, 192dp

Adaptive Icon:
- Foreground: símbolo principal (pegadas)
- Background: gradiente das cores do tema
- Monochrome: versão em preto/branco

SplashScreenTheme:
- windowSplashScreenBackground: cor do tema
- windowSplashScreenAnimatedIcon: ícone animado
- postSplashScreenTheme: tema principal

Configure no AndroidManifest.xml e styles.xml.
```

### Prompt 1.2 - Configuração Inicial do Projeto

```
Crie a estrutura inicial de um projeto Android com as seguintes especificações:

- Linguagem: Kotlin
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34
- Arquitetura: MVVM com Clean Architecture
- Build System: Gradle com Kotlin DSL

Configure o build.gradle.kts (Module: app) com dependências específicas:

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-compiler:2.48")
    
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // Work Manager
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    
    // Coil (imagens)
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Serialization (para backup)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.4")
    testImplementation("io.mockk:mockk:1.13.8")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

Configure também:
- proguard-rules.pro para release
- Permissões no AndroidManifest.xml:
    * RECORD_AUDIO (para ASR)
    * READ_EXTERNAL_STORAGE (para backup)
    * WRITE_EXTERNAL_STORAGE (para backup)
    * POST_NOTIFICATIONS (Android 13+)

Organize a estrutura de pacotes seguindo Clean Architecture:
- data (database, repositories)
- domain (models, usecases)
- presentation (screens, viewmodels)
- di (módulos Hilt)
- utils
```

### Prompt 1.3 - Estrutura de Dados Base

```
Crie as entidades do Room Database para o app de rotinas infantis:

1. Entidade Profile (Perfil/Dependente):
    - id (autoincrement)
    - name (String)
    - birthDate (Long - timestamp)
    - gender (enum: MALE, FEMALE)
    - photoUri (String - nullable)
    - createdAt (Long)

2. Entidade Routine (Rotina):
    - id (autoincrement)
    - name (String)
    - description (String - nullable)
    - createdAt (Long)

3. Entidade Task (Tarefa):
    - id (autoincrement)
    - routineId (Foreign Key)
    - title (String)
    - iconRes (Int - recurso drawable)
    - time (String - formato HH:mm)
    - stars (Int - 1 a 5)
    - observation (String - nullable)
    - daysOfWeek (List<DayOfWeek>)
    - isActive (Boolean)
    - steps (List<Step>) // NOVO: lista de subtarefas/passos

#### Estrutura Step (Passo)
- id (autoincrement)
- taskId (Foreign Key)
- title (String)
- description (String - nullable)
- order (Int - sequência do passo)
- estimatedTime (String - formato MM:ss)
- isCompleted (Boolean)

> Cada Task pode conter múltiplos Steps, cada um com tempo determinado e ordem de execução.

4. Entidade TaskCompletion (Conclusão de Tarefa):
    - id (autoincrement)
    - taskId (Foreign Key)
    - profileId (Foreign Key)
    - completedAt (Long)
    - status (enum: TODO, DONE, CANCELLED)
    - earnedStars (Int)

Inclua os TypeConverters necessários para List e Enum.
Crie o AppDatabase abstrato com versão 1.
```

### Prompt 1.4 - DAOs (Data Access Objects)

```
Crie os DAOs para as entidades do banco de dados:

ProfileDao:
- insert, update, delete
- getAllProfiles(): Flow<List<Profile>>
- getProfileById(id): Flow<Profile?>
- getProfileCount(): Flow<Int>

RoutineDao:
- insert, update, delete
- getAllRoutines(): Flow<List<Routine>>
- getRoutineById(id): Flow<Routine?>

TaskDao:
- insert, update, delete
- getTasksByRoutine(routineId): Flow<List<Task>>
- getTasksByDayOfWeek(day): Flow<List<Task>>
- getTaskById(id): Flow<Task?>

TaskCompletionDao:
- insert, update
- getCompletionsByProfile(profileId, startDate, endDate): Flow<List<TaskCompletion>>
- getCompletionsByTask(taskId, date): Flow<TaskCompletion?>
- getTotalStarsByProfile(profileId): Flow<Int>

Use coroutines e Flow para operações assíncronas.
```

---

## 🎨 FASE 2: UI/UX - SISTEMA DE DESIGN

### Prompt 2.1 - Theme e Cores

```
Crie um sistema de design em Jetpack Compose para app infantil:

Theme.kt:
- Paleta de cores vibrantes e amigáveis:
    * Primary: Azul suave (#4A90E2)
    * Secondary: Laranja alegre (#FF9500)
    * Success: Verde (#4CAF50)
    * Warning: Amarelo (#FFC107)
    * Error: Vermelho suave (#FF5252)
    * Background: Branco (#FFFFFF)
    * Surface: Cinza claro (#F5F5F5)

- Typography apropriada para crianças:
    * Fontes arredondadas e legíveis
    * Tamanhos grandes para facilitar leitura

- Shapes com cantos arredondados (16dp, 24dp)

Crie também um arquivo Colors.kt com cores adicionais para gamificação.
```

### Prompt 2.2 - Componentes Reutilizáveis

```
Crie componentes Compose reutilizáveis para o app:

1. ProfileCard:
    - Exibe foto, nome e idade
    - Card clicável com elevation
    - Indicador de rotinas ativas

2. TaskCard:
    - Ícone ilustrativo
    - Título da tarefa
    - Horário (para garantir uma sequencia adequada)
    - Sistema de estrelas (1-5)
    - Status visual (A fazer, Feito, Cancelado)
    - Botões de ação (concluir, cancelar, editar, deletar)

3. StarRating:
    - Exibição de 1-5 estrelas
    - Versão interativa e read-only
    - Animação ao marcar

4. DayOfWeekSelector:
    - Chips para cada dia da semana (Dom-Sáb)
    - Seleção múltipla
    - Estado visual para dias selecionados

5. TimePickerButton:
    - Botão que abre time picker
    - Exibe horário selecionado

6. CustomButton:
    - Botão primário e secundário
    - Versões com ícone
    - Estados de loading

Todos os componentes devem seguir o theme criado anteriormente.
```

### Prompt 2.3 - Acessibilidade para TEA

```
Implemente recursos de acessibilidade específicos para TEA:

AccessibilityHelper:
- Configuração de semantics para screen readers
- Suporte a TalkBack com descrições claras
- Navegação por foco otimizada
- Contraste de cores ajustável

Recursos TEA específicos:
- Modo de alto contraste
- Redução de animações (configurável)
- Tempo de resposta estendido para interações
- Feedback háptico controlável
- Indicadores visuais de carregamento

ContentDescription padrões:
- Botões: "Botão [ação], toque para [resultado]"
- Imagens: Descrição clara e concisa
- Estados: "Selecionado", "Não selecionado", "Carregando"

Teste com:
- TalkBack ativado
- Navegação apenas por teclado
- Diferentes tamanhos de fonte
- Modo escuro/claro

Configurações de Acessibilidade:
- Velocidade de animações (lenta, normal, desabilitada)
- Tamanho de elementos interativos (48dp mínimo)
- Tempo limite para interações (5s, 10s, sem limite)
- Feedback sonoro para todas as ações
- Confirmação dupla para ações importantes

Implementação:
- Use Modifier.semantics para todos os elementos
- Configure clearAndSetSemantics quando necessário
- Adicione contentDescription em todas as imagens
- Use role apropriado (Button, Checkbox, etc.)
- Implemente onFocusChanged para elementos customizados
```

---

## 👤 FASE 3: MÓDULO DE PERFIS

### 🎯 Objetivos do Módulo
- Gerenciar múltiplos perfis de crianças
- Permitir personalização individual
- Controlar acesso por perfil

### Prompt 3.1 - Repository e UseCases de Perfis

```
Crie a camada de domínio para Perfis:

ProfileRepository (interface):
- getAllProfiles(): Flow<List<Profile>>
- getProfileById(id): Flow<Profile?>
- insertProfile(profile): Result<Long>
- updateProfile(profile): Result<Unit>
- deleteProfile(id): Result<Unit>
- getProfileCount(): Flow<Int>

ProfileRepositoryImpl (implementação com Room)

UseCases:
- GetAllProfilesUseCase
- GetProfileByIdUseCase
- SaveProfileUseCase (insert/update)
- DeleteProfileUseCase
- ValidateProfileUseCase (validações de nome, idade, etc)

Implemente tratamento de erros com sealed class Result<T>.
```

### Prompt 3.2 - Tela de Listagem de Perfis

```
Crie a tela de listagem de perfis (ProfilesScreen) com Jetpack Compose:

ProfilesViewModel:
- StateFlow com lista de perfis
- Função para deletar perfil com confirmação
- Função para navegar para edição
- Loading state

ProfilesScreen:
- TopAppBar com título "Perfis" e botão adicionar
- LazyColumn com ProfileCards
- FloatingActionButton para adicionar novo perfil
- Dialog de confirmação para deletar
- Estado vazio (quando não há perfis)
- Loading indicator

Implemente navegação para tela de adicionar/editar perfil.
```

### Prompt 3.3 - Tela de Adicionar/Editar Perfil

```
Crie a tela de formulário de perfil (ProfileFormScreen):

ProfileFormViewModel:
- State para nome, data de nascimento, gênero, foto
- Validação de campos
- Função para salvar (insert ou update)
- Função para selecionar foto (URI)
- Loading e error states

ProfileFormScreen:
- Campo de foto (círculo clicável para selecionar imagem)
- TextField para nome (obrigatório)
- Botão para selecionar data de nascimento (DatePicker)
- RadioButtons para gênero (Masculino/Feminino)
- Cálculo e exibição automática da idade
- Botão SALVAR
- Validações visuais (erros em vermelho)

Integre com Activity Result API para seleção de foto da galeria.
```

---

## 📅 FASE 4: MÓDULO DE ROTINAS

### 🎯 Funcionalidades Principais
- Criação de rotinas personalizadas
- Organização por dias da semana
- Estruturação hierárquica de atividades

### Prompt 4.1 - Repository e UseCases de Rotinas

```
Crie a camada de domínio para Rotinas:

RoutineRepository (interface):
- getAllRoutines(): Flow<List<Routine>>
- getRoutineById(id): Flow<Routine?>
- insertRoutine(routine): Result<Long>
- updateRoutine(routine): Result<Unit>
- deleteRoutine(id): Result<Unit>

RoutineRepositoryImpl

UseCases:
- GetAllRoutinesUseCase
- GetRoutineByIdUseCase
- SaveRoutineUseCase
- DeleteRoutineUseCase

Implemente tratamento de erros.
```

### Prompt 4.2 - Tela de Gerenciamento de Rotinas

```
Crie a tela de gerenciamento de rotinas (RoutinesScreen):

RoutinesViewModel:
- StateFlow com lista de rotinas
- Função para deletar rotina
- Navegação para edição

RoutinesScreen:
- TopAppBar com título "Gerenciar Rotinas"
- TabRow com dias da semana (Dom-Sáb)
- LazyColumn com cards de rotinas
- Cada card mostra:
    * Nome da rotina
    * Quantidade de tarefas
    * Botões editar e deletar
- FloatingActionButton para adicionar rotina
- Estado vazio

Implemente filtro por dia da semana.
```

### Prompt 4.3 - Tela de Formulário de Rotina

```
Crie a tela de criar/editar rotina (RoutineFormScreen):

RoutineFormViewModel:
- State para nome e descrição
- Validação
- Função salvar

RoutineFormScreen:
- TextField para nome da rotina (obrigatório)
- TextField para descrição (opcional)
- Botão SALVAR
- Navegação de volta após salvar

Design simples e intuitivo.
```

---

## ✅ FASE 5: MÓDULO DE TAREFAS

### 🎯 Características Especiais
- Interface visual amigável para TEA
- Horários estruturados
- Sistema de dificuldade por estrelas
- Feedback imediato

### Prompt 5.1 - Repository e UseCases de Tarefas

```
Crie a camada de domínio para Tarefas:

TaskRepository (interface):
- getTasksByRoutine(routineId): Flow<List<Task>>
- getTasksByDayOfWeek(day): Flow<List<Task>>
- getTaskById(id): Flow<Task?>
- insertTask(task): Result<Long>
- updateTask(task: Task): Result<Unit>
- deleteTask(id): Result<Unit>

TaskRepositoryImpl

UseCases:
- GetTasksByRoutineUseCase
- GetTasksByDayUseCase
- SaveTaskUseCase
- DeleteTaskUseCase
- ValidateTaskUseCase

Inclua validações de horário, estrelas (1-5), dias da semana.
```

### Prompt 5.2 - Tela de Listagem de Tarefas

```
Crie a tela de listagem de tarefas por rotina (TasksScreen):

TasksViewModel:
- StateFlow com lista de tarefas
- Filtro por dia da semana
- Função deletar tarefa
- Ordenação por horário

TasksScreen:
- TopAppBar com nome da rotina
- TabRow com dias da semana
- LazyColumn com TaskCards
- Cada TaskCard mostra:
    * Ícone ilustrativo
    * Nome da tarefa
    * Horário
    * Estrelas
    * Botões de ação (editar, deletar)
- FloatingActionButton para adicionar tarefa

Implemente animações nas transições.
```

### Prompt 5.3 - Formulário de Tarefa Multi-Step

```
Crie o formulário multi-step de tarefa (TaskFormScreen):

TaskFormViewModel:
- Multi-step form state (wizard)
- State para: rotina, título, ícone, horário, estrelas, observação, dias
- Validações por step
- Navegação entre steps

Steps do Formulário:
1. Selecionar Rotina
2. Escolher Tipo de Tarefa (ícone e nome)
3. Definir Horário
4. Selecionar Dificuldade (estrelas 1-5)
5. Adicionar Observação (opcional)
6. Escolher Dias da Semana
7. Confirmar e Salvar

Implemente indicador de progresso e navegação Voltar/Próximo.
```

---

## 🎮 FASE 6: SISTEMA DE GAMIFICAÇÃO

### 🎯 Elementos Motivacionais
- Sistema de estrelas baseado em dificuldade
- Feedback visual imediato
- Recompensas desbloqueáveis
- Progresso visual

### Prompt 6.1 - Repository e UseCases de Conclusão

```
Crie a camada de domínio para Conclusão de Tarefas:

TaskCompletionRepository (interface):
- getCompletionsByProfile(profileId, date): Flow<List<TaskCompletion>>
- getCompletionByTask(taskId, date): Flow<TaskCompletion?>
- completeTask(taskId, profileId, status): Result<Unit>
- getTotalStars(profileId): Flow<Int>
- getStarsByPeriod(profileId, start, end): Flow<Int>

TaskCompletionRepositoryImpl

UseCases:
- CompleteTaskUseCase
- CancelTaskUseCase
- GetDailyTasksUseCase
- CalculateStarsUseCase
- GetTaskCompletionStatsUseCase

Implemente lógica de pontuação (estrelas ganhas = estrelas da tarefa).
```

### Prompt 6.2 - Tela de Rotina Diária Principal

```
Crie a tela principal de rotina diária (DailyRoutineScreen):

DailyRoutineViewModel:
- StateFlow com perfil selecionado
- StateFlow com tarefas do dia agrupadas por horário
- Função para completar tarefa
- Função para cancelar tarefa
- Total de estrelas do dia/perfil

DailyRoutineScreen:
- TopAppBar com:
    * Foto e nome do perfil
    * Data atual (navegável: anterior/próximo)
    * Total de estrelas
- TabRow com horários (Manhã, Tarde, Noite)
- LazyColumn com tarefas do período
- Cada tarefa mostra:
    * Ícone grande e colorido
    * Nome da tarefa
    * Horário
    * Estrelas possíveis
    * Botões: ✓ Concluir | ✗ Cancelar | Detalhes
- Dialog ao clicar na tarefa com:
    * Imagem maior
    * Todas as informações
    * Botões de ação maiores (acessibilidade)
    * Opção de ouvir áudio (TTS)

Implemente animações de confete ao concluir tarefa.
```

---

## 📊 FASE 7: RELATÓRIOS E ANÁLISES

### 🎯 Monitoramento de Progresso
- Estatísticas visuais de conclusão
- Identificação de padrões
- Relatórios para cuidadores

### Prompt 7.1 - Tela de Relatórios

```
Crie a tela de relatórios (ReportsScreen):

ReportsViewModel:
- StateFlow com dados de conclusão
- Filtros: perfil, período (semana/mês)
- Cálculos:
    * Total de tarefas concluídas
    * Total de tarefas canceladas
    * Total de tarefas pendentes
    * Total de estrelas ganhas
    * Taxa de conclusão (%)
    * Tarefa mais realizada
    * Melhor dia da semana

ReportsScreen:
- Seletor de perfil (dropdown)
- Seletor de período
- Cards com estatísticas:
    * Card de estrelas total (destaque visual)
    * Card de conclusão (gráfico circular)
    * Card de tendências
- Lista com detalhamento por dia:
    * Data
    * Tarefas concluídas/total
    * Estrelas do dia
    * Indicador visual de progresso

Use gráficos simples e visuais (barras, pizza).
Cores condicionais (verde para bom desempenho, amarelo para médio).
```

---

## 🎁 FASE 8: SISTEMA DE RECOMPENSAS

### 🎯 Motivação Continuada
- Recompensas virtuais desbloqueáveis
- Sistema de custo em estrelas
- Histórico de conquistas

### Prompt 8.1 - Entidade e Lógica de Recompensas

```
Crie o sistema de recompensas:

Entidade Reward:
- id
- title (String)
- description (String)
- starsCost (Int)
- iconRes (Int)
- isActive (Boolean)

Entidade RewardRedemption:
- id
- rewardId (Foreign Key)
- profileId (Foreign Key)
- redeemedAt (Long)
- starsSpent (Int)

RewardDao:
- CRUD de recompensas
- getActiveRewards()
- getRedemptionsByProfile()

RewardRepository e UseCases:
- GetAvailableRewardsUseCase
- RedeemRewardUseCase (valida se tem estrelas suficientes)
- GetRedemptionHistoryUseCase

Lógica: deduzir estrelas do perfil ao resgatar recompensa.
```

### Prompt 8.2 - Tela de Recompensas

```
Crie a tela de recompensas (RewardsScreen):

RewardsViewModel:
- StateFlow com recompensas disponíveis
- StateFlow com estrelas do perfil
- Função para resgatar recompensa

RewardsScreen:
- Header com:
    * Perfil selecionado
    * Total de estrelas disponíveis (destaque)
- Grid com cards de recompensas:
    * Ícone grande
    * Nome da recompensa
    * Custo em estrelas
    * Botão RESGATAR (desabilitado se não tiver estrelas)
- Ao resgatar:
    * Animação de celebração
    * Dialog de confirmação
    * Atualização do saldo de estrelas
- Tab "Histórico" com recompensas já resgatadas

Design colorido e motivador para crianças.
```

---

## 🏠 FASE 9: TELA INICIAL E NAVEGAÇÃO

### 🎯 Hub Central do Aplicativo
- Acesso rápido a todas as funcionalidades
- Seleção de perfil ativo
- Dashboard de status

### Prompt 9.1 - Tela Principal (Home)

```
Crie a tela principal do app (HomeScreen):

HomeViewModel:
- StateFlow com perfil ativo selecionado
- Função para trocar perfil ativo
- Resumo do dia: tarefas pendentes, concluídas, estrelas

HomeScreen:
- TopAppBar com usuário logado e configurações
- Card do perfil ativo:
    * Foto e nome
    * Botão para trocar perfil
- Grid com menu de opções:
    * Rotina do Dia (destaque)
    * Gerenciar Rotinas
    * Perfis/Dependentes
    * Recompensas
    * Relatórios
    * PECS (básico)
    * Configurações
- BottomNavigationBar:
    * Home
    * Rotina do Dia
    * Perfis

Design amigável e intuitivo, ícones grandes e coloridos.
```

### Prompt 9.2 - Navegação Completa

```
Configure a navegação completa do app com Navigation Compose:

NavGraph:
- Splash Screen (opcional)
- Home Screen (tela principal)
- Profiles Screen (lista de perfis)
- Profile Form Screen (add/edit perfil)
- Routines Screen (gerenciar rotinas)
- Routine Form Screen (add/edit rotina)
- Tasks Screen (lista de tarefas da rotina)
- Task Form Screen (wizard multi-step)
- Daily Routine Screen (rotina do dia do perfil)
- Reports Screen (relatórios)
- Rewards Screen (recompensas)
- Settings Screen (configurações)
- PECS Screen (comunicação básica)
- Onboarding Screen (tutorial)

Implemente:
- Deep linking
- Passagem de argumentos entre telas
- Animações de transição
- Back stack management
- Salvamento de estado

Use sealed class para definir rotas de forma type-safe.
```

---

## 🎨 FASE 10: RECURSOS ESPECIAIS

### 🎯 Acessibilidade e Comunicação
- Biblioteca de ícones ilustrativos
- Text-to-Speech para acessibilidade
- Sistema PECS básico

### Prompt 10.1 - Biblioteca de Ícones de Tarefas

```
Crie um sistema de ícones ilustrativos para tarefas:

TaskIcon (enum class) com categorias:

HIGIENE:
- BRUSH_TEETH (escovar dentes)
- SHOWER (banho)
- WASH_HANDS (lavar mãos)
- BRUSH_HAIR (pentear cabelo)

ALIMENTAÇÃO:
- BREAKFAST (café da manhã)
- LUNCH (almoço)
- DINNER (jantar)
- DRINK_WATER (beber água)

CASA:
- MAKE_BED (arrumar cama)
- ORGANIZE_TOYS (organizar brinquedos)
- PUT_CLOTHES (guardar roupas)

ESCOLA/APRENDIZADO:
- HOMEWORK (lição de casa)
- READ_BOOK (ler livro)
- SCHOOL (escola)

LAZER:
- PLAY_TIME (hora de brincar)
- TV_TIME (assistir TV)
- LISTEN_MUSIC (ouvir música)

ROTINA:
- WAKE_UP (acordar)
- SLEEP_TIME (hora de dormir)
- NAP_TIME (soneca)

Cada enum deve ter:
- Drawable resource ID
- Nome amigável
- Categoria
- Cor associada

Crie uma tela de seleção de ícone em grid com busca por categoria.
```

### Prompt 10.2 - Text-to-Speech para Acessibilidade

```
Implemente funcionalidade de Text-to-Speech:

TTSHelper (classe utilitária):
- Inicialização do TTS
- Função speak(text: String)
- Configuração de idioma (PT-BR)
- Controle de velocidade de fala
- Release de recursos

Integre TTS em:
- Leitura do nome das tarefas (botão de áudio nos cards)
- Leitura de recompensas
- Opção de leitura automática ao abrir tarefa
- Confirmações de conclusão

Adicione controles nas configurações:
- Ativar/desativar TTS
- Velocidade da fala (lenta, normal, rápida)
- Volume específico

Importante para acessibilidade de crianças com TEA.
```

### Prompt 10.3 - Sistema PECS Básico

```
Crie um módulo básico de PECS (Picture Exchange Communication System):

PECSCard:
- Imagem/ícone grande e clara
- Palavra/frase associada
- Categoria de comunicação
- Cor de fundo por categoria

PECSScreen:
- Grid com categorias principais:
    * EU QUERO (desejos básicos)
    * EU SINTO (emoções simples)
    * EU VOU (atividades)
    * OBJETOS (itens do dia a dia)

- Ao clicar em categoria, mostra cards específicos
- Ao clicar no card:
    * Aumenta o tamanho (feedback visual)
    * Reproduz áudio do nome (TTS)
    * Adiciona à "frase construída" (barra no topo)

Funcionalidades:
- Construir frases simples com sequência de cards
- Botão "Falar" que lê a sequência completa
- Botão "Limpar" para recomeçar
- Cards grandes e coloridos para facilitar uso

Nota: Implementação básica, focada em comunicação essencial.
```

---

## ⚙️ FASE 11: CONFIGURAÇÕES E UTILIDADES

### 🎯 Personalização e Controle
- Configurações de acessibilidade
- Backup e restauração
- Notificações inteligentes

### Prompt 11.1 - Tela de Configurações

```
Crie a tela de configurações (SettingsScreen):

Preferências usando DataStore:
- Perfil ativo padrão
- Notificações ativadas
- Som ativado
- TTS ativado
- Velocidade TTS
- Tamanho de fonte

SettingsScreen com seções:

1. PERFIL:
    - Selecionar perfil padrão
    - Modo de entrada (criança/adulto)

2. NOTIFICAÇÕES:
    - Ativar lembretes de tarefas
    - Tempo de antecedência (5, 10, 15 min)
    - Som de notificação

3. ACESSIBILIDADE:
    - TTS ativado
    - Velocidade de fala (lenta, normal, rápida)
    - Tamanho de fonte (pequeno, médio, grande)
    - Alto contraste

4. DADOS:
    - Exportar dados (backup JSON)
    - Importar dados
    - Limpar dados (confirmação dupla)

5. SOBRE:
    - Versão do app (1.0.0)
    - Desenvolvedores
    - Licenças
    - Contato/Suporte

Implemente switches, sliders e dropdowns apropriados.
```

### Prompt 11.2 - Sistema de Notificações

```
Implemente sistema de notificações para lembretes de tarefas:

NotificationHelper:
- Criar notification channel "task_reminders"
- Agendar notificação para tarefa específica
- Cancelar notificações existentes
- Ação rápida "Marcar como concluída"

Use WorkManager para agendar lembretes:
- TaskReminderWorker que roda diariamente
- Verificar tarefas do dia seguinte
- Agendar notificação X minutos antes do horário configurado

Notificação contém:
- Ícone da tarefa
- Título: "Hora da [nome da tarefa]!"
- Horário da tarefa
- Ação: "Concluir" (marca como feita)
- Som configurável

TaskReminderWorker:
- Processa agendamento às 22h de cada dia
- Agenda notificações para próximo dia
- Respeita configurações do usuário (ligado/desligado)
- Cancela notificações de tarefas já concluídas

Implemente permissões para Android 13+ (POST_NOTIFICATIONS).
```

### Prompt 11.3 - Backup e Restauração

```
Implemente funcionalidade de backup/restauração de dados:

BackupManager:
- Função exportData():
    * Exporta todos os dados do Room para JSON estruturado
    * Inclui: perfis, rotinas, tarefas, conclusões, recompensas
    * Salva em Documents/PequenosPassos/backup_[timestamp].json
    * Comprime se necessário

- Função importData(uri: Uri):
    * Lê e valida arquivo JSON
    * Opções: "Substituir tudo" ou "Mesclar dados"
    * Mostra prévia do que será importado
    * Backup automático antes de importar

BackupScreen:
- Card "Fazer Backup":
    * Botão principal
    * Info: data do último backup
    * Tamanho estimado dos dados

- Card "Restaurar Backup":
    * Botão para selecionar arquivo
    * Lista de backups encontrados localmente
    * Prévia do conteúdo antes de restaurar

- Warnings claros sobre perda de dados
- Barra de progresso durante operações
- Mensagens de sucesso/erro

Implemente validação robusta:
- Verificar versão de dados compatível
- Validar integridade do JSON
- Tratar erros de permissão/storage
- Rollback em caso de falha

Use Kotlin Serialization para JSON.
```

---

## 🐛 FASE 12: TESTES E REFINAMENTOS

### 🎯 Qualidade e Confiabilidade
- Testes unitários das regras de negócio
- Testes de UI para fluxos críticos
- Otimizações de performance

### Prompt 12.1 - Testes Unitários

```
Crie testes unitários para as principais UseCases:

Use JUnit, Truth e MockK para:

1. ProfileUseCaseTest:
    - Validação de nome (vazio, muito curto, caracteres especiais)
    - Validação de idade (menor que 0, maior que 18 anos)
    - Save profile (sucesso e falha no repositório)
    - Delete profile com dependências (tarefas existentes)

2. TaskUseCaseTest:
    - Validação de horário (formato HH:mm, range válido)
    - Validação de estrelas (range 1-5)
    - Validação de dias da semana (pelo menos 1 selecionado)
    - Conflito de horários na mesma rotina

3. TaskCompletionUseCaseTest:
    - Cálculo correto de estrelas ganhas
    - Não permitir completar tarefa já concluída no mesmo dia
    - Atualização correta do total de estrelas do perfil
    - Cálculo de estatísticas (taxa de conclusão)

4. RewardUseCaseTest:
    - Validar que não pode resgatar sem estrelas suficientes
    - Dedução correta de estrelas após resgate
    - Histórico de resgates por perfil

5. BackupUseCaseTest:
    - Serialização/deserialização correta dos dados
    - Validação de integridade do backup
    - Tratamento de arquivos corrompidos

Configure Hilt para testes com repositórios fake.
Use TestDispatcher para coroutines.
```

### Prompt 12.2 - Testes de UI com Compose

```
Crie testes de UI para telas principais:

Use Compose Testing com JUnit:

1. ProfilesScreenTest:
    - Verificar exibição de lista de perfis
    - Teste de clique em "Adicionar perfil"
    - Teste de clique para editar perfil existente
    - Verificar estado vazio (sem perfis)
    - Teste de confirmação de delete

2. TaskFormScreenTest:
    - Verificar navegação entre steps do wizard
    - Validação de campos obrigatórios em cada step
    - Teste de salvamento com dados válidos
    - Teste de cancelamento (volta sem salvar)

3. DailyRoutineScreenTest:
    - Verificar carregamento de tarefas do dia
    - Teste de marcar tarefa como concluída
    - Verificar atualização do contador de estrelas
    - Teste de navegação entre datas
    - Teste de filtro por período (manhã/tarde/noite)

4. HomeScreenTest:
    - Verificar cards de navegação
    - Teste de troca de perfil ativo
    - Verificar exibição de resumo do dia

Configure semantics para acessibilidade:
- contentDescription em todos os elementos clicáveis
- Roles apropriados (Button, Checkbox, etc.)
- Labels descritivos para screen readers

Use ComposeTestRule e createComposeRule().
Mock ViewModels com fake data.
```

### Prompt 12.3 - Melhorias de Performance

```
Otimize a performance do app:

1. COMPOSE OTIMIZATIONS:
    - Use keys únicos em LazyColumn/LazyRow/LazyGrid
    - Implemente remember para cálculos custosos
    - Use derivedStateOf para states computados
    - Evite lambdas como parâmetros (cause recomposições)

2. VIEWMODEL OPTIMIZATIONS:
    - Use StateFlow.stateIn para compartilhar fluxos frios
    - Implemente debounce em buscas (300ms)
    - Cache dados frequentemente acessados
    - Cancele coroutines em onCleared()

3. DATABASE OPTIMIZATIONS:
    - Adicione índices em colunas de consulta frequente:
        * Profile.name
        * Task.routineId, Task.time
        * TaskCompletion.profileId, TaskCompletion.completedAt
    - Use @Transaction para operações multi-tabela
    - Limite resultados em consultas grandes (paginação)

4. IMAGE LOADING:
    - Configure Coil com disk cache de 50MB
    - Use placeholder e error handling
    - Implemente cache de memória apropriado
    - Redimensione imagens grandes automaticamente

5. NAVIGATION:
    - Use launchSingleTop para evitar múltiplas instâncias
    - Implemente popUpTo para limpar back stack
    - Cache argumentos pesados entre telas

6. MEMORY MANAGEMENT:
    - Use collectAsStateWithLifecycle() ao invés de collectAsState()
    - Limpe recursos (TTS, NotificationManager) em onCleared()
    - Evite context leaks em ViewModels
    - Use WeakReference para callbacks de longa duração

Execute Android Studio Profiler para:
- Memory leaks
- CPU usage
- Network calls desnecessárias
- Overdraw na UI
```

---

## 🚀 FASE 13: FINALIZAÇÃO E POLIMENTO

### 🎯 Experiência Final do Usuário
- Onboarding intuitivo
- Splash screen atrativa
- Ícone e recursos finais

### Prompt 13.1 - Tela de Onboarding/Tutorial

```
Crie uma tela de onboarding para novos usuários:

OnboardingScreen com HorizontalPager:

Tela 1 - BEM-VINDO:
- Ilustração do logo/mascote do app
- Título: "Bem-vindo ao Pequenos Passos!"
- Descrição: "Ajudando crianças a desenvolver autonomia através de rotinas
  divertidas"

Tela 2 - PERFIS:
- Ilustração de crianças diversas
- Título: "Crie perfis para cada criança"
- Descrição: "Cada criança tem suas próprias rotinas e conquistas"

Tela 3 - ROTINAS:
- Ilustração de calendário com atividades
- Título: "Organize rotinas diárias"
- Descrição: "Crie tarefas com horários e conquiste estrelas"

Tela 4 - GAMIFICAÇÃO:
- Ilustração de estrelas e recompensas
- Título: "Sistema de recompensas"
- Descrição: "Complete tarefas, ganhe estrelas e desbloqueie recompensas"

Tela 5 - ACESSIBILIDADE:
- Ilustração de recursos TEA
- Título: "Feito para o autismo"
- Descrição: "Interface visual, áudio e comunicação PECS incluídos"

Tela 6 - COMEÇAR:
- Botão "Criar primeiro perfil"
- Opção "Pular tutorial" (salva preferência)

Componentes:
- Indicador de página no rodapé
- Botões "Anterior" e "Próximo"
- Animações suaves entre telas
- Design colorido e amigável

Salve no DataStore se já viu o onboarding.
```

### Prompt 13.2 - Validação Final e Documentação

```
Realize validação final e organize documentação:

CHECKLIST DE VALIDAÇÃO:
1. Fluxos principais:
    - [ ] Criar perfil → Criar rotina → Adicionar tarefa → Executar rotina
    - [ ] Completar tarefa → Ganhar estrelas → Resgatar recompensa
    - [ ] Visualizar relatórios → Exportar backup

2. Acessibilidade:
    - [ ] TTS funcionando em português
    - [ ] Botões com contentDescription
    - [ ] Contraste adequado
    - [ ] Tamanhos de toque mínimo (48dp)

3. Performance:
    - [ ] App inicia em < 3 segundos
    - [ ] Navegação fluida sem travamentos
    - [ ] Uso de memória controlado
    - [ ] Sem vazamentos de contexto

4. Edge Cases:
    - [ ] Comportamento sem conexão
    - [ ] Banco vazio (primeiro uso)
    - [ ] Permissões negadas
    - [ ] Interrupções (chamadas, notificações)

DOCUMENTAÇÃO FINAL:
- README.md com:
    * Instruções de instalação
    * Funcionalidades principais
    * Screenshots das telas
    * Requisitos do sistema
    * Informações de contato

- CHANGELOG.md atualizado:
    * Versão 1.0.0
    * Lista completa de funcionalidades
    * Tecnologias utilizadas

Prepare release notes para publicação:
- Descrição para Google Play Store
- Keywords para SEO
- Screenshots em alta qualidade
- Video demo (opcional)
```

---

## 📱 ESTRUTURA FINAL DO PROJETO

### 🗂️ Organização de Pacotes

```
com.example.pequenospassos/
├── data/
│   ├── database/
│   │   ├── entities/
│   │   ├── dao/
│   │   └── AppDatabase.kt
│   ├── repository/
│   └── datastore/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── components/
│   ├── screens/
│   │   ├── home/
│   │   ├── profiles/
│   │   ├── routines/
│   │   ├── tasks/
│   │   ├── daily/
│   │   ├── reports/
│   │   ├── rewards/
│   │   ├── settings/
│   │   ├── pecs/
│   │   └── onboarding/
│   ├── navigation/
│   └── theme/
├── di/
└── utils/
```

### 🎯 Funcionalidades Implementadas

#### ✅ Funcionalidades Herdadas (do projeto FonoVirtual)
- [x] SplashScreen com logo UNIVESP
- [x] HomeScreen básica com navegação
- [x] Módulos de ASR (reconhecimento de voz) com Vosk
- [x] Módulos de TTS (síntese de fala) nativo
- [x] Tela de Debug para testes

#### 🔄 Core Features (A Implementar)
- [ ] Gerenciamento de múltiplos perfis
- [ ] Criação e edição de rotinas
- [ ] Sistema de tarefas com horários
- [ ] Gamificação com estrelas
- [ ] Sistema de recompensas
- [ ] Relatórios e estatísticas

#### 🔄 Acessibilidade TEA (A Implementar)
- [ ] Interface visual clara e intuitiva especializada para TEA
- [ ] Text-to-Speech em português integrado
- [ ] Sistema PECS básico
- [ ] Feedback visual imediato
- [ ] Navegação simplificada

#### 🔄 Funcionalidades Auxiliares (A Implementar)
- [ ] Notificações de lembrete
- [ ] Backup e restauração
- [ ] Configurações personalizáveis
- [ ] Onboarding para novos usuários


#### Versão 1.0.0 (Em Desenvolvimento - Prioridade Máxima)
- [🔄] Splash Screen e Ícone personalizado do "Pequenos Passos"
- [ ] Sistema de design (Theme e Cores)
- [ ] Componentes reutilizáveis básicos
- [ ] Estrutura de dados base (Room Database)
- [ ] Navegação completa entre telas

#### Versão 1.1 (Próxima)
- [ ] Módulo de Perfis completo
- [ ] Módulo de Rotinas
- [ ] Módulo de Tarefas básico
- [ ] Gamificação inicial (sistema de estrelas)

#### Versão 1.2
- [ ] Sistema de recompensas
- [ ] Relatórios e análises
- [ ] Configurações avançadas
- [ ] Sistema de notificações

#### Versão 1.3
- [ ] Sistema PECS básico
- [ ] Backup e restauração
- [ ] Testes unitários e de UI
- [ ] Otimizações de performance

#### Versão 2.0
- [ ] Sincronização em nuvem
- [ ] Suporte a múltiplos idiomas
- [ ] Integração com calendário
- [ ] Modo colaborativo (família)
- [ ] Inteligência artificial para sugestões
- [ ] Gamificação avançada
- [ ] Modo terapeuta/profissional
