package ru.ahtartam.weatherapp.mvp

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.model.CityWithDailyForecast
import ru.ahtartam.weatherapp.model.CityWithWeather
import ru.ahtartam.weatherapp.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.mvp.base.MvpView

interface CityDetailsContract {
    interface View : MvpView {
        fun showCityDetails(info: LiveData<CityWithWeather>)
        fun showDailyForecast(forecast: LiveData<CityWithDailyForecast>)
    }
    interface Presenter : MvpPresenter<View> {
        fun setCityId(cityId: Int)
        fun refresh(scope: CoroutineScope? = getView()?.getScope())
    }
}