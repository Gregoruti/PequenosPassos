package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para atualizar o status de uma tarefa.
 *
 * Atualiza o status da tarefa (PENDING, COMPLETED, CANCELED)
 * e futuramente poderá adicionar estrelas ao completar.
 *
 * Regras de negócio:
 * - Tarefa deve existir
 * - Status deve ser válido
 * - Ao completar, adiciona estrelas (implementação futura)
 *
 * @property taskRepository Repositório de tarefas
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class UpdateTaskStatusUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    /**
     * Atualiza o status de uma tarefa.
     *
     * @param taskId ID da tarefa a atualizar
     * @param status Novo status (PENDING, COMPLETED, CANCELED)
     * @return AppResult indicando sucesso ou erro
     */
    suspend operator fun invoke(
        taskId: Long,
        status: TaskStatus
    ): AppResult<Unit> {
        return try {
            taskRepository.updateTaskStatus(taskId, status).fold(
                onSuccess = {
                    // Futura implementação: adicionar estrelas se COMPLETED
                    // if (status == TaskStatus.COMPLETED) {
                    //     settingsRepository.updateTotalStars(...)
                    // }
                    AppResult.Success(Unit)
                },
                onFailure = { AppResult.Error(it) }
            )
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}

