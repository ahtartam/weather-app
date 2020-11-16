package ru.ahtartam.weatherapp.domain.model

data class CityDailyForecast(
    val city: City,
    val forecastList: List<Forecast>
)