package ru.mts.avpopo85.weathery.data.model.base.yandexWeather

import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWHourInfoResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWPartsResponse

@Suppress("SpellCheckingInspection")
interface IYWForecastResponse : IForecastResponse {

    /**Date of the forecast, in the format YYYY-MM-DD.*/
    override val date: String

    /**The date of the forecast in Unix time.*/
    val dateInUnixtime: Int

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