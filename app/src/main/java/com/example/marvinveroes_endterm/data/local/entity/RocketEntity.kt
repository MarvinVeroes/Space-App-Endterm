package com.example.marvinveroes_endterm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?
)