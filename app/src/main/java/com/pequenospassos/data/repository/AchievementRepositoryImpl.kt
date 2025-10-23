package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.AchievementDao
import com.pequenospassos.domain.model.Achievement
import com.pequenospassos.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Implementação do Repository para operações de Achievement.
 * Conecta a camada de dados (DAO) com a camada de domínio.
 *
 * MVP08 - Sistema de Gamificação
 *
 * @param achievementDao DAO injetado pelo Hilt
 */
class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao
) : AchievementRepository {

    override suspend fun saveAchievement(achievement: Achievement): Long {
        return achievementDao.insert(achievement)
    }

    override suspend fun updateAchievement(achievement: Achievement) {
        achievementDao.update(achievement)
    }

    override suspend fun deleteAchievement(achievement: Achievement) {
        achievementDao.delete(achievement)
    }

    override suspend fun getAchievementById(achievementId: Long): Achievement? {
        return achievementDao.getById(achievementId)
    }

    override fun observeAchievementsByChildId(childId: Long): Flow<List<Achievement>> {
        return achievementDao.observeByChildId(childId)
    }

    override suspend fun getAchievementsByChildId(childId: Long): List<Achievement> {
        return achievementDao.getByChildId(childId)
    }

    override suspend fun getAchievementsByTaskId(taskId: Long): List<Achievement> {
        return achievementDao.getByTaskId(taskId)
    }

    override suspend fun getTotalStars(childId: Long): Int {
        return achievementDao.getTotalStars(childId)
    }

    override suspend fun getStarsInPeriod(
        childId: Long,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Int {
        return achievementDao.getStarsInPeriod(childId, startDate, endDate)
    }

    override suspend fun getTotalTasksCompleted(childId: Long): Int {
        return achievementDao.getTotalTasksCompleted(childId)
    }

    override suspend fun getTasksCompletedInPeriod(
        childId: Long,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Int {
        return achievementDao.getTasksCompletedInPeriod(childId, startDate, endDate)
    }

    override suspend fun getAverageStars(childId: Long): Double {
        return achievementDao.getAverageStars(childId)
    }

    override suspend fun getAchievementsOnDate(
        childId: Long,
        date: LocalDateTime
    ): List<Achievement> {
        return achievementDao.getAchievementsOnDate(childId, date)
    }
}

