package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMClouds

data class OWMClouds(override val cloudiness: Int) : IOWMClouds