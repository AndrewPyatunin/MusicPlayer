package com.andreich.musicplayer.di

import com.andreich.domain.model.Track
import com.andreich.musicplayer.ui.AudioTrack
import com.andreich.musicplayer.ui.mapper.ModelToUiMapper
import com.andreich.musicplayer.ui.mapper.TrackToAudioTrackMapper
import dagger.Binds
import dagger.Module

@Module
interface MusicPlayerModule {

    @Binds
    fun bindTrackMapper(impl: TrackToAudioTrackMapper): ModelToUiMapper<Track, AudioTrack>
//    private val getRemoteTracksUseCase: GetRemoteTracksUseCase,
}