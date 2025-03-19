package com.andreich.data

import com.andreich.data.datasource.local.MusicDataSource
import com.andreich.musicplayer_database.entity.TrackEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocalMusicDataSourceTest {

    private lateinit var musicDataSource: MusicDataSource
    private val trackEntity = TrackEntity(0, "track0", "artist0", filePath = "filePath0")
    private val trackList = listOf(
        trackEntity,
        TrackEntity(1, "track1", "artist1", filePath = "filePath1"),
        TrackEntity(2, "track2", "artist2", filePath = "filePath2")
    )

    @Before
    fun setUp() {
        musicDataSource = object : MusicDataSource {
            private val listTracks: MutableList<TrackEntity> = mutableListOf()

            override suspend fun insertTrack(trackEntity: TrackEntity) {
                listTracks.add(trackEntity)
            }

            override suspend fun insertTrackList(list: List<TrackEntity>) {
                listTracks.addAll(list)
            }

            override suspend fun clearDatabase() {
                listTracks.clear()
            }

            override suspend fun getQueryTracks(query: String?): List<TrackEntity> {
                return listTracks.filter {
                    it.title.contains(query ?: "") || it.artistName.contains(
                        query ?: ""
                    ) || it.albumEntity?.title?.contains(query ?: "") == true
                }
            }

            override suspend fun getTracks(): List<TrackEntity> {
                return listTracks
            }

            override fun getTrack(id: Long): Flow<TrackEntity> {
                return flowOf(listTracks.first { it.id == id })
            }

        }
    }

    @Test
    fun insertTrackTestAndGet() = runTest {
        musicDataSource.insertTrack(trackEntity)
        val result = musicDataSource.getTrack(0).first()
        assertEquals(trackEntity, result)
    }

    @Test
    fun insertTrackList() = runTest {
        musicDataSource.insertTrackList(trackList)
        val result = musicDataSource.getTracks()
        assertEquals(trackList.size, result.size)
        assertEquals(trackList[result.size - 1], result[trackList.size - 1])
    }

    @Test
    fun clearDatabaseTest() = runTest {
        musicDataSource.insertTrackList(trackList)
        musicDataSource.clearDatabase()
        val result = musicDataSource.getTracks()
        assertNotNull(result)
        assertEquals(0, result.size)
    }

    @Test
    fun insertTrackListAndGetQueryTracksTest() = runTest {
        musicDataSource.insertTrackList(trackList)
        val result = musicDataSource.getQueryTracks("2")
        assertNotNull(result)
        assertEquals(result[0], trackList[2])
    }
}