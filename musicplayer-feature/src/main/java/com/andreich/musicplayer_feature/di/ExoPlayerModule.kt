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
import com.andreich.musicplayer_feature.DownloadManagerBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ExoPlayerModule {

    @OptIn(UnstableApi::class)
    @Provides
    fun provideErrorHandlingPolicy(): LoadErrorHandlingPolicy {

        return object : DefaultLoadErrorHandlingPolicy() {
            override fun getRetryDelayMsFor(loadErrorInfo: LoadErrorHandlingPolicy.LoadErrorInfo): Long {
                return when (loadErrorInfo.exception) {
                    is HttpDataSource.InvalidResponseCodeException -> {
                        val responseCode = (loadErrorInfo.exception as HttpDataSource.InvalidResponseCodeException).responseCode
                        if (responseCode == 403) {
                            Log.e("ExoPlayer", "Ошибка 403, пропускаем трек")
                            C.TIME_UNSET // Не пытаться повторить загрузку
                        } else {
                            super.getRetryDelayMsFor(loadErrorInfo)
                        }
                    }
                    else -> super.getRetryDelayMsFor(loadErrorInfo)
                }
            }
        }
    }

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

        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context))
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
    }

    @Named("LocalPlayer")
    @OptIn(UnstableApi::class)
    @Provides
    fun provideLocalPlayer(context: Context, audioAttributes: AudioAttributes): ExoPlayer {
        val mediaSourceFactory = DefaultMediaSourceFactory(context)
        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context))
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
    }

    @Provides
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()
}