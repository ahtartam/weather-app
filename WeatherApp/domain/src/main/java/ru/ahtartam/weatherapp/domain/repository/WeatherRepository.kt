package ru.ahtartam.weatherapp.domain.repository

import ru.ahtartam.weatherapp.domain.model.CityWeather
import kotlinx.coroutines.flow.Flow
import ru.ahtartam.weatherapp.domain.model.City

interface WeatherRepository {
    suspend fun fetchWeatherByCityName(cityName: String): CityWeather?

    suspend fun refreshWeatherList()

    fun subscribeToWeatherByCity(city: City): Flow<CityWeather>
    fun subscribeToWeatherList(): Flow<List<CityWeather>>

    suspend fun deleteByCityId(cityId: Int)
}