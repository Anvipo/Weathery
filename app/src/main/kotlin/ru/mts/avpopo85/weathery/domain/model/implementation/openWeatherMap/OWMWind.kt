package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWind

data class OWMWind(

    override val speed: Int,

    override val directionInDegrees: Int

) : IOWMWind