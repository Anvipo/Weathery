package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather.IOWMCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather.IOWMCurrentWeatherMain
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather

/**This object contains information about the current weather.*/
data class OWMCurrentWeather(

    override val weather: List<OWMWeather>,

    override val main: IOWMCurrentWeatherMain,

    override val visibilityInMeters: Int,

    override val wind: OWMWind,

    override val cloudiness: Int,

    override val timeOfDataCalculation: String,

    override val sys: OWMSys

) : IOWMCurrentWeather