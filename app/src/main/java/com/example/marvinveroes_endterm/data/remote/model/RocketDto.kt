package com.example.marvinveroes_endterm.data.remote.model

import com.google.gson.annotations.SerializedName

data class RocketDto(
    val id: String,
    val name: String,
    @SerializedName("flickr_images") val flickrImages: List<String>?
)