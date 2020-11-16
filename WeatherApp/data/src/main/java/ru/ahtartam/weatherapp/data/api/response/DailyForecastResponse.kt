package ru.ahtartam.weatherapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.ahtartam.weatherapp.data.db.model.CityForecastDBO
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

    fun mapToListDailyForecast(cityId: Int): List<CityForecastDBO> {
        return daily.map {
            CityForecastDBO(
                cityId = cityId,
                date = Date(it.date * 1000),
                temperature = it.temperature.day
            )
        }
    }
}