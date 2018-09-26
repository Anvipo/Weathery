package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWeather

@Parcelize
data class OWMWeather(

    override val conditionCode: Int,

    override val groupOfWeatherParameters: String,

    override val description: String,

    override val icon: String

) : IOWMWeather, Parcelable