package com.andreich.musicplayer_feature

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.andreich.musicplayer_feature.common.ArgumentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MusicService : MediaSessionService() {

    companion object {

        @Volatile
        var playerType: ArgumentType = ArgumentType.HOME

        const val NOTIFICATION_ID = 111

        const val ACTION_SWITCH_PLAYER = "com.andreich.musicplayer.SWITCH_PLAYER"
        const val EXTRA_PLAYER_TYPE = "player_type"
        const val ACTION_PLAYER_SWITCHED = "com.andreich.musicplayer.PLAYER_SWITCHED"
    }

    private val component by lazy { (applicationContext as? MusicPlayerComponentDependencies)?.getAudioPlayerComponent() }

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @Named("LocalPlayer")
    @Inject
    lateinit var localPlayer: ExoPlayer

    @Named("RemotePlayer")
    @Inject
    lateinit var remotePlayer: ExoPlayer

    lateinit var player: ExoPlayer

    lateinit var mediaSession: MediaSession

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_SWITCH_PLAYER -> {
                    val newType =
                        ArgumentType.valueOf(intent.getStringExtra(EXTRA_PLAYER_TYPE) ?: "HOME")
                    switchPlayer(newType)
                }
            }
        }
    }

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

        val filter = IntentFilter(ACTION_SWITCH_PLAYER)
        registerReceiver(broadcastReceiver, filter, RECEIVER_NOT_EXPORTED)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession.player
        if (!player.playWhenReady
            || player.mediaItemCount == 0
            || player.playbackState == Player.STATE_ENDED
        ) {
            stopSelf()
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        Log.d("MUSIC_SERVICE", "Destroy")
        mediaSession.run {
            release()
            player.release()
        }
        unregisterReceiver(broadcastReceiver)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
        super.onDestroy()
    }

    private fun switchPlayer(newType: ArgumentType) {
        Log.d("MusicService", "SwitchPlayer STARTED at: ${System.currentTimeMillis()}")

        serviceScope.launch {
            Log.d("MusicService", "Stopping and releasing old player")
            if (::player.isInitialized && player.playbackState != Player.STATE_IDLE) {
                Log.d("MusicService", "Stopping current player")
                player.stop()
                player.release()
            }
            Log.d("MusicService", "Setting new player")
            player = when (newType) {
                ArgumentType.REMOTE -> remotePlayer
                ArgumentType.HOME -> localPlayer
            }
            Log.d("MusicService", "Updating media session")
            mediaSession.player = player
            val responseIntent = Intent(ACTION_PLAYER_SWITCHED).apply {
                putExtra(EXTRA_PLAYER_TYPE, newType.name)
            }
            sendBroadcast(responseIntent)
        }

        Log.d("MusicService", "SwitchPlayer FINISHED at: ${System.currentTimeMillis()}")
    }
}