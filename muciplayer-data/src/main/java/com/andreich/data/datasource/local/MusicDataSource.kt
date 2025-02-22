package com.andreich.data.datasource.local

import com.andreich.musicplayer_database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataSource {

    suspend fun insertTrack(trackEntity: TrackEntity)

    suspend fun insertTrackList(list: List<TrackEntity>)

    suspend fun clearDatabase()

    suspend fun getQueryTracks(query: String? = null): List<TrackEntity>

    suspend fun getTracks(): List<TrackEntity>

    fun getTrack(id: Long): Flow<TrackEntity>
}