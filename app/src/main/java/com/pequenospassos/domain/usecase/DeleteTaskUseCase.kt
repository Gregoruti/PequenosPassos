package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.repository.TaskRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para deletar uma tarefa.
 *
 * Remove a tarefa e todos os seus steps associados do banco de dados.
 * A exclusão dos steps é feita automaticamente via CASCADE no banco.
 *
 * @property taskRepository Repositório de tarefas
 *
 * @since MVP-07 (20/10/2025) - Fase 3 - Exclusão de Atividades
 * @author PequenosPassos Development Team
 */
@Singleton
class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    /**
     * Deleta uma tarefa pelo ID.
     *
     * @param taskId ID da tarefa a ser deletada
     * @return AppResult<Unit> com sucesso ou erro
     */
    suspend operator fun invoke(taskId: Long): AppResult<Unit> {
        return try {
            // Buscar a tarefa primeiro
            val task = taskRepository.getTaskById(taskId).firstOrNull()

            if (task == null) {
                return AppResult.Error(IllegalArgumentException("Tarefa não encontrada"))
            }

            // Deletar a tarefa
            taskRepository.deleteTask(task)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}

