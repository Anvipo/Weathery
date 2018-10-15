package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates

data class Northeast(

    @SerializedName("lng")
    @Expose
    override val longitude: Double,

    @SerializedName("lat")
    @Expose
    override val latitude: Double

) : ICoordinates
