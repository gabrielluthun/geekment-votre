package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductFromSupabase(
    @SerialName("uuid_goodie")
    val id: String,
    @SerialName("nom")
    val name: String,
    val description: String,
    @SerialName("prix_ttc")
    val price: Double,
    @SerialName("categorie")
    val category: String,
    @SerialName("image_url")
    val imageUrl: String? = null
)

sealed class ShopUiState {
    object Loading : ShopUiState()
    data class Success(val products: List<ProductFromSupabase>) : ShopUiState()
    data class Error(val message: String) : ShopUiState()
}

class ShopViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ShopUiState.Loading
            try {
                val response = SupabaseClient.client.postgrest["goodie"]
                    .select()
                    .decodeList<ProductFromSupabase>()
                
                _uiState.value = ShopUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = ShopUiState.Error(e.message ?: "Erreur inconnue")
            }
        }
    }
}
