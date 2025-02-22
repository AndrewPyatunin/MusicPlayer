package com.andreich.musicplayer_feature.music_player.ui

import androidx.media3.common.MediaItem

sealed interface UserIntent {

    class SaveState(val mediaItem: MediaItem, val position: Long? = null) : UserIntent

    object LoadHomeTracks : UserIntent

    object LoadRemoteTracks : UserIntent

    data class LoadChosenTrack(val item: MediaItem) : UserIntent
}