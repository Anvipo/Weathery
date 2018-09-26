package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWind

@Parcelize
data class OWMWind(

    override val speedInUnits: Double,

    override val direction: String

) : IOWMWind, Parcelable