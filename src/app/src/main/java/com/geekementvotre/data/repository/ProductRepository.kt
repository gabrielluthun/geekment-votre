package com.geekementvotre.data.repository

import com.geekementvotre.data.model.Product
import com.geekementvotre.data.remote.ProductDto
import com.geekementvotre.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {

    suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        try {
            val result = SupabaseClient.client.postgrest["products"]
                .select()
                .decodeList<ProductDto>()

            result.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList() // Gestion d'erreur simplifiée pour le moment
        }
    }

    private fun ProductDto.toDomain(): Product {
        return Product(
            id = id,
            category = category,
            name = name,
            description = description,
            price = "${String.format("%.2f", price)}€",
            imageUrl = image_url
        )
    }
}
