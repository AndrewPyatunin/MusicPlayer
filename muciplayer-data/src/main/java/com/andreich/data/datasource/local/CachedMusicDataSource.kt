package com.andreich.data.datasource.local

import com.andreich.musicplayer_database.entity.CachedTrackEntity
import kotlinx.coroutines.flow.Flow

interface CachedMusicDataSource {

    fun insertTrack(trackEntity: CachedTrackEntity)

    fun getTracks(): Flow<List<CachedTrackEntity>>

    fun getTrack(id: String): Flow<CachedTrackEntity>
}