package com.andreich.data.entity

import androidx.room.Entity

@Entity("track")
data class TrackEntity(
    val id: Int,
    val title: String,
    val artistName: String,
    val albumEntity: AlbumEntity? = null,
    val artistEntity: ArtistEntity? = null,
    val cover: String? = null,
    val filePath: String,
    val duration: Int = 0
)
