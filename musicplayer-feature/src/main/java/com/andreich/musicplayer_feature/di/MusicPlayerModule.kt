package com.andreich.musicplayer_feature.di

import com.andreich.domain.model.AudioModel
import com.andreich.domain.model.Track
import com.andreich.musicplayer_feature.music_player.ui.AudioTrack
import com.andreich.musicplayer_feature.music_player.ui.mapper.AudioModelToAudioTrack
import com.andreich.musicplayer_feature.music_player.ui.mapper.ModelToUiMapper
import com.andreich.musicplayer_feature.music_player.ui.mapper.TrackToAudioTrackMapper
import dagger.Binds
import dagger.Module

@Module
interface MusicPlayerModule {

    @Binds
    fun bindTrackMapper(impl: TrackToAudioTrackMapper): ModelToUiMapper<Track, AudioTrack>

    @Binds
    fun bindAudioModelMapper(impl: AudioModelToAudioTrack): ModelToUiMapper<AudioModel, AudioTrack>
}