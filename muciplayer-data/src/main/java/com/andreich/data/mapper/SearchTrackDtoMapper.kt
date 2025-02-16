package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDto
import com.andreich.musicplayer_network.network.pojo.ArtistDto
import com.andreich.musicplayer_network.network.pojo.SearchTrackDto

class SearchTrackDtoMapper(
    private val albumDtoMapper: DtoMapper<AlbumDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
) : DtoMapper<SearchTrackDto, TrackEntity> {

    override fun map(fromDto: SearchTrackDto): TrackEntity {
        return with(fromDto) {
            TrackEntity(
                id = id?.toIntOrNull() ?: 0,
                title = title ?: "",
                artistName = artist?.name ?: "",
                artistEntity = artist?.let { artistDtoMapper.map(it) },
                albumEntity = album?.let { albumDtoMapper.map(it) },
                filePath = ""
            )
        }
    }
}