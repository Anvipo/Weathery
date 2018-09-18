package ru.mts.avpopo85.weathery.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**This object contains information about the locality.*/
data class CityInfoResponse(

    /**The latitude (in degrees).*/
    @SerializedName("lat")
    @Expose
    val latitude: Double,

    /**The longitude (in degrees).*/
    @SerializedName("lon")
    @Expose
    val longitude: Double,

    /**Information about the time zone.*/
    @SerializedName("tzinfo")
    @Expose
    val timeZoneInfo: TimeZoneInfo,

    /**The normal pressure for the given coordinates (mm Hg).*/
    @SerializedName("def_pressure_mm")
    @Expose
    val normalPressureMmHg: Double,

    /**The normal pressure for the given coordinates (hPa).*/
    @SerializedName("def_pressure_pa")
    @Expose
    val pressureNormInPa: Double,

    /**Locality page on yandex.weather.*/
    @SerializedName("url")
    @Expose
    val localityUrlPath: String

)
