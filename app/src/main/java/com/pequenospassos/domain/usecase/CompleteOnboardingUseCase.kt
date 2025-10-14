package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.repository.AppSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para marcar o onboarding como concluído.
 *
 * Atualiza a flag isFirstRun para false nas configurações,
 * indicando que o usuário já completou o cadastro inicial.
 *
 * @property repository Repositório de configurações
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class CompleteOnboardingUseCase @Inject constructor(
    private val repository: AppSettingsRepository
) {
    /**
     * Marca o onboarding como concluído.
     *
     * @return AppResult indicando sucesso ou erro
     */
    suspend operator fun invoke(): AppResult<Unit> {
        return repository.markFirstRunCompleted().fold(
            onSuccess = { AppResult.Success(Unit) },
            onFailure = { AppResult.Error(it) }
        )
    }
}
