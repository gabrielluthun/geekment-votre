package com.geekementvotre.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.remote.ClientDto
import com.geekementvotre.data.remote.CommandeDto
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.client.call.body
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSheetConfig(
    val paymentIntent: String,
    val publishableKey: String,
    val customer: String? = null,
    val ephemeralKey: String? = null
)

@Serializable
data class CreatePaymentIntentRequest(
    val amount: Long,
    val currency: String = "eur"
)

sealed class CheckoutUiState {
    object Idle : CheckoutUiState()
    object Form : CheckoutUiState()
    object Loading : CheckoutUiState()
    data class Success(val config: PaymentSheetConfig) : CheckoutUiState()
    data class Error(val message: String) : CheckoutUiState()
}

data class CheckoutFormState(
    val nom: String = "",
    val prenom: String = "",
    val email: String = "",
    val telephone: String = "",
    val numeroRue: String = "",
    val nomRue: String = "",
    val codePostal: String = "",
    val nomVille: String = ""
) {
    val isEmailValid: Boolean get() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isNomValid: Boolean get() = nom.isNotEmpty() && nom.first().isUpperCase()
    val isPrenomValid: Boolean get() = prenom.isNotEmpty() && prenom.first().isUpperCase()
    val isTelephoneValid: Boolean get() {
        val digits = telephone.filter { it.isDigit() }
        return if (telephone.startsWith("+33")) digits.length == 11 else if (telephone.startsWith("0")) digits.length == 10 else false
    }
    val isNomRueValid: Boolean get() = 
        listOf("Rue", "Avenue", "Chemin", "Impasse", "Boulevard", "Ruelle", "Passerelle")
            .any { nomRue.startsWith(it, ignoreCase = false) }
    val isCodePostalValid: Boolean get() = codePostal.length == 5 && codePostal.all { it.isDigit() } && codePostal.take(2).toIntOrNull()?.let { it in 1..95 } == true

    val canSubmit: Boolean get() = isNomValid && isPrenomValid && isEmailValid && isTelephoneValid && isNomRueValid && isCodePostalValid && nomVille.isNotBlank()
}

class CheckoutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Idle)
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(CheckoutFormState())
    val formState: StateFlow<CheckoutFormState> = _formState.asStateFlow()

    fun updateNom(value: String) { _formState.update { it.copy(nom = value) } }
    fun updatePrenom(value: String) { _formState.update { it.copy(prenom = value) } }
    fun updateEmail(value: String) { _formState.update { it.copy(email = value) } }
    fun updateTelephone(value: String) { 
        val digitsOnly = value.filter { it.isDigit() || it == '+' }
        if (digitsOnly.length <= (if (digitsOnly.startsWith("+")) 12 else 10)) {
            _formState.update { it.copy(telephone = digitsOnly) } 
        }
    }
    fun updateNumeroRue(value: String) { 
        if (value.length <= 4 && value.all { it.isDigit() }) {
            _formState.update { it.copy(numeroRue = value) } 
        }
    }
    fun updateNomRue(value: String) { _formState.update { it.copy(nomRue = value) } }
    fun updateCodePostal(value: String) { 
        if (value.length <= 5 && value.all { it.isDigit() }) {
            _formState.update { it.copy(codePostal = value) } 
        }
    }
    fun updateNomVille(value: String) { _formState.update { it.copy(nomVille = value) } }

    fun startCheckout() {
        _uiState.value = CheckoutUiState.Form
    }

    fun prepareCheckout(amountInCents: Long) {
        viewModelScope.launch {
            _uiState.value = CheckoutUiState.Loading
            try {
                // 1. Gérer le client (Upsert)
                val currentForm = _formState.value
                val client = ClientDto(
                    nom = currentForm.nom,
                    prenom = currentForm.prenom,
                    email = currentForm.email,
                    telephone = currentForm.telephone,
                    numero_rue = currentForm.numeroRue.toIntOrNull(),
                    nom_rue = currentForm.nomRue,
                    code_postal = currentForm.codePostal,
                    nom_ville = currentForm.nomVille
                )

                SupabaseClient.client.postgrest["client"].upsert(
                    listOf(client),
                    request = { onConflict = "email" }
                )

                val uuidClient = SupabaseClient.client.postgrest["client"].select {
                    filter { eq("email", currentForm.email) }
                }.decodeSingle<ClientDto>().uuid_client ?: throw Exception("Client non trouvé")

                // 2. Créer la commande
                val commande = CommandeDto(
                    uuid_client = uuidClient,
                    montant_total = amountInCents / 100.0,
                    statut = "en_attente"
                )
                SupabaseClient.client.postgrest["commande"].insert(commande)

                // 3. Appeler la fonction Stripe
                val response = SupabaseClient.client.functions.invoke(
                    "stripe-paiement",
                    CreatePaymentIntentRequest(amountInCents)
                )
                
                val config = response.body<PaymentSheetConfig>()
                _uiState.value = CheckoutUiState.Success(config)
                
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "Error in checkout process", e)
                _uiState.value = CheckoutUiState.Error("Erreur : ${e.localizedMessage ?: "Connexion impossible"}")
            }
        }
    }

    fun resetState() {
        _uiState.value = CheckoutUiState.Idle
    }
}
