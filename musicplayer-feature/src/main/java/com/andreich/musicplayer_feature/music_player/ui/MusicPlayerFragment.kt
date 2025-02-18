package com.andreich.musicplayer_feature.music_player.ui

import android.content.ComponentName
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import androidx.navigation.fragment.navArgs
import com.andreich.musicplayer_feature.MusicPlayerComponentDependencies
import com.andreich.musicplayer_feature.MusicService
import com.andreich.musicplayer_feature.R
import com.andreich.musicplayer_feature.common.ArgumentType
import com.andreich.musicplayer_feature.common.ViewModelFactory
import com.andreich.musicplayer_feature.databinding.FragmentMusicPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch
import javax.inject.Inject


class MusicPlayerFragment : Fragment() {

    private val safeArgs by navArgs<MusicPlayerFragmentArgs>()

    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding: FragmentMusicPlayerBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val component by lazy { (requireActivity().applicationContext as? MusicPlayerComponentDependencies)?.getAudioPlayerComponent() }

    val sessionToken by lazy {
        SessionToken(
            requireContext(),
            ComponentName(requireContext(), MusicService::class.java)
        )
    }

    val controllerFuture by lazy {
        MediaController.Builder(requireContext(), sessionToken).buildAsync()
    }

    val controller by lazy { controllerFuture.get() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val playerView: PlayerView by lazy { binding.playerView }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MusicPlayerViewModel::class.java]
    }

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MUSIC_PLAYER", "created")
        component?.inject(this)
        Log.d("MUSIC_PLAYER_SAFE_ARGS", safeArgs.type.toString())
        when (safeArgs.type) {
            ArgumentType.REMOTE -> viewModel.sendIntent(UserIntent.LoadRemoteTracks)
            ArgumentType.HOME -> viewModel.sendIntent(UserIntent.LoadHomeTracks)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    @OptIn(UnstableApi::class)
    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        playerView.player?.stop()
        controller.removeListener(listener)
        super.onDestroy()

    }

    @OptIn(UnstableApi::class)
    private fun initPlayer(state: MusicPlayerState) {
        controllerFuture.addListener({
            controller.stop()
            val mediaMetadata = controller.mediaMetadata
            with(binding) {
                albumTitle.text = mediaMetadata.albumTitle ?: "Unknown album"
                artistName.text = mediaMetadata.artist ?: "Unknown artist"
                trackTitle.text =
                    resources.getString(R.string.track, mediaMetadata.title ?: "Unknown track")
            }
            try {
                val firstItemIndex = state.playList.indexOfFirst {
                    it.id == safeArgs.id
                }
                val listTracks = state.playList.map {
                    Log.d("ARTWORK", it.picture.toString())
                    MediaItem.Builder()
                        .setUri(it.uri)
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setAlbumArtist(it.artist)
                                .setDisplayTitle(it.title)
                                .setSubtitle(it.displayName)
                                .setAlbumTitle(it.album)
                                .setArtworkUri(it.picture)
                                .build()
                        )
                        .build()
                }.toList()
                if (firstItemIndex != -1) controller.setMediaItem(listTracks[firstItemIndex])
                controller.addMediaItems(listTracks.minusElement(listTracks[firstItemIndex]))
                controller.prepare()
                controller.play()
                playerView.player = controller
            } catch (e: Exception) {

                Toast.makeText(this.requireContext(), e.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
            observeMetadata()
        }, MoreExecutors.directExecutor())
    }

    private val listener = object : Player.Listener {
        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            val artworkUri = mediaMetadata.artworkUri
            with(binding) {
                albumTitle.text = mediaMetadata.albumTitle ?: "Unknown album"
                artistName.text = mediaMetadata.artist ?: "Unknown artist"
                trackTitle.text =
                    resources.getString(R.string.track, mediaMetadata.title ?: "Unknown track")
            }
            Glide.with(this@MusicPlayerFragment).load(artworkUri)
                .into(object : CustomTarget<Drawable>() {
                    @OptIn(UnstableApi::class)
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        playerView.defaultArtwork = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })
        }
    }

    @OptIn(UnstableApi::class)
    private fun observeMetadata() {
        controller.addListener(listener)
    }

    @OptIn(UnstableApi::class)
    private fun observeViewModel() {
        Log.d("MUSIC_PLAYER", "observe")
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    Log.d("MUSIC_PLAYER_obeserve", it.playList.toString())
                    initPlayer(it)
                }
            }
        }

    }
}