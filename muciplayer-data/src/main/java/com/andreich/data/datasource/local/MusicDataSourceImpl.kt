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

    override suspend fun clearDatabase() {
        dao.clearTracks()
    }

    override suspend fun getTracks(): List<TrackEntity> {
        return dao.getTracks()
    }

    override suspend fun getQueryTracks(query: String?): List<TrackEntity> {
        return dao.getTracks(query)
    }

    override fun getTrack(id: Long): Flow<TrackEntity> {
        return dao.getTrack(id)
    }
}