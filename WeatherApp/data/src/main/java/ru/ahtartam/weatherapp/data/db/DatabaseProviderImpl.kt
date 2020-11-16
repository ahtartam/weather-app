package ru.ahtartam.weatherapp.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.ahtartam.weatherapp.data.db.migrations.Migration1To2
import ru.ahtartam.weatherapp.data.db.migrations.Migration2To3
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
            "weather.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("insert into weather (cityId, cityName, temperature, lat, lon) VALUES (524901, 'Moscow', NULL, 55.75, 37.6199989318848)")
                    db.execSQL("insert into weather (cityId, cityName, temperature, lat, lon) VALUES (498817, 'Saint Petersburg', NULL, 59.8899993896484, 30.2600002288818)")
                }
            })
            .addMigrations(
                Migration1To2(),
                Migration2To3()
            )
            .build()
    }
}