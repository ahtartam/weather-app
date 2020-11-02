package ru.ahtartam.weatherapp.db

interface DatabaseProvider {
    fun get(): Database
}