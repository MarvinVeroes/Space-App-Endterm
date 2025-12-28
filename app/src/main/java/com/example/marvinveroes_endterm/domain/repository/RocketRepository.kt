package com.example.marvinveroes_endterm.domain.repository

import com.example.marvinveroes_endterm.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun observeRockets(query: String): Flow<List<Rocket>>
    suspend fun refreshRockets()
}