package com.example.marvinveroes_endterm.data.remote.api

import com.example.marvinveroes_endterm.data.remote.model.RocketDto
import retrofit2.http.GET

/**
 * Interfaz que define los endpoints de la API de SpaceX
 */
interface SpaceXApi {
    @GET("v4/rockets")
    suspend fun getRockets(): List<RocketDto>
}