package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWeather

data class OWMWeather(

    override val id: Int,

    override val groupOfWeatherParameters: String,

    override val description: String,

    override val icon: String

) : IOWMWeather