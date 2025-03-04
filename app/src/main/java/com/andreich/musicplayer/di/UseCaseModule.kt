package com.andreich.musicplayer.di

import com.andreich.domain.repo.MusicRepository
import com.andreich.domain.usecase.ClearDatabaseUseCase
import com.andreich.musicplayer_feature.common.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideClearDataBaseUseCase(repository: MusicRepository): ClearDatabaseUseCase {
        return ClearDatabaseUseCase(repository)
    }
}