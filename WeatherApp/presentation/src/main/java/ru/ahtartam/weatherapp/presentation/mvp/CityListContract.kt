package ru.ahtartam.weatherapp.presentation.mvp

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO
import ru.ahtartam.weatherapp.presentation.mvp.base.MvpPresenter
import ru.ahtartam.weatherapp.presentation.mvp.base.MvpView

interface CityListContract {
    interface View : MvpView {
        fun showCityList(list: LiveData<List<CityWeatherDBO>>)
        fun onEmptyResult()
        fun showCityDetails(cityId: Int)
    }
    interface Presenter : MvpPresenter<View> {
        fun onCityClicked(cityId: Int)
        fun onCityDelete(cityId: Int)
        fun refresh(scope: CoroutineScope? = getView()?.getScope())
    }
}