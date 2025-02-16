package com.andreich.musicplayer_database

import androidx.room.TypeConverter
import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatabaseConverter {

    private val gson = Gson()
    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value == null) return emptyList()

        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromListString(list: List<String?>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToAlbumEntity(value: String?): AlbumEntity? {
        if (value == null) return null

        return gson.fromJson(value, object : TypeToken<AlbumEntity>() {}.type)
    }

    @TypeConverter
    fun fromAlbumEntityToString(entity: AlbumEntity?): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun fromArtistEntityToString(entity: ArtistEntity?): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun fromStringToArtistEntity(value: String?): ArtistEntity? {
        if (value == null) return null

        return gson.fromJson(value, object : TypeToken<ArtistEntity>() {}.type)
    }

    @TypeConverter
    fun fromTrackEntityToString(entity: TrackEntity?): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun fromStringToTrackEntity(value: String?): TrackEntity? {
        if (value == null) return null

        return gson.fromJson(value, object : TypeToken<TrackEntity>() {}.type)
    }
}