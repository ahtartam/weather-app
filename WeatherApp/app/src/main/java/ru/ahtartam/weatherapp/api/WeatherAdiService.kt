package ru.ahtartam.weatherapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ahtartam.weatherapp.api.response.DailyForecastResponse
import ru.ahtartam.weatherapp.api.response.WeatherResponse

private val weatherApiKey = "a913876322cbac7165b3285a9b1d09e7"
private val dailyForecastApiKey = weatherApiKey

interface WeatherAdiService {
    @GET("weather")
    suspend fun weatherByCityId(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String = weatherApiKey,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): WeatherResponse

    @GET("forecast/daily")
    suspend fun dailyForecastByCityId(
        @Query("id") cityId: Int,
        @Query("cnt") dayCount: Int = 7,
        @Query("appid") apiKey: String = dailyForecastApiKey,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): DailyForecastResponse
}