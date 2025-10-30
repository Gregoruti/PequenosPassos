package com.pequenospassos.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.repository.ChildProfileRepository
import com.pequenospassos.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

import javax.inject.Inject

/**
 * ViewModel para a HomeScreen.
 *
 * Gerencia o carregamento do perfil da criança para exibição na tela inicial.
 * MVP-09 v1.11.4: Adicionadas estatísticas diárias (estrelas e tarefas disponíveis).
 *
 * @property childProfileRepository Repositório de ChildProfile injetado via Hilt
 * @property taskRepository Repositório de Task injetado via Hilt
 * @since MVP-08 (23/10/2025) - Correção de cadastro
 * @updated MVP-09 v1.11.4 (24/10/2025) - Estatísticas diárias
 * @author PequenosPassos Development Team
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val childProfileRepository: ChildProfileRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    // ID da criança (hardcoded temporariamente - será do perfil selecionado)
    private val childId = 1L

    private val triggerDayUpdate = MutableStateFlow(0)

    /**
     * Perfil da criança observável.
     * Atualiza automaticamente quando houver mudanças no banco.
     */
    val childProfile: StateFlow<ChildProfile?> = childProfileRepository.getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    /**
     * MVP-09 v1.11.4: Total de estrelas ganhas hoje.
     * Atualiza automaticamente quando uma tarefa é completada.
     */
    val starsToday: StateFlow<Int> = triggerDayUpdate.flatMapLatest {
        taskRepository.getStarsForToday(childId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * MVP-09 v1.11.4: Número de tarefas disponíveis hoje.
     * Quantidade de tarefas que ainda NÃO foram completadas hoje.
     */
    val availableTasksCountToday: StateFlow<Int> = triggerDayUpdate.flatMapLatest {
        taskRepository.getAvailableTasksCountToday(childId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * Força atualização dos dados sensíveis ao dia (chamado ao detectar mudança de dia).
     */
    fun refreshDaySensitiveData() {
        triggerDayUpdate.value++
    }
}
