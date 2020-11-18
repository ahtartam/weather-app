package ru.ahtartam.weatherapp.data.db

interface DatabaseProvider {
    fun get(): Database
}