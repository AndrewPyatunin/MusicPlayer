package com.andreich.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_track")
data class CachedTrackEntity(
    @PrimaryKey
    val id: String,
    val url: String
)
