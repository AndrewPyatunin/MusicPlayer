package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class ArtistDto(

    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("link") val link: String? = null,
    @SerializedName("share") val share: String? = null,
    @SerializedName("picture") val picture: String? = null,
    @SerializedName("picture_small") val pictureSmall: String? = null,
    @SerializedName("picture_medium") val pictureMedium: String? = null,
    @SerializedName("picture_big") val pictureBig: String? = null,
    @SerializedName("picture_xl") val pictureXl: String? = null,
    @SerializedName("radio") val radio: Boolean? = null,
    @SerializedName("tracklist") val tracklist: String? = null,
    @SerializedName("position") val position: Int? = null,
    @SerializedName("type") val type: String? = null
)
