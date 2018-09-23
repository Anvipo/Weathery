package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMSys

data class OWMSys(

    override val type: Int,

    override val id: Int,

    override val message: Double,

    override val countryCode: String,

    override val sunrise: String,

    override val sunset: String

) : IOWMSys