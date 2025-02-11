package com.andreich.domain.model

data class Track(
    val id: Int,
    val title: String,
    val album: Album,
    val artist: Artist,
    val cover: String? = null,
    val filePath: String,
    val duration: Int = 0
)
