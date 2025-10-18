package com.pequenospassos.presentation.screens.taskform

import android.net.Uri
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.TaskCategory

/**
 * Estado do formulário de tarefa.
 *
 * Representa todos os dados necessários para criar/editar uma tarefa.
 *
 * @property taskId ID da tarefa (null se for criação)
 * @property title Título da tarefa
 * @property description Descrição da tarefa
 * @property time Horário da tarefa (HH:mm)
 * @property stars Quantidade de estrelas (1-5)
 * @property category Categoria da tarefa (obrigatório - MVP-07)
 * @property imageUrl Imagem principal da tarefa (opcional - MVP-07)
 * @property steps Lista de steps da tarefa
 * @property isLoading Se está salvando
 * @property errorMessage Mensagem de erro (se houver)
 *
 * @since MVP-07 (16/10/2025)
 */
data class TaskFormState(
    val taskId: Long? = null,
    val title: String = "",
    val description: String = "",
    val time: String = "",
    val stars: Int = 3,
    val category: TaskCategory? = null,
    val imageUrl: Uri? = null,
    val steps: List<Step> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false // MVP-07: Flag para indicar salvamento bem-sucedido
) {
    /**
     * Valida se o formulário pode ser salvo.
     */
    fun isValid(): Boolean {
        return title.isNotBlank() &&
               time.matches(Regex("^([01][0-9]|2[0-3]):[0-5][0-9]$")) &&
               stars in 1..5 &&
               category != null && // MVP-07: categoria obrigatória
               steps.isNotEmpty()
    }

    /**
     * Retorna mensagem de erro de validação.
     */
    fun getValidationError(): String? {
        return when {
            title.isBlank() -> "Título é obrigatório"
            !time.matches(Regex("^([01][0-9]|2[0-3]):[0-5][0-9]$")) -> "Horário inválido (use HH:mm)"
            stars !in 1..5 -> "Estrelas devem ser entre 1 e 5"
            category == null -> "Categoria é obrigatória"
            steps.isEmpty() -> "Adicione pelo menos 1 step"
            else -> null
        }
    }
}

/**
 * Eventos do formulário de tarefa.
 */
sealed class TaskFormEvent {
    data class TitleChanged(val title: String) : TaskFormEvent()
    data class DescriptionChanged(val description: String) : TaskFormEvent()
    data class TimeChanged(val time: String) : TaskFormEvent()
    data class StarsChanged(val stars: Int) : TaskFormEvent()
    data class CategorySelected(val category: TaskCategory) : TaskFormEvent()
    data class ImageSelected(val uri: Uri) : TaskFormEvent()
    object ImageRemoved : TaskFormEvent()
    data class StepAdded(val step: Step) : TaskFormEvent()
    data class StepUpdated(val index: Int, val step: Step) : TaskFormEvent()
    data class StepRemoved(val index: Int) : TaskFormEvent()
    object SaveTask : TaskFormEvent()
    object CancelForm : TaskFormEvent()
}
