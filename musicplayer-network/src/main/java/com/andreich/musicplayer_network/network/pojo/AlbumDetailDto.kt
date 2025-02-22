package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class AlbumDetailDto(
    val id: String,
    val title: String,
    val link: String,
    val cover: String,
    @SerializedName("cover_small")
    val coverSmall: String,
    @SerializedName("cover_medium")
    val coverMedium: String,
    @SerializedName("cover_big")
    val coverBig: String,
    @SerializedName("cover_xl")
    val coverXl: String,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val tracklist: String,
    val type: String,
)
