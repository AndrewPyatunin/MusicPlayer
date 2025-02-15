package com.andreich.data.di

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.TrackEntity
import com.andreich.data.mapper.AlbumDtoToAlbumEntityMapper
import com.andreich.data.mapper.ArtistDtoToArtistEntityMapper
import com.andreich.data.mapper.DtoMapper
import com.andreich.data.mapper.SearchTrackDtoMapper
import com.andreich.data.mapper.TrackDetailDtoToTrackEntityMapper
import com.andreich.data.mapper.TrackDtoToTrackEntityMapper
import com.andreich.data.network.pojo.AlbumDto
import com.andreich.data.network.pojo.ArtistDto
import com.andreich.data.network.pojo.SearchTrackDto
import com.andreich.data.network.pojo.TrackDetailDto
import com.andreich.data.network.pojo.TrackDto
import dagger.Binds
import dagger.Module

@Module
interface DtoMapperModule {

    @Binds
    fun bindAlbumDtoToAlbumEntityMapper(impl: AlbumDtoToAlbumEntityMapper): DtoMapper<AlbumDto, AlbumEntity>

    @Binds
    fun bindArtistDtoToArtistEntityMapper(impl: ArtistDtoToArtistEntityMapper): DtoMapper<ArtistDto, ArtistEntity>

    @Binds
    fun bindSearchTrackDtoMapper(impl: SearchTrackDtoMapper): DtoMapper<SearchTrackDto, TrackEntity>

    @Binds
    fun bindTrackDetailDtoToTrackEntityMapper(impl: TrackDetailDtoToTrackEntityMapper): DtoMapper<TrackDetailDto, TrackEntity>

    @Binds
    fun bindTrackDtoToTrackEntityMapper(impl: TrackDtoToTrackEntityMapper): DtoMapper<TrackDto, TrackEntity>
}