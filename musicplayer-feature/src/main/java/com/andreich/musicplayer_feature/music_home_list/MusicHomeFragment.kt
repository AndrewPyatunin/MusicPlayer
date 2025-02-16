package com.andreich.musicplayer_feature.music_home_list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependenciesProvider
import com.andreich.musicplayer_feature.common.ViewModelFactory
import com.andreich.musicplayer_feature.di.DaggerMusicPlayerComponent
import com.andreich.ui.BaseSearchFragment
import com.andreich.ui.BaseViewModel
import javax.inject.Inject

class MusicHomeFragment : BaseSearchFragment() {

    val component by lazy { DaggerMusicPlayerComponent.builder().build() }

    init {
        component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: BaseViewModel by lazy { ViewModelProvider(this, viewModelFactory)[MusicHomeViewModel::class] }

    companion object {

        @JvmStatic
        fun newInstance() = MusicHomeFragment()
    }
}