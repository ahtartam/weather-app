package ru.ahtartam.weatherapp.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithDailyForecast(
    @Embedded
    val city: Weather,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val forecastList: List<DailyForecast>?
)