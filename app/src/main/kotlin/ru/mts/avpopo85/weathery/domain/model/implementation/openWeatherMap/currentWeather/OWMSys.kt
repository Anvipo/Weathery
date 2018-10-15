package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMSys

data class OWMSys(

    override val sunrise: String,

    override val sunset: String

) : IOWMSys
