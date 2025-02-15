package com.andreich.ui

sealed interface BaseUiIntent {

    object LoadTracks : BaseUiIntent

    class ChooseTrack(val track: MusicItem) : BaseUiIntent

    class SearchTrack(val query: String) : BaseUiIntent
}