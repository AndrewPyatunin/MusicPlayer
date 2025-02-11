package com.andreich.domain.pojo

import com.google.gson.annotations.SerializedName

data class TracksDto(

    @SerializedName("data"  ) val data  : ArrayList<TrackDataDto> = arrayListOf(),
    @SerializedName("total" ) val total : Int?            = null
)
