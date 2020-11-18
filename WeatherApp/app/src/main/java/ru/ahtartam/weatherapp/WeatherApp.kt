package ru.ahtartam.weatherapp

import android.app.Application
import ru.ahtartam.weatherapp.di.App
import ru.ahtartam.weatherapp.di.ApplicationComponent
import ru.ahtartam.weatherapp.di.ContextModule
import ru.ahtartam.weatherapp.di.DaggerApplicationComponent
import timber.log.Timber

class WeatherApp : Application(), App {
    val appComponent: ApplicationComponent = DaggerApplicationComponent
        .builder()
        .contextModule(ContextModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}