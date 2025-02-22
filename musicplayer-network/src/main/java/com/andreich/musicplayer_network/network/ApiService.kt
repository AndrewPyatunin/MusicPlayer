package com.andreich.musicplayer_network.network

import com.andreich.musicplayer_network.network.pojo.SearchResultDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TracksDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val SEARCH_URL = "search"
private const val CHART_URL = "chart/0/tracks"
private const val TRACK_URL = "track"
private const val SEARCH_QUERY = "q"
private const val TRACK_ID = "trackId"

interface ApiService {

    @GET(CHART_URL)
    suspend fun getChartTracks(): TracksDto

    @GET(SEARCH_URL)
    suspend fun searchTrack(@Query(SEARCH_QUERY) query: String?): SearchResultDto

    @GET("$TRACK_URL/{$TRACK_ID}")
    suspend fun getTrack(@Path(TRACK_ID) trackId: Long): TrackDetailDto
}