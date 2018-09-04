package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Parts(
        //прогноз на ночь
        @SerializedName("night")
        @Expose
        val nightForecast: DayTime,

        //прогноз на утро
        @SerializedName("morning")
        @Expose
        val morningForecast: DayTime,

        //прогноз на день
        @SerializedName("day")
        @Expose
        val dayForecast: DayTime,

        //прогноз на вечер
        @SerializedName("evening")
        @Expose
        val eveningForecast: DayTime,

        //12-часовой прогноз на день
        @SerializedName("day_short")
        @Expose
        val _12HoursDayForecast: DayShort,

        //12-часовой прогноз на ночь
        @SerializedName("night_short")
        @Expose
        val _12HoursNightForecast: DayShort
)
