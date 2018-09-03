package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastInfo(
        //Time of data forecasted, unix, UTC
        @SerializedName("dt")
        @Expose
        val calculationTimeInUnixUTC: Int,

        @SerializedName("main")
        @Expose
        val main: _5DayForecastMain,

        @SerializedName("weather")
        @Expose
        val weather: List<Weather>,

        @SerializedName("clouds")
        @Expose
        val clouds: Clouds,

        @SerializedName("wind")
        @Expose
        val wind: Wind,

        @SerializedName("sys")
        @Expose
        val sys: _5DayForecastSys,

        //Data/time of calculation, UTC
        @SerializedName("dt_txt")
        @Expose
        val calculationTimeInUTC: String
)
