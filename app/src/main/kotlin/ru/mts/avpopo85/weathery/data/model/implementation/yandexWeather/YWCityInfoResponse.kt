package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWCityInfoResponse

/**This object contains information about the locality.*/
@Suppress("SpellCheckingInspection", "unused")
data class YWCityInfoResponse(

    @SerializedName("lat")
    @Expose
    override val latitude: Double,

    @SerializedName("lon")
    @Expose
    override val longitude: Double,

    @SerializedName("tzinfo")
    @Expose
    override val timeZoneInfo: YWTimeZoneInfo,

    @SerializedName("def_pressure_mm")
    @Expose
    override val normalPressureMmHg: Double,

    @SerializedName("def_pressure_pa")
    @Expose
    override val pressureNormInPa: Double,

    @SerializedName("url")
    @Expose
    override val localityUrlPath: String

) : IYWCityInfoResponse
