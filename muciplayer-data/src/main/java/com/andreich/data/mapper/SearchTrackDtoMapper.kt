package com.andreich.data.mapper

import com.andreich.musicplayer_database.entity.AlbumEntity
import com.andreich.musicplayer_database.entity.ArtistEntity
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.AlbumDto
import com.andreich.musicplayer_network.network.pojo.AlbumSearchDto
import com.andreich.musicplayer_network.network.pojo.ArtistDto
import com.andreich.musicplayer_network.network.pojo.ArtistSearchDto
import com.andreich.musicplayer_network.network.pojo.SearchTrackDto

class SearchTrackDtoMapper(
    private val albumDtoMapper: DtoMapper<AlbumSearchDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistSearchDto, ArtistEntity>
) : DtoMapper<SearchTrackDto, TrackEntity> {

    override fun map(fromDto: SearchTrackDto): TrackEntity {
        return with(fromDto) {
            TrackEntity(
                id = id?.toLong() ?: 0L,
                title = title ?: "",
                artistName = artist?.name ?: "",
                artistEntity = artist?.let { artistDtoMapper.map(it) },
                albumEntity = album?.let { albumDtoMapper.map(it) },
                filePath = preview ?: "",
                duration = duration?.toLong() ?: 0
            )
        }
    }
}