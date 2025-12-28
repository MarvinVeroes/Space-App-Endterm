package com.example.marvinveroes_endterm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.marvinveroes_endterm.data.local.entity.RocketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RocketDao {

    @Query("SELECT * FROM rockets ORDER BY name ASC")
    fun observeAll(): Flow<List<RocketEntity>>

    @Query("SELECT * FROM rockets WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchByName(query: String): Flow<List<RocketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RocketEntity>)

    @Query("DELETE FROM rockets")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(items: List<RocketEntity>) {
        clearAll()
        insertAll(items)
    }
}