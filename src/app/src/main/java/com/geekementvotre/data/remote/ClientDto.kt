package com.geekementvotre.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ClientDto(
    val uuid_client: String? = null,
    val nom: String? = null,
    val prenom: String? = null,
    val email: String? = null,
    val telephone: String? = null,
    val numero_rue: Int? = null,
    val nom_rue: String? = null,
    val code_postal: String? = null,
    val nom_ville: String? = null,
    val date_naissance: String? = null,
    val genre: String? = null
)
