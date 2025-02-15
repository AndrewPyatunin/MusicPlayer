package com.andreich.musicplayer.di

import com.andreich.domain.repo.MusicRepository
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetRemoteTracksUseCase(repository: MusicRepository): GetRemoteTracksUseCase {
        return GetRemoteTracksUseCase(repository)
    }
}