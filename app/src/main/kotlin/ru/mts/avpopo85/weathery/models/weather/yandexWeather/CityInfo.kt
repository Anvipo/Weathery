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

        //id of locality
        @SerializedName("geoid")
        @Expose
        val localityId: Double,

        //url path on https://yandex.tld/pagoda
        @SerializedName("slug")
        @Expose
        val urlPath: String,

        //information about the time zone
        @SerializedName("tzinfo")
        @Expose
        val timeZoneInfo: TimeZoneInfo,

        //daylight saving time
        @SerializedName("dst")
        @Expose
        val daylightSavingTime: Boolean,

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
