package ru.ahtartam.weatherapp.domain.model

data class City(
    val cityId: Int,
    val cityName: String,
    val lat: Float,
    val lon: Float
)