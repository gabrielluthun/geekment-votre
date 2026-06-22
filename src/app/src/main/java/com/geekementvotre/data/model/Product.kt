package com.geekementvotre.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("uuid_goodie")
    val id: String? = null,
    @SerialName("nom")
    val name: String? = null,
    val description: String? = null,
    @SerialName("prix_ttc")
    val price: Double? = null,
    @SerialName("categorie")
    val category: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null
)
