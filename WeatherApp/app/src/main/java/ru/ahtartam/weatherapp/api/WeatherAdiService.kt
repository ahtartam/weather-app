package ru.ahtartam.weatherapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ahtartam.weatherapp.api.response.WeatherResponse

private val weatherApiKey = "a913876322cbac7165b3285a9b1d09e7"

interface WeatherAdiService {
    @GET("weather")
    suspend fun weatherByCityId(
        @Query("id") cityId: String,
        @Query("appid") apiKey: String = weatherApiKey,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}