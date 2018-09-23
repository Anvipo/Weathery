package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap

import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.OWMMainResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.OWMSysResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.OWMWeatherResponse

interface IOWMCurrentWeatherResponse : ICurrentWeatherResponse {

    val coordinates: IOWMCoordinatesResponse?

    /**more info Weather condition codes*/
    val weather: List<OWMWeatherResponse>

    /**Weather condition cityID*/
    val base: String

    val main: OWMMainResponse?

    val visibility: Int

    val wind: IOWMWindResponse?

    val clouds: IOWMCloudsResponse?

    val date: Int

    val sys: OWMSysResponse?

    /**City ID*/
    val cityID: Int

    /**City name*/
    val cityName: String

    /**Internal parameter*/
    val code: Int

}