package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlusCode(

    @SerializedName("compound_code")
    @Expose
    val compoundCode: String?,

    @SerializedName("global_code")
    @Expose
    val globalCode: String?

)
