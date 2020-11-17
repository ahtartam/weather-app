package ru.ahtartam.weatherapp.presentation.addcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import timber.log.Timber
import javax.inject.Inject

class AddCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    sealed class SearchState {
        object Processing : SearchState()
        object Success : SearchState()
        object FailedNotFound : SearchState()
        data class Failed(val message: String) : SearchState()
    }

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> get() = _searchState

    fun search(text: String) {
        if (text.isEmpty()) return

        _searchState.postValue(SearchState.Processing)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            if (throwable is HttpException && throwable.code() == 404) {
                _searchState.postValue(SearchState.FailedNotFound)
            } else {
                _searchState.postValue(SearchState.Failed(throwable.message ?: throwable::class.java.name))
            }
        }) {
            val weather = weatherRepository.fetchWeatherByCityName(text)
            if (weather != null) {
                withContext(Dispatchers.Main) {
                    _searchState.postValue(SearchState.Success)
                }
            } else {
                _searchState.postValue(SearchState.FailedNotFound)
            }
        }
    }
}