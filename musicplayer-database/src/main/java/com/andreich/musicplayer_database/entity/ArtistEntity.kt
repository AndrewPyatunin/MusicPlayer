package com.andreich.musicplayer_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("artistEntity")
data class ArtistEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val picture: String? = null,
    val pictureBig: String? = null,
    val trackList: String
)
