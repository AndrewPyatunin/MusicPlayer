package com.andreich.data.di

import com.andreich.data.datasource.local.CachedMusicDataSource
import com.andreich.data.datasource.local.CachedMusicDataSourceImpl
import com.andreich.data.datasource.local.MusicDataSource
import com.andreich.data.datasource.local.MusicDataSourceImpl
import com.andreich.data.datasource.remote.RemoteDataSource
import com.andreich.data.datasource.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindMusicDataSource(impl: MusicDataSourceImpl): MusicDataSource

    @Binds
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindCachedDataSource(impl: CachedMusicDataSourceImpl): CachedMusicDataSource
}