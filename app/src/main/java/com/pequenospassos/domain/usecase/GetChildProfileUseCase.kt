package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.repository.ChildProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para obter o perfil da criança.
 *
 * Retorna um Flow reativo com o perfil da criança,
 * permitindo observação de mudanças em tempo real.
 *
 * @property repository Repositório de perfil da criança
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class GetChildProfileUseCase @Inject constructor(
    private val repository: ChildProfileRepository
) {
    /**
     * Obtém o perfil da criança de forma reativa.
     *
     * @return Flow com o perfil ou null se não existir
     */
    operator fun invoke(): Flow<ChildProfile?> = repository.getProfile()
}

