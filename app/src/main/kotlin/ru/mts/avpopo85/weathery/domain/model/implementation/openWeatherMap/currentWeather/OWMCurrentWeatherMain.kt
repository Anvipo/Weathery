package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather.IOWMCurrentWeatherMain

data class OWMCurrentWeatherMain(

    override val temperature: String,

    override val atmosphericPressureInhPa: Int,

    override val humidity: Int

) : IOWMCurrentWeatherMain