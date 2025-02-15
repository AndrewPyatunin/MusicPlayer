package com.andreich.musicplayer.di

import android.app.Application
import android.content.Context
import com.andreich.data.di.ApiModule
import com.andreich.data.di.DataSourceModule
import com.andreich.data.di.DatabaseModule
import com.andreich.data.di.DtoMapperModule
import com.andreich.data.di.EntityMapperModule
import com.andreich.data.di.RepositoryModule
import com.andreich.musicplayer.ui.MainActivity
import com.andreich.musicplayer.ui.TabsFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [DatabaseModule::class, ApiModule::class,
        DataSourceModule::class, DtoMapperModule::class,
        EntityMapperModule::class, RepositoryModule::class]
)
interface AppComponent {

    @Component.Factory
    interface ComponentFactory {

        fun create(@BindsInstance context: Application): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: TabsFragment)
}