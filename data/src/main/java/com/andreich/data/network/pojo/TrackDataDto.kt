package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class TrackDataDto(

    @SerializedName("id"              ) val id             : Int?     = null,
    @SerializedName("title"           ) val title          : String?  = null,
    @SerializedName("link"            ) val link           : String?  = null,
    @SerializedName("cover"           ) val cover          : String?  = null,
    @SerializedName("cover_small"     ) val coverSmall     : String?  = null,
    @SerializedName("cover_medium"    ) val coverMedium    : String?  = null,
    @SerializedName("cover_big"       ) val coverBig       : String?  = null,
    @SerializedName("cover_xl"        ) val coverXl        : String?  = null,
    @SerializedName("md5_image"       ) val md5Image       : String?  = null,
    @SerializedName("record_type"     ) val recordType     : String?  = null,
    @SerializedName("tracklist"       ) val tracklist      : String?  = null,
    @SerializedName("explicit_lyrics" ) val explicitLyrics : Boolean? = null,
    @SerializedName("position"        ) val position       : Int?     = null,
    @SerializedName("artistEntity"          ) val artist         : ArtistDto?  = ArtistDto(),
    @SerializedName("type"            ) val type           : String?  = null
)
