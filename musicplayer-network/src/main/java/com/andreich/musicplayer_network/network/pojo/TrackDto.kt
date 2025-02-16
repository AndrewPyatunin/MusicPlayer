package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class TrackDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("title_short") var titleShort: String? = null,
    @SerializedName("title_version") var titleVersion: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("explicit_lyrics") var explicitLyrics: Boolean? = null,
    @SerializedName("explicit_content_lyrics") var explicitContentLyrics: Int? = null,
    @SerializedName("explicit_content_cover") var explicitContentCover: Int? = null,
    @SerializedName("preview") var preview: String? = null,
    @SerializedName("md5_image") var md5Image: String? = null,
    @SerializedName("position") var position: Int? = null,
    @SerializedName("artist") var artist: ArtistDto? = ArtistDto(),
    @SerializedName("album") var album: AlbumDataDto? = AlbumDataDto(),
    @SerializedName("type") var type: String? = null
)