package ru.ahtartam.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ahtartam.weatherapp.data.db.dao.DailyForecastDao
import ru.ahtartam.weatherapp.data.db.dao.WeatherDao
import ru.ahtartam.weatherapp.data.db.model.DailyForecast
import ru.ahtartam.weatherapp.data.db.model.Weather

@Database(
    entities = [
        Weather::class,
        DailyForecast::class
    ], version = 3
)
abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun dailyForecastDao(): DailyForecastDao
}