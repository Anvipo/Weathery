package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCloudsResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMWindResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastMainResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastSysResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMRainResponse

interface IOWMListItemResponse : IForecastRealmResponse {

    /**Time of data forecasted, unix, UTC*/
    override val dateInUnixtimeUTC: Long

    val rain: OWMRainResponse?

    /**Data/time of calculation, UTC*/
    override val dateUTC: String

    val weather: List<OWMWeatherResponse>

    val main: OWMForecastMainResponse?

    val clouds: OWMCloudsResponse?

    val sys: OWMForecastSysResponse?

    val wind: OWMWindResponse?

}
