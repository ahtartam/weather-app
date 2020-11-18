package ru.ahtartam.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoLocation(
    val lat: Float,
    val lon: Float
) : Parcelable