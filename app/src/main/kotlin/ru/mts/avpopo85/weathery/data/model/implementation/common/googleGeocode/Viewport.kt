package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Viewport(

    @SerializedName("southwest")
    @Expose
    val southwest: Southwest?,

    @SerializedName("northeast")
    @Expose
    val northeast: Northeast?

)
