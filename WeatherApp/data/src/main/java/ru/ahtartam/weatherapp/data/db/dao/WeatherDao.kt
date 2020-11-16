package ru.ahtartam.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM weather")
    fun subscribeToCityWithWeatherList(): LiveData<List<CityWeatherDBO>>

    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId LIMIT 1")
    fun subscribeToCityWithWeatherList(cityId: Int): LiveData<CityWeatherDBO>

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getCityWithWeatherList(): List<CityWeatherDBO>

    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId LIMIT 1")
    suspend fun getWeatherByCityId(cityId: Int): CityWeatherDBO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<CityWeatherDBO>)

    @Query("DELETE FROM weather WHERE cityId = :cityId")
    suspend fun deleteByCityId(cityId: Int)
}
