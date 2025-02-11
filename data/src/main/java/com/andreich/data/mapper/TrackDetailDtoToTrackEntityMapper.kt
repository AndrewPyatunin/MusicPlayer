package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.TrackEntity
import com.andreich.data.network.pojo.AlbumDto
import com.andreich.data.network.pojo.ArtistDto
import com.andreich.data.network.pojo.TrackDetailDto

class TrackDetailDtoToTrackEntityMapper(
    private val albumDtoMapper: DtoMapper<AlbumDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
) : DtoMapper<TrackDetailDto, TrackEntity> {

    override fun map(fromDto: TrackDetailDto): TrackEntity {
        return with(fromDto) {
            TrackEntity(
                id = id?.toIntOrNull() ?: 0,
                title = title ?: "",
                artistName = artist?.name ?: "",
                albumEntity = album?.let { albumDtoMapper.map(it) },
                artistEntity = artist?.let { artistDtoMapper.map(it) },
                cover = preview,
                filePath = fromDto.link ?: ""
            )
        }
    }
}