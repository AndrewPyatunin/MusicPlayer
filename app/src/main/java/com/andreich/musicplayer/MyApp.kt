package com.andreich.musicplayer

import android.app.Application
import com.andreich.musicplayer.di.DaggerAppComponent
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependencies
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependenciesProvider

class MyApp: Application(), MusicPlayerFeatureDependenciesProvider {

    val component by lazy { DaggerAppComponent.factory().create(this) }

    override fun getMusicPlayerFeatureDependencies(): MusicPlayerFeatureDependencies {
        return component
    }

}