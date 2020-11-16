package ru.ahtartam.weatherapp.presentation.mvp.base

import androidx.annotation.StringRes
import kotlinx.coroutines.CoroutineScope

interface MvpView {
    fun getScope(): CoroutineScope
    fun showMessage(@StringRes messageResId: Int)
    fun showMessage(message: String)
    fun back()
}
