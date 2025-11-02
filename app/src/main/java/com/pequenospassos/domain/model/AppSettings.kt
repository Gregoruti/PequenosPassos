package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade de configurações do aplicativo.
 *
 * Arquivo: domain/model/AppSettings.kt
 * Tipo: Entity (Room Database)
 * Objetivo: Armazenar configurações globais e estado da aplicação
 * Correlações: AppSettingsDao.kt, AppSettingsRepository.kt, migrations em AppDatabase.kt
 *
 * Histórico de alterações:
 * - 2025-11-01 (Claude Sonnet 4.5): MVP-14 - Adicionado campo enableVoiceResponse
 *   - Habilita reconhecimento de voz no pop-up de tempo extra
 *   - Migration 5→6 criada
 *   - Valor padrão: false (desabilitado)
 * - 2025-11-01 (GPT-4.1): CRÍTICO - Adicionado campo askExtraTimeAtStep (estava ausente)
 *   - Migration criada mas campo faltava na data class
 *   - Causava SQLiteException ao salvar configuração
 *   - Solução: Adicionar campo com valor padrão true
 * - 2025-10-30 (Claude Sonnet): Adicionado suporte a controle de pop-up de tempo extra
 * - 2025-10-13 (MVP-02): Criação inicial da entidade
 *
 * Última atualização: 2025-11-01 (MVP-14 Fase 1)
 * Status: Em desenvolvimento - MVP-14
 * Build: PENDING
 *
 * ⚠️ ATENÇÃO: Ao adicionar novos campos via migration, SEMPRE adicionar na data class também!
 *
 * @property id Identificador fixo ("settings")
 * @property isFirstRun Se é a primeira execução do app
 * @property totalStars Total de estrelas acumuladas pela criança
 * @property currentDate Data atual no formato YYYY-MM-DD
 * @property lastSyncTimestamp Timestamp da última sincronização
 * @property notificationsEnabled Se notificações estão habilitadas
 * @property askExtraTimeAtStep Se deve perguntar por mais tempo ao final do step (v2.1.0)
 * @property enableVoiceResponse Se habilita reconhecimento de voz no pop-up de tempo extra (v2.2.0 - MVP-14)
 *
 * @since MVP-02 (13/10/2025)
 * @author PequenosPassos Development Team
 * @validationStatus 🔄 Em desenvolvimento - MVP-14 Fase 1
 */
@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey
    val id: String = "settings",

    val isFirstRun: Boolean = true,

    val totalStars: Int = 0,

    val currentDate: String = "",

    val lastSyncTimestamp: Long = System.currentTimeMillis(),

    val notificationsEnabled: Boolean = true,

    // Adicionado em MVP-11 (v2.1.0): controla se pergunta por tempo extra ao final do step
    val askExtraTimeAtStep: Boolean = true,

    // Adicionado em MVP-14 (v2.2.0): habilita reconhecimento de voz no pop-up de tempo extra
    val enableVoiceResponse: Boolean = false
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
