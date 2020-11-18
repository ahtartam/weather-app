package ru.ahtartam.weatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityDailyForecast

interface ForecastRepository {
    suspend fun fetchDailyForecastByCity(city: City)
    fun subscribeToDailyForecastByCity(city: City): Flow<CityDailyForecast>
}