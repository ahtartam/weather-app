package ru.ahtartam.weatherapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithDailyForecast(
    @Embedded
    val city: City,
    @Relation(parentColumn = "id", entityColumn = "cityId")
    val forecastList: List<DailyForecast>?
)