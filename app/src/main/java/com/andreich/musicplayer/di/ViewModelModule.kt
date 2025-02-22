package com.andreich.musicplayer.di

import androidx.lifecycle.ViewModel
import com.andreich.musicplayer.ui.MainViewModel
import com.andreich.musicplayer_feature.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

}