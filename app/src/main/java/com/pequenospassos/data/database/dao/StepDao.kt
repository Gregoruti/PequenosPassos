package com.pequenospassos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.TaskWithSteps
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operações de Step.
 *
 * Interface de acesso a dados para passos/subtarefas.
 * Implementa queries otimizadas com ordenação por ordem sequencial.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
 */
@Dao
interface StepDao {

    /**
     * Busca todos os steps de uma tarefa específica.
     * Ordenados por 'order' (sequencial).
     *
     * @param taskId ID da tarefa pai
     * @return Flow com lista de steps ordenados
     */
    @Query("SELECT * FROM steps WHERE taskId = :taskId ORDER BY `order` ASC")
    fun getStepsByTask(taskId: Long): Flow<List<Step>>

    /**
     * Busca uma tarefa com todos os seus steps (relacionamento 1:N).
     * Transaction garante consistência dos dados.
     *
     * @param taskId ID da tarefa
     * @return Flow com TaskWithSteps ou null
     */
    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskWithSteps(taskId: Long): Flow<TaskWithSteps?>

    /**
     * Insere um novo step.
     *
     * @param step Step a ser inserido
     * @return ID do step inserido
     */
    @Insert
    suspend fun insertStep(step: Step): Long

    /**
     * Insere múltiplos steps de uma vez.
     * Útil ao criar tarefa com vários steps.
     *
     * @param steps Lista de steps
     * @return Lista de IDs inseridos
     */
    @Insert
    suspend fun insertSteps(steps: List<Step>): List<Long>

    /**
     * Atualiza um step existente.
     *
     * @param step Step com dados atualizados
     */
    @Update
    suspend fun updateStep(step: Step)

    /**
     * Deleta um step do banco.
     *
     * @param step Step a ser deletado
     */
    @Delete
    suspend fun deleteStep(step: Step)

    /**
     * Atualiza apenas o status de completado de um step.
     * Query otimizada.
     *
     * @param stepId ID do step
     * @param isCompleted Novo status
     */
    @Query("UPDATE steps SET isCompleted = :isCompleted WHERE id = :stepId")
    suspend fun updateStepCompletion(stepId: Long, isCompleted: Boolean)

    /**
     * Deleta todos os steps de uma tarefa específica.
     *
     * @param taskId ID da tarefa pai
     */
    @Query("DELETE FROM steps WHERE taskId = :taskId")
    suspend fun deleteStepsByTask(taskId: Long)

    /**
     * Conta quantos steps uma tarefa possui.
     *
     * @param taskId ID da tarefa
     * @return Quantidade de steps
     */
    @Query("SELECT COUNT(*) FROM steps WHERE taskId = :taskId")
    suspend fun getStepCountByTask(taskId: Long): Int

    /**
     * Deleta todos os steps (útil para reset/testes).
     */
    @Query("DELETE FROM steps")
    suspend fun deleteAll()
}

