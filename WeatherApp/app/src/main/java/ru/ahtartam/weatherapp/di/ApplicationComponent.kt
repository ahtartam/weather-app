package ru.ahtartam.weatherapp.di

import dagger.Component
import ru.ahtartam.weatherapp.ui.citydetails.CityDetailsFragment
import ru.ahtartam.weatherapp.ui.citylist.CityListFragment

@Component(
    modules = [
        MainModule::class
    ]
)
interface ApplicationComponent {
    fun inject(fragment: CityListFragment)
    fun inject(fragment: CityDetailsFragment)
}