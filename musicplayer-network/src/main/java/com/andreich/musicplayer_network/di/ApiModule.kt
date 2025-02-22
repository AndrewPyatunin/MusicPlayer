package com.andreich.musicplayer_network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://api.deezer.com/"

    @Provides
    fun provideApiService(): com.andreich.musicplayer_network.network.ApiService {
        val okHttpClient = OkHttpClient().newBuilder()
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build().create(com.andreich.musicplayer_network.network.ApiService::class.java)
    }
}