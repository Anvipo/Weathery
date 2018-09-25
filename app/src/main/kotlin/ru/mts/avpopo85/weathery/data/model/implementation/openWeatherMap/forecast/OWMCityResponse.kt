package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMCityResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCoordinatesResponse

@Suppress("SpellCheckingInspection")
data class OWMCityResponse(

    @SerializedName("country")
    @Expose
    override val country: String,

    @SerializedName("coord")
    @Expose
    override val coordinates: OWMCoordinatesResponse,

    @SerializedName("name")
    @Expose
    override val name: String,

    @SerializedName("id")
    @Expose
    override val id: Int

) : IOWMCityResponse
