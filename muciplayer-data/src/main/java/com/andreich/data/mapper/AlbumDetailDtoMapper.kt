package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDetailDto

class AlbumDetailDtoMapper : DtoMapper<AlbumDetailDto, AlbumEntity> {

    override fun map(fromDto: AlbumDetailDto): AlbumEntity {
        return with(fromDto) {
            AlbumEntity(
                id = id.toLongOrNull() ?: 0,
                title = title,
                cover = cover,
                coverBig = coverBig,
                trackList = tracklist
            )
        }
    }
}