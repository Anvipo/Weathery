package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMMain

data class OWMMain(

    override val temperature: Double,

    override val atmosphericPressureInhPa: Int,

    override val humidity: Int,

    override val minimumTemperature: Double,

    override val maximumTemperature: Double

) : IOWMMain