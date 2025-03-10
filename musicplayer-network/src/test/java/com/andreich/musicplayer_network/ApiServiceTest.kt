package com.andreich.musicplayer_network

import com.andreich.musicplayer_network.network.ApiService
import com.andreich.musicplayer_network.network.pojo.SearchResultDto
import com.andreich.musicplayer_network.network.pojo.TrackDetailDto
import com.andreich.musicplayer_network.network.pojo.TracksDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiServiceTest {

    private val BASE_URL = "/"

    private val server = MockWebServer()
    private val apiService = Retrofit.Builder()
        .baseUrl(server.url(BASE_URL))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Test
    fun getChartTracksTestWithSuccess() = runTest {
        val dto = TracksDto()
        val gson = GsonBuilder().create()
        val json = gson.toJson(dto)
        val response = MockResponse()
        response.setBody(json)
        server.enqueue(response)
        val data = apiService.getChartTracks()
        server.takeRequest()
        assertEquals(dto, data)
    }

    @Test
    fun getChartTracksWithFailureTest() = runTest {
        val mockResponse = MockResponse().setResponseCode(500).setBody("{}")
        server.enqueue(mockResponse)
        assertThrows(HttpException::class.java) { runBlocking { apiService.getChartTracks() } }
    }

    @Test
    fun searchTrackWithFailureTest() = runTest {
        val mockResponse = MockResponse().setResponseCode(500).setBody("{}")
        server.enqueue(mockResponse)
        assertThrows(HttpException::class.java) { runBlocking { apiService.searchTrack(null) } }
    }

    @Test
    fun getTrackByIdWithFailureTest() = runTest {
        val mockResponse = MockResponse().setResponseCode(500).setBody("{}")
        server.enqueue(mockResponse)
        assertThrows(HttpException::class.java) { runBlocking { apiService.getTrack(0) } }
    }

    @Test
    fun searchTrackWithNullQuery() = runTest {
        val dto = SearchResultDto()
        val json = Gson().toJson(dto)
        val response = MockResponse()
        response.setBody(json)
        server.enqueue(response)
        val data = apiService.searchTrack(null)
        server.takeRequest()
        assertEquals(dto, data)
    }

    @Test
    fun getTrackWithSomeId() = runTest {
        val id = 100L
        val dto = TrackDetailDto(id.toString())
        val json = Gson().toJson(dto)
        val response = MockResponse()
        response.setBody(json)
        server.enqueue(response)
        val data = apiService.getTrack(id)
        server.takeRequest()
        assertEquals(dto, data)
    }

    @After
    fun after() {
        server.shutdown()
    }
}