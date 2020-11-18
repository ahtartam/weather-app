package ru.ahtartam.weatherapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun refreshWeatherList() {
        weatherRepository.refreshWeatherList()
    }

    fun subscribeToWeatherList(): Flow<List<CityWeather>> {
        return weatherRepository.subscribeToWeatherList()
    }

    suspend fun deleteCity(city: City) {
        weatherRepository.deleteByCityId(city.cityId)
    }
}
