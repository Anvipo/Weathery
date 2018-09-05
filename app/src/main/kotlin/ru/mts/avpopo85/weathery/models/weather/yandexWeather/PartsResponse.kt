package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PartsResponse(
    //прогноз на ночь
    @SerializedName("night")
    @Expose
    val nightForecast: DayTimeResponse,

    //прогноз на утро
    @SerializedName("morning")
    @Expose
    val morningForecast: DayTimeResponse,

    //прогноз на день
    @SerializedName("day")
    @Expose
    val dayForecastResponse: DayTimeResponse,

    //прогноз на вечер
    @SerializedName("evening")
    @Expose
    val eveningForecast: DayTimeResponse,

    //12-часовой прогноз на день
    @SerializedName("day_short")
    @Expose
    val _12HoursDayForecastResponse: DayShortResponse,

    //12-часовой прогноз на ночь
    @SerializedName("night_short")
    @Expose
    val _12HoursNightForecast: DayShortResponse
)
