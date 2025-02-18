package com.andreich.domain.repo

import com.andreich.domain.model.AudioModel

interface LocalRepository {

    fun getAudioData(): List<AudioModel>

    fun searchTrack(query: String): List<AudioModel>
}