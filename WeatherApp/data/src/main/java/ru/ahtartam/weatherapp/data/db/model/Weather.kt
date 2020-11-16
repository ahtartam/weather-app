package ru.ahtartam.weatherapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val cityId: Int,
    val cityName: String,
    val lat: Float,
    val lon: Float,
    val temperature: Float? = null
)