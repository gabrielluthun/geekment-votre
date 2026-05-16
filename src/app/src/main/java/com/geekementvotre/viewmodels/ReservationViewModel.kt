package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.remote.ClientDto
import com.geekementvotre.data.remote.ReservationDto
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ReservationUiState(
    // Informations Client
    val nom: String = "",
    val prenom: String = "",
    val email: String = "",
    val telephone: String = "",
    val numeroRue: String = "",
    val nomRue: String = "",
    val codePostal: String = "",
    val nomVille: String = "",
    val dateNaissance: LocalDate? = null,
    val genre: String = "",

    // Détails de la session
    val dateSession: LocalDate? = null,
    val nbJoueurs: String = "",
    val creneauHoraire: String = "", // Format ex: "Après-midi (3h)"
    val typeJeu: String = "",
    
    // Lieu et Message
    val aDomicileClient: Boolean = true,
    val numeroRueSession: String = "",
    val nomRueSession: String = "",
    val codePostalSession: String = "",
    val nomVilleSession: String = "",
    val messageDemande: String = "",
    
    // État de la soumission
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean? = null,
    val errorMessage: String? = null
) {
    val isEmailValid: Boolean
        get() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val canSubmit: Boolean
        get() = nom.isNotBlank() &&
                prenom.isNotBlank() &&
                isEmailValid &&
                dateSession != null && 
                nbJoueurs.isNotBlank() && 
                creneauHoraire.isNotBlank() && 
                typeJeu.isNotBlank() &&
                (aDomicileClient || (nomRueSession.isNotBlank() && nomVilleSession.isNotBlank() && codePostalSession.isNotBlank()))
}

class ReservationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    fun updateNom(value: String) { _uiState.update { it.copy(nom = value) } }
    fun updatePrenom(value: String) { _uiState.update { it.copy(prenom = value) } }
    fun updateEmail(value: String) { _uiState.update { it.copy(email = value) } }
    fun updateTelephone(value: String) { _uiState.update { it.copy(telephone = value) } }
    fun updateNumeroRue(value: String) { _uiState.update { it.copy(numeroRue = value) } }
    fun updateNomRue(value: String) { _uiState.update { it.copy(nomRue = value) } }
    fun updateCodePostal(value: String) { _uiState.update { it.copy(codePostal = value) } }
    fun updateNomVille(value: String) { _uiState.update { it.copy(nomVille = value) } }
    fun updateDateNaissance(value: LocalDate?) { _uiState.update { it.copy(dateNaissance = value) } }
    fun updateGenre(value: String) { _uiState.update { it.copy(genre = value) } }

    fun updateDateSession(value: LocalDate?) { _uiState.update { it.copy(dateSession = value) } }
    fun updateCreneauHoraire(value: String) { _uiState.update { it.copy(creneauHoraire = value) } }
    fun updateTypeJeu(value: String) { _uiState.update { it.copy(typeJeu = value) } }
    fun updateNbJoueurs(value: String) { _uiState.update { it.copy(nbJoueurs = value) } }

    fun updateADomicileClient(value: Boolean) { _uiState.update { it.copy(aDomicileClient = value) } }
    fun updateNumeroRueSession(value: String) { _uiState.update { it.copy(numeroRueSession = value) } }
    fun updateNomRueSession(value: String) { _uiState.update { it.copy(nomRueSession = value) } }
    fun updateCodePostalSession(value: String) { _uiState.update { it.copy(codePostalSession = value) } }
    fun updateNomVilleSession(value: String) { _uiState.update { it.copy(nomVilleSession = value) } }
    fun updateMessageDemande(value: String) { _uiState.update { it.copy(messageDemande = value) } }

    fun submitReservation() {
        val currentState = _uiState.value
        if (!currentState.canSubmit) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submissionSuccess = null, errorMessage = null) }
            try {
                // 1. Gérer le client (Upsert basé sur l'email)
                val client = ClientDto(
                    nom = currentState.nom,
                    prenom = currentState.prenom,
                    email = currentState.email,
                    telephone = currentState.telephone.ifBlank { null },
                    numero_rue = currentState.numeroRue.toIntOrNull(),
                    nom_rue = currentState.nomRue.ifBlank { null },
                    code_postal = currentState.codePostal.ifBlank { null },
                    nom_ville = currentState.nomVille.ifBlank { null },
                    date_naissance = currentState.dateNaissance?.toString(),
                    genre = currentState.genre.ifBlank { null }
                )

                SupabaseClient.client.postgrest["client"].upsert(
                    listOf(client),
                    request = { onConflict = "email" }
                )

                // On récupère l'UUID du client par un select explicite (plus sûr)
                val uuidClient = SupabaseClient.client.postgrest["client"].select {
                    filter { eq("email", currentState.email) }
                }.decodeSingle<ClientDto>().uuid_client ?: throw Exception("Identifiant client introuvable")

                // 2. Créer la réservation
                val reservation = ReservationDto(
                    uuid_client = uuidClient,
                    date_session = currentState.dateSession.toString(),
                    creneau_horaire = currentState.creneauHoraire,
                    numero_rue_session = if (!currentState.aDomicileClient) currentState.numeroRueSession.toIntOrNull() else null,
                    nom_rue_session = if (!currentState.aDomicileClient) currentState.nomRueSession.ifBlank { null } else null,
                    code_postal_session = if (!currentState.aDomicileClient) currentState.codePostalSession.ifBlank { null } else null,
                    nom_ville_session = if (!currentState.aDomicileClient) currentState.nomVilleSession.ifBlank { null } else null,
                    a_domicile_client = currentState.aDomicileClient,
                    message_demande = currentState.messageDemande.ifBlank { null },
                    nb_joueurs = currentState.nbJoueurs.toIntOrNull() ?: 0,
                    type_jeu = currentState.typeJeu
                )

                SupabaseClient.client.postgrest["reservation"].insert(reservation)
                
                _uiState.value = ReservationUiState(submissionSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { 
                    it.copy(
                        isSubmitting = false, 
                        submissionSuccess = false,
                        errorMessage = "Une erreur est survenue lors de la réservation : ${e.localizedMessage}"
                    ) 
                }
            }
        }
    }


    fun resetSubmissionStatus() {
        _uiState.update { it.copy(submissionSuccess = null, errorMessage = null) }
    }
}
