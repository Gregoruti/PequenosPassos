package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.Achievement
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Interface do Repository para operações de Achievement (Conquistas).
 * Define o contrato para acesso aos dados de conquistas e estrelas.
 *
 * MVP08 - Sistema de Gamificação
 */
interface AchievementRepository {

    /**
     * Salva um novo achievement.
     */
    suspend fun saveAchievement(achievement: Achievement): Long

    /**
     * Atualiza um achievement existente.
     */
    suspend fun updateAchievement(achievement: Achievement)

    /**
     * Deleta um achievement.
     */
    suspend fun deleteAchievement(achievement: Achievement)

    /**
     * Busca achievement por ID.
     */
    suspend fun getAchievementById(achievementId: Long): Achievement?

    /**
     * Observa todos os achievements de uma criança.
     */
    fun observeAchievementsByChildId(childId: Long): Flow<List<Achievement>>

    /**
     * Busca todos os achievements de uma criança.
     */
    suspend fun getAchievementsByChildId(childId: Long): List<Achievement>

    /**
     * Busca achievements de uma tarefa específica.
     */
    suspend fun getAchievementsByTaskId(taskId: Long): List<Achievement>

    /**
     * Calcula o total de estrelas de uma criança.
     */
    suspend fun getTotalStars(childId: Long): Int

    /**
     * Calcula estrelas ganhas em um período.
     */
    suspend fun getStarsInPeriod(childId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Int

    /**
     * Conta o total de tarefas completadas.
     */
    suspend fun getTotalTasksCompleted(childId: Long): Int

    /**
     * Conta tarefas completadas em um período.
     */
    suspend fun getTasksCompletedInPeriod(childId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Int

    /**
     * Calcula a média de estrelas por tarefa.
     */
    suspend fun getAverageStars(childId: Long): Double

    /**
     * Busca achievements de um dia específico (para cálculo de streaks).
     */
    suspend fun getAchievementsOnDate(childId: Long, date: LocalDateTime): List<Achievement>
}

