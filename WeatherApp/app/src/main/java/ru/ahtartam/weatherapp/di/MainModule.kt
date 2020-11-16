package ru.ahtartam.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ahtartam.weatherapp.WeatherApp

@Module(includes = [MainModule.ProvidesModule::class])
interface MainModule {
    @Module
    class ProvidesModule {
        @Provides
        fun provideAppContext(): Context =
            WeatherApp.instance!!.getApplicationContext()
    }
}