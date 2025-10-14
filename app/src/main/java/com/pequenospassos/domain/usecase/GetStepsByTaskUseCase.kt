package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.repository.StepRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para obter os steps de uma tarefa específica.
 *
 * Retorna um Flow reativo com a lista de steps ordenados,
 * permitindo observação de mudanças em tempo real.
 *
 * @property repository Repositório de steps
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class GetStepsByTaskUseCase @Inject constructor(
    private val repository: StepRepository
) {
    /**
     * Obtém os steps de uma tarefa de forma reativa.
     *
     * @param taskId ID da tarefa
     * @return Flow com lista de steps ordenados
     */
    operator fun invoke(taskId: Long): Flow<List<Step>> {
        return repository.getStepsByTask(taskId)
    }
}

