package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.remote.ReservationDto
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
    val niveauExperience: String = "",
    
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
                niveauExperience.isNotBlank() &&
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

    fun updateNiveauExperience(value: String) {
        _uiState.update { it.copy(niveauExperience = value) }
    }

    fun updateSelectedDate(value: String) {
        _uiState.update { it.copy(selectedDate = value) }
    }

    fun updateSelectedTime(value: String) {
        _uiState.update { it.copy(selectedTime = value) }
    }

// Send to Supabase Data
    fun submitReservation() {
        val currentState = _uiState.value
        if (!currentState.canSubmit) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submissionSuccess = null, errorMessage = null) }
            try {
                val reservation = ReservationDto(
                    nom = currentState.nom,
                    prenom = currentState.prenom,
                    email = currentState.email,
                    date_de_naissance = currentState.dateDeNaissance,
                    genre = currentState.genre,
                    selected_game = currentState.selectedGame,
                    nb_joueurs = currentState.nbJoueurs,
                    niveau_experience = currentState.niveauExperience,
                    selected_date = currentState.selectedDate,
                    selected_time = currentState.selectedTime
                )

                SupabaseClient.client.postgrest["reservations"].insert(reservation)
                
                _uiState.update { it.copy(isSubmitting = false, submissionSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isSubmitting = false, 
                        submissionSuccess = false,
                        errorMessage = "Une erreur extérieure à l'application est en cours et tout sera rétabli au plus vite."
                    ) 
                }
            }
        }
    }

    fun resetSubmissionStatus() {
        _uiState.update { it.copy(submissionSuccess = null, errorMessage = null) }
    }
}
