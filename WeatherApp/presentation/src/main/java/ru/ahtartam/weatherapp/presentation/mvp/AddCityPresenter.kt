package ru.ahtartam.weatherapp.presentation.mvp

import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import ru.ahtartam.weatherapp.presentation.R
import ru.ahtartam.weatherapp.presentation.mvp.base.PresenterBase
import timber.log.Timber
import javax.inject.Inject

class AddCityPresenter @Inject constructor(
    private val weatherRepository: WeatherRepository
) : PresenterBase<AddCityContract.View>(), AddCityContract.Presenter {

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
            val weather = weatherRepository.weatherByCityName(text)
            if (weather != null) {
                withContext(Dispatchers.Main) {
                    getView()?.back()
                }
            } else {
                getView()?.showMessage(R.string.city_not_found)
            }
        }
    }
}