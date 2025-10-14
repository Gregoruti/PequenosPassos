package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.ChildProfile
import kotlinx.coroutines.flow.Flow

/**
 * Interface de repositório para operações de ChildProfile.
 *
 * Define o contrato para acesso a dados de perfil da criança,
 * seguindo o Repository Pattern e Clean Architecture.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
interface ChildProfileRepository {

    /**
     * Observa o perfil da criança de forma reativa.
     *
     * @return Flow com o perfil ou null se não existir
     */
    fun getProfile(): Flow<ChildProfile?>

    /**
     * Salva ou atualiza o perfil da criança.
     *
     * @param profile Perfil a ser salvo
     * @return Result indicando sucesso ou erro
     */
    suspend fun saveProfile(profile: ChildProfile): Result<Unit>

    /**
     * Verifica se já existe um perfil cadastrado.
     *
     * @return true se existe perfil, false caso contrário
     */
    suspend fun hasProfile(): Boolean

    /**
     * Deleta todos os perfis (útil para reset).
     *
     * @return Result indicando sucesso ou erro
     */
    suspend fun deleteAllProfiles(): Result<Unit>
}

