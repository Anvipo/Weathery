package ru.mts.avpopo85.weathery.domain.model.base.yandexWeather

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWParts

interface IYWForecast : IForecast {

    /**The date of the forecast in Unix time.*/
    val dateInUnixtime: String

    /**Week number.*/
    val weekSerialNumber: Int

    /**Time of the sunrise in local time (may be omitted for polar regions).*/
    val sunriseInLocalTime: String?

    /**Time of the sunset in local time (may be omitted for polar regions).*/
    val sunsetInLocalTime: String?

    /**Code of the lunar phase*/
    val moonCode: String

    /**Text code for the lunar phase*/
    val moonText: String

    /**Forecasts by time of day and 12-hour yandexForecasts*/
    val parts: YWParts

    /**Object for the hourly forecast*/
    val hours: List<IYWHourInfo>?

}
