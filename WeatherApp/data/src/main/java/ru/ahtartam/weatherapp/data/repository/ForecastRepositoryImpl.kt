package ru.ahtartam.weatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ahtartam.weatherapp.data.api.WeatherApiService
import ru.ahtartam.weatherapp.data.api.response.mapper.DailyForecastResponseMapper
import ru.ahtartam.weatherapp.data.db.Database
import ru.ahtartam.weatherapp.data.db.DatabaseProvider
import ru.ahtartam.weatherapp.data.db.model.mapper.CityWeatherWithForecastDBOMapper
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityDailyForecast
import ru.ahtartam.weatherapp.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherApiService: WeatherApiService,
    private val responseMapper: DailyForecastResponseMapper,
    private val dboMapper: CityWeatherWithForecastDBOMapper
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

    override fun subscribeToDailyForecastByCity(city: City): Flow<CityDailyForecast> =
        db.dailyForecastDao().subscribeToCityWithDailyForecast(city.cityId)
            .map { dboList ->
                dboMapper(dboList)
            }
}