package com.pequenospassos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pequenospassos.domain.model.TaskCompletion
import com.pequenospassos.domain.model.TaskExecutionCount
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * DAO para operações relacionadas a conclusões de tarefas.
 *
 * Queries principais:
 * - Verificar se tarefa foi completada hoje
 * - Obter conclusões de um dia específico
 * - Calcular estrelas ganhas no dia
 * - Obter histórico de conclusões
 *
 * @since MVP-09 (v1.11.0) - Sistema de Controle Diário
 * @author PequenosPassos Development Team
 */
@Dao
interface TaskCompletionDao {

    /**
     * Insere uma nova conclusão de tarefa.
     * Se já existir conclusão para mesma tarefa/criança/dia, substitui.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(completion: TaskCompletion): Long

    /**
     * Verifica se uma tarefa foi completada em uma data específica.
     *
     * @param taskId ID da tarefa
     * @param childId ID da criança
     * @param date Data a verificar
     * @return true se foi completada, false caso contrário
     */
    @Query("""
        SELECT COUNT(*) > 0 
        FROM task_completions 
        WHERE taskId = :taskId 
        AND childId = :childId 
        AND date = :date
    """)
    suspend fun isTaskCompletedOnDate(
        taskId: String,
        childId: Long,
        date: LocalDate
    ): Boolean

    /**
     * Verifica se uma tarefa foi completada hoje.
     * Usa Flow para reatividade.
     */
    @Query("""
        SELECT COUNT(*) > 0 
        FROM task_completions 
        WHERE taskId = :taskId 
        AND childId = :childId 
        AND date = :today
    """)
    fun isTaskCompletedTodayFlow(
        taskId: String,
        childId: Long,
        today: LocalDate
    ): Flow<Boolean>

    /**
     * Obtém todas as conclusões de uma criança em uma data específica.
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
        ORDER BY completedAt DESC
    """)
    fun getCompletionsForDate(
        childId: Long,
        date: LocalDate
    ): Flow<List<TaskCompletion>>

    /**
     * Obtém todas as conclusões de uma criança em uma data específica (suspend).
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
        ORDER BY completedAt DESC
    """)
    suspend fun getCompletionsForDateSuspend(
        childId: Long,
        date: LocalDate
    ): List<TaskCompletion>

    /**
     * Calcula total de estrelas ganhas em uma data específica.
     */
    @Query("""
        SELECT COALESCE(SUM(starsEarned), 0) 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
    """)
    suspend fun getStarsForDate(
        childId: Long,
        date: LocalDate
    ): Int

    /**
     * Calcula total de estrelas ganhas em uma data específica (Flow).
     */
    @Query("""
        SELECT COALESCE(SUM(starsEarned), 0) 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
    """)
    fun getStarsForDateFlow(
        childId: Long,
        date: LocalDate
    ): Flow<Int>

    /**
     * Conta quantas tarefas foram completadas em uma data específica.
     */
    @Query("""
        SELECT COUNT(*) 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
    """)
    suspend fun getTasksCompletedCountForDate(
        childId: Long,
        date: LocalDate
    ): Int

    /**
     * Obtém IDs de todas as tarefas completadas em uma data específica.
     * Útil para filtrar lista de tarefas disponíveis.
     */
    @Query("""
        SELECT taskId 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
    """)
    suspend fun getCompletedTaskIdsForDate(
        childId: Long,
        date: LocalDate
    ): List<String>

    /**
     * Obtém IDs de todas as tarefas completadas em uma data específica (Flow).
     */
    @Query("""
        SELECT taskId 
        FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
    """)
    fun getCompletedTaskIdsForDateFlow(
        childId: Long,
        date: LocalDate
    ): Flow<List<String>>

    /**
     * Obtém histórico completo de conclusões de uma criança.
     * Ordenado por data decrescente (mais recente primeiro).
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE childId = :childId 
        ORDER BY date DESC, completedAt DESC
        LIMIT :limit
    """)
    fun getCompletionsHistory(
        childId: Long,
        limit: Int = 100
    ): Flow<List<TaskCompletion>>

    /**
     * Obtém conclusões de um período (range de datas).
     * Útil para gráficos e estatísticas.
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE childId = :childId 
        AND date BETWEEN :startDate AND :endDate
        ORDER BY date ASC, completedAt ASC
    """)
    suspend fun getCompletionsInRange(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<TaskCompletion>

    /**
     * Deleta todas as conclusões de uma data específica.
     * Útil para testes/debug e para zerar atividades/estrelas do dia.
     */
    @Query("""
        DELETE FROM task_completions 
        WHERE childId = :childId 
        AND date = :date
    """)
    suspend fun deleteCompletionsForDate(
        childId: Long,
        date: LocalDate
    )

    /**
     * Deleta todas as conclusões de um dia específico para todas as crianças.
     * Usado para zerar estrelas/atividades do dia globalmente (debug/admin).
     */
    @Query("DELETE FROM task_completions WHERE date = :date")
    suspend fun deleteCompletionsForDateAllChildren(date: LocalDate)

    /**
     * Deleta todas as conclusões de uma criança.
     * Útil para reset completo.
     */
    @Query("DELETE FROM task_completions WHERE childId = :childId")
    suspend fun deleteAllForChild(childId: Long)

    /**
     * Obtém todas as conclusões de uma tarefa específica.
     * Útil para histórico de uma tarefa individual.
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE taskId = :taskId 
        AND childId = :childId 
        ORDER BY date DESC, completedAt DESC
    """)
    fun getCompletionsForTaskFlow(
        taskId: String,
        childId: Long
    ): Flow<List<TaskCompletion>>

    /**
     * Obtém conclusões de um período (range de datas) como Flow.
     * Útil para gráficos e estatísticas reativos.
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE childId = :childId 
        AND date BETWEEN :startDate AND :endDate
        ORDER BY date ASC, completedAt ASC
    """)
    fun getCompletionsInDateRange(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<TaskCompletion>>

    /**
     * Deleta uma conclusão específica pelo ID.
     */
    @Query("DELETE FROM task_completions WHERE id = :id")
    suspend fun deleteById(id: Long)

    /**
     * Obtém a conclusão mais recente de uma tarefa específica.
     * Útil para verificar último desempenho.
     */
    @Query("""
        SELECT * 
        FROM task_completions 
        WHERE taskId = :taskId 
        AND childId = :childId 
        ORDER BY date DESC, completedAt DESC 
        LIMIT 1
    """)
    suspend fun getLastCompletionForTask(
        taskId: String,
        childId: Long
    ): TaskCompletion?

    /**
     * Retorna o ranking de execuções por tarefa na semana (domingo a sábado).
     * @param childId ID da criança
     * @param startDate Data inicial (domingo)
     * @param endDate Data final (sábado)
     * @return Lista de pares (taskId, total de execuções), ordenada do maior para o menor
     */
    @Query("""
        SELECT taskId, COUNT(*) as total
        FROM task_completions
        WHERE childId = :childId
        AND date BETWEEN :startDate AND :endDate
        GROUP BY taskId
        ORDER BY total DESC
        LIMIT :limit
    """)
    suspend fun getMostExecutedTasksInRange(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int = 3
    ): List<TaskExecutionCount>

    /**
     * Retorna o ranking de execuções por tarefa na semana (menos executadas).
     * Inclui tarefas nunca executadas (total = 0).
     * @param childId ID da criança
     * @param startDate Data inicial
     * @param endDate Data final
     * @return Lista de pares (taskId, total de execuções), ordenada do menor para o maior
     */
    @Query("""
        SELECT t.id as taskId, COUNT(tc.id) as total
        FROM tasks t
        LEFT JOIN task_completions tc
            ON t.id = tc.taskId AND tc.childId = :childId AND tc.date BETWEEN :startDate AND :endDate
        GROUP BY t.id
        ORDER BY total ASC
        LIMIT :limit
    """)
    suspend fun getLeastExecutedTasksInRange(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int = 3
    ): List<TaskExecutionCount>
}
