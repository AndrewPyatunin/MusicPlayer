package com.andreich.domain.pojo

import com.google.gson.annotations.SerializedName

data class ArtistsDto(

    @SerializedName("data"  ) val data  : ArrayList<ArtistDataDto> = arrayListOf(),
    @SerializedName("total" ) val total : Int?            = null
)
