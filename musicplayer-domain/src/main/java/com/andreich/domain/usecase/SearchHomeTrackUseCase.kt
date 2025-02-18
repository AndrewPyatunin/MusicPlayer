package com.andreich.domain.usecase

import com.andreich.domain.model.AudioModel
import com.andreich.domain.repo.LocalRepository

class SearchHomeTrackUseCase(
    private val repository: LocalRepository
) {

    operator fun invoke(query: String): List<AudioModel> {
        return repository.searchTrack(query)
    }
}