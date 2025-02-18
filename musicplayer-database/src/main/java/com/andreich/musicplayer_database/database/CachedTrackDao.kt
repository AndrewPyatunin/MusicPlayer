package com.andreich.musicplayer_database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.musicplayer_database.entity.CachedTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCachedTrackData(entity: CachedTrackEntity)

    @Query("SELECT * FROM cached_track")
    fun getCachedTracks(): Flow<List<CachedTrackEntity>>

    @Query("SELECT * FROM cached_track WHERE id = :id")
    fun getCachedTrack(id: String): Flow<CachedTrackEntity>
}