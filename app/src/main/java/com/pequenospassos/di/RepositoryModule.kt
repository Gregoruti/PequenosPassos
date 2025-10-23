package com.pequenospassos.di

import com.pequenospassos.data.repository.AchievementRepositoryImpl
import com.pequenospassos.data.repository.AppSettingsRepositoryImpl
import com.pequenospassos.data.repository.ChildProfileRepositoryImpl
import com.pequenospassos.data.repository.RewardRepositoryImpl
import com.pequenospassos.data.repository.StepRepositoryImpl
import com.pequenospassos.data.repository.TaskRepositoryImpl
import com.pequenospassos.domain.repository.AchievementRepository
import com.pequenospassos.domain.repository.AppSettingsRepository
import com.pequenospassos.domain.repository.ChildProfileRepository
import com.pequenospassos.domain.repository.RewardRepository
import com.pequenospassos.domain.repository.StepRepository
import com.pequenospassos.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para injeção de dependência dos Repositories.
 *
 * @updated MVP-08 (23/10/2025) - Adicionados repositories de gamificação
 * Fornece bindings entre interfaces de repositório (domain) e
 * @validationStatus ✅ Implementado - MVP-04, MVP-08
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Fornece a implementação de ChildProfileRepository.
     *
     * @param impl Implementação concreta
     * @return Interface do repositório
     */
    @Binds
    @Singleton
    abstract fun bindChildProfileRepository(
        impl: ChildProfileRepositoryImpl
    ): ChildProfileRepository

    /**
     * Fornece a implementação de TaskRepository.
     *
     * @param impl Implementação concreta
     * @return Interface do repositório
     */
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    /**
     * Fornece a implementação de StepRepository.
     *
     * @param impl Implementação concreta
     * @return Interface do repositório
     */
    @Binds
    @Singleton
    abstract fun bindStepRepository(
        impl: StepRepositoryImpl
    ): StepRepository

    /**
     * Fornece a implementação de AppSettingsRepository.
     *
     * @param impl Implementação concreta
     * @return Interface do repositório
     */

    /**
     * Fornece a implementação de AchievementRepository (MVP-08).
     *
     * @param impl Implementação concreta
     * @return Interface do repositório
     */
    @Binds
    @Singleton
    abstract fun bindAchievementRepository(
        impl: AchievementRepositoryImpl
    ): AchievementRepository

    /**
     * Fornece a implementação de RewardRepository (MVP-08).
     *
     * @param impl Implementação concreta
     * @return Interface do repositório
     */
    @Binds
    @Singleton
    abstract fun bindRewardRepository(
        impl: RewardRepositoryImpl
    ): RewardRepository
    @Binds
    @Singleton
    abstract fun bindAppSettingsRepository(
        impl: AppSettingsRepositoryImpl
    ): AppSettingsRepository
}

