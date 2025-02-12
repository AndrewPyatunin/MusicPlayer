package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.TrackEntity
import com.andreich.data.network.pojo.AlbumDto
import com.andreich.data.network.pojo.ArtistDto
import com.andreich.data.network.pojo.SearchTrackDto

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