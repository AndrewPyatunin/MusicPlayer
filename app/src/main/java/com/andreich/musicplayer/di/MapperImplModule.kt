package com.andreich.musicplayer.di

import com.andreich.data.mapper.AlbumDataDtoToAlbumEntityMapper
import com.andreich.data.mapper.AlbumDetailDtoMapper
import com.andreich.data.mapper.AlbumDtoToAlbumEntityMapper
import com.andreich.data.mapper.AlbumEntityToAlbumMapper
import com.andreich.data.mapper.AlbumSearchDtoMapper
import com.andreich.data.mapper.ArtistDetailDtoMapper
import com.andreich.data.mapper.ArtistDtoToArtistEntityMapper
import com.andreich.data.mapper.ArtistEntityToArtistMapper
import com.andreich.data.mapper.ArtistSearchDtoMapper
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
import com.andreich.musicplayer_network.network.pojo.AlbumDetailDto
import com.andreich.musicplayer_network.network.pojo.AlbumDto
import com.andreich.musicplayer_network.network.pojo.AlbumSearchDto
import com.andreich.musicplayer_network.network.pojo.ArtistDetailDto
import com.andreich.musicplayer_network.network.pojo.ArtistDto
import com.andreich.musicplayer_network.network.pojo.ArtistSearchDto
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
        albumDtoMapper: DtoMapper<AlbumSearchDto, AlbumEntity>,
        artistDtoMapper: DtoMapper<ArtistSearchDto, ArtistEntity>
    ): DtoMapper<SearchTrackDto, TrackEntity> {
        return SearchTrackDtoMapper(albumDtoMapper, artistDtoMapper)
    }

    @Provides
    fun provideAlbumSearchDtoToAlbumEntityMapper(
    ): DtoMapper<AlbumSearchDto, AlbumEntity> {
        return AlbumSearchDtoMapper()
    }

    @Provides
    fun provideArtistSearchDtoToArtistEntityMapper(
    ): DtoMapper<ArtistSearchDto, ArtistEntity> {
        return ArtistSearchDtoMapper()
    }

    @Provides
    fun provideTrackDetailDtoToTrackEntityMapper(
        albumDtoMapper: DtoMapper<AlbumDetailDto, AlbumEntity>,
        artistDtoMapper: DtoMapper<ArtistDetailDto, ArtistEntity>
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

    @Provides
    fun provideArtistDetailDtoMapper(): DtoMapper<ArtistDetailDto, ArtistEntity> {
        return ArtistDetailDtoMapper()
    }

    @Provides
    fun provideAlbumDetailDtoMapper(): DtoMapper<AlbumDetailDto, AlbumEntity> {
        return AlbumDetailDtoMapper()
    }

}