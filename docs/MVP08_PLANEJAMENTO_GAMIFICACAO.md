# MVP08 - Sistema de Gamificação Completo

**Versão do Documento:** 1.0  
**Data:** 23/10/2025  
**Status:** 📋 Planejado  
**Prioridade:** 🔴 ALTA

---

## 1. VISÃO GERAL

### 1.1. Objetivo

Implementar um sistema completo de gamificação que motive as crianças a 
completarem suas tarefas através de estrelas, conquistas e recompensas 
visuais.

### 1.2. Situação Atual

**✅ Implementado:**
- Sistema básico de estrelas na tela de conclusão
- Exibição visual de estrelas ao completar tarefa
- Cálculo simples de estrelas baseado em conclusão

**⚠️ Pendente:**
- Persistência de estrelas no banco de dados
- Histórico de conquistas
- Sistema de recompensas desbloqueáveis
- Tabela de progresso
- Estatísticas de gamificação
- Integração completa com perfil da criança

### 1.3. Benefícios Esperados

- ✅ Aumento do engajamento das crianças
- ✅ Motivação através de feedback positivo
- ✅ Senso de progresso e conquista
- ✅ Estímulo à consistência nas tarefas
- ✅ Diversão no processo de aprendizado

---

## 2. REQUISITOS FUNCIONAIS

### RF01 - Sistema de Estrelas

**Descrição:** Calcular e persistir estrelas ganhas por tarefa completada

**Critérios de Aceitação:**
- [ ] Estrelas são calculadas baseadas em critérios configuráveis
- [ ] Estrelas são salvas no banco de dados
- [ ] Histórico de estrelas é mantido
- [ ] Total de estrelas é exibido no perfil
- [ ] Estrelas podem ser visualizadas por período (dia, semana, mês)

**Regras de Negócio:**
- Tarefa completada no tempo: 3 estrelas
- Tarefa completada com pequeno atraso: 2 estrelas
- Tarefa completada com grande atraso: 1 estrela
- Tarefa com todos os steps completados: +1 estrela bônus
- Tarefa realizada sem ajuda (opcional): +1 estrela bônus

### RF02 - Conquistas (Achievements)

**Descrição:** Sistema de conquistas desbloqueáveis

**Critérios de Aceitação:**
- [ ] Conquistas pré-definidas no sistema
- [ ] Conquistas são desbloqueadas automaticamente
- [ ] Notificação visual ao desbloquear conquista
- [ ] Lista de conquistas disponíveis e bloqueadas
- [ ] Progresso de conquistas em andamento

**Exemplos de Conquistas:**
- 🌟 "Primeiro Passo": Complete sua primeira tarefa
- 🔥 "Sequência de 3": Complete tarefas 3 dias seguidos
- 🎯 "Perfeccionista": Complete 10 tarefas com 3 estrelas
- 🏆 "Mestre das Rotinas": Complete 50 tarefas
- ⭐ "Colecionador": Alcance 100 estrelas
- 🌈 "Semana Completa": Complete todas as tarefas por 7 dias

### RF03 - Recompensas Visuais

**Descrição:** Sistema de recompensas que podem ser desbloqueadas

**Critérios de Aceitação:**
- [ ] Recompensas têm custo em estrelas
- [ ] Recompensas são desbloqueadas ao atingir estrelas necessárias
- [ ] Recompensas visuais personalizáveis (temas, avatares, etc.)
- [ ] Galeria de recompensas conquistadas
- [ ] Recompensas podem ser ativadas/desativadas

**Tipos de Recompensas:**
- 🎨 Temas de cores personalizados
- 🖼️ Avatares/personagens colecionáveis
- 🎵 Sons de conclusão personalizados
- 🏅 Badges especiais
- 🌟 Efeitos visuais de conclusão

### RF04 - Estatísticas de Gamificação

**Descrição:** Visualização de progresso e estatísticas

**Critérios de Aceitação:**
- [ ] Total de estrelas acumuladas
- [ ] Estrelas ganhas no período
- [ ] Taxa de conclusão de tarefas
- [ ] Sequência atual de dias
- [ ] Maior sequência alcançada
- [ ] Gráfico de evolução de estrelas

---

## 3. MODELO DE DADOS

### 3.1. Entidade Achievement

```kotlin
@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val childId: Long,
    val taskId: Long,
    val starsEarned: Int,
    val completedAt: LocalDateTime,
    val executionTime: Long, // em segundos
    val wasOnTime: Boolean,
    val allStepsCompleted: Boolean,
    val needsHelp: Boolean = false
)
```

### 3.2. Entidade Reward

```kotlin
@Entity(tableName = "rewards")
data class Reward(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val title: String,
    val description: String,
    val starsRequired: Int,
    val rewardType: RewardType,
    val iconResId: Int?,
    val imageUri: String?,
    val isUnlocked: Boolean = false,
    val unlockedAt: LocalDateTime? = null
)

enum class RewardType {
    THEME,
    AVATAR,
    SOUND,
    BADGE,
    EFFECT
}
```

### 3.3. Entidade UnlockableAchievement

```kotlin
@Entity(tableName = "unlockable_achievements")
data class UnlockableAchievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val code: String, // identificador único
    val title: String,
    val description: String,
    val iconResId: Int,
    val achievementType: AchievementType,
    val targetValue: Int, // valor alvo para desbloquear
    val rewardStars: Int = 0 // estrelas bônus ao desbloquear
)

enum class AchievementType {
    FIRST_TASK,
    STREAK_DAYS,
    PERFECT_TASKS,
    TOTAL_TASKS,
    TOTAL_STARS,
    WEEKLY_COMPLETE,
    CATEGORY_MASTER
}
```

### 3.4. Entidade ChildAchievement

```kotlin
@Entity(
    tableName = "child_achievements",
    foreignKeys = [
        ForeignKey(
            entity = ChildProfile::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UnlockableAchievement::class,
            parentColumns = ["id"],
            childColumns = ["achievementId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChildAchievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val childId: Long,
    val achievementId: Long,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedAt: LocalDateTime? = null
)
```

---

## 4. CASOS DE USO

### 4.1. SaveAchievementUseCase

```kotlin
class SaveAchievementUseCase(
    private val achievementRepository: AchievementRepository
) {
    suspend operator fun invoke(achievement: Achievement): Result<Long> {
        return try {
            val id = achievementRepository.saveAchievement(achievement)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 4.2. GetTotalStarsUseCase

```kotlin
class GetTotalStarsUseCase(
    private val achievementRepository: AchievementRepository
) {
    operator fun invoke(childId: Long): Flow<Int> {
        return achievementRepository.getTotalStars(childId)
    }
}
```

### 4.3. GetAchievementsByChildUseCase

```kotlin
class GetAchievementsByChildUseCase(
    private val achievementRepository: AchievementRepository
) {
    operator fun invoke(childId: Long): Flow<List<Achievement>> {
        return achievementRepository.getAchievementsByChild(childId)
    }
}
```

### 4.4. UnlockRewardUseCase

```kotlin
class UnlockRewardUseCase(
    private val rewardRepository: RewardRepository,
    private val achievementRepository: AchievementRepository
) {
    suspend operator fun invoke(
        rewardId: Long,
        childId: Long
    ): Result<Reward> {
        return try {
            val totalStars = achievementRepository.getTotalStarsSync(childId)
            val reward = rewardRepository.getRewardById(rewardId)
            
            if (totalStars >= reward.starsRequired) {
                val unlockedReward = reward.copy(
                    isUnlocked = true,
                    unlockedAt = LocalDateTime.now()
                )
                rewardRepository.updateReward(unlockedReward)
                Result.success(unlockedReward)
            } else {
                Result.failure(
                    InsufficientStarsException(
                        "Needs ${reward.starsRequired} stars, has $totalStars"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 4.5. CheckAndUnlockAchievementsUseCase

```kotlin
class CheckAndUnlockAchievementsUseCase(
    private val achievementRepository: AchievementRepository,
    private val childAchievementRepository: ChildAchievementRepository
) {
    suspend operator fun invoke(childId: Long): List<UnlockableAchievement> {
        val newlyUnlocked = mutableListOf<UnlockableAchievement>()
        
        val allAchievements = achievementRepository.getAllUnlockableAchievements()
        val childProgress = childAchievementRepository.getByChildId(childId)
        
        allAchievements.forEach { achievement ->
            val progress = childProgress.find { it.achievementId == achievement.id }
            
            if (progress == null || !progress.isUnlocked) {
                val currentValue = calculateProgress(childId, achievement.achievementType)
                
                if (currentValue >= achievement.targetValue) {
                    childAchievementRepository.unlock(childId, achievement.id)
                    newlyUnlocked.add(achievement)
                } else if (progress != null) {
                    childAchievementRepository.updateProgress(
                        progress.copy(currentProgress = currentValue)
                    )
                }
            }
        }
        
        return newlyUnlocked
    }
    
    private suspend fun calculateProgress(
        childId: Long,
        type: AchievementType
    ): Int {
        return when (type) {
            AchievementType.FIRST_TASK -> 
                achievementRepository.getCompletedTasksCount(childId)
            AchievementType.STREAK_DAYS -> 
                achievementRepository.getCurrentStreak(childId)
            AchievementType.PERFECT_TASKS -> 
                achievementRepository.getPerfectTasksCount(childId)
            AchievementType.TOTAL_TASKS -> 
                achievementRepository.getCompletedTasksCount(childId)
            AchievementType.TOTAL_STARS -> 
                achievementRepository.getTotalStarsSync(childId)
            // ... outros tipos
        }
    }
}
```

### 4.6. CalculateStarsUseCase

```kotlin
class CalculateStarsUseCase {
    operator fun invoke(
        completedOnTime: Boolean,
        allStepsCompleted: Boolean,
        needsHelp: Boolean,
        expectedDuration: Long,
        actualDuration: Long
    ): Int {
        var stars = 0
        
        // Base stars por conclusão
        stars += when {
            completedOnTime -> 3
            actualDuration <= expectedDuration * 1.2 -> 2
            else -> 1
        }
        
        // Bônus por completar todos os steps
        if (allStepsCompleted) stars += 1
        
        // Bônus por independência
        if (!needsHelp) stars += 1
        
        return stars.coerceAtMost(5) // máximo 5 estrelas
    }
}
```

---

## 5. INTERFACES DE USUÁRIO

### 5.1. Tela de Recompensas (RewardsScreen)

**Componentes:**
- Header com total de estrelas do perfil
- Tabs: "Conquistas" e "Recompensas"
- Grid de conquistas (desbloqueadas e bloqueadas)
- Grid de recompensas com indicador de progresso
- Diálogo de confirmação ao desbloquear
- Animação de celebração ao desbloquear

**Navegação:**
```
HomeScreen → ProfileButton → RewardsScreen
```

### 5.2. Tela de Perfil Atualizada (ProfileScreen)

**Novos Componentes:**
- Widget de estrelas totais
- Widget de sequência atual
- Últimas conquistas desbloqueadas
- Progresso semanal
- Botão "Ver todas as recompensas"

### 5.3. Notificação de Conquista Desbloqueada

**Componente:**
```kotlin
@Composable
fun AchievementUnlockedDialog(
    achievement: UnlockableAchievement,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícone da conquista com animação
                Icon(
                    painter = painterResource(achievement.iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "🎉 Conquista Desbloqueada!",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleLarge
                )
                
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                if (achievement.rewardStars > 0) {
                    Row {
                        Text("Bônus: ")
                        repeat(achievement.rewardStars) {
                            Icon(Icons.Default.Star, contentDescription = null)
                        }
                    }
                }
                
                Button(onClick = onDismiss) {
                    Text("Continuar")
                }
            }
        }
    }
}
```

---

## 6. REPOSITÓRIOS

### 6.1. AchievementRepository

```kotlin
interface AchievementRepository {
    suspend fun saveAchievement(achievement: Achievement): Long
    fun getAchievementsByChild(childId: Long): Flow<List<Achievement>>
    fun getTotalStars(childId: Long): Flow<Int>
    suspend fun getTotalStarsSync(childId: Long): Int
    suspend fun getCompletedTasksCount(childId: Long): Int
    suspend fun getPerfectTasksCount(childId: Long): Int
    suspend fun getCurrentStreak(childId: Long): Int
    suspend fun getAllUnlockableAchievements(): List<UnlockableAchievement>
}
```

### 6.2. RewardRepository

```kotlin
interface RewardRepository {
    suspend fun getRewardById(id: Long): Reward
    fun getAllRewards(): Flow<List<Reward>>
    fun getUnlockedRewards(): Flow<List<Reward>>
    fun getLockedRewards(): Flow<List<Reward>>
    suspend fun updateReward(reward: Reward)
    suspend fun unlockReward(rewardId: Long)
}
```

---

## 7. CRONOGRAMA DE IMPLEMENTAÇÃO

### Fase 1 - Banco de Dados (2 dias)

**Tarefas:**
- [ ] Criar entidades Achievement, Reward, UnlockableAchievement
- [ ] Criar DAOs correspondentes
- [ ] Atualizar AppDatabase com novas tabelas
- [ ] Criar migration
- [ ] Popular tabela de conquistas pré-definidas
- [ ] Testes de DAOs

### Fase 2 - Camada de Domínio (1 dia)

**Tarefas:**
- [ ] Criar repository interfaces
- [ ] Implementar repositories
- [ ] Criar todos os use cases
- [ ] Testes unitários de use cases

### Fase 3 - UI de Recompensas (2 dias)

**Tarefas:**
- [ ] Criar RewardsScreen
- [ ] Criar ViewModel correspondente
- [ ] Implementar grid de conquistas
- [ ] Implementar grid de recompensas
- [ ] Diálogo de desbloqueio
- [ ] Animações

### Fase 4 - Integração (1 dia)

**Tarefas:**
- [ ] Integrar com TaskCompletionScreen
- [ ] Atualizar ProfileScreen
- [ ] Adicionar navegação
- [ ] Verificar conquistas após cada tarefa
- [ ] Notificações de desbloqueio

### Fase 5 - Testes e Validação (1 dia)

**Tarefas:**
- [ ] Testes E2E de fluxo completo
- [ ] Validação de cálculo de estrelas
- [ ] Validação de desbloqueio de conquistas
- [ ] Testes de performance
- [ ] Ajustes finais

**Total Estimado: 7 dias**

---

## 8. MÉTRICAS DE SUCESSO

- [ ] 100% das conquistas podem ser desbloqueadas
- [ ] Estrelas são calculadas corretamente
- [ ] Sem lag ao desbloquear conquistas
- [ ] Animações fluidas (60 FPS)
- [ ] Dados persistem corretamente
- [ ] Testes passando > 95%

---

## 9. RISCOS E MITIGAÇÕES

**Risco 1:** Complexidade de cálculo de sequências  
**Mitigação:** Implementar cache de cálculos, otimizar queries

**Risco 2:** Muitas notificações podem ser intrusivas  
**Mitigação:** Limitar frequência, permitir configuração

**Risco 3:** Inflação de estrelas desbalanceando o sistema  
**Mitigação:** Testes extensivos de balanceamento, ajuste de valores

---

**Status:** Documento aprovado, pronto para implementação  
**Próxima Ação:** Iniciar Fase 1 após conclusão do Sprint 1 (Documentação)

