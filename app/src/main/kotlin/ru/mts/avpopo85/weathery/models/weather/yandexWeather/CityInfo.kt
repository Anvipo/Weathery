package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityInfo(
        //the latitude (in degrees)
        @SerializedName("lat")
        @Expose
        val latitude: Double,

        //the longitude (in degrees)
        @SerializedName("lon")
        @Expose
        val longitude: Double,

        //information about the time zone
        @SerializedName("tzinfo")
        @Expose
        val timeZoneInfo: TimeZoneInfo,

        //the normal pressure for the given coordinates (mm Hg)
        @SerializedName("def_pressure_mm")
        @Expose
        val normalPressureMmHg: Double,

        //the normal pressure for the given coordinates (hPa)
        @SerializedName("def_pressure_pa")
        @Expose
        val pressureNormInPa: Double,

        //locality page on yandex.weather
        @SerializedName("url")
        @Expose
        val localityUrlPath: String
)
