package com.andreich.data.mapper

import com.andreich.data.entity.ArtistEntity
import com.andreich.domain.model.Artist
import javax.inject.Inject

class ArtistEntityToArtistMapper @Inject constructor() : EntityToModelMapper<ArtistEntity, Artist> {

    override fun map(fromEntity: ArtistEntity): Artist {
        return with(fromEntity) {
            Artist(
                id = id,
                name = name,
                picture = picture,
                pictureBig = pictureBig,
                trackList = trackList
            )
        }
    }
}