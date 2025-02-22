package com.andreich.musicplayer.di

import android.content.Context
import com.andreich.domain.repo.LocalRepository
import com.andreich.domain.repo.MusicRepository
import com.andreich.musicplayer.ui.MainActivity
import com.andreich.musicplayer.ui.MainViewModel
import com.andreich.musicplayer.ui.TabsFragment
import com.andreich.musicplayer_database.di.DatabaseModule
import com.andreich.musicplayer_feature.MusicPlayerFeatureDependencies
import com.andreich.musicplayer_network.di.ApiModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DatabaseModule::class, ApiModule::class,
        MapperImplModule::class, UseCaseModule::class,
        RepositoryImplModule::class, ViewModelModule::class,
        DataSourceImplModule::class],
)
interface AppComponent : MusicPlayerFeatureDependencies {

    override fun getRepository(): MusicRepository

    override fun getLocalRepository(): LocalRepository

    @Component.Factory
    interface ComponentFactory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: TabsFragment)
}