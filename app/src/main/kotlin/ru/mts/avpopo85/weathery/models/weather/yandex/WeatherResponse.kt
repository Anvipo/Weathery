package ru.mts.avpopo85.weathery.models.weather.yandex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        //the time on the server in Unix time
        @SerializedName("now")
        @Expose
        val serverInUnix: Int,

        //the time on the server in UTC
        @SerializedName("now_dt")
        @Expose
        val serverTimeInUTC: String,

        //locality information object
        @SerializedName("info")
        @Expose
        val cityInfo: CityInfo,

        //locality description object
        @SerializedName("geo_object")
        @Expose
        val geoObject: GeoObject,

        //current weather information object
        @SerializedName("fact")
        @Expose
        val currentWeather: CurrentWeather,

        //weather forecast object
        @SerializedName("forecasts")
        @Expose
        val forecasts: List<Forecasts>
)