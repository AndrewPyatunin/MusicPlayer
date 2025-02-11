package com.andreich.data.datasource.local

import com.andreich.data.database.MusicDao
import com.andreich.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

class MusicDataSourceImpl(
    private val dao: MusicDao
) : MusicDataSource {

    override suspend fun getTrack(id: Int): TrackEntity? {
        return dao.getTrack(id)
    }

    override fun getSavedTracks(query: String?): Flow<List<TrackEntity>> {
        return dao.getTracks(query)
    }
}