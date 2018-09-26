package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast

interface IYWForecastParameters {

    val latitude: Double

    val longitude: Double

    val language: String

    val dayNumberInForecast: Int

    val withForecastForHours: Boolean

    val withExtraInformation: Boolean

}