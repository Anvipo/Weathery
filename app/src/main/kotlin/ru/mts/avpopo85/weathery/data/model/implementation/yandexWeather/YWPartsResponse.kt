package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWPartsResponse

/**Forecasts by time of day and 12-hour YWForecasts.*/
open class YWPartsResponse(

    @SerializedName("night")
    @Expose
    override var nightForecastResponse: YWDayTimeResponse? = null,

    @SerializedName("morning")
    @Expose
    override var morningForecastResponse: YWDayTimeResponse? = null,

    @SerializedName("day")
    @Expose
    override var dayForecastResponse: YWDayTimeResponse? = null,

    @SerializedName("evening")
    @Expose
    override var eveningForecastResponse: YWDayTimeResponse? = null,

    @SerializedName("day_short")
    @Expose
    override var _12HoursDayForecastResponse: YWDayShortResponse? = null,

    @SerializedName("night_short")
    @Expose
    override var _12HoursNightForecastResponse: YWDayShortResponse? = null

) : RealmObject(), IYWPartsResponse
