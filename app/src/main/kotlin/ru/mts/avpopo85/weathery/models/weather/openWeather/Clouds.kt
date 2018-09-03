package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Clouds(
        //Cloudiness, %
        @SerializedName("all")
        @Expose
        val cloudinessInPercents: Int
)