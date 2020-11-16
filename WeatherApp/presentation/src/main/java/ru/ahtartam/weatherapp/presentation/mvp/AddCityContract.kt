package ru.ahtartam.weatherapp.presentation.mvp

import ru.ahtartam.weatherapp.presentation.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.presentation.mvp.base.MvpView

interface AddCityContract {
    interface View : MvpView {
    }
    interface Presenter : MvpPresenter<View> {
        fun search(text: String)
    }
}