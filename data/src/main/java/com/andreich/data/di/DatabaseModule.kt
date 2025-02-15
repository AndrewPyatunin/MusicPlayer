package com.andreich.data.di

import android.content.Context
import com.andreich.data.database.MusicDao
import com.andreich.data.database.MusicDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): MusicDatabase {
        return MusicDatabase.getInstance(context)
    }

    @Provides
    fun provideMusicDao(database: MusicDatabase):MusicDao {
        return database.musicDao()
    }
}