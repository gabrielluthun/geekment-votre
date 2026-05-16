package com.geekementvotre.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ReservationDto(
    val nom: String,
    val prenom: String,
    val email: String,
    val date_de_naissance: String,
    val genre: String,
    val selected_date: String,
    val nb_joueurs: Int,
    val selected_time: String,
    val selected_game: String,
    val adresse_session: String? = null,
    val message_specifique: String? = null,
    val created_at: String? = null
)
