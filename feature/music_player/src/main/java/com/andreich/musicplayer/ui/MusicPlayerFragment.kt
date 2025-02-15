package com.andreich.musicplayer.ui

import android.content.ComponentName
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.session.SessionToken
import androidx.media3.session.MediaController
import androidx.media3.ui.PlayerView
import com.andreich.common.ViewModelFactory
import com.andreich.music_player.R
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val URI = "\n" +
        "https://dl.zaycev.net/track_auth/17577802/BFkvhxHnjQLH6QwRcC3pEoRQvZ2U9fggBTAcajVJWDY5n2xJvAY3TAd32LNEFjaJxDtvMWstnNjTsekooWiZ4abMHTbeMzYB25GY1hqDWBuzvyDNSXZhsY18rKMW2P5viwewiy5JZCR7aRHavufakoUNDFDBe4WGAYk8N3LDtNigfDLodAHEaFPqbjChyvobZVwPKi4nB3VyDnSqGEWxK5Xvdz79edRRW3DLny4mQa2dCfH73rDVsV9dNCWEBZXwiJBhnuEHCWQTPXUkhPTXkvkFcpgNGHTTg6o6W6mQY163JmQtJ3XuLH7wpFQd72Jd75KSTfWnVDRv84tCpG6QukSmGoQBdw"
private const val URI_2 = "https://dl.zaycev.net/track/25020728/7oVnu1M3DcJ2644QznYLG74qf7LLBhHEBdebXDJ8n2e2CkQaEXo3kFrSo38s1aiD93vWiGQ7aoVrxQnJKsAhPZ3dMvTxarY3gSxbRbUH8p2xSaXYQNhPPZUbukkv8eHGcN1gDS28UC2pFKyaCRxKErLXZKZctxSJjze9hV2C2sYHsAKQ24Z3h1tUNXf4gPyT9Fec3mMy3yWX15D2KChx9MJFLFEsgcaveMtJUiFBfESZ7kQD491YrBrHCRYAJ2EZxbX2sZ7JgKjUGe2oC4wrkfK2eX7aNNWRaf7cR6yKBdg6THRipLxoFKZMQrh5yk3vM8vnh6pqhfdjUgFc3N5rqGmVuP3HG"/*?hdnea=exp=1739522830~acl=/api/1/1/c/4/d/0/c4d7dbe3524ba59d2ad06d8cccd2484f.mp3*~data=user_id=0,application_id=42~hmac=f07140e8ec4e50f81aeead9dde7d2b3796c2c62cd62bafce90488b25eb002c3e*/
private const val URI_3 = "https://cdnt-preview.dzcdn.net/api/1/1/e/9/2/0/e929fb0e6a929aa247259380f3466e82.mp3?hdnea=exp=1739548158~acl=/api/1/1/e/9/2/0/e929fb0e6a929aa247259380f3466e82.mp3*~data=user_id=0,application_id=42~hmac=d675bee9e4ed024f389db6580800caea870d0a337adaea2d4d4d1a3ca21182e0"
class MusicPlayerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[MusicPlayerViewModel::class.java] }

    lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.sendIntent(UserIntent.LoadTracks)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        observeViewModel()
        return inflater.inflate(R.layout.fragment_music_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = view.findViewById(R.id.player_view)
    }

    @OptIn(UnstableApi::class)
    override fun onStart() {
        super.onStart()
        val sessionToken = SessionToken(requireContext(), ComponentName(requireContext(), MusicService::class.java))
        val mediaItem = MediaItem.fromUri(URI)
        val mediaItem2 = MediaItem.fromUri(URI_2)
        val mediaItem3 = MediaItem.fromUri(URI_3)
//        Glide.with(this)
//            .load(mediaItem.mediaMetadata.artworkUri)
//            .centerCrop()
//            .into(object : CustomTarget<Drawable>() {
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: Transition<in Drawable>?
//                ) {
//                    playerView.defaultArtwork = resource
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                }
//            })
        val controllerFuture = MediaController.Builder(requireContext(), sessionToken).buildAsync()
        val mediaSource =
            HlsMediaSource.Factory(DefaultHttpDataSource.Factory()).createMediaSource(mediaItem)
        controllerFuture.addListener(
            {
                val controller = controllerFuture.get()
                controller.setMediaItem(mediaItem)
                controller.addMediaItem(mediaItem2)
                controller.addMediaItem(mediaItem3)
                controller.prepare()
                controller.play()
                // Call controllerFuture.get() to retrieve the MediaController.
                // MediaController implements the Player interface, so it can be
                // attached to the PlayerView UI component.
                playerView.setPlayer(controllerFuture.get())
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        playerView.player?.stop()
    }

    private fun initPlayer(state: MusicPlayerState) {
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    initPlayer(it)
                }
            }
        }

    }

    companion object {

        const val URI_VIDEO = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"

        @JvmStatic
        fun newInstance() =
            MusicPlayerFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}