package ru.ahtartam.weatherapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "base")
    val base: String?,
    @field:Json(name = "main")
    val main: MainGroup?
) {
    data class MainGroup (
        @field:Json(name = "temp")
        val temperature: String?
    )
}