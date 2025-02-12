package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.domain.model.Album

class AlbumEntityToAlbumMapper : EntityToModelMapper<AlbumEntity, Album> {

    override fun map(fromEntity: AlbumEntity): Album {
        return with(fromEntity) {
            Album(
                id = id,
                title = title,
                cover = cover,
                coverBig = coverBig,
                trackList = trackList
            )
        }
    }
}