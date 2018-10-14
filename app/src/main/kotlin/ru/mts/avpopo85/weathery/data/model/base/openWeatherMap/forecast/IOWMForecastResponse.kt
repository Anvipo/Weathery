package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMListItemResponse

interface IOWMForecastResponse : IBaseWeatherResponse {

    val city: IOWMCityResponse

    /**Number of lines returned by this API call*/
    val numberOfLines: Int

    /**Internal parameter*/
    val code: String

    /**Internal parameter*/
    val message: Double

    val forecastsList: List<OWMListItemResponse>

}
