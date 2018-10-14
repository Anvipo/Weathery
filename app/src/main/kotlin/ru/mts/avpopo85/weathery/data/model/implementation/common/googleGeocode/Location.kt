package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(

	@SerializedName("lng")
	@Expose
	val lng: Double?,

	@SerializedName("lat")
	val lat: Double?

)