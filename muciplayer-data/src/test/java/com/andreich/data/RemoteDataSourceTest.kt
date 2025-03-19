package com.andreich.data

import com.andreich.data.datasource.remote.RemoteDataSource
import com.andreich.musicplayer_network.network.pojo.AlbumSearchDto
import com.andreich.musicplayer_network.network.pojo.ArtistSearchDto
import com.andreich.musicplayer_network.network.pojo.SearchResultDto
import com.andreich.musicplayer_network.network.pojo.SearchTrackDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TrackDto
import com.andreich.musicplayer_network.network.pojo.TracksDto
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test

class RemoteDataSourceTest {

    val tracks = TracksDto(arrayListOf(TrackDto(id = 0), TrackDto(id = 1)), total = 10)
    val trackDetail =
        listOf(TrackDetailDto(id = "2"), TrackDetailDto(id = "1"), TrackDetailDto(id = "0"))
    val searchResultDto = SearchResultDto(
        arrayListOf(
            SearchTrackDto(
                id = 0,
                title = "title0",
                album = AlbumSearchDto(id = 0),
                artist = ArtistSearchDto(id = 0)
            ),
            SearchTrackDto(
                id = 1,
                title = "title1",
                album = AlbumSearchDto(id = 1),
                artist = ArtistSearchDto(id = 1)
            ),
            SearchTrackDto(
                id = 2,
                title = "title2",
                album = AlbumSearchDto(id = 2),
                artist = ArtistSearchDto(id = 2)
            )
        )
    )

    private val remoteDataSource = object : RemoteDataSource {
        override suspend fun getChartTracks(): TracksDto {
            return tracks
        }

        override suspend fun getTrack(id: Long): TrackDetailDto {
            return trackDetail.find { it.id == id.toString() }
                ?: throw RuntimeException("Not found track with this id")
        }

        override suspend fun searchTrack(query: String?): SearchResultDto {
            return searchResultDto.copy(data = searchResultDto.data.filter { track ->
                query?.let {
                    track.title.contains(it) || track.album.title.contains(it)
                            || track.artist.name.contains(it)
                } == true
            } as ArrayList<SearchTrackDto>)
        }

    }

    @Test
    fun insertAndGetChartTracksTest() = runTest {
        val result = remoteDataSource.getChartTracks()
        assertEquals(tracks, result)
    }

    @Test
    fun getTrackWithExistedId() = runTest {
        val track = TrackDetailDto(id = "2")
        val result = remoteDataSource.getTrack(2)
        assertEquals(track, result)
    }

    @Test
    fun getTrackWithNonExistedId() = runTest {
        assertThrows(RuntimeException::class.java) {
            runBlocking { remoteDataSource.getTrack(100) }
        }
    }

    @Test
    fun searchWithActualQuery() = runTest {
        val track = searchResultDto.copy(data = arrayListOf(searchResultDto.data[0]))
        val result = remoteDataSource.searchTrack("0")
        assertEquals(track, result)
    }

    @Test
    fun searchWithWrongQuery() = runTest {
        val track = searchResultDto.copy(data = arrayListOf())
        val result = remoteDataSource.searchTrack("100")
        assertEquals(track, result)
    }
}