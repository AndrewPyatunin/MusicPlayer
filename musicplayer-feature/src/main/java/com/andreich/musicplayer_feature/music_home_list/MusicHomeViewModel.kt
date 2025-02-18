package com.andreich.musicplayer_feature.music_home_list

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.SearchHomeTrackUseCase
import com.andreich.ui.BaseUiState
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicHomeViewModel @Inject constructor(
    private val searchTrackUseCase: SearchHomeTrackUseCase,
    private val getHomeTracksUseCase: GetHomeTracksUseCase
) : BaseViewModel() {

    override fun searchTrack(query: String) {
        viewModelScope.launch {
            searchTrackUseCase(query).map {
                with(it) {
                    MusicItem(
                        id = id,
                        url = uri,
                        displayName = title,
                        data = "",
                        duration = duration,
                        title = title,
                        album = album,
                        artist = artist,
                        uri = Uri.parse(uri)
                    )
                }
            }.let {
                _state.value = BaseUiState(it, false)
            }
        }

    }

    override fun loadTracks() {
        viewModelScope.launch {
            getHomeTracksUseCase().map {
                with(it) {
                    MusicItem(
                        id = id,
                        uri = Uri.parse(uri),
                        url = uri,
                        displayName = title,
                        data = "",
                        duration = duration,
                        title = title,
                        album = album,
                        artist = artist,
                    )
                }
            }.let {
                _state.value = BaseUiState(it, false)
            }
        }

    }
}