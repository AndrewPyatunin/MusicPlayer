package com.andreich.musicplayer_database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.andreich.musicplayer_database.DatabaseConverter
import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity


@Database(entities = [AlbumEntity::class, ArtistEntity::class, TrackEntity::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class MusicDatabase : RoomDatabase() {

    companion object {

        @Volatile
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

    abstract fun musicDao(): MusicDao
}