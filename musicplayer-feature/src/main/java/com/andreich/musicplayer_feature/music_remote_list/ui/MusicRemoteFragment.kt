package com.andreich.musicplayer_feature.music_remote_list.ui

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreich.musicplayer_feature.MusicPlayerComponentDependencies
import com.andreich.musicplayer_feature.common.ViewModelFactory
import com.andreich.ui.BaseSearchFragment
import com.andreich.ui.BaseViewModel
import com.andreich.ui.MusicItem
import javax.inject.Inject

class MusicRemoteFragment : BaseSearchFragment() {

    private val component by lazy { (requireActivity().applicationContext as? MusicPlayerComponentDependencies)?.getAudioPlayerComponent() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
    }

    override val viewModel: BaseViewModel by viewModels<MusicRemoteViewModel> { viewModelFactory }

    override fun navigate(musicItem: MusicItem) {
        findNavController().navigate(
            MusicRemoteFragmentDirections.actionMusicRemoteFragmentToMusicPlayerFragment2(
                id = musicItem.id
            )
        )
    }
}