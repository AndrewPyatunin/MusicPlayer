package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class AlbumDto(

    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("link") val link: String? = null,
    @SerializedName("cover") val cover: String? = null,
    @SerializedName("cover_small") val coverSmall: String? = null,
    @SerializedName("cover_medium") val coverMedium: String? = null,
    @SerializedName("cover_big") val coverBig: String? = null,
    @SerializedName("cover_xl") val coverXl: String? = null,
    @SerializedName("md5_image") val md5Image: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("tracklist") val tracklist: String? = null,
    @SerializedName("type") val type: String? = null
)
