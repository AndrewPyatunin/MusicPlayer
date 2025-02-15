package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.domain.model.Album
import javax.inject.Inject

class AlbumEntityToAlbumMapper @Inject constructor() : EntityToModelMapper<AlbumEntity, Album> {

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