package com.andreich.domain.pojo

import com.google.gson.annotations.SerializedName

data class PodcastsDto(

    @SerializedName("data"  ) val data  : ArrayList<PodcastDataDto> = arrayListOf(),
    @SerializedName("total" ) val total : Int?            = null
)
