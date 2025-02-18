package com.andreich.musicplayer_feature

import com.andreich.musicplayer_feature.di.MusicPlayerComponent

interface MusicPlayerComponentDependencies {

    fun getAudioPlayerComponent(): MusicPlayerComponent
}