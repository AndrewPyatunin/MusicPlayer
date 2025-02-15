package com.andreich.data.di

import android.content.Context
import com.andreich.data.network.ApiService
import com.andreich.data.network.CacheInterceptor
import com.andreich.data.network.ForceCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val BASE_URL = "https://api.deezer.com/"

@Module
class ApiModule {

    @Provides
    fun provideApiService(context: Context): ApiService {
        val okHttpClient = OkHttpClient().newBuilder()
            .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(CacheInterceptor)
            .addInterceptor(ForceCacheInterceptor(context))
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build().create(ApiService::class.java)
    }
}