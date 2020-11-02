package ru.ahtartam.weatherapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.api.WeatherAdiService
import ru.ahtartam.weatherapp.db.DatabaseProvider
import ru.ahtartam.weatherapp.db.DatabaseProviderImpl
import ru.ahtartam.weatherapp.mvp.*
import javax.inject.Singleton

@Module(includes = [MainModule.ProvidesModule::class])
interface MainModule {

    @Binds
    fun bindDatabaseProvider(impl: DatabaseProviderImpl): DatabaseProvider

    @Binds
    @Singleton
    fun bindCityListPresenter(impl: CityListPresenter): CityListContract.Presenter

    @Binds
    @Singleton
    fun bindCityDetailsPresenter(impl: CityDetailsPresenter): CityDetailsContract.Presenter

    @Binds
    @Singleton
    fun bindAddCityPresenter(impl: AddCityPresenter): AddCityContract.Presenter

    @Module
    class ProvidesModule {
        @Provides
        fun provideAppContext(): Context =
            WeatherApp.instance!!.getApplicationContext()

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