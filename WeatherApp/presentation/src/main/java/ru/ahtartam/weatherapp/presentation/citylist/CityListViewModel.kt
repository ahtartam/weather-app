package ru.ahtartam.weatherapp.presentation.citylist

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import timber.log.Timber
import javax.inject.Inject

class CityListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    sealed class NetworkState {
        object Processing : NetworkState()
        object Success : NetworkState()
        data class Failed(val message: String) : NetworkState()
    }

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    val weatherList: LiveData<List<CityWeather>> =
        weatherRepository.getWeatherList().asLiveData()

    fun onCityDelete(cityId: Int) {
        viewModelScope.launch {
            weatherRepository.deleteByCityId(cityId)
        }
    }

    fun refresh() {
        _networkState.postValue(NetworkState.Processing)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            _networkState.postValue(NetworkState.Failed(throwable.message ?: throwable::class.java.name))
        }) {
            weatherRepository.refreshWeatherList()
            _networkState.postValue(NetworkState.Success)
        }
    }
}