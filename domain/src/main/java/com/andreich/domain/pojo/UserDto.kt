package com.andreich.domain.pojo

import com.google.gson.annotations.SerializedName

data class UserDto(

    @SerializedName("id"        ) val id        : Int?    = null,
    @SerializedName("name"      ) val name      : String? = null,
    @SerializedName("tracklist" ) val tracklist : String? = null,
    @SerializedName("type"      ) val type      : String? = null
)
