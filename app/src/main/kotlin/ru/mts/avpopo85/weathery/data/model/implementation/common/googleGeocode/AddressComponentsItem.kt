package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddressComponentsItem(

    @SerializedName("types")
    @Expose
    val types: List<String?>?,

    @SerializedName("short_name")
    @Expose
    val shortName: String?,

    @SerializedName("long_name")
    @Expose
    val longName: String?

)