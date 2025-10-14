package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para obter tarefas ordenadas por horário.
 *
 * Retorna todas as tarefas do dia ordenadas automaticamente
 * pelo campo time (HH:mm), facilitando a visualização sequencial.
 *
 * @property repository Repositório de tarefas
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class GetTasksOrderedByTimeUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    /**
     * Obtém todas as tarefas ordenadas por horário.
     *
     * @return Flow com lista de tarefas ordenadas por time
     */
    operator fun invoke(): Flow<List<Task>> = repository.getAllTasksOrderedByTime()
}

