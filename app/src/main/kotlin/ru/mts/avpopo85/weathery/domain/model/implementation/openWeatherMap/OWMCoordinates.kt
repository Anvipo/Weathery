package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMCoordinates

data class OWMCoordinates(

    override val longitude: Double,

    override val latitude: Double

) : IOWMCoordinates