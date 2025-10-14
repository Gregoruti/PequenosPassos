package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.repository.AppSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para verificar se é a primeira execução do app.
 *
 * Usado para decidir se deve mostrar o onboarding ou não.
 * Verifica a flag isFirstRun nas configurações do app.
 *
 * @property repository Repositório de configurações
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class CheckFirstRunUseCase @Inject constructor(
    private val repository: AppSettingsRepository
) {
    /**
     * Verifica se é a primeira execução do app.
     *
     * @return true se é primeira execução, false caso contrário
     */
    suspend operator fun invoke(): Boolean {
        return repository.isFirstRun()
    }
}

