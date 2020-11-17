package ru.ahtartam.weatherapp.data.api.response.mapper

import ru.ahtartam.weatherapp.data.api.response.DailyForecastResponse
import ru.ahtartam.weatherapp.data.db.model.CityForecastDBO
import ru.ahtartam.weatherapp.domain.model.mapper.BaseMapper
import java.util.*
import javax.inject.Inject

class DailyForecastResponseMapper @Inject constructor()
    : BaseMapper<DailyForecastResponse, List<CityForecastDBO>>
{
    fun map(cityId: Int, from: DailyForecastResponse): List<CityForecastDBO> =
        from.daily.map { d ->
            CityForecastDBO(
                cityId = cityId,
                date = Date(d.date),
                temperature = d.temperature.day
            )
        }

    override fun map(from: DailyForecastResponse): List<CityForecastDBO> {
        throw NotImplementedError("Use map(cityId: Int, from: DailyForecastResponse) instead.")
    }

    fun invoke(cityId: Int, from: DailyForecastResponse): List<CityForecastDBO> = map(cityId, from)
}