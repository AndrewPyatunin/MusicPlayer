package com.andreich.data.datasource.remote

import com.andreich.data.network.ApiService
import com.andreich.data.network.pojo.SearchResultDto
import com.andreich.data.network.pojo.TrackDetailDto
import com.andreich.data.network.pojo.TracksDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getChartTracks(): TracksDto {
        return apiService.getChartTracks()
    }

    override suspend fun getTrack(id: Int): TrackDetailDto {
        return apiService.getTrack(id)
    }

    override suspend fun searchTrack(query: String): SearchResultDto {
        return apiService.searchTrack(query)
    }
}