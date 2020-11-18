package ru.ahtartam.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ahtartam.weatherapp.data.db.dao.DailyForecastDao
import ru.ahtartam.weatherapp.data.db.dao.WeatherDao
import ru.ahtartam.weatherapp.data.db.model.CityForecastDBO
import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO

@Database(
    entities = [
        CityWeatherDBO::class,
        CityForecastDBO::class
    ], version = 3
)
abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun dailyForecastDao(): DailyForecastDao
}