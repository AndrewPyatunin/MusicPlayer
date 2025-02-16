package com.andreich.data

import android.net.Uri
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Audio(
    val uri: Uri,
    val displayName: String,
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String,
    val album: String
) : Parcelable
