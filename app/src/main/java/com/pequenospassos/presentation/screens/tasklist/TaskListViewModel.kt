package com.pequenospassos.presentation.screens.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.repository.StepRepository
import com.pequenospassos.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel da tela de lista de tarefas.
 *
 * Gerencia o carregamento e exibição de todas as tarefas cadastradas,
 * incluindo metadados dos steps (contagem de imagens e duração total).
 *
 * @property taskRepository Repositório de tarefas
 * @property stepRepository Repositório de steps
 *
 * @since MVP-07 (18/10/2025) - Fase 3
 * @author PequenosPassos Development Team
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskWithMetadata>>(emptyList())
    val tasks: StateFlow<List<TaskWithMetadata>> = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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
