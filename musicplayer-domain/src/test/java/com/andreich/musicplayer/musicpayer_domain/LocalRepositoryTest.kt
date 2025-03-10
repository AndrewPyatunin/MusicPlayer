package com.andreich.musicplayer.musicpayer_domain

import com.andreich.domain.model.AudioModel
import com.andreich.domain.repo.LocalRepository
import com.andreich.domain.usecase.GetHomeTracksUseCase
import com.andreich.domain.usecase.SearchHomeTrackUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LocalRepositoryTest {

    private val repository = object : LocalRepository {

        val listAudio = listOf(
            AudioModel("uri", "name", 1, "artist", "data", 100, "title", "album"),
            AudioModel("uri", "name", 2, "artist", "data", 100, "title", "album"),
            AudioModel("uri", "name", 3, "artist", "data", 100, "title", "album")
        )

        override fun getAudioData(): List<AudioModel> {
            return listAudio
        }

        override fun searchTrack(query: String): List<AudioModel> {
            return listAudio.filter {
                it.title.contains(query, true) ||
                        it.displayName.contains(query, true) ||
                        it.artist.contains(query, true) ||
                        it.album.contains(query, true)
            }
        }
    }

    val getHomeTracksUseCase = GetHomeTracksUseCase(repository)
    val searchHomeTrackUseCase = SearchHomeTrackUseCase(repository)

    @Test
    fun getHomeTracksUseCaseCheck() {
        val expected = repository.getAudioData()
        val result = getHomeTracksUseCase()
        assertEquals(expected.size, result.size)
        assertEquals(expected, result)
    }

    @Test
    fun searchTrackUseCaseCheckWithQuery() {
        val query = "name"
        val expected = repository.searchTrack(query)
        val result = searchHomeTrackUseCase(query)
        assertEquals(expected, result)
    }
}