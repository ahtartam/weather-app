package ru.ahtartam.weatherapp.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.ahtartam.weatherapp.model.DailyForecast
import java.util.*

@JsonClass(generateAdapter = true)
data class DailyForecastResponse(
    @field:Json(name = "city")
    val city: CityGroup,
    @field:Json(name = "list")
    val list: List<ForecastGroup>
) {
    data class CityGroup(
        val id: Int,
        val name: String
    )
    data class ForecastGroup (
        @field:Json(name = "dt")
        val date: Long,
        @field:Json(name = "temp")
        val temperature: TempGroup
    )
    data class TempGroup (
        @field:Json(name = "day")
        val day: Float
    )

    fun mapToListDailyForecast(): List<DailyForecast> {
        return list
            .map {
                it.date to it.temperature.day
            }
            .map {
                DailyForecast(
                    cityId = city.id,
                    date = Date(it.first),
                    temperature = it.second
                )
            }
    }
}