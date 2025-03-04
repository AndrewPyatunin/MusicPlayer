package com.andreich.musicplayer_feature.music_player.ui

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource
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
import com.andreich.ui.BaseUiIntent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class MusicPlayerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var controllerListener: Runnable? = null

    private val safeArgs by navArgs<MusicPlayerFragmentArgs>()

    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding: FragmentMusicPlayerBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val component by lazy { (requireActivity().applicationContext as? MusicPlayerComponentDependencies)?.getAudioPlayerComponent() }

    private val sessionToken by lazy {
        SessionToken(
            requireContext(),
            ComponentName(requireContext(), MusicService::class.java)
        )
    }

    private val playerSwitchReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == MusicService.ACTION_PLAYER_SWITCHED) {
                val newType = ArgumentType.valueOf(intent.getStringExtra(MusicService.EXTRA_PLAYER_TYPE) ?: "HOME")
                Log.d("MusicService_fragment", "Player switched to: $newType")
                binding.playerView.player = controller
            }
        }
    }

    private val controllerFuture by lazy {
        Log.d("MusicService_fragment", "controllerFuture_lazy")
        MediaController.Builder(requireContext(), sessionToken).buildAsync()
    }

    private lateinit var controller: MediaController

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
        component?.inject(this)
        val playerType = safeArgs.type
        MusicService.playerType = playerType
        when (playerType) {
            ArgumentType.REMOTE -> {
                viewModel.sendIntent(UserIntent.LoadRemoteTracks)
            }

            ArgumentType.HOME -> {
                viewModel.sendIntent(UserIntent.LoadHomeTracks)
            }
        }
        val serviceIntent = Intent(context, MusicService::class.java)
        requireContext().startService(serviceIntent)
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

    override fun onDestroy() {
        controller.clearMediaItems()
        playerView.player?.stop()
        controllerListener = null
        controller.removeListener(playerListener)
        controller.release()
        _binding = null
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(context, MusicService::class.java)
        requireContext().startService(serviceIntent)

        val filter = IntentFilter(MusicService.ACTION_PLAYER_SWITCHED)
        requireContext().registerReceiver(playerSwitchReceiver, filter,
            Context.RECEIVER_NOT_EXPORTED)
    }

    override fun onStop() {
        requireContext().unregisterReceiver(playerSwitchReceiver)
        super.onStop()
    }

    private inline fun <T> List<T>.splitListIntoTwo(predicate: T.() -> Boolean): Pair<List<T>, List<T>> {
        val validList = mutableListOf<T>()
        val invalidList = mutableListOf<T>()
        forEach {
            if (it.predicate()) {
                validList.add(it)
            } else invalidList.add(it)
        }
        return Pair(validList, invalidList)
    }

    private fun AudioTrack.mapAudioTrackToMediaItem(): MediaItem {
        return MediaItem.Builder()
            .setUri(this.uri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(this.artist)
                    .setTitle(this.title)
                    .setAlbumArtist(this.artist)
                    .setDisplayTitle(this.title)
                    .setSubtitle(this.displayName)
                    .setAlbumTitle(this.album)
                    .setArtworkUri(this.pictureBig ?: this.picture)
                    .build()
            )
            .build()
    }

    private fun MusicPlayerState.createListener(): Runnable = Runnable {
        Log.d("MusicService_fragment", "create_listener")
        controller = controllerFuture.get()
        val mediaMetadata = controller.mediaMetadata
        with(binding) {
            albumTitle.text = mediaMetadata.albumTitle ?: "Unknown album"
            artistWithTrackTitle.text = resources.getString(
                R.string.track,
                mediaMetadata.artist ?: "Unknown artist",
                mediaMetadata.title ?: "Unknown title"
            )
        }
        try {
            var firstItemIndex = this.playList.indexOfFirst {
                it.id == safeArgs.id
            }
            val mediaItem = controller.currentMediaItem
            var position = controller.currentPosition

            mediaItem?.let {
                UserIntent.SaveState(it, position)
            }
            if (firstItemIndex < 0 || firstItemIndex >= playList.size) firstItemIndex = 0
            val startList = playList.subList(firstItemIndex, playList.size)
            val endList = playList.subList(0, firstItemIndex)
            val playList = startList + endList
            val validTracks = playList.map { it.mapAudioTrackToMediaItem() }
            if (!validTracks.contains(mediaItem)) {
                controller.stop()
            }
            if (controller.playbackState == Player.STATE_IDLE) {
                controller.addMediaItems(validTracks)

                controller.prepare()
                controller.play()
                playerView.player = controller
            }


        } catch (e: Exception) {
            Toast.makeText(
                this@MusicPlayerFragment.requireContext(),
                e.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        observeMetadata()
    }

    private val playerListener = @UnstableApi
    object : Player.Listener {

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            val cause = error.cause
            if (cause is HttpDataSource.InvalidResponseCodeException) {
                val responseCode = cause.responseCode
                if (responseCode == 403) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        controller.playWhenReady = false
                        controller.prepare()
                        controller.seekToNextMediaItem()

                        controller.playWhenReady = true
                    }, 50)
                } else {
                    Log.e("ExoPlayer", "Ошибка при воспроизведении: ${error.message}")
                }
            } else {
                Log.e("ExoPlayer", "Ошибка при воспроизведении: ${error.message}")
            }
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            val artworkUri = mediaMetadata.artworkUri
            with(binding) {
                albumTitle.text = mediaMetadata.albumTitle ?: "Unknown album"
                artistWithTrackTitle.text = resources.getString(
                    R.string.track,
                    mediaMetadata.artist ?: "Unknown artist",
                    mediaMetadata.title ?: "Unknown title")
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

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }

    private fun observeMetadata() {
        controller.addListener(playerListener)
    }

    @OptIn(UnstableApi::class)
    private fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    if (it.playList.isNotEmpty() || controllerListener == null) {
                        Log.d("MusicService", "create_listener")
                        controllerListener = it.createListener()
                        controllerFuture.addListener(
                            controllerListener!!,
                            MoreExecutors.directExecutor()
                        )
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.news.collect {
                    when (it) {
                        is MusicPlayerNews.ChangePlayer -> {
                            changePlayerType(it.playerType)
                            viewModel.sendIntent(UserIntent.ClearStateNews)
                        }
                        MusicPlayerNews.InitialNews -> {

                        }
                    }
                }
            }
        }
    }

    private fun changePlayerType(newType: ArgumentType) {
        Log.d("MusicService_fragment", "Sending broadcast to switch player: $newType")

        val intent = Intent(MusicService.ACTION_SWITCH_PLAYER).apply {
            putExtra(MusicService.EXTRA_PLAYER_TYPE, newType.name)
        }
        requireContext().sendBroadcast(intent)
    }
}