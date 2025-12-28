package com.example.marvinveroes_endterm.data.remote.mapper

import com.example.marvinveroes_endterm.data.local.entity.RocketEntity
import com.example.marvinveroes_endterm.data.remote.model.RocketDto

fun RocketDto.toEntity(): RocketEntity =
    RocketEntity(
        id = id,
        name = name,
        imageUrl = flickrImages?.firstOrNull(),

        country = country,
        firstFlight = firstFlight,
        successRatePct = successRatePct,
        stages = stages,
        costPerLaunch = costPerLaunch,
        description = description,
        wikipedia = wikipedia
    )