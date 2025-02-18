package com.andreich.data.datasource.remote

import com.andreich.musicplayer_network.network.ApiService
import com.andreich.musicplayer_network.network.pojo.SearchResultDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TracksDto

class RemoteDataSourceImpl(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getChartTracks(): TracksDto {
        return apiService.getChartTracks()
    }

    override suspend fun getTrack(id: Long): TrackDetailDto {
        return apiService.getTrack(id)
    }

    override suspend fun searchTrack(query: String): SearchResultDto {
        return apiService.searchTrack(query)
    }
}