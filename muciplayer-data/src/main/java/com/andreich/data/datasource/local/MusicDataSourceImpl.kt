package com.andreich.data.datasource.local

import com.andreich.musicplayer_database.database.MusicDao
import com.andreich.musicplayer_database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

class MusicDataSourceImpl(
    private val dao: MusicDao
) : MusicDataSource {

    override suspend fun insertTrack(trackEntity: TrackEntity) {
        dao.insertTrack(trackEntity)
    }

    override suspend fun insertTrackList(list: List<TrackEntity>) {
        dao.insertTrackList(list)
    }

    override fun getTrack(id: Int): Flow<TrackEntity> {
        return dao.getTrack(id)
    }

    override fun getSavedTracks(query: String?): Flow<List<TrackEntity>> {
        return dao.getTracks(query)
    }
}