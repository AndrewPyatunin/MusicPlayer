package com.andreich.musicplayer_feature.music_player.ui.mapper

interface ModelToUiMapper<I, O> {

    fun map(from: I): O
}