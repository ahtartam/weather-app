package ru.ahtartam.weatherapp.mvp

import ru.ahtartam.weatherapp.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.mvp.base.MvpView

interface AddCityContract {
    interface View : MvpView {
    }
    interface Presenter : MvpPresenter<View> {
        fun search(text: String)
    }
}