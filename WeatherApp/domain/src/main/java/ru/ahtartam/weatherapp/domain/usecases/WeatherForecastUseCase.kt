package ru.ahtartam.weatherapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityDailyForecast
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.repository.ForecastRepository
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository
) {
    fun subscribeToWeatherByCity(city: City): Flow<CityWeather> {
        return weatherRepository.subscribeToWeatherByCityId(city.cityId)
    }

    suspend fun fetchDailyForecastByCity(city: City) {
        return forecastRepository.fetchDailyForecastByCity(city)
    }

    fun subscribeToDailyForecastByCity(city: City): Flow<CityDailyForecast> {
        return forecastRepository.subscribeToDailyForecastByCity(city)
    }
}
