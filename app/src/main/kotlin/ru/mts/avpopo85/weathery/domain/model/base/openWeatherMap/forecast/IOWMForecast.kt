package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWeather
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWind

interface IOWMForecast : IForecast {

    override val date: String

    val cloudiness: Int

    val wind: IOWMWind

    val weather: IOWMWeather

    val dayTime: String

    val rainVolumeMm: Double

    val main: IOWMForecastMain

}
