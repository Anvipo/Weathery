package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coordinates(
        //City geo location, longitude
        @SerializedName("lon")
        @Expose
        val longitude: Double,

        //City geo location, latitude
        @SerializedName("lat")
        @Expose
        val latitude: Double
)

