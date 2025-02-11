package com.andreich.data.entity

import androidx.room.Entity

@Entity("artistEntity")
data class ArtistEntity(
    val id: Int,
    val name: String,
    val picture: String? = null,
    val pictureBig: String? = null,
    val trackList: String
)
