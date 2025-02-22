package com.andreich.musicplayer_feature.music_player.ui

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioTrack(
    val id: Long,
    val url: String,
    val uri: Uri? = null,
    val displayName: String,
    val artist: String,
    val data: String,
    val duration: Long,
    val title: String,
    val album: String,
    val picture: Uri? = null,
    val pictureBig: Uri? = null
): Parcelable
