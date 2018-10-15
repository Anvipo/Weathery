package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMClouds

@Suppress("unused")
data class OWMClouds(override val cloudiness: Int) : IOWMClouds
