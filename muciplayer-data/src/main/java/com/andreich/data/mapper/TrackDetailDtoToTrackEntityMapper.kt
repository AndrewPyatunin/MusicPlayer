package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDetailDto
import com.andreich.musicplayer_network.network.pojo.ArtistDetailDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto

class TrackDetailDtoToTrackEntityMapper(
    private val albumDtoMapper: DtoMapper<AlbumDetailDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistDetailDto, ArtistEntity>
) : DtoMapper<TrackDetailDto, TrackEntity> {

    override fun map(fromDto: TrackDetailDto): TrackEntity {
        return with(fromDto) {
            TrackEntity(
                id = id?.toLongOrNull() ?: 0L,
                title = title ?: "",
                artistName = artist?.name ?: "",
                albumEntity = album?.let { albumDtoMapper.map(it) },
                artistEntity = artist?.let { artistDtoMapper.map(it) },
                cover = if (preview == "") album.cover else preview,
                filePath = fromDto.link ?: ""
            )
        }
    }
}