package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultsItem(

    @SerializedName("formatted_address")
    @Expose
    val formattedAddress: String?,

    @SerializedName("types")
    @Expose
    val types: List<String?>?,

    @SerializedName("geometry")
    @Expose
    val geometry: Geometry?,

    @SerializedName("address_components")
    @Expose
    val addressComponents: List<AddressComponentsItem?>?,

    @SerializedName("place_id")
    @Expose
    val placeId: String?,

    @SerializedName("plus_code")
    @Expose
    val plusCode: PlusCode?

)
