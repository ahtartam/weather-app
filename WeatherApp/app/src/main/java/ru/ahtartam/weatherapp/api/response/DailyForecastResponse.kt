package ru.ahtartam.weatherapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.ahtartam.weatherapp.model.DailyForecast
import java.util.*

@JsonClass(generateAdapter = true)
data class DailyForecastResponse(
    @field:Json(name = "current")
    val current: CurrentGroup,
    @field:Json(name = "daily")
    val daily: List<DailyGroup>
) {
    data class CurrentGroup (
        @field:Json(name = "dt")
        val date: Long,
        @field:Json(name = "temp")
        val temperature: Float
    )
    data class DailyGroup (
        @field:Json(name = "dt")
        val date: Long,
        @field:Json(name = "temp")
        val temperature: TempGroup
    )
    data class TempGroup (
        @field:Json(name = "day")
        val day: Float
    )

    fun mapToListDailyForecast(cityId: Int): List<DailyForecast> {
        return daily
            .map {
                it.date to it.temperature.day
            }
            .map {
                DailyForecast(
                    cityId = cityId,
                    date = Date(it.first * 1000),
                    temperature = it.second
                )
            }
    }
}