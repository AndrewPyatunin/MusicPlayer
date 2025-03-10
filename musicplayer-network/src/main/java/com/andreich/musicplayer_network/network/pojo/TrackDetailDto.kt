package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class TrackDetailDto(

    val id: String,
    val readable: Boolean = false,
    val title: String = "",
    @SerializedName("title_short")
    val titleShort: String = "",
    @SerializedName("title_version")
    val titleVersion: String = "",
    val isrc: String = "",
    val link: String = "",
    val share: String = "",
    val duration: String = "",
    @SerializedName("track_position")
    val trackPosition: Long = 0,
    @SerializedName("disk_number")
    val diskNumber: Long = 0,
    val rank: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean = false,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Long = 0,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Long = 0,
    val preview: String = "",
    val bpm: Long = 0,
    val gain: Double = 0.0,
    @SerializedName("available_countries")
    val availableCountries: List<String> = emptyList(),
    val contributors: List<ContributorDto> = emptyList(),
    @SerializedName("md5_image")
    val md5Image: String = "",
    @SerializedName("track_token")
    val trackToken: String = "",
    val artist: ArtistDetailDto? = null,
    val album: AlbumDetailDto? = null,
    val type: String = "",
)
