package ru.ahtartam.weatherapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ahtartam.weatherapp.model.CityWithWeather
import ru.ahtartam.weatherapp.model.Weather

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM weather")
    fun subscribeToCityWithWeatherList(): LiveData<List<CityWithWeather>>

    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId LIMIT 1")
    fun subscribeToCityWithWeatherList(cityId: Int): LiveData<CityWithWeather>

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getCityWithWeatherList(): List<CityWithWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<Weather>)

    @Query("DELETE FROM weather WHERE cityId = :cityId")
    suspend fun deleteByCityId(cityId: Int)
}