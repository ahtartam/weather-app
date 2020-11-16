package ru.ahtartam.weatherapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.ahtartam.weatherapp.model.DailyForecast
import java.util.*

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

    fun mapToListDailyForecast(cityId: Int): List<DailyForecast> {
        return daily.map {
            DailyForecast(
                cityId = cityId,
                date = Date(it.date * 1000),
                temperature = it.temperature.day
            )
        }
    }
}