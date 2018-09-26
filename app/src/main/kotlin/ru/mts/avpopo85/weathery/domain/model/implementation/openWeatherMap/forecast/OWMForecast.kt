package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast.IOWMForecast
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.OWMForecastMain
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.OWMWind

@Suppress("SpellCheckingInspection")
@Parcelize

/**This object contains weather forecast data.*/
data class OWMForecast(

    override val date: String,

    override val cloudiness: Int,

    override val wind: OWMWind,

    override val weather: OWMWeather,

    override val dayTime: String,

    override val rainVolumeMm: Double,

    override val main: OWMForecastMain

) : Parcelable, IOWMForecast
