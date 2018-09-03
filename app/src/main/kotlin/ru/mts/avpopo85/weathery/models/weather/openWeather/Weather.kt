package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
        //Weather condition id
        @SerializedName("id")
        @Expose
        val conditionId: Int,

        //Group of weather parameters (Rain, Snow, Extreme etc.)
        @SerializedName("main")
        @Expose
        val main: String,

        //Weather condition within the group
        @SerializedName("description")
        @Expose
        val description: String,

        //Weather icon id
        @SerializedName("icon")
        @Expose
        val icon: String
)
