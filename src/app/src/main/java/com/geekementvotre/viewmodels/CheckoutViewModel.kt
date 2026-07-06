package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import android.util.Log

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
    object Loading : CheckoutUiState()
    data class Success(val config: PaymentSheetConfig) : CheckoutUiState()
    data class Error(val message: String) : CheckoutUiState()
}

class CheckoutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Idle)
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun prepareCheckout(amountInCents: Long) {
        viewModelScope.launch {
            _uiState.value = CheckoutUiState.Loading
            try {
                val response = SupabaseClient.client.functions.invoke(
                    "stripe-paiement",
                    CreatePaymentIntentRequest(amountInCents)
                )
                
                // On décode la réponse en PaymentSheetConfig
                val config = response.body<PaymentSheetConfig>()
                _uiState.value = CheckoutUiState.Success(config)
                
            } catch (e: Exception) {
                Log.e("CheckoutViewModel", "Error preparing checkout", e)
                _uiState.value = CheckoutUiState.Error("Erreur : ${e.localizedMessage ?: "Connexion impossible"}")
            }
        }
    }

    fun resetState() {
        _uiState.value = CheckoutUiState.Idle
    }
}
