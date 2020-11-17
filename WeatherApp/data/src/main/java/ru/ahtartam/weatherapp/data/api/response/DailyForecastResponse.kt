package ru.ahtartam.weatherapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyForecastResponse(
    @field:Json(name = "daily")
    val daily: List<DailyGroup>
) {
    @JsonClass(generateAdapter = true)
    data class DailyGroup (
        @field:Json(name = "dt")
        val date: Long,
        @field:Json(name = "temp")
        val temperature: TempGroup
    )

    @JsonClass(generateAdapter = true)
    data class TempGroup (
        @field:Json(name = "day")
        val day: Float
    )
}