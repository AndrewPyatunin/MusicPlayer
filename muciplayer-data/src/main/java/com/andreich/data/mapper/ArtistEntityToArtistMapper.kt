package com.andreich.data.mapper

import com.andreich.domain.model.Artist
import com.andreich.musicplayer_database.entity.ArtistEntity

class ArtistEntityToArtistMapper : EntityToModelMapper<ArtistEntity, Artist> {

    override fun map(fromEntity: ArtistEntity): Artist {
        return with(fromEntity) {
            Artist(
                id = id.toInt(),
                name = name,
                picture = picture,
                pictureBig = pictureBig,
                trackList = trackList
            )
        }
    }
}