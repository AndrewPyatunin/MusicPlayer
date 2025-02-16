package com.andreich.musicplayer_feature.di

import androidx.lifecycle.ViewModel
import com.andreich.musicplayer_feature.music_home_list.MusicHomeViewModel
import com.andreich.musicplayer_feature.music_player.ui.MusicPlayerViewModel
import com.andreich.musicplayer_feature.music_remote_list.ui.MusicRemoteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MusicHomeViewModel::class)
    @Binds
    fun bindMusicHomeViewModel(impl: MusicHomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MusicRemoteViewModel::class)
    @Binds
    fun bindMusicRemoteViewModel(impl: MusicRemoteViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MusicPlayerViewModel::class)
    @Binds
    fun bindMusicPlayerViewModel(impl: MusicPlayerViewModel): ViewModel

}