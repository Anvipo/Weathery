package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.IOWMCloudsResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.IOWMCoordinatesResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMMainResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMSysResponse

interface IOWMCurrentWeatherResponse : ICurrentWeatherResponse {

    val coordinates: IOWMCoordinatesResponse?

    val weather: List<OWMWeatherResponse>

    /**Internal parameter*/
    val base: String

    val main: OWMMainResponse?

    val visibility: Int

    val wind: IOWMWindResponse?

    val clouds: IOWMCloudsResponse?

    /**Time of data calculation, unix, UTC*/
    val timeOfDataCalculationUnixUTC: Long

    val sys: OWMSysResponse?

    /**City ID*/
    val cityID: Int

    /**City name*/
    val cityName: String

    /**Internal parameter*/
    val code: Int

}