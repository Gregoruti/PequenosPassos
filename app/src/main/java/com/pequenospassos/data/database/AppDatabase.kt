package com.pequenospassos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
 * Versão 1 - MVP-03:
 * - Entidades básicas implementadas
 * - TypeConverters para enums (Gender, TaskStatus)
 * - Relacionamentos 1:N (Task -> Steps) com cascade
 * - DAOs com Flow para reatividade
 *
 * Versão 2 - MVP-07:
 * - Adicionado campo 'category' (TEXT, NOT NULL) em tasks
 * - Adicionado campo 'imageUrl' (TEXT, NULLABLE) em tasks
 * - Adicionado campo 'imageUrl' (TEXT, NULLABLE) em steps
 * - Adicionado campo 'durationSeconds' (INTEGER, DEFAULT 60) em steps
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @updated MVP-07 (16/10/2025) - Migration 1→2
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03, ⏳ Atualizado - MVP-07
 */
@Database(
    entities = [
        ChildProfile::class,
        Task::class,
        Step::class,
        AppSettings::class
    ],
    version = 2, // MVP-07: Incrementado de 1 para 2
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

        /**
         * Migration da versão 1 para 2 (MVP-07).
         *
         * Adiciona suporte a:
         * - Categorias nas tarefas (27 categorias organizadas)
         * - Imagem principal da tarefa (aparece no card da lista)
         * - Imagem por step (aparece durante execução)
         * - Timer configurável por step (15-600 segundos)
         *
         * @since MVP-07 (16/10/2025)
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adicionar coluna 'category' na tabela tasks
                // Valor padrão temporário "OUTRO" para dados existentes
                database.execSQL(
                    "ALTER TABLE tasks ADD COLUMN category TEXT NOT NULL DEFAULT 'OUTRO'"
                )

                // Adicionar coluna 'imageUrl' na tabela tasks (nullable)
                database.execSQL(
                    "ALTER TABLE tasks ADD COLUMN imageUrl TEXT"
                )

                // Adicionar coluna 'imageUrl' na tabela steps (nullable)
                database.execSQL(
                    "ALTER TABLE steps ADD COLUMN imageUrl TEXT"
                )

                // Adicionar coluna 'durationSeconds' na tabela steps
                // Padrão: 60 segundos para dados existentes
                database.execSQL(
                    "ALTER TABLE steps ADD COLUMN durationSeconds INTEGER NOT NULL DEFAULT 60"
                )
            }
        }
    }
}
