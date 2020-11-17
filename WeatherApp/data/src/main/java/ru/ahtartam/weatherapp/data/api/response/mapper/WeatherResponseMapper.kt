package ru.ahtartam.weatherapp.data.api.response.mapper

import ru.ahtartam.weatherapp.data.api.response.WeatherResponse
import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO
import ru.ahtartam.weatherapp.domain.model.mapper.BaseMapper
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() : BaseMapper<WeatherResponse, CityWeatherDBO> {
    override fun map(from: WeatherResponse): CityWeatherDBO =
        CityWeatherDBO(
            cityId = from.cityId,
            cityName = from.cityName,
            lat = from.coord.lat,
            lon = from.coord.lon,
            temperature = from.main?.temperature
        )
}
