package ru.ahtartam.weatherapp.data.api

import android.icu.util.LocaleData
import android.icu.util.ULocale
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ahtartam.weatherapp.data.api.response.DailyForecastResponse
import ru.ahtartam.weatherapp.data.api.response.WeatherResponse
import java.util.*

private const val weatherApiKey = "a913876322cbac7165b3285a9b1d09e7"

fun getLanguage(): String = Locale.getDefault().language

fun getUnits(): String {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        when(LocaleData.getMeasurementSystem(ULocale.getDefault())) {
            LocaleData.MeasurementSystem.US -> "imperial"
            else -> "metric"
        }
    } else {
        Locale.getDefault().country.let { countryCode ->
            when(countryCode) {
                "US" -> "imperial"
                else -> "metric"
            }
        }
    }
}

interface WeatherApiService {
    @GET("weather")
    suspend fun weatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = weatherApiKey,
        @Query("units") units: String = getUnits(),
        @Query("lang") lang: String = getLanguage()
    ): WeatherResponse

    /*
    * Оказалось, что "Daily Forecast 16 Days" (https://openweathermap.org/forecast16) недоступен
    * из бесплатного аккаунта. Пришлось переходить на "One Call API" (https://openweathermap.org/api/one-call-api).
    * В этом запросе есть и прогноз, и текущая температура. Можно было бы обойтись только одним
    * API-поинтом, но я решил не убирать первый запрос, так как тогда пропал бы весь челендж. ))
    * В реальном проекте, я конечно же убрал бы лишний запрос.
    * */
    @GET("onecall")
    suspend fun dailyForecastByCityId(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("appid") apiKey: String = weatherApiKey,
        @Query("units") units: String = getUnits(),
        @Query("lang") lang: String = getLanguage()
    ): DailyForecastResponse
}