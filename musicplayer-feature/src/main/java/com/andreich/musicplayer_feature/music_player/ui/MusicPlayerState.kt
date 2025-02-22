package com.andreich.musicplayer_feature.music_player.ui

import androidx.media3.common.MediaItem

data class MusicPlayerState(
    val playList: List<AudioTrack>,
    val currentTrack: MediaItem? = null,
    val position: Long? = null,
    val loadedTrack: AudioTrack? = null
)
