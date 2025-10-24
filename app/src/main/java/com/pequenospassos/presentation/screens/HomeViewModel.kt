package com.pequenospassos.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.repository.ChildProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel para a HomeScreen.
 *
 * Gerencia o carregamento do perfil da criança para exibição na tela inicial.
 *
 * @property repository Repositório de ChildProfile injetado via Hilt
 * @since MVP-08 (23/10/2025) - Correção de cadastro
 * @author PequenosPassos Development Team
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ChildProfileRepository
) : ViewModel() {

    /**
     * Perfil da criança observável.
     * Atualiza automaticamente quando houver mudanças no banco.
     */
    val childProfile: StateFlow<ChildProfile?> = repository.getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}

