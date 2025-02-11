package com.andreich.domain.repo

import com.andreich.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    suspend fun getTrack(id: Int): Track?

    fun getSavedTracks(query: String?): Flow<List<Track>>

    fun searchTrack(query: String): Flow<List<Track>>
}