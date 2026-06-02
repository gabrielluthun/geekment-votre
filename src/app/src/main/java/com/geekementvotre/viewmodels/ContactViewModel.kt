package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.remote.ResendService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContactUiState(
    val nom: String = "",
    val email: String = "",
    val sujet: String = "",
    val message: String = "",
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean? = null,
    val errorMessage: String? = null
) {
    val isEmailValid: Boolean
        get() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val canSubmit: Boolean
        get() = nom.isNotBlank() && isEmailValid && sujet.isNotBlank() && message.isNotBlank()
}

class ContactViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ContactUiState())
    val uiState: StateFlow<ContactUiState> = _uiState.asStateFlow()

    fun updateNom(value: String) { _uiState.update { it.copy(nom = value) } }
    fun updateEmail(value: String) { _uiState.update { it.copy(email = value) } }
    fun updateSujet(value: String) { _uiState.update { it.copy(sujet = value) } }
    fun updateMessage(value: String) { _uiState.update { it.copy(message = value) } }

    fun submitContact() {
        val currentState = _uiState.value
        if (!currentState.canSubmit) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submissionSuccess = null, errorMessage = null) }
            try {
              // Resend Email Service
                val emailSuccess = ResendService.sendContactEmails(
                    nom = currentState.nom,
                    userEmail = currentState.email,
                    sujet = currentState.sujet,
                    message = currentState.message
                )

                if (emailSuccess) {
                    _uiState.update { ContactUiState(submissionSuccess = true) }
                } else {
                    _uiState.update { 
                        it.copy(
                            isSubmitting = false,
                            submissionSuccess = false,
                            errorMessage = "L'envoi du message a échoué. Veuillez réessayer."
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { 
                    it.copy(
                        isSubmitting = false, 
                        submissionSuccess = false,
                        errorMessage = "Une erreur est survenue : ${e.localizedMessage}"
                    ) 
                }
            }
        }
    }

    fun resetSubmissionStatus() {
        _uiState.update { it.copy(submissionSuccess = null, errorMessage = null) }
    }
}
