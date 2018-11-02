package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast

import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast.IOWMForecast
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType

/**This object contains weather forecast data.*/
@Parcelize
data class OWMForecast(

    override val precipitationType: PrecipitationType,

    override val isFresh: Boolean,

    override val cityName: String,

    override val date: String,

    override val dateInSeconds: Long,

    override val cloudiness: String,

    override val weather: OWMWeather,

    override val dayTime: String,

    override val rainVolumeMm: Double,

    override val main: OWMForecastMain,

    override val windSpeed: String,

    override val windDirection: String

) : IOWMForecast
