package com.andreich.musicplayer.ui

import android.net.Uri

data class AudioTrack(
    val id: Long,
    val url: String,
    val uri: Uri? = null,
    val displayName: String,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String,
    val album: String
)
