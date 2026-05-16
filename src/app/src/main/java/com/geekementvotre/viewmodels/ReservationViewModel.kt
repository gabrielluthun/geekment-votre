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
    // Vos Informations
    val nom: String = "",
    val prenom: String = "",
    val email: String = "",
    val dateNaissance: String = "",
    val genre: String = "",

    // Détails de la session
    val selectedDate: String = "",
    val nbJoueurs: String = "",
    val selectedTime: String = "",
    val selectedGame: String = "",
    
    // Lieu et Message
    val adresseSession: String = "",
    val messageSpecifique: String = "",
    
    // État de la soumission
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean? = null,
    val errorMessage: String? = null
) {
    val canSubmit: Boolean
        get() = nom.isNotBlank() &&
                prenom.isNotBlank() &&
                email.isNotBlank() &&
                selectedDate.isNotBlank() && 
                nbJoueurs.isNotBlank() && 
                selectedTime.isNotBlank() && 
                selectedGame.isNotBlank()
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
        _uiState.update { it.copy(email = value) }
    }

    fun updateDateNaissance(value: String) {
        _uiState.update { it.copy(dateNaissance = value) }
    }

    fun updateGenre(value: String) {
        _uiState.update { it.copy(genre = value) }
    }

    fun updateSelectedDate(value: String) {
        _uiState.update { it.copy(selectedDate = value) }
    }

    fun updateSelectedTime(value: String) {
        _uiState.update { it.copy(selectedTime = value) }
    }

    fun updateGame(value: String) {
        _uiState.update { it.copy(selectedGame = value) }
    }

    fun updateNbJoueurs(value: String) {
        _uiState.update { it.copy(nbJoueurs = value) }
    }

    fun updateAdresseSession(value: String) {
        _uiState.update { it.copy(adresseSession = value) }
    }

    fun updateMessageSpecifique(value: String) {
        _uiState.update { it.copy(messageSpecifique = value) }
    }

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
                    date_de_naissance = currentState.dateNaissance,
                    genre = currentState.genre,
                    selected_date = currentState.selectedDate,
                    nb_joueurs = currentState.nbJoueurs.toIntOrNull() ?: 0,
                    selected_time = currentState.selectedTime,
                    selected_game = currentState.selectedGame,
                    adresse_session = currentState.adresseSession.ifBlank { null },
                    message_specifique = currentState.messageSpecifique.ifBlank { null }
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
