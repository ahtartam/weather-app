package ru.ahtartam.weatherapp.mvp

import ru.ahtartam.weatherapp.model.City
import ru.ahtartam.weatherapp.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.mvp.base.MvpView

interface AddCityContract {
    interface View : MvpView {
        fun showCityList(list: List<City>)
    }
    interface Presenter : MvpPresenter<View> {
        fun onCityClicked(cityId: Int)
        fun search(text: String, isSelected: Boolean)
    }
}