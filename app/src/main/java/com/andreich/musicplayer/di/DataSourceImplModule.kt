package com.andreich.musicplayer.di

import com.andreich.data.datasource.local.CachedMusicDataSource
import com.andreich.data.datasource.local.CachedMusicDataSourceImpl
import com.andreich.data.datasource.local.MusicDataSource
import com.andreich.data.datasource.local.MusicDataSourceImpl
import com.andreich.data.datasource.remote.RemoteDataSource
import com.andreich.data.datasource.remote.RemoteDataSourceImpl
import com.andreich.musicplayer_database.database.CachedTrackDao
import com.andreich.musicplayer_database.database.MusicDao
import com.andreich.musicplayer_network.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class DataSourceImplModule {

    @Provides
    fun provideMusicDataSource(dao: MusicDao): MusicDataSource {
        return MusicDataSourceImpl(dao)
    }

    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideCachedDataSource(dao: CachedTrackDao): CachedMusicDataSource {
        return CachedMusicDataSourceImpl(dao)
    }
}