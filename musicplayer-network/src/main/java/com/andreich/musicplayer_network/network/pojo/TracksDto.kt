package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class TracksDto(

    @SerializedName("data") val data: ArrayList<TrackDto> = arrayListOf(),
    @SerializedName("total") val total: Int? = null
)
