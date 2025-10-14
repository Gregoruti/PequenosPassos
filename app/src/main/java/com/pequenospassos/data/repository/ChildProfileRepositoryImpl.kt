package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.ChildProfileDao
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.repository.ChildProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementação do repositório de ChildProfile.
 *
 * Fornece acesso aos dados de perfil da criança através do DAO,
 * com tratamento de erros usando Result.
 *
 * @property dao DAO de ChildProfile injetado via Hilt
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
@Singleton
class ChildProfileRepositoryImpl @Inject constructor(
    private val dao: ChildProfileDao
) : ChildProfileRepository {

    override fun getProfile(): Flow<ChildProfile?> {
        return dao.getProfile()
    }

    override suspend fun saveProfile(profile: ChildProfile): Result<Unit> {
        return try {
            dao.insertOrUpdate(profile)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasProfile(): Boolean {
        return try {
            dao.getProfileCount() > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteAllProfiles(): Result<Unit> {
        return try {
            dao.deleteAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

