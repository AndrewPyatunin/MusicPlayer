package com.andreich.musicplayer_feature

import android.content.Context
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.DownloadManager
import java.io.File
import java.util.concurrent.Executor

@UnstableApi
internal object DownloadManagerBuilder {

    @Volatile
    private var downloadDirectory: File? = null

    private fun setDownloadDirectory(context: Context) {
        downloadDirectory = File(context.getExternalFilesDir(null).toString() + "/Music")
    }

    @Volatile
    var cacheDataSourceFactory: DataSource.Factory? = null

    @Volatile
    var databaseProvider: DatabaseProvider? = null

    @Volatile
    var downloadManager: DownloadManager? = null

    @Volatile
    var downloadCache: SimpleCache? = null

    // Create a factory for reading the data from the network.
    val dataSourceFactory = DefaultHttpDataSource.Factory()

    val downloadExecutor = Executor(Runnable::run)

    @Synchronized
    fun getDatabaseProvider(context: Context): DatabaseProvider {
        return if (databaseProvider == null) {
            StandaloneDatabaseProvider(context)
        } else databaseProvider!!
    }

    @Synchronized
    fun getDownloadManager(context: Context): DownloadManager {
        if (downloadManager == null) {
            downloadManager = DownloadManager(
                context,
                getDatabaseProvider(context),
                getDownloadCache(context),
                getCacheFactory(context),
                downloadExecutor
            )
        }
        return downloadManager!!
    }

    @Synchronized
    fun getDownloadCache(context: Context): SimpleCache {
        val cacheSize: Long = 1024 * 1024 * 100
        if (downloadDirectory == null) {
            setDownloadDirectory(context)
        }
        Log.d("MY_MUSIC_DIRECTORY", downloadDirectory.toString())
        if (!downloadDirectory!!.exists()) {
            downloadDirectory!!.mkdirs()
        }
        if (downloadCache == null) {
            downloadCache = SimpleCache(
                downloadDirectory!!,
                LeastRecentlyUsedCacheEvictor(cacheSize),
                getDatabaseProvider(context)
            )
        }
        return downloadCache!!
    }

    @Synchronized
    fun getCacheFactory(context: Context): DataSource.Factory {
        if (cacheDataSourceFactory == null) {
            cacheDataSourceFactory = CacheDataSource.Factory().setCache(getDownloadCache(context))
                .setUpstreamDataSourceFactory(dataSourceFactory)
                .setCacheWriteDataSinkFactory(CacheDataSink.Factory().setCache(getDownloadCache(context)))
        }
        return cacheDataSourceFactory!!
    }

}

