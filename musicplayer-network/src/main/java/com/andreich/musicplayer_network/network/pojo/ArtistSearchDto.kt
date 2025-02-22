package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class ArtistSearchDto(

    val id: Long,
    val name: String,
    val link: String,
    val picture: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    val tracklist: String,
    val type: String,
)
