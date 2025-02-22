package com.andreich.musicplayer_feature.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.upstream.DefaultLoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.session.MediaSession
import com.andreich.musicplayer_feature.DownloadManagerBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RemotePlayerModule {

    @Provides
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @Named("RemotePlayer")
    @OptIn(UnstableApi::class)
    @Provides
    fun provideRemotePlayer(context: Context, audioAttributes: AudioAttributes): ExoPlayer {
        val loadErrorHandlingPolicy = object : DefaultLoadErrorHandlingPolicy() {
            override fun getRetryDelayMsFor(loadErrorInfo: LoadErrorHandlingPolicy.LoadErrorInfo): Long {
                return when (loadErrorInfo.exception) {
                    is HttpDataSource.InvalidResponseCodeException -> {
                        val responseCode = (loadErrorInfo.exception as HttpDataSource.InvalidResponseCodeException).responseCode
                        if (responseCode == 403) {
                            Log.e("ExoPlayer", "Ошибка 403, пропускаем трек")
                            C.TIME_UNSET
                        } else {
                            super.getRetryDelayMsFor(loadErrorInfo)
                        }
                    }
                    else -> super.getRetryDelayMsFor(loadErrorInfo)
                }
            }
        }
        val mediaSourceFactory = ProgressiveMediaSource.Factory(DownloadManagerBuilder.getCacheFactory(context)).setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
        val mediaFactory = DefaultMediaSourceFactory(context)
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
}