package ru.ahtartam.weatherapp.mvp

import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.api.WeatherAdiService
import ru.ahtartam.weatherapp.db.Database
import ru.ahtartam.weatherapp.db.DatabaseProvider
import ru.ahtartam.weatherapp.model.Weather
import ru.ahtartam.weatherapp.mvp.base.PresenterBase
import timber.log.Timber
import javax.inject.Inject

class AddCityPresenter @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherAdiService: WeatherAdiService
) : PresenterBase<AddCityContract.View>(), AddCityContract.Presenter {
    private val db: Database = dbProvider.get()

    override fun viewIsReady() {
        search("", false)
    }

    override fun onCityClicked(cityId: Int) {
        getView()?.getScope()?.launch(Dispatchers.IO) {
            db.weatherDao().upsert(listOf(Weather(cityId)))
            withContext(Dispatchers.Main) {
                getView()?.back()
            }
        }
    }

    override fun search(text: String, isSelected: Boolean) {
        getView()?.getScope()?.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            if (throwable is HttpException && throwable.code() == 404) {
                getView()?.showMessage(R.string.city_not_found)
            } else {
                getView()?.showMessage(throwable.message ?: throwable::class.java.name)
            }
        }) {
            val list = db.cityDao().searchCityList("%$text%")
            withContext(Dispatchers.Main) {
                getView()?.showCityList(list)
            }

            if (isSelected) {
                val response = weatherAdiService.weatherByCityName(text)
                if (response.cityName == text) {
                    db.weatherDao().upsert(listOf(response.mapToWeather()))
                    withContext(Dispatchers.Main) {
                        getView()?.back()
                    }
                } else {
                    getView()?.showMessage(R.string.city_not_found)
                }
            }
        }
    }
}