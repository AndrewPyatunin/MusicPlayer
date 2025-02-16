package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDataDto
import com.andreich.musicplayer_network.network.pojo.ArtistDto
import com.andreich.musicplayer_network.network.pojo.TrackDto

class TrackDtoToTrackEntityMapper(
    private val albumDataDtoMapper: DtoMapper<AlbumDataDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
) : DtoMapper<TrackDto, TrackEntity> {

    override fun map(fromDto: TrackDto): TrackEntity {
        return with(fromDto) {
            TrackEntity(
                id = id ?: 0,
                title = title ?: "",
                artistName = artist?.name ?: "",
                albumEntity = album?.let { albumDataDtoMapper.map(it) },
                artistEntity = artist?.let { artistDtoMapper.map(it) },
                cover = album?.cover ?: "",
                filePath = "",
            )
        }
    }
}