package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.repository.ChildProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para salvar perfil da criança com validações.
 *
 * Valida os dados de entrada e salva o perfil no repositório.
 * Regras de negócio:
 * - Nome é obrigatório
 * - Nome deve ter pelo menos 2 caracteres
 * - Gender é obrigatório
 * - Photo URI é opcional
 *
 * @property repository Repositório de perfil da criança
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class SaveChildProfileUseCase @Inject constructor(
    private val repository: ChildProfileRepository
) {
    /**
     * Salva o perfil da criança após validações.
     *
     * @param name Nome da criança
     * @param gender Gênero da criança
     * @param photoUri URI da foto (opcional)
     * @return AppResult indicando sucesso ou erro com mensagem
     */
    suspend operator fun invoke(
        name: String,
        gender: Gender,
        photoUri: String? = null
    ): AppResult<Unit> {
        // Validação: nome é obrigatório
        if (name.isBlank()) {
            return AppResult.Error(
                IllegalArgumentException("Nome é obrigatório")
            )
        }

        // Validação: nome mínimo de 2 caracteres
        if (name.length < 2) {
            return AppResult.Error(
                IllegalArgumentException("Nome deve ter pelo menos 2 caracteres")
            )
        }

        val profile = ChildProfile(
            name = name.trim(),
            gender = gender,
            photoUri = photoUri
        )

        return try {
            repository.saveProfile(profile).fold(
                onSuccess = { AppResult.Success(Unit) },
                onFailure = { AppResult.Error(it) }
            )
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}

