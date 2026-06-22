package com.geekementvotre.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekementvotre.data.model.Product
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ShopUiState {
    data object Loading : ShopUiState()
    data class Success(val products: List<Product>) : ShopUiState()
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
                    .decodeList<Product>()
                
                _uiState.value = ShopUiState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = ShopUiState.Error(e.message ?: "Erreur inconnue")
            }
        }
    }
}
