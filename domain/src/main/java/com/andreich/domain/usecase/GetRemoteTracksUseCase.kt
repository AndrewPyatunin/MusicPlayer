package com.andreich.domain.usecase

import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import kotlinx.coroutines.flow.Flow

class GetRemoteTracksUseCase(
    private val repository: MusicRepository
) {

    operator fun invoke(): Flow<List<Track>> {
        return repository.getTracks()
    }
}