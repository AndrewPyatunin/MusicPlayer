package com.andreich.data.mapper

import com.andreich.domain.model.CachedTrack
import com.andreich.musicplayer_database.entity.CachedTrackEntity

class CachedTrackEntityToCachedTrackMapper : EntityToModelMapper<CachedTrackEntity, CachedTrack> {

    override fun map(fromEntity: CachedTrackEntity): CachedTrack {
        return CachedTrack(
            id = fromEntity.id,
            url = fromEntity.url
        )
    }
}