package com.andreich.data.datasource.local

import com.andreich.data.database.CachedTrackDao
import com.andreich.data.entity.CachedTrackEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CachedMusicDataSourceImpl @Inject constructor(
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