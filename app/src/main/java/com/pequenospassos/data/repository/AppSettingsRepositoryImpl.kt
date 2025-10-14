package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.AppSettingsDao
import com.pequenospassos.domain.model.AppSettings
import com.pequenospassos.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementação do repositório de AppSettings.
 *
 * Fornece acesso aos dados de configurações do app através do DAO,
 * usando single-instance pattern (ID fixo "settings").
 *
 * @property dao DAO de AppSettings injetado via Hilt
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
@Singleton
class AppSettingsRepositoryImpl @Inject constructor(
    private val dao: AppSettingsDao
) : AppSettingsRepository {

    companion object {
        private const val SETTINGS_ID = "settings"
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }

    override fun getSettings(): Flow<AppSettings?> {
        return dao.getSettings(SETTINGS_ID)
    }

    override suspend fun updateSettings(settings: AppSettings): Result<Unit> {
        return try {
            dao.updateSettings(settings)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isFirstRun(): Boolean {
        return try {
            val settings = dao.getSettings(SETTINGS_ID).first()
            settings?.isFirstRun ?: true
        } catch (e: Exception) {
            true // Assume primeira execução em caso de erro
        }
    }

    override suspend fun markFirstRunCompleted(): Result<Unit> {
        return try {
            dao.markFirstRunComplete(SETTINGS_ID)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTotalStars(totalStars: Int): Result<Unit> {
        return try {
            dao.updateTotalStars(totalStars, SETTINGS_ID)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCurrentDate(currentDate: String): Result<Unit> {
        return try {
            dao.updateCurrentDate(currentDate, SETTINGS_ID)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDefaultSettings(): AppSettings {
        val currentDate = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            .format(Date())

        return AppSettings(
            id = SETTINGS_ID,
            isFirstRun = true,
            totalStars = 0,
            currentDate = currentDate,
            lastSyncTimestamp = System.currentTimeMillis(),
            notificationsEnabled = true
        )
    }
}

