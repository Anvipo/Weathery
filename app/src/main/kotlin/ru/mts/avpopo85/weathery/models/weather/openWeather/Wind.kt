package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind(
        //Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
        @SerializedName("speed")
        @Expose
        val speedInUnits: Double,

        //Wind direction, degrees (meteorological)
        @SerializedName("deg")
        @Expose
        val directionInDegrees: String
)