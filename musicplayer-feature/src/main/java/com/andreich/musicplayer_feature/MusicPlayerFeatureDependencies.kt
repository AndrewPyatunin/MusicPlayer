package com.andreich.musicplayer_feature

import android.app.Application
import com.andreich.domain.repo.MusicRepository

interface MusicPlayerFeatureDependencies {

    fun getRepository(): MusicRepository
}