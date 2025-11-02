package com.pequenospassos.domain.repository

import com.pequenospassos.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * Interface de repositório para operações de AppSettings.
 *
 * Arquivo: domain/repository/AppSettingsRepository.kt
 * Tipo: Repository Interface
 * Objetivo: Definir contrato para acesso a dados de configurações do app
 * Correlações: AppSettings.kt, AppSettingsRepositoryImpl.kt, AppSettingsDao.kt
 *
 * Histórico de alterações:
 * - 2025-11-01 (Claude Sonnet 4.5): MVP-14 Fase 1 - Adicionados métodos para enableVoiceResponse
 *   - updateEnableVoiceResponse() para atualizar preferência
 *   - getEnableVoiceResponse() para observar mudanças
 * - 2025-10-31 (MVP-08): Adicionado método updateAskExtraTimeAtStep()
 * - 2025-10-14 (MVP-04): Criação inicial da interface
 *
 * Última atualização: 2025-11-01 (MVP-14 Fase 1)
 * Status: Em desenvolvimento - MVP-14
 *
 * @since MVP-04 (14/10/2025)
 * @author PequenosPassos Development Team
 * @validationStatus 🔄 Em desenvolvimento - MVP-14 Fase 1
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

    /**
     * Atualiza a preferência de perguntar tempo extra ao final do step.
     * @param askExtraTimeAtStep Novo valor da preferência
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateAskExtraTimeAtStep(askExtraTimeAtStep: Boolean): Result<Unit>

    /**
     * Atualiza a preferência de habilitar reconhecimento de voz no pop-up de tempo extra.
     * MVP-14 Fase 1.
     *
     * @param enableVoiceResponse Novo valor da preferência (true = habilitado, false = desabilitado)
     * @return Result indicando sucesso ou erro
     */
    suspend fun updateEnableVoiceResponse(enableVoiceResponse: Boolean): Result<Unit>

    /**
     * Retorna Flow observável da preferência enableVoiceResponse.
     * MVP-14 Fase 1.
     *
     * @return Flow com o valor atual de enableVoiceResponse
     */
    fun getEnableVoiceResponse(): Flow<Boolean>
}
