package ru.ahtartam.weatherapp.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.ahtartam.weatherapp.api.WeatherAdiService

@Module(includes = [MainModule.ProvidesModule::class])
interface MainModule {

    @Module
    class ProvidesModule {
        @Provides
        fun provideWeatherAdiService(retrofit: Retrofit): WeatherAdiService =
            retrofit.create()

        @Provides
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }
}