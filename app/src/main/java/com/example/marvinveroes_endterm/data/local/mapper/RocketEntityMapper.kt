package com.example.marvinveroes_endterm.data.local.mapper

import com.example.marvinveroes_endterm.data.local.entity.RocketEntity
import com.example.marvinveroes_endterm.domain.model.Rocket

fun RocketEntity.toDomain(): Rocket =
    Rocket(
        id = id,
        name = name,
        imageUrl = imageUrl
    )