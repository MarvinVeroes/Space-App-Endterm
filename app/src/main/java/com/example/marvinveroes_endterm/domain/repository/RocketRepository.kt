package com.example.marvinveroes_endterm.domain.repository

import com.example.marvinveroes_endterm.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz del repositorio de cohetes
 * Define las operaciones para obtener y actualizar datos de cohetes.
 */
interface RocketRepository {
    fun observeRockets(query: String): Flow<List<Rocket>>
    suspend fun refreshRockets()

    fun observeRocketById(id: String): Flow<Rocket?>
}