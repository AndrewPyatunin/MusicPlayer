package com.andreich.data.datasource.local

import com.andreich.data.database.CachedTrackDao
import com.andreich.data.entity.CachedTrackEntity
import kotlinx.coroutines.flow.Flow

class CachedMusicDataSourceImpl(
    private val dao: CachedTrackDao
) : CachedMusicDataSource {

    override fun insertTrack(trackEntity: CachedTrackEntity) {
        dao.insertCachedTrackData(trackEntity)
    }

    override fun getTracks(): Flow<List<CachedTrackEntity>> {
        return dao.getCachedTracks()
    }

    override fun getTrack(id: String): Flow<CachedTrackEntity> {
        return dao.getCachedTrack(id)
    }
}