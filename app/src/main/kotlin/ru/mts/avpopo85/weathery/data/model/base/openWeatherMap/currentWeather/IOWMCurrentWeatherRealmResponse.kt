package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.IOWMCloudsResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMCurrentWeatherMainResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMSysResponse

interface IOWMCurrentWeatherRealmResponse : ICurrentWeatherRealmResponse {

    val weather: List<OWMWeatherResponse>

    /**Internal parameter*/
    val base: String

    val main: OWMCurrentWeatherMainResponse?

    val visibility: Int

    val wind: IOWMWindResponse?

    val clouds: IOWMCloudsResponse?

    val sys: OWMSysResponse?

    /**City ID*/
    val cityID: Int

    /**Internal parameter*/
    val code: Int

}
