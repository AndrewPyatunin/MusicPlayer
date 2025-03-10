package com.andreich.musicplayer.musicpayer_domain

import com.andreich.domain.model.Track
import com.andreich.domain.repo.MusicRepository
import com.andreich.domain.usecase.ClearDatabaseUseCase
import com.andreich.domain.usecase.GetRemoteTracksUseCase
import com.andreich.domain.usecase.LoadTrackUseCase
import com.andreich.domain.usecase.SearchTrackUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MusicRepositoryTest {

    private val musicRepository = mock<MusicRepository>()
    private val searchTrackUseCase = SearchTrackUseCase(musicRepository)
    private val getRemoteTracksUseCase = GetRemoteTracksUseCase(musicRepository)
    private val loadTrackUseCase = LoadTrackUseCase(musicRepository)
    private val fakeMusicRepository = object : MusicRepository {

        private val trackList: MutableList<Track> = mutableListOf()

        fun getTracks(): List<Track> {
            return trackList.toList()
        }

        override suspend fun clearDatabase() {
            trackList.clear()
        }

        fun addTracks(tracks: List<Track>) {
            trackList.addAll(tracks)
        }

        override suspend fun getSavedTracks(query: String?): List<Track> {
            return emptyList()
        }

        override fun getRemoteTrack(id: Long): Flow<Track> {
            return emptyFlow()
        }

        override fun searchTrack(query: String?): Flow<List<Track>> {
            return emptyFlow()
        }

        override fun getTracks(fromPlayer: Boolean): Flow<List<Track>> {
            return emptyFlow()
        }
    }

    private val clearDatabaseUseCase = ClearDatabaseUseCase(fakeMusicRepository)

    @Before
    fun setUp() {

    }

    @Test
    fun testSearchTrackWithNullQueryParam() = runTest {
        val expectedTracks = listOf(Track(id = 1, "title", filePath = "file_path"),
            Track(id = 2, "title2", filePath = "file_path2"),
            Track(id = 3, "title3", filePath = "file_path3")
        )
        `when`(musicRepository.searchTrack(null)).thenReturn(flowOf(expectedTracks))
        val result = searchTrackUseCase(null).first()

        assertEquals(expectedTracks, result)
        verify(musicRepository).searchTrack(null)
    }

    @Test
    fun getRemoteTracksWithTrueParam() = runTest {
        val expectedTracks = listOf(Track(id = 1, "title", filePath = "file_path"),
            Track(id = 2, "title2", filePath = "file_path2"),
            Track(id = 3, "title3", filePath = "file_path3")
        )
        `when`(musicRepository.getTracks(true)).thenReturn(flowOf(expectedTracks))
        val result = getRemoteTracksUseCase(true).first()

        assertEquals(expectedTracks, result)
        verify(musicRepository).getTracks(true)
    }

    @Test
    fun getRemoteTracksWithFalseParam() = runTest {
        val expectedTracks = listOf(Track(id = 1, "title", filePath = "file_path"),
            Track(id = 2, "title2", filePath = "file_path2"),
            Track(id = 3, "title3", filePath = "file_path3")
        )
        `when`(musicRepository.getTracks(false)).thenReturn(flowOf(expectedTracks))
        val result = getRemoteTracksUseCase(false).first()

        assertEquals(expectedTracks, result)
        verify(musicRepository).getTracks(false)
    }

    @Test
    fun getRemoteTrackWithIdCheck() = runTest {
        val id = 5L
        val expectedTrack = Track(id = id, title = "title", filePath = "file_path")
        `when`(musicRepository.getRemoteTrack(id)).thenReturn(flowOf(expectedTrack))
        val result = loadTrackUseCase(id).first()
        assertEquals(expectedTrack, result)
        verify(musicRepository).getRemoteTrack(id)
    }

    @Test
    fun clearDatabaseUseCaseCheckIfRemoveAllElements() = runTest {
        val tracks = listOf(Track(id = 1, "title", filePath = "file_path"),
            Track(id = 2, "title2", filePath = "file_path2"),
            Track(id = 3, "title3", filePath = "file_path3")
        )
        fakeMusicRepository.addTracks(tracks)
        clearDatabaseUseCase()
        assertTrue(fakeMusicRepository.getTracks().isEmpty())
    }
}