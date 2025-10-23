package com.pequenospassos.presentation.screens.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.repository.StepRepository
import com.pequenospassos.domain.repository.TaskRepository
import com.pequenospassos.domain.usecase.DeleteTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel da tela de lista de tarefas.
 *
 * Gerencia o carregamento e exibição de todas as tarefas cadastradas,
 * incluindo metadados dos steps (contagem de imagens e duração total).
 * Também gerencia a exclusão de tarefas.
 *
 * @property taskRepository Repositório de tarefas
 * @property stepRepository Repositório de steps
 * @property deleteTaskUseCase Use case para deletar tarefas
 *
 * @since MVP-07 (18/10/2025) - Fase 3
 * @updated MVP-07 (20/10/2025) - Adicionada exclusão de tarefas
 * @author PequenosPassos Development Team
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskWithMetadata>>(emptyList())
    val tasks: StateFlow<List<TaskWithMetadata>> = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _deleteSuccess = MutableStateFlow<String?>(null)
    val deleteSuccess: StateFlow<String?> = _deleteSuccess.asStateFlow()

    private val _deleteError = MutableStateFlow<String?>(null)
    val deleteError: StateFlow<String?> = _deleteError.asStateFlow()

    init {
        loadTasks()
    }

    /**
     * Carrega todas as tarefas com metadados dos steps.
     */
    private fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                taskRepository.getAllTasksOrderedByTime()
                    .collect { taskList ->
                        // Para cada tarefa, buscar metadados dos steps
                        val tasksWithMetadata = taskList.map { task ->
                            val steps = stepRepository.getStepsByTask(task.id).first()
                            TaskWithMetadata(
                                task = task,
                                stepCount = steps.size,
                                imageCount = steps.count { !it.imageUrl.isNullOrEmpty() },
                                totalDurationSeconds = steps.sumOf { it.durationSeconds }
                            )
                        }
                        _tasks.value = tasksWithMetadata
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _isLoading.value = false
                _tasks.value = emptyList()
            }
        }
    }

    /**
     * Deleta uma tarefa pelo ID.
     *
     * @param taskId ID da tarefa a ser deletada
     */
    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            try {
                val result = deleteTaskUseCase(taskId)
                when (result) {
                    is com.pequenospassos.domain.model.AppResult.Success -> {
                        _deleteSuccess.value = "Tarefa excluída com sucesso"
                        // Recarregar lista após exclusão
                        loadTasks()
                    }
                    is com.pequenospassos.domain.model.AppResult.Error -> {
                        _deleteError.value = "Erro ao excluir: ${result.exception.message}"
                    }
                }
            } catch (e: Exception) {
                _deleteError.value = "Erro ao excluir: ${e.message}"
            }
        }
    }

    /**
     * Limpa mensagens de sucesso/erro após exibição.
     */
    fun clearMessages() {
        _deleteSuccess.value = null
        _deleteError.value = null
    }
}

/**
 * Data class que combina Task com metadados dos steps.
 *
 * @property task Tarefa
 * @property stepCount Quantidade de steps
 * @property imageCount Quantidade de steps com imagem
 * @property totalDurationSeconds Duração total de todos os steps em segundos
 */
data class TaskWithMetadata(
    val task: Task,
    val stepCount: Int,
    val imageCount: Int,
    val totalDurationSeconds: Int
) {
    /**
     * Formata a duração total em formato legível.
     * Ex: "2 min 30 seg", "45 seg", "10 min"
     */
    fun getFormattedDuration(): String {
        val minutes = totalDurationSeconds / 60
        val seconds = totalDurationSeconds % 60

        return when {
            minutes > 0 && seconds > 0 -> "${minutes} min ${seconds} seg"
            minutes > 0 -> "${minutes} min"
            else -> "${seconds} seg"
        }
    }
}
