package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDto
import com.andreich.musicplayer_network.network.pojo.ArtistDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto

class TrackDetailDtoToTrackEntityMapper(
    private val albumDtoMapper: DtoMapper<AlbumDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
) : DtoMapper<TrackDetailDto, TrackEntity> {

    override fun map(fromDto: TrackDetailDto): TrackEntity {
        return with(fromDto) {
            TrackEntity(
                id = id?.toLongOrNull() ?: 0L,
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