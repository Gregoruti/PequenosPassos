package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Entidade que representa uma conquista/achievement obtido ao completar uma tarefa.
 * Armazena informações sobre estrelas ganhas e detalhes da execução.
 *
 * MVP08 - Sistema de Gamificação
 */
@Entity(
    tableName = "achievements",
    foreignKeys = [
        ForeignKey(
            entity = ChildProfile::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Task::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["childId"]),
        Index(value = ["taskId"]),
        Index(value = ["completedAt"])
    ]
)
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** ID do perfil da criança que completou a tarefa */
    val childId: Long,

    /** ID da tarefa completada */
    val taskId: Long,

    /** Quantidade de estrelas ganhas (1-5) */
    val starsEarned: Int,

    /** Data e hora da conclusão */
    val completedAt: LocalDateTime,

    /** Tempo de execução em segundos */
    val executionTime: Long,

    /** Se a tarefa foi completada no horário previsto */
    val wasOnTime: Boolean,

    /** Se todos os steps foram completados */
    val allStepsCompleted: Boolean,

    /** Se a criança precisou de ajuda */
    val needsHelp: Boolean = false
)

