package ru.ahtartam.weatherapp.domain.repository

import ru.ahtartam.weatherapp.domain.model.CityWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchWeatherByCityName(cityName: String): CityWeather?
    suspend fun deleteByCityId(cityId: Int)
    suspend fun refreshWeatherList()
    fun getWeatherList(): Flow<List<CityWeather>>
}