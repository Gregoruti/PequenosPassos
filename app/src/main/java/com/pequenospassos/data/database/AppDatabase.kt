package com.pequenospassos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pequenospassos.data.database.dao.AchievementDao
import com.pequenospassos.data.database.dao.AppSettingsDao
import com.pequenospassos.data.database.dao.ChildProfileDao
import com.pequenospassos.data.database.dao.RewardDao
import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.data.database.dao.TaskCompletionDao
import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.domain.model.Achievement
import com.pequenospassos.domain.model.AppSettings
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Reward
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskCompletion

/**
 * Database principal do aplicativo PequenosPassos.
 *
 * Room Database com 6 entidades essenciais:
 * - ChildProfile: Perfil da criança
 * - Task: Tarefas diárias
 * - Step: Passos/subtarefas
 * - AppSettings: Configurações do app
 * - Achievement: Conquistas e estrelas (MVP-08)
 * - Reward: Recompensas desbloqueáveis (MVP-08)
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
 * Versão 3 - MVP-08:
 * - Adicionada tabela 'achievements' para sistema de gamificação
 * - Adicionada tabela 'rewards' para recompensas desbloqueáveis
 * - TypeConverter para RewardType
 *
 * Versão 4 - MVP-09:
 * - Adicionada tabela 'task_completions' para controle diário de tarefas
 * - Índice único para garantir 1 conclusão por tarefa/criança/dia
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @updated MVP-07 (16/10/2025) - Migration 1→2
 * @updated MVP-08 (23/10/2025) - Migration 2→3
 * @updated MVP-09 (24/10/2025) - Migration 3→4
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03, MVP-07, MVP-08, ⏳ Em desenvolvimento - MVP-09
 */
@Database(
    entities = [
        ChildProfile::class,
        Task::class,
        Step::class,
        AppSettings::class,
        Achievement::class,
        Reward::class,
        TaskCompletion::class
    ],
    version = 5, // MVP-09: Incrementado de 4 para 5 - Removidas FOREIGN KEYs
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

    /**
     * DAO para operações de achievements (MVP-08)
     */
    abstract fun achievementDao(): AchievementDao

    /**
     * DAO para operações de rewards (MVP-08)
     */
    abstract fun rewardDao(): RewardDao

    /**
     * DAO para operações de task completions (MVP-09)
     */
    abstract fun taskCompletionDao(): TaskCompletionDao

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

        /**
         * Migration da versão 2 para 3 (MVP-08).
         *
         * Adiciona suporte ao sistema de gamificação:
         * - Tabela achievements: Registra conquistas e estrelas ganhas
         * - Tabela rewards: Recompensas desbloqueáveis
         *
         * @since MVP-08 (23/10/2025)
         */
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Criar tabela achievements
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS achievements (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        childId INTEGER NOT NULL,
                        taskId INTEGER NOT NULL,
                        starsEarned INTEGER NOT NULL,
                        completedAt INTEGER NOT NULL,
                        executionTime INTEGER NOT NULL,
                        wasOnTime INTEGER NOT NULL,
                        allStepsCompleted INTEGER NOT NULL,
                        needsHelp INTEGER NOT NULL DEFAULT 0,
                        FOREIGN KEY(childId) REFERENCES child_profile(id) ON DELETE CASCADE,
                        FOREIGN KEY(taskId) REFERENCES tasks(id) ON DELETE CASCADE
                    )
                """.trimIndent())

                // Criar índices para achievements
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_achievements_childId ON achievements(childId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_achievements_taskId ON achievements(taskId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_achievements_completedAt ON achievements(completedAt)"
                )

                // Criar tabela rewards
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS rewards (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        childId INTEGER NOT NULL,
                        title TEXT NOT NULL,
                        description TEXT NOT NULL,
                        starsRequired INTEGER NOT NULL,
                        rewardType TEXT NOT NULL,
                        iconResId INTEGER,
                        imageUri TEXT,
                        isUnlocked INTEGER NOT NULL DEFAULT 0,
                        unlockedAt INTEGER,
                        isActive INTEGER NOT NULL DEFAULT 0,
                        FOREIGN KEY(childId) REFERENCES child_profile(id) ON DELETE CASCADE
                    )
                """.trimIndent())

                // Criar índices para rewards
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_rewards_childId ON rewards(childId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_rewards_isUnlocked ON rewards(isUnlocked)"
                )
            }
        }

        /**
         * Migration da versão 3 para 4 (MVP-09).
         *
         * Adiciona suporte ao sistema de controle diário de tarefas:
         * - Tabela task_completions: Registra conclusões diárias com data/hora
         * - Índice único: Garante 1 conclusão por tarefa/criança/dia
         * - Permite verificar tarefas completadas hoje
         * - Permite calcular estrelas do dia
         *
         * NOTA: Removidas FOREIGN KEYs para permitir funcionamento sem perfil de criança.
         *
         * @since MVP-09 (24/10/2025)
         */
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Criar tabela task_completions SEM FOREIGN KEY constraints
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS task_completions (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        taskId TEXT NOT NULL,
                        childId INTEGER NOT NULL,
                        completedAt INTEGER NOT NULL,
                        date INTEGER NOT NULL,
                        starsEarned INTEGER NOT NULL DEFAULT 0,
                        allStepsCompleted INTEGER NOT NULL DEFAULT 0,
                        completedWithoutHelp INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                // Criar índices para task_completions
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_task_completions_taskId ON task_completions(taskId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_task_completions_childId ON task_completions(childId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_task_completions_date ON task_completions(date)"
                )

                // Índice único: 1 conclusão por tarefa/criança/dia
                database.execSQL(
                    "CREATE UNIQUE INDEX IF NOT EXISTS index_task_completions_unique ON task_completions(taskId, childId, date)"
                )
            }
        }

        /**
         * Migration da versão 4 para 5 (MVP-09).
         *
         * Corrige problema de FOREIGN KEY constraint:
         * - Remove FOREIGN KEYs da tabela task_completions
         * - Permite funcionamento sem perfil de criança cadastrado
         * - Recria tabela com mesma estrutura mas sem constraints
         *
         * @since MVP-09 (24/10/2025)
         */
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Recriar tabela task_completions SEM FOREIGN KEYs

                // 1. Criar tabela temporária com nova estrutura
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS task_completions_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        taskId TEXT NOT NULL,
                        childId INTEGER NOT NULL,
                        completedAt INTEGER NOT NULL,
                        date INTEGER NOT NULL,
                        starsEarned INTEGER NOT NULL DEFAULT 0,
                        allStepsCompleted INTEGER NOT NULL DEFAULT 0,
                        completedWithoutHelp INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                // 2. Copiar dados existentes (se houver)
                database.execSQL("""
                    INSERT INTO task_completions_new (id, taskId, childId, completedAt, date, starsEarned, allStepsCompleted, completedWithoutHelp)
                    SELECT id, taskId, childId, completedAt, date, starsEarned, allStepsCompleted, completedWithoutHelp
                    FROM task_completions
                """.trimIndent())

                // 3. Dropar tabela antiga
                database.execSQL("DROP TABLE task_completions")

                // 4. Renomear tabela nova para o nome original
                database.execSQL("ALTER TABLE task_completions_new RENAME TO task_completions")

                // 5. Recriar índices
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_task_completions_taskId ON task_completions(taskId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_task_completions_childId ON task_completions(childId)"
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_task_completions_date ON task_completions(date)"
                )
                database.execSQL(
                    "CREATE UNIQUE INDEX IF NOT EXISTS index_task_completions_unique ON task_completions(taskId, childId, date)"
                )
            }
        }
    }
}
