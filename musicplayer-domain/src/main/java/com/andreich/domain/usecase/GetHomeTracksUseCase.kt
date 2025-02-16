package com.andreich.domain.usecase

import com.andreich.domain.model.AudioModel
import com.andreich.domain.repo.LocalRepository

class GetHomeTracksUseCase(
    private val repository: LocalRepository
) {
    operator fun invoke(): List<AudioModel> {
        return repository.getAudioData()
    }
}