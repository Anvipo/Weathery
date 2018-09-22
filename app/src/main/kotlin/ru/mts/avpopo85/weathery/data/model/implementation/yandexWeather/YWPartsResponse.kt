package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

/**Forecasts by time of day and 12-hour YWForecasts.*/
open class YWPartsResponse(

    /**Nighttime forecast.*/
    @SerializedName("night")
    @Expose
    var nightForecastResponseYW: YWDayTimeResponse? = null,

    /**Morning forecast.*/
    @SerializedName("morning")
    @Expose
    var morningForecastResponseYW: YWDayTimeResponse? = null,

    /**Afternoon forecast.*/
    @SerializedName("day")
    @Expose
    var YWDayForecastResponse: YWDayTimeResponse? = null,

    /**Evening forecast.*/
    @SerializedName("evening")
    @Expose
    var eveningForecastResponseYW: YWDayTimeResponse? = null,

    /**12-hour forecast for the day.*/
    @SerializedName("day_short")
    @Expose
    var _12HoursYWDayForecastResponse: YWDayShortResponse? = null,

    /**12-hour forecast for the upcoming night.*/
    @SerializedName("night_short")
    @Expose
    var _12HoursNightForecastResponseYW: YWDayShortResponse? = null

) : RealmObject()
