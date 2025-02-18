package com.andreich.musicplayer_feature

import com.andreich.domain.repo.LocalRepository
import com.andreich.domain.repo.MusicRepository

interface MusicPlayerFeatureDependencies {

    fun getRepository(): MusicRepository

    fun getLocalRepository(): LocalRepository
}