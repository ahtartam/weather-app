package ru.ahtartam.weatherapp.data.db.model.mapper

import ru.ahtartam.weatherapp.data.db.model.CityWeatherWithForecastDBO
import ru.ahtartam.weatherapp.domain.model.*
import ru.ahtartam.weatherapp.domain.model.mapper.BaseMapper
import javax.inject.Inject

class CityWeatherWithForecastDBOMapper @Inject constructor() : BaseMapper<CityWeatherWithForecastDBO, CityDailyForecast> {
    override fun map(from: CityWeatherWithForecastDBO): CityDailyForecast =
        CityDailyForecast(
            city = City(
                cityId = from.city.cityId,
                cityName = from.city.cityName,
                location = GeoLocation(
                    lat = from.city.lat,
                    lon = from.city.lon
                )
            ),
            forecastList = from.forecastList?.map {
                Forecast(
                    date = it.date,
                    weather = Weather(
                        temperature = it.temperature
                    )
                )
            } ?: listOf()
        )
}