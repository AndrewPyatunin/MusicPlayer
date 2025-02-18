package com.andreich.musicplayer_feature.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides

@Module
class ExoPlayerModule {

    @OptIn(UnstableApi::class)
    @Provides
    fun providePlayer(context: Context, audioAttributes: AudioAttributes): ExoPlayer {
        val mediaSourceFactory = DefaultMediaSourceFactory(context)

        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context))
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
    }

    @Provides
    fun provideMediaSession(context: Context, player: ExoPlayer): MediaSession {
        return MediaSession.Builder(context, player).build()
    }

    @Provides
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
        .setUsage(C.USAGE_MEDIA)
        .build()
}