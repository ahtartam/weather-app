package ru.ahtartam.weatherapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithWeather(
    @Embedded
    val weather: Weather?,
    @Relation(parentColumn = "cityId", entityColumn = "id")
    val city: City
)