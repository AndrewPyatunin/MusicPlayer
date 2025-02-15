package com.andreich.data.mapper

import com.andreich.data.entity.CachedTrackEntity
import com.andreich.domain.model.CachedTrack
import javax.inject.Inject

class CachedTrackEntityToCachedTrackMapper @Inject constructor() : EntityToModelMapper<CachedTrackEntity, CachedTrack> {

    override fun map(fromEntity: CachedTrackEntity): CachedTrack {
        return CachedTrack(
            id = fromEntity.id,
            url = fromEntity.url
        )
    }
}