package ru.ahtartam.weatherapp.domain.repository

import ru.ahtartam.weatherapp.domain.model.CityWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchWeatherByCityName(cityName: String): CityWeather?

    suspend fun refreshWeatherList()

    fun subscribeToWeatherByCityId(cityId: Int): Flow<CityWeather>
    fun subscribeToWeatherList(): Flow<List<CityWeather>>

    suspend fun deleteByCityId(cityId: Int)
}