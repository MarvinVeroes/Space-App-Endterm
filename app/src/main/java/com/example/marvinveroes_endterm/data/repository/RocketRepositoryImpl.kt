package com.example.marvinveroes_endterm.data.repository

import android.content.Context
import com.example.marvinveroes_endterm.data.local.db.DatabaseModule
import com.example.marvinveroes_endterm.data.local.mapper.toDomain
import com.example.marvinveroes_endterm.data.remote.mapper.toEntity
import com.example.marvinveroes_endterm.data.remote.network.NetworkModule
import com.example.marvinveroes_endterm.domain.model.Rocket
import com.example.marvinveroes_endterm.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RocketRepositoryImpl(
    context: Context
) : RocketRepository {

    private val dao = DatabaseModule.getDatabase(context).rocketDao()
    private val api = NetworkModule.spaceXApi

    override fun observeRockets(query: String): Flow<List<Rocket>> {
        val source = if (query.isBlank()) dao.observeAll() else dao.searchByName(query)
        return source.map { list -> list.map { it.toDomain() } }
    }

    override suspend fun refreshRockets() {
        val remote = api.getRockets()
        val entities = remote.map { it.toEntity() }
        dao.insertAll(entities)
    }
}