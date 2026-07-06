package com.geekementvotre.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CommandeDto(
    val uuid_commande: String? = null,
    val uuid_client: String,
    val montant_total: Double,
    val statut_paiement: String = "en_attente",
    val date_commande: String? = null // Géré par la DB (now()) généralement
)
