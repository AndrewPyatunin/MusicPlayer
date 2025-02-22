package com.andreich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AudioModel(
    val uri: String,
    val displayName: String,
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Long,
    val title: String,
    val album: String,
    val cover: String? = null
)
