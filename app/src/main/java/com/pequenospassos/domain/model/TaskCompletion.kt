package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Entity que registra a conclusão de uma tarefa em um dia específico.
 *
 * Implementa sistema de controle diário onde:
 * - Cada tarefa pode ser completada apenas 1x por dia
 * - Registra data/hora exata da conclusão
 * - Armazena estrelas ganhas
 * - Permite verificar se tarefa foi completada "hoje"
 *
 * @property id ID único da conclusão
 * @property taskId ID da tarefa completada
 * @property childId ID da criança que completou
 * @property completedAt Data e hora exata da conclusão
 * @property date Data da conclusão (apenas dia, usado para queries)
 * @property starsEarned Quantidade de estrelas ganhas (1-5)
 * @property allStepsCompleted Se todos os steps foram completados
 * @property completedWithoutHelp Se foi completado sem ajuda
 *
 * NOTA: Removidas FOREIGN KEYs para permitir funcionamento sem perfil de criança cadastrado.
 *
 * @since MVP-09 (v1.11.0) - Sistema de Controle Diário
 * @updated MVP-09 (v1.11.2) - Removidas FOREIGN KEYs
 * @author PequenosPassos Development Team
 */
@Entity(
    tableName = "task_completions",
    indices = [
        Index(value = ["taskId"]),
        Index(value = ["childId"]),
        Index(value = ["date"]),
        Index(value = ["taskId", "childId", "date"], unique = true) // 1 conclusão por tarefa/criança/dia
    ]
)
data class TaskCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val taskId: String,
    val childId: Long,
    val completedAt: LocalDateTime,
    val date: LocalDate, // Data sem hora (para queries diárias)
    val starsEarned: Int = 0,
    val allStepsCompleted: Boolean = false,
    val completedWithoutHelp: Boolean = false
)

