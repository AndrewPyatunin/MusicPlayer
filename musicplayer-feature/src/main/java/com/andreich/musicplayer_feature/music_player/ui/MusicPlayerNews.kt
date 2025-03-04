package com.andreich.musicplayer_feature.music_player.ui

import com.andreich.musicplayer_feature.common.ArgumentType

sealed interface MusicPlayerNews {

    class ChangePlayer(val playerType: ArgumentType) : MusicPlayerNews

    object InitialNews : MusicPlayerNews
}