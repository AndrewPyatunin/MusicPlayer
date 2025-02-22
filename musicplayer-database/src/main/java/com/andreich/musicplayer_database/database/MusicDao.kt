package com.andreich.musicplayer_database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.musicplayer_database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Query("SELECT * FROM track WHERE id = :id")
    fun getTrack(id: Long): Flow<TrackEntity>

    @Query("SELECT * FROM track WHERE title LIKE '%' || :query || '%' OR artistName  LIKE '%' || :query || '%' OR :query is NULL")
    fun getTracks(query: String?): List<TrackEntity>

    @Query("SELECT * FROM track")
    suspend fun getTracks(): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackList(list: List<TrackEntity>)

    @Query("DELETE FROM track")
    suspend fun clearTracks()
}