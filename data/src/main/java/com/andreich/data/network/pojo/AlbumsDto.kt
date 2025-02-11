package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class AlbumsDto(


    @SerializedName("data"  ) val data  : ArrayList<AlbumDto> = arrayListOf(),
    @SerializedName("total" ) val total : Int?            = null
)
