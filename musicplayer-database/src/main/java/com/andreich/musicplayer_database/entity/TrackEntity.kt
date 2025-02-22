package com.andreich.musicplayer_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("track")
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val artistName: String,
    val albumEntity: AlbumEntity? = null,
    val artistEntity: ArtistEntity? = null,
    val cover: String? = null,
    val filePath: String,
    val duration: Long = 0
)
