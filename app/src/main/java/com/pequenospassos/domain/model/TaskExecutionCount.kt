package com.pequenospassos.domain.model

/**
 * Representa o total de execuções de uma tarefa em um período.
 *
 * @property taskId ID da tarefa
 * @property total Número de execuções
 */
data class TaskExecutionCount(
    val taskId: String,
    val total: Int
)

