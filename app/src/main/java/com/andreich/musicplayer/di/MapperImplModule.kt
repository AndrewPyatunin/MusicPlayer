package com.andreich.musicplayer.di

import com.andreich.data.mapper.AlbumDataDtoToAlbumEntityMapper
import com.andreich.data.mapper.AlbumDtoToAlbumEntityMapper
import com.andreich.data.mapper.AlbumEntityToAlbumMapper
import com.andreich.data.mapper.ArtistDtoToArtistEntityMapper
import com.andreich.data.mapper.ArtistEntityToArtistMapper
import com.andreich.data.mapper.CachedTrackEntityToCachedTrackMapper
import com.andreich.data.mapper.DtoMapper
import com.andreich.data.mapper.EntityToModelMapper
import com.andreich.data.mapper.SearchTrackDtoMapper
import com.andreich.data.mapper.TrackDetailDtoToTrackEntityMapper
import com.andreich.data.mapper.TrackDtoToTrackEntityMapper
import com.andreich.data.mapper.TrackEntityToTrackMapper
import com.andreich.domain.model.Album
import com.andreich.domain.model.Artist
import com.andreich.domain.model.CachedTrack
import com.andreich.domain.model.Track
import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.CachedTrackEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDataDto
import com.andreich.musicplayer_network.network.pojo.AlbumDto
import com.andreich.musicplayer_network.network.pojo.ArtistDto
import com.andreich.musicplayer_network.network.pojo.SearchTrackDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TrackDto
import dagger.Module
import dagger.Provides

@Module
class MapperImplModule {

    @Provides
    fun provideAlbumDtoToAlbumEntityMapper(): DtoMapper<AlbumDto, AlbumEntity> {
        return AlbumDtoToAlbumEntityMapper()
    }

    @Provides
    fun provideArtistDtoToArtistEntityMapper(): DtoMapper<ArtistDto, ArtistEntity> {
        return ArtistDtoToArtistEntityMapper()
    }

    @Provides
    fun provideAlbumDataDtoToAlbumEntityMapper(): DtoMapper<AlbumDataDto, AlbumEntity> {
        return AlbumDataDtoToAlbumEntityMapper()
    }

    @Provides
    fun provideSearchTrackDtoToTrackEntityMapper(
        albumDtoMapper: DtoMapper<AlbumDto, AlbumEntity>,
        artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
    ): DtoMapper<SearchTrackDto, TrackEntity> {
        return SearchTrackDtoMapper(albumDtoMapper, artistDtoMapper)
    }

    @Provides
    fun provideTrackDetailDtoToTrackEntityMapper(
        albumDtoMapper: DtoMapper<AlbumDto, AlbumEntity>,
        artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
    ): DtoMapper<TrackDetailDto, TrackEntity> {
        return TrackDetailDtoToTrackEntityMapper(albumDtoMapper, artistDtoMapper)
    }

    @Provides
    fun provideTrackDtoToTrackEntityMapper(
        albumDataDtoMapper: DtoMapper<AlbumDataDto, AlbumEntity>,
        artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
    ): DtoMapper<TrackDto, TrackEntity> {
        return TrackDtoToTrackEntityMapper(albumDataDtoMapper, artistDtoMapper)
    }

    @Provides
    fun provideAlbumEntityToAlbumMapper(): EntityToModelMapper<AlbumEntity, Album> {
        return AlbumEntityToAlbumMapper()
    }

    @Provides
    fun provideArtistEntityToArtistMapper(): EntityToModelMapper<ArtistEntity, Artist> {
        return ArtistEntityToArtistMapper()
    }

    @Provides
    fun provideCachedTrackEntityToCachedTrackMapper(): EntityToModelMapper<CachedTrackEntity, CachedTrack> {
        return CachedTrackEntityToCachedTrackMapper()
    }

    @Provides
    fun provideTrackEntityToTrackMapper(
        albumEntityToAlbumMapper: EntityToModelMapper<AlbumEntity, Album>,
        artistEntityToArtistMapper: EntityToModelMapper<ArtistEntity, Artist>
    ): EntityToModelMapper<TrackEntity, Track> {
        return TrackEntityToTrackMapper(albumEntityToAlbumMapper, artistEntityToArtistMapper)
    }
}