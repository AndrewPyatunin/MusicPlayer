package com.andreich.data.datasource.local

import com.andreich.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataSource {

    suspend fun getTrack(id: Int): TrackEntity?

    fun getSavedTracks(query: String?): Flow<List<TrackEntity>>
}