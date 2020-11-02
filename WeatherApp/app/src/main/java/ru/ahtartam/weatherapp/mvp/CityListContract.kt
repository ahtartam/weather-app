package ru.ahtartam.weatherapp.mvp

import androidx.lifecycle.LiveData
import ru.ahtartam.weatherapp.model.CityWithWeather
import ru.ahtartam.weatherapp.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.mvp.base.MvpView

interface CityListContract {
    interface View : MvpView {
        fun showCityList(list: LiveData<List<CityWithWeather>>)
        fun showCityDetails(cityId: Int)
    }
    interface Presenter : MvpPresenter<View> {
        fun onCityClicked(cityId: Int)
    }
}