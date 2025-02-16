package com.andreich.data.mapper

import com.andreich.domain.model.Album
import com.andreich.musicplayer_database.entity.AlbumEntity

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