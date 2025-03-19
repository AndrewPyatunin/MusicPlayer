package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class SearchTrackDto(

    val id: Long,
    val readable: Boolean = false,
    val title: String,
    @SerializedName("title_short")
    val titleShort: String = "",
    @SerializedName("title_version")
    val titleVersion: String = "",
    val link: String = "",
    val duration: Long = 0,
    val rank: Long = 0,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean = false,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Long = 0,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Long = 0,
    val preview: String = "",
    @SerializedName("md5_image")
    val md5Image: String = "",
    val artist: ArtistSearchDto,
    val album: AlbumSearchDto,
    val type: String = "",
)
