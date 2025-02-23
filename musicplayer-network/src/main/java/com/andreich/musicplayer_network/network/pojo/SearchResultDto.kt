package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class SearchResultDto(

    @SerializedName("data") val data: ArrayList<SearchTrackDto> = arrayListOf(),
    @SerializedName("total") val total: Int? = null,
    @SerializedName("next") val next: String? = null
)
