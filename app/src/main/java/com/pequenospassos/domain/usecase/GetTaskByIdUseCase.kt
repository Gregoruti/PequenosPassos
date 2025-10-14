package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para obter uma tarefa por ID.
 *
 * Retorna um Flow reativo com a tarefa específica,
 * permitindo observação de mudanças em tempo real.
 *
 * @property repository Repositório de tarefas
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class GetTaskByIdUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    /**
     * Obtém uma tarefa por ID de forma reativa.
     *
     * @param taskId ID da tarefa
     * @return Flow com a tarefa ou null se não existir
     */
    operator fun invoke(taskId: Long): Flow<Task?> {
        return repository.getTaskById(taskId)
    }
}

