package ru.ahtartam.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ahtartam.weatherapp.data.db.model.Weather

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM weather")
    fun subscribeToCityWithWeatherList(): LiveData<List<Weather>>

    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId LIMIT 1")
    fun subscribeToCityWithWeatherList(cityId: Int): LiveData<Weather>

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getCityWithWeatherList(): List<Weather>

    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId LIMIT 1")
    suspend fun getWeatherByCityId(cityId: Int): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<Weather>)

    @Query("DELETE FROM weather WHERE cityId = :cityId")
    suspend fun deleteByCityId(cityId: Int)
}
