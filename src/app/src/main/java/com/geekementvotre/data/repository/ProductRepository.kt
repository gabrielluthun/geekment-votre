package com.geekementvotre.data.repository

import com.geekementvotre.data.model.Product
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class ProductRepository {

    suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        try {
            val result = SupabaseClient.client.postgrest["goodie"]
                .select()
                .decodeList<Product>()

            result
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur lors de la récupération des produits", e)
            emptyList()
        }
    }
}
