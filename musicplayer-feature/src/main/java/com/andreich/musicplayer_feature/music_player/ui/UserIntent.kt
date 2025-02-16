package com.andreich.musicplayer_feature.music_player.ui

sealed interface UserIntent {

    object LoadTracks : UserIntent
}