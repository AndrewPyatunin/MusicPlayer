package com.andreich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val id: Long,
    val title: String,
    val album: Album? = null,
    val artist: Artist? = null,
    val cover: String? = null,
    val filePath: String,
    val duration: Long = 0,
    val url: String? = null,
    val coverBig: String? = null
)
