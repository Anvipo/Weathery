package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMCurrentWeather

/**This object contains information about the current weather.*/
data class OWMCurrentWeather(

    override val coordinates: OWMCoordinates,

    override val weather: List<OWMWeather>,

    override val base: String,

    override val main: OWMMain,

    override val visibilityInMeters: Int,

    override val wind: OWMWind,

    override val clouds: OWMClouds,

    override val timeOfDataCalculation: String,

    override val sys: OWMSys,

    override val cityID: Int,

    override val cityName: String,

    override val code: Int

) : IOWMCurrentWeather