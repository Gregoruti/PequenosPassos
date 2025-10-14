package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow

/**
 * Interface de repositório para operações de Task.
 *
 * Define o contrato para acesso a dados de tarefas,
 * incluindo CRUD completo e queries especializadas.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
interface TaskRepository {

    /**
     * Observa todas as tarefas ordenadas por horário.
     *
     * @return Flow com lista de tarefas ordenadas por time
     */
    fun getAllTasksOrderedByTime(): Flow<List<Task>>

    /**
     * Observa uma tarefa específica por ID.
     *
     * @param id ID da tarefa
     * @return Flow com a tarefa ou null se não existir
     */
    fun getTaskById(id: Long): Flow<Task?>

    /**
     * Observa tarefas filtradas por status.
     *
     * @param status Status a filtrar (PENDING, COMPLETED, CANCELED)
     * @return Flow com lista de tarefas do status especificado
     */
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>

    /**
     * Insere uma nova tarefa.
     *
     * @param task Tarefa a ser inserida
     * @return Result com ID da tarefa inserida ou erro
     */
    suspend fun insertTask(task: Task): Result<Long>

    /**
     * Atualiza uma tarefa existente.
     *
     * @param task Tarefa com dados atualizados
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateTask(task: Task): Result<Unit>

    /**
     * Atualiza apenas o status de uma tarefa.
     * Operação otimizada sem necessidade de buscar objeto completo.
     *
     * @param taskId ID da tarefa
     * @param status Novo status
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateTaskStatus(taskId: Long, status: TaskStatus): Result<Unit>

    /**
     * Deleta uma tarefa.
     * Cascade deleta os steps relacionados automaticamente.
     *
     * @param task Tarefa a ser deletada
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteTask(task: Task): Result<Unit>

    /**
     * Retorna a quantidade total de tarefas.
     *
     * @return Result com o número de tarefas ou erro
     */
    suspend fun getTaskCount(): Result<Int>

    /**
     * Deleta todas as tarefas (útil para reset).
     *
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteAllTasks(): Result<Unit>
}

