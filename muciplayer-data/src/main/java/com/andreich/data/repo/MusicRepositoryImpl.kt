package com.andreich.data.repo

import android.util.Log
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
import kotlinx.coroutines.flow.map

class MusicRepositoryImpl(
    private val musicDataSource: MusicDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val trackEntityToModelMapper: EntityToModelMapper<TrackEntity, Track>,
    private val trackDetailDtoMapper: DtoMapper<TrackDetailDto, TrackEntity>,
    private val searchTrackDtoMapper: DtoMapper<SearchTrackDto, TrackEntity>,
    private val chartTrackMapper: DtoMapper<TrackDto, TrackEntity>
) : MusicRepository {

    override suspend fun getTrack(id: Long): Flow<Track> {
        return flow {
            var localTrack = musicDataSource.getTrack(id).firstOrNull()
            if (localTrack == null) {
                val remoteTrack = remoteDataSource.getTrack(id)
                musicDataSource.insertTrack(trackDetailDtoMapper.map(remoteTrack))
                localTrack = musicDataSource.getTrack(id).firstOrNull()
            }
            if (localTrack == null) {
                throw IllegalArgumentException("Track with id=$id not found in database.")
            }
            emit(trackEntityToModelMapper.map(localTrack))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun clearDatabase() {
        musicDataSource.clearDatabase()
    }

    override fun getSavedTracks(query: String?): Flow<List<Track>> {
        return musicDataSource.getSavedTracks(query).map { trackList ->
            trackList.filter {
                it.filePath != ""
            }.map {
                trackEntityToModelMapper.map(it)
            }
        }
    }

    private fun getSearchedTracks(query: String): Flow<List<Track>> {
        return musicDataSource.getSavedTracks(query).map { trackList ->
            trackList.map {
                trackEntityToModelMapper.map(it)
            }
        }
    }

    override fun searchTrack(query: String): Flow<List<Track>> {
        return flow {
            val tracks = remoteDataSource.searchTrack(query).data.map {
                searchTrackDtoMapper.map(it)
            }
            musicDataSource.insertTrackList(tracks)
            getSearchedTracks(query).collect {
                emit(it)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTracks(fromPlayer: Boolean): Flow<List<Track>> {
        Log.d("MUSIC_PLAYER_repo", "get_tracks_repository $fromPlayer")
        return flow {
            if (!fromPlayer) {
                val tracks = remoteDataSource.getChartTracks().data.map {
                    Log.d("MUSIC_PLAYER_network_tracks", it.toString())
                    chartTrackMapper.map(it)
                }
                Log.d("MUSIC_PLAYER_network_tracks_mapped", tracks.toString())
                musicDataSource.insertTrackList(tracks)
                Log.d("MUSIC_PLAYER_repo", "get_tracks ${tracks.toString()}")
            }

            musicDataSource.getSavedTracks().collect { list ->
                Log.d("MUSIC_PLAYER_network_tracks_saved", list.toString())
                list.map {
                    trackEntityToModelMapper.map(it)
                }.apply {
                    Log.d("MUSIC_PLAYER_repo_emit", this.toString())
                    emit(this)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}