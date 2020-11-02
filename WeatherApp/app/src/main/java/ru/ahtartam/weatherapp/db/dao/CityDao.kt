package ru.ahtartam.weatherapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.ahtartam.weatherapp.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM citylist")
    fun getCityList(): LiveData<List<City>>

    @Query("SELECT * FROM citylist WHERE id = :cityId LIMIT 1")
    suspend fun getCityById(cityId: Int): City
}