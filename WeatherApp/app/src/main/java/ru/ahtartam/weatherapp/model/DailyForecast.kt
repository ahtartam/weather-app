package ru.ahtartam.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ahtartam.weatherapp.db.Converters
import java.util.*

@Entity(
    tableName = "forecast",
    primaryKeys = ["cityId", "date"]
)
@TypeConverters(Converters::class)
data class DailyForecast(
    val cityId: Int,
    val date: Date,
    val temperature: Float?
)