package com.andreich.musicplayer_feature.music_player.ui.mapper

import android.net.Uri
import com.andreich.domain.model.AudioModel
import com.andreich.musicplayer_feature.music_player.ui.AudioTrack
import javax.inject.Inject

class AudioModelToAudioTrack @Inject constructor() : ModelToUiMapper<AudioModel, AudioTrack> {

    override fun map(from: AudioModel): AudioTrack {
        return with(from) {
            AudioTrack(id, uri, Uri.parse(uri), displayName, artist, data, duration, title, album)
        }
    }
}