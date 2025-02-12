package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class ArtistsDto(

    @SerializedName("data") val data: ArrayList<ArtistDto> = arrayListOf(),
    @SerializedName("total") val total: Int? = null
)
