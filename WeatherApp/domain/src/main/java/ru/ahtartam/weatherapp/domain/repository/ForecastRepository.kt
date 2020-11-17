package ru.ahtartam.weatherapp.domain.repository

import ru.ahtartam.weatherapp.domain.model.City

interface ForecastRepository {
    suspend fun fetchDailyForecastByCity(city: City)
}