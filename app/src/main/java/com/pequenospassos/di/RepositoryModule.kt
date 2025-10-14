package com.pequenospassos.di

import com.pequenospassos.data.repository.AppSettingsRepositoryImpl
import com.pequenospassos.data.repository.ChildProfileRepositoryImpl
import com.pequenospassos.data.repository.StepRepositoryImpl
import com.pequenospassos.data.repository.TaskRepositoryImpl
import com.pequenospassos.domain.repository.AppSettingsRepository
import com.pequenospassos.domain.repository.ChildProfileRepository
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
 * Fornece bindings entre interfaces de repositório (domain) e
 * suas implementações (data), seguindo Clean Architecture.
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
    @Binds
    @Singleton
    abstract fun bindAppSettingsRepository(
        impl: AppSettingsRepositoryImpl
    ): AppSettingsRepository
}

