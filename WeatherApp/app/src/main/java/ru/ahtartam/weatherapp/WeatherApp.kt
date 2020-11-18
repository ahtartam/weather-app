package ru.ahtartam.weatherapp

import android.app.Application
import ru.ahtartam.weatherapp.di.App
import ru.ahtartam.weatherapp.di.ApplicationComponent
import ru.ahtartam.weatherapp.di.DaggerApplicationComponent
import timber.log.Timber

class WeatherApp : Application(), App {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    companion object {
        var instance: App? = null
            private set
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        appComponent.inject(this)

        Timber.plant(Timber.DebugTree())
    }
}