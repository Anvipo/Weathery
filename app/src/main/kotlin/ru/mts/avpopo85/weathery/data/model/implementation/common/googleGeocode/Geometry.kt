package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geometry(

    @SerializedName("viewport")
    @Expose
    val viewport: Viewport?,

    @SerializedName("bounds")
    @Expose
    val bounds: Bounds?,

    @SerializedName("location")
    @Expose
    val location: Location?,

    @SerializedName("location_type")
    @Expose
    val locationType: String?

)