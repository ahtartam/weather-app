package ru.ahtartam.weatherapp.di

import dagger.Component
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.ui.addcity.AddCityFragment
import ru.ahtartam.weatherapp.ui.citydetails.CityDetailsFragment
import ru.ahtartam.weatherapp.ui.citylist.CityListFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class
    ]
)
interface ApplicationComponent {
    fun inject(app: WeatherApp)
    fun inject(fragment: CityListFragment)
    fun inject(fragment: CityDetailsFragment)
    fun inject(fragment: AddCityFragment)
}