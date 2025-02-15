package com.andreich.data.datasource.local

import com.andreich.data.entity.CachedTrackEntity
import kotlinx.coroutines.flow.Flow

interface CachedMusicDataSource {

    fun insertTrack(trackEntity: CachedTrackEntity)

    fun getTracks(): Flow<List<CachedTrackEntity>>

    fun getTrack(id: String): Flow<CachedTrackEntity>
}