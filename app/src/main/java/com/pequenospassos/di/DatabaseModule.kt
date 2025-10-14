package com.pequenospassos.di

import android.content.Context
import androidx.room.Room
import com.pequenospassos.data.database.AppDatabase
import com.pequenospassos.data.database.dao.AppSettingsDao
import com.pequenospassos.data.database.dao.ChildProfileDao
import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.data.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para injeção de dependência do Database.
 *
 * Fornece instâncias singleton de:
 * - AppDatabase
 * - Todos os DAOs (ChildProfileDao, TaskDao, StepDao, AppSettingsDao)
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Fornece a instância singleton do AppDatabase.
     *
     * @param context Contexto da aplicação
     * @return Instância do AppDatabase
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(dropAllTables = true) // MVP: aceita perda de dados em mudanças de schema
            .build()
    }

    /**
     * Fornece o DAO de ChildProfile.
     *
     * @param database Instância do AppDatabase
     * @return ChildProfileDao
     */
    @Provides
    @Singleton
    fun provideChildProfileDao(database: AppDatabase): ChildProfileDao {
        return database.childProfileDao()
    }

    /**
     * Fornece o DAO de Task.
     *
     * @param database Instância do AppDatabase
     * @return TaskDao
     */
    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    /**
     * Fornece o DAO de Step.
     *
     * @param database Instância do AppDatabase
     * @return StepDao
     */
    @Provides
    @Singleton
    fun provideStepDao(database: AppDatabase): StepDao {
        return database.stepDao()
    }

    /**
     * Fornece o DAO de AppSettings.
     *
     * @param database Instância do AppDatabase
     * @return AppSettingsDao
     */
    @Provides
    @Singleton
    fun provideAppSettingsDao(database: AppDatabase): AppSettingsDao {
        return database.appSettingsDao()
    }
}
