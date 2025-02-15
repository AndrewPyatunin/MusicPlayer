package com.andreich.musicplayer.ui.mapper

import com.andreich.domain.model.Track
import com.andreich.musicplayer.ui.AudioTrack
import javax.inject.Inject

class TrackToAudioTrackMapper @Inject constructor() : ModelToUiMapper<Track, AudioTrack> {

    override fun map(from: Track): AudioTrack {
        return with(from) {
            AudioTrack(
                id = id.toLong(),
                url = url.toString(),
                displayName = title,
                title = title,
                data = "",
                artist = artist?.name ?: "",
                album = album?.title ?: "",
                duration = duration
            )
        }
    }
}