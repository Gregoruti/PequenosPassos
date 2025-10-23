package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.Reward
import com.pequenospassos.domain.model.RewardType
import kotlinx.coroutines.flow.Flow

/**
 * Interface do Repository para operações de Reward (Recompensas).
 * Define o contrato para acesso aos dados de recompensas desbloqueáveis.
 *
 * MVP08 - Sistema de Gamificação
 */
interface RewardRepository {

    /**
     * Salva uma nova recompensa.
     */
    suspend fun saveReward(reward: Reward): Long

    /**
     * Atualiza uma recompensa existente.
     */
    suspend fun updateReward(reward: Reward)

    /**
     * Deleta uma recompensa.
     */
    suspend fun deleteReward(reward: Reward)

    /**
     * Busca recompensa por ID.
     */
    suspend fun getRewardById(rewardId: Long): Reward?

    /**
     * Observa todas as recompensas de uma criança.
     */
    fun observeRewardsByChildId(childId: Long): Flow<List<Reward>>

    /**
     * Busca todas as recompensas de uma criança.
     */
    suspend fun getRewardsByChildId(childId: Long): List<Reward>

    /**
     * Busca recompensas desbloqueadas.
     */
    suspend fun getUnlockedRewards(childId: Long): List<Reward>

    /**
     * Busca recompensas bloqueadas.
     */
    suspend fun getLockedRewards(childId: Long): List<Reward>

    /**
     * Busca recompensas por tipo.
     */
    suspend fun getRewardsByType(childId: Long, type: RewardType): List<Reward>

    /**
     * Busca a recompensa ativa de um tipo.
     */
    suspend fun getActiveReward(childId: Long, type: RewardType): Reward?

    /**
     * Conta recompensas desbloqueadas.
     */
    suspend fun countUnlockedRewards(childId: Long): Int

    /**
     * Busca recompensas que podem ser desbloqueadas com as estrelas atuais.
     */
    suspend fun getUnlockableRewards(childId: Long, totalStars: Int): List<Reward>

    /**
     * Desativa todas as recompensas de um tipo.
     */
    suspend fun deactivateAllOfType(childId: Long, type: RewardType)

    /**
     * Desbloqueia uma recompensa.
     */
    suspend fun unlockReward(rewardId: Long)

    /**
     * Ativa uma recompensa (desativa outras do mesmo tipo).
     */
    suspend fun activateReward(rewardId: Long)
}

