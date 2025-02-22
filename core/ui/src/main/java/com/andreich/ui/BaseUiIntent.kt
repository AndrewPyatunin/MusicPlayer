package com.andreich.ui

sealed interface BaseUiIntent {

    data object LoadTracks : BaseUiIntent

    data object ClearStateNews : BaseUiIntent

    data object PermissionGranted : BaseUiIntent

    data class ChooseTrack(val track: MusicItem) : BaseUiIntent

    data class SearchTrack(val query: String) : BaseUiIntent
}