package ru.ahtartam.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citylist")
data class City(
    @PrimaryKey val id: Int,
    val name: String,
    val lat: Float,
    val lon: Float
)