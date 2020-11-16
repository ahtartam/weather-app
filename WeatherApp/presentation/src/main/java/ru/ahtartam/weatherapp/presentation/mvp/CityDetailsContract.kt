package ru.ahtartam.weatherapp.presentation.mvp

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.data.db.model.CityWithDailyForecast
import ru.ahtartam.weatherapp.data.db.model.Weather
import ru.ahtartam.weatherapp.presentation.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.presentation.mvp.base.MvpView

interface CityDetailsContract {
    interface View : MvpView {
        fun showCityDetails(info: LiveData<Weather>)
        fun showDailyForecast(forecast: LiveData<CityWithDailyForecast>)
    }
    interface Presenter : MvpPresenter<View> {
        fun setCityId(cityId: Int)
        fun refresh(scope: CoroutineScope? = getView()?.getScope())
    }
}