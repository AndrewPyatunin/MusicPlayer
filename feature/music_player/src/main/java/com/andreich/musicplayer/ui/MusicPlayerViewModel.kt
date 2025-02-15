package com.andreich.musicplayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreich.domain.model.Track
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.musicplayer.ui.mapper.ModelToUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicPlayerViewModel @Inject constructor(
    private val getRemoteTracksUseCase: GetRemoteTracksUseCase,
    private val mapper: ModelToUiMapper<Track, AudioTrack>
) : ViewModel() {
    private val _state = MutableStateFlow<MusicPlayerState>(MusicPlayerState(emptyList()))
    val state = _state.asStateFlow()

    fun sendIntent(intent: UserIntent) {
        when(intent) {
            UserIntent.LoadTracks -> loadTracks()
        }
    }

    private fun loadTracks() {
        viewModelScope.launch {
            getRemoteTracksUseCase().map { list ->
                list.map {
                    mapper.map(it)
                }
            }.collect {
                _state.value = MusicPlayerState(playList = it)
            }
        }
    }
}