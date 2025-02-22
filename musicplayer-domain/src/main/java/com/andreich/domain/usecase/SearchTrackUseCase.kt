package com.andreich.domain.usecase

import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import kotlinx.coroutines.flow.Flow

class SearchTrackUseCase(
    private val repository: MusicRepository
) {

    operator fun invoke(query: String?): Flow<List<Track>> {
        return repository.searchTrack(query)
    }
}