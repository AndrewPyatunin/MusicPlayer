package com.andreich.musicplayer_feature.di

import com.andreich.domain.repo.LocalRepository
import com.andreich.domain.repo.MusicRepository
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetRemoteTracksUseCase(repository: MusicRepository): GetRemoteTracksUseCase {
        return GetRemoteTracksUseCase(repository)
    }

    @Provides
    fun provideGetHomeTracksUseCase(repository: LocalRepository): GetHomeTracksUseCase {
        return GetHomeTracksUseCase(repository)
    }
}