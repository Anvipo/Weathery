package ru.mts.avpopo85.weathery.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**Forecasts by time of day and 12-hour forecasts.*/
data class PartsResponse(

    /**Nighttime forecast.*/
    @SerializedName("night")
    @Expose
    val nightForecastResponse: DayTimeResponse,

    /**Morning forecast.*/
    @SerializedName("morning")
    @Expose
    val morningForecastResponse: DayTimeResponse,

    /**Afternoon forecast.*/
    @SerializedName("day")
    @Expose
    val dayForecastResponse: DayTimeResponse,

    /**Evening forecast.*/
    @SerializedName("evening")
    @Expose
    val eveningForecastResponse: DayTimeResponse,

    /**12-hour forecast for the day.*/
    @SerializedName("day_short")
    @Expose
    val _12HoursDayForecastResponse: DayShortResponse,

    /**12-hour forecast for the upcoming night.*/
    @SerializedName("night_short")
    @Expose
    val _12HoursNightForecastResponse: DayShortResponse

)
