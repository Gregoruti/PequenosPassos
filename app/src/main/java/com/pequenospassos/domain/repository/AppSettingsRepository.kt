package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * Interface de repositório para operações de AppSettings.
 *
 * Define o contrato para acesso a dados de configurações do app,
 * usando single-instance pattern (sempre ID "settings").
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
interface AppSettingsRepository {

    /**
     * Observa as configurações do app de forma reativa.
     *
     * @return Flow com as configurações ou null se não existir
     */
    fun getSettings(): Flow<AppSettings?>

    /**
     * Salva ou atualiza as configurações.
     *
     * @param settings Configurações a serem salvas
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateSettings(settings: AppSettings): Result<Unit>

    /**
     * Verifica se é a primeira execução do app.
     *
     * @return true se é primeira execução, false caso contrário
     */
    suspend fun isFirstRun(): Boolean

    /**
     * Marca que a primeira execução foi completada.
     *
     * @return Result indicando sucesso ou erro
     */
    suspend fun markFirstRunCompleted(): Result<Unit>

    /**
     * Atualiza o total de estrelas acumuladas.
     *
     * @param totalStars Novo total de estrelas
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateTotalStars(totalStars: Int): Result<Unit>

    /**
     * Atualiza a data atual.
     * Útil para controle de novo dia.
     *
     * @param currentDate Data no formato YYYY-MM-DD
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateCurrentDate(currentDate: String): Result<Unit>

    /**
     * Retorna as configurações padrão para primeira execução.
     *
     * @return AppSettings com valores padrão
     */
    suspend fun getDefaultSettings(): AppSettings
}

