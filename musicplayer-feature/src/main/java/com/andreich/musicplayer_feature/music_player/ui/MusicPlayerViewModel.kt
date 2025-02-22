package com.andreich.musicplayer_feature.music_player.ui

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.andreich.domain.model.AudioModel
import com.andreich.domain.model.Track
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.domain.usecase.LoadTrackUseCase
import com.andreich.musicplayer_feature.music_player.ui.mapper.ModelToUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicPlayerViewModel @Inject constructor(
    private val getRemoteTracksUseCase: GetRemoteTracksUseCase,
    private val getHomeTracksUseCase: GetHomeTracksUseCase,
    private val loadTrackUseCase: LoadTrackUseCase,
    private val trackToAudioTrackMapper: ModelToUiMapper<Track, AudioTrack>,
    private val audioModelToAudioTrackMapper: ModelToUiMapper<AudioModel, AudioTrack>
) : ViewModel() {

    private val _state = MutableStateFlow(MusicPlayerState(emptyList()))
    val state = _state.asStateFlow()

    fun sendIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadHomeTracks -> loadHomeTracks()
            is UserIntent.LoadRemoteTracks -> loadRemoteTracks()
            is UserIntent.SaveState -> _state.value = state.value.copy(currentTrack = intent.mediaItem, position = intent.position)

            is UserIntent.LoadChosenTrack -> downLoadTrackUseCase(intent.item)
        }
    }

    @OptIn(UnstableApi::class)
    private fun downLoadTrackUseCase(item: MediaItem) {
        viewModelScope.launch {

            val meta = item.mediaMetadata
            val track = state.value.playList.firstOrNull {
                it.title == meta.title && it.artist == meta.artist && it.album == meta.albumTitle
            } ?: run {
                return@launch
            }
            try {
                 val track = loadTrackUseCase(track.id).first()

                    _state.value = state.value.copy(
                        currentTrack = null,
                        loadedTrack = trackToAudioTrackMapper.map(track)
                    )

            } catch (e: Exception) {
                Log.e("ExoPlayer", "Ошибка загрузки трека: ${e.message}")
            }

        }
    }

    private fun loadHomeTracks() {
        viewModelScope.launch {
            getHomeTracksUseCase().map {
                audioModelToAudioTrackMapper.map(it)
            }.let {
                _state.value = state.value.copy(playList = it)
            }
        }
    }

    @OptIn(UnstableApi::class)
    private fun loadRemoteTracks() {
        viewModelScope.launch {
            try {
                getRemoteTracksUseCase(fromPlayer = true).collect {
                    it.map { track ->

                        trackToAudioTrackMapper.map(track)
                    }.let { list ->
                        _state.value = state.value.copy(playList = list)
                    }
                }
            } catch (e: Exception) {
                Log.e("MUSIC_PLAYER_error", "Error collecting remote tracks: ${e.message}")
            }
        }
    }
}