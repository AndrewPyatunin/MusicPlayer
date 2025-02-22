package com.andreich.musicplayer_feature.music_player.ui.mapper

import android.net.Uri
import com.andreich.domain.model.Track
import com.andreich.musicplayer_feature.music_player.ui.AudioTrack
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject

class TrackToAudioTrackMapper @Inject constructor() : ModelToUiMapper<Track, AudioTrack> {

    override fun map(from: Track): AudioTrack {
        return with(from) {
            AudioTrack(
                id = id,
                url = url.toString(),
                uri = Uri.parse(filePath),
                displayName = title,
                title = title,
                data = "",
                artist = artist?.name ?: "",
                album = album?.title ?: "",
                duration = duration,
                picture = Uri.parse(cover),
                pictureBig = Uri.parse(coverBig ?: album?.coverBig)
            )
        }
    }
}