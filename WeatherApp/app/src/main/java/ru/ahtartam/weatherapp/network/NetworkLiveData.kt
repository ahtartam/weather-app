package ru.ahtartam.weatherapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import timber.log.Timber

class NetworkLiveData(
    private val context: Context
) : LiveData<Boolean>() {
    private val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Timber.d("NetworkLiveData: Network available: $network")
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Timber.d("NetworkLiveData: Network lost: $network")
            postValue(false)
        }
    }

    override fun setValue(value: Boolean) {
        if (value != this.value) {
            super.setValue(value)
        }
    }

    override fun onActive() {
        super.onActive()

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onInactive() {
        super.onInactive()

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }
}
