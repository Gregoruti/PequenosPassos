package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.repository.StepRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para atualizar o status de conclusão de um step.
 *
 * Marca um step como completo ou incompleto.
 * Permite desfazer a conclusão de steps.
 *
 * @property stepRepository Repositório de steps
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class UpdateStepCompletionUseCase @Inject constructor(
    private val stepRepository: StepRepository
) {
    /**
     * Atualiza o status de conclusão de um step.
     *
     * @param stepId ID do step a atualizar
     * @param isCompleted Novo status de conclusão
     * @return AppResult indicando sucesso ou erro
     */
    suspend operator fun invoke(
        stepId: Long,
        isCompleted: Boolean
    ): AppResult<Unit> {
        return try {
            stepRepository.updateStepCompletion(stepId, isCompleted).fold(
                onSuccess = { AppResult.Success(Unit) },
                onFailure = { AppResult.Error(it) }
            )
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}

