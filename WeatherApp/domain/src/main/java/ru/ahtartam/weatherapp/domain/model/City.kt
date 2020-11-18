package ru.ahtartam.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val cityId: Int,
    val cityName: String,
    val location: GeoLocation
) : Parcelable