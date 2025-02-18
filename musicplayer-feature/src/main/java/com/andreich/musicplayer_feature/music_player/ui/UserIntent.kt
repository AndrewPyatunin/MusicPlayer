package com.andreich.musicplayer_feature.music_player.ui

sealed interface UserIntent {

    object LoadHomeTracks : UserIntent

    object LoadRemoteTracks : UserIntent
}