package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWind

@Parcelize
data class OWMWind(

    override val speedInUnits: String,

    override val direction: String

) : IOWMWind, Parcelable