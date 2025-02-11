package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class TrackDetailDto(

    @SerializedName("id"                      ) val id                    : String?                 = null,
    @SerializedName("readable"                ) val readable              : Boolean?                = null,
    @SerializedName("title"                   ) val title                 : String?                 = null,
    @SerializedName("title_short"             ) val titleShort            : String?                 = null,
    @SerializedName("title_version"           ) val titleVersion          : String?                 = null,
    @SerializedName("isrc"                    ) val isrc                  : String?                 = null,
    @SerializedName("link"                    ) val link                  : String?                 = null,
    @SerializedName("share"                   ) val share                 : String?                 = null,
    @SerializedName("duration"                ) val duration              : String?                 = null,
    @SerializedName("track_position"          ) val trackPosition         : Int?                    = null,
    @SerializedName("disk_number"             ) val diskNumber            : Int?                    = null,
    @SerializedName("rank"                    ) val rank                  : String?                 = null,
    @SerializedName("release_date"            ) val releaseDate           : String?                 = null,
    @SerializedName("explicit_lyrics"         ) val explicitLyrics        : Boolean?                = null,
    @SerializedName("explicit_content_lyrics" ) val explicitContentLyrics : Int?                    = null,
    @SerializedName("explicit_content_cover"  ) val explicitContentCover  : Int?                    = null,
    @SerializedName("preview"                 ) val preview               : String?                 = null,
    @SerializedName("bpm"                     ) val bpm                   : Int?                    = null,
    @SerializedName("gain"                    ) val gain                  : Double?                 = null,
    @SerializedName("available_countries"     ) val availableCountries    : ArrayList<String>       = arrayListOf(),
    @SerializedName("contributors"            ) val contributors          : ArrayList<ContributorDto> = arrayListOf(),
    @SerializedName("md5_image"               ) val md5Image              : String?                 = null,
    @SerializedName("track_token"             ) val trackToken            : String?                 = null,
    @SerializedName("artistEntity"            ) val artist                : ArtistDto?              = ArtistDto(),
    @SerializedName("albumEntity"             ) val album                 : AlbumDto?               = AlbumDto(),
    @SerializedName("type"                    ) val type                  : String?                 = null
)
