package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.Forecast

@Parcelize

/**This object contains weather forecast data.*/
data class YWForecast(

    /**Date of the forecast, in the format YYYY-MM-DD.*/
    override val date: String,

    /**The date of the forecast in Unix time.*/
    val date_ts: String,

    /**Week number.*/
    val weekSerialNumber: Int,

    /**Time of the sunrise in local time (may be omitted for polar regions).*/
    val sunriseInLocalTime: String?,

    /**Time of the sunset in local time (may be omitted for polar regions).*/
    val sunsetInLocalTime: String?,

    /**Code of the lunar phase*/
    val moonCode: String,

    /**Text code for the lunar phase*/
    val moonText: String,

    /**Forecasts by time of day and 12-hour yandexForecasts*/
    val YWParts: YWParts,

    /**Object for the hourly forecast*/
    val YWHours: List<YWHourInfo>?

) : Parcelable, Forecast
