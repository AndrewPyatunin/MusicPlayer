package com.andreich.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.data.entity.CachedTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCachedTrackData(entity: CachedTrackEntity)

    @Query("SELECT * FROM cached_track")
    fun getCachedTracks(): Flow<List<CachedTrackEntity>>

    @Query("SELECT * FROM cached_track WHERE id = :id")
    fun getCachedTrack(id: String): Flow<CachedTrackEntity>
}