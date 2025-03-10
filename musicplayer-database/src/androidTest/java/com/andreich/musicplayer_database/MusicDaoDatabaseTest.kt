package com.andreich.musicplayer_database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import com.andreich.musicplayer_database.database.MusicDao
import com.andreich.musicplayer_database.database.MusicDatabase
import com.andreich.musicplayer_database.entity.TrackEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import org.junit.After

import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MusicDaoDatabaseTest {

    private lateinit var database: MusicDatabase
    private lateinit var dao: MusicDao
    private lateinit var listTracks: List<TrackEntity>

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MusicDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.musicDao()
        listTracks = listOf(
            TrackEntity(id = 0, title = "track0", artistName = "artist0", filePath = "filePath0"),
            TrackEntity(id = 1, title = "track1", artistName = "artist1", filePath = "filePath1"),
            TrackEntity(id = 2, title = "track2", artistName = "artist2", filePath = "filePath2")
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetTrackById() = runTest {
        val id = 0L
        val track = TrackEntity(id = id, title = "track0", artistName = "artist0", filePath = "filePath0")
        dao.insertTrack(track)
        val result = dao.getTrack(id).first()
        assertNotNull(result)
        assertEquals(track, result)

    }

    @Test
    fun insert3TracksAndGetThem() = runTest {
        dao.insertTrackList(listTracks)
        val result = dao.getTracks()
        assertNotNull(result)
        assertEquals(listTracks.size, result.size)
        assertEquals(listTracks[0], result[0])
    }

    @Test
    fun insert3TracksAndClearDatabase() = runTest {
        dao.insertTrackList(listTracks)
        dao.clearTracks()
        val result = dao.getTracks()
        assertNotNull(result)
        assertEquals(emptyList<TrackEntity>(), result)
    }

    @Test
    fun insert3TracksAndTryToGetWithQuery() = runTest {
        dao.insertTrackList(listTracks)
        val track = TrackEntity(id = 0, title = "track0", artistName = "artist0", filePath = "filePath0")
        val result = dao.getTracks("0")
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(result[0].title, track.title)
    }

    @Test
    fun insertListTracksAddOneTrackWithExistedIdAndTryToGetWithNullQuery() = runTest {
        dao.insertTrackList(listTracks)
        val track = TrackEntity(id = 0, title = "track10", artistName = "artist10", filePath = "filePath10")
        dao.insertTrack(track)
        val result = dao.getTracks(null)
        assertNotNull(result)
        assertEquals(3, result.size)
        assertEquals(track, result[0])
    }

    @Test
    fun insertTrackAndTryToGetButWithWrongId() = runTest {
        val track = TrackEntity(id = 0, title = "track0", artistName = "artist0", filePath = "filePath0")
        dao.insertTrack(track)
        val result = dao.getTrack(1).first()
        assertEquals(null, result)
    }
}