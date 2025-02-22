package com.andreich.musicplayer_network.network.pojo

import com.google.gson.annotations.SerializedName

data class TrackDetailDto(

    val id: String,
    val readable: Boolean,
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    val isrc: String,
    val link: String,
    val share: String,
    val duration: String,
    @SerializedName("track_position")
    val trackPosition: Long,
    @SerializedName("disk_number")
    val diskNumber: Long,
    val rank: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Long,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Long,
    val preview: String,
    val bpm: Long,
    val gain: Double,
    @SerializedName("available_countries")
    val availableCountries: List<String>,
    val contributors: List<ContributorDto>,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("track_token")
    val trackToken: String,
    val artist: ArtistDetailDto,
    val album: AlbumDetailDto,
    val type: String,
)
