package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWTimeZoneInfo

/**Information about the time zone.*/
data class YWTimeZoneInfo(

    @SerializedName("offset")
    @Expose
    override val offsetFromUTCInSecond: Double,

    @SerializedName("name")
    @Expose
    override val name: String,

    @SerializedName("abbr")
    @Expose
    override val abbr: String,

    @SerializedName("dst")
    @Expose
    override val withSummerTime: Boolean

) : IYWTimeZoneInfo
