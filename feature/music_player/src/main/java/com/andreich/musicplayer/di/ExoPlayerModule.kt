package com.andreich.musicplayer.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.andreich.musicplayer.ui.DownloadManagerBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExoPlayerModule {

    @OptIn(UnstableApi::class)
    @Singleton
    @Provides
    fun providePlayer(context: Context): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context)).setMediaSourceFactory(
            DefaultMediaSourceFactory(context)
                .setDataSourceFactory(DownloadManagerBuilder.getCacheFactory(context))
        ).build()
    }
}