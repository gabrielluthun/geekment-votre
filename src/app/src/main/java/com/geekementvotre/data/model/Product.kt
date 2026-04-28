package com.geekementvotre.data.model

data class Product(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val price: String, // Formaté pour l'affichage (ex: "5.99€")
    val imageUrl: String? = null
)
