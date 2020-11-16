package ru.ahtartam.weatherapp.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.ahtartam.weatherapp.data.api.WeatherApiService
import ru.ahtartam.weatherapp.data.db.DatabaseProvider
import ru.ahtartam.weatherapp.data.db.DatabaseProviderImpl
import javax.inject.Singleton

@Module(includes = [DataModule.ProvidesModule::class])
interface DataModule {
    @Binds
    @Singleton
    fun bindDatabaseProvider(impl: DatabaseProviderImpl): DatabaseProvider

    @Module
    class ProvidesModule {
        @Provides
        fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService =
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