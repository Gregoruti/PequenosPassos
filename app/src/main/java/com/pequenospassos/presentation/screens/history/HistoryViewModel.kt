package com.pequenospassos.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskExecutionCount
import com.pequenospassos.domain.repository.TaskCompletionRepository
import com.pequenospassos.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel para a tela de Histórico.
 *
 * Gerencia estatísticas diárias, histórico e ferramentas de debug.
 *
 * MVP-09 v1.11.5: Implementação completa de Histórico
 *
 * @property taskRepository Repositório de tarefas
 * @since MVP-09 v1.11.5 (24/10/2025)
 * @author PequenosPassos Development Team
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCompletionRepository: TaskCompletionRepository
) : ViewModel() {

    // ID da criança (hardcoded temporariamente)
    private val childId = 1L

    // ========================================
    // ESTATÍSTICAS DIÁRIAS
    // ========================================

    /**
     * Total de estrelas ganhas hoje.
     */
    val starsToday: StateFlow<Int> = taskRepository.getStarsForToday(childId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /**
     * Número de tarefas disponíveis hoje (não completadas).
     */
    val availableTasksCountToday: StateFlow<Int> =
        taskRepository.getAvailableTasksCountToday(childId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )

    /**
     * IDs das tarefas completadas hoje.
     */
    val completedTaskIdsToday: StateFlow<List<String>> =
        taskRepository.getCompletedTaskIdsToday(childId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    /**
     * Histórico de conclusões (últimos 100 registros).
     */
    val completionsHistory = taskRepository.getCompletionsHistory(childId, limit = 100)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ========================================
    // ESTATÍSTICAS CALCULADAS
    // ========================================

    /**
     * Total de tarefas completadas hoje.
     */
    val completedTasksCountToday: StateFlow<Int> = completedTaskIdsToday
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /**
     * Total de tarefas cadastradas (completadas + disponíveis).
     */
    val totalTasksToday: StateFlow<Int> = combine(
        completedTasksCountToday,
        availableTasksCountToday
    ) { completed, available ->
        completed + available
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * Percentual de conclusão do dia (0-100).
     */
    val completionPercentageToday: StateFlow<Int> = combine(
        completedTasksCountToday,
        totalTasksToday
    ) { completed, total ->
        if (total > 0) {
            ((completed.toFloat() / total.toFloat()) * 100).toInt()
        } else {
            0
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * Média diária de estrelas nos últimos 7 dias.
     */
    val averageStarsPerDayWeek: StateFlow<Int> = flow {
        val today = LocalDate.now()
        val startDate = today.minusDays(6)
        val starsList = mutableListOf<Int>()
        for (i in 0..6) {
            val date = startDate.plusDays(i.toLong())
            val stars = taskRepository.getStarsForDate(childId, date)
            starsList.add(stars)
        }
        val avg = if (starsList.isNotEmpty()) starsList.sum() / 7 else 0
        emit(avg)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * Média diária de tarefas feitas nos últimos 7 dias.
     */
    val averageTasksCompletedPerDayWeek: StateFlow<Int> = flow {
        val today = LocalDate.now()
        val startDate = today.minusDays(6)
        val completedList = mutableListOf<Int>()
        for (i in 0..6) {
            val date = startDate.plusDays(i.toLong())
            val completed = taskRepository.getTasksCompletedCountForDate(childId, date)
            completedList.add(completed)
        }
        val avg = if (completedList.isNotEmpty()) completedList.sum() / 7 else 0
        emit(avg)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    // ========================================
    // RANKING SEMANAL
    // ========================================

    // Top 3 mais e menos executadas na semana
    private val _mostExecutedTasksWeek = MutableStateFlow<List<TaskExecutionCount>>(emptyList())
    val mostExecutedTasksWeek: StateFlow<List<TaskExecutionCount>> = _mostExecutedTasksWeek

    private val _leastExecutedTasksWeek = MutableStateFlow<List<TaskExecutionCount>>(emptyList())
    val leastExecutedTasksWeek: StateFlow<List<TaskExecutionCount>> = _leastExecutedTasksWeek

    init {
        // Carrega todas as tarefas e popula o mapa
        viewModelScope.launch {
            taskRepository.getAllTasksOrderedByTime().collect { tasks ->
                _tasksMap.value = tasks.associateBy { it.id.toString() }
            }
        }
        // Carrega o ranking semanal ao iniciar o ViewModel
        loadWeeklyTaskRankings()
    }

    /**
     * Carrega o ranking das tarefas mais e menos executadas na semana atual.
     */
    fun loadWeeklyTaskRankings() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val startDate = today.minusDays(6)
            val endDate = today
            println("[HistoryViewModel] Carregando ranking semanal: $startDate até $endDate")
            val most = taskCompletionRepository.getMostExecutedTasksInRange(childId, startDate, endDate, 3)
            val least = taskCompletionRepository.getLeastExecutedTasksInRange(childId, startDate, endDate, 3)
            println("[HistoryViewModel] Top 3 mais executadas: $most")
            println("[HistoryViewModel] Top 3 menos executadas: $least")
            _mostExecutedTasksWeek.value = most
            _leastExecutedTasksWeek.value = least
        }
    }

    // ========================================
    // AÇÕES/COMANDOS
    // ========================================

    private val _resetMessage = MutableStateFlow<String?>(null)
    val resetMessage: StateFlow<String?> = _resetMessage.asStateFlow()

    /**
     * Zera as tarefas completadas hoje.
     * Remove todas as conclusões do dia atual do banco de dados.
     */
    fun resetTasksToday() {
        viewModelScope.launch {
            val result = taskRepository.deleteCompletionsForToday(childId)
            if (result.isSuccess) {
                _resetMessage.value = "✅ Tarefas de hoje foram zeradas!"
            } else {
                _resetMessage.value = "❌ Erro ao zerar tarefas: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    /**
     * Zera TODAS as estrelas (histórico completo).
     * Remove todos os registros de conclusões da criança.
     */
    fun resetAllStars() {
        viewModelScope.launch {
            val result = taskRepository.deleteAllCompletions(childId)
            if (result.isSuccess) {
                _resetMessage.value = "✅ Todas as estrelas foram zeradas!"
            } else {
                _resetMessage.value = "❌ Erro ao zerar estrelas: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    /**
     * Limpa mensagem de reset após ser exibida.
     */
    fun clearResetMessage() {
        _resetMessage.value = null
    }

    // Mapa de ID (String) para Task para lookup rápido
    private val _tasksMap = MutableStateFlow<Map<String, Task>>(emptyMap())
    val tasksMap: StateFlow<Map<String, Task>> = _tasksMap

    init {
        // Carrega todas as tarefas e popula o mapa
        viewModelScope.launch {
            taskRepository.getAllTasksOrderedByTime().collect { tasks ->
                _tasksMap.value = tasks.associateBy { it.id.toString() }
            }
        }
    }
}
