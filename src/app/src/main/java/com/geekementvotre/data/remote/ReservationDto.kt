package com.geekementvotre.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ReservationDto(
    val uuid_reservation: String? = null,
    val uuid_client: String,
    val date_session: String, // Format "YYYY-MM-DD"
    val creneau_horaire: String,
    val numero_rue_session: Int? = null,
    val nom_rue_session: String? = null,
    val code_postal_session: String? = null,
    val nom_ville_session: String? = null,
    val a_domicile_client: Boolean = true,
    val message_demande: String? = null,
    val statut_reservation: String = "en_attente",
    val id_google_calendar: String? = null,
    val nb_joueurs: Int,
    val type_jeu: String
)
