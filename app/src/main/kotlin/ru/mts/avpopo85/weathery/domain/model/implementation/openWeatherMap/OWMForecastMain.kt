package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast.IOWMForecastMain

@Parcelize
data class OWMForecastMain(

    override val temperature: Double,

    override val atmosphericPressureOnTheGroundLevelInhPa: Double,

    override val atmosphericPressureOnTheSeaLevelByDefaultInhPa: Double,

    override val atmosphericPressureOnTheSeaLevelInhPa: Double,

    override val humidity: Int,

    override val minimumTemperature: Double,

    override val maximumTemperature: Double

) : IOWMForecastMain, Parcelable