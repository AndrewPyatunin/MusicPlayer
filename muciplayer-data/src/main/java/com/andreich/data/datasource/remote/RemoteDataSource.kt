package com.andreich.data.datasource.remote

import com.andreich.musicplayer_network.network.pojo.SearchResultDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TracksDto

interface RemoteDataSource {

    suspend fun getChartTracks(): TracksDto

    suspend fun getTrack(id: Long): TrackDetailDto

    suspend fun searchTrack(query: String?): SearchResultDto
}