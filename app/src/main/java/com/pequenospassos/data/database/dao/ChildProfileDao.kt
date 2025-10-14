package com.pequenospassos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pequenospassos.domain.model.ChildProfile
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operações de ChildProfile.
 *
 * Interface de acesso a dados para perfil da criança.
 * MVP usa single-user (apenas um perfil com ID "default_child").
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
 */
@Dao
interface ChildProfileDao {

    /**
     * Busca o perfil da criança por ID.
     * Retorna Flow para observação reativa de mudanças.
     *
     * @param id ID do perfil (padrão: "default_child")
     * @return Flow com o perfil ou null se não existir
     */
    @Query("SELECT * FROM child_profile WHERE id = :id")
    fun getProfile(id: String = "default_child"): Flow<ChildProfile?>

    /**
     * Insere ou atualiza o perfil da criança.
     * REPLACE substitui se já existir.
     *
     * @param profile Perfil a ser salvo
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: ChildProfile)

    /**
     * Conta quantos perfis existem no banco.
     * Útil para verificar se é primeira execução.
     *
     * @return Quantidade de perfis
     */
    @Query("SELECT COUNT(*) FROM child_profile")
    suspend fun getProfileCount(): Int

    /**
     * Deleta todos os perfis (útil para reset/testes).
     */
    @Query("DELETE FROM child_profile")
    suspend fun deleteAll()
}

