package com.andreich.data.repo

import com.andreich.data.datasource.local.MusicDataSource
import com.andreich.data.datasource.remote.RemoteDataSource
import com.andreich.data.mapper.DtoMapper
import com.andreich.data.mapper.EntityToModelMapper
import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import com.andreich.musicplayer_database.entity.TrackEntity
import com.andreich.musicplayer_network.network.pojo.SearchTrackDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TrackDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MusicRepositoryImpl(
    private val musicDataSource: MusicDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val trackEntityToModelMapper: EntityToModelMapper<TrackEntity, Track>,
    private val trackDetailDtoMapper: DtoMapper<TrackDetailDto, TrackEntity>,
    private val searchTrackDtoMapper: DtoMapper<SearchTrackDto, TrackEntity>,
    private val chartTrackMapper: DtoMapper<TrackDto, TrackEntity>
) : MusicRepository {

    override fun getRemoteTrack(id: Long): Flow<Track> {
        return flow {
            val remoteTrack = remoteDataSource.getTrack(id)
            musicDataSource.insertTrack(trackDetailDtoMapper.map(remoteTrack))
            val localTrack = musicDataSource.getTrack(id).firstOrNull()
                ?: throw IllegalArgumentException("Track with id=$id not found in database.")
            emit(trackEntityToModelMapper.map(localTrack))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun clearDatabase() {
        musicDataSource.clearDatabase()
    }

    override suspend fun getSavedTracks(query: String?): List<Track> {
        return musicDataSource.getQueryTracks(query).filter {
                it.filePath != ""
            }.map {
                trackEntityToModelMapper.map(it)
            }
    }

    private suspend fun getSearchedTracks(query: String?): List<Track> {
        return musicDataSource.getQueryTracks(query).map {
                trackEntityToModelMapper.map(it)
            }
    }

    override fun searchTrack(query: String?): Flow<List<Track>> {
        return flow {
            val searchResult = remoteDataSource.searchTrack(query)
            val tracks = searchResult.data.apply {
            }.map {
                searchTrackDtoMapper.map(it)
            }
            musicDataSource.insertTrackList(tracks)
            getSearchedTracks(query).let {
                emit(it)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTracks(fromPlayer: Boolean): Flow<List<Track>> {
        return flow {
            if (!fromPlayer) {
                val tracks = remoteDataSource.getChartTracks().data.map {
                    chartTrackMapper.map(it)
                }
                musicDataSource.insertTrackList(tracks)
            }

            musicDataSource.getTracks().let { list ->
                list.map {
                    trackEntityToModelMapper.map(it)
                }.apply {
                    emit(this)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}