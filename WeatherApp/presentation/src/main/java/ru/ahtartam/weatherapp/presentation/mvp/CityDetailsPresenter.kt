package ru.ahtartam.weatherapp.presentation.mvp

import kotlinx.coroutines.*
import ru.ahtartam.weatherapp.data.api.WeatherApiService
import ru.ahtartam.weatherapp.data.db.Database
import ru.ahtartam.weatherapp.data.db.DatabaseProvider
import ru.ahtartam.weatherapp.presentation.mvp.base.PresenterBase
import timber.log.Timber
import javax.inject.Inject

class CityDetailsPresenter @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherApiService: WeatherApiService
) : PresenterBase<CityDetailsContract.View>(), CityDetailsContract.Presenter {
    private val db: Database = dbProvider.get()
    private var cityId: Int? = null

    override fun setCityId(cityId: Int) {
        this.cityId = cityId
    }

    override fun viewIsReady() {
        getView()?.getScope()?.launch(Dispatchers.IO) {
            cityId?.also { cityId ->
                val info = db.weatherDao().subscribeToCityWithWeatherList(cityId)
                withContext(Dispatchers.Main) {
                    getView()?.showCityDetails(info)
                }

                val forecast = db.dailyForecastDao().subscribeToCityWithDailyForecast(cityId)
                withContext(Dispatchers.Main) {
                    getView()?.showDailyForecast(forecast)
                }
            }
        }
        refresh()
    }

    override fun refresh(scope: CoroutineScope?) {
        scope?.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            getView()?.showMessage(throwable.message ?: throwable::class.java.name)
        }) {
            cityId?.also { cityId ->
                val weather = db.weatherDao().getWeatherByCityId(cityId)
                val forecast = weatherApiService.dailyForecastByCityId(weather.lat, weather.lon).mapToListDailyForecast(cityId)
                db.dailyForecastDao().deleteByCityId(cityId)
                db.dailyForecastDao().upsert(forecast)
            }
        }
    }
}