package com.andreich.musicplayer_feature.di

import com.andreich.domain.repo.LocalRepository
import com.andreich.domain.repo.MusicRepository
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.domain.usecase.SearchHomeTrackUseCase
import com.andreich.domain.usecase.SearchTrackUseCase
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

    @Provides
    fun provideSearchTrackUseCase(repository: MusicRepository): SearchTrackUseCase {
        return SearchTrackUseCase(repository)
    }

    @Provides
    fun searchHomeTrackUseCase(repository: LocalRepository): SearchHomeTrackUseCase {
        return SearchHomeTrackUseCase(repository)
    }
}