package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class PodcastDataDto(
    @SerializedName("id"             ) val id            : Int?     = null,
    @SerializedName("title"          ) val title         : String?  = null,
    @SerializedName("description"    ) val description   : String?  = null,
    @SerializedName("available"      ) val available     : Boolean? = null,
    @SerializedName("fans"           ) val fans          : Int?     = null,
    @SerializedName("link"           ) val link          : String?  = null,
    @SerializedName("share"          ) val share         : String?  = null,
    @SerializedName("picture"        ) val picture       : String?  = null,
    @SerializedName("picture_small"  ) val pictureSmall  : String?  = null,
    @SerializedName("picture_medium" ) val pictureMedium : String?  = null,
    @SerializedName("picture_big"    ) val pictureBig    : String?  = null,
    @SerializedName("picture_xl"     ) val pictureXl     : String?  = null,
    @SerializedName("type"           ) val type          : String?  = null
)
