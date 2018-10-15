package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast

import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast.YWHourInfoResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast.YWPartsResponse

interface IYWForecastResponse : IForecastRealmResponse {

    /**Week number.*/
    val weekSerialNumber: Int

    /**Time of the sunrise in local time (may be omitted for polar regions).*/
    val sunriseInLocalTime: String?

    /**Time of the sunset in local time (may be omitted for polar regions).*/
    val sunsetInLocalTime: String?

    /**Code of the lunar phase*/
    val moonCode: Int

    /**Text code for the lunar phase*/
    val moonText: String

    /**Forecasts by time of day and 12-hour forecasts*/
    val partsResponse: YWPartsResponse?

    /**Object for the hourly forecast*/
    val hours: List<YWHourInfoResponse>

}
