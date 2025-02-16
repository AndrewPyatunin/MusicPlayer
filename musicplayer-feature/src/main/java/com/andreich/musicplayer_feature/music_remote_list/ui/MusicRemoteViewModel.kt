package com.andreich.musicplayer_feature.music_remote_list.ui

import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.domain.usecase.SearchTrackUseCase
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import javax.inject.Inject

class MusicRemoteViewModel @Inject constructor(
    private val searchTrackUseCase: SearchTrackUseCase,
    private val getRemoteTracksUseCase: GetRemoteTracksUseCase
) : BaseViewModel() {

    override fun searchTrack(query: String) {
        searchTrackUseCase(query)
    }

    override fun loadTracks() {
        getRemoteTracksUseCase()
    }

    override fun navigateTo(track: MusicItem) {

    }
}