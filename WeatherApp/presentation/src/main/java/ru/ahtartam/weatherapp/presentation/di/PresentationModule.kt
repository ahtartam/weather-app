package ru.ahtartam.weatherapp.presentation.di

import dagger.Binds
import dagger.Module
import ru.ahtartam.weatherapp.presentation.mvp.*
import javax.inject.Singleton

@Module
interface PresentationModule {
    @Binds
    @Singleton
    fun bindCityListPresenter(impl: CityListPresenter): CityListContract.Presenter

    @Binds
    @Singleton
    fun bindCityDetailsPresenter(impl: CityDetailsPresenter): CityDetailsContract.Presenter

    @Binds
    @Singleton
    fun bindAddCityPresenter(impl: AddCityPresenter): AddCityContract.Presenter
}