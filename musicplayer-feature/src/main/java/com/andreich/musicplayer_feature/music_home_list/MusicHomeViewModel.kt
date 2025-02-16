package com.andreich.musicplayer_feature.music_home_list

import androidx.lifecycle.viewModelScope
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.SearchTrackUseCase
import com.andreich.ui.BaseUiState
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicHomeViewModel @Inject constructor(
    private val searchTrackUseCase: SearchTrackUseCase,
    private val getHomeTracksUseCase: GetHomeTracksUseCase
) : BaseViewModel() {

    override fun searchTrack(query: String) {
        viewModelScope.launch {
            searchTrackUseCase(query).map { list ->
                list.map {
                    with(it) {
                        MusicItem(
                            id = id,
                            url = url ?: "",
                            displayName = title,
                            artist = artist?.name ?: "",
                            album = album?.title ?: "",
                            data = "",
                            duration = duration,
                            title = title
                        )
                    }
                }
            }.collect {
                _state.value = BaseUiState(it, false)
            }
        }

    }

    override fun loadTracks() {
        getHomeTracksUseCase().map {
            with(it) {
                MusicItem(
                    id = id.toInt(),
                    url = uri,
                    displayName = artist,
                    data = "",
                    duration = duration,
                    title = title,
                    album = album,
                    artist = artist
                )
            }
        }.let {
            _state.value = BaseUiState(it, false)
        }
    }

    override fun navigateTo(track: MusicItem) {
    }
}