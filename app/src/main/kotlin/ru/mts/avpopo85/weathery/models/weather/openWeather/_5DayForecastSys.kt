package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class _5DayForecastSys(
        @SerializedName("pod")
        @Expose
        val pod: String
)