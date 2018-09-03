package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
        @SerializedName("coord")
        @Expose
        val coordinates: Coordinates,

        @SerializedName("weather")
        @Expose
        val weather: List<Weather>,

        //Internal parameter
        @SerializedName("base")
        @Expose
        val base: String,

        @SerializedName("main")
        @Expose
        val currentWeatherMain: CurrentWeatherMain,

        //Visibility, meter
        @SerializedName("visibility")
        @Expose
        val visibilityInMeters: Int,

        @SerializedName("wind")
        @Expose
        val wind: Wind,

        @SerializedName("clouds")
        @Expose
        val clouds: Clouds,

        //Time of data calculation, unix, UTC
        @SerializedName("dt")
        @Expose
        val calculationTimeInUnixUTC: Int,

        @SerializedName("sys")
        @Expose
        val sys: CurrentWeatherSys,

        //City ID
        @SerializedName("id")
        @Expose
        val cityID: Int,

        //City name
        @SerializedName("name")
        @Expose
        val cityName: String,

        //Internal parameter
        @SerializedName("cod")
        @Expose
        val code: Int
)
