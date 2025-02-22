package com.andreich.domain.usecase

import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import kotlinx.coroutines.flow.Flow

class LoadTrackUseCase(
    private val repository: MusicRepository
) {

    operator fun invoke(id: Long): Flow<Track> {
        return repository.getRemoteTrack(id)
    }
}