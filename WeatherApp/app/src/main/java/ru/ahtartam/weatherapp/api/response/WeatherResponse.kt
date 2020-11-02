package ru.ahtartam.weatherapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.ahtartam.weatherapp.model.Weather

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "id")
    val cityId: Int,
    @field:Json(name = "main")
    val main: MainGroup?
) {
    data class MainGroup (
        @field:Json(name = "temp")
        val temperature: Float?
    )

    fun mapToWeather(): Weather {
        return Weather(
            cityId = cityId,
            temperature = main?.temperature
        )
    }
}