package com.andreich.data.datasource.remote

import com.andreich.data.network.pojo.SearchResultDto
import com.andreich.data.network.pojo.TrackDetailDto
import com.andreich.data.network.pojo.TracksDto

interface RemoteDataSource {

    suspend fun getChartTracks(): TracksDto

    suspend fun getTrack(id: Int): TrackDetailDto

    suspend fun searchTrack(query: String): SearchResultDto
}