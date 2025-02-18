package com.andreich.musicplayer_feature.music_player.ui

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.andreich.domain.model.AudioModel
import com.andreich.domain.model.Track
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.musicplayer_feature.music_player.ui.mapper.ModelToUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicPlayerViewModel @Inject constructor(
    private val getRemoteTracksUseCase: GetRemoteTracksUseCase,
    private val getHomeTracksUseCase: GetHomeTracksUseCase,
    private val trackToAudioTrackMapper: ModelToUiMapper<Track, AudioTrack>,
    private val audioModelToAudioTrackMapper: ModelToUiMapper<AudioModel, AudioTrack>
) : ViewModel() {

    private val _state = MutableStateFlow(MusicPlayerState(emptyList()))
    val state = _state.asStateFlow()

    fun sendIntent(intent: UserIntent) {
        when (intent) {
            UserIntent.LoadHomeTracks -> loadHomeTracks()
            UserIntent.LoadRemoteTracks -> loadRemoteTracks()
        }
    }

    private fun loadHomeTracks() {
        viewModelScope.launch {
            getHomeTracksUseCase().map {
                audioModelToAudioTrackMapper.map(it)
            }.let { _state.value = MusicPlayerState(it) }
        }
    }

    @OptIn(UnstableApi::class)
    private fun loadRemoteTracks() {
        viewModelScope.launch {
            try {
                getRemoteTracksUseCase(fromPlayer = true).collect {
                    it.map { track ->
                        trackToAudioTrackMapper.map(track)
                    }.let {
                        _state.value = MusicPlayerState(playList = it)
                    }
                }
            } catch (e: Exception) {
                Log.e("MUSIC_PLAYER_error", "Error collecting remote tracks: ${e.message}")
            }
        }
    }
}