package com.andreich.musicplayer.di

import android.app.Application
import android.content.Context
import com.andreich.domain.repo.MusicRepository
import com.andreich.musicplayer.ui.MainActivity
import com.andreich.musicplayer.ui.TabsFragment
import com.andreich.musicplayer_database.di.DatabaseModule
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependencies
import com.andreich.musicplayer_network.di.ApiModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DatabaseModule::class, ApiModule::class,
        MapperImplModule::class, RepositoryModule::class,
        RepositoryImplModule::class,
        DataSourceImplModule::class],
)
interface AppComponent : MusicPlayerFeatureDependencies {

    override fun getRepository(): MusicRepository

    @Component.Factory
    interface ComponentFactory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: TabsFragment)
}