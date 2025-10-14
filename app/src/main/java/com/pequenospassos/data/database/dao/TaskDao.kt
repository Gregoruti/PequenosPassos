package com.pequenospassos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operações de Task.
 *
 * Interface de acesso a dados para tarefas diárias.
 * Implementa queries otimizadas com ordenação automática por horário.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
 */
@Dao
interface TaskDao {

    /**
     * Busca todas as tarefas ordenadas por horário (time ASC).
     * Retorna Flow para observação reativa.
     *
     * @return Flow com lista de tarefas ordenadas
     */
    @Query("SELECT * FROM tasks ORDER BY time ASC")
    fun getAllTasksOrderedByTime(): Flow<List<Task>>

    /**
     * Busca uma tarefa específica por ID.
     *
     * @param id ID da tarefa
     * @return Flow com a tarefa ou null se não existir
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Long): Flow<Task?>

    /**
     * Busca tarefas por status específico.
     *
     * @param status Status a filtrar (PENDING, COMPLETED, CANCELED)
     * @return Flow com lista de tarefas do status
     */
    @Query("SELECT * FROM tasks WHERE status = :status ORDER BY time ASC")
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>

    /**
     * Insere uma nova tarefa no banco.
     *
     * @param task Tarefa a ser inserida
     * @return ID da tarefa inserida
     */
    @Insert
    suspend fun insertTask(task: Task): Long

    /**
     * Atualiza uma tarefa existente.
     *
     * @param task Tarefa com dados atualizados
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * Deleta uma tarefa do banco.
     * Cascade deleta os steps relacionados automaticamente.
     *
     * @param task Tarefa a ser deletada
     */
    @Delete
    suspend fun deleteTask(task: Task)

    /**
     * Atualiza apenas o status de uma tarefa.
     * Query otimizada sem necessidade de buscar objeto completo.
     *
     * @param taskId ID da tarefa
     * @param status Novo status
     */
    @Query("UPDATE tasks SET status = :status WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Long, status: TaskStatus)

    /**
     * Conta total de tarefas no banco.
     *
     * @return Quantidade de tarefas
     */
    @Query("SELECT COUNT(*) FROM tasks")
    suspend fun getTaskCount(): Int

    /**
     * Deleta todas as tarefas (útil para reset/testes).
     */
    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}

