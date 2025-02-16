package com.andreich.data.mapper

import com.andreich.domain.model.Album
import com.andreich.domain.model.Artist
import com.andreich.domain.model.Track
import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity

class TrackEntityToTrackMapper(
    private val albumEntityToAlbumMapper: EntityToModelMapper<AlbumEntity, Album>,
    private val artistEntityToArtistMapper: EntityToModelMapper<ArtistEntity, Artist>
) : EntityToModelMapper<TrackEntity, Track> {

    override fun map(fromEntity: TrackEntity): Track {
        return with(fromEntity) {
            Track(
                id = id,
                title = title,
                album = albumEntity?.let { albumEntityToAlbumMapper.map(it) },
                artist = artistEntity?.let { artistEntityToArtistMapper.map(it) },
                cover = cover,
                filePath = filePath,
                duration = duration
            )
        }
    }
}