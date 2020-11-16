package ru.ahtartam.weatherapp.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE weather_tmp (cityId INTEGER NOT NULL, cityName TEXT, temperature REAL, lat REAL, lon REAL)")
        database.execSQL("INSERT INTO weather_tmp (cityId, cityName, temperature, lat, lon) SELECT cityId, c.name AS cityName, temperature, lat, lon FROM weather AS w LEFT JOIN citylist AS c ON c.id=w.cityId")

        // There are two Moscow in the service database. Now the request is made by name, and the server gives another Moscow.
        // Changing the city to avoid duplicates after the first update.
        database.execSQL("DELETE FROM weather_tmp WHERE cityId=524894")
        database.execSQL("INSERT INTO weather_tmp (cityId, cityName, temperature, lat, lon) VALUES (524901, 'Moscow', NULL, 55.75, 37.6199989318848)")

        database.execSQL("DROP TABLE weather")
        database.execSQL("CREATE TABLE weather (cityId INTEGER NOT NULL, temperature REAL, cityName TEXT NOT NULL, lat REAL NOT NULL, lon REAL NOT NULL, PRIMARY KEY(cityId))")
        database.execSQL("INSERT INTO weather (cityId, cityName, temperature, lat, lon) SELECT cityId, cityName, temperature, lat, lon FROM weather_tmp WHERE lat not NULL AND lon not NULL AND cityName not NULL")
        database.execSQL("DROP TABLE weather_tmp")
        database.execSQL("DROP TABLE citylist")
    }
}