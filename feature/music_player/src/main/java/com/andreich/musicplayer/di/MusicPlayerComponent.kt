package com.andreich.musicplayer.di

import com.andreich.musicplayer.ui.MusicPlayerFragment
import dagger.Component

@Component(modules = [MusicPlayerModule::class, UseCaseModule::class, ExoPlayerModule::class])
interface MusicPlayerComponent {

    fun inject(fragment: MusicPlayerFragment)
}