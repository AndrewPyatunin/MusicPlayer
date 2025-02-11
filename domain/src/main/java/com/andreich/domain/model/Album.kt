package com.andreich.domain.model

data class Album(
    val id: Int,
    val title: String,
    val cover: String? = null,
    val coverBig: String? = null,
    val trackList: String
)
