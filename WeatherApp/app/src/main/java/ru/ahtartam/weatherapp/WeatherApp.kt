package ru.ahtartam.weatherapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.ahtartam.weatherapp.di.App
import ru.ahtartam.weatherapp.di.ApplicationComponent
import ru.ahtartam.weatherapp.di.DaggerApplicationComponent
import ru.ahtartam.weatherapp.presentation.citylist.CityListViewModel
import timber.log.Timber
import javax.inject.Inject

class WeatherApp : Application(), App, Application.ActivityLifecycleCallbacks {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    @Inject
    lateinit var listViewModel: CityListViewModel

    private val appJob = SupervisorJob()
    private val appScope = CoroutineScope(appJob)

    companion object {
        var instance: App? = null
            private set
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        appComponent.inject(this)

        registerActivityLifecycleCallbacks(this)
        Timber.plant(Timber.DebugTree())
    }

    private val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Timber.d("NetworkCallback.onAvailable($network)")

            listViewModel.refresh()
        }
    }

    override fun onActivityStarted(activity: Activity) {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onActivityStopped(activity: Activity) {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
}