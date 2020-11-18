package ru.ahtartam.weatherapp.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class CityWeatherWithForecastDBO(
    @Embedded
    val city: CityWeatherDBO,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val forecastList: List<CityForecastDBO>?
)