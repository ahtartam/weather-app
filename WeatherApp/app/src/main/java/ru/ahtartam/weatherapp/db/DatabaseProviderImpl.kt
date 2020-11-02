package ru.ahtartam.weatherapp.db

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class DatabaseProviderImpl @Inject constructor(
    private val context: Context
) : DatabaseProvider {
    private var database: Database? = null
        get() {
            if (field == null) {
                field = buildDatabase()
            }
            return field
        }

    override fun get(): Database = checkNotNull(database)

    private fun buildDatabase(): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "weather_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}