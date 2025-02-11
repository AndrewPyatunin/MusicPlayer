package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class PlaylistsDto(

    @SerializedName("data"  ) val data  : ArrayList<PlaylistDataDto> = arrayListOf(),
    @SerializedName("total" ) val total : Int?            = null
)
