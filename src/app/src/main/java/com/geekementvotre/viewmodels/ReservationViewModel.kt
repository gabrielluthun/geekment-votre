package com.geekementvotre.viewmodels

import android.util.Log
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

    val isNomValid: Boolean get() = nom.isNotBlank()
    val isPrenomValid: Boolean get() = prenom.isNotBlank()
    
    val isTelephoneValid: Boolean get() {
        val digits = telephone.filter { it.isDigit() }
        return if (telephone.startsWith("+33")) {
            digits.length == 11
        } else if (telephone.startsWith("0")) {
            digits.length == 10
        } else false
    }

    val isNomRueValid: Boolean get() = 
        listOf("Rue", "Avenue", "Chemin", "Impasse", "Boulevard", "Ruelle", "Passerelle")
            .any { nomRue.startsWith(it, ignoreCase = true) }

    val isCodePostalValid: Boolean get() = 
        codePostal.length == 5 && 
        codePostal.all { it.isDigit() } && 
        codePostal.take(2).toIntOrNull()?.let { it in 1..95 } == true

    val isNomRueSessionValid: Boolean get() = 
        aDomicileClient || listOf("Rue", "Avenue", "Chemin", "Impasse", "Boulevard", "Ruelle", "Passerelle")
            .any { nomRueSession.startsWith(it, ignoreCase = true) }

    val isCodePostalSessionValid: Boolean get() = 
        aDomicileClient || (
            codePostalSession.length == 5 && 
            codePostalSession.all { it.isDigit() } && 
            codePostalSession.take(2).toIntOrNull()?.let { it in 1..95 } == true
        )

    val isNbJoueursValid: Boolean get() = 
        nbJoueurs.isNotEmpty() && nbJoueurs.length <= 2 && nbJoueurs.all { it.isDigit() } && nbJoueurs.toIntOrNull()?.let { it > 0 } == true

    val isMessageClean: Boolean get() = !containsOffensiveLanguage(messageDemande)

    val canSubmit: Boolean
        get() = isNomValid &&
                isPrenomValid &&
                isEmailValid &&
                isTelephoneValid &&
                isNomRueValid &&
                isCodePostalValid &&
                dateSession != null && 
                isNbJoueursValid && 
                creneauHoraire.isNotBlank() && 
                typeJeu.isNotBlank() &&
                isMessageClean &&
                (aDomicileClient || (isNomRueSessionValid && nomVilleSession.isNotBlank() && isCodePostalSessionValid))

    private fun containsOffensiveLanguage(text: String): Boolean {
        val blacklist = listOf("merde", "con", "salaud", "pute", "enculé", "connard", "chiasse")
        return blacklist.any { text.contains(it, ignoreCase = true) }
    }
}

class ReservationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    fun updateNom(value: String) { _uiState.update { it.copy(nom = value) } }
    fun updatePrenom(value: String) { _uiState.update { it.copy(prenom = value) } }
    fun updateEmail(value: String) { _uiState.update { it.copy(email = value) } }
    fun updateTelephone(value: String) { 
        val digitsOnly = value.filter { it.isDigit() || it == '+' }
        // On limite à 10 chiffres si ça commence par 0, ou 12 caractères si +33 (ex: +33612345678)
        val maxLength = if (digitsOnly.startsWith("+")) 12 else 10
        if (digitsOnly.length <= maxLength) {
            _uiState.update { it.copy(telephone = digitsOnly) } 
        }
    }
    fun updateNumeroRue(value: String) { 
        if (value.length <= 4 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(numeroRue = value) } 
        }
    }
    fun updateNomRue(value: String) { _uiState.update { it.copy(nomRue = value) } }
    fun updateCodePostal(value: String) { 
        if (value.length <= 5 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(codePostal = value) } 
        }
    }
    fun updateNomVille(value: String) { _uiState.update { it.copy(nomVille = value) } }
    fun updateDateNaissance(value: LocalDate?) { _uiState.update { it.copy(dateNaissance = value) } }
    fun updateGenre(value: String) { _uiState.update { it.copy(genre = value) } }

    fun updateDateSession(value: LocalDate?) { _uiState.update { it.copy(dateSession = value) } }
    fun updateCreneauHoraire(value: String) { _uiState.update { it.copy(creneauHoraire = value) } }
    fun updateTypeJeu(value: String) { _uiState.update { it.copy(typeJeu = value) } }
    fun updateNbJoueurs(value: String) { 
        if (value.length <= 2 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(nbJoueurs = value) } 
        }
    }

    fun updateADomicileClient(value: Boolean) { _uiState.update { it.copy(aDomicileClient = value) } }
    fun updateNumeroRueSession(value: String) { 
        if (value.length <= 4 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(numeroRueSession = value) } 
        }
    }
    fun updateNomRueSession(value: String) { _uiState.update { it.copy(nomRueSession = value) } }
    fun updateCodePostalSession(value: String) { 
        if (value.length <= 5 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(codePostalSession = value) } 
        }
    }
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
                    // Si domicile client, on copie l'adresse de facturation
                    numero_rue_session = if (currentState.aDomicileClient) currentState.numeroRue.toIntOrNull() else currentState.numeroRueSession.toIntOrNull(),
                    nom_rue_session = if (currentState.aDomicileClient) currentState.nomRue.ifBlank { null } else currentState.nomRueSession.ifBlank { null },
                    code_postal_session = if (currentState.aDomicileClient) currentState.codePostal.ifBlank { null } else currentState.codePostalSession.ifBlank { null },
                    nom_ville_session = if (currentState.aDomicileClient) currentState.nomVille.ifBlank { null } else currentState.nomVilleSession.ifBlank { null },
                    a_domicile_client = currentState.aDomicileClient,
                    message_demande = currentState.messageDemande.ifBlank { null },
                    nb_joueurs = currentState.nbJoueurs.toIntOrNull() ?: 0,
                    type_jeu = currentState.typeJeu
                )

                SupabaseClient.client.postgrest["reservation"].insert(reservation)
                
                // 3. Envoi du mail de confirmation (Admin + Client)
                val lieuComplet = if (currentState.aDomicileClient) {
                    "${currentState.numeroRue} ${currentState.nomRue}, ${currentState.codePostal} ${currentState.nomVille}"
                } else {
                    "${currentState.numeroRueSession} ${currentState.nomRueSession}, ${currentState.codePostalSession} ${currentState.nomVilleSession}"
                }

                com.geekementvotre.data.remote.ResendService.sendReservationEmail(
                    nom = "${currentState.prenom} ${currentState.nom}",
                    userEmail = currentState.email,
                    dateSession = currentState.dateSession?.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
                    creneau = currentState.creneauHoraire,
                    nbJoueurs = currentState.nbJoueurs.toIntOrNull() ?: 0,
                    typeJeu = currentState.typeJeu,
                    lieu = lieuComplet,
                    message = currentState.messageDemande
                )

                _uiState.value = ReservationUiState(submissionSuccess = true)
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Erreur lors de la réservation", e)
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
