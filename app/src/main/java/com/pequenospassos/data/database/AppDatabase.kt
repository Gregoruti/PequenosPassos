package com.pequenospassos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pequenospassos.data.database.dao.AppSettingsDao
import com.pequenospassos.data.database.dao.ChildProfileDao
import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.domain.model.AppSettings
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task

/**
 * Database principal do aplicativo PequenosPassos.
 *
 * Room Database com 4 entidades essenciais para MVP:
 * - ChildProfile: Perfil da criança
 * - Task: Tarefas diárias
 * - Step: Passos/subtarefas
 * - AppSettings: Configurações do app
 *
 * Versão 1 - MVP-03
 * - Entidades básicas implementadas
 * - TypeConverters para enums (Gender, TaskStatus)
 * - Relacionamentos 1:N (Task -> Steps) com cascade
 * - DAOs com Flow para reatividade
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
 */
@Database(
    entities = [
        ChildProfile::class,
        Task::class,
        Step::class,
        AppSettings::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * DAO para operações de perfil da criança
     */
    abstract fun childProfileDao(): ChildProfileDao

    /**
     * DAO para operações de tarefas
     */
    abstract fun taskDao(): TaskDao

    /**
     * DAO para operações de steps
     */
    abstract fun stepDao(): StepDao

    /**
     * DAO para operações de configurações
     */
    abstract fun appSettingsDao(): AppSettingsDao

    companion object {
        const val DATABASE_NAME = "pequenospassos_database"
    }
}

