package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class AlbumDataDto(

    @SerializedName("id") var id: Long? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("cover") var cover: String? = null,
    @SerializedName("cover_small") var coverSmall: String? = null,
    @SerializedName("cover_medium") var coverMedium: String? = null,
    @SerializedName("cover_big") var coverBig: String? = null,
    @SerializedName("cover_xl") var coverXl: String? = null,
    @SerializedName("md5_image") var md5Image: String? = null,
    @SerializedName("tracklist") var tracklist: String? = null,
    @SerializedName("type") var type: String? = null
)
