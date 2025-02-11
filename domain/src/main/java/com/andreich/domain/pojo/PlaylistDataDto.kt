package com.andreich.domain.pojo

import com.google.gson.annotations.SerializedName

data class PlaylistDataDto(

    @SerializedName("id"             ) val id            : Int?     = null,
    @SerializedName("title"          ) val title         : String?  = null,
    @SerializedName("public"         ) val public        : Boolean? = null,
    @SerializedName("nb_tracks"      ) val nbTracks      : Int?     = null,
    @SerializedName("link"           ) val link          : String?  = null,
    @SerializedName("picture"        ) val picture       : String?  = null,
    @SerializedName("picture_small"  ) val pictureSmall  : String?  = null,
    @SerializedName("picture_medium" ) val pictureMedium : String?  = null,
    @SerializedName("picture_big"    ) val pictureBig    : String?  = null,
    @SerializedName("picture_xl"     ) val pictureXl     : String?  = null,
    @SerializedName("checksum"       ) val checksum      : String?  = null,
    @SerializedName("tracklist"      ) val tracklist     : String?  = null,
    @SerializedName("creation_date"  ) val creationDate  : String?  = null,
    @SerializedName("md5_image"      ) val md5Image      : String?  = null,
    @SerializedName("picture_type"   ) val pictureType   : String?  = null,
    @SerializedName("user"           ) val user          : UserDto?    = UserDto(),
    @SerializedName("type"           ) val type          : String?  = null
)