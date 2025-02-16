package com.andreich.musicplayer_feature.music_remote_list.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.andreich.musicplayer_feature.common.ViewModelFactory
import com.andreich.musicplayer_feature.music_home_list.MusicHomeFragment
import com.andreich.ui.BaseSearchFragment
import com.andreich.ui.BaseViewModel
import javax.inject.Inject

class MusicRemoteFragment : BaseSearchFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override val viewModel: BaseViewModel by lazy { ViewModelProvider(this, viewModelFactory)[MusicRemoteViewModel::class] }

    companion object {

        @JvmStatic
        fun newInstance() = MusicRemoteFragment()
    }
}