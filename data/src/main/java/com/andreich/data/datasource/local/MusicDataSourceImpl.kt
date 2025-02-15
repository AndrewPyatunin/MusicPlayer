package com.andreich.data.datasource.local

import com.andreich.data.database.MusicDao
import com.andreich.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicDataSourceImpl @Inject constructor(
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