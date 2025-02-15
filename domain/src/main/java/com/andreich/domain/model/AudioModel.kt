package com.andreich.domain.model

data class AudioModel(
    val uri: String,
    val displayName: String,
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String,
    val album: String
)
