package com.andreich.data.datasource.local

import com.andreich.musicplayer_database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataSource {

    suspend fun insertTrack(trackEntity: TrackEntity)

    suspend fun insertTrackList(list: List<TrackEntity>)

    suspend fun clearDatabase()

    fun getTrack(id: Long): Flow<TrackEntity>

    fun getSavedTracks(query: String? = null): Flow<List<TrackEntity>>
}