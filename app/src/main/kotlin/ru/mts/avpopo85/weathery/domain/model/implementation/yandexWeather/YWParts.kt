package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**Forecasts by time of day and 12-hour yandexForecasts.*/
data class YWParts(

    /**Nighttime forecast.*/
    val nightForecast: YWDayTime,

    /**Morning forecast.*/
    val morningForecast: YWDayTime,

    /**Afternoon forecast.*/
    val YWDayForecast: YWDayTime,

    /**Evening forecast.*/
    val eveningForecast: YWDayTime,

    /**12-hour forecast for the day.*/
    val _12HoursYWDayForecast: YWDayShort,

    /**12-hour forecast for the upcoming night.*/
    val _12HoursNightForecast: YWDayShort

) : Parcelable
