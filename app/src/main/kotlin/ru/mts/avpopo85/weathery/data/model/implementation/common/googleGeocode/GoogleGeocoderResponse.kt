package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.utils.GoogleGeocodeStatus

data class GoogleGeocoderResponse(

    @SerializedName("plus_code")
    @Expose
    val plusCode: PlusCode?,

    @SerializedName("error_message")
    @Expose
    val errorMessage: String?,

    @SerializedName("results")
    @Expose
    val results: List<ResultsItem?>?,

    @SerializedName("status")
    @Expose
    val status: GoogleGeocodeStatus

)