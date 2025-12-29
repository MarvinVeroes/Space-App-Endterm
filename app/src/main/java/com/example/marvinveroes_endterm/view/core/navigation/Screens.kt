package com.example.marvinveroes_endterm.view.core.navigation

import kotlinx.serialization.Serializable

// Definicion de las pantallas de la aplicacion para la navegacion
@Serializable
object Splash

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Serializable
data class RocketDetail(val rocketId: String)
