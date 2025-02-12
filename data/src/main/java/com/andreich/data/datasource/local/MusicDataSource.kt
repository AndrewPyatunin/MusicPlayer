package com.andreich.data.datasource.local

import com.andreich.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataSource {

    suspend fun insertTrack(trackEntity: TrackEntity)

    suspend fun insertTrackList(list: List<TrackEntity>)

    fun getTrack(id: Int): Flow<TrackEntity>

    fun getSavedTracks(query: String? = null): Flow<List<TrackEntity>>
}