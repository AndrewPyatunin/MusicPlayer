package com.andreich.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _state = MutableStateFlow(BaseUiState(emptyList(), false))
    val state = _state.asStateFlow()

    private val _news = MutableStateFlow<BaseUiNews>(BaseUiNews.Initial)
    val news = _news.asStateFlow()

    fun sendIntent(intent: BaseUiIntent) {
        when (intent) {
            is BaseUiIntent.LoadTracks -> {
                Log.d("MUSIC_TRACK", "loadTracksIntent")
                loadTracks()
            }

            is BaseUiIntent.ChooseTrack -> navigateTo(intent.track)

            is BaseUiIntent.SearchTrack -> searchTrack(query = intent.query)

        }
    }

    abstract fun searchTrack(query: String)

    abstract fun loadTracks()

    private fun navigateTo(track: MusicItem) {
        _news.value = BaseUiNews.NavigateTo(track)
    }
}