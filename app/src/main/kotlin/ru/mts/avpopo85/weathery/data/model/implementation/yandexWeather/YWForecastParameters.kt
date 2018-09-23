package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWForecastParameters

data class YWForecastParameters(

    override val latitude: Double,

    override val longitude: Double,

    override val language: String,

    override val dayNumberInForecast: Int,

    override val withForecastForHours: Boolean,

    override val withExtraInformation: Boolean

) : IYWForecastParameters
