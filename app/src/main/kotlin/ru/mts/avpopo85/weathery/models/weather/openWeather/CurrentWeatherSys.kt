package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherSys(
        //Internal parameter
        @SerializedName("type")
        @Expose
        val type: Int,

        //Internal parameter
        @SerializedName("id")
        @Expose
        val id: Int,

        //Internal parameter
        @SerializedName("message")
        @Expose
        val message: Double,

        //Country code (GB, JP etc.)
        @SerializedName("country")
        @Expose
        val countryCode: String,

        //Sunrise time, unix, UTC
        @SerializedName("sunrise")
        @Expose
        val sunriseTimeInUnixUTC: Int,

        //Sunset time, unix, UTC
        @SerializedName("sunset")
        @Expose
        val sunsetTimeInUnixUTC: Int
)
