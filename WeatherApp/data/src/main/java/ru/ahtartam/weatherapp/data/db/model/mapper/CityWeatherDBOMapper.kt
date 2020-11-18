package ru.ahtartam.weatherapp.data.db.model.mapper

import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.model.GeoLocation
import ru.ahtartam.weatherapp.domain.model.Weather
import ru.ahtartam.weatherapp.domain.model.mapper.BaseMapper
import javax.inject.Inject

class CityWeatherDBOMapper @Inject constructor() : BaseMapper<CityWeatherDBO, CityWeather> {
    override fun map(from: CityWeatherDBO): CityWeather =
        CityWeather(
            city = City(
                cityId = from.cityId,
                cityName = from.cityName,
                location = GeoLocation(
                    lat = from.lat,
                    lon = from.lon
                )
            ),
            weather = Weather(
                temperature = from.temperature
            )
        )
}