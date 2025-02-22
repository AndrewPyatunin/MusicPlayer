package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class SearchTrackDto(

    val id: Long,
    val readable: Boolean,
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    val link: String,
    val duration: Long,
    val rank: Long,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Long,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Long,
    val preview: String,
    @SerializedName("md5_image")
    val md5Image: String,
    val artist: ArtistSearchDto,
    val album: AlbumSearchDto,
    val type: String,
)
