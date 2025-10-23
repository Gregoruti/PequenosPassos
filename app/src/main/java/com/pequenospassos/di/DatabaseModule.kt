package com.pequenospassos.di

import android.content.Context
import androidx.room.Room
import com.pequenospassos.data.database.AppDatabase
import com.pequenospassos.data.database.dao.AchievementDao
import com.pequenospassos.data.database.dao.AppSettingsDao
import com.pequenospassos.data.database.dao.ChildProfileDao
import com.pequenospassos.data.database.dao.RewardDao
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
 * - Todos os DAOs (ChildProfileDao, TaskDao, StepDao, AppSettingsDao, AchievementDao, RewardDao)
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @updated MVP-08 (23/10/2025) - Adicionados novos DAOs para gamificação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03, MVP-08
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Fornece a instância singleton do AppDatabase.
     *
     * @param context Contexto da aplicação
     * @return Instância do AppDatabase
     * @since MVP-03
     * @updated MVP-07 - Adicionada MIGRATION_1_2
     * @updated MVP-08 - Adicionada MIGRATION_2_3
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
            .addMigrations(AppDatabase.MIGRATION_1_2) // MVP-07: Migration 1→2
            .addMigrations(AppDatabase.MIGRATION_2_3) // MVP-08: Migration 2→3
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

    /**
     * Fornece o DAO de Achievement (MVP-08).
     *
     * @param database Instância do AppDatabase
     * @return AchievementDao
     */
    @Provides
    @Singleton
    fun provideAchievementDao(database: AppDatabase): AchievementDao {
        return database.achievementDao()
    }

    /**
     * Fornece o DAO de Reward (MVP-08).
     *
     * @param database Instância do AppDatabase
     * @return RewardDao
     */
    @Provides
    @Singleton
    fun provideRewardDao(database: AppDatabase): RewardDao {
        return database.rewardDao()
    }
}
