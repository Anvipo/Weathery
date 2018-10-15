package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMCoordinates

@Suppress("unused")
data class OWMCoordinates(

    override val longitude: Double,

    override val latitude: Double

) : IOWMCoordinates
