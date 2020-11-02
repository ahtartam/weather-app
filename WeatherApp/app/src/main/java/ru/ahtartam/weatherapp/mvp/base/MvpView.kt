package ru.ahtartam.weatherapp.mvp.base

import kotlinx.coroutines.CoroutineScope

interface MvpView {
    fun getScope(): CoroutineScope
    fun showMessage(message: String)
    fun back()
}
