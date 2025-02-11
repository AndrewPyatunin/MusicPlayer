package com.andreich.data.repo

import com.andreich.data.datasource.local.MusicDataSource
import com.andreich.data.entity.TrackEntity
import com.andreich.data.mapper.EntityToModelMapper
import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MusicRepositoryImpl(
    private val musicDataSource: MusicDataSource,
    private val trackEntityToModelMapper: EntityToModelMapper<TrackEntity, Track>
) : MusicRepository {

    override suspend fun getTrack(id: Int): Track? {
        return musicDataSource.getTrack(id)?.let {
            trackEntityToModelMapper.map(it)
        }
    }

    override fun getSavedTracks(query: String?): Flow<List<Track>> {
        return musicDataSource.getSavedTracks(query).map { trackList ->
            trackList.map {
                trackEntityToModelMapper.map(it)
            }
        }
    }

    override fun searchTrack(query: String): Flow<List<Track>> {
        return getSavedTracks(query)
    }
}