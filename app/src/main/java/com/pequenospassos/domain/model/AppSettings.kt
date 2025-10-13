package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade de configurações do aplicativo.
 *
 * Armazena configurações globais e estado da aplicação.
 * Single-instance (sempre ID "settings").
 *
 * @property id Identificador fixo ("settings")
 * @property isFirstRun Se é a primeira execução do app
 * @property totalStars Total de estrelas acumuladas pela criança
 * @property currentDate Data atual no formato YYYY-MM-DD
 * @property lastSyncTimestamp Timestamp da última sincronização
 * @property notificationsEnabled Se notificações estão habilitadas
 *
 * @since MVP-02 (13/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-02
 */
@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey
    val id: String = "settings",

    val isFirstRun: Boolean = true,

    val totalStars: Int = 0,

    val currentDate: String = "",

    val lastSyncTimestamp: Long = System.currentTimeMillis(),

    val notificationsEnabled: Boolean = true
) {
    /**
     * Valida se as configurações estão consistentes
     */
    fun isValid(): Boolean {
        return totalStars >= 0 && id == "settings"
    }

    /**
     * Retorna se a data atual mudou (novo dia)
     */
    fun isNewDay(today: String): Boolean {
        return currentDate != today
    }

    companion object {
        /**
         * Retorna as configurações padrão para primeira execução
         */
        fun getDefault(): AppSettings {
            return AppSettings(
                id = "settings",
                isFirstRun = true,
                totalStars = 0,
                currentDate = getCurrentDateString(),
                lastSyncTimestamp = System.currentTimeMillis(),
                notificationsEnabled = true
            )
        }

        /**
         * Retorna a data atual no formato YYYY-MM-DD
         */
        private fun getCurrentDateString(): String {
            val calendar = java.util.Calendar.getInstance()
            return String.format(
                "%04d-%02d-%02d",
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH) + 1,
                calendar.get(java.util.Calendar.DAY_OF_MONTH)
            )
        }
    }
}

