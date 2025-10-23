package com.pequenospassos.data.database.dao

import androidx.room.*
import com.pequenospassos.domain.model.Achievement
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * DAO para operações de banco de dados relacionadas a Achievements (conquistas).
 *
 * MVP08 - Sistema de Gamificação
 */
@Dao
interface AchievementDao {

    /**
     * Insere um novo achievement no banco de dados.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: Achievement): Long

    /**
     * Atualiza um achievement existente.
     */
    @Update
    suspend fun update(achievement: Achievement)

    /**
     * Deleta um achievement.
     */
    @Delete
    suspend fun delete(achievement: Achievement)

    /**
     * Busca um achievement por ID.
     */
    @Query("SELECT * FROM achievements WHERE id = :achievementId")
    suspend fun getById(achievementId: Long): Achievement?

    /**
     * Observa todos os achievements de uma criança.
     */
    @Query("SELECT * FROM achievements WHERE childId = :childId ORDER BY completedAt DESC")
    fun observeByChildId(childId: Long): Flow<List<Achievement>>

    /**
     * Busca todos os achievements de uma criança.
     */
    @Query("SELECT * FROM achievements WHERE childId = :childId ORDER BY completedAt DESC")
    suspend fun getByChildId(childId: Long): List<Achievement>

    /**
     * Busca achievements de uma tarefa específica.
     */
    @Query("SELECT * FROM achievements WHERE taskId = :taskId ORDER BY completedAt DESC")
    suspend fun getByTaskId(taskId: Long): List<Achievement>

    /**
     * Calcula o total de estrelas de uma criança.
     */
    @Query("SELECT COALESCE(SUM(starsEarned), 0) FROM achievements WHERE childId = :childId")
    suspend fun getTotalStars(childId: Long): Int

    /**
     * Calcula estrelas ganhas em um período específico.
     */
    @Query("""
        SELECT COALESCE(SUM(starsEarned), 0) 
        FROM achievements 
        WHERE childId = :childId 
        AND completedAt >= :startDate 
        AND completedAt <= :endDate
    """)
    suspend fun getStarsInPeriod(childId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Int

    /**
     * Conta o total de tarefas completadas por uma criança.
     */
    @Query("SELECT COUNT(*) FROM achievements WHERE childId = :childId")
    suspend fun getTotalTasksCompleted(childId: Long): Int

    /**
     * Conta tarefas completadas em um período.
     */
    @Query("""
        SELECT COUNT(*) 
        FROM achievements 
        WHERE childId = :childId 
        AND completedAt >= :startDate 
        AND completedAt <= :endDate
    """)
    suspend fun getTasksCompletedInPeriod(childId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Int

    /**
     * Busca a média de estrelas por tarefa.
     */
    @Query("SELECT COALESCE(AVG(starsEarned), 0.0) FROM achievements WHERE childId = :childId")
    suspend fun getAverageStars(childId: Long): Double

    /**
     * Busca achievements de um dia específico para calcular streaks.
     */
    @Query("""
        SELECT * 
        FROM achievements 
        WHERE childId = :childId 
        AND date(completedAt) = date(:date)
        ORDER BY completedAt
    """)
    suspend fun getAchievementsOnDate(childId: Long, date: LocalDateTime): List<Achievement>

    /**
     * Deleta todos os achievements de uma criança.
     */
    @Query("DELETE FROM achievements WHERE childId = :childId")
    suspend fun deleteByChildId(childId: Long)
}

