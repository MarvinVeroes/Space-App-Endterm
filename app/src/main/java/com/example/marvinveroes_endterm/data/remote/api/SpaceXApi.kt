package com.example.marvinveroes_endterm.data.remote.api

import com.example.marvinveroes_endterm.data.remote.model.RocketDto
import retrofit2.http.GET

interface SpaceXApi {
    @GET("v4/rockets")
    suspend fun getRockets(): List<RocketDto>
}