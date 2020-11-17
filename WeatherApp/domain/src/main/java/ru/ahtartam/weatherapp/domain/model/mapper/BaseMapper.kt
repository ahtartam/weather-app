package ru.ahtartam.weatherapp.domain.model.mapper

interface BaseMapper<in From, out To> {
    fun map(from: From): To

    operator fun invoke(from: From): To = map(from)

    operator fun invoke(list: Collection<From>): List<To> = list.map(this::map)
}
