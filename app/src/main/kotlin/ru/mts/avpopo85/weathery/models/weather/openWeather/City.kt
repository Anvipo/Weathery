package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City(
        //City ID
        @SerializedName("id")
        @Expose
        val id: Int,

        //City name
        @SerializedName("name")
        @Expose
        val name: String,

        @SerializedName("coord")
        @Expose
        val coordinates: Coordinates,

        //Country code (GB, JP etc.)
        @SerializedName("country")
        @Expose
        val countryCode: String
)
