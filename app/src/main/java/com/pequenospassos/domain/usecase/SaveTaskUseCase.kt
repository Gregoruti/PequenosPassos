package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.repository.StepRepository
import com.pequenospassos.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use Case para salvar uma tarefa com steps (subtarefas).
 *
 * Valida os dados e salva a tarefa junto com seus steps opcionais.
 * Regras de negócio:
 * - Título é obrigatório
 * - Horário deve estar no formato HH:mm válido
 * - Estrelas devem estar entre 1 e 5
 * - Steps são opcionais mas são salvos em ordem
 *
 * @property taskRepository Repositório de tarefas
 * @property stepRepository Repositório de steps
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
@Singleton
class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository
) {
    /**
     * Salva uma tarefa com validações e steps opcionais.
     *
     * @param title Título da tarefa
     * @param description Descrição detalhada (opcional)
     * @param iconRes Recurso de ícone
     * @param time Horário no formato HH:mm
     * @param stars Quantidade de estrelas (1-5)
     * @param steps Lista de títulos de steps (opcional)
     * @return AppResult com ID da tarefa criada ou erro
     */
    suspend operator fun invoke(
        title: String,
        description: String = "",
        iconRes: Int,
        time: String,
        stars: Int,
        steps: List<String> = emptyList()
    ): AppResult<Long> {
        // Validação: título é obrigatório
        if (title.isBlank()) {
            return AppResult.Error(
                IllegalArgumentException("Título é obrigatório")
            )
        }

        // Validação: horário no formato HH:mm
        if (!isValidTime(time)) {
            return AppResult.Error(
                IllegalArgumentException("Horário inválido (formato HH:mm)")
            )
        }

        // Validação: estrelas entre 1 e 5
        if (stars !in 1..5) {
            return AppResult.Error(
                IllegalArgumentException("Estrelas devem estar entre 1 e 5")
            )
        }

        val task = Task(
            title = title.trim(),
            description = description.trim(),
            iconRes = iconRes,
            time = time,
            stars = stars
        )

        return try {
            // Salvar a tarefa
            val taskId = taskRepository.insertTask(task).getOrThrow()

            // Inserir steps se houver
            steps.forEachIndexed { index, stepTitle ->
                if (stepTitle.isNotBlank()) {
                    val step = Step(
                        taskId = taskId,
                        title = stepTitle.trim(),
                        order = index + 1
                    )
                    stepRepository.insertStep(step).getOrThrow()
                }
            }

            AppResult.Success(taskId)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    /**
     * Valida se o horário está no formato HH:mm correto.
     */
    private fun isValidTime(time: String): Boolean {
        return try {
            val parts = time.split(":")
            if (parts.size != 2) return false
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()
            hour in 0..23 && minute in 0..59
        } catch (e: Exception) {
            false
        }
    }
}

