package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.currentWeather

import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.currentWeather.IYWCurrentWeatherParameters

data class YWCurrentWeatherParameters(

    override val latitude: Double,

    override val longitude: Double,

    override val language: String

) : IYWCurrentWeatherParameters
