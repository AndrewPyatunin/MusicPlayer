package com.andreich.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.TrackEntity

@Database(entities = [AlbumEntity::class, ArtistEntity::class, TrackEntity::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    companion object {

        var instance: MusicDatabase? = null
        val LOCK = Any()
        private const val DB_NAME = "music_database.db"

        fun getInstance(context: Context): MusicDatabase {

            synchronized(LOCK) {
                instance?.let { return it }
                instance = Room.databaseBuilder(context, MusicDatabase::class.java, DB_NAME).build()
                return instance as MusicDatabase
            }
        }
    }
}