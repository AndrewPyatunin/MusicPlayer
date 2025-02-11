package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.network.pojo.AlbumDto

class AlbumDtoToAlbumEntityMapper : DtoMapper<AlbumDto, AlbumEntity> {

    override fun map(fromDto: AlbumDto): AlbumEntity {
        return with(fromDto) {
            AlbumEntity(
                id = id?.toIntOrNull() ?: 0,
                title = title ?: "",
                cover = cover,
                coverBig = coverBig,
                trackList = tracklist ?: ""
            )
        }
    }
}