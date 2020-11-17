package ru.ahtartam.weatherapp.data.repository

import ru.ahtartam.weatherapp.data.api.WeatherApiService
import ru.ahtartam.weatherapp.data.api.response.mapper.DailyForecastResponseMapper
import ru.ahtartam.weatherapp.data.db.Database
import ru.ahtartam.weatherapp.data.db.DatabaseProvider
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherApiService: WeatherApiService,
    private val responseMapper: DailyForecastResponseMapper
) : ForecastRepository {
    private val db: Database = dbProvider.get()

    override suspend fun fetchDailyForecastByCity(city: City) {
        val response = weatherApiService.dailyForecastByCityId(
            lat = city.location.lat,
            lon = city.location.lon
        )
        val dbo = responseMapper.map(city.cityId, response)
        db.dailyForecastDao().deleteByCityId(dbo.groupBy { it.cityId }.keys)
        db.dailyForecastDao().upsert(dbo)
    }
}