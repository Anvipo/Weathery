package ru.mts.avpopo85.weathery.models.weather.yandex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimeZoneInfo(
        //часовой пояс в секундах от UTC
        @SerializedName("offset")
        @Expose
        val offsetFromUTCInSecond: Double,

        //название часового пояса
        @SerializedName("name")
        @Expose
        val name: String,

        //сокращённое название часового пояса
        @SerializedName("abbr")
        @Expose
        val abbr: String,

        //признак летнего времени
        @SerializedName("dst")
        @Expose
        val withSummerTime: Boolean
)
