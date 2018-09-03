package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
        //Internal parameter
        @SerializedName("cod")
        @Expose
        val code: String,

        //Internal parameter
        @SerializedName("message")
        @Expose
        val message: Double,

        //Number of lines returned by this API call
        @SerializedName("cnt")
        @Expose
        val cnt: Int,

        @SerializedName("list")
        @Expose
        val forecastInfo: List<ForecastInfo>,

        @SerializedName("city")
        @Expose
        val city: City
)
