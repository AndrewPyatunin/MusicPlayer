package com.andreich.musicplayer_feature.music_home_list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andreich.musicplayer_feature.MusicPlayerComponentDependencies
import com.andreich.musicplayer_feature.common.ViewModelFactory
import com.andreich.ui.BaseSearchFragment
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import javax.inject.Inject

class MusicHomeFragment : BaseSearchFragment() {

    private val component by lazy { (requireContext().applicationContext as? MusicPlayerComponentDependencies)?.getAudioPlayerComponent() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: BaseViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MusicHomeViewModel::class]
    }

    override fun navigate(musicItem: MusicItem) {
        findNavController().navigate(
            MusicHomeFragmentDirections.actionMusicHomeFragmentToMusicPlayerFragment(
                id = musicItem.id,
            )
        )
    }
}