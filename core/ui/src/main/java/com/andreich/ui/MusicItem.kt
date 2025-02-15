package com.andreich.ui

import android.net.Uri

data class MusicItem(
    val id: Int,
    val url: String,
    val uri: Uri? = null,
    val displayName: String,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String,
    val album: String
)
