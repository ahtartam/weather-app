package ru.ahtartam.weatherapp.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM weather")
    fun subscribeToCityWithWeatherList(): Flow<List<CityWeatherDBO>>

    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId LIMIT 1")
    fun subscribeToCityWithWeather(cityId: Int): Flow<CityWeatherDBO>

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getCityWithWeatherList(): List<CityWeatherDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<CityWeatherDBO>)

    @Query("DELETE FROM weather WHERE cityId = :cityId")
    suspend fun deleteByCityId(cityId: Int)
}
