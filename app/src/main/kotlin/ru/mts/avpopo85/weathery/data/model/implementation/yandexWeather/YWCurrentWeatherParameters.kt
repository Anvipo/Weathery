package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWCurrentWeatherParameters

data class YWCurrentWeatherParameters(

    override val latitude: Double,

    override val longitude: Double,

    override val language: String

) : IYWCurrentWeatherParameters
