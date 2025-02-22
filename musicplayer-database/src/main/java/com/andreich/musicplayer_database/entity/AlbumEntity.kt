package com.andreich.musicplayer_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("album")
data class AlbumEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val cover: String? = null,
    val coverBig: String? = null,
    val trackList: String
)
