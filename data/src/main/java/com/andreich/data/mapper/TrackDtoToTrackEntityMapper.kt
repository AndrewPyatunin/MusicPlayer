package com.andreich.data.mapper

import com.andreich.data.entity.AlbumEntity
import com.andreich.data.entity.ArtistEntity
import com.andreich.data.entity.TrackEntity
import com.andreich.data.network.pojo.AlbumDataDto
import com.andreich.data.network.pojo.ArtistDto
import com.andreich.data.network.pojo.TrackDto
import javax.inject.Inject

class TrackDtoToTrackEntityMapper @Inject constructor(
    private val albumDataDtoMapper: DtoMapper<AlbumDataDto, AlbumEntity>,
    private val artistDtoMapper: DtoMapper<ArtistDto, ArtistEntity>
): DtoMapper<TrackDto, TrackEntity> {

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