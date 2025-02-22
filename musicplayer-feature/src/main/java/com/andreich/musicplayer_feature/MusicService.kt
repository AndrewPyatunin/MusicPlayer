package com.andreich.musicplayer_feature

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.andreich.musicplayer_feature.common.ArgumentType
import javax.inject.Inject
import javax.inject.Named

class MusicService : MediaSessionService() {

    companion object {
        @Volatile
        var playerType: ArgumentType = ArgumentType.HOME

        const val NOTIFICATION_ID = 111
    }
    private val component by lazy { (applicationContext as? MusicPlayerComponentDependencies)?.getAudioPlayerComponent() }

    @Named("LocalPlayer")
    @Inject
    lateinit var localPlayer: ExoPlayer

    @Named("RemotePlayer")
    @Inject
    lateinit var remotePlayer: ExoPlayer

    lateinit var player: ExoPlayer

    lateinit var mediaSession: MediaSession

    private fun setupMediaSession(): MediaSession {
        return MediaSession.Builder(this.applicationContext, player).build()
    }

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        component?.inject(this)
        player = when (playerType) {
            ArgumentType.REMOTE -> {
                remotePlayer
            }

            ArgumentType.HOME -> {
                localPlayer
            }
        }
        mediaSession = setupMediaSession()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession.player
        if (!player.playWhenReady
            || player.mediaItemCount == 0
            || player.playbackState == Player.STATE_ENDED
        ) {
            stopSelf()
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }



    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession.run {
            release()
            player.release()
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
        super.onDestroy()
    }
}