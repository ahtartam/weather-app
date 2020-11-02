package ru.ahtartam.weatherapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.ahtartam.weatherapp.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM citylist")
    fun getCityList(): LiveData<List<City>>
}