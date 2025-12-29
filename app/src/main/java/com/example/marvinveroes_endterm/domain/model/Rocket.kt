package com.example.marvinveroes_endterm.domain.model

/** Modelo de datos que representa un cohete
 * Contiene información detallada sobre el cohete.
 */
data class Rocket(
    val id: String,
    val name: String,


    val imageUrl: String?,

    val country: String,
    val firstFlight: String,
    val successRatePct: Int,
    val stages: Int,
    val costPerLaunch: Long,
    val description: String,
    val wikipedia: String?,
    val active: Boolean
)