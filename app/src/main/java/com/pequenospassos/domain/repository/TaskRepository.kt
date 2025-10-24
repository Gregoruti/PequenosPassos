package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskCompletion
import com.pequenospassos.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface de repositório para operações de Task.
 *
 * Define o contrato para acesso a dados de tarefas,
 * incluindo CRUD completo e queries especializadas.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @updated MVP-09 (24/10/2025) - Adicionado controle diário de tarefas
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

    // ========================================
    // MVP-09: Controle Diário de Tarefas
    // ========================================

    /**
     * Marca uma tarefa como concluída no dia atual.
     *
     * @param taskId ID da tarefa
     * @param childId ID da criança
     * @param starsEarned Quantidade de estrelas ganhas
     * @return Result com ID da conclusão ou erro
     */
    suspend fun markTaskAsCompleted(
        taskId: String,
        childId: Long,
        starsEarned: Int
    ): Result<Long>

    /**
     * Verifica se uma tarefa foi completada hoje.
     *
     * @param taskId ID da tarefa
     * @param childId ID da criança
     * @return Flow com true se completada, false caso contrário
     */
    fun isTaskCompletedToday(taskId: String, childId: Long): Flow<Boolean>

    /**
     * Obtém total de estrelas ganhas hoje.
     *
     * @param childId ID da criança
     * @return Flow com total de estrelas
     */
    fun getStarsForToday(childId: Long): Flow<Int>

    /**
     * Obtém IDs das tarefas completadas hoje.
     *
     * @param childId ID da criança
     * @return Flow com lista de IDs de tarefas completadas
     */
    fun getCompletedTaskIdsToday(childId: Long): Flow<List<String>>

    /**
     * Obtém histórico de conclusões de tarefas.
     *
     * @param childId ID da criança
     * @param limit Limite de registros
     * @return Flow com lista de conclusões
     */
    fun getCompletionsHistory(childId: Long, limit: Int = 100): Flow<List<TaskCompletion>>

    /**
     * Deleta todas as conclusões de hoje (para Debug/Reset).
     *
     * @param childId ID da criança
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteCompletionsForToday(childId: Long): Result<Unit>

    /**
     * Deleta todas as conclusões (zera estrelas - Debug).
     *
     * @param childId ID da criança
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteAllCompletions(childId: Long): Result<Unit>

    /**
     * Obtém quantidade de tarefas disponíveis hoje (não completadas).
     *
     * @param childId ID da criança
     * @return Flow com quantidade de tarefas disponíveis
     */
    fun getAvailableTasksCountToday(childId: Long): Flow<Int>
}

