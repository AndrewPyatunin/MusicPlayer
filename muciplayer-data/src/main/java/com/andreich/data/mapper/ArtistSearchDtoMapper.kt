package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_network.network.pojo.ArtistSearchDto

class ArtistSearchDtoMapper : DtoMapper<ArtistSearchDto, ArtistEntity> {

    override fun map(fromDto: ArtistSearchDto): ArtistEntity {
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