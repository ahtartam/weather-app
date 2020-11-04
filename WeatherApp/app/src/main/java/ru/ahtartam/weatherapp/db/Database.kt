package ru.ahtartam.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ahtartam.weatherapp.db.dao.CityDao
import ru.ahtartam.weatherapp.db.dao.DailyForecastDao
import ru.ahtartam.weatherapp.db.dao.WeatherDao
import ru.ahtartam.weatherapp.model.City
import ru.ahtartam.weatherapp.model.DailyForecast
import ru.ahtartam.weatherapp.model.Weather

@Database(
    entities = [
        City::class,
        Weather::class,
        DailyForecast::class
    ], version = 2
)
abstract class Database : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
    abstract fun dailyForecastDao(): DailyForecastDao
}