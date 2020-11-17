package ru.ahtartam.weatherapp.domain.repository

import ru.ahtartam.weatherapp.domain.model.CityWeather

interface WeatherRepository {
    suspend fun weatherByCityName(cityName: String): CityWeather?
}