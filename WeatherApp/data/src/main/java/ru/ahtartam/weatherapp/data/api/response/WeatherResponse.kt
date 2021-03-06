package ru.ahtartam.weatherapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "id")
    val cityId: Int,
    @field:Json(name = "name")
    val cityName: String,
    @field:Json(name = "coord")
    val coord: CoordGroup,
    @field:Json(name = "main")
    val main: MainGroup?
) {
    @JsonClass(generateAdapter = true)
    data class MainGroup (
        @field:Json(name = "temp")
        val temperature: Float?
    )

    @JsonClass(generateAdapter = true)
    data class CoordGroup (
        val lat: Float,
        val lon: Float
    )
}