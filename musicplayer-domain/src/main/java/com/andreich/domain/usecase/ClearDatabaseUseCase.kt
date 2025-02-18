package com.andreich.domain.usecase

import com.andreich.domain.repo.MusicRepository

class ClearDatabaseUseCase(
    private val repository: MusicRepository
) {

    suspend operator fun invoke() {
        repository.clearDatabase()
    }
}