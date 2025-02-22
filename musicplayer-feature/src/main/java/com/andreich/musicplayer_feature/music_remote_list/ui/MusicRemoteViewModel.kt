package com.andreich.musicplayer_feature.music_remote_list.ui

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.domain.usecase.SearchTrackUseCase
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicRemoteViewModel @Inject constructor(
    private val searchTrackUseCase: SearchTrackUseCase,
    private val getRemoteTracksUseCase: GetRemoteTracksUseCase
) : BaseViewModel() {

    override fun searchTrack(query: String?) {
        viewModelScope.launch {
            searchTrackUseCase(query).collect {
                it.map { track ->
                    with(track) {
                        MusicItem(
                            id = id,
                            url = url ?: "",
                            displayName = "",
                            artist = artist?.name ?: "",
                            album = album?.title ?: "",
                            duration = duration,
                            title = title,
                            data = "",
                            cover = cover ?: album?.cover,
                            uri = Uri.parse(filePath),
                            coverBig = album?.coverBig
                        )
                    }
                }.let {
                    _state.value = state.value.copy(it, false)
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
                            cover = cover ?: album?.cover,
                            uri = Uri.parse(filePath),
                            coverBig = album?.coverBig
                        )
                    }
                }.let {
                    _state.value = state.value.copy(it, false)
                }
            }
        }

    }
}