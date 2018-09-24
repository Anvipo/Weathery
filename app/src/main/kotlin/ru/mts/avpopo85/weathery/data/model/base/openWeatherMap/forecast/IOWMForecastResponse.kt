package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMListItemResponse

interface IOWMForecastResponse : IForecastResponse {

    val city: IOWMCityResponse

    /**Number of lines returned by this API call*/
    val numberOfLines: Double

    /**Internal parameter*/
    val code: String

    /**Internal parameter*/
    val message: Double

    val list: List<OWMListItemResponse>

}
