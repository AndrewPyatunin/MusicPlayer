package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_network.network.pojo.AlbumSearchDto

class AlbumSearchDtoMapper : DtoMapper<AlbumSearchDto, AlbumEntity> {

    override fun map(fromDto: AlbumSearchDto): AlbumEntity {
        return with(fromDto) {
            AlbumEntity(
                id = id ?: 0,
                title = title ?: "",
                cover = cover,
                coverBig = coverBig,
                trackList = tracklist ?: ""
            )
        }
    }
}