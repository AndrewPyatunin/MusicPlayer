package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.TrackEntity
import com.andreich.domain.model.Album
import com.andreich.domain.model.Artist
import com.andreich.domain.model.Track
import javax.inject.Inject

class TrackEntityToTrackMapper @Inject constructor(
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