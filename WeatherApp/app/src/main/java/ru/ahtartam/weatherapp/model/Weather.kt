package ru.ahtartam.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val cityId: Int,
    val temperature: Float?
)