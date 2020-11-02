package ru.ahtartam.weatherapp.mvp

import kotlinx.coroutines.*
import ru.ahtartam.weatherapp.api.WeatherAdiService
import ru.ahtartam.weatherapp.db.Database
import ru.ahtartam.weatherapp.db.DatabaseProvider
import ru.ahtartam.weatherapp.mvp.base.PresenterBase
import timber.log.Timber
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
        scope?.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            getView()?.showMessage(throwable.message ?: throwable::class.java.name)
        }) {
            val weather = db.weatherDao().getCityWithWeatherList().map {
                weatherAdiService.weatherByCityId(it.city.id).mapToWeather()
            }
            db.weatherDao().upsert(weather)
        }
    }
}