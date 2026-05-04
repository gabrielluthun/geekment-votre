package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ReservationUiState(
    // Informations personnelles
    val nom: String = "",
    val prenom: String = "",
    val email: String = "",
    val dateDeNaissance: String = "",
    val genre: String = "",
    
    // Validation
    val isEmailValid: Boolean = true
) {
    val canSubmit: Boolean
        get() = nom.isNotBlank() && 
                prenom.isNotBlank() && 
                email.isNotBlank() && 
                isEmailValid &&
                dateDeNaissance.isNotBlank()
}

class ReservationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    fun updateNom(value: String) {
        _uiState.update { it.copy(nom = value) }
    }

    fun updatePrenom(value: String) {
        _uiState.update { it.copy(prenom = value) }
    }

    fun updateEmail(value: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        _uiState.update { 
            it.copy(
                email = value,
                isEmailValid = value.isEmpty() || value.matches(emailRegex)
            ) 
        }
    }

    fun updateDateDeNaissance(value: String) {
        _uiState.update { it.copy(dateDeNaissance = value) }
    }

    fun updateGenre(value: String) {
        _uiState.update { it.copy(genre = value) }
    }
}
