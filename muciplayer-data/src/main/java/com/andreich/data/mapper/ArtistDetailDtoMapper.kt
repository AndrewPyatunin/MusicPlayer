package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_network.network.pojo.ArtistDetailDto

class ArtistDetailDtoMapper : DtoMapper<ArtistDetailDto, ArtistEntity> {

    override fun map(fromDto: ArtistDetailDto): ArtistEntity {
        return with(fromDto) {
            ArtistEntity(
                id = id.toLongOrNull() ?: 0,
                name = name,
                picture = picture,
                pictureBig = pictureBig,
                trackList = tracklist
            )
        }
    }
}