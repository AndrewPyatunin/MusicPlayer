package com.andreich.musicplayer_feature.di

import android.content.Context
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependencies
import com.andreich.musicplayer_feature.MusicService
import com.andreich.musicplayer_feature.music_home_list.MusicHomeFragment
import com.andreich.musicplayer_feature.music_player.ui.MusicPlayerFragment
import com.andreich.musicplayer_feature.music_remote_list.ui.MusicRemoteFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [MusicPlayerModule::class, UseCaseModule::class, ExoPlayerModule::class, ViewModelModule::class],
    dependencies = [MusicPlayerFeatureDependencies::class]
)
interface MusicPlayerComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            musicPlayerFeatureDependencies: MusicPlayerFeatureDependencies
        ): MusicPlayerComponent
    }

    fun inject(fragment: MusicPlayerFragment)

    fun inject(fragment: MusicHomeFragment)

    fun inject(fragment: MusicRemoteFragment)

    fun inject(service: MusicService)
}