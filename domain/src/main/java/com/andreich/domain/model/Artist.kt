package com.andreich.domain.model

data class Artist(
    val id: Int,
    val name: String,
    val picture: String? = null,
    val pictureBig: String? = null,
    val trackList: String
)
