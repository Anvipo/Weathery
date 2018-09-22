package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import ru.mts.avpopo85.weathery.data.model.base.CurrentWeatherParameters

data class YWCurrentWeatherParameters(

    val latitude: Double,

    val longitude: Double,

    val language: String

) : CurrentWeatherParameters
