package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.RewardDao
import com.pequenospassos.domain.model.Reward
import com.pequenospassos.domain.model.RewardType
import com.pequenospassos.domain.repository.RewardRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import androidx.annotation.RequiresApi
import android.os.Build

/**
 * Implementação do Repository para operações de Reward.
 * Conecta a camada de dados (DAO) com a camada de domínio.
 *
 * MVP08 - Sistema de Gamificação
 *
 * @param rewardDao DAO injetado pelo Hilt
 */
class RewardRepositoryImpl @Inject constructor(
    private val rewardDao: RewardDao
) : RewardRepository {

    override suspend fun saveReward(reward: Reward): Long {
        return rewardDao.insert(reward)
    }

    override suspend fun updateReward(reward: Reward) {
        rewardDao.update(reward)
    }

    override suspend fun deleteReward(reward: Reward) {
        rewardDao.delete(reward)
    }

    override suspend fun getRewardById(rewardId: Long): Reward? {
        return rewardDao.getById(rewardId)
    }

    override fun observeRewardsByChildId(childId: Long): Flow<List<Reward>> {
        return rewardDao.observeByChildId(childId)
    }

    override suspend fun getRewardsByChildId(childId: Long): List<Reward> {
        return rewardDao.getByChildId(childId)
    }

    override suspend fun getUnlockedRewards(childId: Long): List<Reward> {
        return rewardDao.getUnlockedRewards(childId)
    }

    override suspend fun getLockedRewards(childId: Long): List<Reward> {
        return rewardDao.getLockedRewards(childId)
    }

    override suspend fun getRewardsByType(childId: Long, type: RewardType): List<Reward> {
        return rewardDao.getByType(childId, type)
    }

    override suspend fun getActiveReward(childId: Long, type: RewardType): Reward? {
        return rewardDao.getActiveReward(childId, type)
    }

    override suspend fun countUnlockedRewards(childId: Long): Int {
        return rewardDao.countUnlockedRewards(childId)
    }

    override suspend fun getUnlockableRewards(childId: Long, totalStars: Int): List<Reward> {
        return rewardDao.getUnlockableRewards(childId, totalStars)
    }

    override suspend fun deactivateAllOfType(childId: Long, type: RewardType) {
        rewardDao.deactivateAllOfType(childId, type)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun unlockReward(rewardId: Long) {
        val reward = rewardDao.getById(rewardId)
        reward?.let {
            val unlockedReward = it.copy(
                isUnlocked = true,
                unlockedAt = LocalDateTime.now()
            )
            rewardDao.update(unlockedReward)
        }
    }

    override suspend fun activateReward(rewardId: Long) {
        val reward = rewardDao.getById(rewardId)
        reward?.let {
            // Desativa todas as recompensas do mesmo tipo
            rewardDao.deactivateAllOfType(it.childId, it.rewardType)

            // Ativa a recompensa selecionada
            val activatedReward = it.copy(isActive = true)
            rewardDao.update(activatedReward)
        }
    }
}

