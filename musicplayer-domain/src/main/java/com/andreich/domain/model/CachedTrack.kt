package com.andreich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CachedTrack(
    val id: String,
    val url: String
)
