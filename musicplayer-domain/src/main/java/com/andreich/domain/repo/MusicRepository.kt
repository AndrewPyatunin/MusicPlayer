package com.andreich.domain.repo

import com.andreich.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface MusicRepository {


    suspend fun clearDatabase()

    suspend fun getSavedTracks(query: String?): List<Track>

    fun getRemoteTrack(id: Long): Flow<Track>

    fun searchTrack(query: String?): Flow<List<Track>>

    fun getTracks(fromPlayer: Boolean): Flow<List<Track>>
}