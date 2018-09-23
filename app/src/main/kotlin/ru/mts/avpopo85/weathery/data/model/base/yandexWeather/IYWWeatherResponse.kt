package ru.mts.avpopo85.weathery.data.model.base.yandexWeather

import ru.mts.avpopo85.weathery.data.model.base.common.IWeatherResponse
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType

interface IYWWeatherResponse : IWeatherResponse {

    /**The time on the server in Unix time.*/
    val serverInUnix: Int

    /**The time on the server in UTC.*/
    val serverTimeInUTC: String

    /**Locality information object.*/
    val cityInfoResponse: IYWCityInfoResponse

    /**Current weather information object.	*/
    val currentWeatherResponse: YWCurrentWeatherResponseType

    /**Weather forecast object.*/
    val forecasts: YWForecastListResponseType

}