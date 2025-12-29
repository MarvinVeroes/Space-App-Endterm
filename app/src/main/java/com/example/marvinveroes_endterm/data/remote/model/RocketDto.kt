package com.example.marvinveroes_endterm.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que representa un cohete recibido de la API
 * Contiene información detallada sobre el cohete.
 */
data class RocketDto(
    val id: String,
    val name: String,
    @SerializedName("flickr_images") val flickrImages: List<String>?,

    val country: String,

    @SerializedName("first_flight")
    val firstFlight: String,

    @SerializedName("success_rate_pct")
    val successRatePct: Int,

    val stages: Int,

    @SerializedName("cost_per_launch")
    val costPerLaunch: Long,

    val description: String,
    val wikipedia: String?,

    @SerializedName("active")
    val active: Boolean
)