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
    
    // Sélection JDR
    val selectedGame: String = "",
    val nbJoueurs: Int = 1,
    
    // Planification
    val selectedDate: String = "",
    val selectedTime: String = "",
    
    // État de la soumission
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean? = null,
    val errorMessage: String? = null,
    
    // Validation
    val isEmailValid: Boolean = true
) {
    val canSubmit: Boolean
        get() = nom.isNotBlank() && 
                prenom.isNotBlank() && 
                email.isNotBlank() && 
                isEmailValid &&
                dateDeNaissance.isNotBlank() &&
                selectedGame.isNotBlank() &&
                selectedDate.isNotBlank() &&
                selectedTime.isNotBlank()
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

    fun updateGame(value: String) {
        _uiState.update { it.copy(selectedGame = value) }
    }

    fun updateNbJoueurs(value: Int) {
        _uiState.update { it.copy(nbJoueurs = value) }
    }

    fun updateSelectedDate(value: String) {
        _uiState.update { it.copy(selectedDate = value) }
    }

    fun updateSelectedTime(value: String) {
        _uiState.update { it.copy(selectedTime = value) }
    }
}
