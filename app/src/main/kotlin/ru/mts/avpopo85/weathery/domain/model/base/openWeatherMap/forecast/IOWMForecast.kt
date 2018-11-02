package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWeather

interface IOWMForecast : IForecast {

    /**Wind speed.
     *
     *Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
    val windSpeed: String

    val windDirection: String

    val weather: IOWMWeather

    val dayTime: String

    val rainVolumeMm: Double

    val main: IOWMForecastMain

}
