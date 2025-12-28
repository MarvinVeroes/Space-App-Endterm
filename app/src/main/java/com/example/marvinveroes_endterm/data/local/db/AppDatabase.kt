package com.example.marvinveroes_endterm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvinveroes_endterm.data.local.dao.RocketDao
import com.example.marvinveroes_endterm.data.local.entity.RocketEntity

@Database(
    entities = [RocketEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rocketDao(): RocketDao
}