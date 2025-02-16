package com.andreich.musicplayer.di

import com.andreich.data.datasource.local.MusicDataSource
import com.andreich.data.datasource.remote.RemoteDataSource
import com.andreich.data.mapper.DtoMapper
import com.andreich.data.mapper.EntityToModelMapper
import com.andreich.data.repo.MusicRepositoryImpl
import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.SearchTrackDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TrackDto
import dagger.Module
import dagger.Provides

@Module
class RepositoryImplModule {

    @Provides
    fun provideMusicRepository(
        musicDataSource: MusicDataSource,
        remoteDataSource: RemoteDataSource,
        trackEntityToModelMapper: EntityToModelMapper<TrackEntity, Track>,
        trackDetailDtoMapper: DtoMapper<TrackDetailDto, TrackEntity>,
        searchTrackDtoMapper: DtoMapper<SearchTrackDto, TrackEntity>,
        chartTrackMapper: DtoMapper<TrackDto, TrackEntity>
    ): MusicRepository {
        return MusicRepositoryImpl(
            musicDataSource,
            remoteDataSource,
            trackEntityToModelMapper,
            trackDetailDtoMapper,
            searchTrackDtoMapper,
            chartTrackMapper
        )
    }
}