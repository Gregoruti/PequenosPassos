package com.pequenospassos.presentation.screens.taskform

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.TaskCategory
import com.pequenospassos.domain.usecase.GetTaskByIdUseCase
import com.pequenospassos.domain.usecase.SaveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para TaskFormScreen.
 *
 * Gerencia o estado e lógica do formulário de criação/edição de tarefas.
 * Integra CategoryPicker, ImagePicker e gerenciamento de steps.
 *
 * @property saveTaskUseCase Use case para salvar tarefa
 * @property getTaskByIdUseCase Use case para buscar tarefa (modo edição)
 *
 * @since MVP-07 (16/10/2025)
 */
@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskFormState())
    val state: StateFlow<TaskFormState> = _state.asStateFlow()

    /**
     * Processa eventos do formulário.
     */
    fun onEvent(event: TaskFormEvent) {
        when (event) {
            is TaskFormEvent.TitleChanged -> {
                _state.update { it.copy(title = event.title) }
            }
            is TaskFormEvent.DescriptionChanged -> {
                _state.update { it.copy(description = event.description) }
            }
            is TaskFormEvent.TimeChanged -> {
                _state.update { it.copy(time = event.time) }
            }
            is TaskFormEvent.StarsChanged -> {
                _state.update { it.copy(stars = event.stars) }
            }
            is TaskFormEvent.CategorySelected -> {
                _state.update { it.copy(category = event.category) }
            }
            is TaskFormEvent.ImageSelected -> {
                _state.update { it.copy(imageUrl = event.uri) }
            }
            is TaskFormEvent.ImageRemoved -> {
                _state.update { it.copy(imageUrl = null) }
            }
            is TaskFormEvent.StepAdded -> {
                val updatedSteps = _state.value.steps + event.step
                _state.update { it.copy(steps = updatedSteps) }
            }
            is TaskFormEvent.StepUpdated -> {
                val updatedSteps = _state.value.steps.toMutableList()
                updatedSteps[event.index] = event.step
                _state.update { it.copy(steps = updatedSteps) }
            }
            is TaskFormEvent.StepRemoved -> {
                val updatedSteps = _state.value.steps.toMutableList()
                updatedSteps.removeAt(event.index)
                // Reordenar os steps
                val reorderedSteps = updatedSteps.mapIndexed { index, step ->
                    step.copy(order = index)
                }
                _state.update { it.copy(steps = reorderedSteps) }
            }
            is TaskFormEvent.SaveTask -> {
                saveTask()
            }
            is TaskFormEvent.CancelForm -> {
                // Navegação será tratada na tela
            }
        }
    }

    /**
     * Carrega tarefa existente para edição.
     */
    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            getTaskByIdUseCase(taskId).collect { task ->
                task?.let {
                    _state.update { state ->
                        state.copy(
                            taskId = it.id,
                            title = it.title,
                            description = it.description,
                            time = it.time,
                            stars = it.stars,
                            category = TaskCategory.fromString(it.category),
                            imageUrl = it.imageUrl?.let { url -> Uri.parse(url) }
                            // Steps serão carregados separadamente
                        )
                    }
                }
            }
        }
    }

    /**
     * Salva a tarefa no banco de dados.
     */
    private fun saveTask() {
        val currentState = _state.value

        // Validar antes de salvar
        val validationError = currentState.getValidationError()
        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                // Extrair títulos dos steps
                val stepTitles = currentState.steps.map { it.title }

                // Salvar tarefa com steps usando a assinatura correta
                val result = saveTaskUseCase(
                    title = currentState.title,
                    description = currentState.description,
                    iconRes = 0, // TODO: Implementar seleção de ícone
                    time = currentState.time,
                    stars = currentState.stars,
                    category = currentState.category!!.name,
                    imageUrl = currentState.imageUrl?.toString(),
                    steps = stepTitles
                )

                when (result) {
                    is com.pequenospassos.domain.model.AppResult.Success -> {
                        _state.update { it.copy(isLoading = false, errorMessage = null) }
                        // Sucesso - navegação será tratada na tela
                    }
                    is com.pequenospassos.domain.model.AppResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Erro ao salvar: ${result.exception.message}"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Erro ao salvar: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Limpa mensagem de erro.
     */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
