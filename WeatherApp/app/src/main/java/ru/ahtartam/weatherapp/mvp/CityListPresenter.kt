package ru.ahtartam.weatherapp.mvp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ahtartam.weatherapp.api.WeatherAdiService
import ru.ahtartam.weatherapp.db.Database
import ru.ahtartam.weatherapp.db.DatabaseProvider
import ru.ahtartam.weatherapp.mvp.base.PresenterBase
import javax.inject.Inject

class CityListPresenter @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherAdiService: WeatherAdiService
) : PresenterBase<CityListContract.View>(), CityListContract.Presenter {
    private val db: Database = dbProvider.get()

    override fun viewIsReady() {
        getView()?.getScope()?.launch(Dispatchers.IO) {
            val list = db.weatherDao().subscribeToCityWithWeatherList()
            withContext(Dispatchers.Main) {
                getView()?.showCityList(list)
            }
        }
    }

    override fun onCityClicked(cityId: Int) {
        getView()?.showCityDetails(cityId)
    }

    override fun refresh(scope: CoroutineScope?) {
        scope?.launch(Dispatchers.IO) {
            val weather = db.weatherDao().getCityWithWeatherList().map {
                weatherAdiService.weatherByCityId(it.city.id).mapToWeather()
            }
            db.weatherDao().upsert(weather)
        }
    }
}