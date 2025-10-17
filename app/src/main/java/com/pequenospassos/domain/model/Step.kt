package com.pequenospassos.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * Entidade de passo/subtarefa (step).
 *
 * Representa um passo detalhado dentro de uma tarefa principal.
 * Exemplo: Tarefa "Escovar os dentes" pode ter steps:
 * 1. Pegar a escova
 * 2. Colocar pasta
 * 3. Escovar por 2 minutos
 *
 * @property id Identificador único (auto-gerado)
 * @property taskId ID da tarefa pai
 * @property title Título do passo (ex: "Pegar a escova de dentes")
 * @property order Ordem de execução do passo (sequencial)
 * @property isCompleted Se o passo foi completado
 * @property imageUrl URL/path da imagem do step (aparece durante execução) - MVP-07
 * @property durationSeconds Duração do timer em segundos (15-600s, padrão 60s) - MVP-07
 *
 * @since MVP-02 (13/10/2025) - DIA 1 - Fundação
 * @updated MVP-07 (16/10/2025) - Adicionado imageUrl e durationSeconds
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-02, ⏳ Atualizado - MVP-07
 */
@Entity(
    tableName = "steps",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["taskId"])]
)
data class Step(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val taskId: Long,

    val title: String,

    val order: Int,

    val isCompleted: Boolean = false,

    /**
     * URL ou path local da imagem do step.
     * Aparece durante a execução da tarefa (TaskExecutionScreen).
     * Opcional - se null, não exibe imagem.
     *
     * @since MVP-07
     */
    val imageUrl: String? = null,

    /**
     * Duração do timer em segundos para este step.
     * Range: 5-600 segundos (5s a 10 minutos).
     * Padrão: 60 segundos.
     *
     * @since MVP-07
     */
    val durationSeconds: Int = 60
) {
    /**
     * Valida se o step tem dados mínimos necessários
     */
    fun isValid(): Boolean {
        return title.isNotBlank() && order >= 0 && taskId > 0
    }

    /**
     * Retorna o número do passo para exibição (order + 1)
     */
    fun getStepNumber(): Int = order + 1

    /**
     * Valida se a duração do timer está dentro do range permitido.
     *
     * @return true se duração entre 5 e 600 segundos
     * @since MVP-07
     */
    fun isValidDuration(): Boolean {
        return durationSeconds in 5..600
    }
}

/**
 * Data class para representar uma tarefa com seus steps.
 * Útil para queries que retornam tarefa + lista de steps.
 *
 * @property task Tarefa principal
 * @property steps Lista de steps da tarefa (ordenados por order)
 */
data class TaskWithSteps(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val steps: List<Step>
) {
    /**
     * Retorna o total de steps
     */
    fun getTotalSteps(): Int = steps.size

    /**
     * Retorna quantos steps foram completados
     */
    fun getCompletedSteps(): Int = steps.count { it.isCompleted }

    /**
     * Retorna o progresso em porcentagem (0-100)
     */
    fun getProgressPercentage(): Int {
        if (steps.isEmpty()) return 0
        return (getCompletedSteps() * 100) / getTotalSteps()
    }

    /**
     * Retorna se todos os steps foram completados
     */
    fun isFullyCompleted(): Boolean {
        return steps.isNotEmpty() && steps.all { it.isCompleted }
    }
}
