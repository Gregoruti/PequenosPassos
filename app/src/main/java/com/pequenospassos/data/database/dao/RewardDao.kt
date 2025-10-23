package com.pequenospassos.data.database.dao

import androidx.room.*
import com.pequenospassos.domain.model.Reward
import com.pequenospassos.domain.model.RewardType
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operações de banco de dados relacionadas a Rewards (recompensas).
 *
 * MVP08 - Sistema de Gamificação
 */
@Dao
interface RewardDao {

    /**
     * Insere uma nova recompensa no banco de dados.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reward: Reward): Long

    /**
     * Atualiza uma recompensa existente.
     */
    @Update
    suspend fun update(reward: Reward)

    /**
     * Deleta uma recompensa.
     */
    @Delete
    suspend fun delete(reward: Reward)

    /**
     * Busca uma recompensa por ID.
     */
    @Query("SELECT * FROM rewards WHERE id = :rewardId")
    suspend fun getById(rewardId: Long): Reward?

    /**
     * Observa todas as recompensas de uma criança.
     */
    @Query("SELECT * FROM rewards WHERE childId = :childId ORDER BY starsRequired ASC")
    fun observeByChildId(childId: Long): Flow<List<Reward>>

    /**
     * Busca todas as recompensas de uma criança.
     */
    @Query("SELECT * FROM rewards WHERE childId = :childId ORDER BY starsRequired ASC")
    suspend fun getByChildId(childId: Long): List<Reward>

    /**
     * Busca recompensas desbloqueadas de uma criança.
     */
    @Query("SELECT * FROM rewards WHERE childId = :childId AND isUnlocked = 1 ORDER BY unlockedAt DESC")
    suspend fun getUnlockedRewards(childId: Long): List<Reward>

    /**
     * Busca recompensas bloqueadas de uma criança.
     */
    @Query("SELECT * FROM rewards WHERE childId = :childId AND isUnlocked = 0 ORDER BY starsRequired ASC")
    suspend fun getLockedRewards(childId: Long): List<Reward>

    /**
     * Busca recompensas por tipo.
     */
    @Query("SELECT * FROM rewards WHERE childId = :childId AND rewardType = :type ORDER BY starsRequired ASC")
    suspend fun getByType(childId: Long, type: RewardType): List<Reward>

    /**
     * Busca a recompensa atualmente ativa de um tipo específico.
     */
    @Query("SELECT * FROM rewards WHERE childId = :childId AND rewardType = :type AND isActive = 1 LIMIT 1")
    suspend fun getActiveReward(childId: Long, type: RewardType): Reward?

    /**
     * Conta o total de recompensas desbloqueadas.
     */
    @Query("SELECT COUNT(*) FROM rewards WHERE childId = :childId AND isUnlocked = 1")
    suspend fun countUnlockedRewards(childId: Long): Int

    /**
     * Busca recompensas que podem ser desbloqueadas com a quantidade de estrelas atual.
     */
    @Query("""
        SELECT * FROM rewards 
        WHERE childId = :childId 
        AND isUnlocked = 0 
        AND starsRequired <= :totalStars 
        ORDER BY starsRequired ASC
    """)
    suspend fun getUnlockableRewards(childId: Long, totalStars: Int): List<Reward>

    /**
     * Desativa todas as recompensas de um tipo específico.
     */
    @Query("UPDATE rewards SET isActive = 0 WHERE childId = :childId AND rewardType = :type")
    suspend fun deactivateAllOfType(childId: Long, type: RewardType)

    /**
     * Deleta todas as recompensas de uma criança.
     */
    @Query("DELETE FROM rewards WHERE childId = :childId")
    suspend fun deleteByChildId(childId: Long)
}

