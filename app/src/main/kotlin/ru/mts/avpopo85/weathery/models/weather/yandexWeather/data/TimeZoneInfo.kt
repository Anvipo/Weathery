package ru.mts.avpopo85.weathery.models.weather.yandexWeather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**Information about the time zone.*/
data class TimeZoneInfo(
    /**Time zone in seconds from UTC.*/
    @SerializedName("offset")
    @Expose
    val offsetFromUTCInSecond: Double,

    /**Name of the time zone.*/
    @SerializedName("name")
    @Expose
    val name: String,

    /**Abbreviated name of the time zone.*/
    @SerializedName("abbr")
    @Expose
    val abbr: String,

    /**Daylight saving time.*/
    @SerializedName("dst")
    @Expose
    val withSummerTime: Boolean
)
