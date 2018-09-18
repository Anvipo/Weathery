package ru.mts.avpopo85.weathery.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**Forecasts by time of day and 12-hour forecasts.*/
data class Parts(

    /**Nighttime forecast.*/
    val nightForecast: DayTime,

    /**Morning forecast.*/
    val morningForecast: DayTime,

    /**Afternoon forecast.*/
    val dayForecast: DayTime,

    /**Evening forecast.*/
    val eveningForecast: DayTime,

    /**12-hour forecast for the day.*/
    val _12HoursDayForecast: DayShort,

    /**12-hour forecast for the upcoming night.*/
    val _12HoursNightForecast: DayShort

) : Parcelable
