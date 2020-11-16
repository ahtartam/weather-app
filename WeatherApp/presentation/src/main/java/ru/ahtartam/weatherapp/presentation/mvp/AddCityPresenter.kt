package ru.ahtartam.weatherapp.presentation.mvp

import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.ahtartam.weatherapp.data.api.WeatherApiService
import ru.ahtartam.weatherapp.data.db.Database
import ru.ahtartam.weatherapp.data.db.DatabaseProvider
import ru.ahtartam.weatherapp.presentation.R
import ru.ahtartam.weatherapp.presentation.mvp.base.PresenterBase
import timber.log.Timber
import javax.inject.Inject

class AddCityPresenter @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherApiService: WeatherApiService
) : PresenterBase<AddCityContract.View>(), AddCityContract.Presenter {
    private val db: Database = dbProvider.get()

    override fun viewIsReady() {
    }

    override fun search(text: String) {
        if (text.isEmpty()) return

        getView()?.getScope()?.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            if (throwable is HttpException && throwable.code() == 404) {
                getView()?.showMessage(R.string.city_not_found)
            } else {
                getView()?.showMessage(throwable.message ?: throwable::class.java.name)
            }
        }) {
            val response = weatherApiService.weatherByCityName(text)
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