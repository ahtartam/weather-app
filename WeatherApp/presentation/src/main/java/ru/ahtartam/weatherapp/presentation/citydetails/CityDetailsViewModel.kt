package ru.ahtartam.weatherapp.presentation.citydetails

import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityDailyForecast
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.repository.ForecastRepository
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import timber.log.Timber
import javax.inject.Inject

class CityDetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    sealed class NetworkState {
        object Processing : NetworkState()
        object Success : NetworkState()
        data class Failed(val message: String) : NetworkState()
    }

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    fun getWeather(city: City): LiveData<CityWeather> =
        weatherRepository.subscribeToWeatherByCity(city).asLiveData()

    fun getForecast(city: City): LiveData<CityDailyForecast> =
        forecastRepository.subscribeToDailyForecastByCity(city).asLiveData()

    fun refresh(city: City) {
        _networkState.postValue(NetworkState.Processing)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            _networkState.postValue(NetworkState.Failed(throwable.message ?: throwable::class.java.name))
        }) {
            forecastRepository.fetchDailyForecastByCity(city)
            _networkState.postValue(NetworkState.Success)
        }
    }
}