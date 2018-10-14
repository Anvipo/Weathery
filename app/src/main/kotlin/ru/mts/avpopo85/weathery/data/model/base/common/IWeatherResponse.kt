package ru.mts.avpopo85.weathery.data.model.base.common

import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IBaseWeatherResponse

interface IWeatherResponse : IBaseWeatherResponse {

    val cityName: String

}
