package com.example.marvinveroes_endterm.data.local.db

import android.content.Context
import androidx.room.Room

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