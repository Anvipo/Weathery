package ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

/**This object contains weather forecast data.*/
data class Forecast(
    /**Date of the forecast, in the format YYYY-MM-DD.*/
    val date: String,

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

    /**Forecasts by time of day and 12-hour forecasts*/
    val parts: Parts,

    /**Object for the hourly forecast*/
    val hours: List<HourInfo>?
) : Parcelable
