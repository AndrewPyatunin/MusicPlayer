package com.andreich.data.mapper

import com.andreich.data.entity.ArtistEntity
import com.andreich.data.network.pojo.ArtistDto
import javax.inject.Inject

class ArtistDtoToArtistEntityMapper @Inject constructor() : DtoMapper<ArtistDto, ArtistEntity> {

    override fun map(fromDto: ArtistDto): ArtistEntity {
        return with(fromDto) {
            ArtistEntity(
                id = id ?: 0,
                name = name ?: "",
                picture = picture,
                pictureBig = pictureBig,
                trackList = tracklist ?: ""
            )
        }
    }
}