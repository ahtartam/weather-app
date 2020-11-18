package ru.ahtartam.weatherapp.domain.model

import java.util.*

data class Forecast(
    val date: Date,
    val weather: Weather
)