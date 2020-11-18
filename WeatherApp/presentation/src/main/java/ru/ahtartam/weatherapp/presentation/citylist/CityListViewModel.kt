package ru.ahtartam.weatherapp.presentation.citylist

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.usecases.CurrentWeatherUseCase
import timber.log.Timber
import javax.inject.Inject

class CityListViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : ViewModel() {

    sealed class NetworkState {
        object Processing : NetworkState()
        object Success : NetworkState()
        data class Failed(val message: String) : NetworkState()
    }

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    val weatherList: LiveData<List<CityWeather>> =
        currentWeatherUseCase.subscribeToWeatherList().asLiveData()

    fun onCityDelete(city: City) {
        viewModelScope.launch {
            currentWeatherUseCase.deleteCity(city)
        }
    }

    fun refresh() {
        _networkState.postValue(NetworkState.Processing)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            _networkState.postValue(NetworkState.Failed(throwable.message ?: throwable::class.java.name))
        }) {
            currentWeatherUseCase.refreshWeatherList()
            _networkState.postValue(NetworkState.Success)
        }
    }
}