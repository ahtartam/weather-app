package ru.ahtartam.weatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ahtartam.weatherapp.data.api.WeatherApiService
import ru.ahtartam.weatherapp.data.api.response.mapper.WeatherResponseMapper
import ru.ahtartam.weatherapp.data.db.Database
import ru.ahtartam.weatherapp.data.db.DatabaseProvider
import ru.ahtartam.weatherapp.data.db.model.mapper.CityWeatherDBOMapper
import ru.ahtartam.weatherapp.domain.model.CityWeather
import ru.ahtartam.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    dbProvider: DatabaseProvider,
    private val weatherApiService: WeatherApiService,
    private val responseMapper: WeatherResponseMapper,
    private val dboMapper: CityWeatherDBOMapper
) : WeatherRepository {
    private val db: Database = dbProvider.get()

    override suspend fun fetchWeatherByCityName(cityName: String): CityWeather? {
        val response = weatherApiService.weatherByCityName(cityName)
        return if (response.cityName == cityName) {
            val dbo = responseMapper(response)
            db.weatherDao().upsert(listOf(dbo))
            dboMapper(dbo)
        } else {
            null
        }
    }

    override suspend fun deleteByCityId(cityId: Int) {
        db.weatherDao().deleteByCityId(cityId)
        db.dailyForecastDao().deleteByCityId(cityId)
    }

    override suspend fun refreshWeatherList() {
        db.weatherDao().getCityWithWeatherList().map {
            fetchWeatherByCityName(it.cityName)
        }
    }

    override fun getWeatherList(): Flow<List<CityWeather>> =
        db.weatherDao().subscribeToCityWithWeatherList()
            .map { dboList ->
                dboList.map { dboMapper(it) }
            }
}