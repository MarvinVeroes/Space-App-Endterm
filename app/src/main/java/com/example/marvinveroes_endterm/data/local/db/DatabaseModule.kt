package com.example.marvinveroes_endterm.data.local.db

import android.content.Context
import androidx.room.Room

/**
 * Módulo de base de datos que proporciona una instancia de AppDatabase.
 * Utiliza Room para construir la base de datos local de la aplicación.
 */
object DatabaseModule {

    @Volatile private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "spaceapp.db"
            ).fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}