package com.geekementvotre.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val image_url: String? = null
)
