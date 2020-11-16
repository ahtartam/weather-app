package ru.ahtartam.weatherapp.data.db.model

import androidx.room.Entity
import androidx.room.TypeConverters
import ru.ahtartam.weatherapp.data.db.Converters
import java.util.*

@Entity(
    tableName = "forecast",
    primaryKeys = ["cityId", "date"]
)
@TypeConverters(Converters::class)
data class CityForecastDBO(
    val cityId: Int,
    val date: Date,
    val temperature: Float?
)