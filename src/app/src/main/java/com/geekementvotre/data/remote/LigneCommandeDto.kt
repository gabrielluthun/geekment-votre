package com.geekementvotre.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LigneCommandeDto(
    val uuid_commande: String,
    val uuid_goodie: String,
    val quantite: Int,
    val prix_ttc: Double
)
