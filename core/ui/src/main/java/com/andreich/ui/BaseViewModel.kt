package com.andreich.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _state = MutableStateFlow(BaseUiState(emptyList(), true))
    val state = _state.asStateFlow()

    private val _news = MutableStateFlow<BaseUiNews>(BaseUiNews.Initial)
    val news = _news.asStateFlow()

    fun sendIntent(intent: BaseUiIntent) {
        when (intent) {
            is BaseUiIntent.LoadTracks -> {
            }

            is BaseUiIntent.ChooseTrack -> navigateTo(intent.track)

            is BaseUiIntent.SearchTrack -> {
                _state.value = state.value.copy(audioList = emptyList(), isLoading = true)
                searchTrack(query = intent.query.trim().let { if (it == "") null else it })
            }

            is BaseUiIntent.ClearStateNews -> _news.value = BaseUiNews.Initial

            is BaseUiIntent.PermissionGranted -> {
                _state.value = state.value.copy(isLoading = true)
                loadTracks()
            }

        }
    }

    abstract fun searchTrack(query: String?)

    abstract fun loadTracks()

    private fun navigateTo(track: MusicItem) {
        _news.value = BaseUiNews.NavigateTo(track)
    }
}