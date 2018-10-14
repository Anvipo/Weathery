package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Southwest(

    @SerializedName("lng")
    @Expose
    val lng: Double?,

    @SerializedName("lat")
    @Expose
    val lat: Double?

)