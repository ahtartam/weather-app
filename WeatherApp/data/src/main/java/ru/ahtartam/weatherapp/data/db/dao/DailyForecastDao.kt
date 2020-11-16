package ru.ahtartam.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ahtartam.weatherapp.data.db.Converters
import ru.ahtartam.weatherapp.data.db.model.CityWithDailyForecast
import ru.ahtartam.weatherapp.data.db.model.DailyForecast

@Dao
@TypeConverters(Converters::class)
interface DailyForecastDao {
    @Transaction
    @Query("SELECT * FROM weather WHERE cityId = :cityId")
    fun subscribeToCityWithDailyForecast(cityId: Int): LiveData<CityWithDailyForecast>

    @Query("DELETE FROM forecast WHERE cityId = :cityId")
    suspend fun deleteByCityId(cityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<DailyForecast>)
}
