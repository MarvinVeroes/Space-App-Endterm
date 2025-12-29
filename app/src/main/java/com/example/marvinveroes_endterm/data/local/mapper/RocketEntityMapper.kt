package com.example.marvinveroes_endterm.data.local.mapper

import com.example.marvinveroes_endterm.data.local.entity.RocketEntity
import com.example.marvinveroes_endterm.domain.model.Rocket

/**
 * Mapea un RocketEntity a Rocket
 */
fun RocketEntity.toDomain(): Rocket =
    Rocket(
        id = id,
        name = name,
        imageUrl = imageUrl,

        country = country,
        firstFlight = firstFlight,
        successRatePct = successRatePct,
        stages = stages,
        costPerLaunch = costPerLaunch,
        description = description,
        wikipedia = wikipedia,
        active = active
    )