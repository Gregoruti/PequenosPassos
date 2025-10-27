package com.pequenospassos.presentation.screens.childregistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.repository.ChildProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a tela de cadastro de criança.
 *
 * Gerencia o estado do formulário e a persistência dos dados no banco.
 *
 * @property repository Repositório de ChildProfile injetado via Hilt
 * @since MVP-08 (23/10/2025) - Correção de salvamento
 * @author PequenosPassos Development Team
 */
@HiltViewModel
class ChildRegistrationViewModel @Inject constructor(
    private val repository: ChildProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Idle)
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _existingProfile = MutableStateFlow<ChildProfile?>(null)
    val existingProfile: StateFlow<ChildProfile?> = _existingProfile.asStateFlow()

    init {
        loadExistingProfile()
    }

    /**
     * Carrega perfil existente (se houver) para edição
     */
    private fun loadExistingProfile() {
        viewModelScope.launch {
            repository.getProfile().collect { profile ->
                _existingProfile.value = profile
            }
        }
    }

    /**
     * Salva ou atualiza o perfil da criança
     */
    fun saveProfile(
        name: String,
        gender: Gender,
        birthDate: String? = null,
        observations: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = RegistrationUiState.Loading

            // Validar nome
            if (name.trim().length < 2) {
                _uiState.value = RegistrationUiState.Error("Nome deve ter pelo menos 2 caracteres")
                return@launch
            }

            // Criar perfil
            val profile = ChildProfile(
                id = "default_child",
                name = name.trim(),
                gender = gender,
                photoUri = _existingProfile.value?.photoUri, // Manter foto existente se houver
                createdAt = _existingProfile.value?.createdAt ?: System.currentTimeMillis(),
                birthDate = birthDate,
                observations = observations
            )

            // Salvar no banco
            val result = repository.saveProfile(profile)

            _uiState.value = if (result.isSuccess) {
                RegistrationUiState.Success
            } else {
                RegistrationUiState.Error("Erro ao salvar: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    /**
     * Reseta o estado para idle (após mostrar mensagem)
     */
    fun resetState() {
        _uiState.value = RegistrationUiState.Idle
    }
}

/**
 * Estados possíveis da UI de cadastro
 */
sealed class RegistrationUiState {
    object Idle : RegistrationUiState()
    object Loading : RegistrationUiState()
    object Success : RegistrationUiState()
    data class Error(val message: String) : RegistrationUiState()
}
