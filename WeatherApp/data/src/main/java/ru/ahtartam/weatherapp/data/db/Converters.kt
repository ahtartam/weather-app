package ru.ahtartam.weatherapp.data.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date): Long {
            return date.time
        }

        @TypeConverter
        @JvmStatic
        fun toDate(date: Long): Date {
            return Date(date)
        }
    }
}