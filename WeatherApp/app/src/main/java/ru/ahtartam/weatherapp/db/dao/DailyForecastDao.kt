package ru.ahtartam.weatherapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ahtartam.weatherapp.db.Converters
import ru.ahtartam.weatherapp.model.CityWithDailyForecast
import ru.ahtartam.weatherapp.model.DailyForecast

@Dao
@TypeConverters(Converters::class)
interface DailyForecastDao {
    @Transaction
    @Query("SELECT * FROM citylist WHERE id = :cityId")
    fun subscribeToCityWithDailyForecast(cityId: Int): LiveData<CityWithDailyForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<DailyForecast>)
}