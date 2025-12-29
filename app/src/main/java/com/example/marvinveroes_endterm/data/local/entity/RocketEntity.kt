package com.example.marvinveroes_endterm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de base de datos que representa un cohete.
 * Contiene información detallada sobre el cohete.
 */
@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey val id: String,
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