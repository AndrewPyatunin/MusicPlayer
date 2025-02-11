package com.andreich.data.network.pojo

import com.google.gson.annotations.SerializedName

data class RequestResultDto(

    @SerializedName("tracks"    ) val tracks    : TracksDto?    = TracksDto(),
    @SerializedName("albums"    ) val albums    : AlbumsDto?    = AlbumsDto(),
    @SerializedName("artists"   ) val artists   : ArtistsDto?   = ArtistsDto(),
    @SerializedName("playlists" ) val playlists : PlaylistsDto? = PlaylistsDto(),
    @SerializedName("podcasts"  ) val podcasts  : PodcastsDto?  = PodcastsDto()
)
