package com.andreich.data.di

import com.andreich.data.AudioContentResolver
import com.andreich.data.repo.MusicRepositoryImpl
import com.andreich.domain.repo.LocalRepository
import com.andreich.domain.repo.MusicRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindMusicRepository(impl: MusicRepositoryImpl): MusicRepository

    @Binds
    fun bindLocalRepository(impl: AudioContentResolver): LocalRepository
}