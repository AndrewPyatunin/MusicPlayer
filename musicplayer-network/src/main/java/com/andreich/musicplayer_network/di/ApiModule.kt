package com.andreich.musicplayer_network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
class ApiModule {

    private val BASE_URL = "https://api.deezer.com/"

    @Provides
    fun provideApiService(context: Context): com.andreich.musicplayer_network.network.ApiService {
        val okHttpClient = OkHttpClient().newBuilder()
            .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(com.andreich.musicplayer_network.network.CacheInterceptor)
            .addInterceptor(com.andreich.musicplayer_network.network.ForceCacheInterceptor(context))
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build().create(com.andreich.musicplayer_network.network.ApiService::class.java)
    }
}