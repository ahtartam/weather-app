package ru.ahtartam.weatherapp.domain.usecases

import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class AddCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun searchByCityName(cityName: String): CityWeather? {
        return weatherRepository.fetchWeatherByCityName(cityName)
    }
}
