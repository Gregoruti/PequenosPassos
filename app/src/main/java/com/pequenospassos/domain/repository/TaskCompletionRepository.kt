package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.TaskCompletion
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface do repositório de TaskCompletion.
 *
 * Define operações de acesso a dados para controle diário de tarefas completadas.
 * Permite verificar se uma tarefa foi completada hoje, calcular estrelas do dia,
 * e gerenciar o histórico de conclusões.
 *
 * ## Operações Principais:
 * - Salvar conclusão de tarefa
 * - Verificar se tarefa foi completada hoje
 * - Obter todas as conclusões do dia
 * - Obter histórico de conclusões
 * - Calcular total de estrelas do dia
 * - Limpar conclusões (para debug/reset)
 *
 * @since MVP-09 (24/01/2025)
 * @author PequenosPassos Development Team
 * @validationStatus ⏳ Em desenvolvimento
 */
interface TaskCompletionRepository {

    /**
     * Salva uma nova conclusão de tarefa.
     *
     * @param completion TaskCompletion a ser salvo
     * @return ID do registro inserido
     */
    suspend fun saveCompletion(completion: TaskCompletion): Long

    /**
     * Verifica se uma tarefa específica foi completada hoje.
     *
     * @param taskId ID da tarefa
     * @param childId ID da criança
     * @param date Data a verificar (padrão: hoje)
     * @return true se a tarefa foi completada na data especificada
     */
    suspend fun isTaskCompletedToday(
        taskId: String,
        childId: Long,
        date: LocalDate = LocalDate.now()
    ): Boolean

    /**
     * Obtém todas as conclusões de tarefas de um dia específico.
     *
     * @param childId ID da criança
     * @param date Data a consultar (padrão: hoje)
     * @return Flow com lista de TaskCompletion
     */
    fun getCompletionsForDate(
        childId: Long,
        date: LocalDate = LocalDate.now()
    ): Flow<List<TaskCompletion>>

    /**
     * Obtém todas as conclusões de uma tarefa específica.
     *
     * @param taskId ID da tarefa
     * @param childId ID da criança
     * @return Flow com lista de TaskCompletion
     */
    fun getCompletionsForTask(
        taskId: String,
        childId: Long
    ): Flow<List<TaskCompletion>>

    /**
     * Calcula o total de estrelas ganhas em um dia.
     *
     * @param childId ID da criança
     * @param date Data a calcular (padrão: hoje)
     * @return Total de estrelas ganhas
     */
    suspend fun getTotalStarsForDate(
        childId: Long,
        date: LocalDate = LocalDate.now()
    ): Int

    /**
     * Obtém o histórico de conclusões em um período.
     *
     * @param childId ID da criança
     * @param startDate Data inicial
     * @param endDate Data final
     * @return Flow com lista de TaskCompletion
     */
    fun getCompletionHistory(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<TaskCompletion>>

    /**
     * Deleta uma conclusão específica.
     *
     * @param completion TaskCompletion a ser deletado
     */
    suspend fun deleteCompletion(completion: TaskCompletion)

    /**
     * Deleta todas as conclusões de um dia específico.
     * Útil para funcionalidade de "Zerar tarefas do dia" (Debug).
     *
     * @param childId ID da criança
     * @param date Data cujas conclusões serão deletadas
     */
    suspend fun deleteCompletionsForDate(
        childId: Long,
        date: LocalDate
    )

    /**
     * Deleta todas as conclusões de uma criança.
     * Útil para reset completo (Debug).
     *
     * @param childId ID da criança
     */
    suspend fun deleteAllCompletionsForChild(childId: Long)

    /**
     * Obtém a conclusão mais recente de uma tarefa.
     *
     * @param taskId ID da tarefa
     * @param childId ID da criança
     * @return TaskCompletion mais recente, ou null se não houver
     */
    suspend fun getLastCompletionForTask(
        taskId: String,
        childId: Long
    ): TaskCompletion?

    /**
     * Top N tarefas mais executadas em um intervalo de datas.
     */
    suspend fun getMostExecutedTasksInRange(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int = 3
    ): List<com.pequenospassos.domain.model.TaskExecutionCount>

    /**
     * Top N tarefas menos executadas em um intervalo de datas.
     */
    suspend fun getLeastExecutedTasksInRange(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int = 3
    ): List<com.pequenospassos.domain.model.TaskExecutionCount>
}
