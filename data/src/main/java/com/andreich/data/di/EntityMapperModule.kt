package com.andreich.data.di

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.CachedTrackEntity
import com.andreich.data.entity.TrackEntity
import com.andreich.data.mapper.AlbumEntityToAlbumMapper
import com.andreich.data.mapper.ArtistEntityToArtistMapper
import com.andreich.data.mapper.CachedTrackEntityToCachedTrackMapper
import com.andreich.data.mapper.EntityToModelMapper
import com.andreich.data.mapper.TrackEntityToTrackMapper
import com.andreich.domain.model.Album
import com.andreich.domain.model.Artist
import com.andreich.domain.model.CachedTrack
import com.andreich.domain.model.Track
import dagger.Binds
import dagger.Module

@Module
interface EntityMapperModule {

    @Binds
    fun bindAlbumEntityToAlbumMapper(impl: AlbumEntityToAlbumMapper): EntityToModelMapper<AlbumEntity, Album>

    @Binds
    fun bindArtistEntityToArtistMapper(impl: ArtistEntityToArtistMapper): EntityToModelMapper<ArtistEntity, Artist>

    @Binds
    fun bindCachedTrackEntityToCachedTrackMapper(impl: CachedTrackEntityToCachedTrackMapper): EntityToModelMapper<CachedTrackEntity, CachedTrack>

    @Binds
    fun bindTrackEntityToTrackMapper(impl: TrackEntityToTrackMapper): EntityToModelMapper<TrackEntity, Track>


}