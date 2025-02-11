package com.andreich.data.database

import androidx.room.Dao
import androidx.room.Query
import com.andreich.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Query("SELECT * FROM track WHERE id = :id")
    suspend fun getTrack(id: Int): TrackEntity?

    @Query("SELECT * FROM track WHERE title LIKE '%' || :query || '%' OR artistName  LIKE '%' || :query || '%'")
    fun getTracks(query: String?): Flow<List<TrackEntity>>
}