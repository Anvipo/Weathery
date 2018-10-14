package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather.IOWMCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather

/**This object contains information about the current weather.*/
data class OWMCurrentWeather(

    override val weather: OWMWeather,

    override val visibilityInMeters: Int,

    override val cloudiness: String,

    override val timeOfDataCalculation: String,

    override val sys: OWMSys,

    override val cityName: String,

    override val temperature: String,

    override val atmosphericPressureInhPa: String,

    override val humidity: String,

    override val windSpeed: String,

    override val windDirection: String

) : IOWMCurrentWeather