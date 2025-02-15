package com.andreich.musicplayer.ui

sealed interface UserIntent {

    object LoadTracks : UserIntent
}