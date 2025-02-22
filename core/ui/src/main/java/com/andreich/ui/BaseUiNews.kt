package com.andreich.ui

sealed interface BaseUiNews {

    data object Initial : BaseUiNews

    data class ShowToast(val message: String) : BaseUiNews

    data class NavigateTo(val musicItem: MusicItem) : BaseUiNews
}