package com.example.marvinveroes_endterm.data.remote.network

import com.example.marvinveroes_endterm.data.remote.api.SpaceXApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Módulo de red que provee la configuración de Retrofit y OkHttpClient
 * para interactuar con la API de SpaceX.
 * Proporciona una instancia de SpaceXApi para realizar llamadas a la API.
 *
 */
object NetworkModule {

    private const val BASE_URL = "https://api.spacexdata.com/"

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val spaceXApi: SpaceXApi by lazy {
        retrofit.create(SpaceXApi::class.java)
    }
}