package com.andreich.musicplayer

import android.app.Application
import com.andreich.musicplayer.di.DaggerAppComponent
import com.andreich.musicplayer_feature.MusicPlayerComponentDependencies
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependencies
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependenciesProvider
import com.andreich.musicplayer_feature.di.DaggerMusicPlayerComponent
import com.andreich.musicplayer_feature.di.MusicPlayerComponent

class MyApp: Application(), MusicPlayerFeatureDependenciesProvider, MusicPlayerComponentDependencies {

    val component by lazy { DaggerAppComponent.factory().create(this) }

    val musicPlayerComponent by lazy { DaggerMusicPlayerComponent.factory().create(this, getMusicPlayerFeatureDependencies())}

    override fun getMusicPlayerFeatureDependencies(): MusicPlayerFeatureDependencies {
        return component
    }

    override fun getAudioPlayerComponent(): MusicPlayerComponent {
        return musicPlayerComponent
    }
}