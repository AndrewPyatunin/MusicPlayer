package com.andreich.data.entity

import androidx.room.Entity

@Entity("album")
data class AlbumEntity(
    val id: Int,
    val title: String,
    val cover: String? = null,
    val coverBig: String? = null,
    val trackList: String
)
