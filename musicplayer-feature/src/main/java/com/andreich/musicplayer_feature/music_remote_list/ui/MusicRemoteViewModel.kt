package com.andreich.musicplayer_feature.music_remote_list.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.domain.usecase.SearchTrackUseCase
import com.andreich.ui.BaseUiState
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicRemoteViewModel @Inject constructor(
    private val searchTrackUseCase: SearchTrackUseCase,
    private val getRemoteTracksUseCase: GetRemoteTracksUseCase
) : BaseViewModel() {

    override fun searchTrack(query: String) {
        viewModelScope.launch {
            searchTrackUseCase(query).collect {
                it.map {
                    with(it) {
                        MusicItem(
                            id = id,
                            url = url ?: "",
                            displayName = title,
                            title = title,
                            artist = artist?.name ?: "",
                            album = album?.title ?: "",
                            duration = duration,
                            data = "",
                            cover = cover
                        )
                    }
                }.let {
                    Log.d("MUSIC_PLAYER_search", it.toString())
                    _state.value = BaseUiState(it, false)
                }

            }
        }


    }

    override fun loadTracks() {
        viewModelScope.launch {
            getRemoteTracksUseCase().collect {
                it.map {
                    with(it) {
                        MusicItem(
                            id = id,
                            url = url ?: "",
                            displayName = "",
                            artist = artist?.name ?: "",
                            album = album?.title ?: "",
                            duration = duration,
                            title = title,
                            data = "",
                            cover = cover
                        )
                    }
                }.let {
                    Log.d("MUSIC_PLAYER_Remote", it.toString())
                    _state.value = BaseUiState(it, false)
                }
            }
        }

    }
}