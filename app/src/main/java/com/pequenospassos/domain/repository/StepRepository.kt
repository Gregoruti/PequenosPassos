package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.TaskWithSteps
import kotlinx.coroutines.flow.Flow

/**
 * Interface de repositório para operações de Step.
 *
 * Define o contrato para acesso a dados de passos/subtarefas,
 * incluindo operações em lote e relacionamentos.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
interface StepRepository {

    /**
     * Observa todos os steps de uma tarefa específica.
     * Retorna ordenados por 'order' (sequencial).
     *
     * @param taskId ID da tarefa pai
     * @return Flow com lista de steps ordenados
     */
    fun getStepsByTask(taskId: Long): Flow<List<Step>>

    /**
     * Observa uma tarefa com todos os seus steps.
     * Relacionamento 1:N.
     *
     * @param taskId ID da tarefa
     * @return Flow com TaskWithSteps ou null
     */
    fun getTaskWithSteps(taskId: Long): Flow<TaskWithSteps?>

    /**
     * Insere um novo step.
     *
     * @param step Step a ser inserido
     * @return Result com ID do step inserido ou erro
     */
    suspend fun insertStep(step: Step): Result<Long>

    /**
     * Insere múltiplos steps de uma vez.
     * Útil ao criar tarefa com vários steps.
     *
     * @param steps Lista de steps
     * @return Result com lista de IDs inseridos ou erro
     */
    suspend fun insertSteps(steps: List<Step>): Result<List<Long>>

    /**
     * Atualiza um step existente.
     *
     * @param step Step com dados atualizados
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateStep(step: Step): Result<Unit>

    /**
     * Atualiza apenas o status de completado de um step.
     * Operação otimizada.
     *
     * @param stepId ID do step
     * @param isCompleted Novo status
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateStepCompletion(stepId: Long, isCompleted: Boolean): Result<Unit>

    /**
     * Deleta um step.
     *
     * @param step Step a ser deletado
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteStep(step: Step): Result<Unit>

    /**
     * Deleta todos os steps de uma tarefa específica.
     *
     * @param taskId ID da tarefa pai
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteStepsByTask(taskId: Long): Result<Unit>

    /**
     * Retorna a quantidade de steps de uma tarefa.
     *
     * @param taskId ID da tarefa
     * @return Result com o número de steps ou erro
     */
    suspend fun getStepCountByTask(taskId: Long): Result<Int>
}
