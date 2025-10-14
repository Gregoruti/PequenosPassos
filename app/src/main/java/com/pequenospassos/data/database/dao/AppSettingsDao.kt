package com.pequenospassos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pequenospassos.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operações de AppSettings.
 *
 * Interface de acesso a dados para configurações do aplicativo.
 * Single-instance (sempre ID "settings").
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
 */
@Dao
interface AppSettingsDao {

    /**
     * Busca as configurações do app.
     * Retorna Flow para observação reativa.
     *
     * @param id ID das configurações (padrão: "settings")
     * @return Flow com as configurações ou null se não existir
     */
    @Query("SELECT * FROM app_settings WHERE id = :id")
    fun getSettings(id: String = "settings"): Flow<AppSettings?>

    /**
     * Insere ou atualiza as configurações.
     * REPLACE substitui se já existir.
     *
     * @param settings Configurações a serem salvas
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: AppSettings)

    /**
     * Atualiza apenas o total de estrelas acumuladas.
     * Query otimizada.
     *
     * @param totalStars Novo total de estrelas
     * @param id ID das configurações (padrão: "settings")
     */
    @Query("UPDATE app_settings SET totalStars = :totalStars WHERE id = :id")
    suspend fun updateTotalStars(totalStars: Int, id: String = "settings")

    /**
     * Atualiza apenas a data atual.
     * Útil para controle de novo dia.
     *
     * @param currentDate Data no formato YYYY-MM-DD
     * @param id ID das configurações (padrão: "settings")
     */
    @Query("UPDATE app_settings SET currentDate = :currentDate WHERE id = :id")
    suspend fun updateCurrentDate(currentDate: String, id: String = "settings")

    /**
     * Marca que o app não é mais primeira execução.
     *
     * @param id ID das configurações (padrão: "settings")
     */
    @Query("UPDATE app_settings SET isFirstRun = 0 WHERE id = :id")
    suspend fun markFirstRunComplete(id: String = "settings")

    /**
     * Deleta todas as configurações (útil para reset/testes).
     */
    @Query("DELETE FROM app_settings")
    suspend fun deleteAll()
}

